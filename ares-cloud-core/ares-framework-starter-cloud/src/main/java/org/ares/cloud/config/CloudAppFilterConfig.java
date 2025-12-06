package org.ares.cloud.config;

import org.ares.cloud.filter.ApplicationContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/6 00:55
 */
@Configuration
public class CloudAppFilterConfig {
    /**
     * 应用上下文主持
     * @return 应用上下文注册
     */
    @Bean
    public FilterRegistrationBean<ApplicationContextFilter> userContextFilter() {
        FilterRegistrationBean<ApplicationContextFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApplicationContextFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
