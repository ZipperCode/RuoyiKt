package org.zipper.common.core.domain.mixin.sys

interface SysPostMixin {

    /**
     * 岗位序号
     */
    var postId: Long?

    /**
     * 岗位编码
     */
    var postCode: String?

    /**
     * 岗位名称
     */
    var postName: String?

    /**
     * 岗位排序
     */
    var postSort: Int?

    /**
     * 状态（0正常 1停用）
     */
    var status: String?

    /**
     * 备注
     */
    var remark: String?
}