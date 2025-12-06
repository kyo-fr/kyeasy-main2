package com.ares.cloud.order.domain.model.valueobject;

import lombok.Builder;
import lombok.Value;
import org.ares.cloud.common.model.Money;

/**
 * 商品规格值对象
 */
@Value
@Builder
public class ProductSpecification {
    /**
     * 规格ID
     */
    String id;
    /**
     * 商品规格ID（来自商品服务）
     */
    String productSpecId;
    /**
     * 规格名称
     */
    String name;
    
    /**
     * 规格值
     */
    String value;
    
    /**
     * 规格价格
     */
    Money price;
    
    /**
     * 规格数量
     */
    @Builder.Default
    Integer quantity = 1;
}