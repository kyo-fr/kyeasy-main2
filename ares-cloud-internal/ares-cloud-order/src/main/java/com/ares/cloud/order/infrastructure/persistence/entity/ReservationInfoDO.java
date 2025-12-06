package com.ares.cloud.order.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 预订信息DO
 */
@Data
@TableName("order_reservation_info")
public class ReservationInfoDO {
    /**
     * ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    
    /**
     * 订单ID
     */
    private String orderId;
    
    /**
     * 预订时间
     */
    private Long reservationTime;
    
    /**
     * 预订人姓名
     */
    private String reserverName;
    
    /**
     * 预订人电话
     */
    private String reserverPhone;
    
    /**
     * 就餐人数
     */
    private Integer diningNumber;
    
    /**
     * 包间要求
     */
    private String roomPreference;
    
    /**
     * 特殊餐饮要求
     */
    private String dietaryRequirements;
    
    /**
     * 预订备注
     */
    private String remarks;
} 