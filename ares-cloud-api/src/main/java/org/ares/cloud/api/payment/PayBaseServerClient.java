package org.ares.cloud.api.payment;

import org.ares.cloud.api.payment.command.CreateInvoiceCommand;
import org.ares.cloud.api.payment.command.RefundInvoiceFullCommand;
import org.ares.cloud.api.payment.command.VoidInvoiceCommand;
import org.ares.cloud.api.payment.fallback.PayBaseServerClientFallback;
import org.ares.cloud.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author hugo
 * @version 1.0
 * @description: 客户端
 * @date 2024/10/7 23:30
 */
@FeignClient(name = "ares-payment-base-service",configuration = FeignConfig.class,fallback = PayBaseServerClientFallback.class)
public interface PayBaseServerClient {
    /**
     * 生成发票
     * @param command 生成发票命令
     * @return 发票ID
     */
    @PostMapping("/internal/invoices/generate")
    String generateInvoice(@RequestBody CreateInvoiceCommand command);

    /**
     * 作废发票
     * @param command 作废发票命令
     * @return 操作结果
     */
    @PostMapping("/internal/invoices/void")
    String voidInvoice(@RequestBody VoidInvoiceCommand command);

    /**
     * 全额退款
     * @param command 退款命令
     * @return 操作结果
     */
    @PostMapping("/internal/invoices/refund")
    String refundInvoice(@RequestBody RefundInvoiceFullCommand command);
}
