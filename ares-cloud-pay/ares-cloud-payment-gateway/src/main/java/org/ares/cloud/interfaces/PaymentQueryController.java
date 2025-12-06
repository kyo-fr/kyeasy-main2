package org.ares.cloud.interfaces;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.application.dto.PaymentOrderDTO;
import org.ares.cloud.application.service.PaymentQueryService;
import org.ares.cloud.common.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付查询接口
 */
@Tag(name = "支付查询接口")
@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentQueryController {

    private final PaymentQueryService paymentQueryService;
    
    /**
     * 查询支付订单
     *
     * @param merchantId 商户ID
     * @param orderNo 订单号
     * @return 支付订单信息
     */
    @Operation(summary = "查询支付订单")
    @GetMapping("/{merchantId}/{orderNo}")
    public Result<PaymentOrderDTO> queryOrder(
        @Parameter(description = "商户ID") @PathVariable String merchantId,
        @Parameter(description = "订单号") @PathVariable String orderNo
    ) {
        PaymentOrderDTO order = paymentQueryService.queryOrder(merchantId, orderNo);
        return Result.success(order);
    }
} 