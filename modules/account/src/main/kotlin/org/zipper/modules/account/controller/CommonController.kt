package org.zipper.modules.account.controller

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.zipper.common.core.modules.IStorageApi
import org.zipper.framework.security.aspect.ResultBody

@RestController
@RequestMapping("/account/common")
class CommonController(
    private val storageApi: IStorageApi
) {
    @PostMapping(value = ["/upload"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    @ResultBody
    fun upload(file: MultipartFile): Any {
        return storageApi.upload(file)
    }
}