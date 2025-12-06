package com.ares.cloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author tangxk
 * @version 1.0
 * @Description
 * @package com.ares.cloud.config
 * @reference com.ares.cloud.config.RestTemplateConfig
 * @data 2021/06/21 16:49
 */
@Configuration
public class RestTemplateConfig {

    /**
     * 完整地址调用模板
     * @return
     */
    @Bean("restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
