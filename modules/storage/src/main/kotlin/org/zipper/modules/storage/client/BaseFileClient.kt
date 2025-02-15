package org.zipper.modules.storage.client

/**
 * 文件客户端基类
 * @param id 客户端编号
 * @param config 配置
 */
abstract class BaseFileClient<Config : FileClientConfig>(
    private val id: Long,
    private val config: Config,
) : FileClient {

    override fun getId(): Long = id

    override fun getConfig(): Config = config

    protected fun getFormatUrl(path: String): String {
        return "${config.getCustomDomain()}/store/file/${getId()}/$path".replace("//", "/")
    }

    protected fun getUrlPath(path: String): String {
        return "/store/file/${getId()}/$path"
    }
}
