package org.ares.cloud.service;

import org.ares.cloud.entity.ModelEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description 表服务
 * @date 2024/01/23/22:55
 **/
public interface ModelService extends BsService<ModelEntity> {
    /**
     * 新增
     * @param model 数据
     * @return 保存后的完整数据
     */
    ModelEntity   add(ModelEntity model);
    ModelEntity getByTableName(String tableName);

    void deleteBatchIds(String[] ids);

    /**
     * 导入表
     *
     * @param datasourceId 数据源ID
     * @param tableName    表名
     */
    void tableImport(Long datasourceId, String tableName);

    /**
     * 同步数据库表
     *
     * @param id 表ID
     */
    void sync(String id);

    /**
     * 获取所有基础模型
     * @return 模型集合
     */
    List<ModelEntity> getAllBaseModel();

    /**
     * 导入基础属性的模型
     * @param model 模型ID
     * @param baseId 基础属性模型ID
     */
    void importBaseModelFiled(String model,String baseId);
}
