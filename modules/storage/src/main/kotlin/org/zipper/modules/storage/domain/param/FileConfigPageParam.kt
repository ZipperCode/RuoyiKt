package org.zipper.modules.storage.domain.param

import io.swagger.v3.oas.annotations.media.Schema
import org.zipper.framework.mybatis.core.page.PageQuery

/**
 * 文件管理 分页查询 Request VO
 */
@Schema(description = "管理后台 - 文件配置分页 Request VO")
class FileConfigPageParam : PageQuery() {
    @Schema(description = "配置名", example = "S3 - 阿里云")
    var name: String? = null

    @Schema(description = "存储器", example = "1")
    var storage: Int? = null
}