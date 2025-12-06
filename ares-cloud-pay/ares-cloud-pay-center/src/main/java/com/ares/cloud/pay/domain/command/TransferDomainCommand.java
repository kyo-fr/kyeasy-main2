package com.ares.cloud.pay.domain.command;

import com.ares.cloud.pay.domain.enums.PaymentError;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.StringUtils;

/**
 * 转账领域命令
 * 封装用户间转账的基础校验逻辑
 */
public class TransferDomainCommand {
    
    private final String fromUserId;
    private final String toUserId;
    private final Money amount;
    private final String paymentRegion;
    private final String description;
    
    /**
     * 私有构造函数
     */
    private TransferDomainCommand(String fromUserId, String toUserId, Money amount,
                                  String paymentRegion, String description) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.amount = amount;
        this.paymentRegion = paymentRegion;
        this.description = description;
    }
    
    /**
     * 创建转账命令
     */
    public static TransferDomainCommand create(String fromUserId, String toUserId, Money amount,
                                               String paymentRegion, String description) {
        TransferDomainCommand command = new TransferDomainCommand(fromUserId, toUserId, amount, paymentRegion, description);
        command.validate();
        return command;
    }
    
    /**
     * 基础校验
     */
    private void validate() {
        validateFromUserId();
        validateToUserId();
        validateAmount();
        validatePaymentRegion();
        validateSelfTransfer();
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
     * 校验收款方用户ID
     */
    private void validateToUserId() {
        if (!StringUtils.hasText(toUserId)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }
    
    /**
     * 校验转账金额
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
     * 校验不能自己给自己转账
     */
    private void validateSelfTransfer() {
        if (StringUtils.hasText(fromUserId) && StringUtils.hasText(toUserId) 
            && fromUserId.equals(toUserId)) {
            throw new BusinessException(PaymentError.INVALID_PARAMETER);
        }
    }
    
    /**
     * 校验账户状态
     */
    public void validateAccountStatus(boolean fromAccountActive, boolean toAccountActive) {
        if (!fromAccountActive) {
            throw new BusinessException(PaymentError.ACCOUNT_FROZEN);
        }
        if (!toAccountActive) {
            throw new BusinessException(PaymentError.ACCOUNT_FROZEN);
        }
    }
    
    /**
     * 校验钱包状态
     */
    public void validateWalletStatus(boolean fromWalletActive, boolean toWalletActive) {
        if (!fromWalletActive) {
            throw new BusinessException(PaymentError.WALLET_FROZEN);
        }
        if (!toWalletActive) {
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
    
    public String getToUserId() {
        return toUserId;
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