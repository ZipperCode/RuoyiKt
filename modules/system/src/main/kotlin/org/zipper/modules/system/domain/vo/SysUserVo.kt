package org.zipper.modules.system.domain.vo

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import io.github.linpeilie.annotations.AutoMapper
import org.zipper.common.sensitive.annotation.Sensitive
import org.zipper.common.sensitive.core.SensitiveStrategy
import org.zipper.modules.system.domain.entity.SysUserEntity
import org.zipper.modules.system.domain.mixin.SysUserMixin
import org.zipper.optional.translation.annotation.Translation
import org.zipper.optional.translation.constant.TransConstant
import java.io.Serializable
import java.util.*

/**
 * 用户信息视图对象 sys_user
 *
 * @author Michelle.Chung
 */
@AutoMapper(target = SysUserEntity::class)
class SysUserVo : SysUserMixin, Serializable {
    /**
     * 用户ID
     */
    override var userId: Long? = 0

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
    override var userType: String? = ""

    /**
     * 用户邮箱
     */
    @field:Sensitive(strategy = SensitiveStrategy.EMAIL)
    override var email: String? = null

    /**
     * 手机号码
     */
    @field:Sensitive(strategy = SensitiveStrategy.PHONE)
    override var phonenumber: String? = null

    /**
     * 用户性别（0男 1女 2未知）
     */
    override var sex: String? = null

    /**
     * 头像地址
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL)
    var avatar: Long? = null

    /**
     * 密码
     */
    @JsonIgnore
    @JsonProperty
    override var password: String? = null

    /**
     * 帐号状态（0正常 1停用）
     */
    override var status: String? = null

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

    /**
     * 创建时间
     */
    var createTime: Date? = null

    /**
     * 部门对象
     */
    var dept: SysDeptVo? = null

    /**
     * 角色对象
     */
    var roles: List<SysRoleVo>? = null

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
}
