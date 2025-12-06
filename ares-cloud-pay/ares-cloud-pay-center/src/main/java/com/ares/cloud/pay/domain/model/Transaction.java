package com.ares.cloud.pay.domain.model;

import com.ares.cloud.pay.domain.enums.PaymentError;
import lombok.Data;
import lombok.experimental.Accessors;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Money;

/**
 * 交易领域模型
 */
@Data
@Accessors(chain = true)
public class Transaction {
    
    /**
     * 交易ID
     */
    private String id;
    
    /**
     * 来源账户ID
     */
    private String fromAccountId;
    
    /**
     * 目标账户ID
     */
    private String toAccountId;
    
    /**
     * 支付订单ID
     */
    private String orderId;
    
    /**
     * 交易金额
     */
    private Money amount;
    
    /**
     * 支付区域（EUR/USD/CNY/CHF/GBP）
     */
    private String paymentRegion;
    
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
     * 交易类型
     */
    private String type;
    
    /**
     * 交易状态
     */
    private String status;
    
    /**
     * 交易描述
     */
    private String description;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 更新时间
     */
    private Long updateTime;
    
    /**
     * 检查交易是否成功
     */
    public boolean isSuccess() {
        return "SUCCESS".equals(status);
    }
    
    /**
     * 检查交易是否失败
     */
    public boolean isFailed() {
        return "FAILED".equals(status);
    }
    
    /**
     * 检查交易是否处理中
     */
    public boolean isProcessing() {
        return "PROCESSING".equals(status);
    }
    
    /**
     * 获取交易总金额（包含手续费）
     */
    public Money getTotalAmount() {
        Money fee = feeAmount != null ? feeAmount : Money.zeroMoney(paymentRegion);
        return amount.add(fee);
    }
    
    /**
     * 执行交易
     */
    public void execute() {
        if (isProcessing()) {
            this.status = "SUCCESS";
        this.updateTime = System.currentTimeMillis();
        } else {
            throw new BusinessException(PaymentError.TRANSACTION_FAILED);
        }
        }
        
    /**
     * 交易失败
     */
    public void fail() {
        this.status = "FAILED";
        this.updateTime = System.currentTimeMillis();
    }
    
    /**
     * 交易取消
     */
    public void cancel() {
        this.status = "CANCELLED";
        this.updateTime = System.currentTimeMillis();
    }
    
    /**
     * 交易退款
     */
    public void refund() {
        this.status = "REFUNDED";
        this.updateTime = System.currentTimeMillis();
    }
} 