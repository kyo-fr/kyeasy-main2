package com.ares.cloud.order.domain.service.impl;

import com.ares.cloud.order.domain.model.entity.OrderItem;
import com.ares.cloud.order.domain.model.entity.Product;
import com.ares.cloud.order.domain.model.valueobject.ProductInfo;
import com.ares.cloud.order.domain.model.valueobject.ProductInventory;
import com.ares.cloud.order.domain.model.valueobject.ProductSpecification;
import com.ares.cloud.order.domain.repository.ProductInventoryRepository;
import com.ares.cloud.order.domain.service.ProductService;
import org.ares.cloud.common.exception.RequestBadException;
import com.ares.cloud.order.domain.enums.OrderError;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.model.Money;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品服务实现
 * 
 * 注意：这是一个示例实现，实际项目中需要调用商品服务的API
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    
    private final ProductInventoryRepository productInventoryRepository;
    
    @Override
    public ProductInfo getProductById(String productId, String merchantId) {
        try {
            ProductInventory inventory = productInventoryRepository.findByProductId(productId, merchantId)
                    .orElse(null);
            
            if (inventory == null) {
                return null;
            }
            
            return ProductInfo.builder()
                    .productId(inventory.getProductId())
                    .productName(inventory.getProductName())
                    .isEnable(inventory.getEnabled() ? "1" : "2")
                    .merchantId(inventory.getMerchantId())
                    .inventory(inventory.getCurrentStock())
                    .build();
        } catch (Exception e) {
            log.error("获取商品信息失败，商品ID: {}, 商户ID: {}", productId, merchantId, e);
            return null;
        }
    }



    @Override
    public void validateOrderItems(List<OrderItem> orderItems, String merchantId, String currency, Integer currencyScale) {
        if (orderItems == null || orderItems.isEmpty()) {
            return;
        }

        for (OrderItem orderItem : orderItems) {
            // 跳过服务费和配送费
            if ("service_fee".equals(orderItem.getProductId()) ||
                    "delivery_fee".equals(orderItem.getProductId())) {
                continue;
            }
            
            // 获取商品信息
            Product product = validateAndGetProduct(orderItem.getProductId(), merchantId, currency, currencyScale, orderItem.getUnitPrice());

            // 根据商品价格调整OrderItem的价格
            adjustOrderItemPrices(orderItem, product);
            
            // 验证规格
            if (orderItem.getSpecifications() != null && !orderItem.getSpecifications().isEmpty()) {
                List<ProductSpecification> updatedSpecs = new ArrayList<>();
                for (ProductSpecification spec : orderItem.getSpecifications()) {
                    // 验证规格可用性
                    product.validateSpecAvailability(spec.getProductSpecId());
                    
                    // 获取规格的有效价格（考虑优惠）
                    Money specEffectivePrice = product.getSpecPrice(spec.getProductSpecId());
                    
                    // 创建新的规格对象（因为ProductSpecification是不可变的）
                    ProductSpecification updatedSpec = ProductSpecification.builder()
                            .id(spec.getId())
                            .productSpecId(spec.getProductSpecId())
                            .name(spec.getName())
                            .value(spec.getValue())
                            .price(specEffectivePrice)
                            .quantity(spec.getQuantity())
                            .build();
                    
                    updatedSpecs.add(updatedSpec);
                    
                    // 验证规格价格
                    product.validateSpecPrice(spec.getProductSpecId(), specEffectivePrice);
                }
                
                // 更新订单项的规格列表
                orderItem.setSpecifications(updatedSpecs);
            }
        }
    }
    
    /**
     * 根据商品价格调整OrderItem的价格
     *
     * @param orderItem 订单项
     * @param product   商品信息
     */
    private void adjustOrderItemPrices(OrderItem orderItem, Product product) {
        // 获取商品的有效价格（优先优惠价格）
        Money effectivePrice = product.getEffectivePrice();
        
        // 设置OrderItem的原价
        orderItem.setUnitPrice(product.getPrice());
        
        // 如果有优惠价格，设置优惠价格
        if (product.hasDiscountPrice()) {
            orderItem.setDiscountedPrice(product.getDiscountPrice());
        } else {
            // 没有优惠价格时，优惠价格设为null或原价
            orderItem.setDiscountedPrice(null);
        }
        
        // 验证价格是否匹配
        product.validatePrice(effectivePrice);
    }
    
    /**
     * 验证商品信息
     *
     * @param productId      商品ID
     * @param merchantId     商户ID
     * @param currency       货币代码
     * @param currencyScale  货币精度
     * @param expectedPrice  期望价格
     * @return 商品信息
     */
    private Product validateAndGetProduct(String productId, String merchantId, String currency, Integer currencyScale, Money expectedPrice) {
        Product product = productInventoryRepository.getProductById(productId, merchantId, currency, currencyScale);
        if (product == null) {
            throw new RequestBadException(OrderError.PRODUCT_NOT_FOUND);
        }
        
        // 检查商品是否上架
        if (!product.isAvailable()) {
            throw new RequestBadException(OrderError.PRODUCT_NOT_ENABLED);
        }
        
        // 检查商品价格
        product.validatePrice(expectedPrice);
        
        return product;
    }
    
}
