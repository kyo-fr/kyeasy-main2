package org.ares.cloud.annotations;

import org.ares.cloud.enums.OperateTypeEnum;

/**
 * @author hugo
 * @version 1.0
 * @description: 操作日志
 * @date 2024/9/29 15:22
 */
public @interface OperateLog {
    /**
     * 模块名
     */
    String module() default "";

    /**
     * 操作名
     */
    String name() default "";

    /**
     * 操作类型
     */
    OperateTypeEnum[] type() default OperateTypeEnum.OTHER;
}
