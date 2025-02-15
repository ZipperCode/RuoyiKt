package org.zipper.modules.system.service.auth

import cn.dev33.satoken.exception.NotLoginException
import cn.dev33.satoken.secure.BCrypt
import cn.dev33.satoken.stp.SaLoginModel
import cn.dev33.satoken.stp.StpUtil
import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.ObjectUtil
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.zipper.common.core.constant.Constants
import org.zipper.common.core.constant.GlobalConstants
import org.zipper.common.core.constant.UserConstants
import org.zipper.common.core.domain.model.LoginUser
import org.zipper.common.core.domain.model.LoginUserRole
import org.zipper.common.core.enums.LoginType
import org.zipper.common.core.enums.UserStatus
import org.zipper.common.core.exception.MessageException
import org.zipper.common.core.exception.user.CaptchaException
import org.zipper.common.core.exception.user.CaptchaExpireException
import org.zipper.common.core.exception.user.UserException
import org.zipper.common.core.ext.log
import org.zipper.common.core.framework.MessageUtils
import org.zipper.framework.mybatis.core.MybatisKt
import org.zipper.framework.redis.utils.RedisUtils
import org.zipper.framework.security.utils.LoginHelper
import org.zipper.modules.system.config.properties.CaptchaProperties
import org.zipper.modules.system.constant.UserMessageConst
import org.zipper.modules.system.domain.entity.SysUserEntity
import org.zipper.modules.system.domain.param.PasswordLoginParam
import org.zipper.modules.system.domain.vo.LoginVo
import org.zipper.modules.system.domain.vo.SysUserVo
import org.zipper.modules.system.helper.LoginLogEventHelper
import org.zipper.modules.system.mapper.SysUserMapper
import org.zipper.modules.system.service.client.ISysClientService
import org.zipper.modules.system.service.permission.ISysPermissionService
import java.time.Duration

