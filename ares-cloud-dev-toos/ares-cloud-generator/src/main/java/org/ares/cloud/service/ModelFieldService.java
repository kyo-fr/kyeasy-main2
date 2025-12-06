package org.ares.cloud.service;

import org.ares.cloud.entity.ModelFieldEntity;
import org.ares.cloud.database.service.BaseService;


import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description 模型属性
 * @date 2024/01/23/22:54
 **/
public interface ModelFieldService extends BaseService<ModelFieldEntity> {
    List<ModelFieldEntity> getByModelId(String modelId);

    void deleteBatchTableIds(String[] modelIds);

    /**
     * 修改模型字段数据
     *
     * @param modelId        模型ID
     * @param tableFieldList 字段列模型
     */
    void updateTableField(String modelId, List<ModelFieldEntity> tableFieldList);

    /**
     * 初始化字段数据
     */
    void initFieldList(List<ModelFieldEntity> tableFieldList);
}
