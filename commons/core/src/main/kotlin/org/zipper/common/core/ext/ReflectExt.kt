package org.zipper.common.core.ext

import cn.hutool.core.util.ReflectUtil.getMethodByName
import cn.hutool.core.util.ReflectUtil.invoke
import org.springframework.util.StringUtils

private const val GETTER_PREFIX = "get"
private const val SETTER_PREFIX = "set"

/**
 * 调用Getter方法.
 * 支持多级，如：对象名.对象名.方法
 */
fun <E> Any?.invokeGetter(propertyName: String): E? {
    return try {
        var target: Any? = this
        for (name in propertyName.split(".")) {
            val getterMethodName = GETTER_PREFIX + StringUtils.capitalize(name)
            target = invoke(target, getterMethodName)
        }
        target.anyForceCastOrNull()
    } catch (e: Exception) {
        null
    }
}

/**
 * 调用Setter方法, 仅匹配方法名。
 * 支持多级，如：对象名.对象名.方法
 */
fun <E> Any.invokeSetter(propertyName: String?, value: E) {
    var target = this
    val names = StringUtils.split(propertyName, ".") ?: return
    for (i in names.indices) {
        if (i < names.size - 1) {
            val getterMethodName = GETTER_PREFIX + StringUtils.capitalize(
                names[i]
            )
            target = invoke(target, getterMethodName)
        } else {
            val setterMethodName = SETTER_PREFIX + StringUtils.capitalize(
                names[i]
            )
            val method = getMethodByName(target.javaClass, setterMethodName)
            invoke<Any>(target, method, value)
        }
    }
}