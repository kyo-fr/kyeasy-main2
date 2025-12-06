package com.ares.cloud.base.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道 查询原型
* @version 1.0.0
* @date 2025-05-13
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "商户支付渠道查询")
public class MerchantPaymentChannelQuery  {

    @Schema(description = "渠道类型;1:线上；2:线下")
    private Integer channelType;

    @Schema(description = "商户id(不传从登录信息上获取商户)")
    private String merchantId;
}