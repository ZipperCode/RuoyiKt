package org.zipper.common.core.exception

import org.zipper.common.core.constant.HttpStatus
import org.zipper.common.core.framework.MessageUtils

class MessageException : RuntimeException {

    val resultCode: Int
    private val messageCode: String
    private val args: Array<out Any?>?

    constructor(resultCode: Int, messageCode: String, vararg args: Any?) {
        this.resultCode = resultCode
        this.messageCode = messageCode
        this.args = args
    }

    constructor(messageCode: String, vararg args: Any?) {
        this.resultCode = HttpStatus.ERROR
        this.messageCode = messageCode
        this.args = args
    }

    override val message: String get() = MessageUtils.message(messageCode, *args ?: emptyArray())
}