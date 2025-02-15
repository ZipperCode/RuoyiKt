package org.zipper.modules.storage.controller

import cn.dev33.satoken.annotation.SaCheckPermission
import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.validate.EditGroup
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.framework.web.ext.validateRow
import org.zipper.modules.storage.domain.param.FileConfigPageParam
import org.zipper.modules.storage.domain.param.FileConfigSaveParam
import org.zipper.modules.storage.domain.vo.FileConfigVo
import org.zipper.modules.storage.enums.FileStorageEnum
import org.zipper.modules.storage.service.FileConfigService
import org.zipper.optional.idempotent.annotation.RepeatSubmit

@io.swagger.v3.oas.annotations.tags.Tag(name = "管理后台 - 文件配置")
@RestController
@RequestMapping("/store/file/config")
@Validated
class FileConfigController(
    private val fileConfigService: FileConfigService
) {
    @SaCheckPermission("store:fileConfig:add")
    @Log(title = "对象存储配置", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping
    @ResultBody
    fun add(@Valid @RequestBody param: FileConfigSaveParam, request: HttpServletRequest) {
        fileConfigService.createConfig(param).validateRow()
    }

    @SaCheckPermission("store:fileConfig:edit")
    @Log(title = "编辑存储配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping
    @ResultBody
    fun edit(@Validated(EditGroup::class) @RequestBody param: FileConfigSaveParam) {
        fileConfigService.updateConfig(param).validateRow()
    }

    @SaCheckPermission("store:fileConfig:delete")
    @Log(title = "删除存储配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    @ResultBody
    fun delete(@PathVariable configIds: Array<Long>) {
        fileConfigService.deleteConfig(configIds).validateRow()
    }

    @SaCheckPermission("system:fileConfig:edit")
    @Log(title = "切换主配置", businessType = BusinessType.UPDATE)
    @PutMapping("/updateMaster")
    @ResultBody
    fun updateMaster(configId: Long) {
        fileConfigService.updateMaster(configId)
    }

    @SaCheckPermission("system:fileConfig:list")
    @GetMapping("/{configId}")
    @ResultBody
    fun getInfo(@PathVariable configId: @NotNull(message = "key不能为空") Long): FileConfigVo? {
        return fileConfigService.getConfig(configId)
    }


    @SaCheckPermission("store:ossConfig:list")
    @GetMapping("/list")
    fun list(@Valid param: FileConfigPageParam): TableDataInfo<FileConfigVo> {
        return fileConfigService.pageList(param)
    }
}