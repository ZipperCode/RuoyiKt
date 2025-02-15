package org.zipper.optional.encrypt.core.encryptor

import org.zipper.optional.encrypt.core.EncryptContext
import org.zipper.optional.encrypt.core.IEncryptor


/**
 * 所有加密执行者的基类
 *
 * @author 老马
 * @version 4.6.0
 */
abstract class AbstractEncryptor(protected val context: EncryptContext) : IEncryptor
