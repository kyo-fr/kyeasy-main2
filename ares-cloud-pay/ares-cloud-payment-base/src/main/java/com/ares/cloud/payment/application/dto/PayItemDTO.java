package com.ares.cloud.payment.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 支付项数据传输对象
 */
@Data
@Schema(description = "支付项数据传输对象")
public class PayItemDTO {
    /**
     * 支付项ID
     */
    @Schema(description = "支付项ID")
    private String id;
    
    /**
     * 交易流水号
     */
    @Schema(description = "交易流水号")
    private String tradeNo;
    
    /**
     * 支付金额
     */
    @Schema(description = "支付金额")
    private BigDecimal amount;
    
    /**
     * 支付时间
     */
    @Schema(description = "支付时间")
    private Long payTime;
    
    /**
     * 支付状态(0:未支付,1:已支付,2:支付失败)
     */
    @Schema(description = "支付状态(0:未支付,1:已支付,2:支付失败)")
    private Integer status;

    /**
     * 支付渠道ID
     */
    @Schema(description = "支付渠道ID")
    private String channelId;

    /**
     * 支付渠道唯一key
     */
    @Schema(description = "支付渠道唯一key")
    private String channelKey;


    /**
     * 支付类型(1:线上支付,2:线下支付)
     */
    @Schema(description = "支付类型(1:线上支付,2:线下支付)")
    private Integer payType;
    
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
}