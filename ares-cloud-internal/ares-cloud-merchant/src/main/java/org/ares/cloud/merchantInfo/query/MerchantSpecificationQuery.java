package org.ares.cloud.merchantInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 主规格主数据 查询原型
* @version 1.0.0
* @date 2025-03-18
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "主规格主数据查询")
public class MerchantSpecificationQuery extends Query {
    @Schema(description = "商户id")
    @NotNull(message = "{validation.tenantId.notBlank}")
    String tenantId;



    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

//    @Schema(description = "商户id")
//    @NotNull(message = "{validation.tenantId.notBlank}")
//    String tenantId;
//
//
//    public String getTenantId() {
//        return tenantId;
//    }
//
//    public void setTenantId(String tenantId) {

//    }
}