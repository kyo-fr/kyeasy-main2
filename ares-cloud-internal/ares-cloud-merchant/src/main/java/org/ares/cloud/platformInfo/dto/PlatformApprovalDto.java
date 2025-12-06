package org.ares.cloud.platformInfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.serializer.CustomBigDecimalSerializer;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 平台审批 数据模型
* @version 1.0.0
* @date 2024-10-31
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "平台审批")
public class PlatformApprovalDto extends BaseDto {
	private static final long serialVersionUID = 1L;

	@Schema(description = "租户(商户id系统生成)")
	@JsonProperty(value = "tenantId")
	private String tenantId;

	//审批状态 1:未处理；2:已处理 默认为1
	@Schema(description = "审批状态 1:未处理；2:已处理")
	@JsonProperty(value = "status")
	private Integer status;



	@Schema(description = "审批编号:系统生成")
	@JsonProperty(value = "approvalId")
	private String approvalId;


	@Schema(description = "订阅类型id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "merchantSubscribeId")
	@NotBlank(message = "{validation.platformInfo.approval.merchantSubscribeId.notBlank}")
	private String merchantSubscribeId;


	@Schema(description = "支付方式 0:线上,1:线下支付",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "channelType")
	@NotNull(message = "{validation.platformInfo.approval.channelType.notBlank}")
	private Integer  channelType;

	@Schema(description = "支付渠道id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "paymentChannelId")
	@NotBlank(message = "{validation.platformInfo.approval.paymentChannelId.notBlank}")
	private String  paymentChannelId;


	@Schema(description = "企业名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "name")
	@NotBlank(message = "{validation.platformInfo.approval.name.notBlank}")
	private String name;


	@Schema(description = "税务号(企业编号)",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "taxNum")
	@NotBlank(message = "{validation.platformInfo.approval.taxNum.notBlank}")
	private String taxNum;

	@Schema(description = "订单编号(系统生成)")
	@JsonProperty(value = "orderId")
	private String orderId;


	@Schema(description = "用户id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "userId")
	@NotBlank(message = "{validation.platformInfo.approval.userId.notBlank}")
	private String userId;


	@Schema(description ="月份" ,hidden = true)
	private Integer month;

	@Schema(description ="失效时间" ,hidden = true)
	private Long overdueDate;

	@Schema(description = "购买存储大小,单位G")
	@JsonProperty(value = "memory")
	@NotNull(message = "{validation.platformInfo.approval.memory.notBlank}")
	private Integer memory;

	@Schema(description = "审批订阅类型,open_merchant:开户商户订阅；add_memory:增加存储订阅" )
	@JsonProperty(value = "type")
	@NotNull(message = "{validation.platformInfo.approval.type.notBlank}")
	private String type;


	@Schema(description = "订阅类型：cash-收银系统订阅；storage-存储空间订阅" )
	@JsonProperty(value = "subscribeType")
	private String subscribeType;


	/**
	 * 使用开始时间
	 */
	@Schema(description = "使用开始时间")
	private Long startTime;

	/**
	 * 使用结束时间
	 */
	@Schema(description = "使用结束时间")
	private Long endTime;

	/**
	 * 合约id
	 */
	@Schema(description = "合同id")
	private String contractId;

	/**
	 * 购买总金额
	 */
	@Schema(description ="购买总金额" ,hidden = true)
	@JsonSerialize(using = CustomBigDecimalSerializer.class)
	private BigDecimal totalPrice;


	/**
	 * 税率值
	 */
	@Schema(description ="税率值" )
	private BigDecimal taxRate;


	/**
	 * 订阅状态
	 */
	@Schema(description ="订阅状态 sub:订阅；over:过期(到期)；used:已使用" )
	private String subStatus;

	/**
	 * 可用存储
	 */
	@Schema(description ="可用存储,单位G",hidden = true )
	private Integer usableMemory;
	/**
	* 获取审批状态
	* @return 审批状态
	*/
	public Integer getStatus() {
	return status;
	}

	/**
	* 设置审批状态
	* @param status 审批状态
	*/
	public void setStatus(Integer status) {
	this.status = status;
	}

	/**
	 * 订阅id
	 * @return 订阅id
	 */
	public String getMerchantSubscribeId() {
		return merchantSubscribeId;
	}

	/**
	 * 订阅id
	 * @param merchantSubscribeId 订阅id
	 */
	public void setMerchantSubscribeId(String merchantSubscribeId) {
		this.merchantSubscribeId = merchantSubscribeId;
	}

	/**
	* 获取审批编号
	* @return 审批编号
	*/
	public String getApprovalId() {
	return approvalId;
	}

	/**
	* 设置审批编号
	* @param approvalId 审批编号
	*/
	public void setApprovalId(String approvalId) {
	this.approvalId = approvalId;
	}


	public String getName() {
		return name;
	}

	public String getTaxNum() {
		return taxNum;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public String getUserId() {
		return userId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}

	public String getPaymentChannelId() {
		return paymentChannelId;
	}

	public void setPaymentChannelId(String paymentChannelId) {
		this.paymentChannelId = paymentChannelId;
	}

//	public BigDecimal getUnitPrice() {
//		return unitPrice;
//	}

//	public void setUnitPrice(BigDecimal unitPrice) {
//		this.unitPrice = unitPrice;
//	}

	public Integer getMonth() {
		return month;
	}

	public Long getOverdueDate() {
		return overdueDate;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public void setOverdueDate(Long overdueDate) {
		this.overdueDate = overdueDate;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Integer getMemory() {
		return memory;
	}

	public void setMemory(Integer memory) {
		this.memory = memory;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getStartTime() {
		return startTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public String getContractId() {
		return contractId;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getSubscribeType() {
		return subscribeType;
	}

	public void setSubscribeType(String subscribeType) {
		this.subscribeType = subscribeType;
	}

	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public String getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}

	public Integer getUsableMemory() {
		return usableMemory;
	}

	public void setUsableMemory(Integer usableMemory) {
		this.usableMemory = usableMemory;
	}
}