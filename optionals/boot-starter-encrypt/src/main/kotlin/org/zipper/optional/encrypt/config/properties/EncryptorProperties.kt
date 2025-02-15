package org.zipper.optional.encrypt.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.zipper.optional.encrypt.enums.AlgorithmType
import org.zipper.optional.encrypt.enums.EncodeType

/**
 * 加解密属性配置类
 *
 * @author 老马
 * @version 4.6.0
 */
@ConfigurationProperties(prefix = "mybatis-encryptor")
class EncryptorProperties {
    /**
     * 过滤开关
     */
    var enable: Boolean = false

    /**
     * 默认算法
     */
    var algorithm: AlgorithmType = AlgorithmType.DEFAULT

    /**
     * 安全秘钥
     */
    var password: String = ""

    /**
     * 公钥
     */
    var publicKey: String = ""

    /**
     * 私钥
     */
    var privateKey: String = ""

    /**
     * 编码方式，base64/hex
     */
    var encode: EncodeType = EncodeType.DEFAULT
}
