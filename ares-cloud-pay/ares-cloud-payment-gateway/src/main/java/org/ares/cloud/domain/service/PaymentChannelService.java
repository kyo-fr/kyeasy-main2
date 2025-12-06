package org.ares.cloud.domain.service;

import org.ares.cloud.domain.model.PaymentOrder;
import org.ares.cloud.domain.model.enums.PaymentChannel;

import java.util.Map;

/**
 * 支付渠道服务接口
 * 定义支付渠道需要实现的基本功能
 *
 * @author hugo
 * @version 1.0.0
 * @date 2024/03/15
 */
public interface PaymentChannelService {
    /**
     * 生成支付链接
     * 根据支付订单生成对应渠道的支付链接或支付参数
     *
     * @param order 支付订单
     * @return 支付链接或支付参数
     * @throws IllegalArgumentException 当支付参数无效时抛出
     */
    String generatePaymentUrl(PaymentOrder order);

    /**
     * 生成支付签名
     * 生成支付渠道的支付签名
     */
    String generatePaymentSign(PaymentOrder order);

    /**
     * 验证支付回调
     * 验证支付渠道回调请求的合法性
     *
     * @param callbackParams 回调参数
     * @return true 如果验证通过，false 否则
     */
    boolean verifyCallback(Map<String, String> callbackParams);

    /**
     * 获取支付渠道类型
     * 返回当前支付渠道的标识
     *
     * @return 支付渠道类型
     */
    PaymentChannel getChannelType();
} 