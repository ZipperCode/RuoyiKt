package org.zipper.modules.storage.enums

import org.zipper.modules.storage.client.FileClient
import org.zipper.modules.storage.client.FileClientConfig
import org.zipper.modules.storage.client.db.DataBaseFileClientConfig
import org.zipper.modules.storage.client.db.DatabaseFileClient
import org.zipper.modules.storage.client.local.LocalFileClient
import org.zipper.modules.storage.client.local.LocalFileClientConfig
import org.zipper.modules.storage.client.s3.S3FileClient
import org.zipper.modules.storage.client.s3.S3FileClientConfig
import kotlin.reflect.KClass


/**
 * 文件存储器枚举
 * @param storage 存储器
 * @param configClass 配置类
 * @param clientClass 客户端类
 */
enum class FileStorageEnum(
    val storage: Int,
    val configClass: KClass<out FileClientConfig>,
    val clientClass: KClass<out FileClient>
) {
    DB(1, DataBaseFileClientConfig::class, DatabaseFileClient::class),
    LOCAL(10, LocalFileClientConfig::class, LocalFileClient::class),
    S3(20, S3FileClientConfig::class, S3FileClient::class),
    ;


    companion object {

        fun getByStorage(storage: Int): FileStorageEnum {
            return entries.firstOrNull { it.storage == storage } ?: throw IllegalArgumentException("storage not found")
        }
    }
}
