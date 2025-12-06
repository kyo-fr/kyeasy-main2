package org.ares.cloud.domain.model;

import lombok.Data;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 支付元数据
 * 
 * @author hugo
 * @version 1.0.0
 * @date 2024/03/15
 */
@Data
@Schema(description = "支付元数据")
public class PaymentMetadata {
    /**
     * 支付渠道交易号
     */
    private String channelTradeNo;
    
    /**
     * 支付完成时间
     */
    private LocalDateTime paidTime;
    
    /**
     * 支付回调通知地址
     */
    private String notifyUrl;
    
    /**
     * 支付完成返回地址
     */
    private String returnUrl;
    
    /**
     * 备注信息
     */
    private String remark;
} 