package com.ares.cloud.order.domain.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单支付成功事件消息
 * 领域层消息，表示订单支付成功这一业务事件
 *
 * @author ares-cloud
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaidEventMessage {

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 商户ID
     */
    private String merchantId;
    
    /**
     * 是否为平台订单
     */
    private Boolean isPlatform;

    /**
     * 用户ID
     */
    private String userId;
    
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
    private List<PayItemInfo> payItems;

    /**
     * 扣除金额
     */
    private BigDecimal deductAmount;
    
    /**
     * 订单扣减原因
     */
    private String deductReason;
    
    /**
     * 是否为部分支付
     */
    @Builder.Default
    private boolean isPartialPay = false;
    
    /**
     * 部分支付时的订单项ID列表
     */
    private List<String> orderItemIds;

    /**
     * 支付时间
     */
    private LocalDateTime payTime;

    /**
     * 赠送礼物点（可选）
     */
    private BigDecimal giftPoints;



    /**
     * 事件类型
     */
    @Builder.Default
    private String eventType = "ORDER_PAID";
    
    /**
     * 从 PayCommand 创建事件消息
     * 
     * @param command 支付命令
     * @param userId 用户ID
     * @return OrderPaidEventMessage
     */
    public static OrderPaidEventMessage fromPayCommand(
            com.ares.cloud.order.domain.model.command.PayCommand command, 
            String userId) {
        return command.toEventMessage(userId);
    }
    
    /**
     * 转换为 PayCommand（用于消息消费后的业务处理）
     * 
     * @param currencyCode 币种代码（从订单中获取）
     * @param currencyScale 币种精度（从订单中获取）
     * @return PayCommand
     */
    public com.ares.cloud.order.domain.model.command.PayCommand toPayCommand(String currencyCode, int currencyScale) {
        List<com.ares.cloud.order.domain.model.valueobject.PayItem> payItems = 
            this.payItems.stream()
                .map(item -> com.ares.cloud.order.domain.model.valueobject.PayItem.builder()
                    .channelId(item.getPaymentChannel())
                    .tradeNo(item.getTransactionId())
                    .amount(org.ares.cloud.common.model.Money.of(item.getAmount(), currencyCode, currencyScale))
                    .remark(item.getPaymentMode())
                    .build())
                .collect(java.util.stream.Collectors.toList());
        
        return com.ares.cloud.order.domain.model.command.PayCommand.builder()
                .orderId(this.orderId)
                .merchantId(this.merchantId)
                .isPlatform(this.isPlatform)
                .payItems(payItems)
                .deductAmount(org.ares.cloud.common.model.Money.of(this.deductAmount, currencyCode, currencyScale))
                .deductReason(this.deductReason)
                .countryCode(this.countryCode)
                .userPhone(this.userPhone)
                .giftPoints(this.giftPoints != null ? 
                    org.ares.cloud.common.model.Money.of(this.giftPoints, currencyCode, currencyScale) : null)
                .isPartialPay(this.isPartialPay)
                .orderItemIds(this.orderItemIds)
                .build();
    }

    /**
     * 支付项信息
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PayItemInfo {
        /**
         * 支付项ID
         */
        private String itemId;

        /**
         * 支付方式
         */
        private String paymentMode;

        /**
         * 支付金额
         */
        private BigDecimal amount;

        /**
         * 支付渠道
         */
        private String paymentChannel;

        /**
         * 交易流水号
         */
        private String transactionId;
    }
}
