package com.ares.cloud.pay.domain.command;

import com.ares.cloud.pay.domain.enums.PaymentError;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.StringUtils;

/**
 * 商户充值命令
 * 封装商户充值的基础校验逻辑
 */
public class MerchantRechargeDomainCommand {
    
    private final String userId;
    private final Money amount;
    private final String paymentRegion;
    private final String description;
    
    /**
     * 私有构造函数
     */
    private MerchantRechargeDomainCommand(String userId, Money amount, String paymentRegion, String description) {
        this.userId = userId;
        this.amount = amount;
        this.paymentRegion = paymentRegion;
        this.description = description;
    }
    
    /**
     * 创建商户充值命令
     */
    public static MerchantRechargeDomainCommand create(String userId, Money amount, String paymentRegion, String description) {
        MerchantRechargeDomainCommand command = new MerchantRechargeDomainCommand(userId, amount, paymentRegion, description);
        command.validate();
        return command;
    }
    
    /**
     * 基础校验
     */
    private void validate() {
        validateUserId();
        validateAmount();
        validatePaymentRegion();
    }
    
    /**
     * 校验用户ID
     */
    private void validateUserId() {
        if (!StringUtils.hasText(userId)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }
    
    /**
     * 校验充值金额
     */
    private void validateAmount() {
        if (amount == null || amount.isZero() || amount.isNegative()) {
            throw new BusinessException(PaymentError.INVALID_PAYMENT_AMOUNT);
        }
    }
    
    /**
     * 校验支付区域
     */
    private void validatePaymentRegion() {
        if (!StringUtils.hasText(paymentRegion)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }
    
    /**
     * 校验账户状态
     */
    public void validateAccountStatus(boolean accountActive) {
        if (!accountActive) {
            throw new BusinessException(PaymentError.ACCOUNT_FROZEN);
        }
    }
    
    /**
     * 校验钱包状态
     */
    public void validateWalletStatus(boolean walletActive) {
        if (!walletActive) {
            throw new BusinessException(PaymentError.WALLET_FROZEN);
        }
    }
    
    // Getter方法
    public String getUserId() {
        return userId;
    }
    
    public Money getAmount() {
        return amount;
    }
    
    public String getPaymentRegion() {
        return paymentRegion;
    }
    
    public String getDescription() {
        return description;
    }
} 