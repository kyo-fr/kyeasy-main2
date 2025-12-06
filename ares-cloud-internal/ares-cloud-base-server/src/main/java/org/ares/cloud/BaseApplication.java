package org.ares.cloud;

import org.ares.cloud.annotation.EnableAresServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author hugo
 * @version 1.0
 * @description: 文件服务
 * @date 2024/10/12 12:05
 */
@EnableAresServer
@EnableDiscoveryClient
@ComponentScan(basePackages = {"org.ares.cloud.**","org.ares.cloud.**"})
@EnableFeignClients(basePackages = {"org.ares.cloud.api.merchant"})
public class BaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class, args);
        System.out.println("基础服务启动成功");
    }
}