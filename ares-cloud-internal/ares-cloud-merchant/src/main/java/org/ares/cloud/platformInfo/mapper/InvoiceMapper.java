package org.ares.cloud.platformInfo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.ares.cloud.api.payment.command.InvoiceItemCommand;

@Mapper
public interface InvoiceMapper {

    //根据订单号查询发票信息
    @Select("select id from PAY_INVOICE where ORDER_ID = #{orderId}")
    String getInvoiceByOrderId(String orderId);

}
