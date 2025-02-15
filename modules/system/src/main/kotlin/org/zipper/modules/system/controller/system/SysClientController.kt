package org.zipper.modules.system.controller.system

import cn.dev33.satoken.annotation.SaCheckPermission
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.validate.AddGroup
import org.zipper.common.core.validate.EditGroup
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.framework.web.ext.validate
import org.zipper.modules.system.domain.param.SysClientQueryParam
import org.zipper.modules.system.domain.param.SysClientSaveParam
import org.zipper.modules.system.domain.vo.SysClientVo
import org.zipper.modules.system.excel.responseToExcel
import org.zipper.modules.system.service.client.ISysClientService
import org.zipper.optional.idempotent.annotation.RepeatSubmit

/**
 * 客户端管理
 *
 * @author Michelle.Chung
 * @date 2023-06-18
 */
@Validated
@RestController
@RequestMapping("/system/client")
class SysClientController(
    private val sysClientService: ISysClientService
) {
    /**
     * 查询客户端管理列表
     */
    @SaCheckPermission("system:client:list")
    @GetMapping("/list")
    fun list(param: SysClientQueryParam, pageQuery: PageQuery): TableDataInfo<SysClientVo> {
        return sysClientService.queryPageList(param, pageQuery)
    }

    /**
     * 导出客户端管理列表
     */
    @SaCheckPermission("system:client:export")
    @Log(title = "客户端管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    fun export(response: HttpServletResponse) {
        val list = sysClientService.queryList()
        response.responseToExcel(list, "客户端管理")
    }

    /**
     * 获取客户端管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:client:query")
    @GetMapping("/{id}")
    @ResultBody
    fun getInfo(@PathVariable id: @NotNull(message = "主键不能为空") Long?): SysClientVo? {
        return sysClientService.queryById(id)
    }

    /**
     * 新增客户端管理
     */
    @SaCheckPermission("system:client:add")
    @Log(title = "客户端管理", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping
    fun add(@Validated(AddGroup::class) @RequestBody param: SysClientSaveParam) {
        sysClientService.insert(param).validate()
    }

    /**
     * 修改客户端管理
     */
    @SaCheckPermission("system:client:edit")
    @Log(title = "客户端管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping
    @ResultBody
    fun edit(@Validated(EditGroup::class) @RequestBody param: SysClientSaveParam) {
        sysClientService.update(param)
    }

    /**
     * 状态修改
     */
    @SaCheckPermission("system:client:edit")
    @Log(title = "客户端管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    @ResultBody
    fun changeStatus(@RequestBody bo: SysClientSaveParam) {
        sysClientService.updateUserStatus(bo.id!!, bo.status!!)
    }

    /**
     * 删除客户端管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:client:remove")
    @Log(title = "客户端管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ResultBody
    fun remove(@PathVariable ids: Array<Long>) {
        sysClientService.deleteWithValidByIds(listOf(*ids), true).validate()
    }
}
