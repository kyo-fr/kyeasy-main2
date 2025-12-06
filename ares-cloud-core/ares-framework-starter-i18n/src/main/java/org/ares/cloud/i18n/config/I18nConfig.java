package org.ares.cloud.i18n.config;

import org.ares.cloud.i18n.interceptor.LocaleInterceptor;
import org.ares.cloud.i18n.utils.MessageUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.ares.cloud.i18n.resolver.AresLocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/9/27 18:29
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer {
    // 定义 MessageSource，加载国际化文件
    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages"); // 指定国际化文件的前缀（不带扩展名）
        messageSource.setDefaultEncoding("UTF-8"); // 设置文件编码为 UTF-8
        messageSource.setUseCodeAsDefaultMessage(true); // 如果没有找到对应的语言，则直接使用 code
        return messageSource;
    }
    /**
     * 默认解析器 其中locale表示默认语言
     */
    @Bean("customLocaleResolver")
    public LocaleResolver localeResolver() {
        AresLocaleResolver localeResolver = new AresLocaleResolver();
        localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return localeResolver;
    }

    // 配置拦截器，用于切换国际化语言
    @Bean
    public LocaleInterceptor localeChangeInterceptor() {
        return new LocaleInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加国际化拦截器
        registry.addInterceptor(localeChangeInterceptor());
    }
    /**
     * 国际化工具
     * @return 工具
     */
    @Bean
    public MessageUtils i18nUtils(MessageSource messageSource) {
        return new MessageUtils(messageSource);
    }
}
