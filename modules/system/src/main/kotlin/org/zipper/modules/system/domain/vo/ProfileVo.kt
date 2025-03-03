package org.zipper.modules.system.domain.vo

/**
 * 用户个人信息
 *
 * @author Michelle.Chung
 */
class ProfileVo {
    /**
     * 用户信息
     */
    var user: SysUserVo? = null

    /**
     * 用户所属角色组
     */
    var roleGroup: String? = null

    /**
     * 用户所属岗位组
     */
    var postGroup: String? = null
}
