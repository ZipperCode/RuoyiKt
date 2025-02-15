package org.zipper.modules.storage.domain.mixin

import org.zipper.modules.storage.client.FileClientConfig

interface FileConfigMixin {
    /**
     * 编号
     */
    var id: Long?

    /**
     * 配置名称
     */
    var name: String

    /**
     * 配置名称
     */
    var storage: Int

    /**
     * 备注信息
     */
    var remark: String?

    /**
     * 是否主配置
     */
    var master: Boolean

    /**
     * 配置信息
     */
    var config: FileClientConfig?
}