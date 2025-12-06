package org.ares.cloud.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户仓库主数据 数据模型
* @version 1.0.0
* @date 2025-03-22
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户仓库主数据")
public class MerchantWarehouseDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "仓库名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "name")
	@NotBlank(message = "{validation.merchant.merchantWarehouse.name.notBlank}")
	@Size(max = 128, message = "validation.size.max")
	private String name;

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
}