package org.zipper.common.core.exception.file

import org.zipper.common.core.exception.file.FileException
import java.io.Serial

/**
 * 文件名称超长限制异常类
 *
 * @author ruoyi
 */
class FileNameLengthLimitExceededException(
    defaultFileNameLength: Int
) : FileException("upload.filename.exceed.length", arrayOf(defaultFileNameLength)) {
    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
