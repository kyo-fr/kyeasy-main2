package org.ares.cloud.api.auth.fallback;

import org.ares.cloud.api.auth.AuthServerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/9/23 17:08
 */
@Component
public class AuthServerClientFallback implements AuthServerClient {
    @Override
    public CompletableFuture<ResponseEntity<Map<String, Object>>> validateToken(String token) {
        // 当认证服务不可用时，返回一个默认的响应或处理
        return CompletableFuture.completedFuture(
                ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new HashMap<>())
        );
    }
}
