package org.zipper.framework.core.xss

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

/**
 * 自定义xss校验注解
 *
 * @author Lion Li
 */
@Retention(AnnotationRetention.RUNTIME)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.VALUE_PARAMETER
)
@Constraint(validatedBy = [XssValidator::class])
annotation class Xss(
    val message: String = "不允许任何脚本运行",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
