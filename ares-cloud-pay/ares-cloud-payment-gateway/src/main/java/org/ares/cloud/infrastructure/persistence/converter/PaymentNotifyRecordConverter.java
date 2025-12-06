package org.ares.cloud.infrastructure.persistence.converter;

import org.ares.cloud.domain.model.PaymentNotifyRecord;
import org.ares.cloud.domain.model.enums.PaymentStatus;
import org.ares.cloud.infrastructure.persistence.entity.PaymentNotifyRecordEntity;
import org.springframework.stereotype.Component;

@Component
public class PaymentNotifyRecordConverter {

    public PaymentNotifyRecordEntity toEntity(PaymentNotifyRecord record) {
        if (record == null) {
            return null;
        }
        
        PaymentNotifyRecordEntity entity = new PaymentNotifyRecordEntity();
        entity.setNotifyId(record.getNotifyId());
        entity.setOrderNo(record.getOrderNo());
        entity.setMerchantId(record.getMerchantId());
        entity.setStatus(record.getStatus().name());
        entity.setNotifyParams(record.getNotifyParams());
        entity.setNotifyResult(record.getNotifyResult());
        entity.setRetryCount(record.getRetryCount());
        entity.setNextRetryTime(record.getNextRetryTime());
        entity.setCreateTime(record.getCreateTime());
        entity.setUpdateTime(record.getUpdateTime());
        return entity;
    }

    public PaymentNotifyRecord toDomain(PaymentNotifyRecordEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return PaymentNotifyRecord.builder()
            .notifyId(entity.getNotifyId())
            .orderNo(entity.getOrderNo())
            .merchantId(entity.getMerchantId())
            .status(PaymentStatus.valueOf(entity.getStatus()))
            .notifyParams(entity.getNotifyParams())
            .notifyResult(entity.getNotifyResult())
            .retryCount(entity.getRetryCount())
            .nextRetryTime(entity.getNextRetryTime())
            .createTime(entity.getCreateTime())
            .updateTime(entity.getUpdateTime())
            .build();
    }
} 