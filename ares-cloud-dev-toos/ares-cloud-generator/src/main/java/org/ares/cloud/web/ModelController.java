package org.ares.cloud.web;

import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.entity.ModelEntity;
import org.ares.cloud.entity.ModelFieldEntity;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.query.GenQuery;
import org.ares.cloud.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author hugo  tangxkwork@163.com
 * @description 数据模型管理
 * @date 2024/01/24/00:11
 **/
@RestController
@RequestMapping("ares-gen/model")
@AllArgsConstructor
public class ModelController {
    private final ModelService modelService;
    private final org.ares.cloud.service.ModelFieldService modelFieldService;

    /**
     * 分页
     *
     * @param query 查询参数
     */
    @GetMapping("page")
    public Result<PageResult<ModelEntity>> page(GenQuery query) {
        PageResult<ModelEntity> page = modelService.page(query);

        return Result.success(page);
    }

    /**
     * 获取模型信息
     *
     * @param id 模型ID
     */
    @GetMapping("{id}")
    public Result<ModelEntity> get(@PathVariable("id") Long id) {
        ModelEntity model = modelService.getById(id);

        // 获取模型的字段
        List<ModelFieldEntity> fieldList = modelFieldService.getByModelId(model.getId());
        model.setFieldList(fieldList);

        return Result.success(model);
    }
    /**
     * 新增
     *
     * @param model 模型信息
     */
    @PostMapping
    public Result<ModelEntity> add(@RequestBody ModelEntity model) {
        ModelEntity model1 = modelService.add(model);
        return Result.success(model1);
    }
    /**
     * 修改
     *
     * @param model 模型信息
     */
    @PutMapping
    public Result<String> update(@RequestBody ModelEntity model) {
        modelService.updateById(model);

        return Result.success();
    }

    /**
     * 删除
     *
     * @param ids 模型id数组
     */
    @DeleteMapping
    public Result<String> delete(@RequestBody String[] ids) {
        modelService.deleteBatchIds(ids);

        return Result.success();
    }

    /**
     * 同步模型结构
     *
     * @param id 模型ID
     */
    @PostMapping("sync/{id}")
    public Result<String> sync(@PathVariable("id") String id) {
        modelService.sync(id);

        return Result.success();
    }

    /**
     * 导入数据源中的模型
     *
     * @param datasourceId  数据源ID
     * @param tableNameList 模型名列模型
     */
    @PostMapping("import/{datasourceId}")
    public Result<String> modelImport(@PathVariable("datasourceId") Long datasourceId, @RequestBody List<String> tableNameList) {
        for (String tableName : tableNameList) {
            modelService.tableImport(datasourceId, tableName);
        }
        return Result.success();
    }
    /**
     * 获取所有的基础模型
     *
     */
    @GetMapping("get_all_base_model")
    public Result<List<ModelEntity>> getAllBaseModel() {
        List<ModelEntity> models = modelService.getAllBaseModel();
        return Result.success(models);
    }

    /**
     * 导入基础模型的属性
     *
     */
    @PostMapping("import_base_model_filed")
    public Result<String> importBaseModelFiled(@RequestBody Map<String, String> requestBody) {
        String baseModelId = requestBody.get("baseModelId");
        modelService.importBaseModelFiled(requestBody.get("modelId"),baseModelId);
        return Result.success();
    }

    /**
     * 新增模型字段数据
     *
     * @param modelId        模型ID
     * @param modelField 字段列模型
     */
    @PostMapping("field/{modelId}")
    public Result<String> addModelField(@PathVariable("modelId") String modelId, @RequestBody ModelFieldEntity modelField) {
        modelField.setModelId(modelId);
        modelFieldService.save(modelField);
        return Result.success();
    }
    /**
     * 修改模型字段数据
     *
     * @param modelId        模型ID
     * @param tableFieldList 字段列模型
     */
    @PutMapping("field/{modelId}")
    public Result<String> updateTableField(@PathVariable("modelId") String modelId, @RequestBody List<ModelFieldEntity> tableFieldList) {
        modelFieldService.updateTableField(modelId, tableFieldList);
        return Result.success();
    }
    /**
     * 删除模型字段数据
     *
     * @param ids        属性id
     */
    @DeleteMapping("field")
    public Result<String> delTableField( @RequestBody String[] ids) {
        modelFieldService.removeBatchByIds(Arrays.asList(ids));
        return Result.success();
    }
}
