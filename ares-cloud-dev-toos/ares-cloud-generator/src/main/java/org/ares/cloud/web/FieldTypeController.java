package org.ares.cloud.web;

import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.entity.FieldTypeEntity;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.query.GenQuery;
import org.ares.cloud.service.FieldTypeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @author hugo  tangxkwork@163.com
 * @description 字段类型管理
 * @date 2024/01/24/00:07
 **/
@RestController
@RequestMapping("ares-gen/fieldtype")
@AllArgsConstructor
public class FieldTypeController {
    private final FieldTypeService fieldTypeService;

    @GetMapping("page")
    public Result<PageResult<FieldTypeEntity>> page(GenQuery query) {
        PageResult<FieldTypeEntity> page = fieldTypeService.page(query);

        return Result.success(page);
    }

    @GetMapping("{id}")
    public Result<FieldTypeEntity> get(@PathVariable("id") Long id) {
        FieldTypeEntity data = fieldTypeService.getById(id);

        return Result.success(data);
    }

    @GetMapping("list")
    public Result<Set<String>> list() {
        Set<String> set = fieldTypeService.getList();
        return Result.success(set);
    }

    @PostMapping
    public Result<String> save(@RequestBody FieldTypeEntity entity) {
        fieldTypeService.save(entity);

        return Result.success();
    }

    @PutMapping
    public Result<String> update(@RequestBody FieldTypeEntity entity) {
        fieldTypeService.updateById(entity);

        return Result.success();
    }

    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        fieldTypeService.removeBatchByIds(Arrays.asList(ids));

        return Result.success();
    }

    @GetMapping("all")
    public Result<Set<FieldTypeEntity>> getAll() {
        Set<FieldTypeEntity> all = fieldTypeService.getAll();
        return Result.success(all);
    }
    @GetMapping("mapping")
    public Result<Map<String,String>> getAttributeMapping() {
        Map<String,String> mapping = fieldTypeService.getAttributeMapping();
        return Result.success(mapping);
    }
}