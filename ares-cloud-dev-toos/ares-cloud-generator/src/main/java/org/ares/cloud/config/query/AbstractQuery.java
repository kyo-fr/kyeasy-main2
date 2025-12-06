package org.ares.cloud.config.query;

import org.ares.cloud.enums.DbType;

/**
 * @author hugo  tangxkwork@163.com
 * @description 查询接口
 * @date 2024/01/23/22:37
 **/
public interface AbstractQuery {
    /**
     * 数据库类型
     */
    DbType dbType();

    /**
     * 表信息查询 SQL
     */
    String tableSql(String tableName);

    /**
     * 表名称
     */
    String tableName();

    /**
     * 表注释
     */
    String tableComment();

    /**
     * 表字段信息查询 SQL
     */
    String tableFieldsSql();

    /**
     * 字段名称
     */
    String fieldName();

    /**
     * 字段类型
     */
    String fieldType();

    /**
     * 字段注释
     */
    String fieldComment();

    /**
     * 主键字段
     */
    String fieldKey();
}
