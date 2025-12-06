package org.ares.cloud.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author hugo  tangxkwork@163.com
 * @description 布局枚举
 * @date 2024/01/23/23:38
 **/
@Getter
@AllArgsConstructor
public enum ModelTypeEnum {
    BASE(1), //基础模型
    BUSINESS(2); //业务模型

    private final Integer value;
}
