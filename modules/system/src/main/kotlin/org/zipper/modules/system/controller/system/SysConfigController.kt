package org.zipper.modules.system.controller.system

import cn.dev33.satoken.annotation.SaCheckPermission
import jakarta.servlet.http.HttpServletResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.domain.R
import org.zipper.common.core.exception.ServiceException
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.modules.system.domain.param.SysConfigParam
import org.zipper.modules.system.domain.vo.SysConfigVo
import org.zipper.modules.system.excel.responseToExcel
import org.zipper.modules.system.service.config.ISysConfigService

/**
 * 参数配置 信息操作处理
 *
 * @author Lion Li
 */
@Validated
@RestController
@RequestMapping("/system/config")
class SysConfigController(private val configService: ISysConfigService) {
    /**
     * 获取参数配置列表
     */
    @SaCheckPermission("system:config:list")
    @GetMapping("/list")
    fun list(config: SysConfigParam, pageQuery: PageQuery): TableDataInfo<SysConfigVo> {
        return configService.selectPageConfigList(config, pageQuery)
    }

    /**
     * 导出参数配置列表
     */
    @Log(title = "参数管理", businessType = BusinessType.EXPORT)
    @SaCheckPermission("system:config:export")
    @PostMapping("/export")
    fun export(config: SysConfigParam, response: HttpServletResponse) {
        val list = configService.selectConfigList(config)
        response.responseToExcel(list, "参数数据")
    }

    /**
     * 根据参数编号获取详细信息
     *
     * @param configId 参数ID
     */
    @SaCheckPermission("system:config:query")
    @GetMapping(value = ["/{configId}"])
    @ResultBody
    fun getInfo(@PathVariable configId: Long?): SysConfigVo? {
        return configService.selectConfigById(configId)
    }

    /**
     * 根据参数键名查询参数值
     *
     * @param configKey 参数Key
     */
    @GetMapping(value = ["/configKey/{configKey}"])
    @ResultBody
    fun getConfigKey(@PathVariable configKey: String?): R<String> {
        return R.ok("操作成功", configService.selectConfigByKey(configKey) ?: "")
    }

    /**
     * 新增参数配置
     */
    @SaCheckPermission("system:config:add")
    @Log(title = "参数管理", businessType = BusinessType.INSERT)
    @PostMapping
    @ResultBody
    fun add(@Validated @RequestBody config: SysConfigParam) {
        if (!configService.checkConfigKeyUnique(config)) {
            throw ServiceException("新增参数'" + config.configName + "'失败，参数键名已存在")
        }
        configService.insertConfig(config)
    }

    /**
     * 修改参数配置
     */
    @SaCheckPermission("system:config:edit")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @PutMapping
    @ResultBody
    fun edit(@Validated @RequestBody config: SysConfigParam) {
        if (!configService.checkConfigKeyUnique(config)) {
            throw ServiceException("修改参数'" + config.configName + "'失败，参数键名已存在")
        }
        configService.updateConfig(config)
    }

    /**
     * 根据参数键名修改参数配置
     */
    @SaCheckPermission("system:config:edit")
    @Log(title = "参数管理", businessType = BusinessType.UPDATE)
    @PutMapping("/updateByKey")
    @ResultBody
    fun updateByKey(@RequestBody config: SysConfigParam) {
        configService.updateConfig(config)
    }

    /**
     * 删除参数配置
     *
     * @param configIds 参数ID串
     */
    @SaCheckPermission("system:config:remove")
    @Log(title = "参数管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    @ResultBody
    fun remove(@PathVariable configIds: Array<Long>) {
        configService.deleteConfigByIds(configIds)
    }

    /**
     * 刷新参数缓存
     */
    @SaCheckPermission("system:config:remove")
    @Log(title = "参数管理", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    @ResultBody
    fun refreshCache() {
        configService.resetConfigCache()
    }
}
