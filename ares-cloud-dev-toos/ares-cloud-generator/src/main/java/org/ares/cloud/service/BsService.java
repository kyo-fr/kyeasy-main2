package org.ares.cloud.service;

import cn.hutool.core.util.StrUtil;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.database.service.BaseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.ares.cloud.query.GenQuery;

/**
 * @author hugo  tangxkwork@163.com
 * @description 基础服务
 * @date 2024/01/23/23:02
 **/
public interface BsService<T> extends BaseService<T> {
    PageResult<T> page(GenQuery query);

    /**
     * 获取分页对象
     *
     * @param query 分页参数
     */
    default IPage<T> getPage(GenQuery query) {
        Page<T> page = new Page<>(query.getPage(), query.getLimit());
        page.addOrder(OrderItem.desc("id"));
        return page;
    }

    default QueryWrapper<T> getWrapper(GenQuery query) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapper.like(StrUtil.isNotBlank(query.getCode()), "code", query.getCode());
        wrapper.like(StrUtil.isNotBlank(query.getTableName()), "table_name", query.getTableName());
        wrapper.like(StrUtil.isNotBlank(query.getAttrType()), "attr_type", query.getAttrType());
        wrapper.like(StrUtil.isNotBlank(query.getColumnType()), "column_type", query.getColumnType());
        wrapper.like(StrUtil.isNotBlank(query.getConnName()), "conn_name", query.getConnName());
        wrapper.eq(StrUtil.isNotBlank(query.getDbType()), "db_type", query.getDbType());
        wrapper.like(StrUtil.isNotBlank(query.getProjectName()), "project_name", query.getProjectName());
        return wrapper;
    }

}
