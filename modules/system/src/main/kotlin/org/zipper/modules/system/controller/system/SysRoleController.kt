package org.zipper.modules.system.controller.system

import cn.dev33.satoken.annotation.SaCheckPermission
import jakarta.servlet.http.HttpServletResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.exception.ServiceException
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.framework.web.ext.validateRow
import org.zipper.modules.system.domain.param.SysDeptParam
import org.zipper.modules.system.domain.bo.SysRoleBo
import org.zipper.modules.system.domain.bo.SysUserBo
import org.zipper.modules.system.domain.entity.SysUserRoleEntity
import org.zipper.modules.system.domain.vo.DeptTreeSelectVo
import org.zipper.modules.system.domain.vo.SysRoleVo
import org.zipper.modules.system.domain.vo.SysUserVo
import org.zipper.modules.system.excel.responseToExcel
import org.zipper.modules.system.service.dept.ISysDeptService
import org.zipper.modules.system.service.role.ISysRoleService
import org.zipper.modules.system.service.user.ISysUserService

/**
 * 角色信息
 *
 * @author Lion Li
 */
@Validated
@RestController
@RequestMapping("/system/role")
class SysRoleController(
    private val roleService: ISysRoleService,
    private val userService: ISysUserService,
    private val deptService: ISysDeptService
) {
    /**
     * 获取角色信息列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/list")
    fun list(role: SysRoleBo, pageQuery: PageQuery): TableDataInfo<SysRoleVo> {
        return roleService.selectPageRoleList(role, pageQuery)
    }

    /**
     * 导出角色信息列表
     */
    @Log(title = "角色管理", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:role:export")
    @PostMapping("/export")
    fun export(role: SysRoleBo, response: HttpServletResponse) {
        val list = roleService.selectRoleList(role)
        response.responseToExcel(list, "角色数据")
    }

    /**
     * 根据角色编号获取详细信息
     *
     * @param roleId 角色ID
     */
    @SaCheckPermission("system:role:query")
    @GetMapping(value = ["/{roleId}"])
    @ResultBody
    fun getInfo(@PathVariable roleId: Long?): SysRoleVo? {
        roleService.checkRoleDataScope(roleId ?: 0)
        return roleService.selectRoleById(roleId)
    }

    /**
     * 新增角色
     */
    @SaCheckPermission("system:role:add")
    @Log(title = "角色管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ResultBody
    fun add(@Validated @RequestBody role: SysRoleBo) {
        roleService.checkRoleAllowed(role)
        if (!roleService.checkRoleNameUnique(role)) {
            throw ServiceException("新增角色'" + role.roleName + "'失败，角色名称已存在")
        } else if (!roleService.checkRoleKeyUnique(role)) {
            throw ServiceException("新增角色'" + role.roleName + "'失败，角色权限已存在")
        }
        roleService.insertRole(role).validateRow()
    }

    /**
     * 修改保存角色
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResultBody
    fun edit(@Validated @RequestBody role: SysRoleBo) {
        roleService.checkRoleAllowed(role)
        roleService.checkRoleDataScope(role.roleId ?: 0)
        if (!roleService.checkRoleNameUnique(role)) {
            throw ServiceException("修改角色'" + role.roleName + "'失败，角色名称已存在")
        } else if (!roleService.checkRoleKeyUnique(role)) {
            throw ServiceException("修改角色'" + role.roleName + "'失败，角色权限已存在")
        }

        if (roleService.updateRole(role) > 0) {
            roleService.cleanOnlineUserByRole(role.roleId)
            return
        }
        throw ServiceException("修改角色'" + role.roleName + "'失败，请联系管理员")
    }

    /**
     * 修改保存数据权限
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/dataScope")
    @ResultBody
    fun dataScope(@RequestBody role: SysRoleBo) {
        roleService.checkRoleAllowed(role)
        roleService.checkRoleDataScope(role.roleId ?: 0)
        roleService.authDataScope(role).validateRow()
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    @ResultBody
    fun changeStatus(@RequestBody role: SysRoleBo) {
        roleService.checkRoleAllowed(role)
        roleService.checkRoleDataScope(role.roleId ?: 0)
        roleService.updateRoleStatus(role.roleId, role.status).validateRow()
    }

    /**
     * 删除角色
     *
     * @param roleIds 角色ID串
     */
    @SaCheckPermission("system:role:remove")
    @Log(title = "角色管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{roleIds}")
    @ResultBody
    fun remove(@PathVariable roleIds: Array<Long>) {
        roleService.deleteRoleByIds(roleIds).validateRow()
    }

    /**
     * 获取角色选择框列表
     */
    @SaCheckPermission("system:role:query")
    @GetMapping("/optionselect")
    fun optionselect(): List<SysRoleVo> {
        return roleService.selectRoleAll()
    }

    /**
     * 查询已分配用户角色列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/authUser/allocatedList")
    fun allocatedList(user: SysUserBo, pageQuery: PageQuery): TableDataInfo<SysUserVo> {
        return userService.selectAllocatedList(user, pageQuery)
    }

    /**
     * 查询未分配用户角色列表
     */
    @SaCheckPermission("system:role:list")
    @GetMapping("/authUser/unallocatedList")
    fun unallocatedList(user: SysUserBo, pageQuery: PageQuery): TableDataInfo<SysUserVo> {
        return userService.selectUnallocatedList(user, pageQuery)
    }

    /**
     * 取消授权用户
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancel")
    @ResultBody
    fun cancelAuthUser(@RequestBody userRole: SysUserRoleEntity) {
        roleService.deleteAuthUser(userRole).validateRow()
    }

    /**
     * 批量取消授权用户
     *
     * @param roleId  角色ID
     * @param userIds 用户ID串
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/cancelAll")
    @ResultBody
    fun cancelAuthUserAll(roleId: Long?, userIds: Array<Long>) {
        roleService.deleteAuthUsers(roleId, userIds).validateRow()
    }

    /**
     * 批量选择用户授权
     *
     * @param roleId  角色ID
     * @param userIds 用户ID串
     */
    @SaCheckPermission("system:role:edit")
    @Log(title = "角色管理", businessType = BusinessType.GRANT)
    @PutMapping("/authUser/selectAll")
    @ResultBody
    fun selectAuthUserAll(roleId: Long?, userIds: Array<Long>) {
        roleService.checkRoleDataScope(roleId)
        roleService.insertAuthUsers(roleId, userIds).validateRow()
    }

    /**
     * 获取对应角色部门树列表
     *
     * @param roleId 角色ID
     */
    @SaCheckPermission("system:role:list")
    @GetMapping(value = ["/deptTree/{roleId}"])
    @ResultBody
    fun roleDeptTreeselect(@PathVariable("roleId") roleId: Long?): DeptTreeSelectVo {
        val selectVo = DeptTreeSelectVo()
        selectVo.checkedKeys = deptService.selectDeptListByRoleId(roleId)
        selectVo.depts = deptService.selectDeptTreeList(SysDeptParam())
        return selectVo
    }
}
