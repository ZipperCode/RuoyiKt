package org.zipper.modules.system.controller.common

import cn.dev33.satoken.annotation.SaIgnore
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.zipper.framework.log.annotation.Log
import org.zipper.framework.log.enums.BusinessType
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.modules.system.domain.param.PasswordLoginParam
import org.zipper.modules.system.domain.vo.LoginVo
import org.zipper.modules.system.service.auth.IAuthService
import org.zipper.optional.encrypt.annotation.ApiEncrypt
import org.zipper.optional.idempotent.annotation.RepeatSubmit

/**
 * 授权接口
 * 用于登录注销
 */
@SaIgnore
@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: IAuthService
) {

    @ApiEncrypt
    @RepeatSubmit
    @PostMapping("/login")
    @Log(title = "登录日志", businessType = BusinessType.GRANT)
    @ResultBody
    fun login(@Validated @RequestBody param: PasswordLoginParam): LoginVo {
        return authService.login(param)
    }

    /**
     * 退出登录
     */
    @PostMapping("/logout")
    @ResultBody
    fun logout(): String {
        authService.logout()
        return "退出成功"
    }

}