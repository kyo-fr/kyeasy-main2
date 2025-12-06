package org.ares.cloud.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.ares.cloud.common.constant.MateData;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.utils.LocalUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/6 00:02
 */
@Component
public class ApplicationContextFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 从请求头获取用户ID和租户ID
        String userId = httpServletRequest.getHeader(MateData.USER_ID);
        String tenantId = httpServletRequest.getHeader(MateData.TENANT_Id);
        // 将用户信息存入上下文
        ApplicationContext.setUserId(userId);
        ApplicationContext.setTenantId(tenantId);
        String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            ApplicationContext.setToken(token);
        }
        // powers
        ApplicationContext.setPowers(httpServletRequest.getHeader(MateData.POWERS));
        // 应用主键
        ApplicationContext.setAppId(httpServletRequest.getHeader(MateData.APP_ID));
        //客户端id
        ApplicationContext.setClientId(httpServletRequest.getHeader(MateData.CLIENT_ID));
        // 真实的客户端地址
        ApplicationContext.setRealIpAddress(httpServletRequest.getHeader(MateData.REAL_IP_ADDRESS));
        //链路的id
        ApplicationContext.setTraceId(httpServletRequest.getHeader(MateData.TRACE_Id));
        //用户身份
        ApplicationContext.setIdentity(httpServletRequest.getHeader(MateData.IDENTITY));
        //是否忽略租户
        if (httpServletRequest.getHeader(MateData.IGNORE_TENANT_ID) != null) {
            ApplicationContext.setIgnoreTenant( Boolean.valueOf(httpServletRequest.getHeader(MateData.IGNORE_TENANT_ID)));
        }
        // 从请求头中获取语言标识
        String language = httpServletRequest.getHeader(MateData.Accept_Language);
        Locale locale = LocalUtils.getLocale(language);
        // 将 Locale 存入 ThreadLocal
        ApplicationContext.setLocale(locale);
        //其他参数处理
        Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        // 遍历 headerNames 并筛选出以 "X-Mate-" 开头的请求头
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if (headerName.startsWith(MateData.MATE_PREFIX)) {
                String headerValue = httpServletRequest.getHeader(headerName);
                ApplicationContext.setMateDataValue(headerName, headerValue);
            }
        }
        try {
            chain.doFilter(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            ApplicationContext.clear(); // 请求完成后清理上下文
        }
    }

    @Override
    public void destroy() {}
}
