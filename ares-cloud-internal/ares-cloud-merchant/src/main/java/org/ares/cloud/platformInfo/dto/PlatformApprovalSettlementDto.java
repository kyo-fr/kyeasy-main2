package org.ares.cloud.platformInfo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.api.order.commod.PayCommand;
import org.ares.cloud.api.order.commod.PayOrderCommand;

import java.math.BigDecimal;
import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 平台审批 数据模型
* @version 1.0.0
* @date 2024-10-31
*/
@Schema(description = "平台审批")
@Data
public class PlatformApprovalSettlementDto   {
	private static final long serialVersionUID = 1L;


	@Schema(description = "审批编号",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "approvalId")
	@NotBlank(message = "{validation.platformInfo.approval.approvalId.notBlank}")
	private String approvalId;


	@Schema(description = "用户id",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "userId")
	@NotBlank(message = "{validation.platformInfo.approval.userId.notBlank}")
	private String userId;

	/**
	 * 商户ID，用于获取币种和精度信息
	 * <p>
	 * 商户ID是发票关联的商户标识，系统通过该ID获取对应的币种和精度设置，
	 * 确保发票金额计算的准确性。格式通常为字母M开头加上日期和序号。
	 */
	@Schema(description = "商户ID", example = "M202410250001", hidden = true)
	private String merchantId;

	@Schema(description = "订单ID", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("orderId")
	@NotBlank(message = "{validation.orderId.notBlank}")
	private String orderId;

	@Schema(description = "国家代码",hidden = true)
	@JsonProperty(value = "countryCode")
	private String countryCode;

	@Schema(description = "手机号",hidden = true)
	@JsonProperty("userPhone")
	private String userPhone;

	@Schema(description = "支付渠道", requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty("payChannels")
	@NotNull(message = "{validation.payChannels.notNull}")
	private List<PayCommand> payChannels;

	@Schema(description = "订单扣减金额")
	@JsonProperty("orderDeductAmount")
	private BigDecimal orderDeductAmount;

	@Schema(description = "订单扣减原因，可选暂不记录")
	@JsonProperty("orderDeductReason")
	private String orderDeductReason;

}