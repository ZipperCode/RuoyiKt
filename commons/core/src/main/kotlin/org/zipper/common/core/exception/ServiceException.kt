package org.zipper.common.core.exception

import java.io.Serial

/**
 * 业务异常
 *
 * @author ruoyi
 */
class ServiceException : RuntimeException {
    /**
     * 错误码
     */
    final var code: Int = 400
        private set

    /**
     * 错误提示
     */
    final override var message: String = ""
        private set

    /**
     * 错误明细，内部调试错误
     */
    final var detailMessage: String? = null
        private set

    constructor() : super()

    constructor(message: String) {
        this.message = message
    }

    constructor(message: String, code: Int) {
        this.message = message
        this.code = code
    }

    fun setMessage(message: String): ServiceException {
        this.message = message
        return this
    }

    fun setDetailMessage(detailMessage: String?): ServiceException {
        this.detailMessage = detailMessage
        return this
    }

    companion object {
        @Serial
        private val serialVersionUID = 1L
    }
}
