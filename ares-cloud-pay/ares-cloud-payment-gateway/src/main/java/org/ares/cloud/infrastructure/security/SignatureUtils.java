package org.ares.cloud.infrastructure.security;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class SignatureUtils {
    
    public static String generateSignature(Map<String, String> params, String signKey) {
        // 1. 参数排序
        TreeMap<String, String> sortedParams = new TreeMap<>(params);
        
        // 2. 构建签名字符串
        String signContent = sortedParams.entrySet().stream()
            .filter(entry -> entry.getValue() != null && !entry.getKey().equals("sign"))
            .map(entry -> entry.getKey() + "=" + entry.getValue())
            .collect(Collectors.joining("&"));
            
        // 3. 添加签名密钥
        signContent += "&key=" + signKey;
        
        // 4. MD5加密
        return DigestUtils.md5DigestAsHex(signContent.getBytes(StandardCharsets.UTF_8));
    }
    
    public static void main(String[] args) {
        // 示例代码
        Map<String, String> params = new HashMap<>();
        params.put("merchantId", "M001");
        params.put("amount", "100.00");
        params.put("orderNo", "ORDER123");
        params.put("timestamp", String.valueOf(System.currentTimeMillis()));
        
        String signKey = "your_sign_key";
        String sign = generateSignature(params, signKey);
        params.put("sign", sign);
        
        System.out.println("Signed params: " + params);
    }
} 