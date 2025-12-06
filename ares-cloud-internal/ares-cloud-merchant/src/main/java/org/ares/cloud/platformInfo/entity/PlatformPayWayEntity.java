package org.ares.cloud.platformInfo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo tangxkwork@163.com
* @description 平台支付类型 实体
* @version 1.0.0
* @date 2024-10-15
*/
@TableName("platform_pay_way")
@EqualsAndHashCode(callSuper = false)
public class PlatformPayWayEntity extends BaseEntity {
	/**
	* 支付渠道1:线上；2:线下
	*/
	private Integer channelType;
	/**
	* 图片
	*/
	private String url;
	/**
	* 状态 1:启用；2:关闭
	*/
	private Integer status;
	/**
	* 前端验证key
	*/
	private String frontKey;
	/**
	* 后端验证key
	*/
	private String backKey;

	/**
	 * 支付名称
	 */
	private String payName;

	/**
	* 获取支付渠道1:线上；2:线下
	* @return 支付渠道1:线上；2:线下
	*/
	public Integer getChannelType() {
		return channelType;
	}

	/**
	* 设置支付渠道1:线上；2:线下
	* @param channelType 支付渠道1:线上；2:线下
	*/
	public void setChannelType(Integer channelType) {
		this.channelType = channelType;
	}
	/**
	* 获取图片
	* @return 图片
	*/
	public String getUrl() {
		return url;
	}

	/**
	* 设置图片
	* @param url 图片
	*/
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	* 获取状态 1:启用；2:关闭
	* @return 状态 1:启用；2:关闭
	*/
	public Integer getStatus() {
		return status;
	}

	/**
	* 设置状态 1:启用；2:关闭
	* @param status 状态 1:启用；2:关闭
	*/
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	* 获取前端验证key
	* @return 前端验证key
	*/
	public String getFrontKey() {
		return frontKey;
	}

	/**
	* 设置前端验证key
	* @param frontKey 前端验证key
	*/
	public void setFrontKey(String frontKey) {
		this.frontKey = frontKey;
	}
	/**
	* 获取后端验证key
	* @return 后端验证key
	*/
	public String getBackKey() {
		return backKey;
	}

	/**
	* 设置后端验证key
	* @param backKey 后端验证key
	*/
	public void setBackKey(String backKey) {
		this.backKey = backKey;
	}

	/**
	 * 设置后端验证key
	 *  @return   payName 支付名称
	 */
	public String getPayName() {
		return payName;
	}

	/**
	 * 设置后端验证key
	 * @param payName 支付名称
	 */
	public void setPayName(String payName) {
		this.payName = payName;
	}
}