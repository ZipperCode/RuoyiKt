package org.zipper.modules.account.domain.param

import jakarta.validation.constraints.NotNull
import org.springframework.web.multipart.MultipartFile

class UploadAccountParam {
    @field:NotNull(message = "文件附件不能为空")
    var file: MultipartFile? = null

    @field:NotNull(message = "classify不能为空")
    var classify: Int? = null
}