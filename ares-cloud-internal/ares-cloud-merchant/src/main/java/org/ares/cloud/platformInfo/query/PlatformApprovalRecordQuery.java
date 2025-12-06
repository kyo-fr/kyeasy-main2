package org.ares.cloud.platformInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;

import java.math.BigDecimal;

/**
* @author hugo tangxkwork@163.com
* @description 存储变更明细表 查询原型
* @version 1.0.0
* @date 2025-06-16
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "存储变更明细表查询")
public class PlatformApprovalRecordQuery extends Query {
}