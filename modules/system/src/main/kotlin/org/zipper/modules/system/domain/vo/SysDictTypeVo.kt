package org.zipper.modules.system.domain.vo

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated
import com.alibaba.excel.annotation.ExcelProperty
import io.github.linpeilie.annotations.AutoMapper
import org.zipper.modules.system.domain.entity.SysDictTypeEntity
import org.zipper.modules.system.domain.mixin.SysDictTypeMixin
import java.io.Serializable
import java.util.*

/**
 * 字典类型视图对象 sys_dict_type
 *
 * @author Michelle.Chung
 */
@ExcelIgnoreUnannotated
@AutoMapper(target = SysDictTypeEntity::class)
class SysDictTypeVo : SysDictTypeMixin, Serializable {
    /**
     * 字典主键
     */
    @field:ExcelProperty(value = ["字典主键"])
    override var dictId: Long? = null

    /**
     * 字典名称
     */
    @field:ExcelProperty(value = ["字典名称"])
    override var dictName: String = ""

    /**
     * 字典类型
     */
    @field:ExcelProperty(value = ["字典类型"])
    override var dictType: String = ""

    /**
     * 备注
     */
    @field:ExcelProperty(value = ["备注"])
    override var remark: String? = null

    /**
     * 创建时间
     */
    @field:ExcelProperty(value = ["创建时间"])
    var createTime: Date? = null

}
