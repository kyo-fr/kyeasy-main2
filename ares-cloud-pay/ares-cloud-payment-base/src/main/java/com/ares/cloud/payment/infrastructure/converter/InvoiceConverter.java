package com.ares.cloud.payment.infrastructure.converter;

import com.ares.cloud.base.dto.SysPaymentChannelDto;
import org.ares.cloud.common.model.Money;
import com.ares.cloud.payment.application.dto.*;
import com.ares.cloud.payment.domain.enums.InvoiceStatus;
import com.ares.cloud.payment.domain.enums.TransactionPartyType;
import com.ares.cloud.payment.domain.model.Invoice;
import com.ares.cloud.payment.domain.model.InvoiceItem;
import com.ares.cloud.payment.domain.model.Party;
import com.ares.cloud.payment.domain.model.PayItem;
import com.ares.cloud.payment.infrastructure.persistence.entity.InvoiceEntity;
import com.ares.cloud.payment.infrastructure.persistence.entity.PartyEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 发票转换器
 * 负责在领域模型、DTO和持久化实体之间进行转换
 */
@Component
@RequiredArgsConstructor
public class InvoiceConverter {

    /**
     * 将领域模型转换为DTO
     */
    public InvoiceDTO toBaseDTO(InvoiceEntity invoice) {
        if (invoice == null) {
            return null;
        }
        
        InvoiceDTO dto = new InvoiceDTO();
        dto.setInvoiceId(invoice.getId());
        dto.setTransactionPartyType(TransactionPartyType.valueOf(invoice.getTransactionPartyType()));
        dto.setContractId(invoice.getContractId());
        dto.setOrderId(invoice.getOrderId());
        dto.setCreateTime(invoice.getCreateTime());
        dto.setDeliveryTime(invoice.getDeliveryTime());
        dto.setCompleteTime(invoice.getCompleteTime());
        dto.setTransactionId(invoice.getTransactionId());
        dto.setPreTaxAmount(invoice.getPreTaxAmount() != null ? Money.create(invoice.getPreTaxAmount(), invoice.getCurrency(), invoice.getScale()).toDecimal() : null);
        dto.setTotalAmount(invoice.getTotalAmount() != null ? Money.create(invoice.getTotalAmount(), invoice.getCurrency(), invoice.getScale()).toDecimal() : null);
        dto.setDeductAmount(invoice.getDeductAmount() != null ? Money.create(invoice.getDeductAmount(), invoice.getCurrency(), invoice.getScale()).toDecimal() : null);
        
        return dto;
    }



    /**
     * 将领域模型转换为持久化实体
     */
    public InvoiceEntity toEntity(Invoice invoice) {
        if (invoice == null) {
            return null;
        }
        
        InvoiceEntity entity = new InvoiceEntity();
        entity.setId(invoice.getInvoiceId());
        entity.setTransactionPartyType(invoice.getTransactionPartyType().getCode());
        entity.setPayerId(invoice.getPayer().getPartyId());
        entity.setPayeeId(invoice.getPayee().getPartyId());
        entity.setContractId(invoice.getContractId());
        entity.setOrderId(invoice.getOrderId());
        entity.setCreateTime(invoice.getCreateTime());
        entity.setDeliveryTime(invoice.getDeliveryTime());
        entity.setCompleteTime(invoice.getCompleteTime());
        entity.setTransactionId(invoice.getTransactionId());
        entity.setDigitalSignature(invoice.getDigitalSignature());
        entity.setCurrency(invoice.getCurrency());
        entity.setScale(invoice.getScale());
        // 转换金额时使用币种和精度信息
        entity.setPreTaxAmount(invoice.getPreTaxAmount() != null ? 
                invoice.getPreTaxAmount().getAmount() : null);
        entity.setTotalAmount(invoice.getTotalAmount() != null ? 
                invoice.getTotalAmount().getAmount() : null);
        entity.setDeductAmount(invoice.getDeductAmount() != null ? 
                invoice.getDeductAmount().getAmount() : null);
        
        entity.setStatus(invoice.getStatus().getCode());
        
        return entity;
    }

    /**
     * 将持久化实体转换为领域模型
     */
    public Invoice toDomain(InvoiceEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Invoice.builder()
                .invoiceId(entity.getId())
                .transactionPartyType(TransactionPartyType.valueOf(entity.getTransactionPartyType()))
                .payer(null) // 需要单独查询
                .payee(null) // 需要单独查询
                .contractId(entity.getContractId())
                .orderId(entity.getOrderId())
                .createTime(entity.getCreateTime())
                .deliveryTime(entity.getDeliveryTime())
                .completeTime(entity.getCompleteTime())
                .transactionId(entity.getTransactionId())
                .digitalSignature(entity.getDigitalSignature())

                // 设置币种和精度
                .currency(entity.getCurrency())
                .scale(entity.getScale())
                
                // 转换金额时使用币种和精度信息
                .preTaxAmount(entity.getPreTaxAmount() != null ? 
                        Money.of(entity.getPreTaxAmount(), entity.getCurrency(), entity.getScale()) : null)
                .totalAmount(entity.getTotalAmount() != null ? 
                        Money.of(entity.getTotalAmount(), entity.getCurrency(), entity.getScale()) : null)
                .deductAmount(entity.getDeductAmount() != null ? 
                        Money.of(entity.getDeductAmount(), entity.getCurrency(), entity.getScale()) : null)
                
                .status(InvoiceStatus.valueOf(entity.getStatus()))
                .build();
    }

