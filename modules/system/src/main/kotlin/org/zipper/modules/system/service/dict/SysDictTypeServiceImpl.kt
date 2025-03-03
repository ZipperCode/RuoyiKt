package org.zipper.modules.system.service.dict

import cn.dev33.satoken.context.SaHolder
import cn.hutool.core.collection.CollUtil
import cn.hutool.core.util.ObjectUtil
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import org.apache.commons.lang3.StringUtils
import org.springframework.cache.annotation.CachePut
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.zipper.common.core.constant.CacheConstants
import org.zipper.common.core.constant.CacheNames
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.MapStructExt.convert
import org.zipper.common.core.ext.MapStructExt.convertList
import org.zipper.common.core.ext.MapStructExt.convertOrNull
import org.zipper.common.core.ext.MapStructExt.convertTypeList
import org.zipper.common.core.ext.forceCastOrNull
import org.zipper.common.core.ext.selfAopProxy
import org.zipper.framework.mybatis.core.MybatisKt
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.mybatis.core.selectVoPage
import org.zipper.framework.redis.utils.CacheUtils
import org.zipper.modules.system.domain.param.SysDictTypeParam
import org.zipper.modules.system.domain.entity.SysDictDataEntity
import org.zipper.modules.system.domain.entity.SysDictTypeEntity
import org.zipper.modules.system.domain.vo.SysDictDataVo
import org.zipper.modules.system.domain.vo.SysDictTypeVo
import org.zipper.modules.system.mapper.SysDictDataMapper
import org.zipper.modules.system.mapper.SysDictTypeMapper
import org.zipper.modules.system.mapper.selectDictDataByType

/**
 * 字典 业务层处理
 *
 */
