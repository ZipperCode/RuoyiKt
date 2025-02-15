package org.zipper.framework.web.filter

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.commons.lang3.StringUtils
import org.springframework.http.HttpMethod
import java.io.IOException

/**
 * 防止XSS攻击的过滤器
 *
 * @author ruoyi
 */
class XssFilter : Filter {
    /**
     * 排除链接
     */
    var excludes: MutableList<String> = ArrayList()

    @Throws(ServletException::class)
    override fun init(filterConfig: FilterConfig) {
        val tempExcludes = filterConfig.getInitParameter("excludes")
        if (StringUtils.isNotEmpty(tempExcludes)) {
            excludes.addAll(tempExcludes.split(","))
        }
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest
        val resp = response as HttpServletResponse
        if (handleExcludeURL(req)) {
            chain.doFilter(request, response)
            return
        }
        val xssRequest = XssHttpServletRequestWrapper(request)
        chain.doFilter(xssRequest, response)
    }

    private fun handleExcludeURL(request: HttpServletRequest): Boolean {
        val url = request.servletPath ?: return true
        val method = request.method
        // GET DELETE 不过滤
        if (method == null || HttpMethod.GET.matches(method) || HttpMethod.DELETE.matches(method)) {
            return true
        }

        return excludes.any { it.matches(Regex(url)) }
    }
}
