package org.ares.cloud.product.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 商品分类管理 查询原型
* @version 1.0.0
* @date 2024-10-28
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "商品分类管理查询")
public class ProductTypeQuery extends Query {

    @Schema(description = "商户id")
    @NotNull(message = "{validation.tenantId.notBlank}")
    String tenantId;


    @Schema(description = "父类id")
    String parentId;

    @Schema(description = "分类级别id")
    Integer level;



    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }


    public String getParentId() {
        return parentId;
    }


    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}