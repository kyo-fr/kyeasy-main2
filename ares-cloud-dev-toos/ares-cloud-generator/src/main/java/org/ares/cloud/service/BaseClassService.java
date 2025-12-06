package org.ares.cloud.service;

import org.ares.cloud.entity.BaseClassEntity;

import java.util.List;

/**
 * @author hugo  tangxkwork@163.com
 * @description 基类管理
 * @date 2024/01/23/22:49
 **/
public interface BaseClassService extends BsService<BaseClassEntity> {

    List<BaseClassEntity> getList();
}
