package org.ares.cloud.api.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.ares.cloud.common.dto.TenantDto;

/**
* @author hugo tangxkwork@163.com
* @description 用户 数据模型
* @version 1.0.0
* @date 2024-11-11
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "用户")
public class UserDto  extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "邮箱")
	@JsonProperty(value = "email")
	@Size(max = 32, message = "validation.size.max")
	private String email;

	@Schema(description = "版本号")
	@JsonProperty(value = "version")
	private Integer version;

	@Schema(description = "删除标记")
	@JsonProperty(value = "deleted")
	private Integer deleted;

	@Schema(description = "密码",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "password")
	@NotBlank(message = "{validation.user.password}")
	@Size(max = 255, message = "validation.size.max")
	private String password;

	@Schema(description = "状态(1:正常,2:停用)",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "status")
	private Integer status;

	@Schema(description = "企业编号")
	@JsonProperty(value = "enterpriseNumber")
	@Size(max = 32, message = "validation.size.max")
	private String enterpriseNumber;

	@Schema(description = "合同id")
	@JsonProperty(value = "contractId")
	@Size(max = 32, message = "validation.size.max")
	private String contractId;

	@Schema(description = "商户id")
	@JsonProperty(value = "merchantId")
	@Size(max = 32, message = "validation.size.max")
	private String merchantId;

//	@Schema(description = "租户")
//	@JsonProperty(value = "tenantId")
//	@Size(max = 30, message = "validation.size.max")
//	private String tenantId;

	@Schema(description = "用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "identity")
	private Integer identity;

	@Schema(description = "国家代码",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "countryCode")
	@NotBlank(message = "{validation.user.countryCode}")
	@Size(max = 10, message = "validation.size.max")
	private String countryCode;

	@Schema(description = "手机号",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "phone")
	@NotBlank(message = "{validation.user.phone}")
	@Size(max = 20, message = "validation.size.max")
	private String phone;

	@Schema(description = "账号")
	@JsonProperty(value = "account")
	@Size(max = 20, message = "validation.size.max")
	private String account;

	@Schema(description = "更新时间")
	@JsonProperty(value = "updateTime")
	private Long updateTime;

	@Schema(description = "更新者")
	@JsonProperty(value = "updater")
	@Size(max = 30, message = "validation.size.max")
	private String updater;

	@Schema(description = "创建时间")
	@JsonProperty(value = "createTime")
	private Long createTime;

	@JsonProperty(value = "creator")
	@Size(max = 30, message = "validation.size.max")
	private String creator;

	@Schema(description = "主键")
	@JsonProperty(value = "id")
	@Size(max = 30, message = "validation.size.max")
	private String id;

	@Schema(description = "昵称")
	@JsonProperty(value = "nickname")
	@Size(max = 20, message = "validation.size.max")
	private String nickname;

	private Integer isTemporary;
	public Integer getIsTemporary() {
		return isTemporary;
	}

	public void setIsTemporary(Integer isTemporary) {
		this.isTemporary = isTemporary;
	}


	/**
	* 获取邮箱
	* @return 邮箱
	*/
	public String getEmail() {
	return email;
	}

	/**
	* 设置邮箱
	* @param email 邮箱
	*/
	public void setEmail(String email) {
	this.email = email;
	}
	/**
	* 获取版本号
	* @return 版本号
	*/
	public Integer getVersion() {
	return version;
	}

	/**
	* 设置版本号
	* @param version 版本号
	*/
	public void setVersion(Integer version) {
	this.version = version;
	}
	/**
	* 获取删除标记
	* @return 删除标记
	*/
	public Integer getDeleted() {
	return deleted;
	}

	/**
	* 设置删除标记
	* @param deleted 删除标记
	*/
	public void setDeleted(Integer deleted) {
	this.deleted = deleted;
	}
	/**
	* 获取密码
	* @return 密码
	*/
	public String getPassword() {
	return password;
	}

	/**
	* 设置密码
	* @param password 密码
	*/
	public void setPassword(String password) {
	this.password = password;
	}
	/**
	* 获取状态(1:正常,2:停用)
	* @return 状态(1:正常,2:停用)
	*/
	public Integer getStatus() {
	return status;
	}

	/**
	* 设置状态(1:正常,2:停用)
	* @param status 状态(1:正常,2:停用)
	*/
	public void setStatus(Integer status) {
	this.status = status;
	}
	/**
	* 获取企业编号
	* @return 企业编号
	*/
	public String getEnterpriseNumber() {
	return enterpriseNumber;
	}

	/**
	* 设置企业编号
	* @param enterpriseNumber 企业编号
	*/
	public void setEnterpriseNumber(String enterpriseNumber) {
	this.enterpriseNumber = enterpriseNumber;
	}
	/**
	* 获取合同id
	* @return 合同id
	*/
	public String getContractId() {
	return contractId;
	}

	/**
	* 设置合同id
	* @param contractId 合同id
	*/
	public void setContractId(String contractId) {
	this.contractId = contractId;
	}
	/**
	* 获取商户id
	* @return 商户id
	*/
	public String getMerchantId() {
	return merchantId;
	}

	/**
	* 设置商户id
	* @param merchantId 商户id
	*/
	public void setMerchantId(String merchantId) {
	this.merchantId = merchantId;
	}
