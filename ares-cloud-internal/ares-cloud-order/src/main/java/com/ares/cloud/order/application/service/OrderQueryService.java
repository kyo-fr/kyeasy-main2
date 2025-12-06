package com.ares.cloud.order.application.service;

import com.ares.cloud.order.application.query.DeliveryOrderQuery;
import com.ares.cloud.order.domain.enums.*;
import com.ares.cloud.order.domain.service.MerchantService;
import com.ares.cloud.order.infrastructure.persistence.entity.*;
import com.ares.cloud.order.infrastructure.persistence.mapper.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.common.utils.DateUtils;
import org.ares.cloud.common.utils.StringUtils;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import com.ares.cloud.order.application.dto.DeliveryInfoDTO;
import com.ares.cloud.order.application.dto.OrderDTO;
import com.ares.cloud.order.application.dto.OrderStatusLogDTO;
import com.ares.cloud.order.application.query.CommonOrdersQuery;
import com.ares.cloud.order.application.query.OrderQuery;
import com.ares.cloud.order.domain.model.valueobject.MerchantInfo;
import com.ares.cloud.order.infrastructure.persistence.converter.DeliveryInfoConverter;
import com.ares.cloud.order.infrastructure.persistence.converter.OrderConverter;
import com.ares.cloud.order.infrastructure.persistence.converter.OrderStatusLogConverter;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.ares.cloud.order.application.dto.ReservationInfoDTO;
import com.ares.cloud.order.infrastructure.persistence.converter.ReservationInfoConverter;
import com.ares.cloud.order.infrastructure.persistence.entity.ReservationInfoDO;
import com.ares.cloud.order.infrastructure.persistence.mapper.ReservationInfoMapper;

