package org.ares.cloud.ws.config;

import org.ares.cloud.ws.handler.AresWebSocketHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @author hugo
 * @version 1.0
 * @description: WebSocket配置类，配置WebSocket处理器、心跳检测和连接参数
 * @date 2024/11/11 00:19
 */
@Configuration
@EnableWebSocket
@EnableScheduling // 启用定时任务，用于心跳检测
public class WebSocketConfig implements WebSocketConfigurer {

    private final AresWebSocketHandler aresWebSocketHandler;

    public WebSocketConfig(AresWebSocketHandler myWebSocketHandler) {
        this.aresWebSocketHandler = myWebSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(aresWebSocketHandler, "/ws")
                .setAllowedOrigins("*") // 允许所有来源的连接，生产环境应该限制
                .addInterceptors(new HttpSessionHandshakeInterceptor()) // 添加HTTP会话拦截器
                .setHandshakeHandler(new CustomHandshakeHandler()); // 自定义握手处理器
    }
    
    /**
     * 配置WebSocket容器参数
     * @return ServletServerContainerFactoryBean
     */
    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        // 设置消息缓冲区大小
        container.setMaxTextMessageBufferSize(8192);
        // 设置二进制消息缓冲区大小
        container.setMaxBinaryMessageBufferSize(8192);
        // 设置会话空闲超时（毫秒）
        container.setMaxSessionIdleTimeout(60 * 60 * 1000L); // 1小时
        // 设置异步发送超时（毫秒）
        container.setAsyncSendTimeout(10 * 1000L); // 10秒
        return container;
    }
}
