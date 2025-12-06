package com.ares.cloud.pay.infrastructure.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Redis连接测试配置
 * 用于诊断Redis连接问题
 */
@Slf4j
@Component
public class RedisConnectionTestConfig implements CommandLineRunner {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void run(String... args) throws Exception {
        try {
            log.info("开始测试Redis连接...");
            
            // 测试基本连接
            redisTemplate.opsForValue().set("test:connection", "success");
            Object result = redisTemplate.opsForValue().get("test:connection");
            
            if ("success".equals(result)) {
                log.info("Redis连接测试成功！");
                // 清理测试数据
                redisTemplate.delete("test:connection");
            } else {
                log.error("Redis连接测试失败：返回值不正确");
            }
            
        } catch (Exception e) {
            log.error("Redis连接测试失败：{}", e.getMessage(), e);
            // 不要抛出异常，避免阻止应用启动
        }
    }
} 