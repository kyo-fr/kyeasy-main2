package com.ares.cloud.order.application.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.PageQuery;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/3/23 00:51
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "查询当日有效订单")
public class CommonOrdersQuery extends PageQuery {

    @Schema(description = "商户ID")
    private String merchantId;
}
