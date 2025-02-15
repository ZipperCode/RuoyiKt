package org.zipper.common.core.exception.base

import org.zipper.common.core.framework.MessageUtils
import java.io.Serial

/**
 * 基础异常
 *
 * @author ruoyi
 */
open class BaseException : RuntimeException {
    /**
     * 所属模块
     */
    private val module: String?

    /**
     * 错误码
     */
    private val code: String?

    /**
     * 错误码对应的参数
     */
    private val args: Array<out Any?>?

    /**
     * 错误消息
     */
    private val defaultMessage: String?

    constructor(module: String, code: String, args: Array<out Any?>?) : this(module, code, args, null)

    constructor(module: String?, defaultMessage: String?) : this(module, null, null, defaultMessage)

    constructor(code: String?, args: Array<out Any?>?) : this(null, code, args, null)

    constructor(defaultMessage: String?) : this(null, null, null, defaultMessage)

    constructor(module: String?, code: String?, args: Array<out Any?>?, defaultMessage: String?) : super(defaultMessage) {
        this.module = module
        this.code = code
        this.args = args
        this.defaultMessage = defaultMessage
    }

    override val message: String
        get() {
            var message: String? = null
            if (!code.isNullOrEmpty()) {
                message = MessageUtils.message(code, args)
            }
            if (message == null) {
                message = defaultMessage
            }
            return message!!
        }

    @Serial
    private val serialVersionUID = 1L
}
