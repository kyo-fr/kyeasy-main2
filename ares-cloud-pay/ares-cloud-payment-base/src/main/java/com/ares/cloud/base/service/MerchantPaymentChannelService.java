package com.ares.cloud.base.service;

import com.ares.cloud.base.dto.MerchantPaymentChannelDto;
import com.ares.cloud.base.command.MerchantPaymentChannelCommand;
import com.ares.cloud.base.entity.MerchantPaymentChannelEntity;
import com.ares.cloud.base.query.MerchantPaymentChannelQuery;
import org.ares.cloud.database.service.BaseService;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道 服务接口
* @version 1.0.0
* @date 2025-05-13
*/
public interface MerchantPaymentChannelService extends BaseService<MerchantPaymentChannelEntity> {
    /**
    * 保存渠道
    * @param dto
    */
    void saveChannel(MerchantPaymentChannelCommand dto);
    /**
    * 将数据保存到仓库中.
    * @param dto  数据对象
    */
    void create(MerchantPaymentChannelDto dto);

    /**
    * 将数据保存到仓库中.
    * @param dos 数据对象集合
    */
    void create(List<MerchantPaymentChannelDto> dos);

    /**
    * 更新仓库数据.
    * @param dto 数据对象集合
    */
    void update(MerchantPaymentChannelDto dto);

    /**
    * 更新仓库数据.
    * @param dos 数据对象集合
    */
    void update(List<MerchantPaymentChannelDto> dos);

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
    MerchantPaymentChannelDto loadById(String id);

    /**
    * 按实体ID加载实体列表.
    * @param ids 实体的id
    * @return 集合是实体
    */
    List<MerchantPaymentChannelDto> loadByIds(List<String> ids);

    /**
    * 根据条件加载所有实体，该条件来自模板。
    * 例如：我想加载订单的所有项目，那么模板就是orderItem
    * 并将orderId设置为模板中的“orderId”列。
    * 然后，它将根据模板中的条件加载此订单的所有项目。
    * @return list of entities.
    */
    List<MerchantPaymentChannelDto> loadAll(MerchantPaymentChannelQuery query);

}