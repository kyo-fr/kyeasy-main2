package org.ares.cloud.product.service;

import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.product.dto.ProductDto;
import org.ares.cloud.product.entity.ProductBaseInfoEntity;
import org.ares.cloud.product.query.ProductQuery;

//测试聚合
public interface ProductService {
    /**
     * 更新仓库数据.
     * @param dto 数据对象集合
     */
    void update(ProductBaseInfoEntity dto);



    /**
     * 加载用户信息
     * @param id 逐渐
     * @return 商品实体
     */
    ProductBaseInfoEntity getById(String id);


}
