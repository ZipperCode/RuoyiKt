package org.zipper.common.core.ext

import io.github.linpeilie.Converter
import java.io.Serializable


object MapStructExt {
    private val converter: Converter? by springCacheByType<Converter>()
    fun requireConvertor(): Converter = MapStructExt.converter!!
    fun getConvertor(): Converter? = MapStructExt.converter

    /**
     * Bean对象转换为指定类型
     */
    inline fun <reified V> Serializable.convert(): V {
        return requireConvertor().convert(this, V::class.java)
    }

    /**
     * Bean对象转换为指定类型 可空
     */
    inline fun <reified V> Any?.convertOrNull(): V? {
        if (this == null) {
            return null
        }
        return try {
            getConvertor()?.convert(this, V::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    /**
     * 字段属性值转化为目标对象的属性值
     */
    inline fun <reified V> Any.convert(target: V): V {
        return try {
            getConvertor()?.convert(this, target) ?: target
        } catch (e: Exception) {
            e.printStackTrace()
            return target
        }
    }

    inline fun <reified V> List<Any>?.convertList(): List<V> {
        return try {
            getConvertor()?.convert(this, V::class.java) ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<V>()
        }
    }

    inline fun <reified E, reified V> List<E>?.convertTypeList(): List<V> {
        return try {
            getConvertor()?.convert(this, V::class.java) ?: emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList<V>()
        }
    }

    fun <T, V> Map<String, T>.mapConvert(clazz: Class<V>): V? {
        return try {
            getConvertor()?.convert(this, clazz)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
