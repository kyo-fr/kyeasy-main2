package org.ares.cloud.platformInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 服务和帮助 数据模型
* @version 1.0.0
* @date 2024-10-30
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "服务和帮助")
public class PlatformServiceDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "内容",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "content")
	@NotBlank(message = "{validation.platform.service.content.notBlank}")
	@Size(max = 2000, message = "validation.size.max")
	private String content;

	@Schema(description = "租户id")
	@JsonProperty(value = "tenantId")
	private String tenantId;

	@Schema(description = "类型:1-帮助；2-服务条款",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "type")
	@NotNull(message = "{validation.platform.service.type.notBlank}")
	private Integer type;

	@Schema(description = "标题",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "title")
	@NotBlank(message = "{validation.platform.service.title.notBlank}")
	@Size(max = 1000, message = "validation.size.max")
	private String title;


	@Schema(description = "针对用户标识(1:普通会员;2:骑士;3:商户,4:所有用户;)",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "identity")
	@NotBlank(message = "{validation.platform.service.identity.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String identity;


	/**
	* 获取内容
	* @return 内容
	*/
	public String getContent() {
	return content;
	}

	/**
	* 设置内容
	* @param content 内容
	*/
	public void setContent(String content) {
	this.content = content;
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
	* 获取类型:1-帮助；2-服务条款
	* @return 类型:1-帮助；2-服务条款
	*/
	public Integer getType() {
	return type;
	}

	/**
	* 设置类型:1-帮助；2-服务条款
	* @param type 类型:1-帮助；2-服务条款
	*/
	public void setType(Integer type) {
	this.type = type;
	}
	/**
	* 获取标题
	* @return 标题
	*/
	public String getTitle() {
	return title;
	}

	/**
	* 设置标题
	* @param title 标题
	*/
	public void setTitle(String title) {
	this.title = title;
	}

	/**
	* 获取用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)
	* @return 用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)
	*/
	public String getIdentity() {
		return identity;
	}

	/**
	* 获取用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)
	* @param identity 用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)
	*/
	public void setIdentity(String identity) {
		this.identity = identity;
	}
}