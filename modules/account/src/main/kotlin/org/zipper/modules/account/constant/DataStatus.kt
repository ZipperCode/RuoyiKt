package org.zipper.modules.account.constant

import org.zipper.common.core.exception.ServiceException

/**
 * 数据状态
 */
enum class DataStatus(
    val status: Int,
    val title: String = ""
) {
    Normal(0, "未使用"),
    Used(1, "已使用"),
    Invalid(2, "无效"),
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
