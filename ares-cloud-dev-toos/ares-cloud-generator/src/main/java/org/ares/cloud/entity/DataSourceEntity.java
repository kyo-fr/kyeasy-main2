package org.ares.cloud.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author hugo  tangxkwork@163.com
 * @description 数据源
 * @date 2024/01/23/22:22
 **/
@Data
@TableName("gen_datasource")
public class DataSourceEntity {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 数据库类型
     */
    private String dbType;
    /**
     * 连接名
     */
    private String connName;
    /**
     * URL
     */
    private String connUrl;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建时间
     */
    private Date createTime;
}
