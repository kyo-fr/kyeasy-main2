package org.ares.cloud.domain.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 订单商品项
 * 
 * @author hugo
 * @version 1.0.0
 * @date 2024/03/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "订单商品项")
public class OrderItem {
    /**
     * 商品ID
     */
    private String productId;
    
    /**
     * 商品名称
     */
    private String productName;
    
    /**
     * 商品数量
     */
    private Integer quantity;
    
    /**
     * 商品单价
     */
    private Money unitPrice;
    
    /**
     * 商品总价
     */
    private Money totalPrice;
    
    /**
     * 服务费
     */
    private Money serviceFee;
} 