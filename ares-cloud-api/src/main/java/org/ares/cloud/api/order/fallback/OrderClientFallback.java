package org.ares.cloud.api.order.fallback;

import org.ares.cloud.api.order.OrderClient;
import org.ares.cloud.api.order.commod.CreateOrderCommand;
import org.ares.cloud.api.order.commod.PayOrderCommand;
import org.ares.cloud.exception.ServiceUnavailableException;
import org.springframework.stereotype.Component;

/**
 * @author hugo
 * @version 1.0
 * @description: OrderClient 的降级处理
 * 当订单服务不可用时抛出 ServiceUnavailableException
 * @date 2025/1/8 19:39
 */
@Component
public class OrderClientFallback implements OrderClient {
    
    private static final String SERVICE_NAME = "ares-order-service";
    
    @Override
    public String createOrder(CreateOrderCommand command) {
        throw new ServiceUnavailableException(SERVICE_NAME, "createOrder");
    }

    @Override
    public String manualSettlementOrder(PayOrderCommand command) {
        throw new ServiceUnavailableException(SERVICE_NAME, "manualSettlementOrder");
    }
}