//	/**
//	* 获取租户
//	* @return 租户
//	*/
//	public String getTenantId() {
//	return tenantId;
//	}
//
//	/**
//	* 设置租户
//	* @param tenantId 租户
//	*/
//	public void setTenantId(String tenantId) {
//	this.tenantId = tenantId;
//	}
	/**
	* 获取用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)
	* @return 用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)
	*/
	public Integer getIdentity() {
	return identity;
	}

	/**
	* 设置用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)
	* @param identity 用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)
	*/
	public void setIdentity(Integer identity) {
	this.identity = identity;
	}
	/**
	* 获取国家代码
	* @return 国家代码
	*/
	public String getCountryCode() {
	return countryCode;
	}

	/**
	* 设置国家代码
	* @param countryCode 国家代码
	*/
	public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
	}
	/**
	* 获取手机号
	* @return 手机号
	*/
	public String getPhone() {
	return phone;
	}

	/**
	* 设置手机号
	* @param phone 手机号
	*/
	public void setPhone(String phone) {
	this.phone = phone;
	}
	/**
	* 获取账号
	* @return 账号
	*/
	public String getAccount() {
	return account;
	}

	/**
	* 设置账号
	* @param account 账号
	*/
	public void setAccount(String account) {
	this.account = account;
	}
	/**
	* 获取更新时间
	* @return 更新时间
	*/
	public Long getUpdateTime() {
	return updateTime;
	}

	/**
	* 设置更新时间
	* @param updateTime 更新时间
	*/
	public void setUpdateTime(Long updateTime) {
	this.updateTime = updateTime;
	}
	/**
	* 获取更新者
	* @return 更新者
	*/
	public String getUpdater() {
	return updater;
	}

	/**
	* 设置更新者
	* @param updater 更新者
	*/
	public void setUpdater(String updater) {
	this.updater = updater;
	}
	/**
	* 获取创建时间
	* @return 创建时间
	*/
	public Long getCreateTime() {
	return createTime;
	}

	/**
	* 设置创建时间
	* @param createTime 创建时间
	*/
	public void setCreateTime(Long createTime) {
	this.createTime = createTime;
	}
	/**
	* 获取创建者
	* @return 创建者
	*/
	public String getCreator() {
	return creator;
	}

	/**
	* 设置创建者
	* @param creator 创建者
	*/
	public void setCreator(String creator) {
	this.creator = creator;
	}
	/**
	* 获取主键
	* @return 主键
	*/
	public String getId() {
	return id;
	}

	/**
	* 设置主键
	* @param id 主键
	*/
	public void setId(String id) {
	this.id = id;
	}
	/**
	* 获取昵称
	* @return 昵称
	*/
	public String getNickname() {
	return nickname;
	}

	/**
	* 设置昵称
	* @param nickname 昵称
	*/
	public void setNickname(String nickname) {
	this.nickname = nickname;
	}
}