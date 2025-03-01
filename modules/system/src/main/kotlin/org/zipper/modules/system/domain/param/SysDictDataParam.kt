package org.zipper.modules.system.domain.param

import io.github.linpeilie.annotations.AutoMapper
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.system.domain.entity.SysDictDataEntity
import org.zipper.common.core.domain.mixin.sys.SysDictDataMixin
import org.zipper.framework.mybatis.core.domain.BaseMixinParam

/**
 * 字典数据业务对象 dto
 *
 */
@AutoMapper(target = SysDictDataEntity::class, reverseConvertGenerate = false)
class SysDictDataParam : BaseMixinParam(), SysDictDataMixin {
    /**
     * 字典编码
     */
    override var dictCode: Long? = null

    /**
     * 字典排序
     */
    override var dictSort: Int? = null

    /**
     * 字典标签
     */
    @field:NotBlank(message = "字典标签不能为空")
    @field:Size(
        min = 0,
        max = 100,
        message = "字典标签长度不能超过{max}个字符"
    )
    override var dictLabel: String = ""

    /**
     * 字典键值
     */
    @field:NotBlank(message = "字典键值不能为空")
    @field:Size(
        min = 0,
        max = 100,
        message = "字典键值长度不能超过{max}个字符"
    )
    override var dictValue: String = ""

    /**
     * 字典类型
     */
    @field:NotBlank(message = "字典类型不能为空")
    @field:Size(
        min = 0,
        max = 100,
        message = "字典类型长度不能超过{max}个字符"
    )
    override var dictType: String? = null

    /**
     * 样式属性（其他样式扩展）
     */
    @field:Size(min = 0, max = 100, message = "样式属性长度不能超过{max}个字符")
    override var cssClass: String? = null

    /**
     * 表格回显样式
     */
    override var listClass: String? = null

    /**
     * 是否默认（Y是 N否）
     */
    override var isDefault: String? = null

    /**
     * 备注
     */
    override var remark: String? = null
}
