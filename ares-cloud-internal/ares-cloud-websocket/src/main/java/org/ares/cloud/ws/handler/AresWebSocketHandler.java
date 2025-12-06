package org.ares.cloud.ws.handler;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.utils.ExceptionUtils;
import org.ares.cloud.common.utils.JsonUtils;
import org.ares.cloud.common.utils.StringUtils;
import org.ares.cloud.i18n.utils.MessageUtils;

import org.ares.cloud.ws.dto.WsMessage;
import org.ares.cloud.ws.enums.MessageType;
import org.ares.cloud.ws.enums.WebSocketError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hugo
 * @version 1.0
 * @description: WebSocket处理器，负责处理WebSocket连接、消息收发和心跳检测
 * @date 2024/11/11 00:20
 */
@Component
@Slf4j
public class AresWebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(AresWebSocketHandler.class);

    // 使用 Map 存储每个用户 ID 对应的多个 WebSocket 会话
    private static final Map<String, List<WebSocketSession>> userSessionsMap = new ConcurrentHashMap<>();
    
    // 存储会话最后活动时间
    private static final Map<String, Instant> sessionLastActiveTime = new ConcurrentHashMap<>();
    
    // 心跳超时时间（分钟）
    @Value("${websocket.heartbeat.timeout:10}")
    private int heartbeatTimeout;
    
    // 心跳间隔时间（秒）
    @Value("${websocket.heartbeat.interval:30}")
    private int heartbeatInterval;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        try {
            // 从连接 URL 中提取用户ID
            String userId = extractUserId(session);
            if(StringUtils.isEmpty(userId)){
                throw new RuntimeException(MessageUtils.get("validation.user.user","用户ID不能为空"));
            }
            // 获取该用户的 WebSocket 会话列表，如果没有则新建
            userSessionsMap.computeIfAbsent(userId, k -> new ArrayList<>()).add(session);
            // 记录会话最后活动时间
            sessionLastActiveTime.put(session.getId(), Instant.now());
            log.info("WebSocket连接已建立，用户ID: {}, 会话ID: {}", userId, session.getId());
            
            // 发送连接成功消息
            sendConnectSuccessMessage(session);
        } catch (Exception e) {
            log.error("WebSocket连接建立失败: {}", ExceptionUtils.getExceptionMessage(e));
            session.close(CloseStatus.SERVER_ERROR);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            // 更新会话最后活动时间
            sessionLastActiveTime.put(session.getId(), Instant.now());
            
            // 处理来自客户端的消息
            String payload = message.getPayload();
            log.debug("收到消息: {}", payload);
            
            // 尝试解析为WsMessage对象
            try {
                WsMessage<?> wsMessage = JsonUtils.parseObject(payload, WsMessage.class);
                if (wsMessage != null && wsMessage.getType() != null) {
                    // 处理不同类型的消息
                    handleMessageByType(session, wsMessage);
                    return;
                }
            } catch (Exception e) {
                // 解析失败，当作普通文本消息处理
                log.debug("消息解析失败，作为普通文本处理: {}", e.getMessage());
            }
            
            // 默认响应
            session.sendMessage(new TextMessage("服务器已收到: " + payload));
        } catch (Exception e) {
            log.error("处理消息时发生错误: {}", ExceptionUtils.getExceptionMessage(e));
        }
    }
    
    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        // 更新会话最后活动时间
        sessionLastActiveTime.put(session.getId(), Instant.now());
        log.debug("收到PONG消息，会话ID: {}", session.getId());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        try {
            // 获取用户ID
            String userId = extractUserId(session);
            
            // 从用户会话映射中移除会话
            if (StringUtils.isNotEmpty(userId)) {
                List<WebSocketSession> sessions = userSessionsMap.get(userId);
                if (sessions != null) {
                    sessions.remove(session);
                    // 如果用户没有会话了，移除用户
                    if (sessions.isEmpty()) {
                        userSessionsMap.remove(userId);
                    }
                }
            }
            
            // 移除会话活动时间记录
            sessionLastActiveTime.remove(session.getId());
            
            log.info("WebSocket连接已关闭，用户ID: {}, 会话ID: {}, 状态: {}", userId, session.getId(), status);
        } catch (Exception e) {
            log.error("关闭连接时发生错误: {}", ExceptionUtils.getExceptionMessage(e));
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("WebSocket传输错误，会话ID: {}, 错误: {}", session.getId(), exception.getMessage());
        try {
            session.close(CloseStatus.SERVER_ERROR);
        } catch (IOException e) {
            log.error("关闭错误会话失败: {}", e.getMessage());
        }
    }
    
    /**
     * 定时检查心跳，关闭超时的连接
     */
    @Scheduled(fixedDelayString = "${websocket.heartbeat.check.interval:60000}")
    public void checkHeartbeats() {
        Instant now = Instant.now();
        sessionLastActiveTime.forEach((sessionId, lastActive) -> {
            Duration inactiveDuration = Duration.between(lastActive, now);
            if (inactiveDuration.toMinutes() >= heartbeatTimeout) {
                // 查找并关闭超时会话
                userSessionsMap.forEach((userId, sessions) -> {
                    sessions.stream()
                            .filter(session -> session.getId().equals(sessionId))
                            .findFirst()
                            .ifPresent(session -> {
                                try {
                                    log.info("关闭超时会话，用户ID: {}, 会话ID: {}, 不活跃时间: {}分钟", 
                                            userId, sessionId, inactiveDuration.toMinutes());
                                    session.close(CloseStatus.NORMAL.withReason("心跳超时"));
                                } catch (IOException e) {
                                    log.error("关闭超时会话失败: {}", e.getMessage());
                                }
                            });
                });
            }
        });
    }
    
    /**
     * 定时发送心跳消息
     */
    @Scheduled(fixedDelayString = "${websocket.heartbeat.send.interval:30000}")
    public void sendHeartbeats() {
        userSessionsMap.forEach((userId, sessions) -> {
            sessions.forEach(session -> {
                if (session.isOpen()) {
                    try {
                        WsMessage<String> heartbeat = new WsMessage<>(MessageType.HEARTBEAT, "heartbeat");
                        session.sendMessage(new TextMessage(JsonUtils.toJsonString(heartbeat)));
                        log.debug("发送心跳消息，用户ID: {}, 会话ID: {}", userId, session.getId());
                    } catch (IOException e) {
                        log.error("发送心跳消息失败: {}", e.getMessage());
                    }
                }
            });
        });
    }

    /**
     * 向指定用户ID的所有连接发送消息
     * @param userId 用户ID
     * @param message 消息内容
     * @throws Exception 发送异常
     */
    public void sendMessageToUser(String userId, String message) throws Exception {
        List<WebSocketSession> sessions = userSessionsMap.get(userId);
        // 如果该用户有连接，则发送消息到每一个连接
        if (sessions != null) {
            for (WebSocketSession session : sessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            }
        }
    }

    /**
     * 发送消息
     * @param userId 用户ID
     * @param message 消息对象
     * @param <T> 消息泛型
     */
    public <T> void sendMessage(String userId, WsMessage<T> message) {
        if (message == null) {
            throw new BusinessException(WebSocketError.MESSAGE_IS_EMPTY);
        }
        List<WebSocketSession> sessions = userSessionsMap.get(userId);
        if (CollectionUtil.isEmpty(sessions)) {
            throw new BusinessException(WebSocketError.USER_NOT_ONLINE);
        }
        String string = JsonUtils.toJsonString(message);
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(string));
                    // 更新会话最后活动时间
                    sessionLastActiveTime.put(session.getId(), Instant.now());
                } catch (Exception e) {
                    log.error("发送消息错误: {}", ExceptionUtils.getExceptionMessage(e));
                    throw new BusinessException(WebSocketError.SEND_MESSAGE_ERROR);
                }
            }
        }
    }
    
    /**
     * 广播消息给所有在线用户
     * @param message 消息对象
     * @param <T> 消息泛型
     */
    public <T> void broadcastMessage(WsMessage<T> message) {
        if (message == null) {
            throw new BusinessException(WebSocketError.MESSAGE_IS_EMPTY);
        }
        String messageStr = JsonUtils.toJsonString(message);
        userSessionsMap.forEach((userId, sessions) -> {
            sessions.forEach(session -> {
                if (session.isOpen()) {
                    try {
                        session.sendMessage(new TextMessage(messageStr));
                        // 更新会话最后活动时间
                        sessionLastActiveTime.put(session.getId(), Instant.now());
                    } catch (IOException e) {
                        log.error("广播消息到用户 {} 失败: {}", userId, e.getMessage());
                    }
                }
            });
        });
    }
    
    /**
     * 获取在线用户数量
     * @return 在线用户数量
     */
    public int getOnlineUserCount() {
        return userSessionsMap.size();
    }
    
    /**
     * 获取总会话数量
     * @return 总会话数量
     */
    public int getTotalSessionCount() {
        return userSessionsMap.values().stream().mapToInt(List::size).sum();
    }
    
    /**
     * 检查用户是否在线
     * @param userId 用户ID
     * @return 是否在线
     */
    public boolean isUserOnline(String userId) {
        List<WebSocketSession> sessions = userSessionsMap.get(userId);
        return sessions != null && !sessions.isEmpty() && sessions.stream().anyMatch(WebSocketSession::isOpen);
    }
    
    /**
     * 从会话中提取用户ID
     * @param session WebSocket会话
     * @return 用户ID
     */
    private String extractUserId(WebSocketSession session) {
        try {
            return Objects.requireNonNull(session.getUri()).getQuery().split("=")[1]; // 假设格式为 ?userId=123
        } catch (Exception e) {
            log.error("提取用户ID失败: {}", e.getMessage());
            return null;
        }
    }
    
    /**
     * 发送连接成功消息
     * @param session WebSocket会话
     */
    private void sendConnectSuccessMessage(WebSocketSession session) {
        try {
            WsMessage<String> connectMessage = new WsMessage<>(MessageType.PONG, "连接成功");
            session.sendMessage(new TextMessage(JsonUtils.toJsonString(connectMessage)));
        } catch (IOException e) {
            log.error("发送连接成功消息失败: {}", e.getMessage());
        }
    }
    
    /**
     * 根据消息类型处理消息
     * @param session WebSocket会话
     * @param message 消息对象
     */
    private void handleMessageByType(WebSocketSession session, WsMessage<?> message) {
        try {
            switch (message.getType()) {
                case PING:
                    // 处理心跳PING消息，回复PONG
                    WsMessage<String> pongMessage = new WsMessage<>(MessageType.PONG, "pong");
                    session.sendMessage(new TextMessage(JsonUtils.toJsonString(pongMessage)));
                    log.debug("回复PONG消息，会话ID: {}", session.getId());
                    break;
                case HEARTBEAT:
                    // 处理心跳消息，只更新活动时间，不需要回复
                    log.debug("收到心跳消息，会话ID: {}", session.getId());
                    break;
                default:
                    // 其他类型的消息，记录日志
                    log.debug("收到类型为 {} 的消息，会话ID: {}", message.getType(), session.getId());
                    break;
            }
        } catch (IOException e) {
            log.error("处理消息类型时发生错误: {}", e.getMessage());
        }
    }
}
