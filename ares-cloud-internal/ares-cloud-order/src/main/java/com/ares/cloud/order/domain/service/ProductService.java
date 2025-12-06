package com.ares.cloud.order.domain.service;

import com.ares.cloud.order.domain.model.entity.OrderItem;
import com.ares.cloud.order.domain.model.valueobject.ProductInfo;

import java.util.List;

/**
 * 商品服务接口
 * 用于在订单服务中调用商品相关的业务逻辑
 */
public interface ProductService {
    
    /**
     * 根据商品ID获取商品信息
     * 
     * @param productId 商品ID
     * @param merchantId 商户ID
     * @return 商品信息
     */
    ProductInfo getProductById(String productId, String merchantId);


    /**
     * 验证订单项的商品信息（包括价格和规格）
     */
    void validateOrderItems(List<OrderItem> orderItems, String merchantId, String currency, Integer currencyScale);
}
