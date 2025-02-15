package org.zipper.modules.system.controller.system

import cn.dev33.satoken.annotation.SaCheckPermission
import cn.hutool.core.lang.tree.Tree
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.constant.UserConstants
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.isHttpUrl
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.framework.security.utils.LoginHelper
import org.zipper.framework.web.ext.validateRow
import org.zipper.modules.system.domain.param.SysMenuParam
import org.zipper.modules.system.domain.vo.MenuTreeSelectVo
import org.zipper.modules.system.domain.vo.RouterVo
import org.zipper.modules.system.domain.vo.SysMenuVo
import org.zipper.modules.system.service.menu.ISysMenuService

/**
 * 菜单信息
 *
 * @author Lion Li
 */
@Validated
@RestController
@RequestMapping("/system/menu")
class SysMenuController(
    private val menuService: ISysMenuService
) {

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("/getRouters")
    @ResultBody
    fun getRouters(): List<RouterVo> {
        val menus = menuService.selectMenuTreeByUserId(LoginHelper.getUserId())
        return menuService.buildMenus(menus)
    }


    /**
     * 获取菜单列表
     */
//    @SaCheckRole(
//        value = [
//            TenantConstants.SUPER_ADMIN_ROLE_KEY, TenantConstants.TENANT_ADMIN_ROLE_KEY
//        ], mode = SaMode.OR
//    )
    @SaCheckPermission("system:menu:list")
    @GetMapping("/list")
    @ResultBody
    fun list(menu: SysMenuParam): List<SysMenuVo> {
        return menuService.selectMenuList(menu, LoginHelper.getUserId())
    }

    /**
     * 根据菜单编号获取详细信息
     *
     * @param menuId 菜单ID
     */
//    @SaCheckRole(
//        value = [TenantConstants.SUPER_ADMIN_ROLE_KEY, TenantConstants.TENANT_ADMIN_ROLE_KEY
//        ], mode = SaMode.OR
//    )
    @SaCheckPermission("system:menu:query")
    @GetMapping(value = ["/{menuId}"])
    @ResultBody
    fun getInfo(@PathVariable menuId: Long?): SysMenuVo? {
        return menuService.selectMenuById(menuId)
    }

    /**
     * 获取菜单下拉树列表
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping("/treeselect")
    @ResultBody
    fun treeselect(menu: SysMenuParam): List<Tree<Long>> {
        val menus = menuService.selectMenuList(menu, LoginHelper.getUserId())
        return menuService.buildMenuTreeSelect(menus)
    }

    /**
     * 加载对应角色菜单列表树
     *
     * @param roleId 角色ID
     */
    @SaCheckPermission("system:menu:query")
    @GetMapping(value = ["/roleMenuTreeselect/{roleId}"])
    @ResultBody
    fun roleMenuTreeselect(@PathVariable("roleId") roleId: Long?): MenuTreeSelectVo {
        val menus = menuService.selectMenuList(LoginHelper.getUserId())
        val selectVo = MenuTreeSelectVo()
        selectVo.checkedKeys = menuService.selectMenuListByRoleId(roleId)
        selectVo.menus = menuService.buildMenuTreeSelect(menus)
        return selectVo
    }

    /**
     * 加载对应租户套餐菜单列表树
     *
     * @param packageId 租户套餐ID
     */
//    @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
    @SaCheckPermission("system:menu:query")
    @GetMapping(value = ["/tenantPackageMenuTreeselect/{packageId}"])
    @ResultBody
    fun tenantPackageMenuTreeselect(@PathVariable("packageId") packageId: Long): MenuTreeSelectVo {
        val menus = menuService.selectMenuList(LoginHelper.getUserId())
        val selectVo = MenuTreeSelectVo()
        selectVo.checkedKeys = menuService.selectMenuListByPackageId(packageId)
        selectVo.menus = menuService.buildMenuTreeSelect(menus)
        return selectVo
    }

    /**
     * 新增菜单
     */
//    @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
    @SaCheckPermission("system:menu:add")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ResultBody
    fun add(@Validated @RequestBody menu: SysMenuParam) {
        if (!menuService.checkMenuNameUnique(menu)) {
            throw ServiceException("新增菜单'" + menu.menuName + "'失败，菜单名称已存在")
        } else if (UserConstants.YES_FRAME == menu.isFrame && !menu.path.isHttpUrl()) {
            throw ServiceException("新增菜单'" + menu.menuName + "'失败，地址必须以http(s)://开头")
        }
        menuService.insertMenu(menu).validateRow()
    }

    /**
     * 修改菜单
     */
//    @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
    @SaCheckPermission("system:menu:edit")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResultBody
    fun edit(@Validated @RequestBody menu: SysMenuParam) {
        if (!menuService.checkMenuNameUnique(menu)) {
            throw ServiceException("修改菜单'" + menu.menuName + "'失败，菜单名称已存在")
        } else if (UserConstants.YES_FRAME == menu.isFrame && !menu.path.isHttpUrl()) {
            throw ServiceException("修改菜单'" + menu.menuName + "'失败，地址必须以http(s)://开头")
        } else if (menu.menuId == menu.parentId) {
            throw ServiceException("修改菜单'" + menu.menuName + "'失败，上级菜单不能选择自己")
        }
        menuService.updateMenu(menu).validateRow()
    }

    /**
     * 删除菜单
     *
     * @param menuId 菜单ID
     */
//    @SaCheckRole(TenantConstants.SUPER_ADMIN_ROLE_KEY)
    @SaCheckPermission("system:menu:remove")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    @ResultBody
    fun remove(@PathVariable("menuId") menuId: Long?) {
        if (menuService.hasChildByMenuId(menuId)) {
            throw ServiceException("存在子菜单,不允许删除")
        }
        if (menuService.checkMenuExistRole(menuId)) {
            throw ServiceException("菜单已分配,不允许删除")
        }
        menuService.deleteMenuById(menuId)
    }
}
