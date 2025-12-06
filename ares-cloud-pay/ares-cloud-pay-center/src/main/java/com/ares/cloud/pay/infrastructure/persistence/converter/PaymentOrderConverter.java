package com.ares.cloud.pay.infrastructure.persistence.converter;

import com.ares.cloud.pay.domain.enums.PaymentMethod;
import com.ares.cloud.pay.domain.enums.PaymentStatus;
import com.ares.cloud.pay.domain.model.PaymentOrder;
import com.ares.cloud.pay.infrastructure.persistence.entity.PaymentOrderEntity;
import org.ares.cloud.common.convert.BaseConvert;
import org.ares.cloud.common.model.Money;
import org.ares.cloud.common.utils.JsonUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 支付订单转换器
 */
@Component
public class PaymentOrderConverter implements BaseConvert<PaymentOrderEntity, PaymentOrder> {
    
    @Override
    public PaymentOrder toDto(PaymentOrderEntity entity) {
        if (entity == null) {
            return null;
        }
        
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setId(entity.getOrderNo()); // 使用orderNo作为ID
        paymentOrder.setOrderNo(entity.getOrderNo());
        paymentOrder.setMerchantOrderNo(entity.getMerchantOrderNo());
        paymentOrder.setMerchantId(entity.getMerchantId());
        paymentOrder.setUserId(entity.getMerchantId());
        paymentOrder.setAmount(entity.getAmount() != null ? 
                Money.create(entity.getAmount(), entity.getCurrency(), entity.getScale()) : null);
        paymentOrder.setPaymentRegion(entity.getPaymentRegion() != null ? entity.getPaymentRegion() : entity.getCurrency());
        paymentOrder.setSubject(entity.getSubject());
        paymentOrder.setDescription(entity.getBody());
        paymentOrder.setReturnUrl(entity.getReturnUrl());
        paymentOrder.setNotifyUrl(entity.getNotifyUrl());
        paymentOrder.setStatus(convertToPaymentStatus(entity.getStatus()));
        paymentOrder.setPaymentMethod(convertToPaymentMethod(entity.getPaymentMethod()));
        paymentOrder.setChannel(entity.getChannel() != null ? entity.getChannel().toString() : null);
        paymentOrder.setExpireTime(entity.getExpireTime());
        paymentOrder.setPayTime(entity.getPayTime());
        paymentOrder.setCreateTime(entity.getCreateTime());
        paymentOrder.setUpdateTime(entity.getUpdateTime());
        
        // 处理自定义参数（JSON字符串转Map）
        if (entity.getCustomParams() != null && !entity.getCustomParams().trim().isEmpty()) {
            try {
                Map<String, String> customParams = JsonUtils.parseObject(entity.getCustomParams(), Map.class);
                paymentOrder.setCustomParams(customParams);
            } catch (Exception e) {
                // 如果解析失败，设置为空Map
                paymentOrder.setCustomParams(new HashMap<>());
            }
        } else {
            paymentOrder.setCustomParams(new HashMap<>());
        }
        
        return paymentOrder;
    }
    
    @Override
    public List<PaymentOrder> listToDto(List<PaymentOrderEntity> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public PaymentOrderEntity toEntity(PaymentOrder dto) {
        if (dto == null) {
            return null;
        }
        
        PaymentOrderEntity entity = new PaymentOrderEntity();
        entity.setOrderNo(dto.getOrderNo());
        entity.setMerchantOrderNo(dto.getMerchantOrderNo());
        entity.setMerchantId(dto.getMerchantId());
        entity.setAmount(dto.getAmount() != null ? dto.getAmount().getAmount() : null);
        entity.setCurrency(dto.getPaymentRegion());
        entity.setScale(dto.getAmount() != null ? dto.getAmount().getScale() : 2);
        entity.setPaymentRegion(dto.getPaymentRegion());
        entity.setSubject(dto.getSubject());
        entity.setBody(dto.getDescription());
        entity.setReturnUrl(dto.getReturnUrl());
        entity.setNotifyUrl(dto.getNotifyUrl());
        entity.setStatus(convertFromPaymentStatus(dto.getStatus()));
        entity.setPaymentMethod(convertFromPaymentMethod(dto.getPaymentMethod()));
        entity.setChannel(dto.getChannel() != null ? Integer.parseInt(dto.getChannel()) : 1);
        entity.setExpireTime(dto.getExpireTime());
        entity.setPayTime(dto.getPayTime());
        entity.setCreateTime(dto.getCreateTime());
        entity.setUpdateTime(dto.getUpdateTime());
        
        // 处理自定义参数（Map转JSON字符串）
        if (dto.getCustomParams() != null && !dto.getCustomParams().isEmpty()) {
            try {
                String customParamsJson = JsonUtils.toJsonString(dto.getCustomParams());
                entity.setCustomParams(customParamsJson);
            } catch (Exception e) {
                // 如果转换失败，设置为空字符串
                entity.setCustomParams("");
            }
        } else {
            entity.setCustomParams("");
        }
        
        return entity;
    }
    
    @Override
    public List<PaymentOrderEntity> listToEntities(List<PaymentOrder> list) {
        if (list == null) {
            return null;
        }
        return list.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
    
    /**
     * 将数据库状态转换为枚举
     */
    private PaymentStatus convertToPaymentStatus(Integer status) {
        if (status == null) {
            return PaymentStatus.WAITING;
        }
        
        switch (status) {
            case 0: return PaymentStatus.WAITING;
            case 1: return PaymentStatus.SUCCESS;
            case 2: return PaymentStatus.FAILED;
            case 3: return PaymentStatus.CLOSED;
            default: return PaymentStatus.WAITING;
        }
    }
    
    /**
     * 将枚举转换为数据库状态
     */
    private Integer convertFromPaymentStatus(PaymentStatus status) {
        if (status == null) {
            return 0;
        }
        
        switch (status) {
            case WAITING: return 0;
            case PROCESSING: return 0;
            case SUCCESS: return 1;
            case FAILED: return 2;
            case CLOSED: return 3;
            case REFUNDING: return 0;
            case REFUNDED: return 1;
            case PARTIAL_REFUNDED: return 1;
            default: return 0;
        }
    }
    
    /**
     * 将数据库渠道转换为枚举
     */
    private PaymentMethod convertToPaymentMethod(Integer channel) {
        if (channel == null) {
            return PaymentMethod.BALANCE;
        }
        
        switch (channel) {
            case 1: return PaymentMethod.BALANCE;
            default: return PaymentMethod.BALANCE;
        }
    }
    
    /**
     * 将枚举转换为数据库渠道
     */
    private Integer convertFromPaymentMethod(PaymentMethod method) {
        if (method == null) {
            return 1;
        }
        
        switch (method) {
            case BALANCE: return 1;
            case RECHARGE: return 1;
            case DEDUCTION: return 1;
            case TRANSFER: return 1;
            case MERCHANT_PAYMENT: return 1;
            case REFUND: return 1;
            default: return 1;
        }
    }
} 