package org.ares.cloud.product.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;

/**
* @author hugo tangxkwork@163.com
* @description 商户仓库主数据 数据模型
* @version 1.0.0
* @date 2025-03-22
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户仓库主数据")
public class MerchantWarehouseVo extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "仓库名称")
	@JsonProperty(value = "name")
	private String name;

	@Schema(description = "仓库商品数量")
	@JsonProperty(value = "quantity")
	private Integer inventory;
	/**
	* 获取仓库名称
	* @return 仓库名称
	*/
	public String getName() {
	return name;
	}

	/**
	* 设置仓库名称
	* @param name 仓库名称
	*/
	public void setName(String name) {
	this.name = name;
	}

	/**
	* 获取仓库商品数量
	* @return 仓库商品数量
	*/
	public int getInventory() {
		return inventory;
	}
	/**
	* 设置仓库商品数量
	* @param inventory 仓库商品数量
	*/
	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}
}