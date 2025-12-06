package org.ares.cloud.database.interceptor;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author hugo  tangxkwork@163.com
 * @description
 * @date 2024/01/17/16:00
 **/
@Data
@AllArgsConstructor
public class DataScope {
    private String sqlFilter;
}
