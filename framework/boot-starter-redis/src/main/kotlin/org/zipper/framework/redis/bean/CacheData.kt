package org.zipper.framework.redis.bean

import java.util.*

class CacheData(
    val info: Properties,
    val dbSize: Long,
    val commandStats: List<Map<String, String>>
)