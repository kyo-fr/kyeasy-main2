package org.ares.cloud.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description 数据表
 * @date 2024/01/23/22:24
 **/
@Data
@TableName("gen_models")
public class ModelEntity {
    @TableId
    private String id;
    /**
     * 表名
     */
    private String tableName;
    /**
     * 实体类名称
     */
    private String className;
    /**
     * 表的说明
     */
    private String tableComment;
    /**
     * 项目包名
     */
    private String packageName;
    /**
     * 模型类型 1->基础摩西;2-》业务模型
     */
    private Integer modelType;
    /**
     * 项目版本号
     */
    private String version;
    /**
     * 作者
     */
    private String author;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 后端生成路径
     */
    private String backendPath;
    /**
     * 前端生成路径
     */
    private String frontendPath;
    /**
     * 模块名
     */
    private String moduleName;
    /**
     * 功能名
     */
    private String functionName;
    /**
     * 数据源ID
     */
    private Long datasourceId;
    /**
     * 基类ID
     */
    private String baseclassId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 来源类型；0创建，1导入
     */
    private Integer sourceType;
    /**
     * 字段列表
     */
    @TableField(exist = false)
    private List<ModelFieldEntity> fieldList;
}
