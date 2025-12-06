package org.ares.cloud.merchantInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 商户配送设置 数据模型
* @version 1.0.0
* @date 2024-11-05
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户配送设置")
public class MerchantDistributionDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "每公里收费(收费配送)")
	@JsonProperty(value = "perKilometer")
	private BigDecimal perKilometer;

	@Schema(description = "超过公里费用(第三方配送)")
	@JsonProperty(value = "excessFees")
	private BigDecimal excessFees;

	@Schema(description = "是否开启第三方配送 Y-是；N-否")
	@JsonProperty(value = "isThirdParties")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String isThirdParties;

	@Schema(description = "地址类型: default-默认商户地址；warehouse-仓库地址；port-港口地址",requiredMode = Schema.RequiredMode.REQUIRED)
	@NotBlank(message = "{validation.merchant.merchantDistribution.addressType.notBlank}")
	@JsonProperty(value = "addressType")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String addressType;

	@Schema(description = "地址",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "address")
	@NotBlank(message = "{validation.merchant.merchantDistribution.address.notBlank}")
	@Size(max = 128, message = "validation.size.max")
	private String address;

	@Schema(description = "最低配送金额",requiredMode = Schema.RequiredMode.REQUIRED)
	@NotNull(message = "{validation.merchant.merchantDistribution.minimumDeliveryAmount.notBlank}")
	@JsonProperty(value = "minimumDeliveryAmount")
	private BigDecimal minimumDeliveryAmount;

	@Schema(description = "超出公里不配送",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "beyondKilometerNotDelivered")
	@NotNull(message = "{validation.merchant.merchantDistribution.beyondKilometerNotDelivered.notBlank}")
	private Integer beyondKilometerNotDelivered;

	@Schema(description = "配送类型 free-免费配送；charge-收费配送")
	@JsonProperty(value = "type")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String type;

	@Schema(description = "0-2公里收费(收费配送)")
	@JsonProperty(value = "stage")
	private Double stage;

	@Schema(description = "第三方配送类型 global-全局第三方；exceed-超出公里")
	@JsonProperty(value = "globalThirdParties")
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String globalThirdParties;

	@Schema(description = "超出公里数(第三方配送)")
	@JsonProperty(value = "kilometersExceeded")
	private Integer kilometersExceeded;
	/**
	* 获取每公里收费(收费配送)
	* @return 每公里收费(收费配送)
	*/
	public BigDecimal getPerKilometer() {
	return perKilometer;
	}

	/**
	* 设置每公里收费(收费配送)
	* @param perKilometer 每公里收费(收费配送)
	*/
	public void setPerKilometer(BigDecimal perKilometer) {
	this.perKilometer = perKilometer;
	}
	/**
	* 获取超过公里费用(第三方配送)
	* @return 超过公里费用(第三方配送)
	*/
	public BigDecimal getExcessFees() {
	return excessFees;
	}

	/**
	* 设置超过公里费用(第三方配送)
	* @param excessFees 超过公里费用(第三方配送)
	*/
	public void setExcessFees(BigDecimal excessFees) {
	this.excessFees = excessFees;
	}
	/**
	* 获取是否开启第三方配送 Y-是；N-否
	* @return 是否开启第三方配送 Y-是；N-否
	*/
	public String getIsThirdParties() {
	return isThirdParties;
	}

	/**
	* 设置是否开启第三方配送 Y-是；N-否
	* @param isThirdParties 是否开启第三方配送 Y-是；N-否
	*/
	public void setIsThirdParties(String isThirdParties) {
	this.isThirdParties = isThirdParties;
	}
	/**
	* 获取地址类型: default-默认商户地址；warehouse-仓库地址；port-港口地址
	* @return 地址类型: default-默认商户地址；warehouse-仓库地址；port-港口地址
	*/
	public String getAddressType() {
	return addressType;
	}

	/**
	* 设置地址类型: default-默认商户地址；warehouse-仓库地址；port-港口地址
	* @param addressType 地址类型: default-默认商户地址；warehouse-仓库地址；port-港口地址
	*/
	public void setAddressType(String addressType) {
	this.addressType = addressType;
	}
	/**
	* 获取地址
	* @return 地址
	*/
	public String getAddress() {
	return address;
	}

	/**
	* 设置地址
	* @param address 地址
	*/
	public void setAddress(String address) {
	this.address = address;
	}
	/**
	* 获取最低配送金额
	* @return 最低配送金额
	*/
	public BigDecimal getMinimumDeliveryAmount() {
	return minimumDeliveryAmount;
	}

	/**
	* 设置最低配送金额
	* @param minimumDeliveryAmount 最低配送金额
	*/
	public void setMinimumDeliveryAmount(BigDecimal minimumDeliveryAmount) {
	this.minimumDeliveryAmount = minimumDeliveryAmount;
	}
	/**
	* 获取超出公里不配送
	* @return 超出公里不配送
	*/
	public Integer getBeyondKilometerNotDelivered() {
	return beyondKilometerNotDelivered;
	}

	/**
	* 设置超出公里不配送
	* @param beyondKilometerNotDelivered 超出公里不配送
	*/
	public void setBeyondKilometerNotDelivered(Integer beyondKilometerNotDelivered) {
	this.beyondKilometerNotDelivered = beyondKilometerNotDelivered;
	}
	/**
	* 获取配送类型 配送类型 free-免费配送；charge-收费配送
	* @return 配送类型 配送类型 free-免费配送；charge-收费配送
	*/
	public String getType() {
	return type;
	}

	/**
	* 设置配送类型 配送类型 free-免费配送；charge-收费配送
	* @param type 配送类型 配送类型 free-免费配送；charge-收费配送
	*/
	public void setType(String type) {
	this.type = type;
	}
	/**
	* 获取0-2公里收费(收费配送)
	* @return 0-2公里收费(收费配送)
	*/
	public Double getStage() {
	return stage;
	}

	/**
	* 设置0-2公里收费(收费配送)
	* @param stage 0-2公里收费(收费配送)
	*/
	public void setStage(Double stage) {
	this.stage = stage;
	}
	/**
	* 获取第三方配送类型 第三方配送类型 global-全局第三方；exceed-超出公里
	* @return第三方配送类型 global-全局第三方；exceed-超出公里
	*/
	public String getGlobalThirdParties() {
	return globalThirdParties;
	}

	/**
	* 设置第三方配送类型 第三方配送类型 global-全局第三方；exceed-超出公里
	* @param globalThirdParties 第三方配送类型 global-全局第三方；exceed-超出公里
	*/
	public void setGlobalThirdParties(String globalThirdParties) {
	this.globalThirdParties = globalThirdParties;
	}
	/**
	* 获取超出公里数(第三方配送)
	* @return 超出公里数(第三方配送)
	*/
	public Integer getKilometersExceeded() {
	return kilometersExceeded;
	}

	/**
	* 设置超出公里数(第三方配送)
	* @param kilometersExceeded 超出公里数(第三方配送)
	*/
	public void setKilometersExceeded(Integer kilometersExceeded) {
	this.kilometersExceeded = kilometersExceeded;
	}
}