package org.ares.cloud.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.config.template.*;
import org.ares.cloud.dto.CodeGenerator;
import org.ares.cloud.entity.BaseClassEntity;
import org.ares.cloud.entity.ModelEntity;
import org.ares.cloud.entity.ModelFieldEntity;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.enums.DbType;
import org.ares.cloud.service.*;
import org.ares.cloud.common.utils.DateUtils;
import org.ares.cloud.utils.TemplateUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author hugo  tangxkwork@163.com
 * @description 代码生成服务
 * @date 2024/01/23/23:40
 **/
@Service
@Slf4j
@AllArgsConstructor
public class GeneratorServiceImpl implements GeneratorService {
    private final DataSourceService datasourceService;
    private final FieldTypeService fieldTypeService;
    private final BaseClassService baseClassService;
    private final GeneratorConfig generatorConfig;
    private final ModelService tableService;
    private final ModelFieldService tableFieldService;
    @Override
    public void downloadCode(String modelId, ZipOutputStream zip) {
        // 数据模型
        Map<String, Object> dataModel = getDataModel(modelId);

        // 代码生成器信息
        GeneratorInfo generator = generatorConfig.getGeneratorConfig();

        // 渲染模板并输出
        for (TemplateInfo template : generator.getTemplates()) {
            dataModel.put("templateName", template.getTemplateName());
            String content = TemplateUtils.getContent(template.getTemplateContent(), dataModel);
            String path = TemplateUtils.getContent(template.getGeneratorPath(), dataModel);

            try {
                // 添加到zip
                zip.putNextEntry(new ZipEntry(path));
                IoUtil.writeUtf8(zip, false, content);
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                log.error(e.getLocalizedMessage());
                throw new BusinessException("模板写入失败：" + path);
            }
        }
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generatorCode(String modelId) {
        // 数据模型
        Map<String, Object> dataModel = getDataModel(modelId);

        // 代码生成器信息
        GeneratorInfo generator = generatorConfig.getGeneratorConfig();

        // 渲染模板并输出
        for (TemplateInfo template : generator.getTemplates()) {
            dataModel.put("templateName", template.getTemplateName());
            String content = TemplateUtils.getContent(template.getTemplateContent(), dataModel);
            String path = TemplateUtils.getContent(template.getGeneratorPath(), dataModel);

            FileUtil.writeUtf8String(content, path);
        }
    }
    @Override
    public void modelDownloadCode(CodeGenerator req, ZipOutputStream zip) {
        // 数据模型
        Map<String, Object> dataModel = getDataModelByCodeGenerator(req);

        // 代码生成器信息
        GeneratorInfo generator = getGenerator(req);
        //获取选中的模版名称
        List<String> templateNames = req.getTemplateNames();
        if (CollectionUtil.isEmpty(templateNames)) {
            throw new BusinessException("未选择要生成的代码模版");
        }
        // 渲染模板并输出
        for (TemplateInfo template : generator.getTemplates()) {
           if (templateNames.contains(template.getTemplateName())){
               dataModel.put("templateName", template.getTemplateName());
               String content = TemplateUtils.getContent(template.getTemplateContent(), dataModel);
               String path = TemplateUtils.getContent(template.getGeneratorPath(), dataModel);

               try {
                   // 添加到zip
                   zip.putNextEntry(new ZipEntry(path));
                   IoUtil.writeUtf8(zip, false, content);
                   zip.flush();
                   zip.closeEntry();
               } catch (IOException e) {
                   log.error(e.getLocalizedMessage());
                   throw new BusinessException("模板写入失败：" + path);
               }
           }
        }
    }


    @Override
    public void modelCode(CodeGenerator req){
        // 数据模型
        Map<String, Object> dataModel = getDataModelByCodeGenerator(req);

        // 代码生成器信息
        GeneratorInfo generator = getGenerator(req);

        //获取选中的模版名称
        List<String> templateNames = req.getTemplateNames();
        if (CollectionUtil.isEmpty(templateNames)) {
            throw new BusinessException("未选择要生成的代码模版");
        }
        // 渲染模板并输出
        for (TemplateInfo template : generator.getTemplates()) {
            if (templateNames.contains(template.getTemplateName())) {
                dataModel.put("templateName", template.getTemplateName());
                String content = TemplateUtils.getContent(template.getTemplateContent(), dataModel);
                String path = TemplateUtils.getContent(template.getGeneratorPath(), dataModel);
                System.out.println(path);
                FileUtil.writeUtf8String(content, path);
            }
        }
    }

    /**
     * 获取生成器配置
     * @param codeGenerator 代码生成配置
     * @return 代码生成器
     */
    public GeneratorInfo getGenerator(CodeGenerator codeGenerator) {
        // 代码生成器信息
        GeneratorInfo generator = generatorConfig.getGeneratorConfig();
        //更新从配置中加载的项目配置信息
        ProjectInfo project = generator.getProject();
        project.setBackendPath(codeGenerator.getBackendPath());
        project.setModuleName(codeGenerator.getModuleName());
        project.setVersion(codeGenerator.getVersion());
        project.setPackageName(codeGenerator.getPackageName());
        generator.setProject(project);
        //开发者信息
        DeveloperInfo developer = generator.getDeveloper();
        developer.setAuthor(codeGenerator.getAuthor());
        developer.setEmail(codeGenerator.getEmail());
        generator.setDeveloper(developer);
        return generator;
    }
    /**
     * 获取渲染的数据模型
     *
     * @param modelId 表ID
     */
    private Map<String, Object> getDataModel(String modelId) {
        // 表信息
        ModelEntity model = tableService.getById(modelId);
        List<ModelFieldEntity> fieldList = tableFieldService.getByModelId(modelId);
        model.setFieldList(fieldList);

        // 数据模型
        Map<String, Object> dataModel = new HashMap<>();

        // 获取数据库类型
        String dbType = datasourceService.getDatabaseProductName(model.getDatasourceId());
        dataModel.put("dbType", "Oracle");


        // 项目信息
        dataModel.put("package", model.getPackageName());
        dataModel.put("packagePath", model.getPackageName().replace(".", File.separator));
        dataModel.put("version", model.getVersion());
        dataModel.put("moduleName", model.getModuleName());
        dataModel.put("ModuleName", StrUtil.upperFirst(model.getModuleName()));
        dataModel.put("functionName", model.getFunctionName());
        dataModel.put("FunctionName", StrUtil.upperFirst(model.getFunctionName()));

        // 开发者信息
        dataModel.put("author", model.getAuthor());
        dataModel.put("email", model.getEmail());
        dataModel.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        dataModel.put("date", DateUtils.format(new Date(), DateUtils.DATE_PATTERN));
        // todo 缺少queryList
        // 查询属性
        dataModel.put("queryList",new ArrayList<>());

        // 设置字段分类
        setFieldTypeList(dataModel, model);

        // 设置基类信息
        setBaseClass(dataModel, model,null);

        // 导入的包列表
        Set<String> importList = fieldTypeService.getPackageByTableId(model.getId());
        dataModel.put("importList", importList);

        // 表信息
        dataModel.put("tableName", model.getTableName());
        dataModel.put("tableComment", model.getTableComment());
        dataModel.put("className", StrUtil.lowerFirst(model.getClassName()));
        dataModel.put("ClassName", model.getClassName());
        dataModel.put("fieldList", model.getFieldList());

        // 生成路径
        dataModel.put("backendPath", model.getBackendPath());
        dataModel.put("frontendPath", model.getFrontendPath());

        return dataModel;
    }
    /**
     * 获取渲染的数据模型根据生成代码的模型
     *
     * @param req 表ID
     */
    private Map<String, Object> getDataModelByCodeGenerator(CodeGenerator req) {
        // 表信息
        ModelEntity model = tableService.getById(req.getId());
        List<ModelFieldEntity> fieldList = tableFieldService.getByModelId(req.getId());
        model.setFieldList(fieldList);

        // 数据模型
        Map<String, Object> dataModel = new HashMap<>();

        // 获取数据库类型
        String dbType = DbType.Oracle.toString();
        if (model.getDatasourceId() != null){
            dbType = datasourceService.getDatabaseProductName(model.getDatasourceId());
        }
        dataModel.put("dbType", dbType);
        // 项目信息
        dataModel.put("package", req.getPackageName());
        dataModel.put("packagePath", req.getPackageName().replace(".", File.separator));
        dataModel.put("version", req.getVersion());
        dataModel.put("moduleName", req.getModuleName());
        dataModel.put("ModuleName", StrUtil.upperFirst(model.getModuleName()));
        String functionName = req.getFunctionName();
        if (StringUtils.isBlank(functionName)) {
            functionName = req.getModuleName();
        }
        dataModel.put("functionName", functionName);
        dataModel.put("FunctionName", StrUtil.upperFirst(functionName));

        // 开发者信息
        dataModel.put("author", req.getAuthor());
        dataModel.put("email", req.getEmail());
        dataModel.put("datetime", DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
        dataModel.put("date", DateUtils.format(new Date(), DateUtils.DATE_PATTERN));

        //说明
        dataModel.put("dis",req.getDis());

        //控制器
        dataModel.put("baseApi",req.getBaseApi());
        dataModel.put("apis",req.getApis());

        // 查询属性
        dataModel.put("queryList",new ArrayList<>());

        // 设置字段分类
        setFieldTypeList(dataModel, model);

        // 设置基类信息
        setBaseClass(dataModel, model,req.getBaseclassId());

        // 导入的包列表
        Set<String> importList = fieldTypeService.getPackageByTableId(model.getId());
        dataModel.put("importList", importList);

        // 表信息
        dataModel.put("tableName", req.getTableName());
        dataModel.put("tableComment", model.getTableComment());
        dataModel.put("className", StrUtil.lowerFirst(req.getClassName()));
        dataModel.put("ClassName", req.getClassName());
        dataModel.put("fieldList",getDbFieldList(model));
        // 生成路径
        dataModel.put("backendPath", req.getBackendPath());

        return dataModel;
    }
    /**
     * 设置基类信息
     *
     * @param dataModel 数据模型
     * @param model     表
     */
    private void setBaseClass(Map<String, Object> dataModel, ModelEntity model,String baseClassId) {
        if (baseClassId == null) {
           baseClassId =  model.getBaseclassId();
        }
        if (baseClassId == null) {
            return;
        }
        // 基类
        BaseClassEntity baseClass = baseClassService.getById(baseClassId);
        baseClass.setPackageName(baseClass.getPackageName());
        dataModel.put("entityBaseClass", baseClass);
        //dto的基类
        // 提取类名前缀"Base"
        String prefix = baseClass.getCode().substring(0, baseClass.getCode().indexOf("Entity"));
        //获取
        BaseClassEntity dtoBaseClass = new BaseClassEntity();
        //todo 暂时写死
        dtoBaseClass.setPackageName("org.ares.cloud.common.dto");
        dtoBaseClass.setCode(prefix+"Dto");
        dtoBaseClass.setFields(baseClass.getFields());
        dataModel.put("dtoBaseClass", dtoBaseClass);
        // 基类字段
        String[] fields = baseClass.getFields().split(",");

        // 标注为基类字段
        for (ModelFieldEntity field : model.getFieldList()) {
            if (ArrayUtil.contains(fields, field.getFieldName())) {
                field.setBaseField(true);
            }
        }
    }


    /**
     * 设置字段分类信息
     *
     * @param dataModel 数据模型
     * @param model     表
     */
    private void setFieldTypeList(Map<String, Object> dataModel, ModelEntity model) {
        // 主键列表 (支持多主键)
        List<ModelFieldEntity> primaryList = new ArrayList<>();
        for (ModelFieldEntity field : model.getFieldList()) {
            if (field.isPrimaryPk()) {
                primaryList.add(field);
            }
        }
        dataModel.put("primaryList", primaryList);
    }

    /**
     * 获取对应的类型
     * @param model 模型
     * @return 字段
     */
    private List<ModelFieldEntity> getDbFieldList(ModelEntity model ){
        return model.getFieldList().stream().peek(s-> s.setFieldType(toOracleType(s.getFieldType()))).toList();
    }
    /**
     * 将MySQL数据类型转换为Oracle数据类型
     * @param mysqlType MySQL数据类型
     * @return Oracle对应的数据类型
     */
    private  String toOracleType(String mysqlType) {
        if (mysqlType == null || mysqlType.isEmpty()) {
            throw new IllegalArgumentException("MySQL type cannot be null or empty");
        }

        return switch (mysqlType.toLowerCase()) {
            case "varchar" -> "VARCHAR2";
            case "bigint" -> "NUMBER";
            case "int" -> "NUMBER";
            case "datetime" -> "TIMESTAMP";
            case "text" -> "CLOB";
            case "tinyint" -> "NUMBER(1)";
            case "decimal" -> "NUMBER";
            case "double" -> "FLOAT(24)";
            case "float" -> "FLOAT";
            case "blob" -> "BLOB";
            default -> mysqlType; // 对于无法识别的类型，返回原始类型
        };
    }
}
