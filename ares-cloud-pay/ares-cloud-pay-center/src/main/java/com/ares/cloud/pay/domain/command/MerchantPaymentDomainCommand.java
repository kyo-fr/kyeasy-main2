package com.ares.cloud.pay.domain.command;

import com.ares.cloud.pay.domain.enums.PaymentError;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.StringUtils;

/**
 * 向商户付款命令
 * 封装向商户付款的基础校验逻辑
 */
public class MerchantPaymentDomainCommand {
    
    private final String fromUserId;
    private final String merchantId;
    private final Money amount;
    private final String paymentRegion;
    private final String description;
    
    /**
     * 私有构造函数
     */
    private MerchantPaymentDomainCommand(String fromUserId, String merchantId, Money amount,
                                         String paymentRegion, String description) {
        this.fromUserId = fromUserId;
        this.merchantId = merchantId;
        this.amount = amount;
        this.paymentRegion = paymentRegion;
        this.description = description;
    }
    
    /**
     * 创建商户付款命令
     */
    public static MerchantPaymentDomainCommand create(String fromUserId, String merchantId, Money amount,
                                                      String paymentRegion, String description) {
        MerchantPaymentDomainCommand command = new MerchantPaymentDomainCommand(fromUserId, merchantId, amount, paymentRegion, description);
        command.validate();
        return command;
    }
    
    /**
     * 基础校验
     */
    private void validate() {
        validateFromUserId();
        validateMerchantId();
        validateAmount();
        validatePaymentRegion();
    }
    
    /**
     * 校验付款方用户ID
     */
    private void validateFromUserId() {
        if (!StringUtils.hasText(fromUserId)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
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
     * 校验付款金额
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
    public void validateAccountStatus(boolean fromAccountActive, boolean merchantAccountActive) {
        if (!fromAccountActive) {
            throw new BusinessException(PaymentError.ACCOUNT_FROZEN);
        }
        if (!merchantAccountActive) {
            throw new BusinessException(PaymentError.ACCOUNT_FROZEN);
        }
    }
    
    /**
     * 校验钱包状态
     */
    public void validateWalletStatus(boolean fromWalletActive, boolean merchantWalletActive) {
        if (!fromWalletActive) {
            throw new BusinessException(PaymentError.WALLET_FROZEN);
        }
        if (!merchantWalletActive) {
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
    public String getFromUserId() {
        return fromUserId;
    }
    
    public String getMerchantId() {
        return merchantId;
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