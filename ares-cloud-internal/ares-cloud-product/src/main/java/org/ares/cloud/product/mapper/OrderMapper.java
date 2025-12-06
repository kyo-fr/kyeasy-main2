package org.ares.cloud.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface OrderMapper {

    //根据商品id查询订单状态
    @Select("SELECT count(1) as counts FROM ORDER_ITEMS WHERE PRODUCT_ID = #{productId} and PAYMENT_STATUS not  in ('4','6','7')")
    int getOrderByProductId(String productId);
}
