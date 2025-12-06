package com.ares.cloud.order.application.dto;

import com.ares.cloud.order.domain.enums.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.experimental.SuperBuilder;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.serializer.CustomDateSerializer;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单DTO
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor // 添加全参数构造函数
@Schema(description = "订单DTO")
public class OrderDTO extends TenantDto {
    @Schema(description = "订单ID")
    @JsonProperty("id")
    private String id;
    
    @Schema(description = "订单号")
    @JsonProperty("orderNumber")
    private String orderNumber;

    
    @Schema(description = "订单编号")
    @JsonProperty("orderCode")
    private String orderCode;

    @Schema(description = "国家代码")
    @JsonProperty(value = "countryCode")
    private String countryCode;
    
    @Schema(description = "用户手机号")
    @JsonProperty("phoneNumber")
    private String phoneNumber;
    
    @Schema(description = "商户ID")
    @JsonProperty("merchantId")
    private String merchantId;

    @Schema(description = "订单总金额")
    @JsonProperty("totalAmount")
    private BigDecimal totalAmount;

    @Schema(description = "服务费")
    @JsonProperty("serviceFee")
    private BigDecimal serviceFee;

    @Schema(description = "订单状态")
    @JsonProperty("status")
    private OrderStatus status;

    @Schema(description = "支付状态")
    @JsonProperty("paymentStatus")
    private PaymentStatus paymentStatus;

    @Schema(description = "支付时间")
    @JsonProperty("paymentTime")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Long paymentTime;

    @Schema(description = "支付渠道ID")
    @JsonProperty("paymentChannelId")
    private String paymentChannelId;

    @Schema(description = "支付流水号")
    @JsonProperty("paymentTradeNo")
    private String paymentTradeNo;

    @Schema(description = "配送时间")
    @JsonProperty("deliveryTime")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Long deliveryTime;

    @Schema(description = "完成时间")
    @JsonProperty("finishTime")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Long finishTime;

    @Schema(description = "订单项列表")
    @JsonProperty("items")
    private List<OrderItemDTO> items;

    @Schema(description = "订单类型")
    @JsonProperty("orderType")
    private OrderType orderType;

    @Schema(description = "预定时间")
    @JsonProperty("reservationTime")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Long reservationTime;

    @Schema(description = "桌号")
    @JsonProperty("tableNo")
    private String tableNo;

    @Schema(description = "就餐人数")
    @JsonProperty("diningNumber")
    private Integer diningNumber;

    @Schema(description = "取餐时间")
    @JsonProperty("pickupTime")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Long pickupTime;

    @Schema(description = "配送方式")
    @JsonProperty("deliveryType")
    private DeliveryType deliveryType;

    @Schema(description = "配送信息")
    @JsonProperty("deliveryInfo")
    private DeliveryInfoDTO deliveryInfo;

    @Schema(description = "配送状态")
    @JsonProperty("deliveryStatus")
    private DeliveryStatus deliveryStatus;

    @Schema(description = "支付方式")
    @JsonProperty("paymentMode")
    private PaymentMode paymentMode;

    @Schema(description = "支付截止时间")
    @JsonProperty("paymentDeadline")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Long paymentDeadline;

    @Schema(description = "已支付金额")
    @JsonProperty("paidAmount")
    private BigDecimal paidAmount;


    @Schema(description = "币种")
    @JsonProperty("currency")
    private String currency;

    @Schema(description = "币种精度")
    @JsonProperty("currencyScale")
    private Integer currencyScale;
    
    @Schema(description = "订单来源类型")
    @JsonProperty("sourceType")
    private Integer sourceType;

    @Schema(description = "商户名称")
    @JsonProperty("merchantName")
    private String merchantName;

    @Schema(description = "预订信息")
    @JsonProperty("reservationInfo")
    private ReservationInfoDTO reservationInfo;

    @Schema(description = "取消时间")
    @JsonProperty("cancelTime")
    @JsonSerialize(using = CustomDateSerializer.class)
    private Long cancelTime;

    @Schema(description = "取消原因")
    @JsonProperty("cancelReason")
    private String cancelReason;

    @Getter
    @Setter
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor // 添加全参数构造函数
    @Schema(description = "订单项DTO")
    public static class OrderItemDTO {
        @Schema(description = "订单项ID")
        @JsonProperty("id")
        private String id;

        @Schema(description = "订单ID")
        @JsonProperty("orderId")
        private String orderId;

        @Schema(description = "商品ID")
        @JsonProperty("productId")
        private String productId;

        @Schema(description = "商品名称")
        @JsonProperty("productName")
        private String productName;

        @Schema(description = "商品单价")
        @JsonProperty("unitPrice")
        private BigDecimal unitPrice;
        
        @Schema(description = "优惠价格")
        @JsonProperty("discountedPrice")
        private BigDecimal discountedPrice;

        @Schema(description = "商品数量")
        @JsonProperty("quantity")
        private Integer quantity;

        @Schema(description = "商品总价")
        @JsonProperty("totalPrice")
        private BigDecimal totalPrice;

        @Schema(description = "货币")
        @JsonProperty("currency")
        private String currency;

        @Schema(description = "货币精度")
        @JsonProperty("currencyScale")
        private Integer currencyScale;
        
        @Schema(description = "支付状态")
        @JsonProperty("paymentStatus")
        private PaymentStatus paymentStatus;
        
        @Schema(description = "商品规格列表")
        @JsonProperty("specifications")
        private List<ProductSpecificationDTO> specifications;
    }
}
