package org.ares.cloud.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean;
/**
 * @author hugo
 * @version 1.0
 * @description: 安全的基础配置
 * @date 2024/10/16 12:12
 */
@Configuration
public class SecurityBaseConfig {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
