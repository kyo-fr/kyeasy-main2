package com.ares.cloud.order.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 商品规格实体
 */
@Data
@TableName("order_item_specifications")
public class ProductSpecificationEntity {
    /**
     * 规格ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 商品规格ID（来自商品服务）
     */
    private String productSpecId;

    /**
     * 订单项ID
     */
    private String orderItemId;
    
    /**
     * 规格名称
     */
    private String name;
    
    /**
     * 规格值
     */
    private String value;
    
    /**
     * 规格价格(分)
     */
    private Long price;
    
    /**
     * 币种
     */
    private String currency;
    
    /**
     * 币种精度
     */
    private Integer currencyScale;
}