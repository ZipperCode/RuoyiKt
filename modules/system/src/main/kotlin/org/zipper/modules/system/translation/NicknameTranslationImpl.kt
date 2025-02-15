package org.zipper.modules.system.translation

import org.zipper.modules.system.service.user.UserService
import org.zipper.optional.translation.annotation.TranslationType
import org.zipper.optional.translation.constant.TransConstant
import org.zipper.optional.translation.core.TranslationInterface

/**
 * 用户名称翻译实现
 *
 * @author may
 */
@TranslationType(type = TransConstant.USER_ID_TO_NICKNAME)
class NicknameTranslationImpl(
    private val userService: UserService
) : TranslationInterface<String?> {

    override fun translation(key: Any?, other: String?): String? {
        if (key is Long) {
            return userService.selectNicknameById(key)!!
        }
        return null
    }
}
