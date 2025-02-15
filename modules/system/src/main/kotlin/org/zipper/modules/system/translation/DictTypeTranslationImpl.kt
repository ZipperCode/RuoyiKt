package org.zipper.modules.system.translation

import org.apache.commons.lang3.StringUtils
import org.zipper.modules.system.service.dict.DictService
import org.zipper.optional.translation.annotation.TranslationType
import org.zipper.optional.translation.constant.TransConstant
import org.zipper.optional.translation.core.TranslationInterface

/**
 * 字典翻译实现
 *
 * @author Lion Li
 */
@TranslationType(type = TransConstant.DICT_TYPE_TO_LABEL)
class DictTypeTranslationImpl(
    private val dictService: DictService
) : TranslationInterface<String?> {


    override fun translation(key: Any?, other: String?): String? {
        if (key is String && StringUtils.isNotBlank(other)) {
            return dictService.getDictLabel(other!!, key)
        }
        return null
    }
}
