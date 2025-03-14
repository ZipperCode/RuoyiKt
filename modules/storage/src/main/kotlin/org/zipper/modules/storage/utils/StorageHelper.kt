package org.zipper.modules.storage.utils

import org.zipper.framework.redis.utils.RedisUtils
import org.zipper.modules.storage.constant.StorageConst
import org.zipper.modules.storage.domain.mixin.FileRecordMixin

object StorageHelper {

    fun FileRecordMixin.formatUrl(): String {
        val domainKey = StorageConst.CACHE_KEY_STORAGE_DOMAIN_FMT.format(configId)
        var domain = RedisUtils.getCacheObject<String>(domainKey)
        if (domain?.endsWith("/") == true) {
            domain = domain.substring(0, domain.length - 1)
        }
        var realUrl = url
        if (realUrl?.startsWith("/") == true) {
            realUrl = realUrl.substring(1)
        }
        return "%s/%s".format(domain, realUrl)
    }
}