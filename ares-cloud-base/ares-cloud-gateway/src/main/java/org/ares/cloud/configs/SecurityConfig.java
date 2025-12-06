package org.ares.cloud.configs;

import org.ares.cloud.filter.AuthFilter;
import org.ares.cloud.properties.GatewayProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableWebFluxSecurity
@RefreshScope  // 动态刷新配置
@EnableConfigurationProperties(GatewayProperties.class)
public class SecurityConfig {
    // 从 Nacos 中读取公开路径
    private final GatewayProperties gatewayProperties;
    private final AuthFilter authFilter;
    public SecurityConfig(AuthFilter authFilter,GatewayProperties gatewayProperties) {
        this.gatewayProperties = gatewayProperties;
        this.authFilter = authFilter;
    }

    @Bean
    @RefreshScope
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        updateAuthorization(http);
        return http.build();
    }

    /**
     * 更新配置
     * @param http 请求
     */
    public void updateAuthorization(ServerHttpSecurity http) {
        http
                .authorizeExchange(exchange -> {
                    // 动态添加公开路径
                    for (String path : gatewayProperties.getPublicPaths()) {
                        System.out.println("Public path: " + path); // 调试输出
                        exchange.pathMatchers(path).permitAll();
                    }
                    // 其他路径需要认证
                    exchange.anyExchange().authenticated();
                })
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt.jwtDecoder(jwtDecoder())) // 使用 JWT 认证
//                )
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION) // 设置自定义过滤器顺序
                .csrf(ServerHttpSecurity.CsrfSpec::disable);  // 禁用 CSRF
    }

    @Bean
    public ReactiveJwtDecoder jwtDecoder() {
        return new NimbusReactiveJwtDecoder("http://localhost:8080/oauth/check_token");
    }

    @Bean(name = "authWebClient")
    @RefreshScope
    public WebClient authWebClient() {
        return WebClient.builder()
                .baseUrl(gatewayProperties.getAuthUrl())
                .build();
    }
}
