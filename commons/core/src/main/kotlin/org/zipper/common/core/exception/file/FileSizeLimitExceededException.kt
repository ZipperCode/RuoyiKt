package org.zipper.common.core.exception.file

import org.zipper.common.core.exception.file.FileException
import java.io.Serial

/**
 * 文件名大小限制异常类
 *
 */
class FileSizeLimitExceededException(
    defaultMaxSize: Long
) : FileException("upload.exceed.maxSize", arrayOf(defaultMaxSize)) {
    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
