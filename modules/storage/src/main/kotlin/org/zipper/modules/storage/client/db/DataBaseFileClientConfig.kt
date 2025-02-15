package org.zipper.modules.storage.client.db

import jakarta.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.URL
import org.zipper.common.core.annotation.NoArgs
import org.zipper.modules.storage.client.FileClientConfig

/**
 * 数据库文件存储配置
 */
@NoArgs
data class DataBaseFileClientConfig(
    /**
     * 域名
     */
    @field:NotEmpty(message = "domain 不能为空")
    @field:URL(message = "domain 必须是 URL 格式")
    var domain: String = ""
) : FileClientConfig {

    override fun getCustomDomain(): String {
        return domain
    }
}