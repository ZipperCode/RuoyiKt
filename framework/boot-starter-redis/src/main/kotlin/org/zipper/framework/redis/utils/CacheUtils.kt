package org.zipper.framework.redis.utils

import cn.hutool.extra.spring.SpringUtil
import org.springframework.cache.CacheManager
import org.zipper.common.core.ext.anyForceCastOrNull

/**
 * 缓存操作工具类 []
 *
 * @author Michelle.Chung
 * @date 2022/8/13
 */
object CacheUtils {
    private val CACHE_MANAGER: CacheManager by lazy {
        SpringUtil.getBean(CacheManager::class.java)
    }

    /**
     * 获取缓存值
     *
     * @param cacheNames 缓存组名称
     * @param key        缓存key
     */
    fun <T> get(cacheNames: String, key: Any): T? {
        val wrapper = CACHE_MANAGER.getCache(cacheNames)?.get(key)
        return wrapper?.get().anyForceCastOrNull()
    }

    /**
     * 保存缓存值
     *
     * @param cacheNames 缓存组名称
     * @param key        缓存key
     * @param value      缓存值
     */
    fun put(cacheNames: String, key: Any, value: Any?) {
        CACHE_MANAGER.getCache(cacheNames)?.put(key, value)
    }

    /**
     * 删除缓存值
     *
     * @param cacheNames 缓存组名称
     * @param key        缓存key
     */
    fun evict(cacheNames: String, key: Any) {
        CACHE_MANAGER.getCache(cacheNames)?.evict(key)
    }

    /**
     * 清空缓存值
     *
     * @param cacheNames 缓存组名称
     */
    fun clear(cacheNames: String) {
        CACHE_MANAGER.getCache(cacheNames)?.clear()
    }
}
