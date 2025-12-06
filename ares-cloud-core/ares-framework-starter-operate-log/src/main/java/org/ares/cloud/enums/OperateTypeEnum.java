package org.ares.cloud.enums;

/**
 * @author hugo
 * @version 1.0
 * @description: 操作类型
 * @date 2024/9/29 15:23
 */
public enum OperateTypeEnum {
    /**
     * 查询
     */
    GET(1),
    /**
     * 新增
     */
    INSERT(2),
    /**
     * 修改
     */
    UPDATE(3),
    /**
     * 删除
     */
    DELETE(4),
    /**
     * 导出
     */
    EXPORT(5),
    /**
     * 导入
     */
    IMPORT(6),
    /**
     * 其它
     */
    OTHER(0);

    private final int value;

    OperateTypeEnum(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
