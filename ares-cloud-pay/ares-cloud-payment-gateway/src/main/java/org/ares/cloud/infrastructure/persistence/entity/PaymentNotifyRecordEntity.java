package org.ares.cloud.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * 支付通知记录实体
 */
@Data
@TableName("payment_notify_records")
public class PaymentNotifyRecordEntity {
    
    /**
     * 通知ID
     */
    @TableId(type = IdType.ASSIGN_ID)
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
     * 通知状态
     */
    private String status;
    
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