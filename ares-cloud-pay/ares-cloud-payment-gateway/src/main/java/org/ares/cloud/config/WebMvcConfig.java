package org.ares.cloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(signatureVerifyInterceptor)
//            .addPathPatterns("/api/payment/**")
//            .excludePathPatterns(
//                "/api/payment/notify/**",    // 支付回调通知
//                "/payment/page/**",          // 支付页面
//                "/api/payment/status/**",    // 支付状态查询
//                "/**/error"                  // 错误页面
//            );
    }
} 