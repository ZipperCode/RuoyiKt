package org.zipper.common.core.exception.file

import org.zipper.common.core.exception.base.BaseException
import java.io.Serial


/**
 * 文件信息异常类
 *
 */
open class FileException(code: String?, args: Array<Any?>?) : BaseException("file", code, args, null) {
    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
