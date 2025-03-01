package org.zipper.modules.account.controller

import cn.dev33.satoken.annotation.SaCheckPermission
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.ext.validate
import org.zipper.common.core.validate.EditGroup
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.modules.account.domain.param.AppLinksParam
import org.zipper.modules.account.domain.vo.AppLinksVo
import org.zipper.modules.account.service.AppLinksService
import org.zipper.optional.idempotent.annotation.RepeatSubmit

@RestController
@RequestMapping("/app/links")
@Validated
class AppLinksController(
    private val appLinksService: AppLinksService
) {

    @SaCheckPermission("app:links:add")
    @Log(title = "添加链接", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping
    @ResultBody
    fun add(@Valid @RequestBody param: AppLinksParam) {
        appLinksService.add(param).validate()
    }

    @SaCheckPermission("app:links:edit")
    @Log(title = "修改链接", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping
    @ResultBody
    fun edit(@Validated(EditGroup::class) @RequestBody param: AppLinksParam) {
        appLinksService.edit(param).validate()
    }

    @SaCheckPermission("app:links:delete")
    @Log(title = "删除l链接", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ResultBody
    fun delete(@PathVariable ids: Array<Long>) {
        appLinksService.delete(ids)
    }

    @SaCheckPermission("app:links:check")
    @Log(title = "校验链接", businessType = BusinessType.UPDATE)
    @PutMapping("/check")
    @ResultBody
    fun updateStatus(id: Long): Boolean {
        return appLinksService.checkExists(id)
    }

    @SaCheckPermission("app:links:list")
    @GetMapping("/{id}")
    @ResultBody
    fun getInfo(@PathVariable id: @NotNull(message = "key不能为空") Long): AppLinksVo? {
        return appLinksService.getInfo(id)
    }


    @SaCheckPermission("app:links:list")
    @GetMapping("/list")
    fun list(@Valid param: AppLinksParam, pageQuery: PageQuery): TableDataInfo<AppLinksVo> {
        return appLinksService.pageList(param, pageQuery)
    }

}