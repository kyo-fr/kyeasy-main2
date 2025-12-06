package org.ares.cloud.platformInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 订阅基础信息 查询原型
* @version 1.0.0
* @date 2024-10-31
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "订阅基础信息查询")
public class PlatformSubscribeQuery extends Query {
}