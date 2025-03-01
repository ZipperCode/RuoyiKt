package org.zipper.modules.account.controller

import cn.dev33.satoken.annotation.SaCheckPermission
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.validate
import org.zipper.common.core.validate.EditGroup
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.PageQuery
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.modules.account.constant.DataClassify
import org.zipper.modules.account.domain.param.AppAccountParam
import org.zipper.modules.account.domain.param.AppAccountRecordParam
import org.zipper.modules.account.domain.param.AppQrParam
import org.zipper.modules.account.domain.param.UploadAccountParam
import org.zipper.modules.account.domain.vo.AccountUploadResultVo
import org.zipper.modules.account.domain.vo.AppDispatchVo
import org.zipper.modules.account.domain.vo.AppQrRecordVo
import org.zipper.modules.account.domain.vo.AppQrVo
import org.zipper.modules.account.service.AppQrService
import org.zipper.optional.idempotent.annotation.RepeatSubmit
import java.util.concurrent.atomic.AtomicBoolean

@RestController
@RequestMapping("/app/qr")
@Validated
class AppQrController(
    private val appQrService: AppQrService
) {
    @SaCheckPermission("app:qr:add")
    @Log(title = "上传账号", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping
    @ResultBody
    fun add(@Valid @RequestBody param: AppQrParam) {
        appQrService.add(param).validate()
    }

    @SaCheckPermission("app:qr:edit")
    @Log(title = "修改账号", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping
    @ResultBody
    fun edit(@Validated(EditGroup::class) @RequestBody param: AppQrParam) {
        appQrService.edit(param).validate()
    }

    @SaCheckPermission("app:qr:delete")
    @Log(title = "删除账号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ResultBody
    fun delete(@PathVariable ids: Array<Long>) {
        appQrService.delete(ids)
    }

    @SaCheckPermission("app:qr:edit")
    @Log(title = "更新状态", businessType = BusinessType.UPDATE)
    @PutMapping("/updateStatus")
    @ResultBody
    fun updateStatus(configId: Long, status: Int): Int {
        return appQrService.updateStatus(configId, status)
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
        return appQrService.batchUpload(param)
    }

    @SaCheckPermission("app:qr:list")
    @GetMapping("/{id}")
    @ResultBody
    fun getInfo(@PathVariable id: @NotNull(message = "key不能为空") Long): AppQrVo? {
        return appQrService.getInfo(id)
    }

    @SaCheckPermission("app:qr:list")
    @GetMapping("/list")
    fun list(@Valid param: AppQrParam, pageQuery: PageQuery): TableDataInfo<AppQrVo> {
        return appQrService.pageList(param, pageQuery)
    }

    @SaCheckPermission("app:qr:record:list")
    @GetMapping("/recordList")
    fun recordList(@Valid param: AppAccountRecordParam, pageQuery: PageQuery): TableDataInfo<AppQrRecordVo> {
        return appQrService.recordPageList(param, pageQuery)
    }

    private val lockDispatch = AtomicBoolean()

    @RepeatSubmit
    @Log(title = "更新状态", businessType = BusinessType.UPDATE)
    @SaCheckPermission("app:account:dispatch")
    @GetMapping("/dispatch")
    @ResultBody
    fun dispatch(param: AppQrParam): AppDispatchVo {
        if (lockDispatch.get()) {
            throw ServiceException("正在执行分配，请稍后重试")
        }
        // 并发不考虑，仅考虑未处理完不提交
        synchronized(lockDispatch) {
            try {
                lockDispatch.set(true)
                return appQrService.dispatch(param)
            } finally {
                lockDispatch.set(false)
            }
        }
    }

}