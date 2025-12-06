package org.ares.cloud.platformInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 工单 数据模型
* @version 1.0.0
* @date 2024-10-16
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "工单")
public class PlatformWorkOrderDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "工单状态：created-未处理；finished-已处理")
	@JsonProperty(value = "status")
	private String status = "created";

	@Schema(description = "工单类型", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "workOrderType")
	@NotBlank(message = "{validation.platformInfo.platformWorkOrderDto.workOrderType.notBlank}")
	private String workOrderType;

	@Schema(description = "发起人用户id", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "userId")
	@NotBlank(message = "{validation.platformInfo.platformWorkOrderDto.userId.userId}")
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

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
}