package com.ares.cloud.order.application.query;

import com.ares.cloud.order.domain.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;
import java.math.BigDecimal;
import com.ares.cloud.order.domain.enums.OrderType;
import org.springframework.web.bind.annotation.RequestParam;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * 订单查询对象
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "订单查询对象")
public class OrderQuery extends Query {
    
    @Schema(description = "商户ID")
    @JsonProperty("merchantId")
    private String merchantId;
    
    @Schema(enumAsRef = true, description = "订单状态: 1-待确认, 2-未结算, 3-部分结算, 4-已结算, 5-待就餐, 6-就餐中, 7-已取消")
    @JsonProperty("status")
    private OrderStatus status;
    
    @Schema(description = "开始时间(时间戳)")
    @JsonProperty("startTime")
    private Long startTime;
    
    @Schema(description = "结束时间(时间戳)")
    @JsonProperty("endTime")
    private Long endTime;

    @Schema(enumAsRef = true, description = "订单类型: 1-外卖配送, 2-到店自取, 3-店内就餐, 4-预订订单, 5-虚拟订单, 6-订阅订单, 7-线上订单")
    @JsonProperty("orderType")
    private OrderType orderType;
    
    @Schema(description = "桌号")
    @JsonProperty("tableNo")
    private String tableNo;
    
    @Schema(description = "预定时间(时间戳)")
    @JsonProperty("reservationTime")
    private Long reservationTime;
    /**
     * 订单来源类型
     */
    @Schema(description = "订单来源类型:1网上下单;2商户下单")
    @JsonProperty("sourceType")
    private Integer sourceType;

    /**
     * 订单编号
     */
    @Schema(description = "订单编号")
    @JsonProperty("orderNumber")
    private String orderNumber;


    /**
     * 商户名称
     */
    @Schema(description = "商户名称")
    @JsonProperty("merchantName")
    private String merchantName;

    /**
     * 取消原因
     */
    @Schema(description = "取消原因")
    @JsonProperty("cancelReason")
    private String cancelReason;

    /**
     * 取消开始时间
     */
    @Schema(description = "取消开始时间(时间戳)")
    @JsonProperty("cancelStartTime")
    private Long cancelStartTime;

    /**
     * 取消结束时间
     */
    @Schema(description = "取消结束时间(时间戳)")
    @JsonProperty("cancelEndTime")
    private Long cancelEndTime;

    // 设置status的setter方法，用于处理字符串类型的输入
    public void setStatus(String statusStr) {
        if (statusStr == null || statusStr.isEmpty()) {
            this.status = null;
            return;
        }

        try {
            // 尝试将字符串解析为整数
            int statusValue = Integer.parseInt(statusStr);
            this.status = OrderStatus.fromValue(statusValue);
        } catch (NumberFormatException e) {
            // 如果不是数字，尝试匹配枚举名称（忽略大小写）
            for (OrderStatus status : OrderStatus.values()) {
                if (status.name().equalsIgnoreCase(statusStr)) {
                    this.status = status;
                    return;
                }
            }
            // 如果无法匹配，设置为null
            this.status = null;
        } catch (IllegalArgumentException e) {
            // 如果值不在有效范围内，设置为null
            this.status = null;
        }
    }

    // 设置orderType的setter方法，用于处理字符串类型的输入
    public void setOrderType(String orderTypeStr) {
        if (orderTypeStr == null || orderTypeStr.isEmpty()) {
            this.orderType = null;
            return;
        }

        try {
            // 使用OrderType的fromValue方法处理输入
            this.orderType = OrderType.fromValue(orderTypeStr);
        } catch (IllegalArgumentException e) {
            // 如果转换失败，设置为null
            this.orderType = null;
        }
    }
}