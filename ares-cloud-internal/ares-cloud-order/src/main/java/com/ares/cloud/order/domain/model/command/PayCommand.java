package com.ares.cloud.order.domain.model.command;

import org.ares.cloud.common.model.Money;
import com.ares.cloud.order.domain.model.valueobject.PayItem;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 支付订单命令
 */
@Data
@Builder
public class PayCommand {
    /**
     * 商家ID
     */
    private String merchantId;
    /**
     * 是否为平台订单
     */
    private Boolean isPlatform;
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 国家代码
     */
    private String countryCode;
    
    /**
     * 手机号
     */
    private String userPhone;
    
    /**
     * 支付项列表
     */
    private List<PayItem> payItems;
    
    /**
     * 订单扣减金额
     */
    private Money deductAmount;
    
    /**
     * 订单扣减原因
     */
    private String deductReason;
    
    /**
     * 是否为部分支付
     */
    private boolean isPartialPay;
    
    /**
     * 部分支付时的订单项ID列表
     */
    private List<String> orderItemIds;
    
    /**
     * 赠送礼物点
     */
    private Money giftPoints;
    
    /**
     * 创建全部支付命令
     */
    public static PayCommand createFullPayCommand(String orderId, List<PayItem> payItems, Money deductAmount, String deductReason,
                                                  String countryCode, String userPhone, Money giftPoints) {
        return PayCommand.builder()
                .orderId(orderId)
                .payItems(payItems)
                .deductAmount(deductAmount)
                .deductReason(deductReason)
                .countryCode(countryCode)
                .userPhone(userPhone)
                .giftPoints(giftPoints)
                .isPartialPay(false)
                .build();
    }
    
    /**
     * 创建部分支付命令
     */
    public static PayCommand createPartialPayCommand(String orderId, List<PayItem> payItems, List<String> orderItemIds, Money deductAmount, String deductReason,
                                                     String countryCode, String userPhone, Money giftPoints) {
        return PayCommand.builder()
                .orderId(orderId)
                .payItems(payItems)
                .orderItemIds(orderItemIds)
                .deductAmount(deductAmount)
                .deductReason(deductReason)
                .countryCode(countryCode)
                .userPhone(userPhone)
                .giftPoints(giftPoints)
                .isPartialPay(true)
                .build();
    }
    
    /**
     * 转换为订单支付事件消息
     * 用于消息发送（包含 PayCommand 的全量信息）
     * 
     * @param userId 用户ID（从订单中获取）
     * @return OrderPaidEventMessage
     */
    public com.ares.cloud.order.domain.event.OrderPaidEventMessage toEventMessage(String userId) {
        java.util.List<com.ares.cloud.order.domain.event.OrderPaidEventMessage.PayItemInfo> payItemInfos = 
            this.payItems.stream()
                .map(item -> {
                    com.ares.cloud.order.domain.event.OrderPaidEventMessage.PayItemInfo info = 
                        new com.ares.cloud.order.domain.event.OrderPaidEventMessage.PayItemInfo();
                    info.setItemId(item.getTradeNo()); // 使用 tradeNo 作为 itemId
                    info.setPaymentMode(item.getRemark());
                    info.setAmount(item.getAmount().toDecimal()); // 转换为 BigDecimal
                    info.setPaymentChannel(item.getChannelId());
                    info.setTransactionId(item.getTradeNo());
                    return info;
                })
                .collect(java.util.stream.Collectors.toList());
        
        return com.ares.cloud.order.domain.event.OrderPaidEventMessage.builder()
                .orderId(this.orderId)
                .merchantId(this.merchantId)
                .isPlatform(this.isPlatform)
                .userId(userId)
                .countryCode(this.countryCode)
                .userPhone(this.userPhone)
                .payItems(payItemInfos)
                .deductAmount(this.deductAmount.toDecimal())  // 转换为 BigDecimal
                .deductReason(this.deductReason)
                .isPartialPay(this.isPartialPay)
                .orderItemIds(this.orderItemIds)
                .payTime(java.time.LocalDateTime.now())
                .giftPoints(this.giftPoints != null ? this.giftPoints.toDecimal() : null)  // 转换为 BigDecimal
                .build();
    }
} 