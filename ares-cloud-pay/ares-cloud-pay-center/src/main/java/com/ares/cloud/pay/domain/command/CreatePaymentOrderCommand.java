package com.ares.cloud.pay.domain.command;

import com.ares.cloud.pay.domain.enums.PaymentError;
import lombok.Getter;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建支付订单命令
 * 封装创建支付订单的基础校验逻辑，支持接入方自定义参数
 */
@Getter
public class CreatePaymentOrderCommand {
    
    private final String merchantId;
    private final String merchantOrderNo;
    private final Money amount;
    private final String paymentRegion;
    private final String subject;
    private final String description;
    private final String returnUrl;
    private final String notifyUrl;
    private final Long expireTime;
    private String sign;
    private final Map<String, String> customParams;
    
    /**
     * 私有构造函数
     */
    private CreatePaymentOrderCommand(String merchantId, String merchantOrderNo, Money amount,
                                    String paymentRegion, String subject, String description,
                                    String returnUrl, String notifyUrl, Long expireTime, String sign,
                                    Map<String, String> customParams) {
        this.merchantId = merchantId;
        this.merchantOrderNo = merchantOrderNo;
        this.amount = amount;
        this.paymentRegion = paymentRegion;
        this.subject = subject;
        this.description = description;
        this.returnUrl = returnUrl;
        this.notifyUrl = notifyUrl;
        this.expireTime = expireTime;
        this.sign = sign;
        this.customParams = customParams != null ? new HashMap<>(customParams) : new HashMap<>();
    }
    
    /**
     * 创建支付订单命令
     */
    public static CreatePaymentOrderCommand create(String merchantId, String merchantOrderNo, Money amount,
                                                 String paymentRegion, String subject, String description,
                                                 String returnUrl, String notifyUrl, Long expireTime, String sign) {
        return create(merchantId, merchantOrderNo, amount, paymentRegion, subject, description,
                     returnUrl, notifyUrl, expireTime, sign, null);
    }
    
    /**
     * 创建支付订单命令（带自定义参数）
     */
    public static CreatePaymentOrderCommand create(String merchantId, String merchantOrderNo, Money amount,
                                                 String paymentRegion, String subject, String description,
                                                 String returnUrl, String notifyUrl, Long expireTime, String sign,
                                                 Map<String, String> customParams) {
        CreatePaymentOrderCommand command = new CreatePaymentOrderCommand(
            merchantId, merchantOrderNo, amount, paymentRegion, subject, description,
            returnUrl, notifyUrl, expireTime, sign, customParams
        );
        command.validate();
        return command;
    }

    public void addSign(String sign) {
        this.sign = sign;
    }
    
    /**
     * 基础校验
     */
    private void validate() {
        validateMerchantId();
        validateOrderNo();
        validateAmount();
        validatePaymentRegion();
        validateSubject();
        validateUrls();
        validateExpireTime();
        validateCustomParams();
    }
    
