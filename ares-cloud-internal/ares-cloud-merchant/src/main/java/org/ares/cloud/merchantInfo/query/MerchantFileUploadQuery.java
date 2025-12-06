package org.ares.cloud.merchantInfo.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;


/**
* @author hugo tangxkwork@163.com
* @description 商户文件上传 查询原型
* @version 1.0.0
* @date 2024-10-09
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "商户文件上传查询")
public class MerchantFileUploadQuery extends Query {
}