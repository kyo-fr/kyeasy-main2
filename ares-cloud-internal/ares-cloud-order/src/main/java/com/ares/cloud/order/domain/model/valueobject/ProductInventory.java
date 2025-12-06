package com.ares.cloud.order.domain.model.valueobject;

import lombok.Builder;
import lombok.Value;
import org.ares.cloud.common.exception.RequestBadException;
import com.ares.cloud.order.domain.enums.OrderError;

/**
 * 商品库存值对象
 */
@Value
@Builder
public class ProductInventory {
    
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
    java.math.BigDecimal price;
    
    /**
     * 当前库存数量
     */
    Integer currentStock;
    
    /**
     * 预留库存数量（已下单但未支付）
     */
    @Builder.Default
    Integer reservedStock = 0;
    
    /**
     * 可用库存数量
     */
    @Builder.Default
    Integer availableStock = 0;
    
    /**
     * 商户ID
     */
    String merchantId;
    
    /**
     * 商品规格ID（如果有规格）
     */
    String productSpecId;
    
    /**
     * 是否启用
     */
    @Builder.Default
    Boolean enabled = true;
    
    /**
     * 商品类型
     */
    String productType;
    
    /**
     * 版本号
     */
    @Builder.Default
    Integer version = 1;
    
    /**
     * 操作数量（用于库存操作）
     */
    @Builder.Default
    Integer quantity = 0;
    
    /**
     * 构造函数，计算可用库存
     */
    public ProductInventory(String productId, String productName, java.math.BigDecimal price, Integer currentStock, 
                           Integer reservedStock, Integer availableStock, String merchantId, String productSpecId, 
                           Boolean enabled, String productType, Integer version, Integer quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.currentStock = currentStock != null ? currentStock : 0;
        this.reservedStock = reservedStock != null ? reservedStock : 0;
        this.merchantId = merchantId;
        this.productSpecId = productSpecId;
        this.enabled = enabled != null ? enabled : true;
        this.productType = productType;
        this.version = version != null ? version : 1;
        this.quantity = quantity != null ? quantity : 0;
        this.availableStock = availableStock != null ? availableStock : Math.max(0, this.currentStock - this.reservedStock);
    }
    
    /**
     * 检查商品是否存在且可用
     */
    public void validateProductExists() {
        if (productId == null || productName == null) {
            throw new RequestBadException(OrderError.PRODUCT_NOT_FOUND);
        }
        if (!enabled) {
            throw new RequestBadException(OrderError.PRODUCT_DISABLED);
        }
    }
    
    /**
     * 检查库存是否充足
     * 
     * @param requiredQuantity 需要的数量
     */
    public void validateStockSufficient(Integer requiredQuantity) {
        if (requiredQuantity == null || requiredQuantity <= 0) {
            throw new RequestBadException(OrderError.INVALID_QUANTITY);
        }
        
        if (availableStock < requiredQuantity) {
            throw new RequestBadException(OrderError.INSUFFICIENT_STOCK);
        }
    }
    
    /**
     * 预留库存
     * 
     * @param quantity 预留数量
     * @return 新的库存对象
     */
    public ProductInventory reserveStock(Integer quantity) {
        validateStockSufficient(quantity);
        
        return ProductInventory.builder()
                .productId(this.productId)
                .productName(this.productName)
                .price(this.price)
                .currentStock(this.currentStock)
                .reservedStock(this.reservedStock + quantity)
                .merchantId(this.merchantId)
                .productSpecId(this.productSpecId)
                .enabled(this.enabled)
                .productType(this.productType)
                .version(this.version)
                .quantity(quantity)
                .build();
    }
    
    /**
     * 释放预留库存
     * 
     * @param quantity 释放数量
     * @return 新的库存对象
     */
    public ProductInventory releaseReservedStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new RequestBadException(OrderError.INVALID_QUANTITY);
        }
        
        int newReservedStock = Math.max(0, this.reservedStock - quantity);
        
        return ProductInventory.builder()
                .productId(this.productId)
                .productName(this.productName)
                .price(this.price)
                .currentStock(this.currentStock)
                .reservedStock(newReservedStock)
                .merchantId(this.merchantId)
                .productSpecId(this.productSpecId)
                .enabled(this.enabled)
                .productType(this.productType)
                .version(this.version)
                .quantity(quantity)
                .build();
    }
    
    /**
     * 扣减实际库存（支付完成后）
     * 
     * @param quantity 扣减数量
     * @return 新的库存对象
     */
    public ProductInventory deductStock(Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new RequestBadException(OrderError.INVALID_QUANTITY);
        }
        
        if (this.currentStock < quantity) {
            throw new RequestBadException(OrderError.INSUFFICIENT_STOCK);
        }
        
        int newReservedStock = Math.max(0, this.reservedStock - quantity);
        int newCurrentStock = this.currentStock - quantity;
        
        return ProductInventory.builder()
                .productId(this.productId)
                .productName(this.productName)
                .price(this.price)
                .currentStock(newCurrentStock)
                .reservedStock(newReservedStock)
                .merchantId(this.merchantId)
                .productSpecId(this.productSpecId)
                .enabled(this.enabled)
                .productType(this.productType)
                .version(this.version)
                .quantity(quantity)
                .build();
    }
    
    /**
     * 检查是否有足够的可用库存
     * 
     * @param quantity 需要的数量
     * @return 是否有足够库存
     */
    public boolean hasEnoughStock(Integer quantity) {
        return quantity != null && quantity > 0 && availableStock >= quantity;
    }
    
    /**
     * 获取可用库存数量
     * 
     * @return 可用库存数量
     */
    public Integer getAvailableStock() {
        return Math.max(0, currentStock - reservedStock);
    }
}
