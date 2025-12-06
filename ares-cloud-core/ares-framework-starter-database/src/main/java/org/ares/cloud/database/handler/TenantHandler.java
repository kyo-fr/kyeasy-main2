package org.ares.cloud.database.handler;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.database.properties.MybatisProperties;
import org.springframework.util.StringUtils;

/**
 * @author hugo  tangxkwork@163.com
 * @description 租户处理器
 * @date 2024/01/17/23:16
 **/
public class TenantHandler implements TenantLineHandler {
    private final MybatisProperties properties;

    public TenantHandler(MybatisProperties properties) {
        this.properties = properties;
    }

    /**
     * 获取租户id
     * @return 租户id
     */
    @Override
    public Expression getTenantId() {
        if (ApplicationContext.getTenantId() == null){
            return new LongValue(0L);
        }
        return new LongValue(ApplicationContext.getTenantId());
    }

    /**
     * 判断是否忽略
     * @param tableName 表名
     * @return 是否忽略
     */
    @Override
    public boolean ignoreTable(String tableName) {
        //上下文中指定忽略, 或者租户id为空
        if (ApplicationContext.getIgnoreTenant() || !StringUtils.hasText(ApplicationContext.getTenantId())){
            return true;
        }
        if (properties.getIgnore_tenant_table() == null || properties.getIgnore_tenant_table().isEmpty()){
            return true;
        }
        return properties.getIgnore_tenant_table().stream().allMatch(p -> p.equals(tableName));
    }
}
