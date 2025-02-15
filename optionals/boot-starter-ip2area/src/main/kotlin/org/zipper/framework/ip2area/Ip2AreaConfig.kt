package org.zipper.framework.ip2area

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.zipper.common.core.framework.ip2area.Ip2AreaStarter

@EnableConfigurationProperties(Ip2AreaProperties::class)
@AutoConfiguration
@ConditionalOnProperty(value = ["ip2area.enable"], havingValue = "true")
open class Ip2AreaConfig {

    @Bean
    open fun ip2AreaStarter(): Ip2AreaStarter {
        return Ip2AreaStarterImpl()
    }
}