    // 辅助转换方法
    private PartyDTO toPartyDTO(Party party) {
        if (party == null) {
            return null;
        }
        
        PartyDTO dto = new PartyDTO();
        dto.setPartyId(party.getPartyId());
        dto.setName(party.getName());
        dto.setPartyType(party.getPartyType());
        dto.setTaxId(party.getTaxId());
        dto.setAddress(party.getAddress());
        dto.setPostalCode(party.getPostalCode());
        dto.setPhone(party.getPhone());
        dto.setEmail(party.getEmail());
        
        return dto;
    }

    private Party toParty(PartyDTO dto) {
        if (dto == null) {
            return null;
        }
        
        return Party.builder()
                .partyId(dto.getPartyId())
                .name(dto.getName())
                .partyType(dto.getPartyType())
                .taxId(dto.getTaxId())
                .address(dto.getAddress())
                .postalCode(dto.getPostalCode())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
    }

    private InvoiceItemDTO toInvoiceItemDTO(InvoiceItem item) {
        if (item == null) {
            return null;
        }
        
        InvoiceItemDTO dto = new InvoiceItemDTO();
        dto.setId(item.getId());
        dto.setProductName(item.getProductName());
        dto.setQuantity(item.getQuantity());
        dto.setOriginalPrice(item.getOriginalPrice() != null ? item.getOriginalPrice().toDecimal() : null);
        dto.setUnitPrice(item.getUnitPrice() != null ? item.getUnitPrice().toDecimal() : null);
        dto.setTaxRate(item.getTaxRate());
        dto.setTaxAmount(item.getTaxAmount() != null ? item.getTaxAmount().toDecimal() : null);
        dto.setTotalAmount(item.getTotalAmount() != null ? item.getTotalAmount().toDecimal() : null);
        dto.setRemark(item.getRemark());
        
        return dto;
    }


    private PayItemDTO toPayItemDTO(PayItem item, Map<String, SysPaymentChannelDto> channels) {
        if (item == null) {
            return null;
        }
        
        PayItemDTO dto = new PayItemDTO();
        dto.setChannelId(item.getChannelId());
        dto.setTradeNo(item.getTradeNo());
        dto.setAmount(item.getAmount() != null ? item.getAmount().toDecimal() : null);
        dto.setPayTime(item.getPayTime());
        dto.setStatus(item.getStatus());
        dto.setRemark(item.getRemark());
        dto.setId(item.getId());
        if (channels != null && !channels.isEmpty()) {
            SysPaymentChannelDto dto1 = channels.get(item.getChannelId());
            if (dto1 != null) {
                dto.setChannelKey(dto1.getChannelKey());
                dto.setPayType(dto1.getChannelType());
            }
        }
        
        return dto;
    }

    private PayItem toPayItem(PayItemDTO dto, String currency, Integer scale) {
        if (dto == null) {
            return null;
        }
        
        return PayItem.builder()
                .channelId(dto.getChannelId())
                .tradeNo(dto.getTradeNo())
                .amount(dto.getAmount() != null ? 
                        Money.of(dto.getAmount(), currency, scale) : null)
                .build();
    }

    private List<InvoiceItemDTO> toInvoiceItemDTOList(List<InvoiceItem> items) {
        if (items == null) {
            return null;
        }
        
        return items.stream()
                .map(this::toInvoiceItemDTO)
                .collect(Collectors.toList());
    }


    private List<PayItemDTO> toPayItemDTOList(List<PayItem> items,  Map<String, SysPaymentChannelDto> channels) {
        if (items == null) {
            return null;
        }
        
        return items.stream()
                .map(item -> toPayItemDTO(item, channels))
                .collect(Collectors.toList());
    }

    private List<PayItem> toPayItemList(List<PayItemDTO> dtos, String currency, Integer scale) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(dto -> toPayItem(dto, currency, scale))
                .collect(Collectors.toList());
    }

    /**
     * 转换为商户发票DTO
     *
     * @param entity 发票实体
     * @param counterparty 交易对手方实体
     * @return 商户发票DTO
     */
    public MerchantInvoiceDTO toMerchantDTO(InvoiceEntity entity, PartyEntity counterparty) {
        MerchantInvoiceDTO dto = new MerchantInvoiceDTO();
        // 设置基本信息
        dto.setInvoiceId(entity.getId());
        dto.setOrderId(entity.getOrderId());
        dto.setStatus(entity.getStatus());
        dto.setCreateTime(entity.getCreateTime());
        
        // 设置金额信息
        dto.setAmount(Money.create(entity.getTotalAmount(), entity.getCurrency(), entity.getScale()).toDecimal());
        
        // 设置交易对手方信息
        if (counterparty != null) {
            dto.setCounterpartyName(counterparty.getName());
            dto.setCounterpartyPhone(counterparty.getPhone());
            dto.setCounterpartyType(counterparty.getPartyType());
        }
        
        return dto;
    }

    /**
     * 转换为用户发票DTO
     *
     * @param entity 发票实体
     * @param counterparty 交易对手方实体
     * @return 用户发票DTO
     */
    public UserInvoiceDTO toUserDTO(InvoiceEntity entity, PartyEntity counterparty) {
        UserInvoiceDTO dto = new UserInvoiceDTO();
        // 设置基本信息
        dto.setInvoiceId(entity.getId());
        dto.setOrderId(entity.getOrderId());
        dto.setStatus(entity.getStatus());
        dto.setCreateTime(entity.getCreateTime());
        
        // 设置金额信息
        dto.setAmount(Money.create(entity.getTotalAmount(), entity.getCurrency(), entity.getScale()).toDecimal());
        
        // 设置交易对手方信息
        if (counterparty != null) {
            dto.setCounterpartyName(counterparty.getName());
            dto.setCounterpartyPhone(counterparty.getPhone());
            dto.setCounterpartyType(counterparty.getPartyType());
        }
        
        return dto;
    }
}