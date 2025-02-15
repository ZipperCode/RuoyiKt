package org.zipper.modules.storage.service

import org.springframework.web.multipart.MultipartFile
import org.zipper.modules.storage.domain.bo.FileCreateBo
import org.zipper.modules.storage.domain.entity.SysFileConfigEntity
import java.io.IOException

/**
 * 文件服务
 */
interface FileService {
    /**
     * 创建文件
     */
    fun createFile(multipartFile: MultipartFile): FileCreateBo

    /**
     * 删除文件
     * @param configId [SysFileConfigEntity.id]
     * @param relativePath 相对路径
     * @throws Exception 删除文件时，抛出 Exception 异常
     */
    fun deleteFile(configId: Long, relativePath: String)

    /**
     * 获取文件内容
     * @throws IOException
     */
    @Throws(IOException::class)
    fun getFileContent(configId: Long, relativePath: String): ByteArray
}