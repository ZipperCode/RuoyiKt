package org.zipper.modules.system.controller.system

import cn.dev33.satoken.annotation.SaCheckPermission
import cn.dev33.satoken.secure.BCrypt
import cn.hutool.core.lang.tree.Tree
import cn.hutool.core.util.ArrayUtil
import cn.hutool.core.util.ObjectUtil
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.constraints.NotNull
import org.apache.commons.lang3.StringUtils
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.zipper.common.core.constant.UserConstants
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.MapStructExt.convertList
import org.zipper.framework.excel.utils.ExcelUtil.importExcel
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.framework.security.utils.LoginHelper
import org.zipper.framework.web.ext.validateRow
import org.zipper.modules.system.domain.bo.SysPostBo
import org.zipper.modules.system.domain.param.SysRoleParam
import org.zipper.modules.system.domain.param.SysUserParam
import org.zipper.modules.system.domain.param.SysDeptParam
import org.zipper.modules.system.domain.vo.*
import org.zipper.modules.system.excel.responseToExcel
import org.zipper.modules.system.listener.SysUserImportListener
import org.zipper.modules.system.service.dept.ISysDeptService
import org.zipper.modules.system.service.post.ISysPostService
import org.zipper.modules.system.service.role.ISysRoleService
import org.zipper.modules.system.service.user.ISysUserService
import org.zipper.optional.encrypt.annotation.ApiEncrypt

/**
 * 用户信息
 *
 * @author Lion Li
 */
