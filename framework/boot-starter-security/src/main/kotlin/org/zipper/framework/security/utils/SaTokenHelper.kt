package org.zipper.framework.security.utils

import cn.dev33.satoken.context.SaHolder

object SaTokenHelper {

    operator fun get(key: String): Any? {
        return SaHolder.getStorage().get(key)
    }

    operator fun set(key: String, value: Any?) {
        SaHolder.getStorage().set(key, value)
    }
}