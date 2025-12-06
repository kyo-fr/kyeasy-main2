package org.ares.cloud.merchantInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 商户侧栏 查询原型
* @version 1.0.0
* @date 2024-10-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "商户侧栏查询")
public class MerchantSideBarQuery extends Query {
    @Schema(description = "商户id")
//    @NotNull(message = "{validation.tenantId.notBlank}")
    String tenantId;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}