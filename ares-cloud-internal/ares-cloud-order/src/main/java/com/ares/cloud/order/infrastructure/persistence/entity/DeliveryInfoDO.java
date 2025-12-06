package com.ares.cloud.order.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import com.ares.cloud.order.domain.enums.DeliveryType;

@Data
@TableName("ORDER_DELIVERY_INFO")
public class DeliveryInfoDO {

    @TableId(type = IdType.ASSIGN_ID)
    private String id;
    
    private String orderId;
    
    @EnumValue
    private DeliveryType deliveryType;
    
    private String riderId;
    
    private String deliveryCompany;
    
    private String trackingNo;
    
    private String deliveryPhone;

    private String deliveryName;
    
    private Long deliveryStartTime;
    
    /**
     * 收货人姓名
     */
    private String receiverName;
    
    /**
     * 收货人电话
     */
    private String receiverPhone;
    
    /**
     * 配送地址
     */
    private String deliveryAddress;
    
    /**
     * 配送地址纬度
     */
    private Double deliveryLatitude;
    
    /**
     * 配送地址经度
     */
    private Double deliveryLongitude;
    
    /**
     * 配送距离(公里)
     */
    private Double deliveryDistance;
    
    /**
     * 配送国家
     */
    private String deliveryCountry;
    
    /**
     * 配送城市
     */
    private String deliveryCity;
    
    /**
     * 配送邮编
     */
    private String deliveryPostalCode;
    
    /**
     * 配送费用(分)
     */
    private Long deliveryFee;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 币种精度
     */
    private Integer currencyScale;
    
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    @TableField(fill = FieldFill.UPDATE)
    private Long updateTime;
    
    @Version
    private Integer version;
    
    @TableLogic
    private Integer deleted;
}