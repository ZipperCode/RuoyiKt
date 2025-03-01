package org.zipper.modules.system.domain.param

import io.github.linpeilie.annotations.AutoMapper
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.system.domain.entity.SysConfigEntity
import org.zipper.common.core.domain.mixin.sys.SysConfigMixin
import org.zipper.framework.mybatis.core.domain.BaseMixinParam

/**
 * 参数配置 dto
 *
 */
@AutoMapper(target = SysConfigEntity::class, reverseConvertGenerate = false)
class SysConfigParam : BaseMixinParam(), SysConfigMixin {
    /**
     * 参数主键
     */
    override var configId: Long? = null

    /**
     * 参数名称
     */
    @field:NotBlank(message = "参数名称不能为空")
    @field:Size(
        min = 0,
        max = 100,
        message = "参数名称不能超过{max}个字符"
    )
    override var configName: String? = null

    /**
     * 参数键名
     */
    @field:NotBlank(message = "参数键名不能为空")
    @field:Size(
        min = 0,
        max = 100,
        message = "参数键名长度不能超过{max}个字符"
    )
    override var configKey: String? = null

    /**
     * 参数键值
     */
    @field:NotBlank(message = "参数键值不能为空")
    @field:Size(
        min = 0,
        max = 500,
        message = "参数键值长度不能超过{max}个字符"
    )
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
