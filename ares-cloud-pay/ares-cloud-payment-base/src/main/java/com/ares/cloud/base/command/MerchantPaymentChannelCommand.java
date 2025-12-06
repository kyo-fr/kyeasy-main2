package com.ares.cloud.base.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/5/13 01:52
 */
@Data
@Schema(description = "商户支付渠道保存")
public class MerchantPaymentChannelCommand implements Serializable {

    @Schema(description = "商户id(可以穿优先从登录信息获取)")
    private String merchantId;
    @Schema(description = "开通的线上支付key列表")
    private String[] onlinePaymentKeys;
    @Schema(description = "开通的线下支付key列表")
    private String[] offlinePaymentKeys;
}
