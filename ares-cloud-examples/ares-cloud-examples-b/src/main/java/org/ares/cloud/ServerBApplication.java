package org.ares.cloud;

import org.ares.cloud.annotation.EnableAresServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableAresServer
@EnableFeignClients(basePackages = "org.ares.cloud.api")
@EnableDiscoveryClient
public class ServerBApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerBApplication.class, args);
    }
}
