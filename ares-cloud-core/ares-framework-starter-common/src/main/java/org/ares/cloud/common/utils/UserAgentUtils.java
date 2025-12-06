package org.ares.cloud.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * @author hugo
 * @version 1.0
 * @description: 用户设备工具类，用于识别客户端设备类型和获取请求域名信息
 * @date 2024/9/29 16:30
 */
public class UserAgentUtils {
    private static final Logger log = LoggerFactory.getLogger(UserAgentUtils.class);
    
    // 设备类型常量
    public static final String DEVICE_TYPE_BROWSER = "browser";
    public static final String DEVICE_TYPE_IPAD = "iPad";
    public static final String DEVICE_TYPE_IOS = "iOS";
    public static final String DEVICE_TYPE_ANDROID = "Android";
    public static final String DEVICE_TYPE_UNKNOWN = "unknown";
    
    // 正则表达式匹配模式
    private static final Pattern IPAD_PATTERN = Pattern.compile("(?i)iPad");
    private static final Pattern IOS_PATTERN = Pattern.compile("(?i)iPhone|iPod");
    private static final Pattern ANDROID_PATTERN = Pattern.compile("(?i)Android");
    
    /**
     * 获取客户端设备类型
     *
     * @param request HTTP请求对象
     * @return 设备类型（browser、iPad、iOS、Android、unknown）
     */
    public static String getDeviceType(HttpServletRequest request) {
        if (request == null) {
            return DEVICE_TYPE_UNKNOWN;
        }
        
        String userAgent = request.getHeader("User-Agent");
        return getDeviceType(userAgent);
    }
    
    /**
     * 根据User-Agent字符串获取客户端设备类型
     *
     * @param userAgent User-Agent字符串
     * @return 设备类型（browser、iPad、iOS、Android、unknown）
     */
    public static String getDeviceType(String userAgent) {
        if (userAgent == null || userAgent.isEmpty()) {
            return DEVICE_TYPE_UNKNOWN;
        }
        
        // 按优先级匹配设备类型
        if (IPAD_PATTERN.matcher(userAgent).find()) {
            return DEVICE_TYPE_IPAD;
        }
        
        if (IOS_PATTERN.matcher(userAgent).find()) {
            return DEVICE_TYPE_IOS;
        }
        
        if (ANDROID_PATTERN.matcher(userAgent).find()) {
            return DEVICE_TYPE_ANDROID;
        }
        
        // 默认为浏览器
        return DEVICE_TYPE_BROWSER;
    }
    
    /**
     * 判断是否为移动设备
     *
     * @param request HTTP请求对象
     * @return 是否为移动设备
     */
    public static boolean isMobileDevice(HttpServletRequest request) {
        if (request == null) {
            return false;
        }
        
        String deviceType = getDeviceType(request);
        return DEVICE_TYPE_IOS.equals(deviceType) || 
               DEVICE_TYPE_ANDROID.equals(deviceType) || 
               DEVICE_TYPE_IPAD.equals(deviceType);
    }
    
    /**
     * 获取浏览器类型
     *
     * @param request HTTP请求对象
     * @return 浏览器类型
     */
    public static String getBrowserType(HttpServletRequest request) {
        if (request == null) {
            return DEVICE_TYPE_UNKNOWN;
        }
        
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null || userAgent.isEmpty()) {
            return DEVICE_TYPE_UNKNOWN;
        }
        
        // 提取主要浏览器类型
        if (userAgent.contains("Edge")) {
            return "Edge";
        } else if (userAgent.contains("Chrome") && !userAgent.contains("Edg")) {
            return "Chrome";
        } else if (userAgent.contains("Firefox")) {
            return "Firefox";
        } else if (userAgent.contains("Safari") && !userAgent.contains("Chrome")) {
            return "Safari";
        } else if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
            return "IE";
        }
        
        return DEVICE_TYPE_UNKNOWN;
    }
    
    /**
     * 获取用户请求的域名或IP+端口
     * <p>
     * 在Nginx反向代理环境中，此方法会按优先级检查以下HTTP头信息：
     * 1. Custom-Host - 自定义头，网关设置的真实域名
     * 2. X-Forwarded-Host - 标准的代理头，包含客户端请求的原始主机名
     * 3. Host - 标准HTTP头
     * 4. X-Real-Host - 自定义头，可在Nginx中配置
     * <p>
     * Nginx配置示例：
     * <pre>
     * server {
     *     listen 80;
     *     server_name example.com www.example.com;
     *     
     *     location / {
     *         proxy_pass http://backend_server;
     *         proxy_set_header Host $host;
     *         proxy_set_header X-Forwarded-Host $host:$server_port;
     *         proxy_set_header X-Real-Host $server_name;
     *         proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
     *         proxy_set_header X-Forwarded-Proto $scheme;
     *     }
     * }
     * </pre>
     *
     * @param request HTTP请求对象
     * @return 域名或IP+端口
     */
    public static String getDomainOrIpPort(HttpServletRequest request) {
        if (request == null) {
            return "";
        }

        log.debug("开始获取域名信息，检查请求头...");
        return DomainUtils.getRealDomain( request);
    }

    /**
     * 获取用户请求的域名或IP+端口，不包含协议部分
     * <p>
     * 此方法主要用于需要域名或IP+端口但不需要协议的场景
     * 比如在需要拼接WebSocket地址时使用
     * 
     * @param request HTTP请求对象
     * @return 域名或IP+端口，不包含协议
     */
    public static String getDomain(HttpServletRequest request) {
        String host = getDomainOrIpPort(request);
        
        // 如果包含协议，则去掉协议部分
        if (host.contains("://")) {
            return host.split("://")[1];
        }
        
        return host;
    }

    /**
     * 获取请求的完整URL（包括协议、域名、端口和路径）
     * 
     * @param request HTTP请求对象
     * @return 完整URL
     */
    public static String getFullRequestUrl(HttpServletRequest request) {
        if (request == null) {
            return "";
        }
        
        String scheme = request.getHeader("X-Forwarded-Proto");
        if (scheme == null || scheme.isEmpty()) {
            scheme = request.getScheme();
        }
        
        String host = getDomainOrIpPort(request);
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        
        StringBuilder url = new StringBuilder(scheme).append("://").append(host).append(requestURI);
        
        if (queryString != null && !queryString.isEmpty()) {
            url.append("?").append(queryString);
        }
        
        return url.toString();
    }
}