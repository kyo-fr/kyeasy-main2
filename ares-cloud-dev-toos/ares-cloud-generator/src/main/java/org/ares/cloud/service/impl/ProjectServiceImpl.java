package org.ares.cloud.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.ZipUtil;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.entity.ProjectEntity;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.query.GenQuery;
import org.ares.cloud.repository.ProjectRepository;
import org.ares.cloud.service.ProjectService;
import org.ares.cloud.utils.ProjectUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hugo  tangxkwork@163.com
 * @description 项目
 * @date 2024/01/23/23:19
 **/
@Service
@AllArgsConstructor
public class ProjectServiceImpl extends BaseServiceImpl<ProjectRepository, ProjectEntity> implements ProjectService {
    @Override
    public PageResult<ProjectEntity> page(GenQuery query) {
        IPage<ProjectEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));

        return new PageResult<>(page.getRecords(), page.getTotal());
    }
    @Override
    public byte[] download(ProjectEntity project) throws IOException {
        // 原项目根路径
        File srcRoot = new File(project.getProjectPath());

        // 临时项目根路径
        File destRoot = new File(ProjectUtils.getTmpDirPath(project.getModifyProjectName()));

        // 排除的文件
        List<String> exclusions = StrUtil.split(project.getExclusions(), ProjectUtils.SPLIT);

        // 获取替换规则
        Map<String, String> replaceMap = getReplaceMap(project);

        // 拷贝项目到新路径，并替换路径和文件名
        ProjectUtils.copyDirectory(srcRoot, destRoot, exclusions, replaceMap);

        // 需要替换的文件后缀
        List<String> suffixList = StrUtil.split(project.getModifySuffix(), ProjectUtils.SPLIT);

        // 替换文件内容数据
        ProjectUtils.contentFormat(destRoot, suffixList, replaceMap);

        // 生成zip文件
        File zipFile = ZipUtil.zip(destRoot);

        byte[] data = FileUtil.readBytes(zipFile);

        // 清空文件
        FileUtil.clean(destRoot.getParentFile().getParentFile());

        return data;
    }
    /**
     * 获取替换规则
     */
    private Map<String, String> getReplaceMap(ProjectEntity project) {
        Map<String, String> map = new LinkedHashMap<>();

        // 项目路径替换
        String srcPath = "src/main/java/" + project.getProjectPackage().replaceAll("\\.", "/");
        String destPath = "src/main/java/" + project.getModifyProjectPackage().replaceAll("\\.", "/");
        map.put(srcPath, destPath);

        // 项目包名替换
        map.put(project.getProjectPackage(), project.getModifyProjectPackage());

        // 项目标识替换
        map.put(project.getProjectCode(), project.getModifyProjectCode());
        map.put(StrUtil.upperFirst(project.getProjectCode()), StrUtil.upperFirst(project.getModifyProjectCode()));

        return map;
    }
    @Override
    public boolean save(ProjectEntity entity) {
        entity.setExclusions(ProjectUtils.EXCLUSIONS);
        entity.setModifySuffix(ProjectUtils.MODIFY_SUFFIX);
        entity.setCreateTime(new Date());
        return super.save(entity);
    }
}
