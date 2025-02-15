package org.zipper.optional.translation.core.handler

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.BeanProperty
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.ContextualSerializer
import org.zipper.common.core.ext.invokeGetter
import org.zipper.optional.translation.annotation.Translation
import org.zipper.optional.translation.core.TranslationInterface
import java.io.IOException
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * 翻译处理器
 *
 * @author Lion Li
 */
class TranslationHandler : JsonSerializer<Any?>(), ContextualSerializer {
    private var translation: Translation? = null

    @Throws(IOException::class)
    override fun serialize(value: Any?, gen: JsonGenerator, serializers: SerializerProvider) {
        var obj = value
        val translationAnnotation = this.translation
        if (obj == null || translationAnnotation == null) {
            gen.writeNull()
            return
        }
        val trans = TRANSLATION_MAPPER[translationAnnotation.type]
        if (trans == null) {
            gen.writeObject(obj)
            return
        }
        if (translationAnnotation.mapper.isNotEmpty()) {
            obj = gen.currentValue().invokeGetter(translationAnnotation.mapper)
        }
        gen.writeObject(trans.translation(obj, translationAnnotation.other))
    }

    @Throws(JsonMappingException::class)
    override fun createContextual(prov: SerializerProvider, property: BeanProperty): JsonSerializer<*> {
        val translation = property.getAnnotation(
            Translation::class.java
        )
        if (Objects.nonNull(translation)) {
            this.translation = translation
            return this
        }
        return prov.findValueSerializer(property.type, property)
    }

    companion object {
        /**
         * 全局翻译实现类映射器
         */
        val TRANSLATION_MAPPER: MutableMap<String, TranslationInterface<*>> = ConcurrentHashMap()
    }
}
