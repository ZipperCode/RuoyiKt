package org.zipper.modules.storage.client.db

import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.springCacheByType
import org.zipper.framework.mybatis.core.MybatisKt
import org.zipper.modules.storage.client.BaseFileClient
import org.zipper.modules.storage.domain.entity.SysFileContentEntity
import org.zipper.modules.storage.mapper.FileContentMapper

/**
 * 数据库文件存储客户端
 */
class DatabaseFileClient(
    id: Long, config: DataBaseFileClientConfig
) : BaseFileClient<DataBaseFileClientConfig>(id, config) {

    private val fileContentMapper: FileContentMapper? by springCacheByType<FileContentMapper>()

    override fun upload(content: ByteArray, path: String, type: String): String {
        fileContentMapper?.insert(
            SysFileContentEntity().apply {
                configId = getId()
                this.path = path
                this.content = content
            }
        )
        return getUrlPath(path)
    }

    override fun delete(path: String) {
        fileContentMapper?.delete(
            MybatisKt.ktQuery<SysFileContentEntity>()
                .eq(SysFileContentEntity::configId, getId())
                .eq(SysFileContentEntity::path, path)
        )
    }

    override fun getContent(path: String): ByteArray {
        val list = fileContentMapper?.selectList(
            MybatisKt.ktQuery<SysFileContentEntity>()
                .eq(SysFileContentEntity::configId, getId())
                .eq(SysFileContentEntity::path, path)
                .orderByDesc(SysFileContentEntity::id)
        )
        val content = list?.firstOrNull()?.content ?: throw ServiceException("文件内容不存在")
        return content
    }


}