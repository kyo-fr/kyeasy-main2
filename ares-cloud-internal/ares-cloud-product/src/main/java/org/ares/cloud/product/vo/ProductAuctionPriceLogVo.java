package org.ares.cloud.product.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.BaseDto;
import org.ares.cloud.common.dto.TenantDto;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 拍卖商品竞价记录 数据模型
* @version 1.0.0
* @date 2025-09-23
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "拍卖商品竞价记录")
@Data
public class ProductAuctionPriceLogVo extends BaseDto {
	private static final long serialVersionUID = 1L;

	@Schema(description = "租户id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "tenantId")
	private String tenantId;

	@Schema(description = "用户id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "userId")
	private String userId;

	@Schema(description = "用户名称",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "nickName")
	private String nickName;

	@Schema(description = "每次竞拍价格",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "price")
	private BigDecimal price;

	@Schema(description = "商品id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "productId")
	private String productId;


}