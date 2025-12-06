package org.ares.cloud.rider.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 骑手 查询原型
* @version 1.0.0
* @date 2025-08-26
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "骑手查询")
public class RiderQuery extends Query {
}