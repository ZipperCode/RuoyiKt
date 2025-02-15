package org.zipper.framework.security.aspect

import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice
import org.zipper.common.core.domain.R

/**
 * 注解返回值处理， 返回值包装成R<T>
 */
@RestControllerAdvice
class ResponseResultAdvice : ResponseBodyAdvice<Any> {
    override fun supports(returnType: MethodParameter, converterType: Class<out HttpMessageConverter<*>>): Boolean {
        return returnType.getMethodAnnotation(ResultBody::class.java) != null
    }

    override fun beforeBodyWrite(
        body: Any?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Any? {
        if (body is Unit) {
            return R.ok<Void>()
        }
        if (body is R<*>) {
            return body
        }
        if (body is String) {
            return body
        }
        return R.ok(body)
    }
}