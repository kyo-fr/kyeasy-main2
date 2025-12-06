package org.ares.cloud.platformInfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.BaseEntity;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 平台审批 实体
* @version 1.0.0
* @date 2024-10-31
*/
@TableName("platform_approval")
@EqualsAndHashCode(callSuper = false)
public class PlatformApprovalEntity extends BaseEntity {
	private String tenantId;

	/**
	* 审批状态
	*/
	private Integer status;

	/**
	* 审批编号
	*/
	private String approvalId;

	/**
	 * 商户订阅id
	 */
	private String merchantSubscribeId;

	/**
	 * 支付渠道1:线上；2:线下
	 */
	private Integer channelType;

	/**
	 * 审批订阅类型,open_merchant:开户商户订阅；add_memory:增加存储订阅
	 */
	private String type;
//
//	/**
//	 * 支付渠道唯一key
//	 */
//	private String paymentChannelId;

	/**
	 * 企业名称
	 */
	private String name;


	/**
	 * 税务号(企业编号)
	 */
	private String taxNum;

	/**
	 * 订单编号(系统生成)
	 */
	private String orderId;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 月份
	 */
	private Integer month;


	/**
	 * 失效时间
	 */
	private Long overdueDate;


	/**
	 * 购买总金额
	 */
	private BigDecimal totalPrice;


	/**
	 * 购买内存大小
	 */
	private Integer memory;


	private String subscribeType;


	/**
	 * 使用开始时间
	 */
	private Long startTime;

	/**
	 * 使用结束时间
	 */
	private Long endTime;

	/**
	 * 合同id
	 */
	private String contractId;


	/**
	 * 税率值
	 */
	private BigDecimal taxRate;

	/**
	 * 订阅状态 sub:订阅；over:到期
	 */
	@Schema(description ="订阅状态 sub:订阅；over:到期" )
	private String subStatus;

	/**
	 * 可用存储
	 */
	@Schema(description ="可用存储" )
	private Integer usableMemory;

	@Schema(description ="支付渠道id" )
	private String paymentChannelId;

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

	public String getOrderId() {
		return orderId;
	}

	public String getUserId() {
		return userId;
	}


	public void setName(String name) {
		this.name = name;
	}

	public void setTaxNum(String taxNum) {
		this.taxNum = taxNum;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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


	public Integer getChannelType() {
		return channelType;
	}

	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}
//
//	public String getChannelKey() {
//		return channelKey;
//	}
//
//	public void setChannelKey(String channelKey) {
//		this.channelKey = channelKey;
//	}

	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Long getOverdueDate() {
		return overdueDate;
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

	public String getSubscribeType() {
		return subscribeType;
	}

	public void setSubscribeType(String subscribeType) {
		this.subscribeType = subscribeType;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getPaymentChannelId() {
		return paymentChannelId;
	}

	public void setPaymentChannelId(String paymentChannelId) {
		this.paymentChannelId = paymentChannelId;
	}

}