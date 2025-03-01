package org.zipper.modules.account.constant

enum class RoleCode(
    val code: String
) {

    Admin("account_admin"),
    Uploader("account_uploader"),
    Salesman("account_salesman"),;

    fun create(classify: Int): String {
        return "${code}_$classify"
    }
    ;
}