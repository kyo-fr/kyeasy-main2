package org.ares.cloud.i18n.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.ares.cloud.common.constant.MateData;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.utils.LocalUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Locale;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/1 02:48
 */
public class LocaleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取语言标识
        String language = request.getHeader(MateData.Accept_Language);
        Locale locale = LocalUtils.getLocale(language);
        // 将 Locale 存入 ThreadLocal
        ApplicationContext.setLocale(locale);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 请求处理完成后清理 ThreadLocal 中的 Locale
        ApplicationContext.setLocale(null);
    }
}