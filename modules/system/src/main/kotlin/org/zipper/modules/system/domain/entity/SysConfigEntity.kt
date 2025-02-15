package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity
import org.zipper.modules.system.domain.mixin.SysConfigMixin

/**
 * 参数配置表 sys_config
 *
 */
@TableName("sys_config")
open class SysConfigEntity : BaseMixinEntity(), SysConfigMixin {
    /**
     * 参数主键
     */
    @field:TableId(value = "config_id")
    override var configId: Long? = null

    /**
     * 参数名称
     */
    override var configName: String? = null

    /**
     * 参数键名
     */
    override var configKey: String? = null

    /**
     * 参数键值
     */
    override var configValue: String? = null

    /**
     * 系统内置（Y是 N否）
     */
    override var configType: String? = null

    /**
     * 备注
     */
    override var remark: String? = null
}
