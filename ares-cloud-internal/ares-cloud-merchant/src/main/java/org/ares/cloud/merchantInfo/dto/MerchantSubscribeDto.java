package org.ares.cloud.merchantInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 商户订阅 数据模型
* @version 1.0.0
* @date 2024-10-11
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户订阅")
public class MerchantSubscribeDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "订阅类型：cash-收银系统订阅；storage-存储空间订阅",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "subscribeType")
	@NotNull(message = "{validation.merchantSubscribe.subscribeType.notBlank}")
	private String subscribeType;

	@Schema(description = "发票id")
	@JsonProperty(value = "invoiceId")
	private String invoiceId;

	@Schema(description = "使用开始时间",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "startTime")
//	@NotBlank(message = "{validation.merchantSubscribe.startTime.notBlank}")
	private Long startTime;

	@Schema(description = "使用结束时间",requiredMode = Schema.RequiredMode.REQUIRED)
//	@NotBlank(message = "{validation.merchantSubscribe.endTime.notBlank}")
	@JsonProperty(value = "endTime")
	private Long endTime;

	@Schema(description = "存储空间")
	@JsonProperty(value = "memory")
	private Integer memory;

	@Schema(description = "金额")
	@JsonProperty(value = "amount")
	@NotNull(message = "{validation.merchantSubscribe.amount.notBlank}")
	private BigDecimal amount;

	@Schema(description = "合同id")
	@JsonProperty(value = "contractId")
	private String contractId;

	@Schema(description = "审批id",hidden = true)
	@JsonProperty(value = "approvalId")
	private String approvalId;

	@Schema(description = "状态：1:订阅；2到期")
	@JsonProperty(value = "status")
	private Integer status;

	/**
	* 获取订阅类型（1：网站订阅；2：存储订阅）
	* @return 订阅类型（1：网站订阅；2：存储订阅）
	*/
	public String getSubscribeType() {
	return subscribeType;
	}

	/**
	* 设置订阅类型（1：网站订阅；2：存储订阅）
	* @param subscribeType 订阅类型（1：网站订阅；2：存储订阅）
	*/
	public void setSubscribeType(String subscribeType) {
	this.subscribeType = subscribeType;
	}
	/**
	* 获取发票id
	* @return 发票id
	*/
	public String getInvoiceId() {
	return invoiceId;
	}

	/**
	* 设置发票id
	* @param invoiceId 发票id
	*/
	public void setInvoiceId(String invoiceId) {
	this.invoiceId = invoiceId;
	}
	/**
	* 获取使用开始时间
	* @return 使用开始时间
	*/
	public Long getStartTime() {
	return startTime;
	}

	/**
	* 设置使用开始时间
	* @param startTime 使用开始时间
	*/
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	/**
	* 获取使用结束时间
	* @return 使用结束时间
	*/
	public Long getEndTime() {
	return endTime;
	}

	/**
	* 设置使用结束时间
	* @param endTime 使用结束时间
	*/
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}
	/**
	* 获取存储空间
	* @return 存储空间
	*/
	public Integer getMemory() {
	return memory;
	}

	/**
	* 设置存储空间
	* @param memory 存储空间
	*/
	public void setMemory(Integer memory) {
	this.memory = memory;
	}


	/**
	 * 金额
	 * @return 金额
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 设置金额
	 * @param amount 金额
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getContractId() {
		return contractId;
	}

	public String getApprovalId() {
		return approvalId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public void setApprovalId(String approvalId) {
		this.approvalId = approvalId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}