package org.ares.cloud.service;

import org.ares.cloud.config.GenDataSource;
import org.ares.cloud.entity.DataSourceEntity;

import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description 数据源
 * @date 2024/01/23/22:50
 **/
public interface DataSourceService extends BsService<DataSourceEntity> {

    List<DataSourceEntity> getList();

    /**
     * 获取数据库产品名，如：MySQL
     *
     * @param datasourceId 数据源ID
     * @return 返回产品名
     */
    String getDatabaseProductName(Long datasourceId);

    /**
     * 根据数据源ID，获取数据源
     *
     * @param datasourceId 数据源ID
     */
    GenDataSource get(Long datasourceId);
}
