package org.ares.cloud.rider.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 骑手 实体
* @version 1.0.0
* @date 2025-08-26
*/
@TableName("rider")
@EqualsAndHashCode(callSuper = false)
public class RiderEntity extends TenantEntity {
	/**
	 * 关联的用户ID
	 */
	private String userId;
	/**
	* 邮箱
	*/
	private String email;
	/**
	* 国家代码
	*/
	private String countryCode;
	/**
	* 手机号
	*/
	private String phone;
	/**
	* 名称
	*/
	private String name;
	/**
	* 状态(1:正常,2:停用,3在线)
	*/
	private Integer status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
	* 获取名称
	* @return 名称
	*/
	public String getName() {
		return name;
	}

	/**
	* 设置名称
	* @param name 名称
	*/
	public void setName(String name) {
		this.name = name;
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
}