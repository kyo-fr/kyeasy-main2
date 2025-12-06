package org.ares.cloud.web;

import org.ares.cloud.config.GenDataSource;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.entity.DataSourceEntity;
import org.ares.cloud.entity.ModelEntity;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.query.GenQuery;
import org.ares.cloud.service.DataSourceService;
import org.ares.cloud.utils.DbUtils;
import org.ares.cloud.utils.GenUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description 数据源管理
 * @date 2024/01/24/00:00
 **/
@Slf4j
@RestController
@RequestMapping("ares-gen/datasource")
@AllArgsConstructor
public class DataSourceController {
    private final DataSourceService datasourceService;

    @GetMapping("page")
    public Result<PageResult<DataSourceEntity>> page(GenQuery query) {
        PageResult<DataSourceEntity> page = datasourceService.page(query);

        return Result.success(page);
    }

    @GetMapping("list")
    public Result<List<DataSourceEntity>> list() {
        List<DataSourceEntity> list = datasourceService.getList();

        return Result.success(list);
    }

    @GetMapping("{id}")
    public Result<DataSourceEntity> get(@PathVariable("id") Long id) {
        DataSourceEntity data = datasourceService.getById(id);

        return Result.success(data);
    }

    @GetMapping("test/{id}")
    public Result<String> test(@PathVariable("id") Long id) {
        try {
            DataSourceEntity entity = datasourceService.getById(id);

            Connection connection = DbUtils.getConnection(new GenDataSource(entity));
            connection.close();
            return Result.success("连接成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("连接失败，请检查配置信息");
        }
    }

    @PostMapping
    public Result<String> save(@RequestBody DataSourceEntity entity) {
        datasourceService.save(entity);

        return Result.success();
    }

    @PutMapping
    public Result<String> update(@RequestBody DataSourceEntity entity) {
        datasourceService.updateById(entity);

        return Result.success();
    }

    @DeleteMapping
    public Result<String> delete(@RequestBody Long[] ids) {
        datasourceService.removeBatchByIds(Arrays.asList(ids));

        return Result.success();
    }

    /**
     * 根据数据源ID，获取全部数据表
     *
     * @param id 数据源ID
     */
    @GetMapping("table/list/{id}")
    public Result tableList(@PathVariable("id") Long id) {
        try {
            // 获取数据源
            GenDataSource datasource = datasourceService.get(id);
            // 根据数据源，获取全部数据表
            List<ModelEntity> tableList = GenUtils.getTableList(datasource);

            return Result.success(tableList);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Result.error("数据源配置错误，请检查数据源配置！");
        }
    }
}
