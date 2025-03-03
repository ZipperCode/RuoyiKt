package org.zipper.framework.starter.json.config

import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import org.zipper.framework.starter.json.handler.BigNumberSerializer
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.multipart.MultipartFile
import org.zipper.common.core.ext.log
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * jackson 配置
 *
 */
@AutoConfiguration(before = [JacksonAutoConfiguration::class])
class JacksonConfig {
    @Bean
    fun customizer(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder: Jackson2ObjectMapperBuilder ->
            // 全局配置序列化返回 JSON 处理
            val javaTimeModule = JavaTimeModule()
            javaTimeModule.addSerializer(Long::class.java, BigNumberSerializer.INSTANCE)
            javaTimeModule.addSerializer(java.lang.Long.TYPE, BigNumberSerializer.INSTANCE)
            javaTimeModule.addSerializer(java.lang.Long::class.java, BigNumberSerializer.INSTANCE)
            javaTimeModule.addSerializer(BigInteger::class.java, BigNumberSerializer.INSTANCE)
            javaTimeModule.addSerializer(BigDecimal::class.java, ToStringSerializer.instance)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            javaTimeModule.addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(formatter))
            javaTimeModule.addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(formatter))
            javaTimeModule.addSerializer(MultipartFile::class.java, ToStringSerializer.instance)
            builder.modules(javaTimeModule)
            builder.timeZone(TimeZone.getDefault())
            log.info("初始化 jackson 配置")
        }
    }
}
