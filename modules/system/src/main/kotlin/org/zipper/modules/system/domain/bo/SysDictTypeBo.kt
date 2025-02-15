package org.zipper.modules.system.domain.bo

import io.github.linpeilie.annotations.AutoMapper
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.system.domain.entity.SysDictTypeEntity

/**
 * 字典类型业务对象 sys_dict_type
 *
 * @author Michelle.Chung
 */
@AutoMapper(target = SysDictTypeEntity::class, reverseConvertGenerate = false)
class SysDictTypeBo : BaseMixinVo() {
    /**
     * 字典主键
     */
    var dictId: Long? = null

    /**
     * 字典名称
     */
    @field:NotBlank(message = "字典名称不能为空")
    @field:Size(
        min = 0,
        max = 100,
        message = "字典类型名称长度不能超过{max}个字符"
    )
    var dictName: String? = null

    /**
     * 字典类型
     */
    @field:NotBlank(message = "字典类型不能为空")
    @field:Size(
        min = 0,
        max = 100,
        message = "字典类型类型长度不能超过{max}个字符"
    )
    @field:Pattern(regexp = "^[a-z][a-z0-9_]*$", message = "字典类型必须以字母开头，且只能为（小写字母，数字，下滑线）")
    var dictType: String? = null

    /**
     * 备注
     */
    var remark: String? = null
}
