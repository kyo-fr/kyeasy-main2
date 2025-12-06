package org.ares.cloud.service.impl;

import org.ares.cloud.entity.FieldTypeEntity;
import org.ares.cloud.entity.ModelFieldEntity;
import org.ares.cloud.enums.AutoFillEnum;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.repository.ModelFieldRepository;
import org.ares.cloud.service.FieldTypeService;
import org.ares.cloud.service.ModelFieldService;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author hugo  tangxkwork@163.com
 * @description 表字段
 * @date 2024/01/23/23:25
 **/
@Service
@AllArgsConstructor
public class ModelFieldServiceImpl extends BaseServiceImpl<ModelFieldRepository, ModelFieldEntity> implements ModelFieldService {
    private final FieldTypeService fieldTypeService;

    @Override
    public List<ModelFieldEntity> getByModelId(String modelId) {
        return baseMapper.getByTableId(modelId);
    }

    @Override
    public void deleteBatchTableIds(String[] modelIds) {
        baseMapper.deleteBatchTableIds(modelIds);
    }

    @Override
    public void updateTableField(String modelId, List<ModelFieldEntity> tableFieldList) {
        // 更新字段数据
        int sort = 0;
        for (ModelFieldEntity tableField : tableFieldList) {
            tableField.setSort(sort++);
            this.updateById(tableField);
        }
    }

    public void initFieldList(List<ModelFieldEntity> tableFieldList) {
        // 字段类型、属性类型映射
        Map<String, FieldTypeEntity> fieldTypeMap = fieldTypeService.getMap();
        int index = 0;
        for (ModelFieldEntity field : tableFieldList) {
            field.setAttrName(StringUtils.underlineToCamel(field.getFieldName()));
            // 获取字段对应的类型
            FieldTypeEntity fieldTypeMapping = fieldTypeMap.get(field.getFieldType().toLowerCase());
            if (fieldTypeMapping == null) {
                // 没找到对应的类型，则为Object类型
                field.setAttrType("Object");
            } else {
                field.setAttrType(fieldTypeMapping.getAttrType());
                field.setPackageName(fieldTypeMapping.getPackageName());
            }

            field.setAutoFill(AutoFillEnum.DEFAULT.name());
            field.setSort(index++);
        }
    }

}
