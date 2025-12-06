package org.ares.cloud.businessId.service;

import org.ares.cloud.businessId.dto.BusinessIdDto;
import org.ares.cloud.businessId.entity.BusinessIdEntity;
import org.ares.cloud.businessId.query.BusinessIdQuery;
import org.ares.cloud.database.service.BaseService;
import org.ares.cloud.common.dto.PageResult;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 系统业务id 服务接口
* @version 1.0.0
* @date 2024-10-13
*/
public interface BusinessIdService extends BaseService<BusinessIdEntity> {
    /**
    * 将数据保存到仓库中.
    * @param dto  数据对象
    */
    void create(BusinessIdDto dto);

    /**
    * 将数据保存到仓库中.
    * @param dos 数据对象集合
    */
    void create(List<BusinessIdDto> dos);

    /**
    * 更新仓库数据.
    * @param dto 数据对象集合
    */
    void update(BusinessIdDto dto);

    /**
    * 更新仓库数据.
    * @param dos 数据对象集合
    */
    void update(List<BusinessIdDto> dos);

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
    BusinessIdDto loadById(String id);

    /**
    * 按实体ID加载实体列表.
    * @param ids 实体的id
    * @return 集合是实体
    */
    List<BusinessIdDto> loadByIds(List<String> ids);

    /**
    * 根据条件加载所有实体，该条件来自模板。
    * 例如：我想加载订单的所有项目，那么模板就是orderItem
    * 并将orderId设置为模板中的“orderId”列。
    * 然后，它将根据模板中的条件加载此订单的所有项目。
    * @return list of entities.
    */
    List<BusinessIdDto> loadAll();

    /**
    * 根据条件分页加载加载所有实体，该条件来自map
    * @param query 查询对象
    * @return list of entities.
    */
    PageResult<BusinessIdDto> loadList(BusinessIdQuery query);

    /**
     * 生成随机的业务id
     * @return 业务id
     */
    String generateRandomBusinessId();

    /**
     * 根据业务模块名生成id
     * @param moduleName 业务模块名
     * @return 业务id
     */
    String generateBusinessId(String moduleName);

    /**
     * 生成16位短雪花ID
     * 格式：秒级时间戳(10位) + 机器ID(2位) + 序列号(4位)
     * @return 16位雪花ID
     */
    String generateSnowflakeId();
}