package org.zipper.modules.system.service.config

import cn.hutool.core.util.ObjectUtil
import com.baomidou.mybatisplus.extension.kotlin.KtQueryWrapper
import org.apache.commons.lang3.StringUtils
import org.springframework.aop.framework.AopContext
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.zipper.common.core.constant.CacheNames
import org.zipper.common.core.constant.UserConstants
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.MapStructExt.convert
import org.zipper.common.core.ext.MapStructExt.convertList
import org.zipper.common.core.ext.MapStructExt.convertOrNull
import org.zipper.framework.mybatis.core.MybatisKt
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.mybatis.core.selectVoPage
import org.zipper.framework.redis.utils.CacheUtils
import org.zipper.modules.system.domain.param.SysConfigParam
import org.zipper.modules.system.domain.entity.SysConfigEntity
import org.zipper.modules.system.domain.vo.SysConfigVo
import org.zipper.modules.system.mapper.SysConfigMapper

/**
 * 参数配置 服务层实现
 *
 * @author Lion Li
 */
@Service
class SysConfigServiceImpl(
    private val baseMapper: SysConfigMapper
) : ISysConfigService, ConfigService {


    override fun selectPageConfigList(config: SysConfigParam, pageQuery: PageQuery): TableDataInfo<SysConfigVo> {
        val lqw = buildQueryWrapper(config)
        val page = baseMapper.selectVoPage<SysConfigEntity, SysConfigVo>(pageQuery.build(), lqw)
        return TableDataInfo.build(page)
    }

    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
//    @DS("master")
    override fun selectConfigById(configId: Long?): SysConfigVo? {
        return baseMapper.selectById(configId).convertOrNull<SysConfigVo>()
    }

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数key
     * @return 参数键值
     */
    @Cacheable(cacheNames = [CacheNames.SYS_CONFIG], key = "#configKey")
    override fun selectConfigByKey(configKey: String?): String {
        val retConfig = baseMapper.selectOne(
            MybatisKt.ktQuery<SysConfigEntity>()
                .eq(SysConfigEntity::configKey, configKey)
        )
        if (ObjectUtil.isNotNull(retConfig)) {
            return retConfig.configValue!!
        }
        return ""
    }

    /**
     * 获取注册开关
     * @param tenantId 租户id
     * @return true开启，false关闭
     */
    override fun selectRegisterEnabled(tenantId: String?): Boolean {
//        val retConfig: SysConfigEntity = TenantHelper.dynamic(tenantId, Supplier {
//            baseMapper.selectOne(
//                MybatisKt.ktQuery<SysConfigEntity>()
//                    .eq(SysConfigEntity::configKey, "sys.account.registerUser")
//            )
//        })
//        if (ObjectUtil.isNull(retConfig)) {
//            return false
//        }
//        return Convert.toBool(retConfig.configValue)
        return true
    }

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    override fun selectConfigList(config: SysConfigParam): List<SysConfigVo> {
        val lqw = buildQueryWrapper(config)
        return baseMapper.selectList(lqw).convertList<SysConfigVo>()
    }

    private fun buildQueryWrapper(bo: SysConfigParam): KtQueryWrapper<SysConfigEntity> {
        val params: Map<String, Any?> = bo.params
        val lqw = MybatisKt.ktQuery<SysConfigEntity>()
        lqw.like(StringUtils.isNotBlank(bo.configName), SysConfigEntity::configName, bo.configName)
        lqw.eq(StringUtils.isNotBlank(bo.configType), SysConfigEntity::configType, bo.configType)
        lqw.like(StringUtils.isNotBlank(bo.configKey), SysConfigEntity::configKey, bo.configKey)
        lqw.between(
            params["beginTime"] != null && params["endTime"] != null,
            SysConfigEntity::createTime, params["beginTime"], params["endTime"]
        )
        lqw.orderByAsc(SysConfigEntity::configId)
        return lqw
    }

    /**
     * 新增参数配置
     *
     * @param bo 参数配置信息
     * @return 结果
     */
    @CachePut(cacheNames = [CacheNames.SYS_CONFIG], key = "#bo.configKey")
    override fun insertConfig(bo: SysConfigParam): String? {
        val config = bo.convert<SysConfigEntity>()
        val row = baseMapper.insert(config)
        if (row > 0) {
            return config.configValue
        }
        throw ServiceException("操作失败")
    }

    /**
     * 修改参数配置
     *
     * @param bo 参数配置信息
     * @return 结果
     */
    @CachePut(cacheNames = [CacheNames.SYS_CONFIG], key = "#bo.configKey")
    override fun updateConfig(bo: SysConfigParam): String? {
        val row: Int
        val config: SysConfigEntity = bo.convert()
        if (config.configId != null) {
            val temp = baseMapper.selectById(config.configId)
            if (!StringUtils.equals(temp.configKey, config.configKey)) {
                CacheUtils.evict(CacheNames.SYS_CONFIG, temp!!.configKey!!)
            }
            row = baseMapper.updateById(config)
        } else {
            row = baseMapper.update(
                config, MybatisKt.ktQuery<SysConfigEntity>()
                    .eq(SysConfigEntity::configKey, config.configKey)
            )
        }
        if (row > 0) {
            return config.configValue
        }
        throw ServiceException("操作失败")
    }

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     */
    override fun deleteConfigByIds(configIds: Array<Long>) {
        for (configId in configIds) {
            val config = baseMapper.selectById(configId)
            if (StringUtils.equals(UserConstants.YES, config.configType)) {
                throw ServiceException(String.format("内置参数【%1\$s】不能删除 ", config.configKey))
            }
            CacheUtils.evict(CacheNames.SYS_CONFIG, config.configKey!!)
        }
        baseMapper.deleteBatchIds(listOf(*configIds))
    }

    /**
     * 重置参数缓存数据
     */
    override fun resetConfigCache() {
        CacheUtils.clear(CacheNames.SYS_CONFIG)
    }

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数配置信息
     * @return 结果
     */
    override fun checkConfigKeyUnique(config: SysConfigParam): Boolean {
        val configId = if (ObjectUtil.isNull(config.configId)) -1L else config.configId
        val info = baseMapper.selectOne(MybatisKt.ktQuery<SysConfigEntity>().eq(SysConfigEntity::configKey, config.configKey))
        if (ObjectUtil.isNotNull(info) && info.configId != configId) {
            return false
        }
        return true
    }

    /**
     * 根据参数 key 获取参数值
     *
     * @param configKey 参数 key
     * @return 参数值
     */
    override fun getConfigValue(configKey: String): String {
        return (AopContext.currentProxy() as SysConfigServiceImpl).selectConfigByKey(configKey)
    }
}
