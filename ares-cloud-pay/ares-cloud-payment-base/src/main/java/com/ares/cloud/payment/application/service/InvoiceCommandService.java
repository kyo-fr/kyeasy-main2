package com.ares.cloud.payment.application.service;

import org.ares.cloud.api.payment.command.CreateInvoiceCommand;
import com.ares.cloud.payment.application.command.MerchantInvoiceCommand;
import org.ares.cloud.api.payment.command.RefundInvoiceFullCommand;
import org.ares.cloud.api.payment.command.VoidInvoiceCommand;
import com.ares.cloud.payment.domain.service.InvoiceService;
import jakarta.annotation.Resource;
import org.ares.cloud.common.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 发票命令服务类
 * 实现CQRS模式中的命令部分
 */
@Service
public class InvoiceCommandService {

    @Resource
    private InvoiceService invoiceService;
    
    /**
     * 生成发票
     */
    @Transactional
    public String generateInvoice(CreateInvoiceCommand command) {
        return invoiceService.generateInvoice(command);
    }

    /**
     * 商户开票
     */
    @Transactional
    public String generateMerchantInvoice(MerchantInvoiceCommand command) {
        if (StringUtils.hasText(ApplicationContext.getTenantId())){
            command.setMerchantId(ApplicationContext.getTenantId());
        }
        return invoiceService.generateMerchantInvoice(command);
    }

    /**
     * 作废发票
     */
    @Transactional
    public boolean voidInvoice(VoidInvoiceCommand command) {
        return invoiceService.voidInvoice(command.getInvoiceId(), command.getReason());
    }

    /**
     * 全额退款
     */
    @Transactional
    public boolean refundInvoiceFull(RefundInvoiceFullCommand command) {
        return invoiceService.refundFull(command.getInvoiceId(), command.getReason());
    }
}