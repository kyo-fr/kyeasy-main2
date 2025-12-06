package com.ares.cloud.pay.interfaces.rest;

import com.ares.cloud.pay.application.command.ScanCodePaymentCommand;
import com.ares.cloud.pay.application.command.TestPaymentCommand;
import com.ares.cloud.pay.application.handlers.PaymentGatewayHandler;
import com.ares.cloud.pay.domain.valueobject.PaymentCreateResult;
import com.ares.cloud.pay.domain.valueobject.PaymentExecuteResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.model.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付控制器
 * 提供支付相关的REST接口
 */
@RestController
@RequestMapping("/api/payment")
@Tag(name = "支付接口", description = "支付相关的REST接口")
public class PaymentController {
    
    @Resource
    private PaymentGatewayHandler paymentGatewayHandler;

    /**
     * 二维码支付
     */
    @PostMapping("/qrcode/pay")
    @Operation(summary = "二维码支付", description = "通过扫描二维码进行支付")
    public Result<PaymentExecuteResult> scanQRCodePayment(@RequestBody ScanCodePaymentCommand command) {
        PaymentExecuteResult result = paymentGatewayHandler.scanQRCodePayment(ApplicationContext.getUserId(),command);
        return Result.success(result);
    }

    /**
     * 测试下单
     * 使用平台商户信息创建测试支付订单
     */
    @PostMapping("/test/create")
    @Operation(summary = "测试下单", description = "使用平台商户信息创建测试支付订单，传入金额即可")
    public Result<PaymentCreateResult> createTestPayment(@RequestBody TestPaymentCommand command) {
        PaymentCreateResult result = paymentGatewayHandler.createTestPayment(command);
        return Result.success(result);
    }
} 