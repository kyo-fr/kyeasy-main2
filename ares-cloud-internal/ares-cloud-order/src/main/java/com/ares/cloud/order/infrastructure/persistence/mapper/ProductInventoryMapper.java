package com.ares.cloud.order.infrastructure.persistence.mapper;

import com.ares.cloud.order.domain.model.valueobject.ProductInventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品库存Mapper接口
 * 
 * @author ares-cloud
 */
@Mapper
public interface ProductInventoryMapper {
    
    /**
     * 根据商品ID查询商品基础信息
     * 
     * @param productId 商品ID
     * @param tenantId 租户ID
     * @return 商品库存信息
     */
    ProductInventory selectByProductId(@Param("productId") String productId, @Param("tenantId") String tenantId);
    
    /**
     * 批量查询商品基础信息
     * 
     * @param productIds 商品ID列表
     * @param tenantId 租户ID
     * @return 商品库存信息列表
     */
    List<ProductInventory> selectByProductIds(@Param("productIds") List<String> productIds, @Param("tenantId") String tenantId);
    
    /**
     * 检查商品是否存在
     * 
     * @param productId 商品ID
     * @param tenantId 租户ID
     * @return 是否存在
     */
    boolean existsByProductId(@Param("productId") String productId, @Param("tenantId") String tenantId);
    
    /**
     * 检查商品是否启用
     * 
     * @param productId 商品ID
     * @param tenantId 租户ID
     * @return 是否启用
     */
    boolean isEnabledByProductId(@Param("productId") String productId, @Param("tenantId") String tenantId);
    
    /**
     * 预留库存
     * 
     * @param id 预留记录ID（雪花ID）
     * @param productId 商品ID
     * @param quantity 预留数量
     * @param tenantId 租户ID
     * @param orderId 订单ID
     * @param createTime 创建时间（毫秒时间戳）
     * @return 是否成功
     */
    boolean reserveStock(@Param("id") String id,
                        @Param("productId") String productId, 
                        @Param("quantity") Integer quantity, 
                        @Param("tenantId") String tenantId,
                        @Param("orderId") String orderId,
                        @Param("createTime") Long createTime);
    
    /**
     * 释放预留库存
     * 
     * @param productId 商品ID
     * @param quantity 释放数量
     * @param tenantId 租户ID
     * @param orderId 订单ID
     * @param updateTime 更新时间（毫秒时间戳）
     * @return 是否成功
     */
    boolean releaseReservedStock(@Param("productId") String productId, 
                                @Param("quantity") Integer quantity, 
                                @Param("tenantId") String tenantId,
                                @Param("orderId") String orderId,
                                @Param("updateTime") Long updateTime);
    
    /**
     * 扣减实际库存
     * 
     * @param productId 商品ID
     * @param quantity 扣减数量
     * @param tenantId 租户ID
     * @param orderId 订单ID
     * @param updateTime 更新时间（毫秒时间戳）
     * @return 是否成功
     */
    boolean deductStock(@Param("productId") String productId, 
                       @Param("quantity") Integer quantity, 
                       @Param("tenantId") String tenantId,
                       @Param("orderId") String orderId,
                       @Param("updateTime") Long updateTime);
    
    /**
     * 批量预留库存
     * 
     * @param productInventories 商品库存列表
     * @param tenantId 租户ID
     * @param orderId 订单ID
     * @param createTime 创建时间（毫秒时间戳）
     * @return 是否成功
     */
    boolean reserveStockBatch(@Param("productInventories") List<ProductInventory> productInventories, 
                             @Param("tenantId") String tenantId,
                             @Param("orderId") String orderId,
                             @Param("createTime") Long createTime);
    
    /**
     * 批量释放预留库存
     * 
     * @param productInventories 商品库存列表
     * @param tenantId 租户ID
     * @param orderId 订单ID
     * @param updateTime 更新时间（毫秒时间戳）
     * @return 是否成功
     */
    boolean releaseReservedStockBatch(@Param("productInventories") List<ProductInventory> productInventories, 
                                     @Param("tenantId") String tenantId,
                                     @Param("orderId") String orderId,
                                     @Param("updateTime") Long updateTime);
    
    /**
     * 批量扣减实际库存
     * 
     * @param productInventories 商品库存列表
     * @param tenantId 租户ID
     * @param orderId 订单ID
     * @param updateTime 更新时间（毫秒时间戳）
     * @return 是否成功
     */
    boolean deductStockBatch(@Param("productInventories") List<ProductInventory> productInventories, 
                            @Param("tenantId") String tenantId,
                            @Param("orderId") String orderId,
                            @Param("updateTime") Long updateTime);
    
    /**
     * 根据商品ID和规格ID查询商品库存信息
     * 
     * @param productId 商品ID
     * @param productSpecId 商品规格ID
     * @param tenantId 租户ID
     * @return 商品库存信息
     */
    ProductInventory selectByProductIdAndSpecId(@Param("productId") String productId, 
                                               @Param("productSpecId") String productSpecId, 
                                               @Param("tenantId") String tenantId);
    
    /**
     * 检查商品规格是否存在
     * 
     * @param productId 商品ID
     * @param productSpecId 商品规格ID
     * @param tenantId 租户ID
     * @return 是否存在
     */
    boolean existsSpecByProductIdAndSpecId(@Param("productId") String productId, 
                                          @Param("productSpecId") String productSpecId, 
                                          @Param("tenantId") String tenantId);
    
    /**
     * 检查商品规格是否启用
     * 
     * @param productId 商品ID
     * @param productSpecId 商品规格ID
     * @param tenantId 租户ID
     * @return 是否启用
     */
    boolean isSpecEnabledByProductIdAndSpecId(@Param("productId") String productId, 
                                             @Param("productSpecId") String productSpecId, 
                                             @Param("tenantId") String tenantId);
}
