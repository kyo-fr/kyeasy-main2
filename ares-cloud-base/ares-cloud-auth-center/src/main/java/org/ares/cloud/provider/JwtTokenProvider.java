package org.ares.cloud.provider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.Resource;
import org.ares.cloud.properties.SecurityProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenProvider {
    @Resource
    private SecurityProperties securityProperties;

    private SecretKey secretKey; // 32 字节密钥

    // 生成访问令牌
    public String generateAccessToken(String userId, String tenantId, String role, String scope) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "access");
        claims.put("userId", userId);
        claims.put("tenantId", tenantId);
        claims.put("role", role);
        claims.put("scope", scope);
        return createToken(claims, userId, securityProperties.getAccessTokenExpire());
    }

    // 生成刷新令牌
    public String generateRefreshToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "userId");
        claims.put("userId", userId);
        return createToken(claims, userId, securityProperties.getRefreshTokenExpire());
    }

    // 生成访问令牌
    public String generateAccessToken(Map<String, Object> claims) {
        String userId = claims.get("userId") != null ? claims.get("userId").toString() : "";
        return createToken(claims, userId, securityProperties.getAccessTokenExpire());
    }

    // 生成刷新令牌
    public String generateRefreshToken(Map<String, Object> claims) {
        String userId = claims.get("userId") != null ? claims.get("userId").toString() : "";
        return createToken(claims, userId, securityProperties.getRefreshTokenExpire());
    }

    // 创建令牌的方法
    private String createToken(Map<String, Object> claims, String subject, long expiration) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date())
                .expiration(getExpireData(expiration))
                .claims(claims)  // 使用 addClaims 来添加自定义声明
                .signWith(this.getKey())  // 不再需要指定 SignatureAlgorithm
                .compact();

    }

    // 验证令牌是否有效
    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(this.getKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid or expired JWT token.");
        }
        return false;
    }

    // 从令牌中获取用户名
    public String extractUsername(String token) {
        return Jwts.parser()
                .verifyWith(this.getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    public Map<String, Object> getClaims(String token) {
        return Jwts.parser()
                .verifyWith(this.getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * 生成签名
     *
     * @param sub 签名标识
     * @return 签名
     */
    public String generateSing(String sub) {
        return Jwts.builder()
                .subject(sub)
                .expiration(getExpireData(securityProperties.getSignExpire()))
                .signWith(this.getKey())
                .compact();
    }

    /**
     * 验证签名
     *
     * @param sign 签名
     * @param sub  签名标识
     * @return 验证结果
     */
    public boolean validateSign(String sign, String sub) {
        try {
            String subject = Jwts.parser()
                    .verifyWith(this.getKey())
                    .build()
                    .parseSignedClaims(sign)
                    .getPayload()
                    .getSubject();
            return subject.equals(sub); // 验证手机号是否匹配
        } catch (Exception e) {
            return false;
        }
    }

    // 获取过期时间的方法
    public long getAccessTokenExpiration() {
        return securityProperties.getAccessTokenExpire();
    }

    public long getRefreshTokenExpiration() {
        return securityProperties.getRefreshTokenExpire();
    }

    public String getSecretKey() {
        SecretKey key = this.getKey();
        return key.toString();
    }

    private SecretKey getKey() {
        if (secretKey == null) {
            this.secretKey = Keys.hmacShaKeyFor(this.securityProperties.getKey().getBytes(StandardCharsets.UTF_8));
        }
        return secretKey;
    }

    /**
     * 获取过期时间
     * @param expireSeconds 过期时间单位秒
     * @return 过期的时间
     */
    private Date getExpireData(long expireSeconds) {
        return new Date(System.currentTimeMillis() + expireSeconds * 1000);
    }
}
