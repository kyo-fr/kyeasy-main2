package org.ares.cloud.platformInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 存储变更明细表 数据模型
* @version 1.0.0
* @date 2025-06-16
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "存储变更明细表")
public class PlatformApprovalRecordDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "变更的存储")
	@JsonProperty(value = "changeMemory")
	private Long changeMemory;

	@Schema(description = "审批id")
	@JsonProperty(value = "approvalId")
	private String approvalId;

	@Schema(description = "变更类型 SEND:开通；OVER：过期；USED:使用(扣除)")
	@JsonProperty(value = "recordType")
	private String recordType;

	@Schema(description = "数据来源 order:订单; invoice：发票; product:商品; cancel:取消; refund:退款; video:视频; img:图片;other：其他")
	@JsonProperty(value = "dataSource")
	@NotNull(message = "{validation.platformInfo.approvalRecordDto.dataSource.notBlank}")
	private String dataSource;

	@Schema(description = "数据类型 file:文件类; data:数据类(数据表中的数据)")
	@JsonProperty(value = "dataType")
	@NotNull(message = "{validation.platformInfo.approvalRecordDto.dataType.notBlank}")
	private String dataType;


//	@Schema(description = "租户")
//	@JsonProperty(value = "tenantId")
//	@NotNull(message = "{validation.platformInfo.approvalRecordDto.dtaType.notBlank}")
//	private String tenantId;

	@Schema(description = "描述")
	@JsonProperty(value = "description")
	private String description;

	/**
	* 获取变更的存储
	* @return 变更的存储
	*/
	public Long getChangeMemory() {
	return changeMemory;
	}

	/**
	* 设置变更的存储
	* @param changeMemory 变更的存储
	*/
	public void setChangeMemory(Long changeMemory) {
	this.changeMemory = changeMemory;
	}
	/**
	* 获取审批id
	* @return 审批id
	*/
	public String getApprovalId() {
	return approvalId;
	}

	/**
	* 设置审批id
	* @param approvalId 审批id
	*/
	public void setApprovalId(String approvalId) {
	this.approvalId = approvalId;
	}
	/**
	* 获取变更类型
	* @return 变更类型
	*/
	public String getRecordType() {
	return recordType;
	}

	/**
	* 设置变更类型
	* @param recordType 变更类型
	*/
	public void setRecordType(String recordType) {
	this.recordType = recordType;
	}

	/**
	* 获取描述
	* @return 描述
	*/
	public String getDescription() {
		return description;
	}

	/**
	* 描述
	* @param desc 描述
	*/

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDataSource() {
		return dataSource;
	}

	public String getDataType() {
		return dataType;
	}


	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}