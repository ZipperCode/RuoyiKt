package org.zipper.framework.redis.utils

import org.apache.commons.lang3.StringUtils
import org.redisson.spring.data.connection.RedissonConnectionFactory
import org.zipper.common.core.exception.ServiceException
import org.zipper.common.core.ext.springCacheByType
import org.zipper.framework.redis.bean.CacheData
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

object RedisHelper {
    private val connectionFactory: RedissonConnectionFactory? by springCacheByType<RedissonConnectionFactory>()

    fun getCacheList(): CacheData {
        val connection = connectionFactory?.connection ?: throw ServiceException("not redis connection")
        val commandStats = connection.commands().info("commandstats")
        val pieList: MutableList<Map<String, String>> = ArrayList()
        commandStats?.stringPropertyNames()?.forEach { key ->
            val data: MutableMap<String, String> = HashMap(2)
            val property = commandStats.getProperty(key)
            data["name"] = StringUtils.removeStart(key, "cmdstat_")
            data["value"] = StringUtils.substringBetween(property, "calls=", ",usec")
            pieList.add(data)
        }
        val properties = connection.serverCommands().info() ?: Properties()
        val dbSize = connection.serverCommands().dbSize() ?: 0
        return CacheData(properties, dbSize, pieList)
    }
}