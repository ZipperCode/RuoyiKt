package org.zipper.optional.encrypt.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.zipper.optional.encrypt.config.properties.EncryptorProperties
import org.zipper.optional.encrypt.core.EncryptorManager
import org.zipper.optional.encrypt.interceptor.MybatisDecryptInterceptor
import org.zipper.optional.encrypt.interceptor.MybatisEncryptInterceptor

/**
 * 加解密配置
 *
 * @author 老马
 * @version 4.6.0
 */
@AutoConfiguration
@EnableConfigurationProperties(EncryptorProperties::class)
@ConditionalOnProperty(value = ["mybatis-encryptor.enable"], havingValue = "true")
class EncryptorAutoConfiguration {
    @Autowired
    private lateinit var properties: EncryptorProperties

    @Bean
    fun encryptorManager(): EncryptorManager {
        return EncryptorManager()
    }

    @Bean
    fun mybatisEncryptInterceptor(encryptorManager: EncryptorManager): MybatisEncryptInterceptor {
        return MybatisEncryptInterceptor(encryptorManager, properties)
    }

    @Bean
    fun mybatisDecryptInterceptor(encryptorManager: EncryptorManager): MybatisDecryptInterceptor {
        return MybatisDecryptInterceptor(encryptorManager, properties)
    }
}
