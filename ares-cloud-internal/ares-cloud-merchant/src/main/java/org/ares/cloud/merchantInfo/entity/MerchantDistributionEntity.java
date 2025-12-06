package org.ares.cloud.merchantInfo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;
import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 商户配送设置 实体
* @version 1.0.0
* @date 2024-11-05
*/
@TableName("merchant_distribution")
@EqualsAndHashCode(callSuper = false)
public class MerchantDistributionEntity extends TenantEntity {
	/**
	* 每公里收费(收费配送)
	*/
	private BigDecimal perKilometer;
	/**
	* 超过公里费用(第三方配送)
	*/
	private BigDecimal excessFees;
	/**
	* 是否开启第三方配送 1-是；2-否
	*/
	private String isThirdParties;
	/**
	* 地址类型: 1-默认商户地址；2-仓库地址；3-港口地址
	*/
	private String addressType;
	/**
	* 地址
	*/
	private String address;
	/**
	* 最低配送金额
	*/
	private BigDecimal minimumDeliveryAmount;
	/**
	* 超出公里不配送
	*/
	private Integer beyondKilometerNotDelivered;
	/**
	* 配送类型 1-免费配送；2-收费配送
	*/
	private String type;
	/**
	* 0-2公里收费(收费配送)
	*/
	private Double stage;
	/**
	* 第三方配送类型 1-全局第三方；2-超出公里
	*/
	private String globalThirdParties;
	/**
	* 超出公里数(第三方配送)
	*/
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
	* 获取是否开启第三方配送 1-是；2-否
	* @return 是否开启第三方配送 1-是；2-否
	*/
	public String getIsThirdParties() {
		return isThirdParties;
	}

	/**
	* 设置是否开启第三方配送 1-是；2-否
	* @param isThirdParties 是否开启第三方配送 1-是；2-否
	*/
	public void setIsThirdParties(String isThirdParties) {
		this.isThirdParties = isThirdParties;
	}
	/**
	* 获取地址类型: 1-默认商户地址；2-仓库地址；3-港口地址
	* @return 地址类型: 1-默认商户地址；2-仓库地址；3-港口地址
	*/
	public String getAddressType() {
		return addressType;
	}

	/**
	* 设置地址类型: 1-默认商户地址；2-仓库地址；3-港口地址
	* @param addressType 地址类型: 1-默认商户地址；2-仓库地址；3-港口地址
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
	* 获取配送类型 1-免费配送；2-收费配送
	* @return 配送类型 1-免费配送；2-收费配送
	*/
	public String getType() {
		return type;
	}

	/**
	* 设置配送类型 1-免费配送；2-收费配送
	* @param type 配送类型 1-免费配送；2-收费配送
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
	* 获取第三方配送类型 1-全局第三方；2-超出公里
	* @return 第三方配送类型 1-全局第三方；2-超出公里
	*/
	public String getGlobalThirdParties() {
		return globalThirdParties;
	}

	/**
	* 设置第三方配送类型 1-全局第三方；2-超出公里
	* @param globalThirdParties 第三方配送类型 1-全局第三方；2-超出公里
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