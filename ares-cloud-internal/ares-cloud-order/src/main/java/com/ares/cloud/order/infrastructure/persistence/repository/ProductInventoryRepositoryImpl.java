package com.ares.cloud.order.infrastructure.persistence.repository;

import com.ares.cloud.order.domain.enums.OrderError;
import com.ares.cloud.order.domain.model.entity.Product;
import com.ares.cloud.order.domain.model.valueobject.ProductInventory;
import com.ares.cloud.order.domain.repository.ProductInventoryRepository;
import com.ares.cloud.order.infrastructure.persistence.mapper.ProductInventoryMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.api.product.ProductClient;
import org.ares.cloud.api.product.dto.ProductBaseInfoVo;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.utils.IdUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 商品库存仓储实现
 * 
 * 基于MyBatis实现商品库存的数据库操作
 */
@Repository
@Slf4j
@RequiredArgsConstructor
public class ProductInventoryRepositoryImpl implements ProductInventoryRepository {
    
    private final ProductInventoryMapper productInventoryMapper;
    // 商品服务客户端
    private final ProductClient productClient;
    
    @Override
    public Optional<ProductInventory> findByProductId(String productId, String merchantId) {
        try {
            ProductBaseInfoVo productInfo = productClient.getProductInfoByProductId(productId);
            if (productInfo != null) {
                ProductInventory productInventory = convertToProductInventory(productInfo, merchantId);
                return Optional.of(productInventory);
            }
            return Optional.empty();
        } catch (Exception e) {
            log.error("查询商品库存信息失败，商品ID: {}, 商户ID: {}", productId, merchantId, e);
            return Optional.empty();
        }
    }
    
    @Override
    public Optional<ProductInventory> findByProductIdAndSpecId(String productId, String productSpecId, String merchantId) {
        try {
            ProductBaseInfoVo productInfo = productClient.getProductInfoByProductId(productId);
            if (productInfo != null) {
                // 检查规格是否存在
                if (isSpecExistsInProduct(productInfo, productSpecId)) {
                    ProductInventory productInventory = convertToProductInventory(productInfo, merchantId, productSpecId);
                    return Optional.of(productInventory);
                }
            }
            return Optional.empty();
        } catch (Exception e) {
            log.error("查询商品规格库存信息失败，商品ID: {}, 规格ID: {}, 商户ID: {}", productId, productSpecId, merchantId, e);
            return Optional.empty();
        }
    }
    
    @Override
    public List<ProductInventory> findByProductIds(List<String> productIds, String merchantId) {
        try {
            if (productIds == null || productIds.isEmpty()) {
                return new ArrayList<>();
            }
            return productInventoryMapper.selectByProductIds(productIds, merchantId);
        } catch (Exception e) {
            log.error("批量查询商品库存信息失败，商品ID列表: {}, 商户ID: {}", productIds, merchantId, e);
            return new ArrayList<>();
        }
    }
    
    @Override
    public void save(ProductInventory productInventory) {
        // 商品库存信息通常由商品服务管理，这里只记录日志
        log.info("商品库存信息由商品服务管理，当前操作商品ID: {}", productInventory.getProductId());
    }
    
    @Override
    public void saveAll(List<ProductInventory> productInventories) {
        // 商品库存信息通常由商品服务管理，这里只记录日志
        log.info("商品库存信息由商品服务管理，当前操作库存数量: {}", productInventories.size());
    }



    @Override
    public boolean reserveStock(String productId, String productSpecId, Integer quantity, String merchantId) {
        try {
            // 生成雪花ID作为预留记录的主键
            String reservationId = IdUtils.generateSnowflakeId();
            // 生成订单ID用于预留库存记录
            String orderId = generateOrderId();
            Long currentTime = System.currentTimeMillis();
            return productInventoryMapper.reserveStock(reservationId, productId, quantity, merchantId, orderId, currentTime);
        } catch (Exception e) {
            log.error("预留库存失败，商品ID: {}, 规格ID: {}, 数量: {}, 商户ID: {}", productId, productSpecId, quantity, merchantId, e);
            return false;
        }
    }
    
