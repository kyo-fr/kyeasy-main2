package com.ares.cloud.order.application.query;

import com.ares.cloud.order.domain.enums.DeliveryStatus;
import com.ares.cloud.order.domain.enums.OrderStatus;
import com.ares.cloud.order.domain.enums.OrderType;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.PageQuery;
import org.ares.cloud.common.query.Query;

/**
 * 配送订单查询对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "订单查询对象")
public class DeliveryOrderQuery extends PageQuery {

    @Schema(description = "配送状态: 2-待接单, 3-取货中, 4-配送中, 6-完成配送, 7-已取消")
    @JsonProperty("status")
    private Integer status;
    
    @Schema(description = "开始时间(时间戳)")
    @JsonProperty("startTime")
    private Long startTime;
    
    @Schema(description = "结束时间(时间戳)")
    @JsonProperty("endTime")
    private Long endTime;
    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    @JsonProperty("orderNumber")
    private String orderNumber;
}