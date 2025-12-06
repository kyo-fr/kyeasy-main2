package org.ares.cloud.platformInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo tangxkwork@163.com
* @description 工单内容 实体
* @version 1.0.0
* @date 2024-10-17
*/
@TableName("platform_work_order_content")
@EqualsAndHashCode(callSuper = false)
public class PlatformWorkOrderContentEntity extends BaseEntity {
	/**
	* 发送者id
	*/
	private String sendId;
	/**
	* 接收者id
	*/
	private String receiverId;
	/**
	* 工单id
	*/
	private String workOrderId;
	/**
	* 会话内容
	*/
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