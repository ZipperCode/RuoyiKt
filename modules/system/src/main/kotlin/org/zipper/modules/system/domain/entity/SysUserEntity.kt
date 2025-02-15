package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.*
import org.zipper.common.core.constant.UserConstants
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity
import org.zipper.framework.mybatis.core.domain.LogicDeleteMixin
import org.zipper.modules.system.domain.mixin.SysUserMixin
import java.util.*

/**
 * 用户对象 sys_user
 *
 * @author Lion Li
 */
@TableName("sys_user")
class SysUserEntity : BaseMixinEntity(), SysUserMixin, LogicDeleteMixin {
    /**
     * 用户ID
     */
    @field:TableId(value = "user_id")
    override var userId: Long? = null

    /**
     * 部门ID
     */
    override var deptId: Long? = null

    /**
     * 用户账号
     */
    override var userName: String? = null

    /**
     * 用户昵称
     */
    override var nickName: String? = null

    /**
     * 用户类型（sys_user系统用户）
     */
    override var userType: String? = null

    /**
     * 用户邮箱
     */
    override var email: String? = null

    /**
     * 手机号码
     */
    override var phonenumber: String? = null

    /**
     * 用户性别
     */
    override var sex: String? = null

    /**
     * 用户头像
     */
    var avatar: Long? = null

    /**
     * 密码
     */
    @field:TableField(
        insertStrategy = FieldStrategy.NOT_EMPTY,
        updateStrategy = FieldStrategy.NOT_EMPTY,
        whereStrategy = FieldStrategy.NOT_EMPTY
    )
    override var password: String? = null

    /**
     * 帐号状态（0正常 1停用）
     */
    override var status: String? = null

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @field:TableLogic(value = LogicDeleteMixin.NORMAL, delval = LogicDeleteMixin.DELETED)
    override var deleted: String? = LogicDeleteMixin.NORMAL

    /**
     * 最后登录IP
     */
    var loginIp: String? = null

    /**
     * 最后登录时间
     */
    var loginDate: Date? = null

    /**
     * 备注
     */
    override var remark: String? = null


    val isSuperAdmin: Boolean
        get() = UserConstants.SUPER_ADMIN_ID == this.userId
}
