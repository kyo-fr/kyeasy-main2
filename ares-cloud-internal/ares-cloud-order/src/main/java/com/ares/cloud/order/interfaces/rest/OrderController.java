package com.ares.cloud.order.interfaces.rest;

import com.ares.cloud.order.application.query.DeliveryOrderQuery;
import com.ares.cloud.order.application.query.OrderOperationLogQuery;
import com.ares.cloud.order.domain.model.aggregate.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.model.Result;
import com.ares.cloud.order.application.dto.DeliveryInfoDTO;
import com.ares.cloud.order.application.dto.OrderDTO;
import com.ares.cloud.order.application.dto.OrderStatusLogDTO;
import com.ares.cloud.order.application.query.CommonOrdersQuery;
import com.ares.cloud.order.application.query.OrderQuery;
import com.ares.cloud.order.application.service.OrderQueryService;
import com.ares.cloud.order.domain.enums.OrderType;
import com.ares.cloud.order.domain.enums.OrderStatus;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 订单查询接口
 */
@Tag(name = "订单查询接口")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderQueryService orderQueryService;


    
    @Operation(summary = "查询订单配送信息")
    @GetMapping("/{orderId}/delivery")
    public Result<DeliveryInfoDTO> getDeliveryInfo(@PathVariable("orderId") String orderId) {
        DeliveryInfoDTO deliveryInfo = orderQueryService.getDeliveryInfo(orderId);
        // 如果订单不是配送订单或配送信息不存在，返回空数据而不是null
        if (deliveryInfo == null) {
            return Result.success(new DeliveryInfoDTO());
        }
        return Result.success(deliveryInfo);
    }

    // ========== 查询统计接口 ==========
    @Operation(summary = "分页查询订单")
    @GetMapping
    public Result<PageResult<OrderDTO>> queryOrders(@ParameterObject OrderQuery query) {
        PageResult<OrderDTO> result = orderQueryService.queryOrders(query);
        return Result.success(result);
    }

    @Operation(summary = "配送订单查询")
    @GetMapping("/delivery")
    public Result<PageResult<OrderDTO>> getDeliveryOrders(@ParameterObject DeliveryOrderQuery query) {
        PageResult<OrderDTO> result = orderQueryService.getDeliveryOrders(query);
        return Result.success(result);
    }

    @Operation(summary = "查询订单详情")
    @GetMapping("/{orderId}")
    public Result<OrderDTO> getOrderDetail(@PathVariable(name="orderId") String orderId) {
        OrderDTO orderDTO = orderQueryService.getOrderDetail(orderId);
        return Result.success(orderDTO);
    }

    @Operation(summary = "查询订单状态变更记录")
    @GetMapping("/{orderId}/status-logs")
    public Result<List<OrderStatusLogDTO>> getStatusLogs(@PathVariable(name="orderId") String orderId) {
        List<OrderStatusLogDTO> logs = orderQueryService.queryStatusLogs(orderId, null);
        return Result.success(logs);
    }

    @Operation(summary = "统计商户各类型订单数量")
    @GetMapping("/statistics/type-count")
    public Result<Map<OrderType, Long>> countOrdersByType(@RequestParam String merchantId) {
        Map<OrderType, Long> result = orderQueryService.countOrdersByType(merchantId);
        return Result.success(result);
    }

    @Operation(summary = "统计商户当日营业额")
    @GetMapping("/statistics/daily-revenue")
    public Result<BigDecimal> calculateDailyRevenue(
        @RequestParam String merchantId,
        @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        BigDecimal revenue = orderQueryService.calculateDailyRevenue(merchantId, date);
        return Result.success(revenue);
    }

    @Operation(summary = "查询当日有效订单")
    @GetMapping("/statistics/daily-orders")
    public Result<PageResult<OrderDTO>> getDailyValidOrders(@ParameterObject CommonOrdersQuery query) {
        PageResult<OrderDTO> orders = orderQueryService.getDailyValidOrders(query);
        return Result.success(orders);
    }

    @Operation(summary = "查询预定订单")
    @GetMapping("/statistics/reservation-orders")
    public Result<PageResult<OrderDTO>> getReservationOrders(@ParameterObject CommonOrdersQuery query) {
        PageResult<OrderDTO> orders = orderQueryService.getReservationOrders(query);
        return Result.success(orders);
    }

    @Operation(summary = "查询取消订单")
    @GetMapping("/cancelled")
    public Result<PageResult<OrderDTO>> getCancelledOrders(@ParameterObject OrderQuery query) {
        // 设置查询条件为已取消状态
        query.setStatus(String.valueOf(OrderStatus.CANCELLED.getValue()));
        PageResult<OrderDTO> result = orderQueryService.queryOrders(query);
        return Result.success(result);
    }

    @Operation(summary = "查询当日预定订单")
    @GetMapping("/statistics/daily-reservation-orders")
    public Result<PageResult<OrderDTO>> getDailyReservationOrders(@Validated CommonOrdersQuery query) {
        PageResult<OrderDTO> orders = orderQueryService.getDailyReservationOrders(query);
        return Result.success(orders);
    }
}