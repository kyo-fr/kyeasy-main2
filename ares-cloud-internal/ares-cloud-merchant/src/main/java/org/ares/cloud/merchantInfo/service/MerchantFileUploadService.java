package org.ares.cloud.merchantInfo.service;

import org.ares.cloud.merchantInfo.dto.MerchantFileUploadDto;
import org.ares.cloud.merchantInfo.entity.MerchantFileUploadEntity;
import org.ares.cloud.merchantInfo.query.MerchantFileUploadQuery;
import org.ares.cloud.database.service.BaseService;
import org.ares.cloud.common.dto.PageResult;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户文件上传 服务接口
* @version 1.0.0
* @date 2024-10-09
*/
public interface MerchantFileUploadService extends BaseService<MerchantFileUploadEntity> {
    /**
    * 将数据保存到仓库中.
    * @param dto  数据对象
    */
    void create(MerchantFileUploadDto dto);

    /**
    * 将数据保存到仓库中.
    * @param dos 数据对象集合
    */
    void create(List<MerchantFileUploadDto> dos);

    /**
    * 更新仓库数据.
    * @param dto 数据对象集合
    */
    void update(MerchantFileUploadDto dto);

    /**
    * 更新仓库数据.
    * @param dos 数据对象集合
    */
    void update(List<MerchantFileUploadDto> dos);

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
    MerchantFileUploadDto loadById(String id);

    /**
    * 按实体ID加载实体列表.
    * @param ids 实体的id
    * @return 集合是实体
    */
    List<MerchantFileUploadDto> loadByIds(List<String> ids);

    /**
    * 根据条件加载所有实体，该条件来自模板。
    * 例如：我想加载订单的所有项目，那么模板就是orderItem
    * 并将orderId设置为模板中的“orderId”列。
    * 然后，它将根据模板中的条件加载此订单的所有项目。
    * @return list of entities.
    */
    List<MerchantFileUploadDto> loadAll();

    /**
    * 根据条件分页加载加载所有实体，该条件来自map
    * @param query 查询对象
    * @return list of entities.
    */
    PageResult<MerchantFileUploadDto> loadList(MerchantFileUploadQuery query);
}