package org.ares.cloud.merchantInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description 商户休假 数据模型
* @version 1.0.0
* @date 2025-01-03
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商户休假")
public class MerchantHoliDayDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "休假描述",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "contents")
	@NotBlank(message = "{validation.merchant.merchantHoLiDay.contents.notBlank}")
	@Size(max = 3000, message = "validation.size.max")
	private String contents;
	/**
	* 获取休假描述
	* @return 休假描述
	*/
	public String getContents() {
	return contents;
	}

	/**
	* 设置休假描述
	* @param contents 休假描述
	*/
	public void setContents(String contents) {
	this.contents = contents;
	}
}