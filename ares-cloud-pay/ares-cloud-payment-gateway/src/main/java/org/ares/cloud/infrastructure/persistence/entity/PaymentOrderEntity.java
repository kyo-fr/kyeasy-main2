package org.ares.cloud.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 支付订单实体
 */
/**
 * 支付订单实体类
 * 该类用于映射支付订单相关的数据，与数据库表"payment_orders"对应
 */
@Data
@TableName("payment_orders")
public class PaymentOrderEntity {

    /**
     * 订单号
     * 用于唯一标识一个支付订单，通过输入方式生成主键
     */
    @TableId(type = IdType.INPUT)
    private String orderNo;

    /**
     * 商户ID
     * 用于标识订单所属的商户
     */
    private String merchantId;

    /**
     * 支付渠道
     * 指定订单使用的支付渠道，例如支付宝、微信等
     */
    private String channel;

    /**
     * 订单金额
     * 表示订单的总金额，使用BigDecimal以精确表示金融数据
     */
    private BigDecimal amount;

    /**
     * 订单主题
     * 描述订单的主题或标题
     */
    private String subject;
    /**
     * 订单内容
     * 订单的内容
     */
    private String body;

    /**
     * 订单状态
     * 表示订单的当前状态，例如未支付、已支付等
     */
    private String status;

    /**
     * 平台标识
     * 用于标识订单所属的平台
     */
    private String platform;

    /**
     * 返回URL
     * 用户在支付完成后跳转的页面地址
     */
    private String returnUrl;

    /**
     * 通知URL
     * 支付完成后，支付平台通过该URL通知商户服务器
     */
    private String notifyUrl;

    /**
     * 渠道交易号
     * 支付渠道生成的交易号
     */
    private String channelTradeNo;

    /**
     * 订单过期时间
     * 订单在该时间之前有效，超过该时间未支付则订单失效
     */
    private LocalDateTime expireTime;

    /**
     * 订单创建时间
     * 记录订单创建的时间
     */
    private LocalDateTime createTime;

    /**
     * 支付完成时间
     * 记录订单支付完成的时间
     */
    private LocalDateTime payTime;
}
