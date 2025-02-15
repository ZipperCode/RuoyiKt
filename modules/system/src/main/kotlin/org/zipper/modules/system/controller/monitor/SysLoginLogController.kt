package org.zipper.modules.system.controller.monitor

import cn.dev33.satoken.annotation.SaCheckPermission
import jakarta.servlet.http.HttpServletResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.constant.GlobalConstants
import org.zipper.framework.excel.utils.ExcelUtil.writeToExcel
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.redis.utils.RedisUtils
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.framework.web.ext.validateRow
import org.zipper.modules.system.domain.bo.SysLoginLogBo
import org.zipper.modules.system.domain.vo.SysLoginLogVo
import org.zipper.modules.system.excel.ExcelDownHandler
import org.zipper.modules.system.excel.responseToExcel
import org.zipper.modules.system.service.log.ISysLoginLogService

/**
 * 系统访问记录
 *
 * @author Lion Li
 */
@Validated
@RestController
@RequestMapping("/monitor/logininfor")
class SysLoginLogController(
    private val loginLogService: ISysLoginLogService
) {
    /**
     * 获取系统访问记录列表
     */
    @SaCheckPermission("monitor:logininfor:list")
    @GetMapping("/list")
    fun list(loginLogBo: SysLoginLogBo, pageQuery: PageQuery): TableDataInfo<SysLoginLogVo> {
        return loginLogService.selectPageLoginLogList(loginLogBo, pageQuery)
    }

    /**
     * 导出系统访问记录列表
     */
    @Log(title = "登录日志", businessType = BusinessType.EXPORT)
    @SaCheckPermission("monitor:logininfor:export")
    @PostMapping("/export")
    fun export(logininfor: SysLoginLogBo, response: HttpServletResponse) {
        val list = loginLogService.selectLoginLogList(logininfor)
        response.responseToExcel(list, "登录日志")
    }

    /**
     * 批量删除登录日志
     * @param infoIds 日志ids
     */
    @SaCheckPermission("monitor:logininfor:remove")
    @Log(title = "登录日志", businessType = BusinessType.DELETE)
    @DeleteMapping("/{infoIds}")
    @ResultBody
    fun remove(@PathVariable infoIds: Array<Long>) {
        loginLogService.deleteLoginLogByIds(infoIds).validateRow()
    }

    /**
     * 清理系统访问记录
     */
    @SaCheckPermission("monitor:logininfor:remove")
    @Log(title = "登录日志", businessType = BusinessType.CLEAN)
    @DeleteMapping("/clean")
    @ResultBody
    fun clean() {
        loginLogService.cleanLoginLog()
    }

    @SaCheckPermission("monitor:logininfor:unlock")
    @Log(title = "账户解锁", businessType = BusinessType.OTHER)
    @GetMapping("/unlock/{userName}")
    @ResultBody
    fun unlock(@PathVariable("userName") userName: String) {
        val loginName: String = GlobalConstants.PWD_ERR_CNT_KEY + userName
        if (RedisUtils.hasKey(loginName)) {
            RedisUtils.deleteObject(loginName)
        }
    }
}
