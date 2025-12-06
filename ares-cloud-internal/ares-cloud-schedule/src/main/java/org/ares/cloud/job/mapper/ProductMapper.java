package org.ares.cloud.job.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.ares.cloud.job.entity.ProductBaseInfoEntity;

import java.util.List;

@Mapper
public interface ProductMapper {

    //查询当前上架商品数据
    @Select("SELECT id,overdue_time,is_enable,tenant_id FROM product_base_info    WHERE is_enable = #{isEnable}   AND overdue_time BETWEEN #{now} AND #{threshold}  and    DELETED =  #{delete} ")
    List<ProductBaseInfoEntity> getProductsExpiringSoon(String isEnable, Long now, Long threshold,Integer delete);

    //查询当前库存不足商品数据
    @Select("SELECT id,is_enable,name FROM product_base_info    WHERE is_enable = #{isEnable}   AND  DELETED =  #{delete} and inventory <= #{inventory} ")
    List<ProductBaseInfoEntity> getProductsInsufficient(String isEnable,Integer delete,Integer inventory);

    //更新商品下架状态
    @Update("UPDATE product_base_info SET is_enable = #{isEnable} WHERE id = #{id}")
    void updateProductByEnable(String isEnable, String id);
}
