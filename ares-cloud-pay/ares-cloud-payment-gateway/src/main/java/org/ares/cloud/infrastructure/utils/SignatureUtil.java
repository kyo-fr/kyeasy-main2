package org.ares.cloud.infrastructure.utils;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

@Component
public class SignatureUtil {
    
    public String generateSign(Map<String, String> params, String secret) {
        // 按键排序
        TreeMap<String, String> sortedParams = new TreeMap<>(params);
        
        // 构建签名字符串
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : sortedParams.entrySet()) {
            if (!"sign".equals(entry.getKey()) && StringUtils.hasText(entry.getValue())) {
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        sb.append("key=").append(secret);
        
        // MD5加密
        return DigestUtils.md5DigestAsHex(sb.toString().getBytes(StandardCharsets.UTF_8));
    }
    
    public boolean verifySign(Map<String, String> params, String secret) {
        String providedSign = params.remove("sign");
        String calculatedSign = generateSign(params, secret);
        return calculatedSign.equals(providedSign);
    }
} 