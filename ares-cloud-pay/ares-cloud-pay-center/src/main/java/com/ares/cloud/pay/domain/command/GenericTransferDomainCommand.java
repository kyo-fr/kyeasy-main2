package com.ares.cloud.pay.domain.command;

import lombok.Data;
import org.ares.cloud.common.model.Money;

/**
 * 通用转账领域命令
 * 支持指定交易类型的灵活转账操作
 */
@Data
public class GenericTransferDomainCommand {
    
    /**
     * 转出账户ID
     */
    private String fromAccountId;
    
    /**
     * 转入账户ID
     */
    private String toAccountId;
    
    /**
     * 转账金额
     */
    private Money amount;
    
    /**
     * 支付区域
     */
    private String paymentRegion;
    
    /**
     * 交易类型
     */
    private String transactionType;
    
    /**
     * 交易描述
     */
    private String description;
    
    /**
     * 是否需要验证支付密码
     */
    private Boolean requirePaymentPassword = true;
    
    /**
     * 手续费承担方（FROM: 转出方承担, TO: 转入方承担）
     */
    private String feeBearer = "TO";
    
    /**
     * 创建通用转账命令
     */
    public static GenericTransferDomainCommand create(String fromAccountId, String toAccountId, 
                                                    Money amount, String paymentRegion, 
                                                    String transactionType, String description) {
        GenericTransferDomainCommand command = new GenericTransferDomainCommand();
        command.setFromAccountId(fromAccountId);
        command.setToAccountId(toAccountId);
        command.setAmount(amount);
        command.setPaymentRegion(paymentRegion);
        command.setTransactionType(transactionType);
        command.setDescription(description);
        return command;
    }
    
    /**
     * 创建通用转账命令（带手续费承担方）
     */
    public static GenericTransferDomainCommand create(String fromAccountId, String toAccountId, 
                                                    Money amount, String paymentRegion, 
                                                    String transactionType, String description,
                                                    String feeBearer) {
        GenericTransferDomainCommand command = create(fromAccountId, toAccountId, amount, paymentRegion, transactionType, description);
        command.setFeeBearer(feeBearer);
        return command;
    }
    
    /**
     * 创建通用转账命令（完整参数）
     */
    public static GenericTransferDomainCommand create(String fromAccountId, String toAccountId, 
                                                    Money amount, String paymentRegion, 
                                                    String transactionType, String description,
                                                    Boolean requirePaymentPassword, String feeBearer) {
        GenericTransferDomainCommand command = create(fromAccountId, toAccountId, amount, paymentRegion, transactionType, description);
        command.setRequirePaymentPassword(requirePaymentPassword);
        command.setFeeBearer(feeBearer);
        return command;
    }
}
