package org.ares.cloud.platformInfo.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.serializer.CustomBigDecimalSerializer;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 订阅基础信息 数据模型
* @version 1.0.0
* @date 2024-10-31
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "订阅基础信息")
public class PlatformSubscribeDto extends BaseDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "订阅类型：cash-收银系统订阅；storage-存储空间订阅",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "subscribeType")
	private String subscribeType;

	@Schema(description = "金额",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "price")
	@JsonSerialize(using = CustomBigDecimalSerializer.class)
	private BigDecimal price;


	@Schema(description = "存储空间" ,hidden = true)
	@JsonProperty(value = "memory")
	private Integer memory;

	@Schema(description ="月份" )
	@NotNull(message = "{validation.platformInfo.approval.month.notBlank}")
	private Integer month;

	/**
	* 获取订阅类型key
	* @return 订阅类型key
	*/
	public String getSubscribeType() {
	return subscribeType;
	}

	/**
	* 设置订阅类型（cash-收银系统订阅；storage-存储空间订阅）
	* @param subscribeType 订阅类型（cash-收银系统订阅；storage-存储空间订阅）
	*/
	public void setSubscribeType(String subscribeType) {
	this.subscribeType = subscribeType;
	}
	/**
	* 获取金额
	* @return 金额
	*/
	public BigDecimal getPrice() {
	return price;
	}

	/**
	* 设置金额
	* @param price 金额
	*/
	public void setPrice(BigDecimal price) {
	this.price = price;
	}
//	/**
//	* 获取子类型:网站、财务软件
//	* @return 子类型:网站、财务软件
//	*/
//	public Integer getType() {
//	return type;
//	}
//
//	/**
//	* 设置子类型:网站、财务软件
//	* @param type 子类型:网站、财务软件
//	*/
//	public void setType(Integer type) {
//	this.type = type;
//	}
	/**
	* 获取存储空间
	* @return 存储空间
	*/
	public Integer getMemory() {
	return memory;
	}

	/**
	* 设置存储空间
	* @param memory 存储空间
	*/
	public void setMemory(Integer memory) {
	this.memory = memory;
	}


	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}
}