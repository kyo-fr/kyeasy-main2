package org.ares.cloud.platformInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo platformInfo
* @description 商品标注 查询原型
* @version 1.0.0
* @date 2024-11-04
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "商品标注查询")
public class PlatformMarkingQuery extends Query {
}