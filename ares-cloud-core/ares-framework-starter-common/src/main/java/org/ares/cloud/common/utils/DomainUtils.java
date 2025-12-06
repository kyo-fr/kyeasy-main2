package org.ares.cloud.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.URI;

public class DomainUtils {

    /**
     * 从 HttpServletRequest 获取真实域名
     */
    public static String getRealDomain(HttpServletRequest request) {
        if (request == null) {
            return "";
        }

        // 优先取标准头
        String host = request.getHeader("X-Forwarded-Host");
        if (hasText(host)) {
            return host.split(",")[0].trim();
        }

        // 兜底：Host
        host = request.getHeader("Host");
        if (hasText(host)) {
            return host.split(",")[0].trim();
        }

        // 最后用服务器信息
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        if (serverPort == 80 || serverPort == 443) {
            return serverName;
        }
        return serverName + ":" + serverPort;
    }

    /**
     * 从 WebFlux ServerHttpRequest 获取真实域名
     */
    public static String getRealDomain(ServerHttpRequest request) {
        if (request == null) {
            return "";
        }

        // 优先取标准头
        String host = request.getHeaders().getFirst("X-Forwarded-Host");
        if (hasText(host)) {
            return host.split(",")[0].trim();
        }

        // 兜底：Host
        host = request.getHeaders().getFirst("Host");
        if (hasText(host)) {
            return host.split(",")[0].trim();
        }

        // 最后用 URI 信息
        URI uri = request.getURI();
        String h = uri.getHost();
        int port = uri.getPort();
        if (h == null) {
            return "";
        }
        if (port == 80 || port == 443 || port == -1) {
            return h;
        }
        return h + ":" + port;
    }

    /**
     * 工具方法：判断字符串是否有内容
     */
    private static boolean hasText(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
