package org.ares.cloud.product.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 商品标注 查询原型
* @version 1.0.0
* @date 2024-11-08
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "商品标注查询")
public class ProductMarkingQuery extends Query {
    @Schema(description = "商品id")
    private String productId;

    public String getProductId() {
        return productId;
    }


    public void setProductId(String productId) {
        this.productId = productId;
    }
}