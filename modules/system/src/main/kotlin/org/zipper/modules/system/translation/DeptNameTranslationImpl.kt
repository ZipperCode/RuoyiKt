package org.zipper.modules.system.translation

import org.zipper.modules.system.service.dept.DeptService
import org.zipper.optional.translation.annotation.TranslationType
import org.zipper.optional.translation.constant.TransConstant
import org.zipper.optional.translation.core.TranslationInterface

/**
 * 部门翻译实现
 *
 * @author Lion Li
 */
@TranslationType(type = TransConstant.DEPT_ID_TO_NAME)
class DeptNameTranslationImpl(
    private val deptService: DeptService
) : TranslationInterface<String?> {


    override fun translation(key: Any?, other: String?): String? {
        if (key is String) {
            return deptService.selectDeptNameByIds(key)
        } else if (key is Long) {
            return deptService.selectDeptNameByIds(key.toString())
        }
        return null
    }
}
