package org.ares.cloud.platformInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 服务和帮助 查询原型
* @version 1.0.0
* @date 2024-10-30
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "服务和帮助查询")
public class PlatformServiceQuery extends Query {

    @Schema(description = "商户id")
    String tenantId;

    @Schema(description = "指定用户标识：1-普通会员；2-骑士；3-商户；4-平台用户", required = true)
    String identity;

    @Schema(description = "类型:1-帮助；2-服务条款")
    String type;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getIdentity() {
        return identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}