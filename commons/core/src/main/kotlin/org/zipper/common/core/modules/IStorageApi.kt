package org.zipper.common.core.modules

import org.springframework.web.multipart.MultipartFile

interface IStorageApi {

    fun upload(file: MultipartFile?): Any
}