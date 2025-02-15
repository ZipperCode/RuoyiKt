package org.zipper.optional.encrypt.core.encryptor

import org.zipper.optional.encrypt.core.EncryptContext
import org.zipper.optional.encrypt.enums.AlgorithmType
import org.zipper.optional.encrypt.enums.EncodeType
import org.zipper.optional.encrypt.utils.EncryptUtils


/**
 * Base64算法实现
 *
 * @author 老马
 * @version 4.6.0
 */
class Base64Encryptor(context: EncryptContext) : AbstractEncryptor(context) {
    /**
     * 获得当前算法
     */
    override fun algorithm(): AlgorithmType {
        return AlgorithmType.BASE64
    }

    /**
     * 加密
     *
     * @param value      待加密字符串
     * @param encodeType 加密后的编码格式
     */
    override fun encrypt(value: String?, encodeType: EncodeType): String {
        return EncryptUtils.encryptByBase64(value)
    }

    /**
     * 解密
     *
     * @param value      待加密字符串
     */
    override fun decrypt(value: String?): String? {
        return EncryptUtils.decryptByBase64(value)
    }
}
