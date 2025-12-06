//package org.ares.cloud.domain.event;
//
//import org.ares.cloud.domain.model.PaymentOrder;
//
///**
// * 支付失败事件
// */
//public class PaymentFailedEvent extends PaymentEvent {
//    private final String failureReason;
//
//    public PaymentFailedEvent(PaymentOrder order, String failureReason) {
//        super(order);
//        this.failureReason = failureReason;
//    }
//}