    @Override
    public boolean releaseReservedStock(String productId, String productSpecId, Integer quantity, String merchantId) {
        try {
            // 这里需要传入订单ID，实际使用时应该从上下文获取
            String orderId = generateOrderId();
            Long currentTime = System.currentTimeMillis();
            return productInventoryMapper.releaseReservedStock(productId, quantity, merchantId, orderId, currentTime);
        } catch (Exception e) {
            log.error("释放预留库存失败，商品ID: {}, 规格ID: {}, 数量: {}, 商户ID: {}", productId, productSpecId, quantity, merchantId, e);
            return false;
        }
    }
    
    @Override
    public boolean deductStock(String productId, String productSpecId, Integer quantity, String merchantId) {
        try {
            // 这里需要传入订单ID，实际使用时应该从上下文获取
            String orderId = generateOrderId();
            Long currentTime = System.currentTimeMillis();
            boolean success = productInventoryMapper.deductStock(productId, quantity, merchantId, orderId, currentTime);
            
            // 扣减成功后，检查库存是否为 0 或不足，如果是则通知商品服务下架
            if (success) {
                checkAndNotifyProductStatus(productId, merchantId);
            }
            
            return success;
        } catch (Exception e) {
            log.error("扣减库存失败，商品ID: {}, 规格ID: {}, 数量: {}, 商户ID: {}", productId, productSpecId, quantity, merchantId, e);
            return false;
        }
    }
    
