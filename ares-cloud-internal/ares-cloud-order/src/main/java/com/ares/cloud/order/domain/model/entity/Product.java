package com.ares.cloud.order.domain.model.entity;

import com.ares.cloud.order.domain.enums.OrderError;
import lombok.Builder;
import lombok.Getter;
import org.ares.cloud.api.product.dto.ProductBaseInfoVo;
import org.ares.cloud.api.product.dto.ProductSpecificationDto;
import org.ares.cloud.api.product.dto.ProductSubSpecificationVo;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.common.model.Money;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 商品领域实体（充血模型）
 */
@Getter
@Builder
public class Product {
    
    /**
     * 商品ID
     */
    private final String productId;
    
    /**
     * 商品名称
     */
    private final String productName;
    
    /**
     * 商品价格
     */
    private final Money price;
    
    /**
     * 优惠价格（可选）
     */
    private final Money discountPrice;
    
    /**
     * 库存数量
     */
    private final Integer inventory;
    
    /**
     * 是否上架（enable-上架，not_enable-下架）
     */
    private final String isEnable;
    
    /**
     * 商户ID
     */
    private final String merchantId;
    
    /**
     * 商品类型
     */
    private final String type;
    
    /**
     * 商品图片URL
     */
    private final String pictureUrl;
    
    /**
     * 商品简介
     */
    private final String briefly;
    
    /**
     * 主规格列表
     */
    private final List<ProductSpecificationDto> mainSpecifications;
    
    /**
     * 子规格列表
     */
    private final List<ProductSubSpecificationVo> subSpecifications;
    
    /**
     * 从ProductBaseInfoVo创建Product实体
     */
    public static Product fromProductBaseInfo(ProductBaseInfoVo productInfo, String merchantId, String currency, Integer currencyScale) {
        if (productInfo == null || productInfo.getId() == null) {
            throw new RequestBadException(OrderError.PRODUCT_NOT_FOUND);
        }
        
        Money price = Money.of(productInfo.getPrice(), currency, currencyScale);
        Money discountPrice = null;
        if (productInfo.getPreferential() != null && productInfo.getPreferential().getPreferentialPrice() != null) {
            discountPrice = Money.of(productInfo.getPreferential().getPreferentialPrice(), currency, currencyScale);
        }
        
        return Product.builder()
                .productId(productInfo.getId())
                .productName(productInfo.getName())
                .price(price)
                .discountPrice(discountPrice)
                .inventory(productInfo.getInventory())
                .isEnable(productInfo.getIsEnable())
                .merchantId(merchantId)
                .type(productInfo.getType())
                .pictureUrl(productInfo.getPictureUrl())
                .briefly(productInfo.getBriefly())
                .mainSpecifications(productInfo.getProductSpecificationList())
                .subSpecifications(productInfo.getProductSubSpecificationList())
                .build();
    }
    
    /**
     * 验证商品是否可用（存在且上架）
     */
    public void validateAvailability() {
        if (productId == null || productName == null) {
            throw new RequestBadException(OrderError.PRODUCT_NOT_FOUND);
        }
        
        if (!"enable".equals(isEnable)) {
            throw new RequestBadException(OrderError.PRODUCT_NOT_ENABLED);
        }
    }
    
    /**
     * 验证商品库存是否充足
     */
    public void validateStock(Integer requiredQuantity) {
        if (inventory == null || inventory < requiredQuantity) {
            throw new RequestBadException(OrderError.INSUFFICIENT_STOCK);
        }
    }
    
    /**
     * 获取有效价格（优先使用优惠价格）
     */
    public Money getEffectivePrice() {
        return discountPrice != null ? discountPrice : price;
    }
    
    /**
     * 验证商品价格是否匹配
     */
    public void validatePrice(Money expectedPrice) {
        if (expectedPrice == null) {
            throw new RequestBadException(OrderError.PRODUCT_PRICE_MISMATCH);
        }
        
        Money effectivePrice = getEffectivePrice();
        if (!effectivePrice.equals(expectedPrice)) {
            throw new RequestBadException(OrderError.PRODUCT_PRICE_MISMATCH);
        }
    }
    
    /**
     * 检查规格是否存在
     */
    public boolean isSpecExists(String productSpecId) {
        if (productSpecId == null) {
            return false;
        }
        
        // 检查主规格
        if (mainSpecifications != null) {
            boolean existsInMain = mainSpecifications.stream()
                    .anyMatch(spec -> productSpecId.equals(spec.getSpecificationId()));
            if (existsInMain) {
                return true;
            }
        }
        
        // 检查子规格
        if (subSpecifications != null) {
            return subSpecifications.stream()
                    .anyMatch(spec -> productSpecId.equals(spec.getSubSpecificationId()));
        }
        
        return false;
    }
    
