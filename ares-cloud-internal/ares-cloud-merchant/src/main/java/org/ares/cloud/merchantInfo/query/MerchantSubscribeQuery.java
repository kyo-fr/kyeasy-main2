package org.ares.cloud.merchantInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 商户订阅 查询原型
* @version 1.0.0
* @date 2024-10-11
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "商户订阅查询")
public class MerchantSubscribeQuery extends Query {

    @Schema(description = "商户id")
    String tenantId;



    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}