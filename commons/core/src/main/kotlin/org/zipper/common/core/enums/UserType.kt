package org.zipper.common.core.enums

/**
 * 设备类型
 * 针对多套 用户体系
 *
 */
enum class UserType(
    val userType: String
) {
    /**
     * pc端
     */
    SYS_USER("sys_user"),

    /**
     * app端
     */
    APP_USER("app_user"),

    UNKNOWN("unknown")
    ;


    companion object {
        fun getUserType(str: String?): UserType {
            return entries.firstOrNull { "$str".contains(it.userType) } ?: UNKNOWN
        }
    }
}
