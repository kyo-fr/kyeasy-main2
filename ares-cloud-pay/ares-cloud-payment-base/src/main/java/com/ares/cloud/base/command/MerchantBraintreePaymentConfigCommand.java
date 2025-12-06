package com.ares.cloud.base.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/5/13 02:15
 */
@Data
@Schema(description = "商户Braintree支付配置")
public class MerchantBraintreePaymentConfigCommand {
    @Schema(description = "商户id(可以穿优先从登录信息获取)")
    private String merchantId;

    @Schema(description = "braintree商户id", requiredMode = Schema.RequiredMode.REQUIRED)
    private  String braintreeMerchantId;

    @Schema(description = "braintree商户公钥", requiredMode = Schema.RequiredMode.REQUIRED)
    private  String  braintreePublicKey;

    @Schema(description = "braintree商户私钥", requiredMode = Schema.RequiredMode.REQUIRED)
    private  String  braintreePrivateKey;
}
