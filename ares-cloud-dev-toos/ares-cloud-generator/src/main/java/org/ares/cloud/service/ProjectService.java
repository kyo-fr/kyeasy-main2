package org.ares.cloud.service;

import org.ares.cloud.entity.ProjectEntity;

import java.io.IOException;

/**
 * @author hugo  tangxkwork@163.com
 * @description 项目服务
 * @date 2024/01/23/22:53
 **/
public interface ProjectService extends BsService<ProjectEntity> {

    byte[] download(ProjectEntity project) throws IOException;
}
