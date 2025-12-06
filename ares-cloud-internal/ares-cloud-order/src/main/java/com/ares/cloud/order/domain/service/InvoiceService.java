package com.ares.cloud.order.domain.service;

import com.ares.cloud.order.domain.model.aggregate.Order;
import com.ares.cloud.order.domain.model.command.PayCommand;

/**
 * @description: 发票服务
 * @author hugo
 * @date 2025/4/15 01:42
 * @version 1.0
 */
public interface InvoiceService {
    /**
     * 生成发票
     * @param order 订单
     * @param command 支付命令
     */
    void generateInvoice(Order order, PayCommand command);
}
