package org.zipper.modules.storage.service

import cn.hutool.core.io.FileMagicNumber
import cn.hutool.core.io.IoUtil
import cn.hutool.crypto.digest.DigestUtil
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.zipper.common.core.ext.withType
import org.zipper.common.core.framework.store.IStorageService
import org.zipper.common.core.framework.store.UploadFileResult
import org.zipper.common.core.utils.FileUtils
import org.zipper.modules.storage.client.s3.S3FileClientConfig
import org.zipper.modules.storage.domain.bo.FileCreateBo
import java.io.BufferedInputStream
import kotlin.math.min

/**
 * 文件服务，用于操作文件上传，删除，获取
 */
@Service
class FileServiceImpl(
    private val fileConfigService: FileConfigService,
    private val fileRecordService: FileRecordService,
) : FileService, IStorageService {

    override fun upload(file: MultipartFile): UploadFileResult {
        if (file.isEmpty) {
            throw RuntimeException("上传文件为空")
        }
        val create = createFile(file)
        val record = fileRecordService.create(create)

        return UploadFileResult(record.id, record.url)
    }

    override fun createFile(multipartFile: MultipartFile): FileCreateBo {
        val stream = BufferedInputStream(multipartFile.inputStream)
        stream.mark(min(stream.available(), 1024))
        val originFileName: String? = multipartFile.originalFilename ?: multipartFile.name
        val type = FileUtils.getFileType(stream, originFileName)
        if (type == FileMagicNumber.UNKNOWN) {
            throw RuntimeException("文件类型未知")
        }
        stream.reset()
        val fileSize = multipartFile.size
        val extName = originFileName?.substringAfter(".") ?: type.extension
        val bytes = IoUtil.readBytes(stream)
        val hash = DigestUtil.sha256Hex(bytes)
        val path = "$hash.$extName"
        val masterFileClient = fileConfigService.getMasterFileClient()
        val configId = masterFileClient.getId()
        val urlPath = masterFileClient.upload(bytes, path, type.mimeType)
        var serviceProvider: String? = null
        masterFileClient.getConfig().withType<S3FileClientConfig> {
            serviceProvider = service
        }
        return FileCreateBo(configId, path, urlPath, type.mimeType, hash, fileSize, serviceProvider, extName)
    }

    override fun deleteFile(configId: Long, relativePath: String) {
        val fileClient = fileConfigService.getFileClient(configId)
        fileClient.delete(relativePath)
    }


    override fun getFileContent(configId: Long, relativePath: String): ByteArray {
        val fileClient = fileConfigService.getFileClient(configId)
        return fileClient.getContent(relativePath)
    }

    override fun getFileUrlById(id: Long): String? {
        return fileRecordService.queryByIds(arrayOf(id)).firstOrNull()?.url
    }
}