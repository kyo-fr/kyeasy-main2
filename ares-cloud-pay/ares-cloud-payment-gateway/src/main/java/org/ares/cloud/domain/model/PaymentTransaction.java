package org.ares.cloud.domain.model;

import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * PaymentTransaction类用于表示支付交易信息。
 * 包含了支付交易的核心属性，如交易ID、商户ID、渠道ID、支付类型、金额等，
 * 以及支付状态和回调URL等辅助信息。
 */
@Data
@Builder
public class PaymentTransaction {

    /**
     * 交易ID，用于唯一标识一次支付交易。
     */
    private String transactionId;

    /**
     * 商户ID，表示发起支付请求的商户。
     */
    private String merchantId;

    /**
     * 渠道ID，表示具体的支付渠道（如支付宝、微信等）。
     */
    private String channelId;

    /**
     * 支付类型，表示支付的具体方式（如支付宝、微信、BRAINTREE等）。
     */
    private PaymentType paymentType;

    /**
     * 支付金额，表示本次支付的金额。
     */
    private BigDecimal amount;

    /**
     * 货币代码，表示支付金额的货币单位。
     */
    private String currency;

    /**
     * 支付状态，表示当前支付交易的状态（如待处理、成功、失败、取消等）。
     */
    private PaymentStatus status;

    /**
     * 回调URL，支付完成后会向该URL发送通知。
     */
    private String callbackUrl;

    /**
     * 创建时间，表示支付交易创建的时间。
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间，表示支付交易最近一次更新的时间。
     */
    private LocalDateTime updatedAt;

    /**
     * PaymentType枚举用于定义支付类型。
     */
    public enum PaymentType {
        /**
         * 支付宝支付。
         */
        ALIPAY,

        /**
         * 微信支付。
         */
        WECHAT,

        /**
         * BRAINTREE支付。
         */
        BRAINTREE
    }

    /**
     * PaymentStatus枚举用于定义支付状态。
     */
    public enum PaymentStatus {
        /**
         * 待处理状态，表示支付请求已接收但尚未完成。
         */
        PENDING,

        /**
         * 成功状态，表示支付已完成且成功。
         */
        SUCCESS,

        /**
         * 失败状态，表示支付过程中出现错误或失败。
         */
        FAILED,

        /**
         * 取消状态，表示支付请求已被取消。
         */
        CANCELLED
    }
}
