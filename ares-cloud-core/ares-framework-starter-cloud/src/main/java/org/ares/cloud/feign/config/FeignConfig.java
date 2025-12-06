package org.ares.cloud.feign.config;

import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.ares.cloud.feign.decode.FeignErrorDecoder;
import org.ares.cloud.feign.decode.FeignResponseDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hugo
 * @version 1.0
 * @description: Feign 配置类
 * 配置自定义的响应解码器和错误解码器
 * @date 2024/10/16 15:10
 */
@Configuration
public class FeignConfig {
    
    /**
     * 自定义响应解码器
     * 处理返回 HTTP 200 但 Result.code 不为 200 的情况
     */
    @Bean
    public Decoder feignDecoder(ObjectFactory<HttpMessageConverters> messageConverters) {
        return new FeignResponseDecoder(messageConverters);
    }
    
    /**
     * 自定义错误解码器
     * 处理 HTTP 状态码不是 2xx 的情况
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }
}

