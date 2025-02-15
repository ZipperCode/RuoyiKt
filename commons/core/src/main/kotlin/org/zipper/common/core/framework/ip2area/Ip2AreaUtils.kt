package org.zipper.common.core.framework.ip2area

import org.zipper.common.core.ext.springCacheByType

object Ip2AreaUtils {
    private val ip2AreaStarter: Ip2AreaStarter? by springCacheByType<Ip2AreaStarter>()

    fun getAreaCity(ip: String?): String = ip2AreaStarter?.getAreaCity(ip) ?: "unknown"
}
