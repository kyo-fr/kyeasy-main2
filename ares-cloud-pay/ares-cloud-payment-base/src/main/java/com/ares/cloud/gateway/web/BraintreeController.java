package com.ares.cloud.gateway.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.model.Result;
import com.ares.cloud.gateway.dto.PaymentCallbackResult;
import com.ares.cloud.gateway.dto.PaymentResponse;
import com.ares.cloud.gateway.dto.PaymentTransactionRequest;
import com.ares.cloud.gateway.service.IBraintreeService;
import org.springframework.web.bind.annotation.*;

/**
 * @author hugo
 * @version 1.0.0
 * @description Braintree支付中心控制器
 * @date 2024-03-08
 */
@RestController
@Tag(name = "Braintree支付中心")
@Slf4j
@RequestMapping("/api/ares/openApi/pay/braintree")
public class BraintreeController {

    @Resource(name = "productionBraintreeService")
    private IBraintreeService productionBraintreeService;
    
    @Resource(name = "sandboxBraintreeService")
    private IBraintreeService sandboxBraintreeService;

    // ================ 生产环境接口 ================
//    {
//        "clientToken": "sandbox_abc123",
//            "allowedPaymentMethods": ["card"] // 也可能是 ["paypal"]
//    }

    /**
     * 获取生产环境客户端token
     */
    @GetMapping("/clientToken")
    @Operation(summary = "获取生产环境客户端token")
    public Result<String> clientToken() {
        log.info("获取Braintree生产环境客户端token");
        return Result.success(productionBraintreeService.generateClientToken());
    }

    /**
     * 生产环境发起支付交易
     */
    @PostMapping("/transaction")
    @Operation(summary = "生产环境发起支付交易")
    public Result<PaymentResponse> transaction(@RequestBody PaymentTransactionRequest request) {
        log.info("发起Braintree生产环境支付交易: {}", request);
        return Result.success(productionBraintreeService.processTransaction(request));
    }

    /**
     * 生产环境支付结果回调处理
     */
    @PostMapping("/webhook")
    @Operation(summary = "生产环境支付结果回调处理")
    public Result<String> handleWebhook(HttpServletRequest request) {
        String btSignature = request.getParameter("bt_signature");
        String btPayload = request.getParameter("bt_payload");
        
        log.info("接收到Braintree生产环境支付回调: signature={}, payload={}", btSignature, btPayload);
        
        PaymentCallbackResult result = productionBraintreeService.handleWebhook(btSignature, btPayload);
        return Result.success(result.getMessage());
    }

    /**
     * 生产环境查询支付状态
     */
    @GetMapping("/transaction/{orderId}")
    @Operation(summary = "生产环境查询支付状态")
    public Result<PaymentResponse> queryTransaction(@PathVariable String orderId) {
        log.info("查询生产环境支付状态: orderId={}", orderId);
        return Result.success(productionBraintreeService.queryTransaction(orderId));
    }

    /**
     * 生产环境退款
     */
    @PostMapping("/refund")
    @Operation(summary = "生产环境退款")
    public Result<PaymentResponse> refund(@RequestParam String transactionId, @RequestParam String amount) {
        log.info("发起生产环境退款请求: transactionId={}, amount={}", transactionId, amount);
        return Result.success(productionBraintreeService.processRefund(transactionId, amount));
    }

    // ================ 沙盒环境接口 ================

    /**
     * 获取沙盒环境客户端token
     */
    @GetMapping("/sandbox/clientToken")
    @Operation(summary = "获取沙盒环境客户端token")
    public Result<String> sandboxClientToken() {
        log.info("获取Braintree沙盒环境客户端token");
        return Result.success(sandboxBraintreeService.generateClientToken());
    }

    /**
     * 沙盒环境发起支付交易
     */
    @PostMapping("/sandbox/transaction")
    @Operation(summary = "沙盒环境发起支付交易")
    public Result<PaymentResponse> sandboxTransaction(@RequestBody PaymentTransactionRequest request) {
        log.info("发起Braintree沙盒环境支付交易: {}", request);
        return Result.success(sandboxBraintreeService.processTransaction(request));
    }

    /**
     * 沙盒环境支付结果回调处理
     */
    @PostMapping("/sandbox/webhook")
    @Operation(summary = "沙盒环境支付结果回调处理")
    public Result<String> handleSandboxWebhook(HttpServletRequest request) {
        String btSignature = request.getParameter("bt_signature");
        String btPayload = request.getParameter("bt_payload");
        
        log.info("接收到Braintree沙盒环境支付回调: signature={}, payload={}", btSignature, btPayload);
        
        PaymentCallbackResult result = sandboxBraintreeService.handleWebhook(btSignature, btPayload);
        return Result.success(result.getMessage());
    }

    /**
     * 沙盒环境查询支付状态
     */
    @GetMapping("/sandbox/transaction/{orderId}")
    @Operation(summary = "沙盒环境查询支付状态")
    public Result<PaymentResponse> querySandboxTransaction(@PathVariable String orderId) {
        log.info("查询沙盒环境支付状态: orderId={}", orderId);
        return Result.success(sandboxBraintreeService.queryTransaction(orderId));
    }

    /**
     * 沙盒环境退款
     */
    @PostMapping("/sandbox/refund")
    @Operation(summary = "沙盒环境退款")
    public Result<PaymentResponse> sandboxRefund(@RequestParam String transactionId, @RequestParam String amount) {
        log.info("发起沙盒环境退款请求: transactionId={}, amount={}", transactionId, amount);
        return Result.success(sandboxBraintreeService.processRefund(transactionId, amount));
    }
}
