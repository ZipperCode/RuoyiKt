package org.zipper.modules.system.domain.vo

/**
 * 登录用户信息
 *
 * @author Michelle.Chung
 */
class UserInfoVo {
    /**
     * 用户基本信息
     */
    var user: SysUserVo? = null

    /**
     * 菜单权限
     */
    var permissions: Set<String>? = null

    /**
     * 角色权限
     */
    var roles: Set<String>? = null
}
