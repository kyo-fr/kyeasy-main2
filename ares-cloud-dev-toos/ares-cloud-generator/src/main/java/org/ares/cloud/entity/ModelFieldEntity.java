package org.ares.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * @author hugo  tangxkwork@163.com
 * @description 表字段
 * @date 2024/01/23/22:24
 **/
@Data
@TableName("gen_model_field")
public class ModelFieldEntity {
    @TableId
    private String id;
    /**
     * 模型id
     */
    private String modelId;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 字段类型
     */
    private String fieldType;
    /**
     * 字段说明
     */
    private String fieldComment;
    /**
     * 属性名
     */
    private String attrName;
    /**
     * 属性类型
     */
    private String attrType;
    /**
     * 属性包名
     */
    private String packageName;
    /**
     * 自动填充
     */
    private String autoFill;
    /**
     * 主键 0：否  1：是
     */
    private boolean primaryPk;
    /**
     * 基类字段 0：否  1：是
     */
    private boolean baseField;
    /**
     * 长度
     */
    private String length;
    /**
     * 小数点位数
     */
    private Integer point;
    /**
     * 是否能够为空1、可以；2、不能
     */
    private Integer isNull;
    /**
     * 默认值
     */
    private String defValue;
}
