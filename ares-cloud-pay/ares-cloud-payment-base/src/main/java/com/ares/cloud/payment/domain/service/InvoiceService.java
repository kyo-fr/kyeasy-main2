package com.ares.cloud.payment.domain.service;

import org.ares.cloud.api.payment.command.CreateInvoiceCommand;
import com.ares.cloud.payment.application.command.MerchantInvoiceCommand;
import com.ares.cloud.payment.domain.model.Invoice;

/**
 * 发票领域服务接口
 */
public interface InvoiceService {
    /**
     * 根据CreateInvoiceCommand生成发票
     *
     * @param command 创建发票命令
     * @return 生成的发票ID
     */
    String generateInvoice(CreateInvoiceCommand command);

    /**
     * 根据MerchantInvoiceCommand生成发票
     *
     * @param command 创建发票命令
     * @return 生成的发票ID
     */
    String generateMerchantInvoice(MerchantInvoiceCommand command);

    /**
     * 计算发票税额
     *
     * @param invoice 发票信息
     * @return 更新后的发票信息
     */
    Invoice calculateTax(Invoice invoice);
    
    /**
     * 验证发票信息
     *
     * @param invoice 发票信息
     * @return 验证结果
     */
    boolean validateInvoice(Invoice invoice);

    /**
     * 作废发票
     *
     * @param invoiceId 发票ID
     * @param reason 作废原因
     * @return 是否作废成功
     */
    boolean voidInvoice(String invoiceId, String reason);

    /**
     * 全额退款
     *
     * @param invoiceId 发票ID
     * @param reason 退款原因
     * @return 是否退款成功
     */
    boolean refundFull(String invoiceId, String reason);
}