package org.ares.cloud.merchantInfo.service;

import org.ares.cloud.merchantInfo.dto.MerchantInfoDto;
import org.ares.cloud.merchantInfo.vo.MerchantInfoVo;
import org.ares.cloud.merchantInfo.entity.MerchantInfoEntity;
import org.ares.cloud.merchantInfo.query.MerchantInfoQuery;
import org.ares.cloud.database.service.BaseService;
import org.ares.cloud.common.dto.PageResult;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户信息 服务接口
* @version 1.0.0
* @date 2024-10-08
*/
public interface MerchantInfoService extends BaseService<MerchantInfoEntity> {
    /**
    * 将数据保存到仓库中.
    * @param dto  数据对象
    */
    MerchantInfoDto create(MerchantInfoDto dto);

    /**
    * 将数据保存到仓库中.
    * @param dos 数据对象集合
    */
    void create(List<MerchantInfoDto> dos);

    /**
    * 更新仓库数据.
    * @param dto 数据对象集合
    */
    void update(MerchantInfoDto dto);

    /**
    * 更新仓库数据.
    * @param dos 数据对象集合
    */
    void update(List<MerchantInfoDto> dos);

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
    MerchantInfoDto getMerchantInfoById( String id );

    /**
    * 按实体ID加载实体列表.
    * @param ids 实体的id
    * @return 集合是实体
    */
    List<MerchantInfoDto> loadByIds(List<String> ids);

    /**
    * 根据条件加载所有实体，该条件来自模板。
    * 例如：我想加载订单的所有项目，那么模板就是orderItem
    * 并将orderId设置为模板中的“orderId”列。
    * 然后，它将根据模板中的条件加载此订单的所有项目。
    * @return list of entities.
    */
    List<MerchantInfoDto> loadAll();

    /**
    * 根据条件分页加载加载所有实体，该条件来自map
    * @param query 查询对象
    * @return list of entities.
    */
    PageResult<MerchantInfoDto> loadList(MerchantInfoQuery query);



//    /**
//    * 根据条件加载实体
//    * @param countryCode 查询对象
//    * @return entity
//    */
//    MerchantInfoVo loadById( String countryCode,String registerPhone );

    /**
     * 根据条件加载实体
     * @param countryCode 查询对象
     * @return entity
     */
    MerchantInfoVo getMerchantInfoByMobile( String countryCode,String registerPhone );

    /**
     * 根据userId查询商户
     * @param userId 查询对象
     * @return entity
     */
    MerchantInfoVo findByUserId( String userId );

    /**
     * 根据域名查询商户
     * @param domain 查询对象
     * @return entity
     */
    MerchantInfoVo findByDomain( String domain );



    /**
     * 根据域名获取商户信息
     * @param domainName 查询对象
     * @return entity
     */
    MerchantInfoVo getMerchantInfoByDomainName( String domainName );


    /**
     * 根据名称获取商户信息
     * @param name 查询对象
     * @return entity
     */
    List<MerchantInfoVo> getMerchantInfoByName( String name);
}