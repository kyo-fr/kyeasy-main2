package org.ares.cloud.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/**
 * @author hugo
 * @version 1.0
 * @description: Referer工具类，用于处理请求来源信息
 * @date 2024/12/19 10:30
 */
public class RefererUtils {
    private static final Logger log = LoggerFactory.getLogger(RefererUtils.class);
    
    /**
     * 从HTTP请求中获取Referer头信息
     * 
     * @param request HTTP请求对象
     * @return Referer URL，如果不存在返回null
     */
    public static String getReferer(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        
        String referer = request.getHeader("Referer");
        log.debug("获取Referer头: {}", referer);
        return referer;
    }
    
    /**
     * 从WebFlux请求中获取Referer头信息
     * 
     * @param request WebFlux请求对象
     * @return Referer URL，如果不存在返回null
     */
    public static String getReferer(ServerHttpRequest request) {
        if (request == null) {
            return null;
        }
        
        String referer = request.getHeaders().getFirst("Referer");
        log.debug("获取Referer头(WebFlux): {}", referer);
        return referer;
    }
    
    /**
     * 从Referer URL中提取域名
     * 
     * @param referer Referer URL
     * @return 域名，如果解析失败返回null
     */
    public static String getDomainFromReferer(String referer) {
        if (StringUtils.isBlank(referer)) {
            return null;
        }
        
        try {
            URI uri = new URI(referer);
            String host = uri.getHost();
            int port = uri.getPort();
            
            if (host == null) {
                return null;
            }
            
            // 对于标准端口（80、443），不显示端口号
            if (port == 80 || port == 443 || port == -1) {
                return host;
            } else {
                return host + ":" + port;
            }
        } catch (URISyntaxException e) {
            log.warn("解析Referer URL失败: {}", referer, e);
            return null;
        }
    }
    
    /**
     * 从HTTP请求的Referer中提取域名
     * 
     * @param request HTTP请求对象
     * @return 域名，如果解析失败返回null
     */
    public static String getDomainFromReferer(HttpServletRequest request) {
        String referer = getReferer(request);
        return getDomainFromReferer(referer);
    }
    
    /**
     * 从WebFlux请求的Referer中提取域名
     * 
     * @param request WebFlux请求对象
     * @return 域名，如果解析失败返回null
     */
    public static String getDomainFromReferer(ServerHttpRequest request) {
        String referer = getReferer(request);
        return getDomainFromReferer(referer);
    }
    
    /**
     * 从Referer URL中提取协议
     * 
     * @param referer Referer URL
     * @return 协议（http、https等），如果解析失败返回null
     */
    public static String getProtocolFromReferer(String referer) {
        if (StringUtils.isBlank(referer)) {
            return null;
        }
        
        try {
            URI uri = new URI(referer);
            return uri.getScheme();
        } catch (URISyntaxException e) {
            log.warn("解析Referer协议失败: {}", referer, e);
            return null;
        }
    }
    
    /**
     * 从Referer URL中提取路径
     * 
     * @param referer Referer URL
     * @return 路径，如果解析失败返回null
     */
    public static String getPathFromReferer(String referer) {
        if (StringUtils.isBlank(referer)) {
            return null;
        }
        
        try {
            URI uri = new URI(referer);
            return uri.getPath();
        } catch (URISyntaxException e) {
            log.warn("解析Referer路径失败: {}", referer, e);
            return null;
        }
    }
    
    /**
     * 从Referer URL中提取查询参数
     * 
     * @param referer Referer URL
     * @return 查询参数字符串，如果解析失败返回null
     */
    public static String getQueryFromReferer(String referer) {
        if (StringUtils.isBlank(referer)) {
            return null;
        }
        
        try {
            URI uri = new URI(referer);
            return uri.getQuery();
        } catch (URISyntaxException e) {
            log.warn("解析Referer查询参数失败: {}", referer, e);
            return null;
        }
    }
    
    /**
     * 解析Referer URL为URI对象
     * 
     * @param referer Referer URL
     * @return URI对象，如果解析失败返回Optional.empty()
     */
    public static Optional<URI> parseRefererUri(String referer) {
        if (StringUtils.isBlank(referer)) {
            return Optional.empty();
        }
        
        try {
            URI uri = new URI(referer);
            return Optional.of(uri);
        } catch (URISyntaxException e) {
            log.warn("解析Referer URI失败: {}", referer, e);
            return Optional.empty();
        }
    }
    
    /**
     * 判断Referer是否为内部域名
     * 
     * @param referer Referer URL
     * @param internalDomains 内部域名列表
     * @return 是否为内部域名
     */
    public static boolean isInternalReferer(String referer, String... internalDomains) {
        String domain = getDomainFromReferer(referer);
        if (domain == null) {
            return false;
        }
        
        for (String internalDomain : internalDomains) {
            if (domain.equalsIgnoreCase(internalDomain) || 
                domain.endsWith("." + internalDomain.toLowerCase())) {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 判断Referer是否为本地域名
     * 
     * @param referer Referer URL
     * @return 是否为本地域名
     */
    public static boolean isLocalReferer(String referer) {
        String domain = getDomainFromReferer(referer);
        if (domain == null) {
            return false;
        }
        
        String domainLower = domain.toLowerCase();
        return domainLower.equals("localhost") ||
               domainLower.startsWith("127.") ||
               domainLower.startsWith("192.168.") ||
               domainLower.startsWith("10.") ||
               domainLower.startsWith("172.");
    }
    
    /**
     * 获取完整的Referer信息对象
     * 
     * @param referer Referer URL
     * @return RefererInfo对象，包含解析后的各个部分
     */
    public static RefererInfo parseRefererInfo(String referer) {
        if (StringUtils.isBlank(referer)) {
            return null;
        }
        
        try {
            URI uri = new URI(referer);
            return new RefererInfo(
                uri.getScheme(),
                uri.getHost(),
                uri.getPort(),
                uri.getPath(),
                uri.getQuery(),
                referer
            );
        } catch (URISyntaxException e) {
            log.warn("解析Referer信息失败: {}", referer, e);
            return null;
        }
    }
    
    /**
     * Referer信息封装类
     */
    public static class RefererInfo {
        private final String protocol;
        private final String host;
        private final int port;
        private final String path;
        private final String query;
        private final String fullUrl;
        
        public RefererInfo(String protocol, String host, int port, String path, String query, String fullUrl) {
            this.protocol = protocol;
            this.host = host;
            this.port = port;
            this.path = path;
            this.query = query;
            this.fullUrl = fullUrl;
        }
        
        public String getProtocol() {
            return protocol;
        }
        
        public String getHost() {
            return host;
        }
        
        public int getPort() {
            return port;
        }
        
        public String getPath() {
            return path;
        }
        
        public String getQuery() {
            return query;
        }
        
        public String getFullUrl() {
            return fullUrl;
        }
        
        /**
         * 获取完整域名（包含端口）
         */
        public String getDomain() {
            if (port == 80 || port == 443 || port == -1) {
                return host;
            } else {
                return host + ":" + port;
            }
        }
        
        /**
         * 获取完整域名（不包含端口）
         */
        public String getDomainWithoutPort() {
            return host;
        }
        
        @Override
        public String toString() {
            return "RefererInfo{" +
                    "protocol='" + protocol + '\'' +
                    ", host='" + host + '\'' +
                    ", port=" + port +
                    ", path='" + path + '\'' +
                    ", query='" + query + '\'' +
                    ", fullUrl='" + fullUrl + '\'' +
                    '}';
        }
    }
} 