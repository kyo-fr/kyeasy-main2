package org.ares.cloud.database.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ares.cloud.common.constant.interfaces.Constant;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.query.PageQuery;
import org.ares.cloud.database.entity.BaseEntity;
import org.ares.cloud.database.handler.FieldMetaHandler;
import org.ares.cloud.database.interceptor.DataScope;
import org.ares.cloud.database.service.BaseService;
import org.ares.cloud.common.query.Query;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description 基础服务类
 * @date 2024/01/17/18:10
 **/
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T>  {
    public final static String CREATE_TIME = "create_time";
    public final static String ID = "id";
    public final static String UPDATE_TIME = "update_time";

    private final Class<T> entityClass;

    // Constructor to initialize the entity class dynamically
    public BaseServiceImpl() {
        // Use reflection to obtain the entity type
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[1]; // 1 is the index for T
    }

    // Method to get the entity class type
    public Class<T> getEntityClass() {
        return this.entityClass;
    }

    /**
     * 获取分页对象
     *
     * @param query 分页参数
     */
    protected IPage<T> getPage(PageQuery query) {
        return new Page<>(query.getPage(), query.getLimit());
    }

    /**
     *  获取查询条件
     * @param query 查询参数
     * @return 查询条件
     */
    public LambdaQueryWrapper<T> getWrapper(Query query) {
        LambdaQueryWrapper<T> wrapper = new LambdaQueryWrapper<>();
        // 动态处理父类字段
//        if (isSubclassOfBaseEntity()) {
//            wrapper.apply("deleted is NOE", 0);
//        }
        if (StringUtils.isNotBlank(query.getOrder())) {
            if (query.isAsc()) {
                wrapper.last("ORDER BY " + query.getOrder());
            } else {
                wrapper.last("ORDER BY " + query.getOrder() + " DESC");
            }
        } else if (isSubclassOfBaseEntity()) {
            // 默认按更新时间和创建时间倒序排序
            wrapper.last("ORDER BY update_time DESC NULLS LAST, create_time DESC NULLS LAST");
        }
        return wrapper;
    }

    /**
     *  判断是否是子类
     * @return 返回是否是子类
     */
    public boolean isSubclassOfBaseEntity() {
        return BaseEntity.class.isAssignableFrom(entityClass);
    }

    /**
     * MyBatis-Plus 数据权限
     */
    protected void dataScopeWrapper(LambdaQueryWrapper<T> queryWrapper) {
        DataScope dataScope = getDataScope(null, null);
        if (dataScope != null) {
            queryWrapper.apply(dataScope.getSqlFilter());
        }
    }

    /**
     * 原生SQL 数据权限
     *
     * @param tableAlias 表别名，多表关联时，需要填写表别名
     * @param orgIdAlias 机构ID别名，null：表示org_id
     * @return 返回数据权限
     */
    protected DataScope getDataScope(String tableAlias, String orgIdAlias) {
        List<String> roleScopeList = ApplicationContext.getRoleScopeList();
        // 如果是超级管理员，则不进行数据过滤
        if (roleScopeList != null && roleScopeList.contains(Constant.SUPER_ADMIN)) {
            return null;
        }
        // 如果为null，则设置成空字符串
        if (tableAlias == null) {
            tableAlias = "";
        }

        // 获取表的别名
        if (StringUtils.isNotBlank(tableAlias)) {
            tableAlias += ".";
        }

        StringBuilder sqlFilter = new StringBuilder();
        sqlFilter.append(" (");

        // 数据权限范围
        List<Long> dataScopeList = ApplicationContext.getDataScopeList();
        // 全部数据权限
        if (dataScopeList == null) {
            return null;
        }
        // 数据过滤
        if (!dataScopeList.isEmpty()) {
            if (StringUtils.isBlank(orgIdAlias)) {
                orgIdAlias = FieldMetaHandler.ORG_ID;
            }
            sqlFilter.append(tableAlias).append(orgIdAlias);

            sqlFilter.append(" in(").append(StrUtil.join(",", dataScopeList)).append(")");

            sqlFilter.append(" or ");
        }

        // 查询本人数据
        sqlFilter.append(tableAlias).append(FieldMetaHandler.CREATOR).append("=").append(ApplicationContext.getUserId());

        sqlFilter.append(")");

        return new DataScope(sqlFilter.toString());
    }

}
