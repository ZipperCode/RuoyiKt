package org.zipper.common.core.domain.mixin.sys

interface SysDictDataMixin {
    /**
     * 字典编码
     */
    var dictCode: Long?

    /**
     * 字典排序
     */
    var dictSort: Int?

    /**
     * 字典标签
     */
    var dictLabel: String

    /**
     * 字典键值
     */
    var dictValue: String

    /**
     * 字典类型
     */
    var dictType: String?

    /**
     * 样式属性（其他样式扩展）
     */
    var cssClass: String?

    /**
     * 表格字典样式
     */
    var listClass: String?

    /**
     * 是否默认（Y是 N否）
     */

    var isDefault: String?

    /**
     * 备注
     */
    var remark: String?
}