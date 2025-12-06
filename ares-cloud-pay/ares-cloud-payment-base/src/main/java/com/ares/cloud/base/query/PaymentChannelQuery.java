package com.ares.cloud.base.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 支付商户 查询原型
* @version 1.0.0
* @date 2024-10-23
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "支付商户查询")
public class PaymentChannelQuery  {
    @Schema(description = "渠道类型;1:线上；2:线下")
    private Integer channelType;
}