/**
 * 订单查询服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderQueryService  extends BaseServiceImpl<OrderMapper, OrderEntity>{

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderConverter orderConverter;
    private final DeliveryInfoMapper deliveryInfoMapper;
    private final DeliveryInfoConverter deliveryInfoConverter;
    private final ProductSpecificationMapper productSpecificationMapper;
    private final MerchantService merchantService;
    private final OrderStatusLogMapper orderStatusLogMapper;
    private final OrderStatusLogConverter orderStatusLogConverter;
    private final ReservationInfoMapper reservationInfoMapper;
    private final ReservationInfoConverter reservationInfoConverter;

    /**
     * 查询订单列表
     *
     * @param query 查询参数
     * @return 订单列表
     */

    public PageResult<OrderDTO> queryOrders(OrderQuery query) {
        log.debug("Query orders with params: {}", query);
        
        // 查询订单
        IPage<OrderEntity> page = orderMapper.selectPage(getPage(query), buildQueryWrapper(query));
        // 查询订单项并组装
        List<OrderDTO> orderDTOs = page.getRecords().stream()
            .map(entity -> {
                // 分页查询时，只获取订单项基本信息，不查询规格详情
                List<OrderItemEntity> items = queryOrderItemsBasic(entity.getId());
                //根据商户id查询商户信息
                MerchantInfo merchantInfo = merchantService.findById(entity.getMerchantId());
                // 查询配送信息
                DeliveryInfoDO deliveryInfoDO = deliveryInfoMapper.selectByOrderId(entity.getId());
                var dto = orderConverter.toDTO(entity, items, deliveryInfoDO,merchantInfo);
                // 如果是预定订单
                if (OrderType.RESERVATION.equals(entity.getOrderType())){
                    ReservationInfoDO reservationInfoDO = reservationInfoMapper.selectOne(
                        new LambdaQueryWrapper<ReservationInfoDO>()
                            .eq(ReservationInfoDO::getOrderId, entity.getId())
                    );
                    if (reservationInfoDO != null) {
                        dto.setReservationInfo(reservationInfoConverter.toDTO(reservationInfoDO));
                    }
                }
                return dto;
            })
            .toList();
            
        return new PageResult<>(orderDTOs, page.getTotal());
    }

    /**
     * 获取配送订单列表
     *
     * @param query 查询参数
     * @return 配送订单列表
     */

     public PageResult<OrderDTO>  getDeliveryOrders(DeliveryOrderQuery query) {
         LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();

         String merchantId = ApplicationContext.getTenantId();
         if (StringUtils.isBlank(merchantId)) {
             throw new BusinessException(OrderError.MERCHANT_NOT_EXIST);
         }
         MerchantInfo merchantInfo = merchantService.findById(merchantId);
         if (merchantInfo == null) {
             throw new RequestBadException(OrderError.MERCHANT_NOT_EXIST);
         }
         wrapper.eq(OrderEntity::getMerchantId, merchantId);
         if (query.getOrderNumber() != null){
             wrapper.eq(OrderEntity::getOrderNumber, query.getOrderNumber());
         }
         // 加入订单类型限制
         wrapper.eq(OrderEntity::getOrderType, OrderType.DELIVERY.getValue());
         // 配送类型。必须是商户自配送
         wrapper.eq(OrderEntity::getDeliveryType, DeliveryType.MERCHANT.getValue());
         if (query.getStatus() != null) {
             wrapper.eq(OrderEntity::getDeliveryStatus,query.getStatus());
             // 订单状态为待接单时，要加上骑手的id作为查询条件
             if (query.getStatus() != DeliveryStatus.ORDER_CREATED.getValue()) {
                wrapper.eq(OrderEntity::getRiderId, ApplicationContext.getUserId());
             }
         }
         // 查询订单
         IPage<OrderEntity> page = orderMapper.selectPage(getPage(query), wrapper);
         // 查询订单项并组装
         List<OrderDTO> orderDTOs = page.getRecords().stream()
                 .map(entity -> {
                     // 分页查询时，只获取订单项基本信息，不查询规格详情
                     List<OrderItemEntity> items = queryOrderItemsBasic(entity.getId());
                     // 查询配送信息
                     DeliveryInfoDO deliveryInfoDO = deliveryInfoMapper.selectByOrderId(entity.getId());
                     return orderConverter.toDTO(entity, items, deliveryInfoDO,merchantInfo);
                 })
                 .toList();

         return new PageResult<>(orderDTOs, page.getTotal());
     }
    /**
     * 查询订单详情，包含订单项和规格信息
     *
     * @param orderId 订单ID
     * @return 订单详情DTO，如果订单不存在则返回null
     */
    public OrderDTO getOrderDetail(String orderId) {
        // 查询订单
        OrderEntity orderEntity = orderMapper.selectById(orderId);
        if (orderEntity == null) {
            return null;
        }
        
        // 查询订单项
        List<OrderItemEntity> items = queryOrderItems(orderId);
        
        // 查询每个订单项的规格信息
        for (OrderItemEntity item : items) {
            // 使用LambdaQueryWrapper查询订单项的规格信息
            LambdaQueryWrapper<ProductSpecificationEntity> specWrapper = new LambdaQueryWrapper<>();
            specWrapper.eq(ProductSpecificationEntity::getOrderItemId, item.getId());
            List<ProductSpecificationEntity> specifications = productSpecificationMapper.selectList(specWrapper);
            
            // 将规格信息设置到订单项中
            item.setSpecifications(specifications);
        }

        // 查询配送信息
        DeliveryInfoDO deliveryInfoDO = deliveryInfoMapper.selectByOrderId(orderEntity.getId());
        
        // 查询预订信息
        ReservationInfoDTO reservationInfoDTO = null;
        if (OrderType.RESERVATION.equals(orderEntity.getOrderType())) {
            ReservationInfoDO reservationInfoDO = reservationInfoMapper.selectOne(
                new LambdaQueryWrapper<ReservationInfoDO>()
                    .eq(ReservationInfoDO::getOrderId, orderId)
            );
            if (reservationInfoDO != null) {
                // 转换为DTO
                reservationInfoDTO = reservationInfoConverter.toDTO(reservationInfoDO);
            }
        }
        MerchantInfo merchantInfo = merchantService.findById(orderEntity.getMerchantId());
        // 转换为DTO
        OrderDTO orderDTO = orderConverter.toDTO(orderEntity, items, deliveryInfoDO,merchantInfo);
        // 设置预订信息
        orderDTO.setReservationInfo(reservationInfoDTO);

        return orderDTO;
    }

    /**
     * 查询订单的所有订单项
     *
     * @param orderId 订单ID
     * @return 订单项列表
     */
    private List<OrderItemEntity> queryOrderItems(String orderId) {
        LambdaQueryWrapper<OrderItemEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderItemEntity::getOrderId, orderId);
        return orderItemMapper.selectList(wrapper);
    }
    
    /**
     * 查询订单项基本信息，不包含规格详情，用于提高分页查询性能
     *
     * @param orderId 订单ID
     * @return 订单项基本信息列表
     */
    private List<OrderItemEntity> queryOrderItemsBasic(String orderId) {
        LambdaQueryWrapper<OrderItemEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderItemEntity::getOrderId, orderId);
        return orderItemMapper.selectList(wrapper);
    }
    /**
     * 统计商户各类型订单数量
     *
     * @param merchantId 商户ID
     * @return 各类型订单数量统计
     */
    public Map<OrderType, Long> countOrdersByType(String merchantId) {
        log.debug("Count orders by type for merchant: {}", merchantId);

        // 查询商户所有订单
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderEntity::getMerchantId, merchantId)
                .notIn(OrderEntity::getStatus,
                List.of(
                    orderConverter.convertFromOrderStatus(OrderStatus.CANCELLED),
                    orderConverter.convertFromOrderStatus(OrderStatus.TO_BE_CONFIRMED)
                )); // 排除已取消和待确认订单

        List<OrderEntity> orders = orderMapper.selectList(wrapper);

        // 按订单类型分组统计
        return orders.stream()
                .collect(Collectors.groupingBy(
                        OrderEntity::getOrderType,
                        Collectors.counting()
                ));
    }

    /**
     * 统计商户当日营业额
     *
     * @param merchantId 商户ID
     * @param date 统计日期
     * @return 当日营业额
     */
    public BigDecimal calculateDailyRevenue(String merchantId, LocalDate date) {
        log.debug("Calculate daily revenue for merchant: {} on date: {}", merchantId, date);

        // 获取日期的起止时间戳
        long startTime = date.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
        long endTime = date.plusDays(1).atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();

        // 查询指定日期的已完成订单
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderEntity::getMerchantId, merchantId)
                .ge(OrderEntity::getCreateTime, startTime)
                .lt(OrderEntity::getCreateTime, endTime);

        List<OrderEntity> orders = orderMapper.selectList(wrapper);

        // 计算总营业额
        return orders.stream()
                .map(order -> {
                    // 考虑金额精度，转换为BigDecimal
                    long amount = order.getTotalAmount();
                    return BigDecimal.valueOf(amount).movePointLeft(2);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    /**
     * 构建查询条件
     */
    private LambdaQueryWrapper<OrderEntity> buildQueryWrapper(OrderQuery query) {
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();

        String merchantId = ApplicationContext.getTenantId();
        if (StringUtils.isBlank(merchantId)) {
            merchantId = query.getMerchantId();
        }
        MerchantInfo merchantInfo = merchantService.findById(merchantId);
        if (merchantInfo == null) {
            throw new RequestBadException(OrderError.MERCHANT_NOT_EXIST);
        }

        if (query.getOrderNumber() != null){
            wrapper.eq(OrderEntity::getOrderNumber, query.getOrderNumber());
        }

        if (query.getOrderType() != null) {
            wrapper.eq(OrderEntity::getOrderType, query.getOrderType());
        }
        
        if (StringUtils.isNotBlank(query.getMerchantId())) {
            wrapper.eq(OrderEntity::getMerchantId, query.getMerchantId());
        }

        if (query.getMerchantName() != null) {
            //根据商户名称查询商户ID
            List<MerchantInfo> byName = merchantService.findByName(query.getMerchantName());
            //根据商户id集合查询商户信息
            if (!byName.isEmpty()) {
                wrapper.in(OrderEntity::getMerchantId, byName.stream().map(MerchantInfo::getId).collect(Collectors.toList()));
            }else {
                wrapper.eq(OrderEntity::getMerchantId,null);
            }
        }

        if (query.getStatus() != null) {
            wrapper.eq(OrderEntity::getStatus, query.getStatus());
        }

        // 桌号查询(店内就餐)
        if (StringUtils.isNotBlank(query.getTableNo())) {
            wrapper.eq(OrderEntity::getTableNo, query.getTableNo());
        }
        
        // 预定时间查询
        if (query.getReservationTime() != null) {
            wrapper.eq(OrderEntity::getReservationTime, DateUtils.convertTimezoneToUtc(query.getReservationTime(),merchantInfo.getTimezone()));
        }
        
        // 创建时间范围查询
        if (query.getStartTime() != null) {
            wrapper.ge(OrderEntity::getCreateTime, DateUtils.convertTimezoneToUtc(query.getStartTime(),merchantInfo.getTimezone()));
        }
        
        if (query.getEndTime() != null) {
            wrapper.le(OrderEntity::getCreateTime, DateUtils.convertTimezoneToUtc(query.getEndTime(),merchantInfo.getTimezone()));
        }

        if (query.getSourceType() != null) {
            wrapper.eq(OrderEntity::getSourceType, query.getSourceType());
        }

        // 取消原因查询
        if (StringUtils.isNotBlank(query.getCancelReason())) {
            wrapper.like(OrderEntity::getCancelReason, query.getCancelReason());
        }

        // 取消时间范围查询
        if (query.getCancelStartTime() != null) {
            wrapper.ge(OrderEntity::getCancelTime, DateUtils.convertTimezoneToUtc(query.getCancelStartTime(), merchantInfo.getTimezone()));
        }
        
        if (query.getCancelEndTime() != null) {
            wrapper.le(OrderEntity::getCancelTime, DateUtils.convertTimezoneToUtc(query.getCancelEndTime(), merchantInfo.getTimezone()));
        }

        // 关键字处理
        if (StringUtils.isNotBlank(query.getKeyword())) {
            wrapper.and(
                andWrapper -> {
                    andWrapper.like(OrderEntity::getOrderNumber, query.getKeyword())
                        .or()
                        .like(OrderEntity::getPhoneNumber, query.getKeyword())
                        .or()
                        .like(OrderEntity::getOrderCode, query.getKeyword());
                }
            );
        }
        
        wrapper.orderByDesc(OrderEntity::getCreateTime);
        
        return wrapper;
    }

    /**
     * 查询订单配送信息
     *
     * @param orderId 订单ID
     * @return 配送信息DTO
     */
    public DeliveryInfoDTO getDeliveryInfo(String orderId) {
        log.debug("Query delivery info for order: {}", orderId);
        
        // 1. 查询订单基本信息
        OrderEntity orderEntity = orderMapper.selectById(orderId);
        if (orderEntity == null) {
            throw new RequestBadException(OrderError.ORDER_NOT_FOUND);
        }
        
        // 2. 如果不是配送订单，返回空对象而不是null
        if (!OrderType.DELIVERY.equals(orderEntity.getOrderType())) {
            return new DeliveryInfoDTO(); // 返回空对象而非null
        }
        
        // 3. 查询配送信息
        DeliveryInfoDO deliveryInfoDO = deliveryInfoMapper.selectOne(
            new LambdaQueryWrapper<DeliveryInfoDO>()
                .eq(DeliveryInfoDO::getOrderId, orderId)
                .eq(DeliveryInfoDO::getDeleted, 0)
        );
        
        // 4. 如果配送信息不存在，返回空对象而不是null
        if (deliveryInfoDO == null) {
            return new DeliveryInfoDTO();
        }
        
        return deliveryInfoConverter.toDeliveryInfoDTO(deliveryInfoDO);
    }

    /**
     * 查询当日有效订单（不含预定订单）
     */
    public PageResult<OrderDTO> getDailyValidOrders(CommonOrdersQuery query) {
        log.debug("Fetching daily valid orders with params: {}", query);
        MerchantInfo merchantInfo = validateMerchant(query);
        
        // 获取当日时间范围
        DateUtils.TimeRange timeRange = DateUtils.getDailyTimeRange(merchantInfo.getTimezone());
        
        // 构建查询条件
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderEntity::getMerchantId, merchantInfo.getId())
               .ge(OrderEntity::getCreateTime, timeRange.getStartTime())
               .lt(OrderEntity::getCreateTime, timeRange.getEndTime())
                .notIn(OrderEntity::getStatus,
                    List.of(
                            orderConverter.convertFromOrderStatus(OrderStatus.CANCELLED),
                            orderConverter.convertFromOrderStatus(OrderStatus.TO_BE_CONFIRMED)
                    )); // 排除已取消和待确认订单

        return executePageQuery(query, wrapper);
    }

    /**
     * 查询所有预定订单
     */
    public PageResult<OrderDTO> getReservationOrders(CommonOrdersQuery query) {
        log.debug("Fetching all reservation orders with params: {}", query);

        MerchantInfo merchantInfo = validateMerchant(query);

        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderEntity::getMerchantId, merchantInfo.getId())
               .eq(OrderEntity::getOrderType, OrderType.RESERVATION)
               .ne(OrderEntity::getStatus, orderConverter.convertFromOrderStatus(OrderStatus.CANCELLED))
               .orderByDesc(OrderEntity::getCreateTime);

        return executePageQuery(query, wrapper);
    }

    /**
     * 查询当日预定订单
     */
    public PageResult<OrderDTO> getDailyReservationOrders(CommonOrdersQuery query) {
        log.debug("Fetching daily reservation orders with params: {}", query);

        MerchantInfo merchantInfo = validateMerchant(query);

        // 获取当日时间范围
        DateUtils.TimeRange timeRange = DateUtils.getDailyTimeRange(merchantInfo.getTimezone());
        
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderEntity::getMerchantId, merchantInfo.getId())
               .eq(OrderEntity::getOrderType, OrderType.RESERVATION)
               .ge(OrderEntity::getReservationTime, timeRange.getStartTime())
               .lt(OrderEntity::getReservationTime, timeRange.getEndTime())
               .ne(OrderEntity::getStatus, orderConverter.convertFromOrderStatus(OrderStatus.CANCELLED))
               .orderByAsc(OrderEntity::getReservationTime);

        return executePageQuery(query, wrapper);
    }

    /**
     * 查询订单状态变更记录
     *
     * @param orderId 订单ID
     * @param statusType 订单状态类型
     * @return 订单状态变更记录列表
     */
    public List<OrderStatusLogDTO> queryStatusLogs(String orderId, OrderStatusType statusType) {
        LambdaQueryWrapper<OrderStatusLogEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OrderStatusLogEntity::getOrderId, orderId)
                .eq(statusType != null, OrderStatusLogEntity::getStatusType, statusType)
                .orderByAsc(OrderStatusLogEntity::getOperateTime);


        return orderStatusLogConverter.toDTOList(orderStatusLogMapper.selectList(wrapper));
    }
    /**
     * 商户校验
     * @param query 查询
     * @return 商户信息
     */
    private MerchantInfo validateMerchant(CommonOrdersQuery query) {
        String merchantId = ApplicationContext.getTenantId();
        if (StringUtils.isBlank(merchantId)) {
            merchantId = query.getMerchantId();
        }
        MerchantInfo merchantInfo = merchantService.findById(merchantId);
        if (merchantInfo == null) {
            throw new RequestBadException(OrderError.MERCHANT_NOT_EXIST);
        }
        return merchantInfo;
    }


    /**
     * 执行分页查询
     * @param query 查询参数
     * @param wrapper 查询调价
     * @return 分页结果
     */
    private PageResult<OrderDTO> executePageQuery(CommonOrdersQuery query, LambdaQueryWrapper<OrderEntity> wrapper) {
        IPage<OrderEntity> page = orderMapper.selectPage(getPage(query), wrapper);
        List<OrderDTO> dtos = page.getRecords().stream()
            .map(entity -> {
                List<OrderItemEntity> items = queryOrderItemsBasic(entity.getId());
                return orderConverter.toDTO(entity, items, null,null);
            })
            .collect(Collectors.toList());
        return new PageResult<>(dtos, page.getTotal());
    }

    /**
     * 查询预订信息
     *
     * @param orderId 订单ID
     * @return 预订信息DTO
     */
    public ReservationInfoDTO getReservationInfo(String orderId) {
        log.debug("Query reservation info for order: {}", orderId);
        
        // 1. 查询订单基本信息
        OrderEntity orderEntity = orderMapper.selectById(orderId);
        if (orderEntity == null) {
            throw new RequestBadException(OrderError.ORDER_NOT_FOUND);
        }
        
        // 2. 如果不是预订订单，返回空对象而不是null
        if (!OrderType.RESERVATION.equals(orderEntity.getOrderType())) {
            return new ReservationInfoDTO();
        }
        
        // 3. 查询预订信息
        ReservationInfoDO reservationInfoDO = reservationInfoMapper.selectOne(
            new LambdaQueryWrapper<ReservationInfoDO>()
                .eq(ReservationInfoDO::getOrderId, orderId)
        );
        
        // 4. 如果预订信息不存在，返回空对象而不是null
        if (reservationInfoDO == null) {
            return new ReservationInfoDTO();
        }
        
        // 5. 转换并返回
        return reservationInfoConverter.toDTO(reservationInfoDO);
    }

}