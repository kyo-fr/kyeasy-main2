package org.ares.cloud.product.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 商品子规格 查询原型
* @version 1.0.0
* @date 2025-03-24
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "商品子规格查询")
public class ProductSubSpecificationQuery extends Query {
    @Schema(description = "商品id")
    private String productId;

    @Schema(description = "商品主规格id")
    private String productSpecificationId;

    public String getProductId() {
        return productId;
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }


    public String getProductSpecificationId() {
        return productSpecificationId;
    }

    public void setProductSpecificationId(String productSpecificationId) {
        this.productSpecificationId = productSpecificationId;
    }
}