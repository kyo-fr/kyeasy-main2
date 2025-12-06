package com.ares.cloud.order.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.ares.cloud.order.domain.model.aggregate.Order;
import com.ares.cloud.order.domain.enums.OrderStatus;
import com.ares.cloud.order.domain.enums.OrderType;
import com.ares.cloud.order.domain.repository.OrderRepository;
import com.ares.cloud.order.infrastructure.persistence.converter.*;
import com.ares.cloud.order.infrastructure.persistence.entity.*;
import com.ares.cloud.order.infrastructure.persistence.mapper.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.ares.cloud.order.infrastructure.persistence.converter.OrderItemConverter.productSpecificationConverter;

/**
 * 订单仓储实现
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final ProductSpecificationMapper productSpecificationMapper;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderConverter orderConverter;
    private final OrderItemConverter orderItemConverter;
    private final DeliveryInfoMapper deliveryInfoMapper;
    private final DeliveryInfoConverter deliveryInfoConverter;
    private final ReservationInfoMapper reservationInfoMapper;
    private final ReservationInfoConverter reservationInfoConverter;

    @Override
    @Transactional
    public void save(Order order) {
        // 转换并保存订单
        OrderEntity orderEntity = orderConverter.toEntity(order);
        if (orderEntity.getId() == null) {
            orderMapper.insert(orderEntity);
        } else {
            orderMapper.updateById(orderEntity);
        }

        // 保存订单项，过滤掉已删除的订单项
        order.getItems().stream()
            .filter(item -> item.getDeleted() == null || !item.getDeleted())
            .forEach(item -> {
                OrderItemEntity itemEntity = orderItemConverter.toEntity(item);
                itemEntity.setOrderId(orderEntity.getId());

                if (itemEntity.getId() == null) {
                    orderItemMapper.insert(itemEntity);
                } else {
                    orderItemMapper.updateById(itemEntity);
                }

                // 保存商品规格
                item.getSpecifications().forEach(spec -> {
                    ProductSpecificationEntity specEntity = productSpecificationConverter.toEntity(spec, itemEntity.getId());
                    if (specEntity.getId() == null) {
                        productSpecificationMapper.insert(specEntity);
                    } else {
                        productSpecificationMapper.updateById(specEntity);
                    }
                });
            });
            
        // 处理已删除的订单项
        order.getItems().stream()
            .filter(item -> item.getDeleted() != null && item.getDeleted() && item.getId() != null)
            .forEach(item -> {
                OrderItemEntity itemEntity = orderItemConverter.toEntity(item);
                orderItemMapper.deleteById(itemEntity);
            });

        // 保存配送信息
        if (order.getDeliveryInfo() != null) {
            DeliveryInfoDO deliveryInfoDO = deliveryInfoConverter.toDeliveryInfoDO(order.getDeliveryInfo());
            deliveryInfoDO.setOrderId(orderEntity.getId());
            if (deliveryInfoDO.getId() == null) {
                deliveryInfoMapper.insert(deliveryInfoDO);
            } else {
                deliveryInfoMapper.updateById(deliveryInfoDO);
            }
        }

        // 保存预订信息
        if (OrderType.RESERVATION.equals(order.getOrderType()) && order.getReservationInfo() != null) {
            ReservationInfoDO reservationInfoDO = reservationInfoConverter.toReservationInfoDO(order.getReservationInfo(), orderEntity.getId());
            if (reservationInfoDO.getId() == null) {
                reservationInfoMapper.insert(reservationInfoDO);
            } else {
                reservationInfoMapper.updateById(reservationInfoDO);
            }
        }

        order.setId(orderEntity.getId());
    }

    @Override
    public Optional<Order> findById(String id) {
        // 查询订单
        OrderEntity orderEntity = orderMapper.selectById(id);
        if (orderEntity == null) {
            return Optional.empty();
        }

        // 查询订单项，只查询未删除的订单项
        LambdaQueryWrapper<OrderItemEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderItemEntity::getOrderId, id)
              .eq(OrderItemEntity::getDeleted, 0);
        List<OrderItemEntity> itemEntities = orderItemMapper.selectList(wrapper);

        // 转换为领域模型
        Order order = orderConverter.toDomain(orderEntity);
        order.setItems(itemEntities.stream()
                .map(orderItemConverter::toDomain)
                .collect(Collectors.toList()));

        // 加载配送信息
        if (OrderType.DELIVERY.equals(orderEntity.getOrderType())) {
            DeliveryInfoDO deliveryInfoDO = deliveryInfoMapper.selectByOrderId(orderEntity.getId());
            if (deliveryInfoDO != null) {
                order.setDeliveryInfo(deliveryInfoConverter.toDeliveryInfo(deliveryInfoDO));
            }
        }

        // 加载预订信息
        if (OrderType.RESERVATION.equals(orderEntity.getOrderType())) {
            ReservationInfoDO reservationInfoDO = reservationInfoMapper.selectOne(
                new LambdaQueryWrapper<ReservationInfoDO>()
                    .eq(ReservationInfoDO::getOrderId, orderEntity.getId())
            );
            if (reservationInfoDO != null) {
                order.setReservationInfo(reservationInfoConverter.toReservationInfo(reservationInfoDO));
            }
        }

        return Optional.of(order);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        // 查询指定状态的订单
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderEntity::getStatus, orderConverter.convertFromOrderStatus(status));

        return orderMapper.selectList(wrapper).stream()
                .map(entity -> {
                    // 查询订单项，只查询未删除的订单项
                    LambdaQueryWrapper<OrderItemEntity> itemWrapper = new LambdaQueryWrapper<>();
                    itemWrapper.eq(OrderItemEntity::getOrderId, entity.getId())
                              .eq(OrderItemEntity::getDeleted, 0);
                    List<OrderItemEntity> itemEntities = orderItemMapper.selectList(itemWrapper);

                    // 转换为领域模型
                    Order order = orderConverter.toDomain(entity);
                    order.setItems(itemEntities.stream()
                            .map(orderItemConverter::toDomain)
                            .collect(Collectors.toList()));
                    
                    // 加载配送信息
                    if (OrderType.DELIVERY.equals(entity.getOrderType())) {
                        DeliveryInfoDO deliveryInfoDO = deliveryInfoMapper.selectByOrderId(entity.getId());
                        if (deliveryInfoDO != null) {
                            order.setDeliveryInfo(deliveryInfoConverter.toDeliveryInfo(deliveryInfoDO));
                        }
                    }

                    // 加载预订信息
                    if (OrderType.RESERVATION.equals(entity.getOrderType())) {
                        ReservationInfoDO reservationInfoDO = reservationInfoMapper.selectOne(
                            new LambdaQueryWrapper<ReservationInfoDO>()
                                .eq(ReservationInfoDO::getOrderId, entity.getId())
                        );
                        if (reservationInfoDO != null) {
                            order.setReservationInfo(reservationInfoConverter.toReservationInfo(reservationInfoDO));
                        }
                    }

                    return order;
                })
                .collect(Collectors.toList());
    }
}