package com.ares.cloud.order.application.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.PageQuery;

/**
 * 订单操作日志查询条件
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "订单操作日志查询条件")
public class OrderOperationLogQuery extends PageQuery {
    /**
     * 订单ID
     */
    @Schema(description = "订单ID")
    private String orderId;

    /**
     * 开始时间
     */
    @Schema(description = "开始时间")
    private Long startTime;
    /**
     * 结束时间
     */
    @Schema(description = "结束时间")
    private Long endTime;

}