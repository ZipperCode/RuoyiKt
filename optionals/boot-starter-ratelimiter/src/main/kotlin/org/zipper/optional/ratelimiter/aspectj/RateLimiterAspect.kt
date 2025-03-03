package org.zipper.optional.ratelimiter.aspectj

import cn.hutool.core.util.ArrayUtil
import org.apache.commons.lang3.StringUtils
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RateType
import org.springframework.core.DefaultParameterNameDiscoverer
import org.springframework.core.ParameterNameDiscoverer
import org.springframework.expression.EvaluationContext
import org.springframework.expression.ExpressionParser
import org.springframework.expression.ParserContext
import org.springframework.expression.common.TemplateParserContext
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext
import org.zipper.common.core.constant.GlobalConstants
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.getClientIp
import org.zipper.common.core.ext.getRequest
import org.zipper.common.core.ext.log
import org.zipper.common.core.framework.MessageUtils
import org.zipper.framework.redis.utils.RedisUtils
import org.zipper.optional.ratelimiter.annotation.RateLimiter
import org.zipper.optional.ratelimiter.enums.LimitType

/**
 * 限流处理
 *
 * @author Lion Li
 */
@Aspect
class RateLimiterAspect {
    /**
     * 定义spel表达式解析器
     */
    private val parser: ExpressionParser = SpelExpressionParser()

    /**
     * 定义spel解析模版
     */
    private val parserContext: ParserContext = TemplateParserContext()

    /**
     * 定义spel上下文对象进行解析
     */
    private val context: EvaluationContext = StandardEvaluationContext()

    /**
     * 方法参数解析器
     */
    private val pnd: ParameterNameDiscoverer = DefaultParameterNameDiscoverer()

    @Before("@annotation(rateLimiter)")
    @Throws(Throwable::class)
    fun doBefore(point: JoinPoint, rateLimiter: RateLimiter) {
        val time = rateLimiter.time
        val count = rateLimiter.count
        val combineKey = getCombineKey(rateLimiter, point)
        try {
            var rateType = RateType.OVERALL
            if (rateLimiter.limitType == LimitType.CLUSTER) {
                rateType = RateType.PER_CLIENT
            }
            val number = RedisUtils.rateLimiter(combineKey, rateType, count, time)
            if (number == -1L) {
                var message = rateLimiter.message
                if (StringUtils.startsWith(message, "{") && StringUtils.endsWith(message, "}")) {
                    message = MessageUtils.message(StringUtils.substring(message, 1, message.length - 1))
                }
                throw ServiceException(message)
            }
            log.info("限制令牌 => {}, 剩余令牌 => {}, 缓存key => '{}'", count, number, combineKey)
        } catch (e: Exception) {
            if (e is ServiceException) {
                throw e
            } else {
                throw RuntimeException("服务器限流异常，请稍候再试")
            }
        }
    }

    fun getCombineKey(rateLimiter: RateLimiter, point: JoinPoint): String {
        var key = rateLimiter.key
        // 获取方法(通过方法签名来获取)
        val signature = point.signature as MethodSignature
        val method = signature.method
        val targetClass = method.declaringClass
        // 判断是否是spel格式
        if (StringUtils.containsAny(key, "#")) {
            // 获取参数值
            val args = point.args
            // 获取方法上参数的名称
            val parameterNames = pnd.getParameterNames(method)
            if (ArrayUtil.isEmpty(parameterNames)) {
                throw ServiceException("限流key解析异常!请联系管理员!")
            }
            for (i in parameterNames!!.indices) {
                context.setVariable(parameterNames[i], args[i])
            }
            // 解析返回给key
            try {
                val expression = if (StringUtils.startsWith(key, parserContext.expressionPrefix)
                    && StringUtils.endsWith(key, parserContext.expressionSuffix)
                ) {
                    parser.parseExpression(key, parserContext)
                } else {
                    parser.parseExpression(key)
                }
                key = "${expression.getValue(context, String::class.java)}:"
            } catch (e: Exception) {
                throw ServiceException("限流key解析异常!请联系管理员!")
            }
        }
        val stringBuffer: StringBuilder = StringBuilder(GlobalConstants.RATE_LIMIT_KEY)
        stringBuffer.append(Thread.currentThread().getRequest()?.requestURI).append(":")
        if (rateLimiter.limitType == LimitType.IP) {
            // 获取请求ip
            stringBuffer.append(Thread.currentThread().getRequest()?.getClientIp()).append(":")
        } else if (rateLimiter.limitType == LimitType.CLUSTER) {
            // 获取客户端实例id
            stringBuffer.append(RedisUtils.client.id).append(":")
        }
        return stringBuffer.append(key).toString()
    }
}
