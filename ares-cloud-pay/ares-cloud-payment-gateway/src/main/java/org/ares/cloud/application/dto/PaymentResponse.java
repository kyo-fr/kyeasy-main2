package org.ares.cloud.application.dto;

import lombok.Builder;
import lombok.Data;

/**
 * PaymentResponse类用于表示支付响应信息
 * 它提供了支付过程中需要的关键信息，包括订单号、支付URL、状态和消息
 */
@Data
@Builder
public class PaymentResponse {
    /**
     * 订单号，用于唯一标识一个订单
     * 这是发起支付请求时对应的订单编号
     */
    private String orderNo;

    /**
     * 支付URL，用户需要访问这个URL以完成支付过程
     * 这个URL包含了支付所需的详细信息和指引
     */
    private String paymentUrl;

    /**
     * 状态，表示支付请求的当前状态
     * 这个状态用于指示支付过程是否成功或处于其他状态
     */
    private String status;

    /**
     * 消息，提供关于支付状态的额外信息
     * 这个字段可以用来传达成功消息、错误描述或其他相关信息
     */
    private String message;
}
