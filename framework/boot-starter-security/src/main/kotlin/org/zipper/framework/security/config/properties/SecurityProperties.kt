package org.zipper.framework.security.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Security 配置属性
 *
 *
 * @author Lion Li
 */
@ConfigurationProperties(prefix = "security")
class SecurityProperties {
    /**
     * 排除路径
     */
    var excludes: Array<String> = emptyArray()
}
