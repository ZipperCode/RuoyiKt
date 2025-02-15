package org.zipper.common.core.ext

import cn.hutool.core.bean.BeanException
import cn.hutool.extra.servlet.JakartaServletUtil
import cn.hutool.extra.spring.SpringUtil
import cn.hutool.extra.spring.SpringUtil.getBeanFactory
import jakarta.servlet.http.HttpServletRequest
import org.springframework.aop.framework.AopContext
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.web.context.request.RequestAttributes
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

internal class SpringDelegateByTypeImpl<T>(
    private val type: Class<T>
) : ReadOnlyProperty<Any, T?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return try {
            SpringUtil.getBean<T>(type)
        } catch (e: BeanException) {
            e.printStackTrace()
            null
        }
    }
}

internal class SpringDelegateByNameImpl<T>(
    private val name: String
) : ReadOnlyProperty<Any, T?> {
    override fun getValue(thisRef: Any, property: KProperty<*>): T? {
        return try {
            SpringUtil.getBean<T>(name)
        } catch (e: BeanException) {
            e.printStackTrace()
            null
        }
    }
}

fun <T> createSpringDelegateByName(name: String): ReadOnlyProperty<Any, T?> {
    return SpringDelegateByNameImpl(name)
}

fun <T> createSpringDelegateByType(type: Class<T>): ReadOnlyProperty<Any, T?> {
    return SpringDelegateByTypeImpl(type)
}

inline fun <reified T> springCacheByType(): ReadOnlyProperty<Any, T?> {
    var bean: T? = null
    var isInit = false
    return ReadOnlyProperty<Any, T?> { _, _ ->
        try {
            if (isInit) {
                return@ReadOnlyProperty bean
            }
            if (bean == null) {
                bean = SpringUtil.getBean<T>(T::class.java)
                isInit = true
            }
            return@ReadOnlyProperty bean
        } catch (e: Throwable) {
            e.printStackTrace()
            isInit = true
            return@ReadOnlyProperty null
        }
    }
}

inline fun <reified T> springByType(): ReadOnlyProperty<Any, T?> {
    return ReadOnlyProperty<Any, T?> { _, _ ->
        return@ReadOnlyProperty try {
            SpringUtil.getBean<T>(T::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

inline fun <reified T> springByName(name: String): ReadOnlyProperty<Any, T?> {
    return ReadOnlyProperty<Any, T?> { _, _ ->
        return@ReadOnlyProperty try {
            SpringUtil.getBean<T>(name)
        } catch (e: BeanException) {
            e.printStackTrace()
            null
        }
    }
}

/**
 * 获取aop代理对象
 */
inline fun <reified T> T.aopProxy(): T {
    return AopContext.currentProxy() as T
}

inline fun <reified T> getBeanByName(name: String): T {
    return getBeanFactory().getBean(name) as T
}

inline fun <reified T> getBeanByNameOrNull(name: String): T? {
    return try {
        getBeanFactory().getBean(name) as T
    } catch (e: BeansException) {
        null
    }
}

inline fun <reified T> getBeanByType(clazz: Class<out T>): T {
    return getBeanFactory().getBean(clazz)
}

inline fun <reified T> getBeanByTypeOrNull(clazz: Class<out T>): T? {
    return try {
        getBeanFactory().getBean(clazz)
    } catch (e: Exception) {
        null
    }
}

fun Thread.getRequestAttributes(): ServletRequestAttributes? {
    return try {
        RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes
    } catch (e: Exception) {
        null
    }
}

fun Thread.getRequest(): HttpServletRequest? = getRequestAttributes()?.request

fun HttpServletRequest.getClientIp() = JakartaServletUtil.getClientIP(this)

fun RequestAttributes.getRequest(): HttpServletRequest? {
    return try {
        (RequestContextHolder.getRequestAttributes() as? ServletRequestAttributes)?.request
    } catch (e: Exception) {
        null
    }
}

fun ServletRequestAttributes?.getRequestParam(key: String?): String? {
    return this?.request?.getParameter(key)
}

inline fun <reified T> T.selfAopProxy(): T {
    return AopContext.currentProxy() as T
}

fun currentSpringContext(): ApplicationContext {
    return SpringUtil.getApplicationContext()
}