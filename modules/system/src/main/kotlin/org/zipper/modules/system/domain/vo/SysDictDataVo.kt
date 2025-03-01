package org.zipper.modules.system.domain.vo

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated
import com.alibaba.excel.annotation.ExcelProperty
import io.github.linpeilie.annotations.AutoMapper
import org.zipper.framework.excel.annotation.ExcelDictFormat
import org.zipper.modules.system.domain.entity.SysDictDataEntity
import org.zipper.common.core.domain.mixin.sys.SysDictDataMixin
import org.zipper.modules.system.excel.ExcelDictConvert
import java.io.Serializable
import java.util.*

/**
 * 字典数据视图对象 sys_dict_data
 *
 * @author Michelle.Chung
 */
@ExcelIgnoreUnannotated
@AutoMapper(target = SysDictDataEntity::class)
open class SysDictDataVo : Serializable, SysDictDataMixin {
    /**
     * 字典编码
     */
    @field:ExcelProperty(value = ["字典编码"])
    override var dictCode: Long? = null

    /**
     * 字典排序
     */
    @field:ExcelProperty(value = ["字典排序"])
    override var dictSort: Int? = null

    /**
     * 字典标签
     */
    @field:ExcelProperty(value = ["字典标签"])
    override var dictLabel: String = ""

    /**
     * 字典键值
     */
    @field:ExcelProperty(value = ["字典键值"])
    override var dictValue: String = ""

    /**
     * 字典类型
     */
    @field:ExcelProperty(value = ["字典类型"])
    override var dictType: String? = null

    /**
     * 样式属性（其他样式扩展）
     */
    override var cssClass: String? = null

    /**
     * 表格回显样式
     */
    override var listClass: String? = null

    /**
     * 是否默认（Y是 N否）
     */
    @field:ExcelProperty(value = ["是否默认"], converter = ExcelDictConvert::class)
    @field:ExcelDictFormat(dictType = "sys_yes_no")
    override var isDefault: String? = null

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
