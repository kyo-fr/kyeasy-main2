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
public enum FormLayoutEnum {
    ONE(1),
    TWO(2);

    private final Integer value;
}
