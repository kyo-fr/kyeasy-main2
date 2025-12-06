package org.ares.cloud.domain.model;

import lombok.Builder;
import lombok.Data;
import org.ares.cloud.domain.model.enums.PaymentStatus;
import java.time.LocalDateTime;

/**
 * 支付通知记录值对象
 */
@Data
@Builder
public class PaymentNotifyRecord {
    
    /**
     * 通知ID
     */
    private String notifyId;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 商户ID
     */
    private String merchantId;
    
    /**
     * 通知参数
     */
    private String notifyParams;
    
    /**
     * 支付状态
     */
    private PaymentStatus status;
    
    /**
     * 通知结果
     */
    private String notifyResult;
    
    /**
     * 通知次数
     */
    private Integer retryCount;
    
    /**
     * 下次通知时间
     */
    private LocalDateTime nextRetryTime;
    
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
} 