package org.zipper.common.core.exception

class BaseException : RuntimeException {

    val code: String
    val msg: String
    val args: Array<Any>

    constructor(code: String): super("") {
        this.code = code
        this.msg = "code"
        this.args = arrayOf()
    }

    constructor(code: String, msg: String) : super(msg) {
        this.code = code
        this.msg = msg
        this.args = arrayOf()
    }

    constructor(code: String, msg: String, args: Array<Any>) : super(msg) {
        this.code = code
        this.msg = msg
        this.args = args
    }
}