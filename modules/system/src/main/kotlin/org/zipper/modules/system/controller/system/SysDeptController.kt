package org.zipper.modules.system.controller.system

import cn.dev33.satoken.annotation.SaCheckPermission
import cn.hutool.core.convert.Convert
import org.apache.commons.lang3.StringUtils
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.constant.UserConstants
import org.zipper.common.core.exception.ServiceException
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.framework.web.ext.validateRow
import org.zipper.modules.system.domain.param.SysDeptParam
import org.zipper.modules.system.domain.vo.SysDeptVo
import org.zipper.modules.system.service.dept.ISysDeptService

/**
 * 部门信息
 *
 * @author Lion Li
 */
@Validated
@RestController
@RequestMapping("/system/dept")
class SysDeptController(private val deptService: ISysDeptService) {
    /**
     * 获取部门列表
     */
    @SaCheckPermission("system:dept:list")
    @GetMapping("/list")
    @ResultBody
    fun list(dept: SysDeptParam): List<SysDeptVo> {
        return deptService.selectDeptList(dept)
    }

    /**
     * 查询部门列表（排除节点）
     *
     * @param deptId 部门ID
     */
    @SaCheckPermission("system:dept:list")
    @GetMapping("/list/exclude/{deptId}")
    @ResultBody
    fun excludeChild(@PathVariable(value = "deptId", required = false) deptId: Long): List<SysDeptVo> {
        val depts = deptService.selectDeptList(SysDeptParam())
        return depts.toMutableSet().apply {
            removeIf {
                deptId == it.deptId || StringUtils.contains(it.ancestors, Convert.toStr(deptId))
            }
        }.toList()
    }

    /**
     * 根据部门编号获取详细信息
     *
     * @param deptId 部门ID
     */
    @SaCheckPermission("system:dept:query")
    @GetMapping(value = ["/{deptId}"])
    fun getInfo(@PathVariable deptId: Long?): SysDeptVo? {
        deptService.checkDeptDataScope(deptId)
        return deptService.selectDeptById(deptId)
    }

    /**
     * 新增部门
     */
    @SaCheckPermission("system:dept:add")
    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ResultBody
    fun add(@Validated @RequestBody dept: SysDeptParam) {
        if (!deptService.checkDeptNameUnique(dept)) {
            throw ServiceException("新增部门'" + dept.deptName + "'失败，部门名称已存在")
        }
        deptService.insertDept(dept).validateRow()
    }

    /**
     * 修改部门
     */
    @SaCheckPermission("system:dept:edit")
    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResultBody
    fun edit(@Validated @RequestBody dept: SysDeptParam) {
        val deptId = dept.deptId
        deptService.checkDeptDataScope(deptId)
        if (!deptService.checkDeptNameUnique(dept)) {
            throw ServiceException("修改部门'" + dept.deptName + "'失败，部门名称已存在")
        } else if (dept.parentId == deptId) {
            throw ServiceException("修改部门'" + dept.deptName + "'失败，上级部门不能是自己")
        } else if (StringUtils.equals(UserConstants.DEPT_DISABLE, dept.status)) {
            if (deptService.selectNormalChildrenDeptById(deptId) > 0) {
                throw ServiceException("该部门包含未停用的子部门!")
            } else if (deptService.checkDeptExistUser(deptId)) {
                throw ServiceException("该部门下存在已分配用户，不能禁用!")
            }
        }
        deptService.updateDept(dept).validateRow()
    }

    /**
     * 删除部门
     *
     * @param deptId 部门ID
     */
    @SaCheckPermission("system:dept:remove")
    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deptId}")
    fun remove(@PathVariable deptId: Long) {
        if (deptService.hasChildByDeptId(deptId)) {
            throw ServiceException("存在下级部门,不允许删除")
        }
        if (deptService.checkDeptExistUser(deptId)) {
            throw ServiceException("部门存在用户,不允许删除")
        }
        deptService.checkDeptDataScope(deptId)
        deptService.deleteDeptById(deptId)
    }
}
