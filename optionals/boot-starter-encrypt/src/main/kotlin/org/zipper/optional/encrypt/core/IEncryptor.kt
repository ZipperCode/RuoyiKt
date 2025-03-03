package org.zipper.optional.encrypt.core

import org.zipper.optional.encrypt.enums.AlgorithmType
import org.zipper.optional.encrypt.enums.EncodeType


/**
 * 加解者
 *
 * @author 老马
 * @version 4.6.0
 */
interface IEncryptor {
    /**
     * 获得当前算法
     */
    fun algorithm(): AlgorithmType

    /**
     * 加密
     *
     * @param value      待加密字符串
     * @param encodeType 加密后的编码格式
     * @return 加密后的字符串
     */
    fun encrypt(value: String?, encodeType: EncodeType): String?

    /**
     * 解密
     *
     * @param value      待加密字符串
     * @return 解密后的字符串
     */
    fun decrypt(value: String?): String?
}
