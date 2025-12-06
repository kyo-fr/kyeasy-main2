package com.ares.cloud;

import org.ares.cloud.annotation.EnableAresServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@EnableAresServer
@EnableDiscoveryClient
@ComponentScan(basePackages = {"org.ares.cloud.**", "com.ares.cloud.**"})
@EnableFeignClients(basePackages = {"org.ares.cloud.api.base","org.ares.cloud.api.payment","org.ares.cloud.api.product",
        "org.ares.cloud.api.user","org.ares.cloud.api.merchant","org.ares.cloud.api.msg_center"})
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class, args);
        System.out.println("订单服务启动成功");

    }
}
