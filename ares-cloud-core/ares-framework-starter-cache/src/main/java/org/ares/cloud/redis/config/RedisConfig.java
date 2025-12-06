package org.ares.cloud.redis.config;

import org.ares.cloud.api.LockApi;
import org.ares.cloud.redis.service.RedisLockService;
import org.ares.cloud.redis.util.RedisUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * @Author hugo  tangxkwork@163.com
 * @description redis配置
 * @date 2024/01/17/14:42
 **/
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // Key HashKey使用String序列化
        template.setKeySerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());

        // Value HashValue使用Json序列化
        template.setValueSerializer(RedisSerializer.json());
        template.setHashValueSerializer(RedisSerializer.json());

        template.setConnectionFactory(factory);
        return template;
    }
    /**
     * 注入封装RedisTemplate
     * @param redisTemplate redis模版
     * @return 工具
     */
    @Bean(name = "redisUtil")
    public RedisUtil redisUtil(RedisTemplate<String, Object> redisTemplate) {
        //redis工具类
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        //返回工具类
        return redisUtil;
    }
    /**
     * 锁的redis实现
     * @param redisUtil 工具
     * @param redisTemplate Redis模板
     * @return 锁api
     */
    @Bean
    public LockApi lockApi(RedisUtil redisUtil, RedisTemplate<String, Object> redisTemplate){
        return new RedisLockService(redisUtil, redisTemplate);
    }
}
