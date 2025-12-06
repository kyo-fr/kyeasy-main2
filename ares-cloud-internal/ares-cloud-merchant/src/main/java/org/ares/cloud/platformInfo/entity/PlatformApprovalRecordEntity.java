package org.ares.cloud.platformInfo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 存储变更明细表 实体
* @version 1.0.0
* @date 2025-06-16
*/
@TableName("platform_approval_record")
@EqualsAndHashCode(callSuper = false)
public class PlatformApprovalRecordEntity extends TenantEntity {
	/**
	* 变更的存储
	*/
	private Long changeMemory;
	/**
	* 审批id
	*/
	private String approvalId;
	/**
	* 变更类型
	*/
	private String recordType;

	private String dataSource;

	private String dataType;


	/**
	* 租户
	*/
//	private String tenantId;

	/**
	 * 描述
	 */
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}


	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}