package org.ares.cloud.query;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hugo  tangxkwork@163.com
 * @description 公共查询参数
 * @date 2024/01/23/23:00
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class GenQuery {
    String code;
    String tableName;
    String attrType;
    String columnType;
    String connName;
    String dbType;
    String projectName;

    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    Integer page;

    @NotNull(message = "每页条数不能为空")
    Integer limit;
}
