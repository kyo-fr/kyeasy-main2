package com.ares.cloud.pay.domain.valueobject;

import lombok.Data;

/**
 * 支付执行结果值对象
 * 封装支付操作的执行结果信息
 */
@Data
public class PaymentExecuteResult {
    
    /**
     * 是否成功
     */
    private boolean success;
    
    /**
     * 订单号
     */
    private String orderNo;
    
    /**
     * 交易ID
     */
    private String transactionId;
    
    /**
     * 消息
     */
    private String message;
    
    /**
     * 创建成功结果
     *
     * @param orderNo 订单号
     * @param transactionId 交易ID
     * @param message 消息
     * @return 支付执行结果
     */
    public static PaymentExecuteResult success(String orderNo, String transactionId, String message) {
        PaymentExecuteResult result = new PaymentExecuteResult();
        result.success = true;
        result.orderNo = orderNo;
        result.transactionId = transactionId;
        result.message = message;
        return result;
    }
    
    /**
     * 创建失败结果
     *
     * @param orderNo 订单号
     * @param message 失败消息
     * @return 支付执行结果
     */
    public static PaymentExecuteResult failure(String orderNo, String message) {
        PaymentExecuteResult result = new PaymentExecuteResult();
        result.success = false;
        result.orderNo = orderNo;
        result.message = message;
        return result;
    }
} 