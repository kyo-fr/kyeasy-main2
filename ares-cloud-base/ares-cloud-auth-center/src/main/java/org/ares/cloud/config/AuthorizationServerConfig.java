package org.ares.cloud.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import jakarta.annotation.Resource;
import org.ares.cloud.provider.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;
import java.time.Duration;
import java.util.Map;

@Configuration
public class AuthorizationServerConfig {
    @Resource
    private JwtTokenProvider jwtTokenProvider; // 注入自定义令牌生成器
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        RegisteredClient registeredClient = RegisteredClient.withId("client-id")
                .clientId("client-id")
                .clientSecret("{noop}client-secret") // 密码前使用 {noop} 以避免加密
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://localhost:8080/login/oauth2/code/my-client") // 确保设置了重定向 URI
                .scope("read")
                .scope("write")
                .tokenSettings(tokenSettings())
                .build();
        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public TokenSettings tokenSettings() {
        return TokenSettings.builder()
                .accessTokenTimeToLive(Duration.ofSeconds(jwtTokenProvider.getAccessTokenExpiration()))
                .refreshTokenTimeToLive(Duration.ofSeconds(jwtTokenProvider.getRefreshTokenExpiration()))
                .build();
    }
    @Bean
    public JwtEncoder jwtEncoder() {
        // 使用自定义的密钥配置 JwtEncoder
        SecretKeySpec secretKey = new SecretKeySpec(jwtTokenProvider.getSecretKey().getBytes(), "HmacSHA256");
        return new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));
    }
    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        return http.build();
    }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
        return (context) -> {
            Map<String, Object> claims = context.getClaims().build().getClaims();
            if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                claims.put("custom_claim", "custom_value"); // 添加自定义声明
            }
        };
    }

}