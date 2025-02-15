package org.zipper.modules.storage.client.local

import jakarta.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.URL
import org.zipper.common.core.annotation.NoArgs
import org.zipper.modules.storage.client.FileClientConfig

@NoArgs
data class LocalFileClientConfig(
    /**
     * 基础路径，保存文件的目录
     */
    @field:NotEmpty(message = "基础路径不能为空")
    var basePath: String = "",

    /**
     * 自定义域名
     */
    @field:NotEmpty(message = "domain 不能为空")
    @field:URL(message = "domain 必须是 URL 格式")
    var domain: String = ""
) : FileClientConfig {

    override fun getCustomDomain(): String {
        return domain
    }
}