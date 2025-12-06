package org.ares.cloud;

import org.ares.cloud.annotation.EnableAresServer;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableAresServer
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "org.ares.cloud.base")
public class PaymentGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentGatewayApplication.class, args);
        System.out.println("支付网关服务服务启动成功");

    }
}
