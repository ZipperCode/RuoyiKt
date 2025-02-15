package org.zipper.modules.system.controller.monitor

import cn.dev33.satoken.annotation.SaCheckPermission
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.zipper.framework.redis.utils.RedisHelper
import org.zipper.framework.security.aspect.ResultBody
import org.zipper.modules.system.domain.vo.CacheListInfoVo

/**
 * 缓存监控
 *
 * @author Lion Li
 */
@RestController
@RequestMapping("/monitor/cache")
class CacheController() {

    /**
     * 获取缓存监控列表
     */
    @Throws(Exception::class)
    @GetMapping
    @SaCheckPermission("monitor:cache:list")
    @ResultBody
    fun getInfo(): CacheListInfoVo {
        val cacheData = RedisHelper.getCacheList()
        return CacheListInfoVo(cacheData.info, cacheData.dbSize, cacheData.commandStats)
    }
}
