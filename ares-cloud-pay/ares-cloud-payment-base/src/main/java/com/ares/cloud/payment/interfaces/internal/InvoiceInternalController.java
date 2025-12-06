package com.ares.cloud.payment.interfaces.internal;

import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.api.payment.command.CreateInvoiceCommand;
import com.ares.cloud.payment.application.command.MerchantInvoiceCommand;
import org.ares.cloud.api.payment.command.RefundInvoiceFullCommand;
import org.ares.cloud.api.payment.command.VoidInvoiceCommand;
import com.ares.cloud.payment.application.service.InvoiceCommandService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Resource;
import org.ares.cloud.exception.RpcCallException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发票内部服务调用控制器
 */
@RestController
@RequestMapping("/internal/invoices")
@Slf4j
public class InvoiceInternalController {

    @Resource
    private InvoiceCommandService invoiceCommandService;

    /**
     * 生成发票
     * @param command 生成发票命令
     * @return 发票ID
     */
    @Hidden
    @PostMapping("generate")
    public String generate(@RequestBody CreateInvoiceCommand command) {
        try {
            return invoiceCommandService.generateInvoice(command);
        } catch (Exception e) {
            log.error("生成发票失败", e);
            throw new RpcCallException(e);
        }
    }

    /**
     * 商户开票
     * @param command 商户开票命令
     * @return 发票ID
     */
    @Hidden
    @PostMapping("generate-merchant")
    public String generateMerchant(@RequestBody MerchantInvoiceCommand command) {
        try {
            return invoiceCommandService.generateMerchantInvoice(command);
        } catch (Exception e) {
            throw new RpcCallException(e);
        }
    }

    /**
     * 作废发票
     * @param command 作废发票命令
     * @return 操作结果
     */
    @Hidden
    @PostMapping("void")
    public String voidInvoice(@RequestBody VoidInvoiceCommand command) {
        try {
            boolean result = invoiceCommandService.voidInvoice(command);
            return result ? "success" : "failed";
        } catch (Exception e) {
            throw new RpcCallException(e);
        }
    }

    /**
     * 全额退款
     * @param command 退款命令
     * @return 操作结果
     */
    @Hidden
    @PostMapping("refund")
    public String refund(@RequestBody RefundInvoiceFullCommand command) {
        try {
            boolean result = invoiceCommandService.refundInvoiceFull(command);
            return result ? "success" : "failed";
        } catch (Exception e) {
            throw new RpcCallException(e);
        }
    }
} 