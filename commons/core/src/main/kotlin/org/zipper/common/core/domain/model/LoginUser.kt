package org.zipper.common.core.domain.model

import java.io.Serializable

/**
 * 登录用户身份权限
 *
 * @author Lion Li
 */
open class LoginUser : Serializable {
    /**
     * 租户ID
     */
    var tenantId: String? = null

    /**
     * 用户ID
     */
    var userId: Long? = 0

    /**
     * 部门ID
     */
    var deptId: Long? = null

    /**
     * 部门名
     */
    var deptName: String? = null

    /**
     * 用户唯一标识
     */
    var token: String? = null

    /**
     * 用户类型
     */
    var userType: String? = ""

    /**
     * 登录时间
     */
    var loginTime: Long? = null

    /**
     * 过期时间
     */
    var expireTime: Long? = null

    /**
     * 登录IP地址
     */
    var ipaddr: String? = null

    /**
     * 登录地点
     */
    var loginLocation: String? = null

    /**
     * 浏览器类型
     */
    var browser: String? = null

    /**
     * 操作系统
     */
    var os: String? = null

    /**
     * 菜单权限
     */
    var menuPermission: Set<String> = emptySet()

    /**
     * 角色权限
     */
    var rolePermission: Set<String> = emptySet()

    /**
     * 用户名
     */
    var username: String? = null

    /**
     * 用户昵称
     */
    var nickname: String? = null

    /**
     * 角色对象
     */
    var roles: List<LoginUserRole> = emptyList()

    /**
     * 数据权限 当前角色ID
     */
    var roleId: Long? = null

    /**
     * 客户端
     */
    var clientKey: String? = null

    /**
     * 设备类型
     */
    var deviceType: String? = null

    /**
     * 获取登录id
     */
    val loginId: String
        get() {
            requireNotNull(userType) { "用户类型不能为空" }
            requireNotNull(userId) { "用户ID不能为空" }
            return "$userType:$userId"
        }
}
