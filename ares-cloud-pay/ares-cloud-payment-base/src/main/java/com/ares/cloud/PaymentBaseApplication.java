package com.ares.cloud;

import org.ares.cloud.annotation.EnableAresServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@EnableAresServer
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"org.ares.cloud.api.user","org.ares.cloud.api.merchant"})
public class PaymentBaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentBaseApplication.class, args);
        System.out.println("支付基础服务服务启动成功");

    }
}
