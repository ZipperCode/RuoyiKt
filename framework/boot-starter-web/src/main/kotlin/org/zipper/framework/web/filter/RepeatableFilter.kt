package org.zipper.framework.web.filter

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import org.apache.commons.lang3.StringUtils
import org.springframework.http.MediaType
import java.io.IOException

/**
 * Repeatable 过滤器
 * 作用：防止重复请求
 */
class RepeatableFilter : Filter {

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        var requestWrapper: ServletRequest? = null
        if (request is HttpServletRequest
            && StringUtils.startsWithIgnoreCase(request.getContentType(), MediaType.APPLICATION_JSON_VALUE)
        ) {
            requestWrapper = RepeatedlyRequestWrapper(request, response)
        }
        if (null == requestWrapper) {
            chain.doFilter(request, response)
        } else {
            chain.doFilter(requestWrapper, response)
        }
    }
}
