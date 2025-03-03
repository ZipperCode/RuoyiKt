package org.zipper.modules.system.service.client

import cn.hutool.crypto.SecureUtil
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import org.zipper.common.core.ext.MapStructExt.convert
import org.zipper.common.core.ext.MapStructExt.convertList
import org.zipper.framework.mybatis.core.MybatisKt
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.mybatis.core.selectVoPage
import org.zipper.modules.system.domain.entity.SysClientEntity
import org.zipper.modules.system.domain.param.SysClientQueryParam
import org.zipper.modules.system.domain.param.SysClientSaveParam
import org.zipper.modules.system.domain.vo.SysClientVo
import org.zipper.modules.system.mapper.SysClientMapper

/**
 * 客户端管理Service业务层处理
 *
 */
@Service
class SysClientServiceImpl(
    private val baseMapper: SysClientMapper
) : ISysClientService {


    /**
     * 查询客户端管理
     */
    override fun queryById(id: Long?): SysClientVo? {
        val vo = baseMapper.selectById(id)?.convert<SysClientVo?>() ?: return null
        vo.grantTypeList = vo.grantType?.split(",")
        return vo
    }


    /**
     * 查询客户端管理
     */
    override fun queryByClientId(clientId: String?): SysClientEntity? {
        return baseMapper.selectOne(MybatisKt.ktQuery<SysClientEntity>().eq(SysClientEntity::clientId, clientId))
    }

    /**
     * 查询客户端管理列表
     */
    override fun queryPageList(param: SysClientQueryParam, pageQuery: PageQuery): TableDataInfo<SysClientVo> {
        val lqw = buildQueryWrapper(param)
        val result = baseMapper.selectVoPage<SysClientEntity, SysClientVo>(pageQuery.build(), lqw)
        result.records.forEach { r: SysClientVo ->
            r.grantTypeList = r.grantType?.split(",")
        }
        return TableDataInfo.build(result)
    }

    /**
     * 查询客户端管理列表
     */
    override fun queryList(): List<SysClientVo> {
        return baseMapper.selectList(
            MybatisKt.ktQuery<SysClientEntity>()
                .orderByAsc(SysClientEntity::id)
        ).convertList()
    }

    private fun buildQueryWrapper(param: SysClientQueryParam): KtQueryWrapper<SysClientEntity> {
        val lqw = MybatisKt.ktQuery<SysClientEntity>()
        lqw.eq(StringUtils.isNotBlank(param.clientId), SysClientEntity::clientId, param.clientId)
        lqw.eq(StringUtils.isNotBlank(param.clientKey), SysClientEntity::clientKey, param.clientKey)
        lqw.eq(StringUtils.isNotBlank(param.clientSecret), SysClientEntity::clientSecret, param.clientSecret)
        lqw.eq(StringUtils.isNotBlank(param.status), SysClientEntity::status, param.status)
        lqw.orderByAsc(SysClientEntity::id)
        return lqw
    }

    /**
     * 新增客户端管理
     */
    override fun insert(param: SysClientSaveParam): Boolean {
        val add = param.convert<SysClientEntity>()
        add.grantType = java.lang.String.join(",", param.grantTypeList)
        // 生成clientid
        val clientKey = param.clientKey
        val clientSecret = param.clientSecret
        add.clientId = SecureUtil.md5(clientKey + clientSecret)
        val flag = baseMapper.insert(add) > 0
        if (flag) {
            param.id = add.id
        }
        return flag
    }

    /**
     * 修改客户端管理
     */
    override fun update(param: SysClientSaveParam): Boolean {
        val update: SysClientEntity = param.convert()
        update.grantType = java.lang.String.join(",", param.grantTypeList)
        return baseMapper.updateById(update) > 0
    }

    /**
     * 修改状态
     */
    override fun updateUserStatus(id: Long, status: String): Int {
        return baseMapper.update(
            null,
            MybatisKt.ktUpdate<SysClientEntity>()
                .set(SysClientEntity::status, status)
                .eq(SysClientEntity::id, id)
        )
    }

    /**
     * 批量删除客户端管理
     */
    override fun deleteWithValidByIds(ids: Collection<Long>, isValid: Boolean): Boolean {
        return baseMapper.deleteBatchIds(ids) > 0
    }
}
