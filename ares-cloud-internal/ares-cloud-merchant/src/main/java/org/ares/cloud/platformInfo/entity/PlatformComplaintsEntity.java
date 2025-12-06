package org.ares.cloud.platformInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description 平台投诉建议 实体
* @version 1.0.0
* @date 2024-10-17
*/
@TableName("platform_complaints")
@EqualsAndHashCode(callSuper = false)
public class PlatformComplaintsEntity extends TenantEntity {
	/**
	* 用户名称
	*/
	private String userName;
	/**
	* 用户邮件
	*/
	private String userEmail;
	/**
	* 投诉内容
	*/
	private String content;
	/**
	* 用户手机号
	*/
	private String phone;
	/**
	* 用户id
	*/
	private String userId;
	/**
	* 获取用户名称
	* @return 用户名称
	*/
	public String getUserName() {
		return userName;
	}

	/**
	* 设置用户名称
	* @param userName 用户名称
	*/
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	* 获取用户邮件
	* @return 用户邮件
	*/
	public String getUserEmail() {
		return userEmail;
	}

	/**
	* 设置用户邮件
	* @param userEmail 用户邮件
	*/
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	/**
	* 获取投诉内容
	* @return 投诉内容
	*/
	public String getContent() {
		return content;
	}

	/**
	* 设置投诉内容
	* @param content 投诉内容
	*/
	public void setContent(String content) {
		this.content = content;
	}
	/**
	* 获取用户手机号
	* @return 用户手机号
	*/
	public String getPhone() {
		return phone;
	}

	/**
	* 设置用户手机号
	* @param phone 用户手机号
	*/
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	* 获取用户id
	* @return 用户id
	*/
	public String getUserId() {
		return userId;
	}

	/**
	* 设置用户id
	* @param userId 用户id
	*/
	public void setUserId(String userId) {
		this.userId = userId;
	}
}