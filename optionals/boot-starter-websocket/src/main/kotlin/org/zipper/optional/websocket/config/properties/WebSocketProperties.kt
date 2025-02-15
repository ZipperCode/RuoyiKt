package org.zipper.optional.websocket.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * WebSocket 配置项
 *
 */
@ConfigurationProperties("websocket")
data class WebSocketProperties(
    val enabled: Boolean = false,

    /**
     * 路径
     */
    var path: String = "",

    /**
     * 设置访问源地址
     */
    var allowedOrigins: String? = null
)
