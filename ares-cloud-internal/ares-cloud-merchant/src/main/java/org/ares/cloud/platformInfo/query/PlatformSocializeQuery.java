package org.ares.cloud.platformInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 平台海外社交 查询原型
* @version 1.0.0
* @date 2024-10-15
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "平台海外社交查询")
public class PlatformSocializeQuery extends Query {
}