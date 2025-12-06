package org.ares.cloud.api.order;

import org.ares.cloud.api.order.commod.CreateOrderCommand;
import org.ares.cloud.api.order.commod.PayOrderCommand;
import org.ares.cloud.api.order.fallback.OrderClientFallback;
import org.ares.cloud.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/1/8 19:32
 */
@FeignClient(name = "ares-order-service",configuration = FeignConfig.class,fallback = OrderClientFallback.class)
public interface OrderClient {
    /**
     * 下单通知
     * @param command 创建订单
     * @return id
     */
    @PostMapping("/internal/orders/create")
    String createOrder(@RequestBody CreateOrderCommand command);
    
    /**
     * 手动结算订单
     * @param command 支付订单命令
     * @return 结果
     */
    @PostMapping("/internal/orders/manual-settlement")
    String manualSettlementOrder(@RequestBody PayOrderCommand command);
}
