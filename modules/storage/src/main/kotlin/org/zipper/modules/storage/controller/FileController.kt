package org.zipper.modules.storage.controller

import cn.dev33.satoken.annotation.SaCheckPermission
import cn.dev33.satoken.annotation.SaIgnore
import cn.hutool.core.util.StrUtil
import cn.hutool.core.util.URLUtil
import io.swagger.v3.oas.annotations.Operation
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.log
import org.zipper.common.core.ext.writeAttachment
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.mybatis.core.page.TableDataInfo
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.modules.storage.domain.param.FileRecordPageParam
import org.zipper.modules.storage.domain.param.FileUploadParam
import org.zipper.modules.storage.domain.vo.FileRecordVo
import org.zipper.modules.storage.service.FileRecordService
import org.zipper.modules.storage.service.FileService

//@Tag(name = "管理后台 - 文件存储")
@RestController
@RequestMapping("/store/file")
@Validated
class FileController(
    private val fileService: FileService,
    private val fileRecordService: FileRecordService,
) {

    @SaCheckPermission("store:file:upload")
    @Log(title = "文件存储-上传", businessType = BusinessType.INSERT)
//    @Operation(summary = "上传文件", description = "模式一：后端上传文件")
    @PostMapping(value = ["/upload"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResultBody
    fun upload(@Valid fileUploadParam: FileUploadParam): FileRecordVo {
        if (fileUploadParam.file.isEmpty) {
            throw ServiceException("上传文件不能为空")
        }
        val createBo = fileService.createFile(fileUploadParam.file)
        return fileRecordService.create(createBo)
    }

    @SaCheckPermission("system:file:remove")
    @DeleteMapping("/delete")
    @ResultBody
    fun delete(@RequestParam("ids") recordIds: Array<Long>) {
        if (recordIds.isEmpty()) {
            throw ServiceException("id不能为空")
        }

        val deleteList = fileRecordService.delete(recordIds)
        for (fileRecordEntity in deleteList) {
            if (fileRecordEntity.configId != null && fileRecordEntity.path != null) {
                kotlin.runCatching {
                    fileService.deleteFile(fileRecordEntity.configId!!, fileRecordEntity.path!!)
                }.onFailure {
                    log.error("删除文件失败", it)
                }
            }
        }
    }

    @SaCheckPermission("store:file:download")
    @Operation(summary = "下载文件", description = "模式二：前端下载文件")
    @GetMapping("/download/{configId}/**")
    fun download(
        request: HttpServletRequest,
        response: HttpServletResponse,
        @PathVariable("configId") configId: Long
    ) {
        // 获取请求的路径
        var path = StrUtil.subAfter(request.requestURI, "/download/", false)
        require(!StrUtil.isEmpty(path)) { "结尾的 path 路径必须传递" }
        // 解码，解决中文路径的问题 https://gitee.com/zhijiantianya/ruoyi-vue-pro/pulls/807/
        path = URLUtil.decode(path)
        kotlin.runCatching {
            val content = fileService.getFileContent(configId, path)
            response.writeAttachment(path, content)
        }.onFailure {
            log.warn("下载文件失败 configId={} path={}", configId, path)
            log.error("下载文件失败", it)
            response.status = HttpStatus.NOT_FOUND.value()
        }
    }

//    @CrossOrigin(value = ["*"], allowCredentials = "true", allowedHeaders = ["*"])
    @SaIgnore
    @Operation(summary = "请求文件", description = "获取上传文件内容")
    @GetMapping("/{configId}/**")
    fun getFile(request: HttpServletRequest, response: HttpServletResponse, @PathVariable("configId") configId: Long) {
        val filePath = URLUtil.decode(StrUtil.subAfter(request.requestURI, "/${configId}/", false))
        if (filePath.isEmpty()) {
            response.status = HttpStatus.NOT_FOUND.value()
            return
        }
        runCatching {
            val content = fileService.getFileContent(configId, filePath)
            response.writeAttachment(filePath, content)
        }.onFailure {
            log.warn("获取文件内容失败 url = {}", request.requestURI)
            log.error("获取文件内容失败", it)
            response.status = HttpStatus.NOT_FOUND.value()
        }
    }

    @SaCheckPermission("store:file:list")
    @GetMapping("/list")
    fun pageList(@Valid param: FileRecordPageParam): TableDataInfo<FileRecordVo> {
        return fileRecordService.pageList(param)
    }

    /**
     *
     */
    @SaCheckPermission("store:file:list")
    @GetMapping("/listByIds")
    @ResultBody
    fun listByIds(@RequestParam("ids") recordIds: Array<Long>): List<FileRecordVo> {
        return fileRecordService.queryByIds(recordIds)
    }
}