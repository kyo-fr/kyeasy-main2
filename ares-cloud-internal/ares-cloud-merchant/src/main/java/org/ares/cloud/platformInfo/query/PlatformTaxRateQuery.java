package org.ares.cloud.platformInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 税率 查询原型
* @version 1.0.0
* @date 2024-10-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "税率查询")
public class PlatformTaxRateQuery extends Query {

    @Schema(description = "商户id")
    String tenantId;


    @Schema(description = "税率类型:1-商品税率(商户)；2-服务税率(商户)；3-运送税率(商户)；4-订阅税率(平台)；5-礼物点税率(平台)")
    String type;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}