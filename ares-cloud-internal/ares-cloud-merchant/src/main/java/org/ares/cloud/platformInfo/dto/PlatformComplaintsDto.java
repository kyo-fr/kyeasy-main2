package org.ares.cloud.platformInfo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;

/**
* @author hugo tangxkwork@163.com
* @description 平台投诉建议 数据模型
* @version 1.0.0
* @date 2024-10-17
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "平台投诉建议")
public class PlatformComplaintsDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "用户名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "userName")
	@NotBlank(message = "{validation.platform.complaints.userName.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String userName;

	@Schema(description = "用户邮件",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "userEmail")
	@NotBlank(message = "{validation.platform.complaints.userEmail.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String userEmail;

	@Schema(description = "投诉内容",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "content")
	@NotBlank(message = "{validation.platform.complaints.content.notBlank}")
	@Size(max = 2000, message = "validation.size.max")
	private String content;

	@Schema(description = "用户手机号",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "phone")
	@NotBlank(message = "{validation.platform.complaints.phone.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String phone;

	@Schema(description = "用户id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "userId")
	@NotBlank(message = "{validation.platform.complaints.userId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
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