package org.ares.cloud.config;

import org.ares.cloud.config.template.GeneratorConfig;
import org.ares.cloud.properties.GeneratorProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author hugo  tangxkwork@163.com
 * @description 配置
 * @date 2024/01/24/01:04
 **/
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties(GeneratorProperties.class)
public class GeneratorConfiguration {
    private final GeneratorProperties properties;

    @Bean
    GeneratorConfig generatorConfig() {
        return new GeneratorConfig(properties.getTemplate());
    }
}