    /**
     * 校验商户ID
     */
    private void validateMerchantId() {
        if (!StringUtils.hasText(merchantId)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }
    
    /**
     * 校验订单号
     */
    private void validateOrderNo() {
        if (!StringUtils.hasText(merchantOrderNo)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        // 订单号长度限制
        if (merchantOrderNo.length() > 64) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        // 订单号格式校验（只允许字母、数字、下划线、中划线）
        if (!merchantOrderNo.matches("^[a-zA-Z0-9_-]+$")) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }
    
    /**
     * 校验支付金额
     */
    private void validateAmount() {
        if (amount == null || amount.isZero() || amount.isNegative()) {
            throw new BusinessException(PaymentError.INVALID_PAYMENT_AMOUNT);
        }
        // 金额上限校验（100万）
        Money maxAmount = Money.of(java.math.BigDecimal.valueOf(100000000), amount.getCurrency());
        if (amount.isGreaterThan(maxAmount)) {
            throw new BusinessException(PaymentError.PAYMENT_AMOUNT_EXCEEDED);
        }
    }
    
    /**
     * 校验支付区域
     */
    private void validatePaymentRegion() {
        if (!StringUtils.hasText(paymentRegion)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        // 支付区域格式校验（3位字母代码）
        if (!paymentRegion.matches("^[A-Z]{3}$")) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }
    
    /**
     * 校验订单标题
     */
    private void validateSubject() {
        if (!StringUtils.hasText(subject)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
        // 标题长度限制
        if (subject.length() > 128) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }
    
    /**
     * 校验URL地址
     */
    private void validateUrls() {
        // 返回地址校验
        if (StringUtils.hasText(returnUrl)) {
            if (!isValidUrl(returnUrl)) {
                throw new BusinessException(PaymentError.INVALID_PARAMETER);
            }
        }
        
        // 通知地址校验
        if (StringUtils.hasText(notifyUrl)) {
            if (!isValidUrl(notifyUrl)) {
                throw new BusinessException(PaymentError.INVALID_PARAMETER);
            }
        }
    }
    
    /**
     * 校验过期时间
     */
    private void validateExpireTime() {
        if (expireTime != null) {
            long currentTime = System.currentTimeMillis();
            // 过期时间不能早于当前时间
            if (expireTime <= currentTime) {
                throw new BusinessException(PaymentError.INVALID_PARAMETER);
            }
            // 过期时间不能超过24小时
            if (expireTime > currentTime + 24 * 60 * 60 * 1000) {
                throw new BusinessException(PaymentError.INVALID_PARAMETER);
            }
        }
    }
    
    /**
     * 校验自定义参数
     */
    private void validateCustomParams() {
        if (customParams != null) {
            // 自定义参数数量限制
            if (customParams.size() > 20) {
                throw new BusinessException(PaymentError.INVALID_PARAMETER);
            }
            
            // 校验每个自定义参数
            for (Map.Entry<String, String> entry : customParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                
                // 参数名不能为空
                if (!StringUtils.hasText(key)) {
                    throw new BusinessException(PaymentError.INVALID_PARAMETER);
                }
                
                // 参数名长度限制
                if (key.length() > 32) {
                    throw new BusinessException(PaymentError.INVALID_PARAMETER);
                }
                
                // 参数名格式校验（只允许字母、数字、下划线）
                if (!key.matches("^[a-zA-Z0-9_]+$")) {
                    throw new BusinessException(PaymentError.INVALID_PARAMETER);
                }
                
                // 参数值长度限制
                if (value != null && value.length() > 512) {
                    throw new BusinessException(PaymentError.INVALID_PARAMETER);
                }
                
                // 禁止使用系统保留参数名
                if (isReservedParamName(key)) {
                    throw new BusinessException(PaymentError.INVALID_PARAMETER);
                }
            }
        }
    }
    
    /**
     * 检查是否为系统保留参数名
     */
    private boolean isReservedParamName(String paramName) {
        String[] reservedNames = {
            "merchantId", "merchantOrderNo", "amount", "currency", "paymentRegion",
            "subject", "description", "returnUrl", "notifyUrl", "expireTime", 
            "sign", "timestamp", "signature"
        };
        
        for (String reserved : reservedNames) {
            if (reserved.equalsIgnoreCase(paramName)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 校验URL格式
     */
    private boolean isValidUrl(String url) {
        try {
            new java.net.URL(url);
            return url.startsWith("http://") || url.startsWith("https://");
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * 校验签名
     */
    public void validateSign(String expectedSign) {
        if (!StringUtils.hasText(sign)) {
            throw new BusinessException(PaymentError.INVALID_SIGNATURE);
        }
        if (!StringUtils.hasText(expectedSign)) {
            throw new BusinessException(PaymentError.INVALID_SIGNATURE);
        }
        if (!sign.equals(expectedSign)) {
            throw new BusinessException(PaymentError.INVALID_SIGNATURE);
        }
    }
    
    /**
     * 校验商户权限
     */
    public void validateMerchantPermission(String actualMerchantId) {
        if (!StringUtils.hasText(actualMerchantId)) {
            throw new BusinessException(PaymentError.MERCHANT_NOT_FOUND);
        }
        if (!merchantId.equals(actualMerchantId)) {
            throw new BusinessException(PaymentError.UNAUTHORIZED);
        }
    }
    
    /**
     * 添加自定义参数
     */
    public void addCustomParam(String key, String value) {
        if (StringUtils.hasText(key) && !isReservedParamName(key)) {
            customParams.put(key, value);
        }
    }
    
    /**
     * 获取自定义参数
     */
    public String getCustomParam(String key) {
        return customParams.get(key);
    }
    
    /**
     * 获取所有自定义参数
     */
    public Map<String, String> getCustomParams() {
        return new HashMap<>(customParams);
    }
    
    /**
     * 检查是否包含自定义参数
     */
    public boolean hasCustomParam(String key) {
        return customParams.containsKey(key);
    }
    
    /**
     * 移除自定义参数
     */
    public void removeCustomParam(String key) {
        customParams.remove(key);
    }
    
    /**
     * 清空所有自定义参数
     */
    public void clearCustomParams() {
        customParams.clear();
    }
    
    /**
     * 获取自定义参数数量
     */
    public int getCustomParamCount() {
        return customParams.size();
    }
} 