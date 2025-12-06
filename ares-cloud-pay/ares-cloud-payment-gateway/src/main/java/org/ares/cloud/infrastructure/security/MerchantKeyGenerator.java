package org.ares.cloud.infrastructure.security;

import org.springframework.stereotype.Component;
import java.security.SecureRandom;
import java.util.Base64;

@Component
public class MerchantKeyGenerator {
    private static final int KEY_LENGTH = 32;
    private final SecureRandom secureRandom = new SecureRandom();
    
    /**
     * 生成商户签名密钥
     */
    public String generateSignKey() {
        byte[] bytes = new byte[KEY_LENGTH];
        secureRandom.nextBytes(bytes);
        return Base64.getEncoder().encodeToString(bytes);
    }
    
    /**
     * 生成商户API密钥
     */
    public String generateApiKey() {
        byte[] bytes = new byte[KEY_LENGTH];
        secureRandom.nextBytes(bytes);
        return bytesToHex(bytes);
    }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
} 