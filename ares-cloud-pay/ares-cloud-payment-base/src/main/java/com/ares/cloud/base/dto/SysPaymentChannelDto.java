package com.ares.cloud.base.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;

/**
* @author hugo tangxkwork@163.com
* @description 支付渠道 数据模型
* @version 1.0.0
* @date 2025-05-13
*/
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "支付渠道")
public class SysPaymentChannelDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "Logo")
	@JsonProperty(value = "logo")
	@Size(max = 255, message = "validation.size.max")
	private String logo;

	@Schema(description = "状态 0:启用；1:关闭")
	@JsonProperty(value = "status")
	private Integer status;

	@Schema(description = "渠道唯一key",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "channelKey")
	@NotBlank(message = "{validation.base.channelKey}")
	@Size(max = 100, message = "validation.size.max")
	private String channelKey;

	@Schema(description = "支付渠道;1:线上；2:线下",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "channelType")
	private Integer channelType;

	@Schema(description = "渠道名称")
	@JsonProperty(value = "channelName")
	@Size(max = 100, message = "validation.size.max")
	private String channelName;

	@Schema(description = "支付商家针对线上支付(braintree、alipay、wechat)")
	@JsonProperty(value = "paymentMerchant")
	@Size(max = 20, message = "validation.size.max")
	private String paymentMerchant;
}