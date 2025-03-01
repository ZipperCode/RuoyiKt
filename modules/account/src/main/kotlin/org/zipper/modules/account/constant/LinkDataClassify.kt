package org.zipper.modules.account.constant

import org.zipper.common.core.exception.ServiceException

enum class LinkDataClassify(
    val classify: Int
) {
    One(1),
    Two(2),

    Unknown(-999)
    ;

    companion object {
        fun valid(classify: Int?){
            if (classify == null) {
                throw ServiceException("classify不能为空")
            }
            if (entries.firstOrNull { it.classify == classify } == null) {
                throw ServiceException("classify值无效")
            }
        }
    }
}
