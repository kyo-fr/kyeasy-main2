package com.ares.cloud.pay.infrastructure.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 商户加密测试工具类
 * 用于验证加密功能的正确性
 */
@Component
public class MerchantCryptoTestUtil {

    @Autowired
    private MerchantCryptoUtil merchantCryptoUtil;

    /**
     * 测试密钥生成功能
     */
    public void testKeyGeneration() {
        System.out.println("=== 测试密钥生成功能 ===");
        
        String key1 = merchantCryptoUtil.generateMerchantKey();
        String key2 = merchantCryptoUtil.generateMerchantKey();
        
        System.out.println("生成的密钥1: " + key1);
        System.out.println("生成的密钥2: " + key2);
        System.out.println("密钥是否不同: " + !key1.equals(key2));
        System.out.println();
    }

    /**
     * 测试参数加密解密功能
     */
    public void testEncryptionDecryption() {
        System.out.println("=== 测试参数加密解密功能 ===");
        
        String merchantKey = merchantCryptoUtil.generateMerchantKey();
        String plainText = "amount=100&orderId=123456&timestamp=1640995200000";
        
        System.out.println("原始参数: " + plainText);
        System.out.println("商户密钥: " + merchantKey);
        
        // 加密
        String encryptedText = merchantCryptoUtil.encryptParameter(plainText, merchantKey);
        System.out.println("加密后: " + encryptedText);
        
        // 解密
        String decryptedText = merchantCryptoUtil.decryptParameter(encryptedText, merchantKey);
        System.out.println("解密后: " + decryptedText);
        System.out.println("解密是否正确: " + plainText.equals(decryptedText));
        System.out.println();
    }

    /**
     * 测试签名生成和验证功能
     */
    public void testSignatureGeneration() {
        System.out.println("=== 测试签名生成和验证功能 ===");
        
        String merchantKey = merchantCryptoUtil.generateMerchantKey();
        String params = "amount=100&orderId=123456&timestamp=1640995200000";
        
        System.out.println("参数: " + params);
        System.out.println("商户密钥: " + merchantKey);
        
        // 生成签名
        String signature = merchantCryptoUtil.generateSignature(params, merchantKey);
        System.out.println("生成的签名: " + signature);
        
        // 验证签名
        boolean isValid = merchantCryptoUtil.verifySignature(params, merchantKey, signature);
        System.out.println("签名验证结果: " + isValid);
        
        // 测试错误签名
        boolean isInvalid = merchantCryptoUtil.verifySignature(params, merchantKey, "wrong_signature");
        System.out.println("错误签名验证结果: " + isInvalid);
        System.out.println();
    }

    /**
     * 测试商户号生成功能
     */
    public void testMerchantNoGeneration() {
        System.out.println("=== 测试商户号生成功能 ===");
        
        String merchantNo1 = merchantCryptoUtil.generateMerchantNo();
        String merchantNo2 = merchantCryptoUtil.generateMerchantNo();
        
        System.out.println("生成的商户号1: " + merchantNo1);
        System.out.println("生成的商户号2: " + merchantNo2);
        System.out.println("商户号是否不同: " + !merchantNo1.equals(merchantNo2));
        System.out.println("商户号1格式是否正确: " + merchantNo1.startsWith("M"));
        System.out.println("商户号2格式是否正确: " + merchantNo2.startsWith("M"));
        System.out.println();
    }

    /**
     * 运行所有测试
     */
    public void runAllTests() {
        System.out.println("开始运行商户加密功能测试...\n");
        
        testKeyGeneration();
        testEncryptionDecryption();
        testSignatureGeneration();
        testMerchantNoGeneration();
        
        System.out.println("所有测试完成！");
    }
} 