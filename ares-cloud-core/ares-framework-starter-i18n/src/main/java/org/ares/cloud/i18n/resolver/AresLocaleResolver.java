package org.ares.cloud.i18n.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;
/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/9/27 18:19
 */

public class AresLocaleResolver implements LocaleResolver {
    // 手动创建 Logger 实例
    private static final Logger log = LoggerFactory.getLogger(AresLocaleResolver.class);
    private Locale defaultLocale;
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        // 获取请求中的语言参数
        String language = request.getHeader("lang");
        log.debug("Language parameter from header: {}", language);
        Locale locale = defaultLocale; // 默认简体中文

        // 如果请求中带有语言参数
        if (StringUtils.hasText(language)) {
            try {
                String[] langRegion = language.split("_");
                if (langRegion.length == 2) {
                    locale = new Locale(langRegion[0], langRegion[1]);
                } else {
                    log.warn("Invalid language format: {}", language);
                }
            } catch (Exception e) {
                log.error("Error parsing language parameter: {}", language, e);
            }
        }

        return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        this.defaultLocale = locale;
    }

    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public void setDefaultLocale(Locale defaultLocale) {
        this.defaultLocale = defaultLocale;
    }
}
