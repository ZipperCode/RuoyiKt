package org.zipper.framework.web.core

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.servlet.LocaleResolver
import java.util.*

/**
 * 获取请求头国际化信息
 *
 * @author Lion Li
 */
class I18nLocaleResolver : LocaleResolver {
    override fun resolveLocale(httpServletRequest: HttpServletRequest): Locale {
        val language = httpServletRequest.getHeader("content-language")
        var locale = Locale.getDefault()
        if (language != null && language.isNotEmpty()) {
            val split = language.split("_".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            locale = Locale(split[0], split[1])
        }
        return locale
    }

    override fun setLocale(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse?,
        locale: Locale?
    ) = Unit
}
