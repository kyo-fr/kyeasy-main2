package com.ares.cloud.payment.domain.service.impl;

import org.ares.cloud.api.payment.command.*;
import com.ares.cloud.payment.application.command.MerchantInvoiceCommand;
import org.ares.cloud.common.model.Money;
import com.ares.cloud.payment.domain.enums.InvoiceError;
import com.ares.cloud.payment.domain.enums.InvoiceStatus;
import com.ares.cloud.payment.domain.enums.TransactionPartyType;
import com.ares.cloud.payment.domain.enums.TransactionType;
import com.ares.cloud.payment.domain.model.*;
import com.ares.cloud.payment.domain.repository.InvoiceRepository;
import com.ares.cloud.payment.domain.repository.MerchantRepository;
import com.ares.cloud.payment.domain.service.InvoiceService;
import jakarta.annotation.Resource;
import org.ares.cloud.common.exception.RequestBadException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

/**
 * 发票领域服务实现类
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Resource
    private InvoiceRepository invoiceRepository;
    
    @Resource
    private MerchantRepository merchantRepository;

    @Override
    @Transactional
    public String generateInvoice(CreateInvoiceCommand command) {
        // 根据交易双方的partyType判断交易模式
        TransactionPartyType transactionPartyType = determineTransactionType(command.getPayer().getPartyType(), command.getPayee().getPartyType());
        
        // 转换付款方和收款方
        Party payer = convertToParty(command.getPayer());
        Party payee = convertToParty(command.getPayee());
        
        // 构建Invoice领域模型
        Invoice invoice = Invoice.builder()
                .transactionPartyType(transactionPartyType)
                .payer(payer)
                .payee(payee)
                .contractId(command.getContractId())
                .orderId(command.getOrderId())
                .createTime(Instant.now().toEpochMilli())
                .status(InvoiceStatus.CREATED)
                .currency(command.getCurrency())
                .scale(command.getScale())
                .preTaxAmount(Money.of(command.getPreTaxAmount(), command.getCurrency(), command.getScale()))
                .totalAmount(Money.of(command.getTotalAmount(), command.getCurrency(), command.getScale()))
                .deductAmount(Money.of(command.getDeductAmount(), command.getCurrency(), command.getScale()))
                .build();
        
        // 处理发票明细项
        if (command.getItems() != null && !command.getItems().isEmpty()) {
            command.getItems().forEach(item -> {
                invoice.addItem(convertToInvoiceItem(item, command.getCurrency(), command.getScale()));
            });
        }
        
        // 处理支付项
        if (command.getPayItems() != null && !command.getPayItems().isEmpty()) {
            command.getPayItems().forEach(payItem -> {
                invoice.addPayItem(convertToPayItem(payItem, command.getCurrency(), command.getScale()));
            });
        }
        
        // 验证发票信息
        if (!invoice.validate()) {
            throw new RequestBadException(InvoiceError.INVOICE_CHECK_FAILED);
        }
        
        // 保存发票信息
        return invoiceRepository.save(invoice);
    }

    @Override
    @Transactional
    public String generateMerchantInvoice(MerchantInvoiceCommand command) {
        // 获取商户基本信息
        MerchantInfo merchantInfo = merchantRepository.getMerchantBasicInfo(command.getMerchantId());
        if (merchantInfo == null) {
            throw new RequestBadException(InvoiceError.MERCHANT_NOT_EXIST);
        }
        
        // 将MerchantInfo转换为Party
        Party merchantParty = Party.builder()
                .partyId(merchantInfo.getMerchantId())
                .name(merchantInfo.getName())
                .partyType(1) // 商户类型
                .taxId(merchantInfo.getTaxId())
                .address(merchantInfo.getAddress())
                .postalCode(merchantInfo.getPostalCode())
                .countryCode(merchantInfo.getCountryCode())
                .phone(merchantInfo.getPhone())
                .email(merchantInfo.getEmail())
                .build();
        
        // 根据交易类型确定付款方和收款方
        Party payer;
        Party payee;
        Integer payerType;
        Integer payeeType;
        
        if (command.getTransactionType() == TransactionType.EXPENSE) {
            // 支出：商户作为付款方，对方作为收款方
            payer = merchantParty;
            payee = convertToParty(command.getParty());
            payerType = 1; // 商户
            payeeType = command.getParty().getPartyType(); // 收款方类型
        } else {
            // 收入：对方作为付款方，商户作为收款方
            payer = convertToParty(command.getParty());
            payee = merchantParty;
            payerType = command.getParty().getPartyType(); // 付款方类型
            payeeType = 1; // 商户
        }
        
        // 判断实际的交易模式
        TransactionPartyType transactionPartyType = determineTransactionType(payerType, payeeType);
        
        // 构建Invoice领域模型
        Invoice invoice = Invoice.builder()
                .transactionPartyType(transactionPartyType)
                .payer(payer)
                .payee(payee)
                .contractId(command.getContractId())
                .orderId(command.getOrderId())
                .createTime(Instant.now().toEpochMilli())
                .status(InvoiceStatus.CREATED)
                .currency(merchantInfo.getCurrency())
                .scale(merchantInfo.getScale())
                .preTaxAmount(Money.of(command.getPreTaxAmount(), merchantInfo.getCurrency(), merchantInfo.getScale()))
                .totalAmount(Money.of(command.getTotalAmount(), merchantInfo.getCurrency(), merchantInfo.getScale()))
                .deductAmount(Money.of(command.getDeductAmount(), merchantInfo.getCurrency(), merchantInfo.getScale()))
                .build();
        
        // 处理发票明细项
        if (command.getItems() != null && !command.getItems().isEmpty()) {
            command.getItems().forEach(item -> {
                invoice.addItem(convertToInvoiceItem(item, merchantInfo.getCurrency(), merchantInfo.getScale()));
            });
        }
        
        // 处理支付项
        if (command.getPayItems() != null && !command.getPayItems().isEmpty()) {
            command.getPayItems().forEach(payItem -> {
                invoice.addPayItem(convertToPayItem(payItem, merchantInfo.getCurrency(), merchantInfo.getScale()));
            });
        }
        
        // 验证发票信息
        if (!invoice.validate()) {
            throw new RequestBadException(InvoiceError.INVOICE_CHECK_FAILED);
        }
        
        // 保存发票信息
        return invoiceRepository.save(invoice);
    }
    
    /**
     * 根据交易双方的partyType判断交易模式
     * 1: 商户
     * 2: 个人
     */
    private TransactionPartyType determineTransactionType(Integer payerType, Integer payeeType) {
        if (payerType == 1 && payeeType == 1) {
            return TransactionPartyType.B2B;
        } else if (payerType == 1 && payeeType == 2) {
            return TransactionPartyType.B2C;
        } else if (payerType == 2 && payeeType == 1) {
            return TransactionPartyType.C2B;
        } else if (payerType == 2 && payeeType == 2) {
            return TransactionPartyType.C2C;
        } else {
            throw new RequestBadException(InvoiceError.INVALID_PARTY_COMBINATION);
        }
    }

    /**
     * 将命令对象转换为Party对象
     */
    private Party convertToParty(PartyCommand commandParty) {
        return Party.builder()
                .partyId(commandParty.getPartyId())
                .name(commandParty.getName())
                .partyType(commandParty.getPartyType())
                .taxId(commandParty.getTaxId())
                .address(commandParty.getAddress())
                .postalCode(commandParty.getPostalCode())
                .countryCode(commandParty.getCountryCode())
                .country(commandParty.getCountry())
                .city(commandParty.getCity())
                .phone(commandParty.getPhone())
                .email(commandParty.getEmail())
                .build();
    }

    @Override
    public Invoice calculateTax(Invoice invoice) {
        // 使用领域模型中的计算方法
        invoice.calculateAmounts();
        return invoice;
    }

    @Override
    public boolean validateInvoice(Invoice invoice) {
        // 直接委托给领域模型的验证方法
        return invoice.validate();
    }

    @Override
    @Transactional
    public boolean voidInvoice(String invoiceId, String reason) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RequestBadException(InvoiceError.INVOICE_NOT_FOUND));
        
        // 调用领域模型的作废方法
        boolean result = invoice.voidInvoice(reason);
        
        if (result) {
            // 更新发票状态
            invoiceRepository.update(invoice);
        }
        
        return result;
    }

    @Override
    @Transactional
    public boolean refundFull(String invoiceId, String reason) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RequestBadException(InvoiceError.INVOICE_NOT_FOUND));
        
        // 获取发票总金额作为退款金额（全额退款）
        Money totalAmount = invoice.getTotalAmount();
        
        // 调用领域模型的退款方法
        boolean result = invoice.refund(totalAmount, reason);
        
        if (result) {
            // 更新发票状态
            invoiceRepository.update(invoice);
        }
        
        return result;
    }

    /**
     * 将命令对象转换为PayItem对象
     */
    private PayItem convertToPayItem(PayItemCommand command, String currency, Integer scale) {
        return PayItem.builder()
                .channelId(command.getChannelId())
                .tradeNo(command.getTradeNo())
                .amount(Money.of(command.getAmount(), currency, scale))
                .payTime(command.getPayTime())
                .status(1) // 默认状态为支付完成
                .remark(command.getRemark())
                .build();
    }

    /**
     * 将命令对象转换为InvoiceItem对象
     */
    private InvoiceItem convertToInvoiceItem(InvoiceItemCommand command, String currency, Integer scale) {
        return InvoiceItem.builder()
                .orderItemId(command.getOrderItemId())
                .productId(command.getProductId())
                .productName(command.getProductName())
                .quantity(command.getQuantity())
                .originalPrice(Money.of(command.getOriginalPrice(), currency, scale))
                .unitPrice(Money.of(command.getUnitPrice(), currency, scale))
                .taxRate(command.getTaxRate())
                .taxAmount(Money.of(command.getTaxAmount(), currency, scale))
                .totalAmount(Money.of(command.getTotalAmount(), currency, scale))
                .remark(command.getRemark())
                .build();
    }
}