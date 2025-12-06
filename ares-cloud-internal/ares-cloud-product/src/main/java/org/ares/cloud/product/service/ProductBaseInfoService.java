package org.ares.cloud.product.service;

import org.ares.cloud.product.dto.ProductDto;
import org.ares.cloud.product.dto.ProductBaseInfoDto;
import org.ares.cloud.product.entity.ProductBaseInfoEntity;
import org.ares.cloud.product.query.ProductBaseInfoQuery;
import org.ares.cloud.database.service.BaseService;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.product.query.ProductQuery;
import org.ares.cloud.product.vo.ProductBaseInfoVo;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商品基础信息 服务接口
* @version 1.0.0
* @date 2024-11-06
*/
public interface ProductBaseInfoService extends BaseService<ProductBaseInfoEntity> {
    /**
    * 将数据保存到仓库中.
    * @param dto  数据对象
    */
    void create(ProductBaseInfoDto dto);

    /**
    * 将数据保存到仓库中.
    * @param dos 数据对象集合
    */
    void create(List<ProductBaseInfoDto> dos);

    /**
    * 更新仓库数据.
    * @param dto 数据对象集合
    */
    void update(ProductBaseInfoDto dto);

    /**
    * 更新仓库数据.
    * @param dos 数据对象集合
    */
    void update(List<ProductBaseInfoDto> dos);

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
    ProductBaseInfoVo loadById(String id);

    /**
    * 按实体ID加载实体列表.
    * @param ids 实体的id
    * @return 集合是实体
    */
    List<ProductBaseInfoDto> loadByIds(List<String> ids);

    /**
    * 根据条件加载所有实体，该条件来自模板。
    * 例如：我想加载订单的所有项目，那么模板就是orderItem
    * 并将orderId设置为模板中的“orderId”列。
    * 然后，它将根据模板中的条件加载此订单的所有项目。
    * @return list of entities.
    */
    List<ProductBaseInfoDto> loadAll();

    /**
    * 根据条件分页加载加载所有实体，该条件来自map
    * @param query 查询对象
    * @return list of entities.
    */
    PageResult<ProductDto> loadList(ProductBaseInfoQuery query,String domainName);


    /**
     * 商品上下架
     * @param isEnable 上下架
     * @param productId 商品id
     */
    void updateProductByStatus(String isEnable,  String productId);


    /**
     * 检查商品是否下架
     * @param id 规格id、关键字id、标注id
     * @param type 规格关键字id、标注id
     * @return
     */
    boolean checkProductIsDelete(String id,String type);



    /**
     * 根据类型id获取该商户下的商品信息
     * @param query
     * @return
     */
    PageResult<ProductBaseInfoDto>  getProductInfoByTypeId(ProductBaseInfoQuery query,String domainName);


     void updateProductIsEnable( String productId);

}