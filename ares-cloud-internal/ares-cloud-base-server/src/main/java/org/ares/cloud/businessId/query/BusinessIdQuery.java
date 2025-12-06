package org.ares.cloud.businessId.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 系统业务id 查询原型
* @version 1.0.0
* @date 2024-10-13
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "系统业务id查询")
public class BusinessIdQuery extends Query {
}