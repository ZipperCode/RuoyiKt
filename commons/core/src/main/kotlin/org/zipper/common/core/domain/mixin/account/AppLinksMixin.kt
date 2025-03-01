package org.zipper.common.core.domain.mixin.account

interface AppLinksMixin {
    /**
     * 数据id
     */
    var id: Long?

    /**
     * 链接
     */
    var link: String?

    /**
     * 分类
     */
    var classify: Int?

    /**
     * 备注
     */
    var remark: String?
}