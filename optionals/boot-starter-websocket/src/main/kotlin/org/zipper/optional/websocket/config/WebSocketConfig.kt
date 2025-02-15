package org.zipper.optional.websocket.config

import cn.hutool.core.util.StrUtil
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import org.springframework.web.socket.server.HandshakeInterceptor
import org.zipper.common.core.event.WebSocketMsgEvent
import org.zipper.optional.websocket.config.properties.WebSocketProperties
import org.zipper.optional.websocket.handler.PlusWebSocketHandler
import org.zipper.optional.websocket.interceptor.PlusWebSocketInterceptor
import org.zipper.optional.websocket.listener.WebSocketTopicListener
import org.zipper.optional.websocket.utils.WebSocketUtils

/**
 * WebSocket 配置
 *
 * @author zendwang
 */
@AutoConfiguration
@ConditionalOnProperty(value = ["websocket.enabled"], havingValue = "true")
@EnableConfigurationProperties(
    WebSocketProperties::class
)
@EnableWebSocket
class WebSocketConfig {
    @Bean
    fun webSocketConfigurer(
        handshakeInterceptor: HandshakeInterceptor?,
        webSocketHandler: WebSocketHandler,
        webSocketProperties: WebSocketProperties
    ): WebSocketConfigurer {
        if (StrUtil.isBlank(webSocketProperties.path)) {
            webSocketProperties.path = "/websocket"
        }

        if (StrUtil.isBlank(webSocketProperties.allowedOrigins)) {
            webSocketProperties.allowedOrigins = "*"
        }

        return WebSocketConfigurer { registry: WebSocketHandlerRegistry ->
            registry
                .addHandler(webSocketHandler, webSocketProperties.path)
                .addInterceptors(handshakeInterceptor)
                .setAllowedOrigins(webSocketProperties.allowedOrigins)
        }
    }

    @Bean
    fun handshakeInterceptor(): HandshakeInterceptor {
        return PlusWebSocketInterceptor()
    }

    @Bean
    fun webSocketHandler(): WebSocketHandler {
        return PlusWebSocketHandler()
    }

    @Bean
    fun topicListener(): WebSocketTopicListener {
        return WebSocketTopicListener()
    }

    @EventListener(WebSocketMsgEvent::class)
    fun onEvent(event: WebSocketMsgEvent) {
        WebSocketUtils.publishAll(event.message)
    }
}
