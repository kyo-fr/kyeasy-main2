package org.ares.cloud.database.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.utils.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author hugo  tangxkwork@163.com
 * @description 自动填充字段
 * @date 2024/01/17/15:58
 **/

public class FieldMetaHandler implements MetaObjectHandler {
    public final static String CREATE_TIME = "createTime";
    public final static String CREATOR = "creator";
    public final static String UPDATE_TIME = "updateTime";
    private final static String UPDATER = "updater";
    public final static String ORG_ID = "orgId";
    private final static String VERSION = "version";
    private final static String DELETED = "deleted";
    private final static String TENANT_ID = "tenantId";
    @Override
    public void insertFill(MetaObject metaObject) {
        long date = DateUtils.getCurrentTimestampInUTC();

        // 用户字段填充：创建者
        setFieldValByName(CREATOR,
                ApplicationContext.getUserId() != null ? ApplicationContext.getUserId() : "",
                metaObject);

        // 租户处理
        if (!ApplicationContext.getIgnoreTenant()) {
            Object tenantIdField = getFieldValByName(TENANT_ID, metaObject);

            boolean hasValue = tenantIdField != null
                    && !(tenantIdField instanceof String && ((String) tenantIdField).trim().isEmpty());

            if (!hasValue) {
                // 实体没有值时，从上下文取
                String tenantId = ApplicationContext.getTenantId();
                if (StringUtils.hasText(tenantId)) {
                    setFieldValByName(TENANT_ID, tenantId, metaObject);
                }
            }
        }

        // 创建时间
        setFieldValByName(CREATE_TIME, date, metaObject);
        // 版本号
        setFieldValByName(VERSION, 1, metaObject);
        // 删除标识
        setFieldValByName(DELETED, 0, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        long date = DateUtils.getCurrentTimestampInUTC();
        // 更新者
        setFieldValByName(UPDATER, ApplicationContext.getUserId() != null ? ApplicationContext.getUserId():"", metaObject);
        // 更新时间
        setFieldValByName(UPDATE_TIME, date, metaObject);
    }
}
