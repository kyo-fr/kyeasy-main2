package org.ares.cloud.s3.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description s3存储 查询原型
* @version 1.0.0
* @date 2024-10-12
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "s3存储查询")
public class ObjectStorageQuery extends Query {
}