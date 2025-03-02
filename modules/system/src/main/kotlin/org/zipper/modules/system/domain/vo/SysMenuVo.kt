package org.zipper.modules.system.domain.vo

import io.github.linpeilie.annotations.AutoMapper
import org.apache.commons.lang3.StringUtils
import org.zipper.common.core.constant.Constants
import org.zipper.common.core.constant.UserConstants
import org.zipper.common.core.ext.isHttpUrl
import org.zipper.modules.system.domain.entity.SysMenuEntity
import org.zipper.common.core.domain.mixin.sys.SysMenuMixin
import java.io.Serializable
import java.util.*

/**
 * 菜单权限视图对象 sys_menu
 *
 * @author Michelle.Chung
 */
@AutoMapper(target = SysMenuEntity::class)
class SysMenuVo : SysMenuMixin, Serializable {
    /**
     * 菜单ID
     */
    override var menuId: Long? = null

    /**
     * 菜单名称
     */
    override var menuName: String? = null

    /**
     * 父菜单ID
     */
    override var parentId: Long? = null

    /**
     * 显示顺序
     */
    override var orderNum: Int? = null

    /**
     * 路由地址
     */
    override var path: String? = null

    /**
     * 组件路径
     */
    override var component: String? = null

    /**
     * 路由参数
     */
    override var queryParam: String? = null

    /**
     * 是否为外链（0是 1否）
     */
    override var isFrame: String? = null

    /**
     * 是否缓存（0缓存 1不缓存）
     */
    override var isCache: String? = null

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */
    override var menuType: String? = null

    /**
     * 显示状态（0显示 1隐藏）
     */
    override var visible: String? = null

    /**
     * 菜单状态（0正常 1停用）
     */
    override var status: String? = null

    /**
     * 权限标识
     */
    override var perms: String? = null

    /**
     * 菜单图标
     */
    override var icon: String? = null

    /**
     * 创建部门
     */
    var createDept: Long? = null

    /**
     * 备注
     */
    override var remark: String? = null

    /**
     * 创建时间
     */
    var createTime: Date? = null

    /**
     * 子菜单
     */
    var children: List<SysMenuVo> = ArrayList()


    /**
     * 获取路由名称
     */
    fun getRouteName(parentName: String? = null): String {
        var contactPath = ""
        if (parentName != null) {
            contactPath = parentName
            if (contactPath.first() == '/') {
                contactPath = contactPath.substring(1)
            }
            if (contactPath.last() != '/') {
                contactPath += "/"
            }
        }
        var routerName: String = StringUtils.capitalize(contactPath + path)
        // 非外链并且是一级目录（类型为目录）
        if (isMenuFrame()) {
            routerName = StringUtils.EMPTY
        }
        return routerName
    }

    /**
     * 获取路由地址
     */
    fun getRouterPath(): String? {
        var routerPath = this.path
        // 内链打开外网方式
        if (parentId != 0L && isInnerLink()) {
            routerPath = innerLinkReplaceEach(routerPath)
        }
        // 非外链并且是一级目录（类型为目录）
        if (0L == parentId && UserConstants.TYPE_DIR == menuType && UserConstants.NO_FRAME == isFrame) {
            routerPath = "/" + this.path
        } else if (isMenuFrame()) {
            routerPath = "/"
        }
        return routerPath
    }

    /**
     * 获取组件信息
     */
    fun getComponentInfo(): String? {
        var component: String? = UserConstants.LAYOUT
        if (StringUtils.isNotEmpty(this.component) && !isMenuFrame()) {
            component = this.component
        } else if (StringUtils.isEmpty(this.component) && parentId != 0L && isInnerLink()) {
            component = UserConstants.INNER_LINK
        } else if (StringUtils.isEmpty(this.component) && isParentView()) {
            component = UserConstants.PARENT_VIEW
        }
        return component
    }


    /**
     * 是否为菜单内部跳转
     */
    fun isMenuFrame(): Boolean {
        return parentId == 0L && UserConstants.TYPE_MENU == menuType && isFrame == UserConstants.NO_FRAME
    }

    /**
     * 是否为内链组件
     */
    fun isInnerLink(): Boolean {
        return isFrame == UserConstants.NO_FRAME && path.isHttpUrl()
    }

    /**
     * 是否为parent_view组件
     */
    fun isParentView(): Boolean {
        return parentId != 0L && UserConstants.TYPE_DIR == menuType
    }


    companion object {
        /**
         * 内链域名特殊字符替换
         */
        fun innerLinkReplaceEach(path: String?): String {
            return StringUtils.replaceEach(
                path,
                arrayOf(Constants.HTTP, Constants.HTTPS, Constants.WWW, ".", ":"),
                arrayOf("", "", "", "/", "/")
            )
        }
    }
}
