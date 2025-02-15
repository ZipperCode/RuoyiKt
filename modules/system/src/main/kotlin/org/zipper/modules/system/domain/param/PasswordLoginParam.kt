package org.zipper.modules.system.domain.param

import jakarta.validation.constraints.NotBlank
import org.hibernate.validator.constraints.Length
import org.zipper.common.core.constant.UserConstants
import java.io.Serializable

/**
 * 密码登录 dto
 */
class PasswordLoginParam : Serializable {

    /**
     * 用户名
     */
    @field:NotBlank(message = "{user.username.not.blank}")
    @field:Length(
        min = UserConstants.USERNAME_MIN_LENGTH,
        max = UserConstants.USERNAME_MAX_LENGTH,
        message = "{user.username.length.valid}"
    )
    lateinit var username: String

    /**
     * 用户密码
     */
    @field:NotBlank(message = "{user.password.not.blank}")
    @field:Length(
        min = UserConstants.PASSWORD_MIN_LENGTH,
        max = UserConstants.PASSWORD_MAX_LENGTH,
        message = "{user.password.length.valid}"
    )
    lateinit var password: String

    /**
     * 客户端id
     */
    @field:NotBlank(message = "{auth.clientid.not.blank}")
    lateinit var clientId: String

    /**
     * 授权类型
     */
    @field:NotBlank(message = "{auth.grant.type.not.blank}")
    var grantType: String = ""

    /**
     * 验证码id
     */
    var uuid: String? = null

    /**
     * 验证码
     */
    var code: String? = null
}