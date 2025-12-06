package org.ares.cloud.merchantInfo.service;

import org.ares.cloud.merchantInfo.dto.MerchantDistributionDto;
import org.ares.cloud.merchantInfo.entity.MerchantDistributionEntity;
import org.ares.cloud.merchantInfo.query.MerchantDistributionQuery;
import org.ares.cloud.database.service.BaseService;
import org.ares.cloud.common.dto.PageResult;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户配送设置 服务接口
* @version 1.0.0
* @date 2024-11-05
*/
public interface MerchantDistributionService extends BaseService<MerchantDistributionEntity> {
    /**
    * 将数据保存到仓库中.
    * @param dto  数据对象
    */
    void create(MerchantDistributionDto dto);

    /**
    * 将数据保存到仓库中.
    * @param dos 数据对象集合
    */
    void create(List<MerchantDistributionDto> dos);

    /**
    * 更新仓库数据.
    * @param dto 数据对象集合
    */
    void update(MerchantDistributionDto dto);

    /**
    * 更新仓库数据.
    * @param dos 数据对象集合
    */
    void update(List<MerchantDistributionDto> dos);

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
    MerchantDistributionDto loadById(String id);

    /**
    * 按实体ID加载实体列表.
    * @param ids 实体的id
    * @return 集合是实体
    */
    List<MerchantDistributionDto> loadByIds(List<String> ids);

    /**
    * 根据条件加载所有实体，该条件来自模板。
    * 例如：我想加载订单的所有项目，那么模板就是orderItem
    * 并将orderId设置为模板中的“orderId”列。
    * 然后，它将根据模板中的条件加载此订单的所有项目。
    * @return list of entities.
    */
    List<MerchantDistributionDto> loadAll();

    /**
    * 根据条件分页加载加载所有实体，该条件来自map
    * @param query 查询对象
    * @return list of entities.
    */
    PageResult<MerchantDistributionDto> loadList(MerchantDistributionQuery query);

    /**
     * 根据租户id查询详情
     * @param tenantId 查询详情
     */
    MerchantDistributionDto getInfoByTenantId(String tenantId);

}