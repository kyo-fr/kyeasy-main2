package com.ares.cloud.pay.domain.model;

import lombok.Data;

/**
 * 支付通知模型
 */
@Data
public class PaymentNotify {
    
    /**
     * 通知ID
     */
    private String id;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 通知类型
     */
    private String notifyType;
    
    /**
     * 通知状态
     */
    private String status;
    
    /**
     * 通知次数
     */
    private Integer notifyCount;
    
    /**
     * 下次通知时间
     */
    private Long nextNotifyTime;
    
    /**
     * 通知URL
     */
    private String notifyUrl;
    
    /**
     * 通知内容
     */
    private String notifyContent;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 更新时间
     */
    private Long updateTime;
} 