@Service
class AuthService(
    private val captchaProperties: CaptchaProperties,
    private var userMapper: SysUserMapper,
    private var permissionService: ISysPermissionService,
    private val clientService: ISysClientService,
) : IAuthService {
    /**
     * 最大重试次数
     */
    @Value("\${user.password.maxRetryCount}")
    private var maxRetryCount: Int = 5

    /**
     * 锁定时长
     */
    @Value("\${user.password.lockTime}")
    private var lockTime: Int = 10

    override fun login(param: PasswordLoginParam): LoginVo {
        if (captchaProperties.enable) {
            validateCaptcha(param.username, param.code, param.uuid)
        }
        val clientId = param.clientId
        val grantType = param.grantType
        val client = clientService.queryByClientId(param.clientId)
        if (client == null || !StringUtils.contains(client.grantType, grantType)) {
            log.info("客户端id: {} 认证类型：{} 异常!.", clientId, grantType)
            throw UserMessageConst.AuthGrantError
        }
        if (UserConstants.NORMAL != client.status) {
            throw UserMessageConst.AuthBlockedError
        }

        val user = userMapper.selectOne(
            MybatisKt.ktQuery<SysUserEntity>()
                .select(SysUserEntity::userName, SysUserEntity::status)
                .eq(SysUserEntity::userName, param.username)
        )
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在.", param.username)
            throw MessageException("user.not.exists", param.username)
        } else if (UserStatus.DISABLE.code == user.status) {
            log.info("登录用户：{} 已被停用.", param.username)
            throw MessageException("user.blocked", param.username)
        }
        if (user.delFlag == UserStatus.DELETED.code) {
            throw MessageException("user.not.exists", param.username)
        }
        val userVo = userMapper.selectUserByUserName(user.userName)
        if (userVo == null) {
            log.info("登录用户：{} 不存在.", param.username)
            throw MessageException("user.not.exists", param.username)
        }
        loginCheck(param, userVo)
        // 此处可根据登录用户的数据不同 自行创建 loginUser
        val loginUser = buildLoginUser(userVo)
        loginUser.clientKey = client.clientKey
        loginUser.deviceType = client.deviceType
        val model = SaLoginModel()
        model.setDevice(client.deviceType)
        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
        // 例如: 后台用户30分钟过期 app用户1天过期
        model.setTimeout(client.timeout!!)
        model.setActiveTimeout(client.activeTimeout!!)
        model.setExtra(LoginHelper.CLIENT_KEY, client.clientId)
        // 生成token
        LoginHelper.login(loginUser, model)

        val loginVo = LoginVo()
        loginVo.accessToken = StpUtil.getTokenValue()
        loginVo.expireIn = StpUtil.getTokenTimeout()
        loginVo.clientId = client.clientId
        return loginVo
    }

    override fun logout() {
        try {
            val loginUser = LoginHelper.getLoginUser() ?: return
            LoginLogEventHelper.postRecord(loginUser.username, Constants.LOGOUT, MessageUtils.message("user.logout.success"))
        } catch (ignored: NotLoginException) {
        } finally {
            try {
                StpUtil.logout()
            } catch (ignored: NotLoginException) {
            }
        }
    }

    private fun loginCheck(param: PasswordLoginParam, userInfo: SysUserVo) {
        val passwordParam = param.password
        val username = userInfo.userName
        val errorKey = GlobalConstants.PWD_ERR_CNT_KEY + username
        val loginFail = Constants.LOGIN_FAIL

        // 获取用户登录错误次数，默认为0 (可自定义限制策略 例如: key + username + ip)
        var errorNumber: Int = ObjectUtil.defaultIfNull(RedisUtils.getCacheObject(errorKey), 0)
        val loginType = LoginType.PASSWORD
        // 锁定时间内登录 则踢出
        if (errorNumber >= maxRetryCount) {
            LoginLogEventHelper.postRecord(
                username,
                loginFail,
                MessageUtils.message(loginType.retryLimitExceed, maxRetryCount, lockTime)
            )
            throw UserException(loginType.retryLimitExceed, maxRetryCount, lockTime)
        }

        if (!BCrypt.checkpw(passwordParam, userInfo.password)) {
            val messageCode = loginType.retryLimitExceed
            // 错误次数递增
            errorNumber++
            RedisUtils.setCacheObject(errorKey, errorNumber, Duration.ofMinutes(lockTime.toLong()))
            // 达到规定错误次数 则锁定登录
            if (errorNumber >= maxRetryCount) {
                LoginLogEventHelper.postRecord(
                    username, loginFail, MessageUtils.message(messageCode, maxRetryCount, lockTime)
                )
                throw UserException(messageCode, maxRetryCount, lockTime)
            } else {
                // 未达到规定错误次数
                LoginLogEventHelper.postRecord(
                    username, loginFail, MessageUtils.message(messageCode, errorNumber)
                )
                throw UserException(loginType.retryLimitCount, errorNumber)
            }
        }


        // 登录成功 清空错误次数
        RedisUtils.deleteObject(errorKey)
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     */
    private fun validateCaptcha(username: String, code: String?, uuid: String?) {
        if (code.isNullOrEmpty() || uuid.isNullOrEmpty()) {
            LoginLogEventHelper.postRecord(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.not.exists"))
            throw CaptchaException()
        }
        val verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + uuid.ifEmpty { "" }
        val captcha = RedisUtils.getCacheObject<String>(verifyKey)
        RedisUtils.deleteObject(verifyKey)
        if (captcha == null) {
            LoginLogEventHelper.postRecord(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire"))
            throw CaptchaExpireException()
        }
        if (!code.equals(captcha, ignoreCase = true)) {
            LoginLogEventHelper.postRecord(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error"))
            throw CaptchaException()
        }
    }

    private fun buildLoginUser(user: SysUserVo): LoginUser {
        val loginUser = LoginUser()
        loginUser.tenantId = user.tenantId
        loginUser.userId = user.userId
        loginUser.deptId = user.deptId
        loginUser.username = user.userName
        loginUser.nickname = user.nickName
        loginUser.userType = user.userType
        loginUser.menuPermission = permissionService.getMenuPermission(user.userId)
        loginUser.rolePermission = permissionService.getRolePermission(user.userId)
        loginUser.deptName = if (ObjectUtil.isNull(user.dept)) "" else user.dept!!.deptName
        val roles = BeanUtil.copyToList(user.roles, LoginUserRole::class.java)
        loginUser.roles = roles
        return loginUser
    }
}