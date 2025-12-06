package org.ares.cloud.service.impl;

import cn.hutool.core.text.NamingCase;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.config.GenDataSource;
import org.ares.cloud.config.template.GeneratorConfig;
import org.ares.cloud.config.template.GeneratorInfo;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.entity.ModelEntity;
import org.ares.cloud.entity.ModelFieldEntity;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.enums.ModelTypeEnum;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.query.GenQuery;
import org.ares.cloud.repository.ModelRepository;
import org.ares.cloud.service.DataSourceService;
import org.ares.cloud.service.ModelFieldService;
import org.ares.cloud.service.ModelService;
import org.ares.cloud.utils.GenUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author hugo  tangxkwork@163.com
 * @description 表服务
 * @date 2024/01/23/23:33
 **/
@Service
@AllArgsConstructor
public class ModelServiceImpl extends BaseServiceImpl<ModelRepository, ModelEntity> implements ModelService {

    private final ModelFieldService modelFieldService;
    private final DataSourceService dataSourceService;
    private final GeneratorConfig generatorConfig;

    @Override
    public PageResult<ModelEntity> page(GenQuery query) {
        IPage<ModelEntity> page = baseMapper.selectPage(
                getPage(query),
                getWrapper(query)
        );
        return new PageResult<>(page.getRecords(), page.getTotal());
    }


    @Override
    public QueryWrapper<ModelEntity> getWrapper(GenQuery query) {
        QueryWrapper<ModelEntity> wrapper = ModelService.super.getWrapper(query);
       // wrapper.eq("model_type",2);
        return wrapper;
    }

    @Override
    public ModelEntity add(ModelEntity model) {
        // 代码生成器信息
        GeneratorInfo generator = generatorConfig.getGeneratorConfig();
        // 保存表信息
        model.setPackageName(generator.getProject().getPackageName());
        model.setVersion(generator.getProject().getVersion());
        model.setBackendPath(generator.getProject().getBackendPath());
        model.setFrontendPath(generator.getProject().getFrontendPath());
        model.setAuthor(generator.getDeveloper().getAuthor());
        model.setEmail(generator.getDeveloper().getEmail());
        if(StringUtils.isNoneEmpty(model.getClassName())){
            model.setClassName(model.getClassName());
        }else{
            model.setClassName(NamingCase.toCamelCase(model.getTableName()));
        }
        model.setTableName(model.getTableName());
        model.setModuleName(generator.getProject().getModuleName());
        model.setFunctionName(GenUtils.getFunctionName(model.getTableName()));
        model.setTableComment(model.getTableComment());
        model.setCreateTime(new Date());
        model.setSourceType(0);
        model.setModelType(model.getModelType());
        this.save(model);
        return model;
    }

