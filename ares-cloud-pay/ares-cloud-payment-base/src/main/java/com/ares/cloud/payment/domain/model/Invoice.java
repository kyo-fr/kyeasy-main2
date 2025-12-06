package com.ares.cloud.payment.domain.model;

import org.ares.cloud.common.model.Money;
import com.ares.cloud.payment.domain.enums.InvoiceStatus;
import com.ares.cloud.payment.domain.enums.TransactionPartyType;
import lombok.Data;
import lombok.Builder;
import java.util.ArrayList;
import java.util.List;

/**
 * 发票领域模型
 */
@Data
@Builder
public class Invoice {
    /**
     * 发票ID
     */
    private String invoiceId;
    
    /**
     * 交易方类型
     */
    private TransactionPartyType transactionPartyType;
    
    /**
     * 付款方信息
     */
    private Party payer;
    
    /**
     * 收款方信息
     */
    private Party payee;
    
    /**
     * 发票明细项列表
     */
    private List<InvoiceItem> items;
    
    /**
     * 支付项列表
     */
    private List<PayItem> payItems;
    
    /**
     * 合同ID
     */
    private String contractId;
    
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 配送时间
     */
    private Long deliveryTime;
    
    /**
     * 完成时间
     */
    private Long completeTime;
    
    /**
     * 交易ID
     */
    private String transactionId;
    
    /**
     * 数字签名hash
     */
    private String digitalSignature;
    
    /**
     * 币种
     */
    private String currency;

    /**
     * 币种精度
     */
    private Integer scale;
    
    /**
     * 税前总价
     */
    private Money preTaxAmount;
    
    /**
     * 总价（含税）
     */
    private Money totalAmount;

    /**
     * 减免金额
     */
    private Money deductAmount;
    
    /**
     * 发票状态
     */
    private InvoiceStatus status;

    /**
     * 添加支付项
     */
    public void addPayItem(PayItem payItem) {
        if (payItems == null) {
            payItems = new ArrayList<>();
        }
        payItem.setInvoiceId(this.invoiceId);
        payItems.add(payItem);
    }

    /**
     * 获取已支付金额
     */
    public Money getPaidAmount() {
        if (payItems == null) {
            return Money.zero(currency,  scale);
        }
        return payItems.stream()
                .filter(item -> item.getStatus() == 1)
                .map(PayItem::getAmount)
                .reduce(Money.zero(currency,  scale), Money::add);
    }

    /**
     * 验证发票信息
     */
    public boolean validate() {
        if (payer == null || payee == null) {
            return false;
        }
        
        if (items == null || items.isEmpty()) {
            return false;
        }
        
        // 验证交易方类型与实际交易方是否匹配
        switch (transactionPartyType) {
            case B2B:
                if (payer.getPartyType() != 1 || payee.getPartyType() != 1) {
                    return false;
                }
                break;
            case B2C:
                if (payer.getPartyType() != 1 || payee.getPartyType() != 2) {
                    return false;
                }
                break;
            case C2B:
                if (payer.getPartyType() != 2 || payee.getPartyType() != 1) {
                    return false;
                }
                break;
            case C2C:
                if (payer.getPartyType() != 2 || payee.getPartyType() != 2) {
                    return false;
                }
                break;
        }
        
        // 验证金额
        if (preTaxAmount != null && preTaxAmount.isNegative()) {
            return false;
        }
        
        if (totalAmount != null && totalAmount.isNegative()) {
            return false;
        }
        
        // 验证必填项
        if (orderId == null || orderId.isEmpty()) {
            return false;
        }

        // 验证支付项
//        if (payItems != null && !payItems.isEmpty()) {
//            Money paidAmount = getPaidAmount();
//            if (paidAmount.compareTo(totalAmount) > 0) {
//                return false;
//            }
//        }
        
        return true;
    }
    
    /**
     * 作废发票
     */
    public boolean voidInvoice(String reason) {
        // 只有已创建或已支付状态的发票可以作废
        if (status != InvoiceStatus.CREATED && status != InvoiceStatus.PAID) {
            return false;
        }
        
        // 更新发票状态为已作废
        this.status = InvoiceStatus.VOIDED;
        return true;
    }
    
    /**
     * 退款
     */
    public boolean refund(Money refundAmount, String reason) {
        // 只有已支付状态的发票可以退款
        if (status != InvoiceStatus.PAID) {
            return false;
        }
        
        // 验证退款金额不能大于总金额
        if (refundAmount.compareTo(totalAmount) > 0) {
            return false;
        }
        
        // 全额退款
        if (refundAmount.equals(totalAmount)) {
            this.status = InvoiceStatus.REFUNDED;
        } else {
            // 部分退款
            this.status = InvoiceStatus.PARTIALLY_REFUNDED;
        }
        
        return true;
    }
    
    /**
     * 计算发票税额和总金额
     */
    public void calculateAmounts() {
        Money preTaxTotal = Money.zero(currency,  scale);
        Money total = Money.zero(currency,  scale);
        
        for (InvoiceItem item : items) {
            // 计算单项税前金额和含税金额
            item.calculate();
            
            // 累加总金额
            preTaxTotal = preTaxTotal.add(item.getUnitPrice().multiply(item.getQuantity()));
            total = total.add(item.getTotalAmount());
        }
        
        this.preTaxAmount = preTaxTotal;
        this.totalAmount = total;
        
        // 应用减免金额（如果有）
        if (deductAmount != null && !deductAmount.isZero()) {
            this.totalAmount = this.totalAmount.subtract(deductAmount);
        }
    }

    /**
     * 添加发票明细项
     */
    public void addItem(InvoiceItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        item.setInvoiceId(this.invoiceId);
        items.add(item);
    }
}