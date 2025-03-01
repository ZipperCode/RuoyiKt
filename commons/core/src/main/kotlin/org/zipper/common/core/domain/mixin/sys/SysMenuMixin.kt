package org.zipper.common.core.domain.mixin.sys

interface SysMenuMixin {

    /**
     * 菜单ID
     */
    var menuId: Long?

    /**
     * 父菜单ID
     */
    var parentId: Long?

    /**
     * 菜单名称
     */
    var menuName: String?

    /**
     * 显示顺序
     */
    var orderNum: Int?

    /**
     * 路由地址
     */
    var path: String?

    /**
     * 组件路径
     */
    var component: String?

    /**
     * 路由参数
     */
    var queryParam: String?

    /**
     * 是否为外链（0是 1否）
     */
    var isFrame: String?

    fun setIsFrame(isFrame: String?) {
        this.isFrame = isFrame
    }

    fun getIsFrame(): String? = this.isFrame

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    var isCache: String?

    /**
     * 类型（M目录 C菜单 F按钮）
     */
    var menuType: String?

    /**
     * 显示状态（0显示 1隐藏）
     */
    var visible: String?

    /**
     * 菜单状态（0正常 1停用）
     */
    var status: String?

    /**
     * 权限字符串
     */
    var perms: String?

    /**
     * 菜单图标
     */
    var icon: String?

    /**
     * 备注
     */
    var remark: String?
}