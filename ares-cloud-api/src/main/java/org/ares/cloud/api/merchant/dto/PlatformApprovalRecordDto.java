package org.ares.cloud.api.merchant.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;

/**
* @author hugo tangxkwork@163.com
* @description 存储变更明细表 数据模型
* @version 1.0.0
* @date 2025-06-16
*/
@Schema(description = "存储变更明细")
public class PlatformApprovalRecordDto  {
	private static final long serialVersionUID = 1L;


	@Schema(description = "变更的存储")
	@JsonProperty(value = "changeMemory")
	private Long changeMemory;


	@Schema(description = "数据来源 order:订单; invoice：发票; product:商品; cancel:取消; refund:退款; video:视频; img:图片;other：其他")
	@JsonProperty(value = "dataSource")
	@NotNull(message = "{validation.platformInfo.approvalRecordDto.dataSource.notBlank}")
	private String dataSource;

	@Schema(description = "数据类型 file:文件类; data:数据类(数据表中的数据)")
	@JsonProperty(value = "dataType")
	@NotNull(message = "{validation.platformInfo.approvalRecordDto.dataType.notBlank}")
	private String dataType;


	@Schema(description = "租户")
	@JsonProperty(value = "tenantId")
	@NotNull(message = "{validation.platformInfo.approvalRecordDto.dtaType.notBlank}")
	private String tenantId;

	@Schema(description = "描述")
	@JsonProperty(value = "description")
	private String description;


	public Long getChangeMemory() {
		return changeMemory;
	}

	public String getDataSource() {
		return dataSource;
	}

	public String getDataType() {
		return dataType;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getDescription() {
		return description;
	}

	public void setChangeMemory(Long changeMemory) {
		this.changeMemory = changeMemory;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}