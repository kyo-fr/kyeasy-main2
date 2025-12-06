package org.ares.cloud.platformInfo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.ares.cloud.common.dto.TenantDto;

/**
* @author hugo platformInfo
* @description 商品标注 数据模型
* @version 1.0.0
* @date 2024-11-04
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "商品标注")
public class PlatformMarkingDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "标注名称", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "markingName")
	@NotBlank(message = "{validation.platform.marking.markingName.notBlank}")
	@Size(max = 32, message = "validation.size.max")
	private String markingName;
	/**
	 * 获取标注名称
	 *
	 * @return 标注名称
	 */
	public String getMarkingName() {
	return markingName;
	}

	/**
	* 设置标注名称
	* @param markingName 标注名称
	*/
	public void setMarkingName(String markingName) {
	this.markingName = markingName;
	}
}