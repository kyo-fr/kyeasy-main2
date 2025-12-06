package com.ares.cloud.pay.domain.command;

import com.ares.cloud.pay.domain.enums.PaymentError;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.StringUtils;

/**
 * 商户向用户发放命令
 * 封装商户向用户发放的基础校验逻辑
 */
public class MerchantToUserPaymentDomainCommand {
    
    private final String merchantId;
    private final String userId;
    private final Money amount;
    private final String paymentRegion;
    private final String description;
    
    /**
     * 私有构造函数
     */
    private MerchantToUserPaymentDomainCommand(String merchantId, String userId, Money amount, String paymentRegion, String description) {
        this.merchantId = merchantId;
        this.userId = userId;
        this.amount = amount;
        this.paymentRegion = paymentRegion;
        this.description = description;
    }
    
    /**
     * 创建商户向用户发放命令
     */
    public static MerchantToUserPaymentDomainCommand create(String merchantId, String userId, Money amount, String paymentRegion, String description) {
        MerchantToUserPaymentDomainCommand command = new MerchantToUserPaymentDomainCommand(merchantId, userId, amount, paymentRegion, description);
        command.validate();
        return command;
    }
    
    /**
     * 基础校验
     */
    private void validate() {
        validateMerchantId();
        validateUserId();
        validateAmount();
        validatePaymentRegion();
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
     * 校验用户ID
     */
    private void validateUserId() {
        if (!StringUtils.hasText(userId)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }
    
    /**
     * 校验发放金额
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
     * 校验商户状态
     */
    public void validateMerchantStatus(boolean merchantActive) {
        if (!merchantActive) {
            throw new BusinessException(PaymentError.MERCHANT_FROZEN);
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
    
    /**
     * 校验商户余额是否足够
     */
    public void validateMerchantBalance(Money availableBalance) {
        if (availableBalance == null || !availableBalance.isGreaterThanOrEqual(amount)) {
            throw new BusinessException(PaymentError.MERCHANT_BALANCE_INSUFFICIENT);
        }
    }
    
    // Getter方法
    public String getMerchantId() {
        return merchantId;
    }
    
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