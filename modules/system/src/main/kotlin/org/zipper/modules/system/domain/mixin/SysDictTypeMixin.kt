package org.zipper.modules.system.domain.mixin

interface SysDictTypeMixin {
    /**
     * 字典主键
     */
    var dictId: Long?

    /**
     * 字典名称
     */
    var dictName: String

    /**
     * 字典类型
     */
    var dictType: String

    /**
     * 备注
     */
    var remark: String?
}