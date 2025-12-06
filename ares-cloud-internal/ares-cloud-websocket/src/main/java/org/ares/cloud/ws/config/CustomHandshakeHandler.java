package org.ares.cloud.ws.config;

import org.ares.cloud.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * @author hugo
 * @version 1.0
 * @description: 自定义WebSocket握手处理器，用于处理连接握手过程中的身份验证和会话管理
 * @date 2024/11/11 00:19
 */
public class CustomHandshakeHandler extends DefaultHandshakeHandler {
    private static final Logger log = LoggerFactory.getLogger(CustomHandshakeHandler.class);

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // 从请求中获取用户ID
        String userId = extractUserId(request);
        if (StringUtils.isEmpty(userId)) {
            log.warn("WebSocket连接握手失败：未提供用户ID");
            return null;
        }
        
        // 创建自定义Principal对象
        return new UserPrincipal(userId);
    }
    
    /**
     * 从请求中提取用户ID
     * @param request HTTP请求
     * @return 用户ID
     */
    private String extractUserId(ServerHttpRequest request) {
        try {
            String query = request.getURI().getQuery();
            if (query != null && query.contains("userId=")) {
                return query.split("userId=")[1].split("&")[0];
            }
            return null;
        } catch (Exception e) {
            log.error("提取用户ID失败: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * 自定义Principal实现，用于表示WebSocket连接的用户身份
     */
    private static class UserPrincipal implements Principal {
        private final String userId;
        
        public UserPrincipal(String userId) {
            this.userId = userId;
        }
        
        @Override
        public String getName() {
            return userId;
        }
    }
}