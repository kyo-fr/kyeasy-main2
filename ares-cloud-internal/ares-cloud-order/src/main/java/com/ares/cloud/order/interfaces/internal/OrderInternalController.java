package com.ares.cloud.order.interfaces.internal;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Resource;
import org.ares.cloud.api.order.commod.CreateOrderCommand;
import org.ares.cloud.api.order.commod.PayOrderCommand;
import org.ares.cloud.exception.RpcCallException;
import com.ares.cloud.order.application.service.OrderApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/7 23:19
 */
@RestController
@RequestMapping("/internal/orders")
public class OrderInternalController {

    @Resource
    private OrderApplicationService orderApplicationService;

    /**
     * 创建订单
     * @param apiCommand 创建订单命令
     * @return 订单ID
     */
    @Hidden
    @PostMapping("create")
    public String create(@RequestBody CreateOrderCommand apiCommand) {
        try {
            apiCommand.setPlatform(true);
            return orderApplicationService.createOrder(apiCommand);
        } catch (Exception e) {
            throw new RpcCallException(e);
        }
    }

    /**
     * 手动结算
     * @param apiCommand 支付订单命令
     * @return 结果
     */
    @Hidden
    @PostMapping("manual-settlement")
    public String manualSettlement(@RequestBody PayOrderCommand apiCommand) {
        try {
            orderApplicationService.manualSettlementOrder(apiCommand, true);
            return "success";
        } catch (Exception e) {
            throw new RpcCallException(e);
        }
    }
}
