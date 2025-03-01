package org.zipper.common.core.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object TimeUtils {
    /**
     * 获取北京时间 LocalDateTime
     */
    fun getBeijingTime(): LocalDateTime {
        val zoneId = ZoneId.of("Asia/Shanghai")
        val now = LocalDateTime.now()
        return now.atZone(zoneId).toLocalDateTime()
    }

    /**
     * 获取当天的北京时间范围
     */
    fun getBeijingTimeRange(): Pair<LocalDateTime, LocalDateTime> {
        val zoneId = ZoneId.of("Asia/Shanghai")
        val now = LocalDateTime.now()
        val start = now.withHour(0).withMinute(0).withSecond(0).withNano(0)
        val end = now.withHour(23).withMinute(59).withSecond(59).withNano(999999999)
        return Pair(start.atZone(zoneId).toLocalDateTime(),end.atZone(zoneId).toLocalDateTime())
    }

    /**
     * 获取当前北京时间五个月之前的LocalDataTime
     */
    fun getBeijingTimeBeforeFiveMonth(): LocalDateTime {
        val zoneId = ZoneId.of("Asia/Shanghai")
        val now = LocalDateTime.now()
        val fiveMonthAgo = now.minusMonths(5)
        return fiveMonthAgo.atZone(zoneId).toLocalDateTime()
    }

    /**
     * localDateTime 格式化为 yyy-MM-dd HH:mm:ss
     */
    fun LocalDateTime.formatYmdHms(): String {
        return format("yyyy-MM-dd HH:mm:ss")
    }

    fun LocalDateTime.format(pattern: String): String {
        return this.format(DateTimeFormatter.ofPattern(pattern))
    }
}