package org.ares.cloud.service;

import org.ares.cloud.entity.FieldTypeEntity;

import java.util.Map;
import java.util.Set;

/**
 * @author hugo  tangxkwork@163.com
 * @description 字段类型管理
 * @date 2024/01/23/22:52
 **/
public interface FieldTypeService extends BsService<FieldTypeEntity> {


    Map<String, FieldTypeEntity> getMap();

    /**
     * 根据modelId，获取包列表
     *
     * @param modelId 表ID
     * @return 返回包列表
     */
    Set<String> getPackageByTableId(String modelId);
    /**
     * 获取所有的 字段类型
     * @return 字段类型
     */
    Set<String> getList();
    /**
     * 获取所有的 字段类型
     * @return 字段类型
     */
    Set<FieldTypeEntity> getAll();

    /**
     * 获取属性映射
     * @return 属性映射
     */
    Map<String,String> getAttributeMapping();
}
