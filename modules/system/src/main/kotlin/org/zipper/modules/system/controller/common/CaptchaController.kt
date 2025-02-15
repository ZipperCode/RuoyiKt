package org.zipper.modules.system.controller.common

import cn.dev33.satoken.annotation.SaIgnore
import cn.hutool.captcha.generator.CodeGenerator
import cn.hutool.core.util.IdUtil
import cn.hutool.core.util.ReflectUtil
import cn.hutool.extra.spring.SpringUtil
import org.apache.commons.lang3.StringUtils
import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.zipper.common.core.constant.Constants
import org.zipper.common.core.constant.GlobalConstants
import org.zipper.framework.redis.utils.RedisUtils
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.framework.web.enums.CaptchaType
import org.zipper.modules.system.config.properties.CaptchaProperties
import org.zipper.modules.system.domain.vo.CaptchaVo
import org.zipper.optional.ratelimiter.annotation.RateLimiter
import org.zipper.optional.ratelimiter.enums.LimitType
import java.time.Duration

/**
 * 验证码接口
 *
 */
@SaIgnore
@Validated
@RestController
class CaptchaController(
    private val captchaProperties: CaptchaProperties,
) {
    @GetMapping("/auth/code")
    @RateLimiter(time = 60, count = 10, limitType = LimitType.IP)
    @ResultBody
    fun getCode(): CaptchaVo {
        val captchaVo = CaptchaVo()
        val captchaEnabled = captchaProperties.enable
        if (!captchaEnabled) {
            captchaVo.captchaEnabled = false
            return captchaVo
        }
        // 保存验证码信息
        val uuid = IdUtil.simpleUUID()
        val verifyKey = GlobalConstants.CAPTCHA_CODE_KEY + uuid
        // 生成验证码
        val captchaType: CaptchaType = captchaProperties.type
        val isMath = CaptchaType.MATH === captchaType
        val length = if (isMath) captchaProperties.numberLength else captchaProperties.charLength
        val codeGenerator: CodeGenerator = ReflectUtil.newInstance(captchaType.clazz, length)
        val captcha = SpringUtil.getBean(captchaProperties.category.clazz)
        captcha.generator = codeGenerator
        captcha.createCode()
        var code = captcha.code
        if (isMath) {
            val parser: ExpressionParser = SpelExpressionParser()
            val exp = parser.parseExpression(StringUtils.remove(code, "="))
            code = exp.getValue(String::class.java)
        }
        RedisUtils.setCacheObject(verifyKey, code, Duration.ofMinutes(Constants.CAPTCHA_EXPIRATION.toLong()))
        captchaVo.uuid = uuid
        captchaVo.img = captcha.imageBase64
        return captchaVo
    }
}
