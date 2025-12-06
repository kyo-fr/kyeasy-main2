package com.ares.cloud.pay.domain.model;

import lombok.Data;
import org.ares.cloud.common.model.Money;

/**
 * 账户流水领域模型
 */
@Data
public class AccountFlow {
    
    /**
     * 流水ID
     */
    private String id;
    
    /**
     * 账户ID
     */
    private String accountId;
    
    /**
     * 交易ID
     */
    private String transactionId;
    
    /**
     * 流水类型
     */
    private String flowType;
    
    /**
     * 交易类型（记录资金用途）
     */
    private String transactionType;
    
    /**
     * 变动金额
     */
    private Money amount;
    
    /**
     * 变动前余额
     */
    private Money balanceBefore;
    
    /**
     * 变动后余额
     */
    private Money balanceAfter;
    
    /**
     * 手续费金额
     */
    private Money feeAmount;
    
    /**
     * 手续费百分比（以万分比为单位，如100表示1%）
     */
    private Integer feeRate;
    
    /**
     * 实际到账金额（扣除手续费后的金额）
     */
    private Money actualAmount;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 创建者
     */
    private String creator;
    
    /**
     * 更新者
     */
    private String updater;
    
    /**
     * 更新时间
     */
    private Long updateTime;
    
    /**
     * 版本号
     */
    private Integer version;
    
    /**
     * 删除标记
     */
    private Integer deleted;
    
    /**
     * 创建账户流水
     */
    public static AccountFlow create(String accountId, String transactionId, String flowType, String transactionType,
                                   Money amount, Money balanceBefore, Money balanceAfter,
                                   Money feeAmount, Integer feeRate, Money actualAmount) {
        AccountFlow accountFlow = new AccountFlow();
        accountFlow.setId(org.ares.cloud.common.utils.IdUtils.fastSimpleUUID());
        accountFlow.setAccountId(accountId);
        accountFlow.setTransactionId(transactionId);
        accountFlow.setFlowType(flowType);
        accountFlow.setTransactionType(transactionType);
        accountFlow.setAmount(amount);
        accountFlow.setBalanceBefore(balanceBefore);
        accountFlow.setBalanceAfter(balanceAfter);
        accountFlow.setFeeAmount(feeAmount);
        accountFlow.setFeeRate(feeRate);
        accountFlow.setActualAmount(actualAmount);
        accountFlow.setCreateTime(System.currentTimeMillis());
        accountFlow.setUpdateTime(System.currentTimeMillis());
        accountFlow.setVersion(1);
        accountFlow.setDeleted(0);
        return accountFlow;
    }
    
    /**
     * 创建转出流水
     */
    public static AccountFlow createOutFlow(String accountId, String transactionId, String transactionType,
                                          Money amount, Money balanceBefore, Money balanceAfter,
                                          Money feeAmount, Integer feeRate, Money actualAmount) {
        return create(accountId, transactionId, "OUT", transactionType, amount, balanceBefore, balanceAfter, feeAmount, feeRate, actualAmount);
    }
    
    /**
     * 创建转入流水
     */
    public static AccountFlow createInFlow(String accountId, String transactionId, String transactionType,
                                         Money amount, Money balanceBefore, Money balanceAfter,
                                         Money feeAmount, Integer feeRate, Money actualAmount) {
        return create(accountId, transactionId, "IN", transactionType, amount, balanceBefore, balanceAfter, feeAmount, feeRate, actualAmount);
    }
    
    /**
     * 创建转出流水（兼容旧版本，使用默认交易类型）
     */
    public static AccountFlow createOutFlow(String accountId, String transactionId, 
                                          Money amount, Money balanceBefore, Money balanceAfter,
                                          Money feeAmount, Integer feeRate, Money actualAmount) {
        return create(accountId, transactionId, "OUT", "USER_TO_USER", amount, balanceBefore, balanceAfter, feeAmount, feeRate, actualAmount);
    }
    
    /**
     * 创建转入流水（兼容旧版本，使用默认交易类型）
     */
    public static AccountFlow createInFlow(String accountId, String transactionId, 
                                         Money amount, Money balanceBefore, Money balanceAfter,
                                         Money feeAmount, Integer feeRate, Money actualAmount) {
        return create(accountId, transactionId, "IN", "USER_TO_USER", amount, balanceBefore, balanceAfter, feeAmount, feeRate, actualAmount);
    }
} 