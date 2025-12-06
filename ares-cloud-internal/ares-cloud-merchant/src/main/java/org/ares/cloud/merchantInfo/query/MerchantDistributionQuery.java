package org.ares.cloud.merchantInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 商户配送设置 查询原型
* @version 1.0.0
* @date 2024-11-05
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "商户配送查询")
public class MerchantDistributionQuery extends Query {
}