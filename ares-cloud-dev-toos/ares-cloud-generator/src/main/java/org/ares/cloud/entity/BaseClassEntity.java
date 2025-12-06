package org.ares.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author hugo  tangxkwork@163.com
 * @description 基类管理
 * @date 2024/01/23/22:20
 **/
@Data
@TableName("gen_base_class")
public class BaseClassEntity {
    /**
     * id
     */
    @TableId
    private String id;
    /**
     * 基类包名
     */
    private String packageName;
    /**
     * 基类编码
     */
    private String code;
    /**
     * 公共字段，多个用英文逗号分隔
     */
    private String fields;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
}
