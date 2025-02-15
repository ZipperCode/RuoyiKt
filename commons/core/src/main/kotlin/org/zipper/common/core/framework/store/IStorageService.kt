package org.zipper.common.core.framework.store

import org.springframework.web.multipart.MultipartFile

interface IStorageService {

    fun upload(file: MultipartFile): UploadFileResult

    fun getFileUrlById(id: Long): String?
}