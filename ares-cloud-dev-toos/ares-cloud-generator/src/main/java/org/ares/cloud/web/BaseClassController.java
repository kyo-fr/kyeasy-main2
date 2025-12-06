package org.ares.cloud.web;

import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.entity.BaseClassEntity;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.query.GenQuery;
import org.ares.cloud.service.BaseClassService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description 基类控制器
 * @date 2024/01/23/23:56
 **/
@RestController
@RequestMapping("ares-gen/baseclass")
@AllArgsConstructor
public class BaseClassController {
    private final BaseClassService baseClassService;
    @GetMapping("page")
    public Result<PageResult<BaseClassEntity>>  page(GenQuery query) {
        PageResult<BaseClassEntity> page = baseClassService.page(query);
        return Result.success(page);
    }

    @GetMapping("list")
    public Result<List<BaseClassEntity>> list() {
        List<BaseClassEntity> list = baseClassService.getList();

        return Result.success(list);
    }

    @GetMapping("{id}")
    public Result<BaseClassEntity> get(@PathVariable("id") Long id) {
        BaseClassEntity data = baseClassService.getById(id);

        return Result.success(data);
    }

    @PostMapping
    public Result<String> save(@RequestBody BaseClassEntity entity) {
        baseClassService.save(entity);

        return Result.success();
    }

    @PutMapping
    public Result<String> update(@RequestBody BaseClassEntity entity) {
        baseClassService.updateById(entity);

        return Result.success();
    }

    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        baseClassService.removeBatchByIds(Arrays.asList(ids));

        return Result.success();
    }
}
