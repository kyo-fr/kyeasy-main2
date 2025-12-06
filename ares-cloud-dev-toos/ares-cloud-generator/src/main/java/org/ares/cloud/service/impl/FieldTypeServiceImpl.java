package org.ares.cloud.service.impl;

import cn.hutool.core.util.StrUtil;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.entity.FieldTypeEntity;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.query.GenQuery;
import org.ares.cloud.repository.FieldTypeRepository;
import org.ares.cloud.service.FieldTypeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author hugo  tangxkwork@163.com
 * @description 字段管理实现
 * @date 2024/01/23/23:09
 **/
@Service
@AllArgsConstructor
public class FieldTypeServiceImpl extends BaseServiceImpl<FieldTypeRepository, FieldTypeEntity> implements FieldTypeService {
    @Override
    public PageResult<FieldTypeEntity> page(GenQuery query) {
        IPage<FieldTypeEntity> page = baseMapper.selectPage(
                getPage(query),
                getWrapper(query)
        );
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public Map<String, FieldTypeEntity> getMap() {
        List<FieldTypeEntity> list = baseMapper.selectList(null);
        Map<String, FieldTypeEntity> map = new LinkedHashMap<>(list.size());
        for (FieldTypeEntity entity : list) {
            map.put(entity.getColumnType().toLowerCase(), entity);
        }
        return map;
    }

    @Override
    public Set<String> getPackageByTableId(String modelId) {
        Set<String> importList = baseMapper.getPackageByTableId(modelId);

        return importList.stream().filter(StrUtil::isNotBlank).collect(Collectors.toSet());
    }

    @Override
    public Set<FieldTypeEntity> getAll() {
        List<FieldTypeEntity> fieldTypeEntities = baseMapper.selectList(null);
        if(fieldTypeEntities == null || fieldTypeEntities.isEmpty()){
            return new HashSet<>();
        }
        // 以 columnType 作为 Key
        // 以 field 对象作为 Value
        // 如果有重复的 columnType，保留第一个
        return new HashSet<>(fieldTypeEntities.stream()
                .collect(Collectors.toMap(
                        FieldTypeEntity::getColumnType,  // 以 columnType 作为 Key
                        field -> field,  // 以 field 对象作为 Value
                        (existing, replacement) -> existing)) // 如果有重复的 columnType，保留第一个
                .values());
    }

    @Override
    public Map<String, String> getAttributeMapping() {
        List<FieldTypeEntity> fieldTypeEntities = baseMapper.selectList(null);
        if(fieldTypeEntities == null || fieldTypeEntities.isEmpty()){
            return new HashMap<>();
        }
        Map<String, String> attributeMapping = new LinkedHashMap<>();
        for (FieldTypeEntity entity : fieldTypeEntities) {
            attributeMapping.put(entity.getColumnType(), entity.getAttrType());
        }
        return attributeMapping;
    }

    @Override
    public Set<String> getList() {
        return baseMapper.list();
    }

    @Override
    public boolean save(FieldTypeEntity entity) {
        entity.setCreateTime(new Date());
        return super.save(entity);
    }
}