    @Override
    public ModelEntity getByTableName(String tableName) {
        LambdaQueryWrapper<ModelEntity> queryWrapper = Wrappers.lambdaQuery();
        return baseMapper.selectOne(queryWrapper.eq(ModelEntity::getTableName, tableName));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBatchIds(String[] ids) {
        // 删除表
        baseMapper.deleteBatchIds(Arrays.asList(ids));

        // 删除列
        modelFieldService.deleteBatchTableIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void tableImport(Long datasourceId, String tableName) {
        // 初始化配置信息
        GenDataSource dataSource = dataSourceService.get(datasourceId);
        // 查询表是否存在
        ModelEntity model = this.getByTableName(tableName);
        // 表存在
        if (model != null) {
            throw new BusinessException(tableName + "已存在");
        }

        // 从数据库获取表信息
        model = GenUtils.getTable(dataSource, tableName);

        // 代码生成器信息
        GeneratorInfo generator = generatorConfig.getGeneratorConfig();

        // 保存表信息
        model.setPackageName(generator.getProject().getPackageName());
        model.setVersion(generator.getProject().getVersion());
        model.setBackendPath(generator.getProject().getBackendPath());
        model.setFrontendPath(generator.getProject().getFrontendPath());
        model.setAuthor(generator.getDeveloper().getAuthor());
        model.setEmail(generator.getDeveloper().getEmail());
        model.setClassName(NamingCase.toPascalCase(tableName));
        model.setModuleName(generator.getProject().getModuleName());
        model.setFunctionName(GenUtils.getFunctionName(tableName));
        model.setCreateTime(new Date());
        model.setSourceType(1);
        model.setModelType(ModelTypeEnum.BUSINESS.getValue());
        this.save(model);

        // 获取原生字段数据
        List<ModelFieldEntity> tableFieldList = GenUtils.getTableFieldList(dataSource, model.getId(), model.getTableName());
        // 初始化字段数据
        modelFieldService.initFieldList(tableFieldList);

        // 保存列数据
        tableFieldList.forEach(modelFieldService::save);

        try {
            //释放数据源
            dataSource.getConnection().close();
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sync(String id) {
        ModelEntity model = this.getById(id);

        // 初始化配置信息
        GenDataSource datasource = dataSourceService.get(model.getDatasourceId());
        try {

            // 从数据库获取表字段列表
            List<ModelFieldEntity> dbTableFieldList = GenUtils.getTableFieldList(datasource, model.getId(), model.getTableName());
            if (dbTableFieldList.isEmpty()) {
                throw new BusinessException("同步失败，请检查数据库表：" + model.getTableName());
            }

            List<String> dbTableFieldNameList = dbTableFieldList.stream().map(ModelFieldEntity::getFieldName).toList();

            // 表字段列表
            List<ModelFieldEntity> modelFieldList = modelFieldService.getByModelId(id);

            Map<String, ModelFieldEntity> modelFieldMap = modelFieldList.stream().collect(Collectors.toMap(ModelFieldEntity::getFieldName, Function.identity()));

            // 初始化字段数据
            modelFieldService.initFieldList(dbTableFieldList);

            // 同步表结构字段
            dbTableFieldList.forEach(field -> {
                // 新增字段
                if (!modelFieldMap.containsKey(field.getFieldName())) {
                    modelFieldService.save(field);
                    return;
                }

                // 修改字段
                ModelFieldEntity updateField = modelFieldMap.get(field.getFieldName());
                updateField.setPrimaryPk(field.isPrimaryPk());
                updateField.setFieldComment(field.getFieldComment());
                updateField.setFieldType(field.getFieldType());
                updateField.setAttrType(field.getAttrType());

                modelFieldService.updateById(updateField);
            });

            // 删除数据库表中没有的字段
            List<ModelFieldEntity> delFieldList = modelFieldList.stream().filter(field -> !dbTableFieldNameList.contains(field.getFieldName())).toList();
            if (!delFieldList.isEmpty()) {
                List<String> fieldIds = delFieldList.stream().map(ModelFieldEntity::getId).collect(Collectors.toList());
                modelFieldService.removeBatchByIds(fieldIds);
            }
        }catch (Exception e){
            //释放资源
            if( datasource != null){
               try {
                   datasource.getConnection().close();
               }catch (Exception e1){
                   log.error("clos datasource connection err"+e1.getMessage());
               }
            }
        }
    }

    @Override
    public List<ModelEntity> getAllBaseModel() {
        QueryWrapper<ModelEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("model_type", 1);
        return this.list(wrapper);
    }

    @Override
    public void importBaseModelFiled(String model, String baseId) {
        //新模型
        ModelEntity model1 = this.baseMapper.selectById(baseId);
        if (model1 == null) {
            throw  new RuntimeException("模型不存在");
        }
        List<ModelFieldEntity> fieldEntities = this.modelFieldService.getByModelId(baseId);
        if (fieldEntities == null || fieldEntities.isEmpty()) {
            return;
        }
        for (ModelFieldEntity modelFieldEntity : fieldEntities) {
            modelFieldEntity.setModelId(model);
            modelFieldEntity.setId(null);
        }
        // 保存列数据
        fieldEntities.forEach(modelFieldService::save);
    }
}
