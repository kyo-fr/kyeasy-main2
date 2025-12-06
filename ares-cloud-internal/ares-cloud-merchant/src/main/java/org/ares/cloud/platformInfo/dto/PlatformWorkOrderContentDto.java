package org.ares.cloud.platformInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 工单内容 数据模型
* @version 1.0.0
* @date 2024-10-17
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "工单内容")
public class PlatformWorkOrderContentDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "发送者id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "sendId")
	@NotBlank(message = "{validation.platform.workOrderContent.sendId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String sendId;

	@Schema(description = "接收者id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "receiverId")
	@NotBlank(message = "{validation.platform.workOrderContent.receiverId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String receiverId;

	@Schema(description = "工单id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "workOrderId")
	@NotBlank(message = "{validation.platform.workOrderContent.workOrderId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String workOrderId;

	@Schema(description = "会话内容",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "content")
	@NotBlank(message = "{validation.platform.workOrderContent.content.notBlank}")
	@Size(max = 2048, message = "validation.size.max")
	private String content;
	/**
	* 获取发送者id
	* @return 发送者id
	*/
	public String getSendId() {
	return sendId;
	}

	/**
	* 设置发送者id
	* @param sendId 发送者id
	*/
	public void setSendId(String sendId) {
	this.sendId = sendId;
	}
	/**
	* 获取接收者id
	* @return 接收者id
	*/
	public String getReceiverId() {
	return receiverId;
	}

	/**
	* 设置接收者id
	* @param receiverId 接收者id
	*/
	public void setReceiverId(String receiverId) {
	this.receiverId = receiverId;
	}
	/**
	* 获取工单id
	* @return 工单id
	*/
	public String getWorkOrderId() {
	return workOrderId;
	}

	/**
	* 设置工单id
	* @param workOrderId 工单id
	*/
	public void setWorkOrderId(String workOrderId) {
	this.workOrderId = workOrderId;
	}
	/**
	* 获取会话内容
	* @return 会话内容
	*/
	public String getContent() {
	return content;
	}

	/**
	* 设置会话内容
	* @param content 会话内容
	*/
	public void setContent(String content) {
	this.content = content;
	}
}