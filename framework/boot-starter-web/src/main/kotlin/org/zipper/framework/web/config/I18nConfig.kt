package org.zipper.framework.web.config

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.LocaleResolver
import org.zipper.framework.web.core.I18nLocaleResolver

/**
 * 国际化配置
 *
 * @author Lion Li
 */
@AutoConfiguration(before = [WebMvcAutoConfiguration::class])
class I18nConfig {
    @Bean
    fun localeResolver(): LocaleResolver {
        return I18nLocaleResolver()
    }
}
