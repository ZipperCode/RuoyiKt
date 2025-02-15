package org.zipper.modules.system.domain.mixin

interface SysUserMixin {
    /**
     * 用户ID
     */
    var userId: Long?

    /**
     * 部门ID
     */
    var deptId: Long?

    /**
     * 用户账号
     */
    var userName: String?

    /**
     * 用户昵称
     */
    var nickName: String?

    /**
     * 用户类型（sys_user系统用户）
     */
    var userType: String?

    /**
     * 用户邮箱
     */
    var email: String?

    /**
     * 手机号码
     */
    var phonenumber: String?

    /**
     * 用户性别
     */
    var sex: String?

    /**
     * 密码
     */
    var password: String?

    /**
     * 帐号状态（0正常 1停用）
     */
    var status: String?

    /**
     * 备注
     */
    var remark: String?
}