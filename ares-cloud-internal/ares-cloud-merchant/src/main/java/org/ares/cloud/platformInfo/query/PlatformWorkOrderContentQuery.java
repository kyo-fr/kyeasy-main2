package org.ares.cloud.platformInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 工单内容 查询原型
* @version 1.0.0
* @date 2024-10-17
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "工单内容查询")
public class PlatformWorkOrderContentQuery extends Query {

    @Schema(description = "工单ID" ,requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "{validation.platform.workOrderContent.workOrderId.notBlank}")
    private String workOrderId;


    public String getWorkOrderId() {
        return workOrderId;
    }
    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }
}