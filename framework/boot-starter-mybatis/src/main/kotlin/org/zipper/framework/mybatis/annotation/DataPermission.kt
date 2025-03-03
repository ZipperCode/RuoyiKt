package org.zipper.framework.mybatis.annotation

/**
 * 数据权限组
 *
 * @author Lion Li
 * @version 3.5.0
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS)
@Retention(
    AnnotationRetention.RUNTIME
)
@MustBeDocumented
annotation class DataPermission(vararg val value: org.zipper.framework.mybatis.annotation.DataColumn)
