package com.ares.cloud.order.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.common.model.Result;
import com.ares.cloud.order.application.command.PartialPayOrderCommand;
import org.ares.cloud.api.order.commod.PayOrderCommand;
import com.ares.cloud.order.application.command.RefundOrderCommand;
import com.ares.cloud.order.application.service.OrderApplicationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 订单支付接口
 */
@Tag(name = "订单支付接口")
@RestController
@RequestMapping("/api/v1/orders/payment")
@RequiredArgsConstructor
public class OrderPaymentController {

    private final OrderApplicationService orderApplicationService;

    /**
     * 手动单支付方式全部结算
     */
    @Operation(summary = "手动全部结算", description = "将订单状态转换为已结算状态")
    @PostMapping("/manual-settlement")
    public Result<String> signManualSettlementOrder(@Validated @RequestBody PayOrderCommand command) {
        orderApplicationService.manualSettlementOrder(command,false);
        return Result.success();
    }

    /**
     * 手动多支付方式结算
     */
    @Operation(summary = "手动多支付方式结算", description = "支持全部结算和部分结算，将订单状态转换为部分结算或已结算状态")
    @PostMapping("/manual-partial-settlement")
    public Result<String> manualSettlementOrder(@Validated @RequestBody PartialPayOrderCommand command) {
        orderApplicationService.manualPartialSettlementOrder(command);
        return Result.success();
    }

    /**
     * 退款
     */
    @Operation(summary = "退款", description = "处理订单退款，更新订单结算状态")
    @PostMapping("/refund")
    public Result<String> refundOrder(@Validated @RequestBody RefundOrderCommand command) {
        orderApplicationService.refundOrder(command);
        return Result.success();
    }
}