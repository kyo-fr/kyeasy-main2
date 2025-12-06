package org.ares.cloud.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.application.command.CreatePaymentCommand;
import org.ares.cloud.application.dto.PaymentResponseDTO;
import org.ares.cloud.application.service.PaymentApplicationService;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.infrastructure.annotation.VerifySignature;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付接口
 */
@Tag(name = "支付接口")
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentApplicationService paymentApplicationService;
    
    /**
     * 创建支付订单
     */
    @Operation(summary = "创建支付订单")
    @PostMapping("create_order")
    @VerifySignature
    public Result<PaymentResponseDTO> createPayment(@RequestBody CreatePaymentCommand command) {
        PaymentResponseDTO response = paymentApplicationService.createPayment(command);
        return Result.success(response);
    }
}