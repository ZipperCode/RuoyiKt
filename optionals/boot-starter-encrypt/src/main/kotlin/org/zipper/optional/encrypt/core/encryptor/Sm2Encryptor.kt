package org.zipper.optional.encrypt.core.encryptor

import org.apache.commons.lang3.StringUtils
import org.zipper.optional.encrypt.core.EncryptContext
import org.zipper.optional.encrypt.enums.AlgorithmType
import org.zipper.optional.encrypt.enums.EncodeType
import org.zipper.optional.encrypt.utils.EncryptUtils


/**
 * sm2算法实现
 *
 * @author 老马
 * @version 4.6.0
 */
class Sm2Encryptor(context: EncryptContext) : AbstractEncryptor(context) {

    init {
        val privateKey: String = context.privateKey
        val publicKey: String = context.publicKey
        require(!StringUtils.isAnyEmpty(privateKey, publicKey)) { "SM2公私钥均需要提供，公钥加密，私钥解密。" }
    }

    /**
     * 获得当前算法
     */
    override fun algorithm(): AlgorithmType {
        return AlgorithmType.SM2
    }

    /**
     * 加密
     *
     * @param value      待加密字符串
     * @param encodeType 加密后的编码格式
     */
    override fun encrypt(value: String?, encodeType: EncodeType): String {
        return if (encodeType === EncodeType.HEX) {
            EncryptUtils.encryptBySm2Hex(value, context.publicKey)
        } else {
            EncryptUtils.encryptBySm2(value, context.publicKey)
        }
    }

    /**
     * 解密
     *
     * @param value      待加密字符串
     */
    override fun decrypt(value: String?): String? {
        return EncryptUtils.decryptBySm2(value, context.privateKey)
    }
}
