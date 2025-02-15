package org.zipper.framework.redis.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Redisson 配置属性
 *
 * @author Lion Li
 */
@ConfigurationProperties(prefix = "redisson")
class RedissonProperties {
    /**
     * redis缓存key前缀
     */
    var keyPrefix: String = ""

    /**
     * 线程池数量,默认值 = 当前处理核数量 * 2
     */
    var threads = 0

    /**
     * Netty线程池数量,默认值 = 当前处理核数量 * 2
     */
    var nettyThreads = 0

    /**
     * 单机服务配置
     */
    var singleServerConfig: SingleServerConfig = SingleServerConfig()

    class SingleServerConfig {
        /**
         * 客户端名称
         */
        var clientName: String = "ruoyi"

        /**
         * 最小空闲连接数
         */
        var connectionMinimumIdleSize = 8

        /**
         * 连接池大小
         */
        var connectionPoolSize = 32

        /**
         * 连接空闲超时，单位：毫秒
         */
        var idleConnectionTimeout = 10000

        /**
         * 命令等待超时，单位：毫秒
         */
        var timeout = 3000

        /**
         * 发布和订阅连接池大小
         */
        var subscriptionConnectionPoolSize = 50
    }
}
