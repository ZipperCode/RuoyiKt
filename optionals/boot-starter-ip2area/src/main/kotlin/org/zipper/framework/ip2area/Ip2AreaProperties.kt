package org.zipper.framework.ip2area

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "ip2area")
class Ip2AreaProperties(
    val enable: Boolean = false
)