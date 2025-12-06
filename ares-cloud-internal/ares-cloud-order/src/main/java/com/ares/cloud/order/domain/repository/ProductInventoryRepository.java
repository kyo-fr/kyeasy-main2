package com.ares.cloud.order.domain.repository;

import com.ares.cloud.order.domain.model.entity.Product;
import com.ares.cloud.order.domain.model.valueobject.ProductInventory;

import java.util.List;
import java.util.Optional;

/**
 * 商品库存仓储接口
 */
public interface ProductInventoryRepository {
    
    /**
     * 根据商品ID查询库存信息
     * 
     * @param productId 商品ID
     * @param merchantId 商户ID
     * @return 商品库存信息
     */
    Optional<ProductInventory> findByProductId(String productId, String merchantId);
    
    /**
     * 根据商品ID和规格ID查询库存信息
     * 
     * @param productId 商品ID
     * @param productSpecId 商品规格ID
     * @param merchantId 商户ID
     * @return 商品库存信息
     */
    Optional<ProductInventory> findByProductIdAndSpecId(String productId, String productSpecId, String merchantId);
    
    /**
     * 批量查询商品库存信息
     * 
     * @param productIds 商品ID列表
     * @param merchantId 商户ID
     * @return 商品库存信息列表
     */
    List<ProductInventory> findByProductIds(List<String> productIds, String merchantId);
    
    /**
     * 保存商品库存信息
     * 
     * @param productInventory 商品库存信息
     */
    void save(ProductInventory productInventory);
    
    /**
     * 批量保存商品库存信息
     * 
     * @param productInventories 商品库存信息列表
     */
    void saveAll(List<ProductInventory> productInventories);

    /**
     * 预留库存（下单时）
     * 
     * @param productId 商品ID
     * @param productSpecId 商品规格ID（可为null）
     * @param quantity 预留数量
     * @param merchantId 商户ID
     * @return 是否预留成功
     */
    boolean reserveStock(String productId, String productSpecId, Integer quantity, String merchantId);
    
    /**
     * 释放预留库存（订单取消时）
     * 
     * @param productId 商品ID
     * @param productSpecId 商品规格ID（可为null）
     * @param quantity 释放数量
     * @param merchantId 商户ID
     * @return 是否释放成功
     */
    boolean releaseReservedStock(String productId, String productSpecId, Integer quantity, String merchantId);
    
    /**
     * 扣减实际库存（支付完成后）
     * 
     * @param productId 商品ID
     * @param productSpecId 商品规格ID（可为null）
     * @param quantity 扣减数量
     * @param merchantId 商户ID
     * @return 是否扣减成功
     */
    boolean deductStock(String productId, String productSpecId, Integer quantity, String merchantId);
    
    /**
     * 批量预留库存
     * 
     * @param productInventories 商品库存信息列表（包含预留数量）
     * @param merchantId 商户ID
     * @return 是否全部预留成功
     */
    boolean reserveStockBatch(List<ProductInventory> productInventories, String merchantId);
    
    /**
     * 批量释放预留库存
     * 
     * @param productInventories 商品库存信息列表（包含释放数量）
     * @param merchantId 商户ID
     * @return 是否全部释放成功
     */
    boolean releaseReservedStockBatch(List<ProductInventory> productInventories, String merchantId);
    
    /**
     * 批量扣减实际库存
     * 
     * @param productInventories 商品库存信息列表（包含扣减数量）
     * @param merchantId 商户ID
     * @return 是否全部扣减成功
     */
    boolean deductStockBatch(List<ProductInventory> productInventories, String merchantId);
    
    /**
     * 根据商品ID获取完整的商品信息（充血模型）
     * 
     * @param productId 商品ID
     * @param merchantId 商户ID
     * @param currency 币种
     * @param currencyScale 币种精度
     * @return 商品领域实体
     */
    Product getProductById(String productId, String merchantId, String currency, Integer currencyScale);
}
