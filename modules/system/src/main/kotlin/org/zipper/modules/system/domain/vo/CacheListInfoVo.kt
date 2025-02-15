package org.zipper.modules.system.domain.vo

import java.util.*

/**
 * 缓存监控列表信息
 *
 */
data class CacheListInfoVo(
    var info: Properties,
    var dbSize: Long,
    var commandStats: List<Map<String, String>>,
)
