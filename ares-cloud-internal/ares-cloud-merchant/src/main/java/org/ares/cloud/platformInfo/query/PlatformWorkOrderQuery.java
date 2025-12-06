package org.ares.cloud.platformInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 工单 查询原型
* @version 1.0.0
* @date 2024-10-16
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "工单查询")
public class PlatformWorkOrderQuery extends Query {
    @Schema(description = "商户id")
    String tenantId;

    @Schema(description = "用户id")
    String userId;



    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}