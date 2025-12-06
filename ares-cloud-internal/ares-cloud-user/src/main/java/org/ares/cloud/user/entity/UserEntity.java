package org.ares.cloud.user.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 用户 实体
* @version 1.0.0
* @date 2024-11-11
*/
@TableName("users")
@EqualsAndHashCode(callSuper = false)
public class UserEntity  extends TenantEntity {
	/**
	* 邮箱
	*/
	private String email;
	/**
	* 密码
	*/
	private String password;
	/**
	* 状态(1:正常,2:停用)
	*/
	private Integer status;
	/**
	* 企业编号
	*/
	private String enterpriseNumber;
	/**
	* 合同id
	*/
	private String contractId;
	/**
	* 商户id
	*/
	private String merchantId;
	/**
	* 用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)
	*/
	private Integer identity;
	/**
	* 国家代码
	*/
	private String countryCode;
	/**
	* 手机号
	*/
	private String phone;
	/**
	* 账号
	*/
	private String account;
	/**
	* 昵称
	*/
	private String nickname;
	/**
	* 是否为临时用户(0:否,1:是)
	*/
	private Integer isTemporary;
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
	/**
	* 获取租户
	* @return 租户
	*/
	public String getTenantId() {
		return tenantId;
	}

	/**
	* 设置租户
	* @param tenantId 租户
	*/
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
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
	/**
	* 获取是否为临时用户
	* @return 是否为临时用户
	*/
	public Integer getIsTemporary() {
		return isTemporary;
	}
	/**
	* 设置是否为临时用户
	* @param isTemporary 是否为临时用户
	*/
	public void setIsTemporary(Integer isTemporary) {
		this.isTemporary = isTemporary;
	}
}