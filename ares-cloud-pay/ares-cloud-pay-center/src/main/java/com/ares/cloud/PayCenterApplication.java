package com.ares.cloud;

import org.ares.cloud.annotation.EnableAresServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableAresServer
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"org.ares.cloud.api.user", "org.ares.cloud.api.merchant"})
@ComponentScan(basePackages = {"org.ares.cloud", "com.ares.cloud"})
@MapperScan("com.ares.cloud.pay.infrastructure.persistence.mapper")
public class PayCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(PayCenterApplication.class, args);
        System.out.println("支付中心服务服务启动成功");

    }
}