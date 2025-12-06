package com.ares.cloud.pay.infrastructure.repository;

import com.ares.cloud.pay.domain.enums.PaymentStatus;
import com.ares.cloud.pay.domain.model.PaymentOrder;
import com.ares.cloud.pay.domain.repository.PaymentOrderRepository;
import com.ares.cloud.pay.infrastructure.persistence.converter.PaymentOrderConverter;
import com.ares.cloud.pay.infrastructure.persistence.entity.PaymentOrderEntity;
import com.ares.cloud.pay.infrastructure.persistence.mapper.PaymentOrderMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 支付订单仓储实现类
 */
@Repository
public class PaymentOrderRepositoryImpl implements PaymentOrderRepository {
    
    @Resource
    private PaymentOrderMapper paymentOrderMapper;

    @Resource
    private PaymentOrderConverter paymentOrderConverter;
    
    @Override
    public void save(PaymentOrder paymentOrder) {
        PaymentOrderEntity entity = paymentOrderConverter.toEntity(paymentOrder);
        if (paymentOrderMapper.selectById(entity.getOrderNo()) == null) {
            paymentOrderMapper.insert(entity);
        } else {
            paymentOrderMapper.updateById(entity);
        }
    }
    
    @Override
    public PaymentOrder findByOrderNo(String orderNo) {
        LambdaQueryWrapper<PaymentOrderEntity> query = Wrappers.lambdaQuery();
        query.eq(PaymentOrderEntity::getOrderNo, orderNo);
        query.eq(PaymentOrderEntity::getDeleted, 0);
        PaymentOrderEntity entity = paymentOrderMapper.selectOne(query);
        return paymentOrderConverter.toDto(entity);
    }
    
    @Override
    public PaymentOrder findByMerchantOrderNo(String merchantOrderNo) {
        LambdaQueryWrapper<PaymentOrderEntity> query = Wrappers.lambdaQuery();
        query.eq(PaymentOrderEntity::getMerchantOrderNo, merchantOrderNo);
        query.eq(PaymentOrderEntity::getDeleted, 0);
        PaymentOrderEntity entity = paymentOrderMapper.selectOne(query);
        return paymentOrderConverter.toDto(entity);
    }
    
    @Override
    public List<PaymentOrder> findByMerchantId(String merchantId) {
        List<PaymentOrderEntity> entities = paymentOrderMapper.findByMerchantId(merchantId);
        return paymentOrderConverter.listToDto(entities);
    }
    
    @Override
    public List<PaymentOrder> findByUserId(String userId) {
        List<PaymentOrderEntity> entities = paymentOrderMapper.findByUserId(userId);
        return paymentOrderConverter.listToDto(entities);
    }
    
    @Override
    public List<PaymentOrder> findByStatus(PaymentStatus status) {
        Integer statusValue = convertFromPaymentStatus(status);
        List<PaymentOrderEntity> entities = paymentOrderMapper.findByStatus(statusValue);
        return paymentOrderConverter.listToDto(entities);
    }
    
    @Override
    public void updateStatus(String orderNo, PaymentStatus status) {
        PaymentOrderEntity entity = new PaymentOrderEntity();
        entity.setOrderNo(orderNo);
        entity.setStatus(convertFromPaymentStatus(status));
        paymentOrderMapper.updateById(entity);
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
} 