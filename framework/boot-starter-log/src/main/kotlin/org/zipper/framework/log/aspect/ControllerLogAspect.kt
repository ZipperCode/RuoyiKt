package org.zipper.framework.log.aspect

import jakarta.servlet.http.HttpServletRequest
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import org.zipper.common.core.ext.log
import java.io.IOException


@Aspect
@AutoConfiguration
class ControllerLogAspect {
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    fun controllerPointcut() {
    }
    @Before("controllerPointcut()")
    fun logBefore(joinPoint: JoinPoint) {
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?)!!.request
        val url = request.requestURI
        val method = request.method
        val params = mutableMapOf<String, Any>()
        log.debug(">>>>>> Method: {} URL: {}", method, url)
        val signature = joinPoint.signature as MethodSignature
        val parameters = signature.parameterNames
        if (parameters.isNotEmpty()) {
            val args = joinPoint.args
            for (i in parameters.indices) {
                params[parameters[i]] = args[i]
            }
            return
        }
        log.debug(">>>>>> Params: {}", params)
    }

}