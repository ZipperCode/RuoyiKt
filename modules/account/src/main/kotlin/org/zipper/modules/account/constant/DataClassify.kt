package org.zipper.modules.account.constant

import org.zipper.common.core.exception.ServiceException

/**
 * 数据分类
 */
enum class DataClassify(
    val classify: Int,
    val title: String
) {

    Line(1, "Line类型"),
    Pairs(2, "Pairs类型"),
    WhatsApp1(3, "WhatsApp1类型"),
    WhatsApp2(4, "WhatsApp2类型"),
    WhatsApp3(5, "WhatsApp3类型"),
    WhatsApp4(6, "WhatsApp4类型"),
    WhatsApp5(7, "WhatsApp5类型"),
    WhatsApp6(8, "WhatsApp6类型"),
    WhatsApp7(9, "WhatsApp7类型"),

    Unknown(-100, "无效类型")
    ;

    companion object {

        fun valid(classify: Int?) {
            if (entries.any { it.classify == classify }) {
                return
            }
            throw ServiceException("classify参数错误")
        }
    }
}