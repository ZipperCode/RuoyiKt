package org.zipper.modules.storage.service

import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.modules.IStorageApi

@Service
class StorageApiImpl(
    private val fileService: FileService,
    private val fileRecordService: FileRecordService,
) : IStorageApi {
    override fun upload(file: MultipartFile?): Any {
        if (file === null || file.isEmpty) {
            throw ServiceException("上传文件不能为空")
        }
        val createBo = fileService.createFile(file)
        return fileRecordService.create(createBo)
    }
}