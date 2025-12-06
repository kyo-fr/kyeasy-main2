package org.ares.cloud.platformInfo.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import org.ares.cloud.common.dto.BaseDto;

public class PlatformWorkOrderVo extends BaseDto {

    @Schema(description = "工单状态：created-未处理；finished-已处理")
    @JsonProperty(value = "status")
    private String status = "created";

    @Schema(description = "工单类型")
    @JsonProperty(value = "workOrderType")
    private String workOrderType;


    @Schema(description = "发起人用户id")
    @JsonProperty(value = "userId")
    private String userId;

    @Schema(description = "租户id")
    @JsonProperty(value = "tenantId")
    private String tenantId;

    @Schema(description = "用户手机号")
    @JsonProperty(value = "phone")
    private String phone;


    public String getStatus() {
        return status;
    }

    public String getWorkOrderType() {
        return workOrderType;
    }

    public String getUserId() {
        return userId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public String getPhone() {
        return phone;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setWorkOrderType(String workOrderType) {
        this.workOrderType = workOrderType;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
