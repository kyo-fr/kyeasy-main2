package com.ares.cloud.order.infrastructure.persistence.entity;

import com.ares.cloud.order.domain.enums.*;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.TenantEntity;

/**
 * 订单实体
 */
@Data
@TableName("orders")
@EqualsAndHashCode(callSuper = true)
public class OrderEntity extends TenantEntity {

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 订单总金额
     */
    private Long totalAmount;

    /**
     * 币种
     */
    private String currency;


    /**
     * 服务费
     */
    private Long serviceFee;

    /**
     * 订单状态
     */
    private OrderStatus status;

    /**
     * 订单类型
     */
    private OrderType orderType;

    /**
     * 支付方式
     */
    private PaymentMode paymentMode;

    /**
     * 支付渠道ID
     */
    private String paymentChannelId;

    /**
     * 支付流水号
     */
    private String paymentTradeNo;

    /**
     * 支付时间
     */
    private Long paymentTime;

    /**
     * 支付截止时间
     */
    private Long paymentDeadline;

    /**
     * 支付状态
     */
    private PaymentStatus paymentStatus;

    /**
     * 已支付金额
     */
    private Long paidAmount;


    /**
     * 商户时区
     */
    private String timezone;

    /**
     * 预定时间
     */
    private Long reservationTime;

    /**
     * 桌号
     */
    private String tableNo;

    /**
     * 就餐人数
     */
    private Integer diningNumber;

    /**
     * 取餐时间
     */
    private Long pickupTime;

    /**
     * 配送骑手
     */
    private String riderId;

    /**
     * 配送时间
     */
    private Long deliveryTime;

    /**
     * 完成时间
     */
    private Long finishTime;

    /**
     * 币种精度
     */
    private Integer currencyScale;

    /**
     * 配送方式
     */
    private DeliveryType deliveryType;

    /**
     * 配送状态
     */
    private DeliveryStatus deliveryStatus;

    /**
     * 订单号
     */
    private String orderNumber;
    /**
     * 国家代码
     */
    private String countryCode;

    /**
     * 用户手机号
     */
    private String phoneNumber;

    /**
     * 订单编号
     */
    private String orderCode;
    
    /**
     * 订单来源类型
     */
    private Integer sourceType;

    /**
     * 取消时间
     */
    private Long cancelTime;

    /**
     * 取消原因
     */
    private String cancelReason;

}