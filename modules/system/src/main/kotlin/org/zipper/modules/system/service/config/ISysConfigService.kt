package org.zipper.modules.system.service.config

import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.modules.system.domain.param.SysConfigParam
import org.zipper.modules.system.domain.vo.SysConfigVo


/**
 * 参数配置 服务层
 *
 * @author Lion Li
 */
interface ISysConfigService {
    fun selectPageConfigList(config: SysConfigParam, pageQuery: PageQuery): TableDataInfo<SysConfigVo>

    /**
     * 查询参数配置信息
     *
     * @param configId 参数配置ID
     * @return 参数配置信息
     */
    fun selectConfigById(configId: Long?): SysConfigVo?

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    fun selectConfigByKey(configKey: String?): String?

    /**
     * 获取注册开关
     * @param tenantId 租户id
     * @return true开启，false关闭
     */
    fun selectRegisterEnabled(tenantId: String?): Boolean

    /**
     * 查询参数配置列表
     *
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    fun selectConfigList(config: SysConfigParam): List<SysConfigVo>

    /**
     * 新增参数配置
     *
     * @param bo 参数配置信息
     * @return 结果
     */
    fun insertConfig(bo: SysConfigParam): String?

    /**
     * 修改参数配置
     *
     * @param bo 参数配置信息
     * @return 结果
     */
    fun updateConfig(bo: SysConfigParam): String?

    /**
     * 批量删除参数信息
     *
     * @param configIds 需要删除的参数ID
     */
    fun deleteConfigByIds(configIds: Array<Long>)

    /**
     * 重置参数缓存数据
     */
    fun resetConfigCache()

    /**
     * 校验参数键名是否唯一
     *
     * @param config 参数信息
     * @return 结果
     */
    fun checkConfigKeyUnique(config: SysConfigParam): Boolean
}
