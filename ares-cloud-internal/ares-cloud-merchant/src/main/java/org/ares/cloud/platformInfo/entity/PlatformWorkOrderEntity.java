package org.ares.cloud.platformInfo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo tangxkwork@163.com
* @description 工单 实体
* @version 1.0.0
* @date 2024-10-16
*/
@TableName("platform_work_order")
@EqualsAndHashCode(callSuper = false)
public class PlatformWorkOrderEntity extends BaseEntity {
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

	/**
	 * 获取工单状态(1:未处理；2:处理中；3:已处理)
	 * @return 工单状态(1:未处理；2:处理中；3:已处理)
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 设置工单状态(1:未处理；2:处理中；3:已处理)
	 * @param status 工单状态(1:未处理；2:处理中；3:已处理)
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getWorkOrderType() {
		return workOrderType;
	}

	public String getUserId() {
		return userId;
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
	public String getTenantId() {
		return tenantId;
	}

}