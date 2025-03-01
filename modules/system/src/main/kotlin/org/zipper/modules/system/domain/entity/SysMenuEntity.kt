package org.zipper.modules.system.domain.entity

import com.baomidou.mybatisplus.annotation.TableId
import com.baomidou.mybatisplus.annotation.TableName
import org.zipper.framework.mybatis.core.domain.BaseMixinEntity
import org.zipper.common.core.domain.mixin.sys.SysMenuMixin

/**
 * 菜单权限表 sys_menu
 *
 */
@TableName("sys_menu")
open class SysMenuEntity : BaseMixinEntity(), SysMenuMixin {
    /**
     * 菜单ID
     */
    @field:TableId(value = "menu_id")
    override var menuId: Long? = null

    /**
     * 父菜单ID
     */
    override var parentId: Long? = null

    /**
     * 菜单名称
     */
    override var menuName: String? = null

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
     * 类型（M目录 C菜单 F按钮）
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
     * 权限字符串
     */
    override var perms: String? = null

    /**
     * 菜单图标
     */
    override var icon: String? = null

    /**
     * 备注
     */
    override var remark: String? = null
}
