package org.zipper.optional.idempotent.config

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConfiguration
import org.zipper.optional.idempotent.aspectj.RepeatSubmitAspect

/**
 * 幂等功能配置
 *
 */
@AutoConfiguration(after = [RedisConfiguration::class])
class IdempotentConfig {
    @Bean
    fun repeatSubmitAspect(): RepeatSubmitAspect {
        return RepeatSubmitAspect()
    }
}