@Service
class SysDictTypeServiceImpl(
    private val baseMapper: SysDictTypeMapper,
    private val dictDataMapper: SysDictDataMapper
) : ISysDictTypeService, DictService {


    override fun selectPageDictTypeList(dictType: SysDictTypeParam, pageQuery: PageQuery): TableDataInfo<SysDictTypeVo> {
        val lqw = buildQueryWrapper(dictType)
        return TableDataInfo.build(baseMapper.selectVoPage<SysDictTypeEntity, SysDictTypeVo>(pageQuery.build(), lqw))
    }

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    override fun selectDictTypeList(dictType: SysDictTypeParam): List<SysDictTypeVo> {
        val lqw = buildQueryWrapper(dictType)
        return baseMapper.selectList(lqw).convertList()
    }

    private fun buildQueryWrapper(bo: SysDictTypeParam): KtQueryWrapper<SysDictTypeEntity> {
        val params: Map<String, Any?> = bo.params
        val lqw = MybatisKt.ktQuery<SysDictTypeEntity>()
        lqw.like(StringUtils.isNotBlank(bo.dictName), SysDictTypeEntity::dictName, bo.dictName)
        lqw.like(StringUtils.isNotBlank(bo.dictType), SysDictTypeEntity::dictType, bo.dictType)
        lqw.between(
            params["beginTime"] != null && params["endTime"] != null,
            SysDictTypeEntity::createTime, params["beginTime"], params["endTime"]
        )
        lqw.orderByAsc(SysDictTypeEntity::dictId)
        return lqw
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    override fun selectDictTypeAll(): List<SysDictTypeVo> {
        return baseMapper.selectList(QueryWrapper()).convertTypeList()
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
//    @Cacheable(cacheNames = [CacheNames.SYS_DICT], key = "#dictType")
    override fun selectDictDataByType(dictType: String?): List<SysDictDataVo>? {
        val dictDatas: List<SysDictDataVo> = dictDataMapper.selectDictDataByType(dictType)
        if (CollUtil.isNotEmpty(dictDatas)) {
            return dictDatas
        }
        return null
    }

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    override fun selectDictTypeById(dictId: Long?): SysDictTypeVo? {
        return baseMapper.selectById(dictId).convertOrNull()
    }

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型
     */
    override fun selectDictTypeByType(dictType: String?): SysDictTypeVo? {
        return baseMapper.selectOne(
            MybatisKt.ktQuery<SysDictTypeEntity>().eq(SysDictTypeEntity::dictType, dictType)
        ).convertOrNull()
    }

    /**
     * 批量删除字典类型信息
     *
     * @param dictIds 需要删除的字典ID
     */
    override fun deleteDictTypeByIds(dictIds: Array<Long>) {
        for (dictId in dictIds) {
            val dictType: SysDictTypeEntity = baseMapper.selectById(dictId)
            if (dictDataMapper.exists(
                    MybatisKt.ktQuery<SysDictDataEntity>()
                        .eq(SysDictDataEntity::dictType, dictType.dictType)
                )
            ) {
                throw ServiceException(String.format("%1\$s已分配,不能删除", dictType.dictName))
            }
            CacheUtils.evict(CacheNames.SYS_DICT, dictType.dictType)
        }
        baseMapper.deleteBatchIds(listOf(*dictIds))
    }

    /**
     * 重置字典缓存数据
     */
    override fun resetDictCache() {
        CacheUtils.clear(CacheNames.SYS_DICT)
    }

    /**
     * 新增保存字典类型信息
     *
     * @param bo 字典类型信息
     * @return 结果
     */
    @CachePut(cacheNames = [CacheNames.SYS_DICT], key = "#bo.dictType")
    override fun insertDictType(bo: SysDictTypeParam): List<SysDictDataVo> {
        val dict: SysDictTypeEntity = bo.convert()
        val row: Int = baseMapper.insert(dict)
        if (row > 0) {
            // 新增 type 下无 data 数据 返回空防止缓存穿透
            return ArrayList<SysDictDataVo>()
        }
        throw ServiceException("操作失败")
    }

    /**
     * 修改保存字典类型信息
     *
     * @param bo 字典类型信息
     * @return 结果
     */
    @CachePut(cacheNames = [CacheNames.SYS_DICT], key = "#bo.dictType")
    @Transactional(rollbackFor = [Exception::class])
    override fun updateDictType(bo: SysDictTypeParam): List<SysDictDataVo> {
        val dict: SysDictTypeEntity = bo.convert()
        val oldDict: SysDictTypeEntity = baseMapper.selectById(dict.dictId)
        dictDataMapper.update(
            null, MybatisKt.ktUpdate<SysDictDataEntity>()
                .set(SysDictDataEntity::dictType, dict.dictType)
                .eq(SysDictDataEntity::dictType, oldDict.dictType)
        )
        val row: Int = baseMapper.updateById(dict)
        if (row > 0) {
            CacheUtils.evict(CacheNames.SYS_DICT, oldDict.dictType)
            return dictDataMapper.selectDictDataByType(dict.dictType)
        }
        throw ServiceException("操作失败")
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @return 结果
     */
    override fun checkDictTypeUnique(dictType: SysDictTypeParam): Boolean {
        val exist: Boolean = baseMapper.exists(
            MybatisKt.ktQuery<SysDictTypeEntity>()
                .eq(SysDictTypeEntity::dictType, dictType.dictType)
                .ne(ObjectUtil.isNotNull(dictType.dictId), SysDictTypeEntity::dictId, dictType.dictId)
        )
        return !exist
    }

    /**
     * 根据字典类型和字典值获取字典标签
     *
     * @param dictType  字典类型
     * @param dictValue 字典值
     * @param separator 分隔符
     * @return 字典标签
     */
    override fun getDictLabel(dictType: String, dictValue: String, separator: String): String {
        // 优先从本地缓存获取
        var datas: List<SysDictDataVo>? = SaHolder.getStorage().get(CacheConstants.SYS_DICT_KEY + dictType).forceCastOrNull()
        if (ObjectUtil.isNull(datas)) {
            datas = selfAopProxy().selectDictDataByType(dictType)
            if (datas != null) {
                SaHolder.getStorage().set(CacheConstants.SYS_DICT_KEY + dictType, datas)
            }
        }

        val map = datas?.associate { it.dictValue to it.dictLabel } ?: return ""
        if (StringUtils.containsAny(dictValue, separator)) {
            return dictValue.split(separator).joinToString(separator) { v -> map[v] ?: "" }
        }
        return map.getOrDefault(dictValue, StringUtils.EMPTY)
    }

    /**
     * 根据字典类型和字典标签获取字典值
     *
     * @param dictType  字典类型
     * @param dictLabel 字典标签
     * @param separator 分隔符
     * @return 字典值
     */
    override fun getDictValue(dictType: String, dictLabel: String, separator: String): String {
        // 优先从本地缓存获取
        var datas: List<SysDictDataVo>? = SaHolder.getStorage().get(CacheConstants.SYS_DICT_KEY + dictType).forceCastOrNull()
        if (ObjectUtil.isNull(datas)) {
            datas = selfAopProxy().selectDictDataByType(dictType)
            if (datas != null) {
                SaHolder.getStorage().set(CacheConstants.SYS_DICT_KEY + dictType, datas)
            }
        }

        val map = datas?.associate { it.dictLabel to it.dictValue } ?: return ""

        if (StringUtils.containsAny(dictLabel, separator)) {
            return dictLabel.split(separator).joinToString(separator) { v -> map[v] ?: "" }
        }
        return map.getOrDefault(dictLabel, StringUtils.EMPTY)
    }

    override fun getAllDictByDictType(dictType: String): Map<String, String> {
        val list: List<SysDictDataVo>? = selectDictDataByType(dictType)
        return list?.associate { it.dictValue to it.dictLabel } ?: emptyMap()
    }
}
