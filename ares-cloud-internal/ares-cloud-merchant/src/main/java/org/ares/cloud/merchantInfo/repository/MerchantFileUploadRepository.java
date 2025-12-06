package org.ares.cloud.merchantInfo.repository;

import org.ares.cloud.merchantInfo.entity.MerchantFileUploadEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author hugo tangxkwork@163.com
* @description 商户文件上传 数据仓库
* @version 1.0.0
* @date 2024-10-09
*/
@Mapper
public interface MerchantFileUploadRepository extends BaseMapper<MerchantFileUploadEntity> {
	
}