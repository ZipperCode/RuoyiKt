package org.zipper.optional.translation.config

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import jakarta.annotation.Resource
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.zipper.common.core.ext.log
import org.zipper.optional.translation.annotation.TranslationType
import org.zipper.optional.translation.core.TranslationInterface
import org.zipper.optional.translation.core.handler.TranslationBeanSerializerModifier
import org.zipper.optional.translation.core.handler.TranslationHandler

/**
 * 翻译模块配置类
 *
 * @author Lion Li
 */
@AutoConfiguration
class TranslationConfig {
    @Resource
    private lateinit var list: List<TranslationInterface<*>>

    @Resource
    private lateinit var objectMapper: ObjectMapper

    @PostConstruct
    fun init() {
        val map = HashMap<String, TranslationInterface<*>>(list.size)
        for (trans in list) {
            if (trans.javaClass.isAnnotationPresent(TranslationType::class.java)) {
                val annotation = trans.javaClass.getAnnotation(TranslationType::class.java)
                map[annotation.type] = trans
            } else {
                log.warn(trans.javaClass.name + " 翻译实现类未标注 TranslationType 注解!")
            }
        }
        TranslationHandler.TRANSLATION_MAPPER.putAll(map)
        // 设置 Bean 序列化修改器
        objectMapper.setSerializerFactory(
            objectMapper.serializerFactory
                .withSerializerModifier(TranslationBeanSerializerModifier())
        )
    }
}
