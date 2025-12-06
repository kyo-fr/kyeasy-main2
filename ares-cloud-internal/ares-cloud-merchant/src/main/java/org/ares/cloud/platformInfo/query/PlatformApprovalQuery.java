package org.ares.cloud.platformInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 平台审批 查询原型
* @version 1.0.0
* @date 2024-10-31
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "平台审批查询")
public class PlatformApprovalQuery extends Query {


    @Schema(description = "商户id")
    String tenantId;

    @Schema(description = "审批状态1:未处理；2:已处理")
    String status;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}