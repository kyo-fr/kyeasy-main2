package com.ares.cloud.pay.domain.command;

import com.ares.cloud.pay.domain.enums.PaymentError;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.StringUtils;

/**
 * 商户回收命令
 * 封装商户回收的基础校验逻辑
 */
public class MerchantDeductionDomainCommand {
    
    private final String userId;
    private final Money amount;
    private final String paymentRegion;
    private final String description;
    
    /**
     * 私有构造函数
     */
    private MerchantDeductionDomainCommand(String userId, Money amount, String paymentRegion, String description) {
        this.userId = userId;
        this.amount = amount;
        this.paymentRegion = paymentRegion;
        this.description = description;
    }
    
    /**
     * 创建商户回收命令
     */
    public static MerchantDeductionDomainCommand create(String userId, Money amount, String paymentRegion, String description) {
        MerchantDeductionDomainCommand command = new MerchantDeductionDomainCommand(userId, amount, paymentRegion, description);
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
     * 校验回收金额
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
    
    /**
     * 校验余额是否足够
     */
    public void validateBalance(Money availableBalance) {
        if (availableBalance == null || !availableBalance.isGreaterThanOrEqual(amount)) {
            throw new BusinessException(PaymentError.ACCOUNT_BALANCE_INSUFFICIENT);
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