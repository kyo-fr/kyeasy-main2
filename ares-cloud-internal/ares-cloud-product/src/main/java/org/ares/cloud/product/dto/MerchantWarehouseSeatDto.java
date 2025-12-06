package org.ares.cloud.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 商户仓库位子主数据 数据模型
* @version 1.0.0
* @date 2025-03-22
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户仓库位子主数据")
public class MerchantWarehouseSeatDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "仓库id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "warehouseId")
	@NotBlank(message = "{validation.merchant.merchantWarehouseSeat.warehouseId.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String warehouseId;

	@Schema(description = "位子名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "seatName")
	@NotBlank(message = "{validation.merchant.merchantWarehouseSeat.seatName.notBlank}")
	@Size(max = 128, message = "validation.size.max")
	private String seatName;

	/**
	* 获取仓库id
	* @return 仓库id
	*/
	public String getWarehouseId() {
	return warehouseId;
	}

	/**
	* 设置仓库id
	* @param warehouseId 仓库id
	*/
	public void setWarehouseId(String warehouseId) {
	this.warehouseId = warehouseId;
	}
	/**
	* 获取位子名称
	* @return 位子名称
	*/
	public String getSeatName() {
	return seatName;
	}

	/**
	* 设置位子名称
	* @param seatName 位子名称
	*/
	public void setSeatName(String seatName) {
	this.seatName = seatName;
	}
}