    @Override
    public boolean reserveStockBatch(List<ProductInventory> productInventories, String merchantId) {
        try {
            if (productInventories == null || productInventories.isEmpty()) {
                return true;
            }
            String orderId = generateOrderId();
            Long currentTime = System.currentTimeMillis();
            
            // 为每个商品生成独立的雪花ID并单独插入
            // 这样可以支持同一订单的同一商品有多个item
            for (ProductInventory inventory : productInventories) {
                String reservationId = IdUtils.generateSnowflakeId();
                boolean success = productInventoryMapper.reserveStock(
                    reservationId,
                    inventory.getProductId(), 
                    inventory.getQuantity(), 
                    merchantId, 
                    orderId, 
                    currentTime
                );
                if (!success) {
                    log.error("预留库存失败，商品ID: {}, 数量: {}", inventory.getProductId(), inventory.getQuantity());
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            log.error("批量预留库存失败，库存数量: {}, 商户ID: {}", 
                    productInventories != null ? productInventories.size() : 0, merchantId, e);
            return false;
        }
    }
    
    @Override
    public boolean releaseReservedStockBatch(List<ProductInventory> productInventories, String merchantId) {
        try {
            if (productInventories == null || productInventories.isEmpty()) {
                return true;
            }
            String orderId = generateOrderId();
            Long currentTime = System.currentTimeMillis();
            return productInventoryMapper.releaseReservedStockBatch(productInventories, merchantId, orderId, currentTime);
        } catch (Exception e) {
            log.error("批量释放预留库存失败，库存数量: {}, 商户ID: {}", productInventories.size(), merchantId, e);
            return false;
        }
    }
    
    @Override
    public boolean deductStockBatch(List<ProductInventory> productInventories, String merchantId) {
        try {
            if (productInventories == null || productInventories.isEmpty()) {
                return true;
            }
            String orderId = generateOrderId();
            Long currentTime = System.currentTimeMillis();
            boolean success = productInventoryMapper.deductStockBatch(productInventories, merchantId, orderId, currentTime);
            
            // 批量扣减成功后，检查每个商品的库存状态
            if (success && productInventories != null) {
                for (ProductInventory inventory : productInventories) {
                    try {
                        checkAndNotifyProductStatus(inventory.getProductId(), merchantId);
                    } catch (Exception e) {
                        log.error("检查商品状态失败，商品ID: {}", inventory.getProductId(), e);
                        // 不影响整体流程，只记录日志
                    }
                }
            }
            
            return success;
        } catch (Exception e) {
            log.error("批量扣减库存失败，库存数量: {}, 商户ID: {}", 
                    productInventories != null ? productInventories.size() : 0, merchantId, e);
            return false;
        }
    }
    
    
    /**
     * 将ProductBaseInfoVo转换为ProductInventory
     * 
     * @param productInfo 商品基础信息
     * @param merchantId 商户ID
     * @return ProductInventory对象
     */
    private ProductInventory convertToProductInventory(ProductBaseInfoVo productInfo, String merchantId) {
        return convertToProductInventory(productInfo, merchantId, null);
    }
    
    /**
     * 将ProductBaseInfoVo转换为ProductInventory（带规格）
     * 
     * @param productInfo 商品基础信息
     * @param merchantId 商户ID
     * @param productSpecId 商品规格ID
     * @return ProductInventory对象
     */
    private ProductInventory convertToProductInventory(ProductBaseInfoVo productInfo, String merchantId, String productSpecId) {
        return ProductInventory.builder()
                .productId(productInfo.getId())
                .productName(productInfo.getName())
                .price(productInfo.getPrice())
                .currentStock(productInfo.getInventory())
                .reservedStock(0) // 预留库存初始为0
                .availableStock(productInfo.getInventory()) // 可用库存等于当前库存
                .merchantId(merchantId)
                .productSpecId(productSpecId)
                .enabled("enable".equals(productInfo.getIsEnable()))
                .productType(productInfo.getType())
                .version(1)
                .quantity(0)
                .build();
    }
    
    /**
     * 检查规格是否存在于商品中
     * 
     * @param productInfo 商品信息
     * @param productSpecId 规格ID
     * @return 规格是否存在
     */
    private boolean isSpecExistsInProduct(ProductBaseInfoVo productInfo, String productSpecId) {
        if (productSpecId == null || productInfo == null) {
            return true; // 如果没有规格ID，认为存在
        }
        
        // 检查主规格
        if (productInfo.getProductSpecificationList() != null) {
            boolean existsInMainSpec = productInfo.getProductSpecificationList().stream()
                    .anyMatch(spec -> productSpecId.equals(spec.getSpecificationId()));
            if (existsInMainSpec) {
                return true;
            }
        }
        
        // 检查子规格
        if (productInfo.getProductSubSpecificationList() != null) {
            return productInfo.getProductSubSpecificationList().stream()
                    .anyMatch(spec -> productSpecId.equals(spec.getSubSpecificationId()));
        }
        
        return false;
    }
    
    /**
     * 生成订单ID（临时方法，实际使用时应该从上下文获取）
     * 
     * @return 订单ID
     */
    private String generateOrderId() {
        return "ORDER_" + System.currentTimeMillis();
    }
    
    /**
     * 通知商品服务更新商品状态
     * 库存扣减后直接通知商品服务，由商品服务自行判断是否需要下架
     * 
     * @param productId 商品ID
     * @param merchantId 商户ID
     */
    private void checkAndNotifyProductStatus(String productId, String merchantId) {
        try {
            log.info("库存扣减后通知商品服务 - 商品ID: {}, 商户ID: {}", productId, merchantId);
            
            // 直接调用商品服务接口，由商品服务自行判断是否需要下架
            productClient.updateProductIsEnable(productId);
            
            log.info("✅ 成功通知商品服务 - 商品ID: {}", productId);
            
        } catch (Exception e) {
            log.error("❌ 通知商品服务失败 - 商品ID: {}, 商户ID: {}, 错误信息: {}", 
                    productId, merchantId, e.getMessage(), e);
            // 这里不抛出异常，避免影响库存扣减的主流程
        }
    }
    
    @Override
    public Product getProductById(String productId, String merchantId, String currency, Integer currencyScale) {
        ProductBaseInfoVo productInfo = productClient.getProductInfoByProductId(productId);
        if (productInfo == null) {
            throw new BusinessException(OrderError.PRODUCT_NOT_FOUND);
        }

        return Product.fromProductBaseInfo(productInfo, merchantId, currency, currencyScale);
    }
}
