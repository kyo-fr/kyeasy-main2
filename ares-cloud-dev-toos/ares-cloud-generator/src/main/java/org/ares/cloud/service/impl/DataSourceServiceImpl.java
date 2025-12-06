package org.ares.cloud.service.impl;

import org.ares.cloud.config.GenDataSource;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.entity.DataSourceEntity;
import org.ares.cloud.enums.DbType;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.query.GenQuery;
import org.ares.cloud.repository.DataSourceRepository;
import org.ares.cloud.service.DataSourceService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description 数据源服务
 * @date 2024/01/23/23:07
 **/
@Service
@AllArgsConstructor
@Slf4j
public class DataSourceServiceImpl extends BaseServiceImpl<DataSourceRepository, DataSourceEntity> implements DataSourceService {
    private final DataSource dataSource;
    @Override
    public PageResult<DataSourceEntity> page(GenQuery query) {
        IPage<DataSourceEntity> page = baseMapper.selectPage(
                getPage(query),
                getWrapper(query)
        );
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public List<DataSourceEntity> getList() {
        return baseMapper.selectList(Wrappers.emptyWrapper());
    }
    @Override
    public String getDatabaseProductName(Long dataSourceId) {
        if (dataSourceId.intValue() == 0) {
            return DbType.MySQL.name();
        } else {
            return getById(dataSourceId).getDbType();
        }
    }
    @Override
    public GenDataSource get(Long datasourceId) {
        // 初始化配置信息
        GenDataSource info = null;
        if (datasourceId.intValue() == 0) {
            try {
                info = new GenDataSource(dataSource.getConnection());
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        } else {
            info = new GenDataSource(this.getById(datasourceId));
        }

        return info;
    }
    @Override
    public boolean save(DataSourceEntity entity) {
        entity.setCreateTime(new Date());
        return super.save(entity);
    }
}
