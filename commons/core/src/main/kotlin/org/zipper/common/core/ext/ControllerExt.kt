package org.zipper.common.core.ext

import org.zipper.common.core.exception.ServiceException

fun Int.validateRow() {
    if (this <= 0) {
        throw ServiceException("验证失败，row < 0")
    }
}

fun Boolean.validate() {
    if (!this) {
        throw ServiceException("验证失败")
    }
}