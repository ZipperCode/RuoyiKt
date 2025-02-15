package org.zipper.common.core.ext

/**
 * 定义常用的 sql关键字
 */
const val SQL_REGEX: String = "select |insert |delete |update |drop |count |exec |chr |mid |master |truncate |char |and |declare "

/**
 * 仅支持字母、数字、下划线、空格、逗号、小数点（支持多个字段排序）
 */
const val SQL_PATTERN: String = "[a-zA-Z0-9_\\ \\,\\.]+"


fun String.escapeOrderBySql(): String {
    require(!(isNotEmpty() && !matches(SQL_PATTERN.toRegex()))) {
        "参数不符合规范，不能进行查询"
    }
    return this
}