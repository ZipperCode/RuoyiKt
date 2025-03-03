package org.zipper.optional.encrypt.enums

import org.zipper.optional.encrypt.core.encryptor.*

/**
 * 算法名称
 *
 * @author 老马
 * @version 4.6.0
 */

enum class AlgorithmType(
    val clazz: Class<out AbstractEncryptor>?
) {
    /**
     * 默认走yml配置
     */
    DEFAULT(null),

    /**
     * base64
     */
    BASE64(Base64Encryptor::class.java),

    /**
     * aes
     */
    AES(AesEncryptor::class.java),

    /**
     * rsa
     */
    RSA(RsaEncryptor::class.java),

    /**
     * sm2
     */
    SM2(Sm2Encryptor::class.java),

    /**
     * sm4
     */
    SM4(Sm4Encryptor::class.java);

    fun isDefault() = this.clazz == null
}
