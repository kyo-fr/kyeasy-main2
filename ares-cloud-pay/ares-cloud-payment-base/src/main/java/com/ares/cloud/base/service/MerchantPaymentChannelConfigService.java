package com.ares.cloud.base.service;

import com.ares.cloud.base.command.MerchantBraintreePaymentConfigCommand;
import com.ares.cloud.base.dto.MerchantBraintreePaymentConfigDto;
import com.ares.cloud.base.dto.MerchantPaymentChannelConfigDto;
import com.ares.cloud.base.entity.MerchantPaymentChannelConfigEntity;
import com.ares.cloud.base.query.MerchantPaymentChannelConfigQuery;
import org.ares.cloud.database.service.BaseService;
import org.ares.cloud.common.dto.PageResult;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道配置 服务接口
* @version 1.0.0
* @date 2025-05-13
*/
public interface MerchantPaymentChannelConfigService extends BaseService<MerchantPaymentChannelConfigEntity> {

    /**
     * 商户Braintree支付配置
     * @param command 商户Braintree支付配置
     */
    void saveBraintreePaymentConfig(MerchantBraintreePaymentConfigCommand command);
    /**
    * 将数据保存到仓库中.
    * @param dto  数据对象
    */
    void create(MerchantPaymentChannelConfigDto dto);

    /**
    * 将数据保存到仓库中.
    * @param dos 数据对象集合
    */
    void create(List<MerchantPaymentChannelConfigDto> dos);

    /**
    * 更新仓库数据.
    * @param dto 数据对象集合
    */
    void update(MerchantPaymentChannelConfigDto dto);

    /**
    * 更新仓库数据.
    * @param dos 数据对象集合
    */
    void update(List<MerchantPaymentChannelConfigDto> dos);

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
    MerchantPaymentChannelConfigDto loadById(String id);

    /**
    * 按实体ID加载实体列表.
    * @param ids 实体的id
    * @return 集合是实体
    */
    List<MerchantPaymentChannelConfigDto> loadByIds(List<String> ids);

    /**
    * 根据条件加载所有实体，该条件来自模板。
    * 例如：我想加载订单的所有项目，那么模板就是orderItem
    * 并将orderId设置为模板中的“orderId”列。
    * 然后，它将根据模板中的条件加载此订单的所有项目。
    * @return list of entities.
    */
    List<MerchantPaymentChannelConfigDto> loadAll();

    /**
    * 根据条件分页加载加载所有实体，该条件来自map
    * @param query 查询对象
    * @return list of entities.
    */
    PageResult<MerchantPaymentChannelConfigDto> loadList(MerchantPaymentChannelConfigQuery query);

    /**
     * 根据商户ID和支付渠道key 查询支付渠道配置
     * @param merchantId 商户ID
     * @param paymentMerchant  渠道key
     * @return 配置
     */
    MerchantPaymentChannelConfigDto selectByMerchantIdAndPaymentMerchant(String merchantId, String paymentMerchant);
    /**
    * 获取商户braintree支付配置
    * @param merchantId 商户ID
    * @return 配置
    */
    MerchantBraintreePaymentConfigDto getBraintreePaymentConfig(String merchantId);
}