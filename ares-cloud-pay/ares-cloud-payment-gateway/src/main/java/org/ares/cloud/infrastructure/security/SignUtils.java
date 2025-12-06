package org.ares.cloud.infrastructure.security;

import org.ares.cloud.common.exception.BusinessException;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SignUtils {
    private static final String HMAC_SHA256 = "HmacSHA256";
    
    public static String generateSign(Map<String, String> params, String signKey) {
        if (params == null || !StringUtils.hasText(signKey)) {
            throw new BusinessException("Invalid parameters for sign generation");
        }
        
        try {
            String signContent = buildSignContent(params);
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec secretKeySpec = new SecretKeySpec(signKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(signContent.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
    
    public static boolean verifySign(Map<String, String> params, String signKey) {
        if (params == null || !StringUtils.hasText(signKey)) {
            return false;
        }
        
        try {
            String sign = params.remove("sign");
            if (!StringUtils.hasText(sign)) {
                return false;
            }
            
            String generatedSign = generateSign(params, signKey);
            return sign.equalsIgnoreCase(generatedSign);
        } catch (Exception e) {
            return false;
        }
    }
    
    private static String buildSignContent(Map<String, String> params) {
        return new TreeMap<>(params).entrySet().stream()
            .filter(entry -> StringUtils.hasText(entry.getValue()) && !entry.getKey().equals("sign"))
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining("&"));
    }
    
    private static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
} 