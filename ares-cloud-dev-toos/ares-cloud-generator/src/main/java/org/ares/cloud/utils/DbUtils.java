package org.ares.cloud.utils;

import org.ares.cloud.config.GenDataSource;
import org.ares.cloud.enums.DbType;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import oracle.jdbc.driver.OracleConnection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author hugo  tangxkwork@163.com
 * @description DB工具类
 * @date 2024/01/23/22:34
 **/
public class DbUtils {
    private static final int CONNECTION_TIMEOUTS_SECONDS = 6;

    /**
     * 获得数据库连接
     */
    public static Connection getConnection(GenDataSource dataSource) throws ClassNotFoundException, SQLException {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dataSource.getConnUrl());
        config.setUsername(dataSource.getUsername());
        config.setPassword(dataSource.getPassword());
        config.setDriverClassName(dataSource.getDbType().getDriverClass());
        config.setIdleTimeout(CONNECTION_TIMEOUTS_SECONDS);
        // 创建 Hikari 数据源
        HikariDataSource ds = new HikariDataSource(config);
        {
            // 从连接池获取连接
            Connection connection = ds.getConnection();
            if (dataSource.getDbType() == DbType.Oracle) {
                ((OracleConnection) connection).setRemarksReporting(true);
            }
            return connection;
        }
    }
}
