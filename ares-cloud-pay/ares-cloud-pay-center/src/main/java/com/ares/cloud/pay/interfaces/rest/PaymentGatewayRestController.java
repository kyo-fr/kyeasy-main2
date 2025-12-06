package com.ares.cloud.pay.interfaces.rest;

import com.ares.cloud.pay.application.command.CreateOrderCommand;
import com.ares.cloud.pay.application.command.QueryPaymentOrderCommand;
import com.ares.cloud.pay.application.command.RefundCommand;
import com.ares.cloud.pay.application.dto.CreatePaymentOrderResponseDTO;
import com.ares.cloud.pay.application.dto.QueryPaymentOrderResponseDTO;
import com.ares.cloud.pay.application.dto.RefundPaymentResponseDTO;
import com.ares.cloud.pay.application.handlers.PaymentGatewayCommandHandler;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.ares.cloud.common.model.Result;
import org.springframework.web.bind.annotation.*;

/**
 * 支付网关REST控制器
 * 提供支付订单相关的REST接口
 */
@RestController
@RequestMapping("/api/payment")
@Tag(name = "三方接入", description = "第三方接入支付相关的REST接口")
public class PaymentGatewayRestController {
    
    @Resource
    private PaymentGatewayCommandHandler paymentGatewayCommandHandler;
    
    /**
     * 创建支付订单
     * POST /api/payment/create
     */
    @PostMapping("/create")
    public Result<CreatePaymentOrderResponseDTO> createPaymentOrder(@RequestBody CreateOrderCommand request) {
        // 通过命令处理器创建支付订单
        CreatePaymentOrderResponseDTO result = paymentGatewayCommandHandler.createPaymentOrder(request);
        return Result.success(result);
    }
    
    /**
     * 查询支付订单状态
     * POST /api/payment/query
     */
    @PostMapping("/query")
    public Result<QueryPaymentOrderResponseDTO> queryPaymentOrder(@RequestBody QueryPaymentOrderCommand request) {
        // 通过命令处理器查询支付订单
        QueryPaymentOrderResponseDTO result = paymentGatewayCommandHandler.queryPaymentOrder(request);
        return Result.success(result);
    }
    
    /**
     * 退款
     * POST /api/payment/refund
     */
    @PostMapping("/refund")
    public Result<RefundPaymentResponseDTO> refund(@RequestBody RefundCommand request) {
        // 通过命令处理器退款
        RefundPaymentResponseDTO result = paymentGatewayCommandHandler.refund(request);
        return Result.success(result);
    }

    
    /**
     * 健康检查
     * GET /api/payment/health
     */
    @GetMapping("/health")
    public Result<String> health() {
        return Result.success("支付网关服务正常");
    }
} 