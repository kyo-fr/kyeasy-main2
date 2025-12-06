package org.ares.cloud.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.ares.cloud.domain.model.PaymentOrder;
import org.ares.cloud.domain.repository.PaymentOrderRepository;
import org.ares.cloud.infrastructure.persistence.converter.PaymentOrderConverter;
import org.ares.cloud.infrastructure.persistence.entity.PaymentOrderEntity;
import org.ares.cloud.infrastructure.persistence.mapper.PaymentOrderMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 支付订单仓储实现类
 */
@Repository
@RequiredArgsConstructor
public class PaymentOrderRepositoryImpl implements PaymentOrderRepository {

    private final PaymentOrderMapper paymentOrderMapper;
    private final PaymentOrderConverter paymentOrderConverter;

    /**
     * 保存支付订单
     *
     * @param order 支付订单
     */
    @Override
    public void save(PaymentOrder order) {
        PaymentOrderEntity entity = paymentOrderConverter.toEntity(order);
        if (paymentOrderMapper.selectById(order.getOrderNo()) == null) {
            paymentOrderMapper.insert(entity);
        } else {
            paymentOrderMapper.updateById(entity);
        }
    }

    /**
     * 根据订单号查询支付订单
     *
     * @param orderNo 订单号
     * @return 支付订单
     */
    @Override
    public Optional<PaymentOrder> findByOrderNo(String orderNo) {
        PaymentOrderEntity entity = paymentOrderMapper.selectById(orderNo);
        return Optional.ofNullable(entity)
            .map(paymentOrderConverter::toDomain);
    }
} 