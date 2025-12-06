package org.ares.cloud.platformInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;

/**
* @author hugo tangxkwork@163.com
* @description 货币礼物点统计 查询原型
* @version 1.0.0
* @date 2025-06-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "货币礼物点查询")
public class PlatformInfoGiftPointsQuery extends Query {
}