package org.zipper.common.core.ext

import org.zipper.common.core.constant.Constants

fun String?.isHttpUrl(): Boolean {
    if (this == null) {
        return false
    }
    return this.startsWith(Constants.HTTP) || this.startsWith(Constants.HTTPS)
}