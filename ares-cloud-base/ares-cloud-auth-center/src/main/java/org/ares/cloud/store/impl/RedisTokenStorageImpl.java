package org.ares.cloud.store.impl;

import jakarta.annotation.Resource;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.utils.StringUtils;
import org.ares.cloud.enums.AuthError;
import org.ares.cloud.properties.SecurityProperties;
import org.ares.cloud.store.TokenStorage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/9 00:29
 */
@Service
public class RedisTokenStorageImpl implements TokenStorage {
    private static final String tokenKeyPrefix = "token:";
    private static final String refTokenPrefix = "ref_token:";
    private static final String userKeyPrefix = "userKey:";
    private static final String signPrefix = "sign:";
    /**
     * 缓存
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * 认证配置
     */
    @Resource
    private SecurityProperties securityProperties;

    @Override
    public boolean saveTokens(String userId, String token, String refToken) {
        try {
            String tokenKey = generateTokenKey(token); // token 的哈希值
            String refTokenKey = generateTokenKey(refToken); // ref token 的哈希值
            String userKey = userKeyPrefix+ userId; // 用户哈希的键

            // 1. 将 tokenKey 和 refTokenKey 存储到 userKey:userId
            redisTemplate.opsForHash().put( userKey, "tokenKey", tokenKey);
            redisTemplate.opsForHash().put(userKey, "refTokenKey", refTokenKey);

            // 2. 将 userId 存储到 tokenKey 和 refTokenKey 下
            redisTemplate.opsForValue().set(tokenKeyPrefix + tokenKey, userId,securityProperties.getAccessTokenExpire(), TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(refTokenPrefix + refTokenKey, userId,securityProperties.getRefreshTokenExpire(), TimeUnit.SECONDS);
            // 3. 设置 userKey 的过期时间
            redisTemplate.expire(userKey, Math.max(securityProperties.getAccessTokenExpire(), securityProperties.getRefreshTokenExpire()), TimeUnit.SECONDS);
        } catch (Exception e) {
            e.fillInStackTrace();
            return false;
        }
        return true;
    }


    @Override
    public boolean chickTokenExist(String token) {
        String tokenKey = generateTokenKey(token);
        // 两者都存在才返回 true
        return Boolean.TRUE.equals(redisTemplate.hasKey(tokenKeyPrefix + tokenKey));
    }

    @Override
    public boolean chickRefTokenExist(String refToken) {
        String refTokenKey = generateTokenKey(refToken);
        return Boolean.TRUE.equals(redisTemplate.hasKey(refTokenPrefix + refTokenKey));
    }

    @Override
    public boolean delToken(String token) {
        try {
            String tokenKey = generateTokenKey(token);
            String userId = (String) redisTemplate.opsForValue().get(tokenKeyPrefix + tokenKey);
            if (userId != null) {
                // 1. 删除 tokenKey
                redisTemplate.delete(tokenKeyPrefix + tokenKey);

                // 2. 获取 refTokenKey 并删除 refToken
                String refTokenKey = (String) redisTemplate.opsForHash().get(userKeyPrefix + userId, "refTokenKey");
                if (refTokenKey != null) {
                    redisTemplate.delete(refTokenPrefix + refTokenKey);
                }

                // 3. 删除 userKey:userId 下的 tokenKey 和 refTokenKey
                redisTemplate.opsForHash().delete(userKeyPrefix + userId, "tokenKey", "refTokenKey");
            }
        }catch (Exception e){
            e.fillInStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delUser(String userId) {
        try {
            // 1. 获取 tokenKey 和 refTokenKey
            String tokenKey = (String) redisTemplate.opsForHash().get(userKeyPrefix + userId, "tokenKey");
            String refTokenKey = (String) redisTemplate.opsForHash().get(userKeyPrefix + userId, "refTokenKey");

            // 2. 删除 tokenKey 和 refTokenKey
            if (tokenKey != null) {
                redisTemplate.delete(tokenKeyPrefix + tokenKey);
            }
            if (refTokenKey != null) {
                redisTemplate.delete(refTokenPrefix + refTokenKey);
            }

            // 3. 删除 userKey:userId 下的 tokenKey 和 refTokenKey
            redisTemplate.opsForHash().delete(userKeyPrefix + userId, "tokenKey", "refTokenKey");
        }catch (Exception e){
            e.fillInStackTrace();
            return false;
        }
       return true;
    }

    @Override
    public boolean saveSign(String sign) {
        try {
            String signKey = generateTokenKey(sign);
            redisTemplate.opsForValue().set(signPrefix + signKey, sign, securityProperties.getSignExpire(), TimeUnit.SECONDS);
        }catch (Exception e){
            e.fillInStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean saveSign(String key, String sign) {
        try {
            redisTemplate.opsForValue().set(signPrefix + key, sign, securityProperties.getSignExpire(), TimeUnit.SECONDS);
        }catch (Exception e){
            e.fillInStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean chickSignExist(String sign) {
        String signKey = generateTokenKey(sign);
        // 两者都存在才返回 true
        return Boolean.TRUE.equals(redisTemplate.hasKey(signPrefix + signKey));
    }
    @Override
    public  boolean chickSignExist(String key,String sign){
        Object o = redisTemplate.opsForValue().get(signPrefix + key);
        if (o == null) {
            return false;
        }
        delSign(sign);

        return StringUtils.isNotBlank(sign) && sign.equals(o.toString());
    }
    @Override
    public boolean delSign(String sign) {
        String signKey = generateTokenKey(sign);
        return Boolean.TRUE.equals(redisTemplate.delete(signPrefix + signKey));
    }


    // 生成 SHA-256 哈希作为 token key
    private String generateTokenKey(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            // 将哈希结果转换为16进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.fillInStackTrace();
           throw new BusinessException(AuthError.TOKEN_SAVE_ERROR);
        }
    }
}
