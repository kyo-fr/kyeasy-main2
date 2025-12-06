package org.ares.cloud.web;

import cn.hutool.core.io.IoUtil;
import org.ares.cloud.entity.ProjectEntity;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.query.GenQuery;
import org.ares.cloud.service.ProjectService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.ares.cloud.common.dto.PageResult;
/**
 * @author hugo  tangxkwork@163.com
 * @description 项目名
 * @date 2024/01/24/00:09
 **/
@RestController
@RequestMapping("ares-gen/project")
@AllArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("page")
    public Result<PageResult<ProjectEntity>> page(@Valid GenQuery query) {
        PageResult<ProjectEntity> page = projectService.page(query);

        return Result.success(page);
    }

    @GetMapping("{id}")
    public Result<ProjectEntity> get(@PathVariable("id") Long id) {
        ProjectEntity entity = projectService.getById(id);

        return Result.success(entity);
    }

    @PostMapping
    public Result<String> save(@RequestBody ProjectEntity entity) {
        projectService.save(entity);

        return Result.success();
    }

    @PutMapping
    public Result<String> update(@RequestBody @Valid ProjectEntity entity) {
        projectService.updateById(entity);

        return Result.success();
    }

    @DeleteMapping
    public Result<String> delete(@RequestBody List<Long> idList) {
        projectService.removeByIds(idList);

        return Result.success();
    }

    /**
     * 源码下载
     */
    @GetMapping("download/{id}")
    public void download(@PathVariable("id") Long id, HttpServletResponse response) throws Exception {
        // 项目信息
        ProjectEntity project = projectService.getById(id);

        byte[] data = projectService.download(project);

        response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"" + project.getModifyProjectName() + ".zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IoUtil.write(response.getOutputStream(), false, data);
    }
}
