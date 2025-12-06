package org.ares.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author hugo  tangxkwork@163.com
 * @description 字段类型管理
 * @date 2024/01/23/22:23
 **/
@Data
@TableName("gen_field_type")
public class FieldTypeEntity {
    /**
     * id
     */
    @TableId
    private String id;
    /**
     * 字段类型
     */
    private String columnType;
    /**
     * 属性类型
     */
    private String attrType;
    /**
     * 属性包名
     */
    private String packageName;
    /**
     * 创建时间
     */
    private Date createTime;
}
