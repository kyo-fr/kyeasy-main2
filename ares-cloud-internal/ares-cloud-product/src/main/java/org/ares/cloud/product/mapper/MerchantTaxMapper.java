package org.ares.cloud.product.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;

@Mapper
public interface MerchantTaxMapper {
    //查询商户税率
    @Select("SELECT tax_rate FROM platform_tax_rate WHERE id = #{taxId}")
    BigDecimal getTaxRateByTaxId(String taxId);
}
