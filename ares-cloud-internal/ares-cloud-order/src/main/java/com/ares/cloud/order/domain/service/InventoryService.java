package com.ares.cloud.order.domain.service;

import com.ares.cloud.order.domain.model.entity.OrderItem;
import com.ares.cloud.order.domain.model.valueobject.ProductInventory;

import java.util.List;

/**
 * 库存服务接口
 */
public interface InventoryService {
    
    /**
     * 检查订单项库存是否充足
     * 
     * @param orderItems 订单项列表
     * @param merchantId 商户ID
     * @return 检查结果，true表示库存充足
     */
    boolean checkStockAvailability(List<OrderItem> orderItems, String merchantId);

    /**
     * 预留订单项库存
     * 
     * @param orderItems 订单项列表
     * @param merchantId 商户ID
     * @return 是否预留成功
     */
    boolean reserveStock(List<OrderItem> orderItems, String merchantId);
    
    /**
     * 预留订单项库存（带订单ID）
     * 
     * @param orderId 订单ID
     * @param orderItems 订单项列表
     * @param merchantId 商户ID
     * @return 是否预留成功
     */
    boolean reserveStock(String orderId, List<OrderItem> orderItems, String merchantId);
    
    /**
     * 释放订单项库存
     * 
     * @param orderItems 订单项列表
     * @param merchantId 商户ID
     * @return 是否释放成功
     */
    boolean releaseStock(List<OrderItem> orderItems, String merchantId);
    
    /**
     * 释放订单项库存（带订单ID）
     * 
     * @param orderId 订单ID
     * @param orderItems 订单项列表
     * @param merchantId 商户ID
     * @return 是否释放成功
     */
    boolean releaseStock(String orderId, List<OrderItem> orderItems, String merchantId);
    
    /**
     * 扣减订单项库存（支付完成后）
     * 
     * @param orderItems 订单项列表
     * @param merchantId 商户ID
     * @return 是否扣减成功
     */
    boolean deductStock(List<OrderItem> orderItems, String merchantId);
    
    /**
     * 扣减订单项库存（支付完成后，带订单ID）
     * 
     * @param orderId 订单ID
     * @param orderItems 订单项列表
     * @param merchantId 商户ID
     * @return 是否扣减成功
     */
    boolean deductStock(String orderId, List<OrderItem> orderItems, String merchantId);
    
    /**
     * 检查单个商品库存
     * 
     * @param productId 商品ID
     * @param productSpecId 商品规格ID（可为null）
     * @param quantity 需要数量
     * @param merchantId 商户ID
     * @return 库存信息
     */
    ProductInventory checkProductStock(String productId, String productSpecId, Integer quantity, String merchantId);
    
    /**
     * 获取商品库存信息
     * 
     * @param productId 商品ID
     * @param productSpecId 商品规格ID（可为null）
     * @param merchantId 商户ID
     * @return 商品库存信息
     */
    ProductInventory getProductInventory(String productId, String productSpecId, String merchantId);

}