    /**
     * 检查规格是否可用（存在且启用）
     */
    public boolean isSpecAvailable(String productSpecId) {
        if (!isSpecExists(productSpecId)) {
            return false;
        }
        
        // 检查主规格是否启用（主规格没有启用状态字段，默认启用）
        if (mainSpecifications != null) {
            Optional<ProductSpecificationDto> mainSpec = mainSpecifications.stream()
                    .filter(spec -> productSpecId.equals(spec.getSpecificationId()))
                    .findFirst();
            if (mainSpec.isPresent()) {
                return true; // 主规格默认启用
            }
        }
        
        // 检查子规格是否启用（子规格没有启用状态字段，默认启用）
        if (subSpecifications != null) {
            Optional<ProductSubSpecificationVo> subSpec = subSpecifications.stream()
                    .filter(spec -> productSpecId.equals(spec.getSubSpecificationId()))
                    .findFirst();
            if (subSpec.isPresent()) {
                return true; // 子规格默认启用
            }
        }
        
        return false;
    }
    
    /**
     * 验证规格可用性
     */
    public void validateSpecAvailability(String productSpecId) {
        if (!isSpecExists(productSpecId)) {
            throw new RequestBadException(OrderError.PRODUCT_SPEC_NOT_FOUND);
        }
        
        if (!isSpecAvailable(productSpecId)) {
            throw new RequestBadException(OrderError.PRODUCT_SPEC_NOT_ENABLED);
        }
    }
    
    /**
     * 获取规格价格
     */
    public Money getSpecPrice(String productSpecId) {
        if (productSpecId == null) {
            return getEffectivePrice(); // 如果没有规格，返回商品有效价格（优先优惠价格）
        }
        
        // 检查主规格价格（主规格没有价格字段，使用商品有效价格）
        if (mainSpecifications != null) {
            Optional<ProductSpecificationDto> mainSpec = mainSpecifications.stream()
                    .filter(spec -> productSpecId.equals(spec.getSpecificationId()))
                    .findFirst();
            if (mainSpec.isPresent()) {
                return getEffectivePrice(); // 主规格使用商品有效价格
            }
        }
        
        // 检查子规格价格
        if (subSpecifications != null) {
            Optional<ProductSubSpecificationVo> subSpec = subSpecifications.stream()
                    .filter(spec -> productSpecId.equals(spec.getSubSpecificationId()))
                    .findFirst();
            if (subSpec.isPresent()) {
                BigDecimal specPrice = subSpec.get().getSubPrice();
                if (specPrice != null) {
                    // 子规格价格也需要考虑优惠价格
                    Money specMoney = Money.of(specPrice, price.getCurrency(), price.getScale());
                    // 如果有优惠价格，子规格价格也需要应用优惠
                    if (discountPrice != null) {
                        // 计算优惠比例
                        BigDecimal discountRatio = discountPrice.toDecimal().divide(price.toDecimal(), 4, java.math.RoundingMode.HALF_UP);
                        BigDecimal discountedSpecPrice = specPrice.multiply(discountRatio);
                        return Money.of(discountedSpecPrice, price.getCurrency(), price.getScale());
                    }
                    return specMoney;
                }
            }
        }
        
        return getEffectivePrice(); // 如果规格没有价格，返回商品有效价格
    }
    
    /**
     * 验证规格价格是否匹配
     */
    public void validateSpecPrice(String productSpecId, Money expectedPrice) {
        Money actualPrice = getSpecPrice(productSpecId);
        if (!actualPrice.equals(expectedPrice)) {
            throw new RequestBadException(OrderError.PRODUCT_SPEC_PRICE_MISMATCH);
        }
    }
    
    /**
     * 检查商品是否有库存
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
    
    /**
     * 是否可用（存在且上架）
     */
    public boolean isAvailable() {
        return productId != null && 
               productName != null && 
               "enable".equals(isEnable);
    }
    
    /**
     * 是否有优惠价格
     */
    public boolean hasDiscountPrice() {
        return discountPrice != null;
    }
    
    /**
     * 获取优惠金额
     */
    public Money getDiscountAmount() {
        if (discountPrice == null) {
            return Money.of(BigDecimal.ZERO, price.getCurrency(), price.getScale());
        }
        return price.subtract(discountPrice);
    }
    
    /**
     * 获取优惠比例（0-1之间的小数）
     */
    public BigDecimal getDiscountRatio() {
        if (discountPrice == null) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.ONE.subtract(discountPrice.toDecimal().divide(price.toDecimal(), 4, java.math.RoundingMode.HALF_UP));
    }
}
