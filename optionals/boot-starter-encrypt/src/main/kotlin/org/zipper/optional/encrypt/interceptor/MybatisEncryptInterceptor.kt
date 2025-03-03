package org.zipper.optional.encrypt.interceptor

import cn.hutool.core.collection.CollUtil
import cn.hutool.core.convert.Convert
import cn.hutool.core.util.ObjectUtil
import org.apache.ibatis.executor.parameter.ParameterHandler
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.plugin.Intercepts
import org.apache.ibatis.plugin.Invocation
import org.apache.ibatis.plugin.Signature
import org.zipper.common.core.ext.log
import org.zipper.optional.encrypt.config.properties.EncryptorProperties
import org.zipper.optional.encrypt.core.EncryptorManager
import java.lang.reflect.Field
import java.sql.PreparedStatement
import java.util.*

/**
 * 入参加密拦截器
 *
 * @author 老马
 * @version 4.6.0
 */
@Intercepts(Signature(type = ParameterHandler::class, method = "setParameters", args = [PreparedStatement::class]))
class MybatisEncryptInterceptor(
    private val encryptorManager: EncryptorManager,
    private val defaultProperties: EncryptorProperties,
) : Interceptor {

    override fun intercept(invocation: Invocation): Any = invocation

    override fun plugin(target: Any): Any {
        if (target is ParameterHandler) {
            // 进行加密操作
            val parameterObject: Any = target.parameterObject
            if (ObjectUtil.isNotNull(parameterObject) && parameterObject !is String) {
                this.encryptHandler(parameterObject)
            }
        }
        return target
    }

    /**
     * 加密对象
     *
     * @param sourceObject 待加密对象
     */
    private fun encryptHandler(sourceObject: Any?) {
        sourceObject ?: return
        if (sourceObject is Map<*, *>) {
            HashSet(sourceObject.values).forEach(this::encryptHandler)
            return
        }
        if (sourceObject is List<*>) {
            // 判断第一个元素是否含有注解。如果没有直接返回，提高效率
            val firstItem = sourceObject.firstOrNull() ?: return
            if (CollUtil.isEmpty(encryptorManager.getFieldCache(firstItem.javaClass))) {
                return
            }
            sourceObject.forEach(this::encryptHandler)
            return
        }
        val fields = encryptorManager.getFieldCache(sourceObject.javaClass)
        try {
            for (field in fields) {
                field[sourceObject] = encryptField(Convert.toStr(field[sourceObject]), field)
            }
        } catch (e: Exception) {
            log.error("处理加密字段时出错", e)
        }
    }

    /**
     * 字段值进行加密。通过字段的批注注册新的加密算法
     *
     * @param value 待加密的值
     * @param field 待加密字段
     * @return 加密后结果
     */
    private fun encryptField(value: String, field: Field): String? {
        if (ObjectUtil.isNull(value)) {
            return null
        }
        val encryptField = field.getAnnotation(org.zipper.optional.encrypt.annotation.EncryptField::class.java)
        val encryptContext = org.zipper.optional.encrypt.core.EncryptContext(encryptField, defaultProperties)
        return encryptorManager.encrypt(value, encryptContext)
    }


    override fun setProperties(properties: Properties) {
    }
}
