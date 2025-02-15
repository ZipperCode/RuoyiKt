package org.zipper.modules.system.listener

import cn.dev33.satoken.config.SaTokenConfig
import cn.dev33.satoken.listener.SaTokenListener
import cn.dev33.satoken.stp.SaLoginModel
import cn.hutool.http.useragent.UserAgentUtil
import org.springframework.stereotype.Component
import org.zipper.common.core.constant.CacheConstants
import org.zipper.common.core.domain.model.OnlineUserBean
import org.zipper.common.core.ext.getClientIp
import org.zipper.common.core.ext.getRequest
import org.zipper.common.core.ext.log
import org.zipper.common.core.framework.ip2area.Ip2AreaUtils
import org.zipper.framework.redis.utils.RedisUtils
import org.zipper.framework.security.utils.LoginHelper
import java.time.Duration

/**
 * 用户行为 侦听器的实现
 *
 * @author Lion Li
 */
@Component
class UserActionListener(
    private val tokenConfig: SaTokenConfig
) : SaTokenListener {
    /**
     * 每次登录时触发
     */
    override fun doLogin(loginType: String, loginId: Any, tokenValue: String, loginModel: SaLoginModel) {
        val request = Thread.currentThread().getRequest() ?: return
        val userAgent = UserAgentUtil.parse(request.getHeader("User-Agent"))
        val ip: String = request.getClientIp()
        val user = LoginHelper.getLoginUser()
        val dto = OnlineUserBean(
            tokenId = tokenValue,
            deptName = user?.deptName,
            userName = user?.username,
            clientKey = "",
            deviceType = "",
            ipaddr = ip,
            loginLocation = Ip2AreaUtils.getAreaCity(ip),
            browser = userAgent.browser.name,
            os = userAgent.os.name,
            loginTime = System.currentTimeMillis()
        )

        if (tokenConfig.timeout.toInt() == -1) {
            RedisUtils.setCacheObject(CacheConstants.ONLINE_TOKEN_KEY + tokenValue, dto)
        } else {
            RedisUtils.setCacheObject(CacheConstants.ONLINE_TOKEN_KEY + tokenValue, dto, Duration.ofSeconds(tokenConfig.getTimeout()))
        }
        // TODO 记录登录日志
//        LoginLogEventHelper.postRecord(
//            tenantId = user?.tenantId,
//            username = user?.username,
//            status = Constants.LOGIN_SUCCESS,
//            message = MessageUtils.message("user.login.success"),
//        )

        // 更新登录信息
        // loginService.recordLoginInfo(user?.userId, ip)
        log.info("user doLogin, userId:{}, token:{}", loginId, tokenValue)
    }

    /**
     * 每次注销时触发
     */
    override fun doLogout(loginType: String, loginId: Any, tokenValue: String) {
        RedisUtils.deleteObject(CacheConstants.ONLINE_TOKEN_KEY + tokenValue)
        log.info("user doLogout, userId:{}, token:{}", loginId, tokenValue)
    }

    /**
     * 每次被踢下线时触发
     */
    override fun doKickout(loginType: String, loginId: Any, tokenValue: String) {
        RedisUtils.deleteObject(CacheConstants.ONLINE_TOKEN_KEY + tokenValue)
        log.info("user doKickout, userId:{}, token:{}", loginId, tokenValue)
    }

    /**
     * 每次被顶下线时触发
     */
    override fun doReplaced(loginType: String, loginId: Any, tokenValue: String) {
        RedisUtils.deleteObject(CacheConstants.ONLINE_TOKEN_KEY + tokenValue)
        log.info("user doReplaced, userId:{}, token:{}", loginId, tokenValue)
    }

    /**
     * 每次被封禁时触发
     */
    override fun doDisable(loginType: String, loginId: Any, service: String, level: Int, disableTime: Long) {
    }

    /**
     * 每次被解封时触发
     */
    override fun doUntieDisable(loginType: String, loginId: Any, service: String) {
    }

    /**
     * 每次打开二级认证时触发
     */
    override fun doOpenSafe(loginType: String, tokenValue: String, service: String, safeTime: Long) {
    }

    /**
     * 每次创建Session时触发
     */
    override fun doCloseSafe(loginType: String, tokenValue: String, service: String) {
    }

    /**
     * 每次创建Session时触发
     */
    override fun doCreateSession(id: String) {
    }

    /**
     * 每次注销Session时触发
     */
    override fun doLogoutSession(id: String) {
    }

    /**
     * 每次Token续期时触发
     */
    override fun doRenewTimeout(tokenValue: String, loginId: Any, timeout: Long) {
    }
}
