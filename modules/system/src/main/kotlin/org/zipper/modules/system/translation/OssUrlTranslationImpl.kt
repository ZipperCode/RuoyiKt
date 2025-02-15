package org.zipper.modules.system.translation

import org.zipper.common.core.framework.store.IStorageService
import org.zipper.optional.translation.annotation.TranslationType
import org.zipper.optional.translation.constant.TransConstant
import org.zipper.optional.translation.core.TranslationInterface

/**
 * OSS翻译实现
 *
 * @author Lion Li
 */
@TranslationType(type = TransConstant.OSS_ID_TO_URL)
class OssUrlTranslationImpl(
    private val storageService: IStorageService
) : TranslationInterface<String?> {

    override fun translation(key: Any?, other: String?): String? {
        if (key is Long) {
            return storageService.getFileUrlById(key)
        }
        return null
    }
}
