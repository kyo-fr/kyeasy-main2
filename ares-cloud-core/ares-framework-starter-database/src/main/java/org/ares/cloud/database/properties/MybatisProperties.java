package org.ares.cloud.database.properties;

import lombok.Data;
import org.ares.cloud.common.constant.interfaces.AresConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description mybatis配置
 * @date 2024/01/17/23:35
 **/
@Data
@ConfigurationProperties(prefix = MybatisProperties.PREFIX)
public class MybatisProperties {
    /**
     * 配置前缀
     */
    public static final String PREFIX = AresConstant.PROPERTIES_PREFIX+".mybatis";
    /**
     * 是否打印可执行 sql
     */
    private boolean sql = true;
    /**
     * 忽略的租户的表名
     */
    private List<String> ignore_tenant_table = new ArrayList<>();
}
