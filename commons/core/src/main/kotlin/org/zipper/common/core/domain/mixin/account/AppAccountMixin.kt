package org.zipper.common.core.domain.mixin.account



interface AppAccountMixin {
    /**
     * 数据id
     */
    var id: Long?
    /**
     * 账号或者链接
     */
    var account: String?

    /**
     * 地区
     */
    var country: String?

    /**
     * 工作
     */
    var work: String?

    /**
     * 收入
     */
    var income: Float?

    /**
     * 年龄
     */
    var age: Int?

    /**
     * 数据分类[DataClassify]
     */
    var classify: Int?

    /**
     * 数据状态[DataStatus]
     */
    var status: Int?

    /**
     * 备注
     */
    var remark: String?

    /**
     * 链接备注
     */
    var linkRemark: String?
    /**
     * 截图
     */
    var screenshot: String?

    /**
     * 修改人
     */
    var modifier: String?
}