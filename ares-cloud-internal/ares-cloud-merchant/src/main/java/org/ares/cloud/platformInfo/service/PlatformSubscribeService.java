package org.ares.cloud.platformInfo.service;

import org.ares.cloud.platformInfo.dto.PlatformSubscribeDto;
import org.ares.cloud.platformInfo.entity.PlatformSubscribeEntity;
import org.ares.cloud.platformInfo.query.PlatformSubscribeQuery;
import org.ares.cloud.database.service.BaseService;
import org.ares.cloud.common.dto.PageResult;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 订阅基础信息 服务接口
* @version 1.0.0
* @date 2024-10-31
*/
public interface PlatformSubscribeService extends BaseService<PlatformSubscribeEntity> {
    /**
    * 将数据保存到仓库中.
    * @param dto  数据对象
    */
    void create(PlatformSubscribeDto dto);

    /**
    * 将数据保存到仓库中.
    * @param dos 数据对象集合
    */
    void create(List<PlatformSubscribeDto> dos);

    /**
    * 更新仓库数据.
    * @param dto 数据对象集合
    */
    void update(PlatformSubscribeDto dto);

    /**
    * 更新仓库数据.
    * @param dos 数据对象集合
    */
    void update(List<PlatformSubscribeDto> dos);

    /**
    * 根据主键删除仓库数据.
    * @param id 删除数据
    */
    void deleteById(String id);

    /**
    * 根据主键删除仓库数据
    * @param ids 数据集合
    */
    void deleteByIds(List<String> ids);

    /**
    * 根据主键加载实体
    * @param id id
    * @return entity
    */
    PlatformSubscribeDto loadById(String id);

    /**
    * 按实体ID加载实体列表.
    * @param ids 实体的id
    * @return 集合是实体
    */
    List<PlatformSubscribeDto> loadByIds(List<String> ids);

    /**
    * 根据条件加载所有实体，该条件来自模板。
    * 例如：我想加载订单的所有项目，那么模板就是orderItem
    * 并将orderId设置为模板中的“orderId”列。
    * 然后，它将根据模板中的条件加载此订单的所有项目。
    * @return list of entities.
    */
    List<PlatformSubscribeDto> loadAll();

    /**
    * 根据条件分页加载加载所有实体，该条件来自map
    * @param query 查询对象
    * @return list of entities.
    */
    PageResult<PlatformSubscribeDto> loadList(PlatformSubscribeQuery query);
}