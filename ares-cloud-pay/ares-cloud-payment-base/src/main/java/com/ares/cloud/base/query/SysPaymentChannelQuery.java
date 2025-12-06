package com.ares.cloud.base.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 支付渠道 查询原型
* @version 1.0.0
* @date 2025-05-13
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "支付渠道查询")
public class SysPaymentChannelQuery extends Query {
}