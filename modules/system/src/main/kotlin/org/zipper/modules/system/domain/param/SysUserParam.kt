package org.zipper.modules.system.domain.param

import io.github.linpeilie.annotations.AutoMapper
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.zipper.common.core.constant.UserConstants
import org.zipper.framework.core.xss.Xss
import org.zipper.framework.mybatis.core.domain.BaseMixinVo
import org.zipper.modules.system.domain.entity.SysUserEntity
import org.zipper.modules.system.domain.mixin.SysUserMixin

/**
 * 用户信息业务对象 dto
 *
 */
@AutoMapper(target = SysUserEntity::class, reverseConvertGenerate = false)
class SysUserParam : BaseMixinVo(), SysUserMixin {
    /**
     * 用户ID
     */
    override var userId: Long? = null

    /**
     * 部门ID
     */
    override var deptId: Long? = null

    /**
     * 用户账号
     */
    @field:Xss(message = "用户账号不能包含脚本字符")
    @field:NotBlank(message = "用户账号不能为空")
    @field:Size(
        min = 0,
        max = 30,
        message = "用户账号长度不能超过{max}个字符"
    )
    override var userName: String? = null

    /**
     * 用户昵称
     */
    @field:Xss(message = "用户昵称不能包含脚本字符")
    @field:NotBlank(message = "用户昵称不能为空")
    @field:Size(
        min = 0,
        max = 30,
        message = "用户昵称长度不能超过{max}个字符"
    )
    override var nickName: String? = null

    /**
     * 用户类型（sys_user系统用户）
     */
    override var userType: String? = null

    /**
     * 用户邮箱
     */
    @field:Email(message = "邮箱格式不正确")
    @field:Size(min = 0, max = 50, message = "邮箱长度不能超过{max}个字符")
    override var email: String? = null

    /**
     * 手机号码
     */
    override var phonenumber: String? = null

    /**
     * 用户性别（0男 1女 2未知）
     */
    override var sex: String? = null

    /**
     * 密码
     */
    override var password: String? = null

    /**
     * 帐号状态（0正常 1停用）
     */
    override var status: String? = null

    /**
     * 备注
     */
    override var remark: String? = null

    /**
     * 角色组
     */
    var roleIds: Array<Long> = emptyArray()

    /**
     * 岗位组
     */
    var postIds: Array<Long> = emptyArray()

    /**
     * 数据权限 当前角色ID
     */
    var roleId: Long? = null

    val isSuperAdmin: Boolean
        get() = UserConstants.SUPER_ADMIN_ID.equals(this.userId)
}
