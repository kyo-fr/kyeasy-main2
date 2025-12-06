package org.ares.cloud.merchantInfo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.entity.TenantEntity;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 商户订阅 实体
* @version 1.0.0
* @date 2024-10-11
*/
@TableName("merchant_subscribe")
@EqualsAndHashCode(callSuper = false)
public class MerchantSubscribeEntity extends TenantEntity {
	/**
	* 订阅类型：cash-收银系统订阅；storage-存储空间订阅
	*/
	private String subscribeType;

	/**
	* 发票id
	*/
	private String invoiceId;

	/**
	* 使用开始时间
	*/
	private Long startTime;

	/**
	* 使用结束时间
	*/
	private Long endTime;

	/**
	* 存储空间
	*/
	private Integer memory;


	/**
	 * 金额
	 */
	private BigDecimal amount;


	private String contractId;


	private String approvalId;


	private Integer status;


	/**
	* 获取订阅类型：cash-收银系统订阅；storage-存储空间订阅
	* @return 订阅类型：cash-收银系统订阅；storage-存储空间订阅
	*/
	public String getSubscribeType() {
		return subscribeType;
	}

	/**
	* 设置订阅类型：cash-收银系统订阅；storage-存储空间订阅
	* @param subscribeType 订阅类型：cash-收银系统订阅；storage-存储空间订阅
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
	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getApprovalId() {
		return approvalId;
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