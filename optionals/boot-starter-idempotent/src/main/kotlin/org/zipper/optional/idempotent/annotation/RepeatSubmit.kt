package org.zipper.optional.idempotent.annotation

import java.lang.annotation.Inherited
import java.util.concurrent.TimeUnit

/**
 * 自定义注解防止表单重复提交
 *
 * @author Lion Li
 */
@Inherited
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(
    AnnotationRetention.RUNTIME
)
@MustBeDocumented
annotation class RepeatSubmit(
    /**
     * 间隔时间(ms)，小于此时间视为重复提交
     */
    val interval: Int = 5000,
    val timeUnit: TimeUnit = TimeUnit.MILLISECONDS,
    /**
     * 提示消息 支持国际化 格式为 {code}
     */
    val message: String = "{repeat.submit.message}"
)