@Validated
@RestController
@RequestMapping("/system/user")
class SysUserController(
    private val userService: ISysUserService,
    private val roleService: ISysRoleService,
    private val postService: ISysPostService,
    private val deptService: ISysDeptService,
) {
    /**
     * 获取用户列表
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/list")
    fun list(user: SysUserParam, pageQuery: PageQuery): TableDataInfo<SysUserVo> {
        return userService.selectPageUserList(user, pageQuery)
    }

    /**
     * 导出用户列表
     */
    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:user:export")
    @PostMapping("/export")
    fun export(user: SysUserParam, response: HttpServletResponse) {
        val list = userService.selectUserList(user)
        response.responseToExcel(list.convertList<SysUserExportVo>(), "用户数据")
    }

    /**
     * 导入数据
     *
     * @param file          导入文件
     * @param updateSupport 是否更新已存在数据
     */
    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @SaCheckPermission("system:user:import")
    @PostMapping(value = ["/importData"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResultBody
    fun importData(@RequestPart("file") file: MultipartFile, updateSupport: Boolean): String {
        val result = importExcel(file.inputStream, SysUserImportVo::class.java, SysUserImportListener(updateSupport))
        return result.getAnalysis()
    }

    /**
     * 获取导入模板
     */
    @PostMapping("/importTemplate")
    fun importTemplate(response: HttpServletResponse) {
        response.responseToExcel(emptyList<SysUserImportVo>(), "用户数据")
    }


    @GetMapping("/getInfo")
    @ResultBody
    fun getInfo(): UserInfoVo {
        val userInfoVo = UserInfoVo()
        val loginUser = LoginHelper.getLoginUser()
//        if (TenantHelper.isEnable() && LoginHelper.isSuperAdmin()) {
//            // 超级管理员 如果重新加载用户信息需清除动态租户
//            TenantHelper.clearDynamic()
//        }
        val user = userService.selectUserById(loginUser?.userId)
        if (ObjectUtil.isNull(user)) {
            throw ServiceException("没有权限访问用户数据!")
        }
        userInfoVo.user = user
        userInfoVo.permissions = loginUser?.menuPermission
        userInfoVo.roles = loginUser?.rolePermission
        return userInfoVo
    }

    /**
     * 根据用户编号获取详细信息
     *
     * @param userId 用户ID
     */
    @SaCheckPermission("system:user:query")
    @GetMapping(value = ["/", "/{userId}"])
    @ResultBody
    fun getInfo(@PathVariable(value = "userId", required = false) userId: Long?): SysUserInfoVo {
        userService.checkUserDataScope(userId)
        val userInfoVo = SysUserInfoVo()
        val roleBo = SysRoleParam()
        roleBo.status = UserConstants.ROLE_NORMAL
        val postBo = SysPostBo()
        postBo.status = UserConstants.POST_NORMAL
        val roles = roleService.selectRoleList(roleBo)
        userInfoVo.roles = if (LoginHelper.isSuperAdmin(userId)) roles else roles.filter { !it.isSuperAdmin }
        userInfoVo.posts = postService.selectPostList(postBo)
        if (ObjectUtil.isNotNull(userId)) {
            val sysUser = userService.selectUserById(userId)
            userInfoVo.user = sysUser
            userInfoVo.roleIds = sysUser?.roles?.mapNotNull { it.roleId }
            userInfoVo.postIds = postService.selectPostListByUserId(userId)
        }
        return userInfoVo
    }

    /**
     * 新增用户
     */
    @SaCheckPermission("system:user:add")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ResultBody
    fun add(@Validated @RequestBody user: SysUserParam) {
        deptService.checkDeptDataScope(user.deptId)
        if (!userService.checkUserNameUnique(user)) {
            throw ServiceException("新增用户'" + user.userName + "'失败，登录账号已存在")
        } else if (StringUtils.isNotEmpty(user.phonenumber) && !userService.checkPhoneUnique(user)) {
            throw ServiceException("新增用户'" + user.userName + "'失败，手机号码已存在")
        } else if (StringUtils.isNotEmpty(user.email) && !userService.checkEmailUnique(user)) {
            throw ServiceException("新增用户'" + user.userName + "'失败，邮箱账号已存在")
        }
//        if (TenantHelper.isEnable()) {
//            if (!userService.checkAccountBalance(TenantHelper.tenantId)) {
//                throw ServiceException("当前租户下用户名额不足，请联系管理员")
//            }
//        }
        user.password = BCrypt.hashpw(user.password)
        userService.insertUser(user).validateRow()
    }

    /**
     * 修改用户
     */
    @SaCheckPermission("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResultBody
    fun edit(@Validated @RequestBody user: SysUserParam) {
        userService.checkUserAllowed(user.userId)
        userService.checkUserDataScope(user.userId)
        deptService.checkDeptDataScope(user.deptId)
        if (!userService.checkUserNameUnique(user)) {
            throw ServiceException("修改用户'" + user.userName + "'失败，登录账号已存在")
        } else if (StringUtils.isNotEmpty(user.phonenumber) && !userService.checkPhoneUnique(user)) {
            throw ServiceException("修改用户'" + user.userName + "'失败，手机号码已存在")
        } else if (StringUtils.isNotEmpty(user.email) && !userService.checkEmailUnique(user)) {
            throw ServiceException("修改用户'" + user.userName + "'失败，邮箱账号已存在")
        }
        userService.updateUser(user).validateRow()
    }

    /**
     * 删除用户
     *
     * @param userIds 角色ID串
     */
    @SaCheckPermission("system:user:remove")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    @ResultBody
    fun remove(@PathVariable userIds: Array<Long>) {
        if (ArrayUtil.contains(userIds, LoginHelper.getUserId())) {
            throw ServiceException("当前用户不能删除")
        }
        userService.deleteUserByIds(userIds).validateRow()
    }

    /**
     * 重置密码
     */
    @ApiEncrypt
    @SaCheckPermission("system:user:resetPwd")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    @ResultBody
    fun resetPwd(@RequestBody user: SysUserParam) {
        userService.checkUserAllowed(user.userId)
        userService.checkUserDataScope(user.userId)
        user.password = BCrypt.hashpw(user.password)
        userService.resetUserPwd(user.userId, user.password).validateRow()
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    @ResultBody
    fun changeStatus(@RequestBody user: SysUserParam) {
        userService.checkUserAllowed(user.userId)
        userService.checkUserDataScope(user.userId)
        userService.updateUserStatus(user.userId, user.status).validateRow()
    }

    @SaCheckPermission("system:user:edit")
    @Log(title = "用户管理-修改分配状态", businessType = BusinessType.UPDATE)
    @PutMapping("/changeDispatchStatus")
    @ResultBody
    fun changeDispatchStatus(@RequestBody user: SysUserParam) {
        userService.checkUserAllowed(user.userId)
        userService.updateUserDispatchStatus(user.userId, user.dispatch).validateRow()
    }

    /**
     * 根据用户编号获取授权角色
     *
     * @param userId 用户ID
     */
    @SaCheckPermission("system:user:query")
    @GetMapping("/authRole/{userId}")
    @ResultBody
    fun authRole(@PathVariable userId: Long?): SysUserInfoVo {
        val user = userService.selectUserById(userId)
        val roles = roleService.selectRolesByUserId(userId)
        val userInfoVo = SysUserInfoVo()
        userInfoVo.user = user
        userInfoVo.roles = if (LoginHelper.isSuperAdmin(userId)) roles else roles.filter { !it.isSuperAdmin }
        return userInfoVo
    }

    /**
     * 用户授权角色
     *
     * @param userId  用户Id
     * @param roleIds 角色ID串
     */
    @SaCheckPermission("system:user:edit")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    @ResultBody
    fun insertAuthRole(userId: Long?, roleIds: Array<Long>) {
        userService.checkUserDataScope(userId)
        userService.insertUserAuth(userId, roleIds)
    }

    /**
     * 获取部门树列表
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/deptTree")
    @ResultBody
    fun deptTree(dept: SysDeptParam): List<Tree<Long>> {
        return deptService.selectDeptTreeList(dept)
    }

    /**
     * 获取部门下的所有用户信息
     */
    @SaCheckPermission("system:user:list")
    @GetMapping("/list/dept/{deptId}")
    @ResultBody
    fun listByDept(@PathVariable deptId: @NotNull Long?): List<SysUserVo> {
        return userService.selectUserListByDept(deptId)
    }
}
