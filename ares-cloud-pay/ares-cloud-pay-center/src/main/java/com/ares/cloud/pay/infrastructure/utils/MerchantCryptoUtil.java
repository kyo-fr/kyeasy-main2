package com.ares.cloud.pay.infrastructure.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

/**
 * 商户加密工具类
 * 提供密钥生成、参数加密和解密功能
 */
@Component
public class MerchantCryptoUtil {

    private static final String ALGORITHM = "AES";
    private static final String CIPHER_TRANSFORM = "AES/ECB/PKCS5Padding";
    private static final int KEY_SIZE = 256;

    /**
     * 生成商户密钥
     * 
     * @return 生成的密钥（Base64编码）
     */
    public String generateMerchantKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            SecureRandom secureRandom = new SecureRandom();
            keyGen.init(KEY_SIZE, secureRandom);
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("生成商户密钥失败", e);
        }
    }

    /**
     * 使用商户密钥加密参数
     * 
     * @param plainText 明文参数
     * @param merchantKey 商户密钥（Base64编码）
     * @return 加密后的参数（Base64编码）
     */
    public String encryptParameter(String plainText, String merchantKey) {
        if (StringUtils.isBlank(plainText) || StringUtils.isBlank(merchantKey)) {
            throw new IllegalArgumentException("明文参数和商户密钥不能为空");
        }

        try {
            byte[] keyBytes = Base64.getDecoder().decode(merchantKey);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
            
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("参数加密失败", e);
        }
    }

    /**
     * 使用商户密钥解密参数
     * 
     * @param encryptedText 加密的参数（Base64编码）
     * @param merchantKey 商户密钥（Base64编码）
     * @return 解密后的明文参数
     */
    public String decryptParameter(String encryptedText, String merchantKey) {
        if (StringUtils.isBlank(encryptedText) || StringUtils.isBlank(merchantKey)) {
            throw new IllegalArgumentException("加密参数和商户密钥不能为空");
        }

        try {
            byte[] keyBytes = Base64.getDecoder().decode(merchantKey);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, ALGORITHM);
            
            Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("参数解密失败", e);
        }
    }

    /**
     * 生成参数签名
     * 
     * @param params 参数字符串
     * @param merchantKey 商户密钥
     * @return 签名（MD5）
     */
    public String generateSignature(String params, String merchantKey) {
        if (StringUtils.isBlank(params) || StringUtils.isBlank(merchantKey)) {
            throw new IllegalArgumentException("参数和商户密钥不能为空");
        }

        try {
            String signString = params + "&key=" + merchantKey;
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(signString.getBytes(StandardCharsets.UTF_8));
            
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().toUpperCase();
        } catch (Exception e) {
            throw new RuntimeException("生成签名失败", e);
        }
    }

    /**
     * 验证参数签名
     * 
     * @param params 参数字符串
     * @param merchantKey 商户密钥
     * @param signature 签名
     * @return 验证结果
     */
    public boolean verifySignature(String params, String merchantKey, String signature) {
        if (StringUtils.isBlank(params) || StringUtils.isBlank(merchantKey) || StringUtils.isBlank(signature)) {
            return false;
        }

        String expectedSignature = generateSignature(params, merchantKey);
        return expectedSignature.equals(signature.toUpperCase());
    }
    
    /**
     * 验证参数签名（Map版本）
     * 
     * @param params 参数Map
     * @param merchantKey 商户密钥
     * @param signature 签名
     * @return 验证结果
     */
    public boolean verifySignature(Map<String, String> params, String merchantKey, String signature) {
        if (params == null || params.isEmpty() || StringUtils.isBlank(merchantKey) || StringUtils.isBlank(signature)) {
            return false;
        }

        String plainText = buildEncryptedPlainText(params);
        return verifySignature(plainText, merchantKey, signature);
    }

    /**
     * 生成随机字符串
     * 
     * @param length 字符串长度
     * @return 随机字符串
     */
    public String generateRandomString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("长度必须大于0");
        }

        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        
        return sb.toString();
    }

    /**
     * 生成商户号
     * 
     * @return 商户号
     */
    public String generateMerchantNo() {
        // 生成格式：M + 时间戳 + 6位随机数
        long timestamp = System.currentTimeMillis();
        String random = generateRandomString(6);
        return "M" + timestamp + random;
    }

    /**
     * 构建加密的明文参数，Map的key按照首字母排序
     * 
     * @param params 参数Map
     * @return 加密后的明文参数
     */
    public String buildEncryptedPlainText(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            throw new IllegalArgumentException("参数不能为空");
        }

        TreeMap<String, String> sortedParams = new TreeMap<>(params);
        StringBuilder sb = new StringBuilder();
        
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        
        String plainText = sb.toString();
        if (plainText.endsWith("&")) {
            plainText = plainText.substring(0, plainText.length() - 1);
        }
        
        return plainText;
    }
    
    /**
     * 构建并加密参数，Map的key按照首字母排序
     * 
     * @param params 参数Map
     * @param merchantKey 商户密钥
     * @return 加密后的参数
     */
    public String buildAndEncryptParams(Map<String, String> params, String merchantKey) {
        String plainText = buildEncryptedPlainText(params);
        return encryptParameter(plainText, merchantKey);
    }
    
    /**
     * 构建参数并生成签名，Map的key按照首字母排序
     * 
     * @param params 参数Map
     * @param merchantKey 商户密钥
     * @return 签名
     */
    public String buildParamsAndSign(Map<String, String> params, String merchantKey) {
        String plainText = buildEncryptedPlainText(params);
        return generateSignature(plainText, merchantKey);
    }
} 