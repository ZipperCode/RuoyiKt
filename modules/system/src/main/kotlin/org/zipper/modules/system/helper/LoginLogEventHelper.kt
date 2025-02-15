package org.zipper.modules.system.helper

import cn.hutool.extra.servlet.JakartaServletUtil
import cn.hutool.extra.spring.SpringUtil
import cn.hutool.http.useragent.UserAgentUtil
import org.zipper.common.core.ext.getRequest
import org.zipper.framework.log.event.LoginLogEvent

object LoginLogEventHelper {
    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status   状态
     * @param message  消息内容
     * @return
     */
    fun postRecord(username: String?, status: String?, message: String?) {
        val request = Thread.currentThread().getRequest() ?: return
        val userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"))
        val ip = JakartaServletUtil.getClientIP(request)
        SpringUtil.getApplicationContext().publishEvent(
            LoginLogEvent(
                username = username,
                status = status,
                message = message,
                userAgent = userAgent,
                ip = ip,
            )
        )
    }
}