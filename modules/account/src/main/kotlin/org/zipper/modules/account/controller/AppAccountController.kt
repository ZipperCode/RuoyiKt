package org.zipper.modules.account.controller

import cn.dev33.satoken.annotation.SaCheckPermission
import cn.dev33.satoken.annotation.SaMode
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
import org.zipper.modules.account.constant.Permissions
import org.zipper.modules.account.domain.param.*
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
    @SaCheckPermission(
        value = [
            Permissions.LineAdd,
            Permissions.PairsAdd,
            Permissions.Ws1Add,
            Permissions.Ws2Add,
            Permissions.Ws3Add,
            Permissions.Ws4Add,
            Permissions.Ws5Add,
            Permissions.Ws6Add,
            Permissions.Ws7Add
        ], mode = SaMode.OR
    )
    @Log(title = "上传账号", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping
    @ResultBody
    fun add(@Validated(AddGroup::class) @RequestBody param: AppAccountParam) {
        appAccountService.add(param).validate()
    }

    @SaCheckPermission(
        value = [
            Permissions.LineEdit,
            Permissions.PairsEdit,
            Permissions.Ws1Edit,
            Permissions.Ws2Edit,
            Permissions.Ws3Edit,
            Permissions.Ws4Edit,
            Permissions.Ws5Edit,
            Permissions.Ws6Edit,
            Permissions.Ws7Edit
        ], mode = SaMode.OR
    )
    @Log(title = "修改账号", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping
    @ResultBody
    fun edit(@Validated(EditGroup::class) @RequestBody param: AppAccountParam) {
        appAccountService.edit(param).validate()
    }

    @SaCheckPermission(
        value = [
            Permissions.LineDelete,
            Permissions.PairsDelete,
            Permissions.Ws1Delete,
            Permissions.Ws2Delete,
            Permissions.Ws3Delete,
            Permissions.Ws4Delete,
            Permissions.Ws5Delete,
            Permissions.Ws6Delete,
            Permissions.Ws7Delete
        ], mode = SaMode.OR
    )
    @Log(title = "删除账号", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    @ResultBody
    fun delete(@PathVariable ids: Array<Long>) {
        appAccountService.delete(ids)
    }

    @SaCheckPermission(
        value = [
            Permissions.LineEdit,
            Permissions.PairsEdit,
            Permissions.Ws1Edit,
            Permissions.Ws2Edit,
            Permissions.Ws3Edit,
            Permissions.Ws4Edit,
            Permissions.Ws5Edit,
            Permissions.Ws6Edit,
            Permissions.Ws7Edit
        ], mode = SaMode.OR
    )
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

    @SaCheckPermission(
        value = [
            Permissions.LineUnbind,
            Permissions.PairsUnbind,
            Permissions.Ws1Unbind,
            Permissions.Ws2Unbind,
            Permissions.Ws3Unbind,
            Permissions.Ws4Unbind,
            Permissions.Ws5Unbind,
            Permissions.Ws6Unbind,
            Permissions.Ws7Unbind
        ], mode = SaMode.OR
    )
    @Log(title = "解绑数据", businessType = BusinessType.UPDATE)
    @DeleteMapping("/unbind/{id}")
    @ResultBody
    fun unbind(@PathVariable id: Long): Boolean {
        return appAccountService.unBind(id)
    }

    @SaCheckPermission(
        value = [
            Permissions.LineUpload,
            Permissions.PairsUpload,
            Permissions.Ws1Upload,
            Permissions.Ws2Upload,
            Permissions.Ws3Upload,
            Permissions.Ws4Upload,
            Permissions.Ws5Upload,
            Permissions.Ws6Upload,
            Permissions.Ws7Upload
        ], mode = SaMode.OR
    )
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

    @SaCheckPermission(
        value = [
            Permissions.LineList,
            Permissions.PairsList,
            Permissions.Ws1List,
            Permissions.Ws2List,
            Permissions.Ws3List,
            Permissions.Ws4List,
            Permissions.Ws5List,
            Permissions.Ws6List,
            Permissions.Ws7List
        ], mode = SaMode.OR
    )
    @GetMapping("/{id}")
    @ResultBody
    fun getInfo(@PathVariable id: @NotNull(message = "key不能为空") Long): AppAccountVo? {
        return appAccountService.getInfo(id)
    }

    @SaCheckPermission(
        value = [
            Permissions.LineList,
            Permissions.PairsList,
            Permissions.Ws1List,
            Permissions.Ws2List,
            Permissions.Ws3List,
            Permissions.Ws4List,
            Permissions.Ws5List,
            Permissions.Ws6List,
            Permissions.Ws7List
        ], mode = SaMode.OR
    )
    @GetMapping("/list")
    fun list(@Valid param: AppAccountParam, pageQuery: PageQuery): TableDataInfo<AppAccountVo> {
        return appAccountService.pageList(param, pageQuery)
    }


    @SaCheckPermission(Permissions.WsSearch)
    @GetMapping("/search")
    fun searchTypeList(@Valid param: SearchAccountParam, pageQuery: PageQuery): TableDataInfo<AppAccountVo> {
        return appAccountService.searchTypeList(param, pageQuery)
    }

    @SaCheckPermission(
        value = [
            Permissions.LineList,
            Permissions.PairsList,
            Permissions.Ws1List,
            Permissions.Ws2List,
            Permissions.Ws3List,
            Permissions.Ws4List,
            Permissions.Ws5List,
            Permissions.Ws6List,
            Permissions.Ws7List
        ], mode = SaMode.OR
    )
    @GetMapping("/unusedList")
    fun unusedList(@Valid param: AppAccountParam, pageQuery: PageQuery): TableDataInfo<AppAccountVo> {
        param.status = DataStatus.Normal.status
        return appAccountService.pageList(param, pageQuery)
    }

    @SaCheckPermission(
        value = [
            Permissions.LineRecord,
            Permissions.PairsRecord,
            Permissions.Ws1Record,
            Permissions.Ws2Record,
            Permissions.Ws3Record,
            Permissions.Ws4Record,
            Permissions.Ws5Record,
            Permissions.Ws6Record,
            Permissions.Ws7Record
        ], mode = SaMode.OR
    )
    @GetMapping("/recordList")
    fun recordList(@Valid param: AppAccountRecordParam, pageQuery: PageQuery): TableDataInfo<AppAccountRecordVo> {
        return appAccountService.recordPageList(param, pageQuery)
    }

    @SaCheckPermission(
        value = [
            Permissions.LineExport,
            Permissions.PairsExport,
            Permissions.Ws1Export,
            Permissions.Ws2Export,
            Permissions.Ws3Export,
            Permissions.Ws4Export,
            Permissions.Ws5Export,
            Permissions.Ws6Export,
            Permissions.Ws7Export
        ], mode = SaMode.OR
    )
    @Log(title = "账号管理-导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    fun export(@Valid param: AppAccountParam, response: HttpServletResponse) {
        val list = appAccountService.exportList(param)
        response.responseToExcel(list, "账号数据")
    }


    private val lockDispatch = AtomicBoolean()

    @RepeatSubmit
    @SaCheckPermission(
        value = [
            Permissions.LineDispatch,
            Permissions.PairsDispatch,
            Permissions.Ws1Dispatch,
            Permissions.Ws2Dispatch,
            Permissions.Ws3Dispatch,
            Permissions.Ws4Dispatch,
            Permissions.Ws5Dispatch,
            Permissions.Ws6Dispatch,
            Permissions.Ws7Dispatch
        ], mode = SaMode.OR
    )
    @Log(title = "更新状态", businessType = BusinessType.UPDATE)
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