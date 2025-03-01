package org.zipper.modules.storage.client.local

import cn.hutool.core.io.FileUtil
import org.zipper.modules.storage.client.BaseFileClient
import java.io.File
import java.nio.file.Paths

class LocalFileClient(
    id: Long, config: LocalFileClientConfig
) : BaseFileClient<LocalFileClientConfig>(id, config) {
    override fun upload(content: ByteArray, path: String, type: String): String {
        val filePath = File(getConfig().basePath, Paths.get(path).normalize().toString())
        filePath.parentFile?.takeIf { !it.exists() }?.run {
            mkdirs()
        }
        FileUtil.writeBytes(content, filePath)
        return getUrlPath(path)
    }

    override fun delete(path: String) {
        val filePath = File(getConfig().basePath, Paths.get(path).normalize().toString())
        FileUtil.del(filePath)
    }

    override fun getContent(path: String): ByteArray {
        val filePath = File(getConfig().basePath, Paths.get(path).normalize().toString())
        return FileUtil.readBytes(filePath)
    }
}