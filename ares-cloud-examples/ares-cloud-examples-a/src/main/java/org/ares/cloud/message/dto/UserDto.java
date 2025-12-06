package org.ares.cloud.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
	import org.ares.cloud.common.dto.BaseDto;

import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
* @author hugo tangxkwork@163.com
* @description 用户 数据模型
* @version 1.0.0
* @date 2024-10-07
*/
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户")
public class UserDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "手机号")
	@JsonProperty(value = "phone")
	private String phone;

	@Schema(description = "国家代码")
	@JsonProperty(value = "countryCode")
	private String countryCode;

	@Schema(description = "账号")
	@JsonProperty(value = "account")
	private String account;

	@Schema(description = "昵称")
	@JsonProperty(value = "nickname")
	private String nickname;
}