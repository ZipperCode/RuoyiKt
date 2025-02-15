package org.zipper.modules.system.controller.monitor

import cn.dev33.satoken.annotation.SaCheckPermission
import jakarta.servlet.http.HttpServletResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.framework.web.ext.validateRow
import org.zipper.modules.system.domain.bo.SysOperateLogBo
import org.zipper.modules.system.domain.vo.SysOperateLogVo
import org.zipper.modules.system.excel.responseToExcel
import org.zipper.modules.system.service.log.ISysOperLogService

/**
 * 操作日志记录
 *
 * @author Lion Li
 */
@Validated
@RestController
@RequestMapping("/monitor/operlog")
class SysOperlogController(private val operLogService: ISysOperLogService) {
    /**
     * 获取操作日志记录列表
     */
    @SaCheckPermission("monitor:operlog:list")
    @GetMapping("/list")
    fun list(operLog: SysOperateLogBo, pageQuery: PageQuery): TableDataInfo<SysOperateLogVo> {
        return operLogService.selectPageOperLogList(operLog, pageQuery)
    }

    /**
     * 导出操作日志记录列表
     */
    @Log(title = "操作日志", businessType = BusinessType.EXPORT)
    @SaCheckPermission("monitor:operlog:export")
    @PostMapping("/export")
    fun export(operLog: SysOperateLogBo, response: HttpServletResponse) {
        val list = operLogService.selectOperLogList(operLog)
        response.responseToExcel(list, "操作日志")
    }

    /**
     * 批量删除操作日志记录
     * @param operIds 日志ids
     */
    @Log(title = "操作日志", businessType = BusinessType.DELETE)
    @SaCheckPermission("monitor:operlog:remove")
    @DeleteMapping("/{operIds}")
    @ResultBody
    fun remove(@PathVariable operIds: Array<Long>) {
        operLogService.deleteOperLogByIds(operIds).validateRow()
    }

    /**
     * 清理操作日志记录
     */
    @Log(title = "操作日志", businessType = BusinessType.CLEAN)
    @SaCheckPermission("monitor:operlog:remove")
    @DeleteMapping("/clean")
    fun clean() {
        operLogService.cleanOperLog()
    }
}
