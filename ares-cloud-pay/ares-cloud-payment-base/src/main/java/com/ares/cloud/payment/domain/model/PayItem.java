package com.ares.cloud.payment.domain.model;

import org.ares.cloud.common.model.Money;
import lombok.Data;
import lombok.Builder;

/**
 * 支付项领域模型
 */
@Data
@Builder
public class PayItem {
    /**
     * 支付项ID
     */
    private String id;

    
    /**
     * 交易流水号
     */
    private String tradeNo;
    
    /**
     * 支付金额
     */
    private Money amount;
    
    /**
     * 支付时间
     */
    private Long payTime;
    
    /**
     * 支付状态(0:未支付,1:已支付,2:支付失败)
     */
    private Integer status;
    
    /**
     * 关联发票ID
     */
    private String invoiceId;
    /**
     * 支付渠道ID
     */
    private String channelId;

    /**
     * 备注
     */
    private String remark;
    
    /**
     * 验证支付项是否有效
     */
    public boolean isValid() {
        if (channelId == null || channelId.isEmpty()) {
            return false;
        }
        
        if (amount == null || amount.isZero() || amount.isNegative()) {
            return false;
        }
        if (status == null || (status != 0 && status != 1 && status != 2)) {
            return false;
        }
        
        return true;
    }
    
    /**
     * 计算支付项的手续费
     */
    public Money calculateFee(double rate) {
        if (rate < 0 || rate > 1) {
            throw new IllegalArgumentException("费率必须在0到1之间");
        }
        return amount.multiply(rate);
    }
    
    /**
     * 更新支付状态
     */
    public void updateStatus(Integer newStatus) {
        if (newStatus == null || (newStatus != 0 && newStatus != 1 && newStatus != 2)) {
            throw new IllegalArgumentException("无效的支付状态");
        }
        this.status = newStatus;
    }
    
    /**
     * 完成支付
     */
    public void completePayment(String tradeNo, Long payTime) {
        if (tradeNo == null || tradeNo.isEmpty()) {
            throw new IllegalArgumentException("交易流水号不能为空");
        }
        if (payTime == null || payTime <= 0) {
            throw new IllegalArgumentException("支付时间无效");
        }
        this.tradeNo = tradeNo;
        this.payTime = payTime;
        this.status = 1;
    }
    
    /**
     * 支付失败
     */
    public void failPayment(String reason) {
        this.status = 2;
        this.remark = reason;
    }
}