package com.ares.cloud.order.infrastructure.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;

import java.util.List;

@Data
@TableName("order_items")
public class OrderItemEntity {
    /**
     * 订单项ID
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 商品ID
     */
    private String productId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品单价(分)
     */
    private Long unitPrice;
    
    /**
     * 优惠价格(分)
     */
    private Long discountedPrice;

    /**
     * 数量
     */
    private Integer quantity;

    /**
     * 总价(分)
     */
    private Long totalPrice;

    /**
     * 币种
     */
    private String currency;

    /**
     * 币种精度
     */
    private Integer currencyScale;
    /**
     * 支付状态
     */
    private Integer paymentStatus;
    
    /**
     * 软删除标记 0-未删除 1-已删除
     */
    @TableLogic
    private Integer deleted = 0;
    
    /**
     * 商品规格列表
     * 非数据库字段，用于传输数据
     */
    @TableField(exist = false)
    private List<ProductSpecificationEntity> specifications;
}