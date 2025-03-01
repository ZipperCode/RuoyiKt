package org.zipper.modules.account.constant

/**
 * 账号类型
 */
enum class AccountType(
    val type: Int
) {
    Line(1),
    WhatsApp(2),
    Unknown(-100)

    ;
}