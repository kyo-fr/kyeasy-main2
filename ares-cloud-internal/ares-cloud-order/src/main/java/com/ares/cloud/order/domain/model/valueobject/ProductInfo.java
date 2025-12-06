package com.ares.cloud.order.domain.model.valueobject;

import lombok.Builder;
import lombok.Value;
import org.ares.cloud.common.model.Money;

/**
 * 商品信息值对象
 */
@Value
@Builder
public class ProductInfo {
    
    /**
     * 商品ID
     */
    String productId;
    
    /**
     * 商品名称
     */
    String productName;
    
    /**
     * 商品价格
     */
    Money price;
    
    /**
     * 库存数量
     */
    Integer inventory;
    
    /**
     * 是否上架（1-上架，2-下架）
     */
    String isEnable;
    
    /**
     * 商户ID
     */
    String merchantId;
    
    /**
     * 商品类型
     */
    String type;
    
    /**
     * 商品图片URL
     */
    String pictureUrl;
    
    /**
     * 商品简介
     */
    String briefly;
    
    /**
     * 是否可用（存在且上架）
     */
    public boolean isAvailable() {
        return productId != null && 
               productName != null && 
               "enable".equals(isEnable);
    }
    
    /**
     * 是否有库存
     */
    public boolean hasStock() {
        return inventory != null && inventory > 0;
    }
    
    /**
     * 检查库存是否充足
     */
    public boolean hasEnoughStock(Integer requiredQuantity) {
        return hasStock() && inventory >= requiredQuantity;
    }
}
