package org.zipper.optional.encrypt.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * api解密属性配置类
 * @author wdhcr
 */
@ConfigurationProperties(prefix = "api-decrypt")
class ApiDecryptProperties() {
    /**
     * 加密开关
     */
    var enabled: Boolean = false

    /**
     * 头部标识
     */
    var headerFlag: String = ""

    /**
     * 响应加密公钥
     */
    var publicKey: String = ""

    /**
     * 请求解密私钥
     */
    var privateKey: String = ""
}
