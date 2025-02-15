package org.zipper.common.core.ext

import org.apache.commons.lang3.time.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.util.*

const val YYYY: String = "yyyy"

const val YYYY_MM: String = "yyyy-MM"

const val YYYY_MM_DD: String = "yyyy-MM-dd"

const val YYYY_MM_DD_PATH: String = "yyyy/MM/dd"

const val YYYYMMDDHHMMSS: String = "yyyyMMddHHmmss"

const val YYYY_MM_DD_HH_MM_SS: String = "yyyy-MM-dd HH:mm:ss"

private val PARSE_PATTERNS = arrayOf(
    "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
    "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
    "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"
)

/**
 * 当前日期默认格式：YYYY_MM_DD
 */
fun nowDate(fmt: String = YYYY_MM_DD): String {
    return parseDateToStr(fmt, Date())
}

/**
 * 当前时间默认格式：YYYY_MM_DD_HH_MM_SS
 */
fun nowDateTime(fmt: String = YYYY_MM_DD_HH_MM_SS): String {
    return parseDateToStr(fmt, Date())
}

fun parseDateToStr(format: String, date: Date): String {
    return try {
        SimpleDateFormat(format).format(date)
    } catch (e: ParseException) {
        date.toString()
    }
}

fun parseAnyFormatDate(str: String): Date? {
    return try {
        DateUtils.parseDate(str, *PARSE_PATTERNS)
    } catch (e: Exception) {
        null
    }
}


fun LocalDateTime.toDate(): Date {
    return Date.from(this.atZone(ZoneId.systemDefault()).toInstant())
}

fun LocalDate.toDate(): Date {
    val localDateTime = LocalDateTime.of(this, LocalTime.of(0, 0, 0))
    val zdt = localDateTime.atZone(ZoneId.systemDefault())
    return Date.from(zdt.toInstant())
}