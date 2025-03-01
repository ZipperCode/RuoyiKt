package org.zipper.modules.account.constant

import org.zipper.common.core.exception.ServiceException

/**
 * 数据状态
 */
enum class DataStatus(
    val status: Int
) {
    Normal(0),
    Used(1),
    Invalid(2),
    ;

    companion object {
        fun of(status: Int): DataStatus {
            return DataStatus.entries.find { it.status == status } ?: Normal
        }

        fun valid(status: Int) {
            if (entries.any { it.status == status }) {
                return
            }
            throw ServiceException("不支持的状态值 $status")
        }
    }
}
