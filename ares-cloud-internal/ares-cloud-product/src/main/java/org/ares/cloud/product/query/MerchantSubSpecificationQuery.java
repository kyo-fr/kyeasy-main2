package org.ares.cloud.product.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;

/**
* @author hugo tangxkwork@163.com
* @description 子规格主数据 查询原型
* @version 1.0.0
* @date 2025-03-18
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "子规格主数据查询")
public class MerchantSubSpecificationQuery extends Query {
    @Schema(description = "主规格id")
    @NotNull(message = "{validation.merchant.productSubSpecification.specificationId.notBlank}")
    String specificationId;



    public String getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(String specificationId) {
        this.specificationId = specificationId;
    }

}