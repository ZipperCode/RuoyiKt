package org.zipper.modules.account.controller

import cn.dev33.satoken.annotation.SaCheckPermission
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.validate
import org.zipper.common.core.validate.AddGroup
import org.zipper.common.core.validate.EditGroup
import org.zipper.framework.excel.utils.responseToExcel
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.modules.account.constant.DataClassify
import org.zipper.modules.account.constant.DataStatus
import org.zipper.modules.account.domain.param.AppAccountParam
import org.zipper.modules.account.domain.param.AppAccountRecordParam
import org.zipper.modules.account.domain.param.UpdateStatusParam
import org.zipper.modules.account.domain.param.UploadAccountParam
import org.zipper.modules.account.domain.vo.AccountUploadResultVo
import org.zipper.modules.account.domain.vo.AppAccountRecordVo
import org.zipper.modules.account.domain.vo.AppAccountVo
import org.zipper.modules.account.domain.vo.AppDispatchVo
import org.zipper.modules.account.service.AppAccountService
import org.zipper.optional.idempotent.annotation.RepeatSubmit
import java.util.concurrent.atomic.AtomicBoolean

@RestController
@RequestMapping("/app/account")
@Validated
class AppAccountController(
    private val appAccountService: AppAccountService
) {
    @SaCheckPermission("app:account:add")
    @Log(title = "上传账号", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping
    @ResultBody
    fun add(@Validated(AddGroup::class) @RequestBody param: AppAccountParam) {
        appAccountService.add(param).validate()
    }

    @SaCheckPermission("app:account:edit")
    @Log(title = "修改账号", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping
    @ResultBody
    fun edit(@Validated(EditGroup::class) @RequestBody param: AppAccountParam) {
        appAccountService.edit(param).validate()
    }

    @SaCheckPermission("app:account:delete")
    @Log(title = "删除账号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ResultBody
    fun delete(@PathVariable ids: Array<Long>) {
        appAccountService.delete(ids)
    }

    @SaCheckPermission("app:account:edit")
    @Log(title = "更新状态", businessType = BusinessType.UPDATE)
    @PutMapping("/updateStatus")
    @ResultBody
    fun updateStatus(@RequestBody param: UpdateStatusParam): Int {
        if (param.ids.isEmpty() || param.status == null) {
            throw ServiceException("参数错误")
        }
        DataStatus.valid(param.status)
        return appAccountService.updateStatus(param.ids, param.status)
    }

    @SaCheckPermission("app:account:unbind")
    @Log(title = "解绑数据", businessType = BusinessType.UPDATE)
    @DeleteMapping("/unbind/{id}")
    @ResultBody
    fun unbind(@PathVariable id: Long): Boolean {
        return appAccountService.unBind(id)
    }

    @SaCheckPermission("app:account:upload")
    @Log(title = "批量上传账号", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping("/upload")
    @ResultBody
    fun upload(param: UploadAccountParam): AccountUploadResultVo {
        if (param.file == null) {
            throw ServiceException("未解析到文件内容")
        }
        DataClassify.valid(param.classify?.toInt())
        return appAccountService.batchUpload(param)
    }

    @SaCheckPermission("app:account:list")
    @GetMapping("/{id}")
    @ResultBody
    fun getInfo(@PathVariable id: @NotNull(message = "key不能为空") Long): AppAccountVo? {
        return appAccountService.getInfo(id)
    }

    @SaCheckPermission("app:account:list")
    @GetMapping("/list")
    fun list(@Valid param: AppAccountParam, pageQuery: PageQuery): TableDataInfo<AppAccountVo> {
        return appAccountService.pageList(param, pageQuery)
    }

    @SaCheckPermission("app:account:list")
    @GetMapping("/unusedList")
    fun unusedList(@Valid param: AppAccountParam, pageQuery: PageQuery): TableDataInfo<AppAccountVo> {
        param.status = DataStatus.Normal.status
        return appAccountService.pageList(param, pageQuery)
    }

    @SaCheckPermission("app:account:record:list")
    @GetMapping("/recordList")
    fun recordList(@Valid param: AppAccountRecordParam, pageQuery: PageQuery): TableDataInfo<AppAccountRecordVo> {
        return appAccountService.recordPageList(param, pageQuery)
    }

    @Log(title = "账号管理-导出", businessType = BusinessType.EXPORT)
    @SaCheckPermission("app:account:export")
    @PostMapping("/export")
    fun export(@Valid param: AppAccountParam, response: HttpServletResponse) {
        val list = appAccountService.exportList(param)
        response.responseToExcel(list, "账号数据")
    }


    private val lockDispatch = AtomicBoolean()

    @RepeatSubmit
    @Log(title = "更新状态", businessType = BusinessType.UPDATE)
    @SaCheckPermission("app:account:dispatch")
    @PostMapping("/dispatch")
    @ResultBody
    fun dispatch(@RequestBody param: AppAccountParam): AppDispatchVo {
        if (lockDispatch.get()) {
            throw ServiceException("正在执行分配，请稍后重试")
        }
        // 并发不考虑，仅考虑未处理完不提交
        synchronized(lockDispatch) {
            try {
                lockDispatch.set(true)
                return appAccountService.dispatch(param)
            } finally {
                lockDispatch.set(false)
            }
        }
    }
}