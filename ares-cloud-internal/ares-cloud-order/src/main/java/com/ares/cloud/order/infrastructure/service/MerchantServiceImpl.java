package com.ares.cloud.order.infrastructure.service;

import com.ares.cloud.order.domain.model.valueobject.MerchantInfo;
import com.ares.cloud.order.domain.service.MerchantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.api.merchant.MerchantClient;
import org.ares.cloud.api.merchant.dto.PlatformApprovalRecordDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2025/3/22 23:13
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {
    /**
     * 订单默认存储空间消耗
     */
    private static final Integer DEFAULT_ORDER_STORAGE = 10;
    /**
     * 商户服务
     */
    private final MerchantClient merchantClient;

    @Override
    public MerchantInfo findById(String Id) {
        org.ares.cloud.api.merchant.dto.MerchantInfo merchantInfoDto = merchantClient.getMerchantInfoById(Id);
        return buildMerchantInfo(merchantInfoDto);
    }

    @Override
    public MerchantInfo findPlatformById(String Id) {
        org.ares.cloud.api.merchant.dto.MerchantInfo merchantInfoDto = merchantClient.getPlatformInfoById(Id);
        return buildMerchantInfo(merchantInfoDto);
    }

    @Override
    public List<MerchantInfo> findByName(String name) {
        List<org.ares.cloud.api.merchant.dto.MerchantInfo> merchantInfoList = merchantClient.getMerchantInfoByName(name);
        return buildMerchantInfos(merchantInfoList);
    }

    /**
     * 构建商户信息
     * @param dto 商户信息DTO
     * @return 商户信息
     */
    private MerchantInfo buildMerchantInfo(org.ares.cloud.api.merchant.dto.MerchantInfo dto) {
        if (dto == null) {
            return null;
        }
        MerchantInfo merchantInfo = new MerchantInfo();
        merchantInfo.setId(dto.getId());
        merchantInfo.setCurrency(dto.getCurrency());
        merchantInfo.setCurrencyScale(2);
        merchantInfo.setTimezone("Asia/Shanghai");
        merchantInfo.setName(dto.getName());
        return merchantInfo;
    }

    /**
     * 构建商户信息列表
     * @param dtoList 商户信息DTO列表
     * @return 商户信息列表
     */
    private List<MerchantInfo> buildMerchantInfos(List<org.ares.cloud.api.merchant.dto.MerchantInfo> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return List.of(); // 返回空列表而不是null
        }

        return dtoList.stream()
                .map(dto -> {
                    MerchantInfo merchantInfo = new MerchantInfo();
                    merchantInfo.setId(dto.getId());
                    merchantInfo.setCurrency(dto.getCurrency());
                    merchantInfo.setCurrencyScale(2);
                    merchantInfo.setTimezone("Asia/Shanghai");
                    merchantInfo.setName(dto.getName());
                    return merchantInfo;
                })
                .toList();
    }

    @Override
    public boolean validateStorageSpace(String merchantId, Integer requiredStorage) {
        try {
            log.debug("校验商户存储空间，商户ID: {}, 需要存储空间: {}", merchantId, requiredStorage);
            if (requiredStorage == null) {
                requiredStorage = DEFAULT_ORDER_STORAGE;
            }
            // 获取可用存储空间
            Long availableStorage = merchantClient.getAvailableStorage(merchantId);
            
            if (availableStorage == null) {
                log.error("获取商户可用存储空间失败，商户ID: {}", merchantId);
                return false;
            }
            
            boolean hasEnoughStorage = availableStorage >= requiredStorage;
            
            log.debug("商户存储空间校验结果，商户ID: {}, 可用存储: {}, 需要存储: {}, 结果: {}", 
                    merchantId, availableStorage, requiredStorage, hasEnoughStorage);
            
            return hasEnoughStorage;
            
        } catch (Exception e) {
            log.error("校验商户存储空间异常，商户ID: {}, 需要存储空间: {}", merchantId, requiredStorage, e);
            return false;
        }
    }

    @Override
    public boolean consumeStorageSpace(String merchantId, String orderNo, Integer consumedStorage) {
        try {
            log.debug("消耗商户存储空间，商户ID: {}, 消耗存储空间: {}", merchantId, consumedStorage);
            if (consumedStorage == null) {
                consumedStorage = DEFAULT_ORDER_STORAGE;
            }
            // 构建存储消耗记录
            PlatformApprovalRecordDto recordDto = new PlatformApprovalRecordDto();
            recordDto.setChangeMemory(consumedStorage.longValue());
            recordDto.setDataSource("order");
            recordDto.setDataType("data");
            recordDto.setTenantId(merchantId);
            recordDto.setDescription(String.format("订单[%s]创建消耗存储空间", orderNo));
            
            // 消耗存储空间
            String result = merchantClient.updatePlatformApprovalRecord(recordDto);
            
            if (result == null) {
                log.error("消耗商户存储空间失败，商户ID: {}, 消耗存储空间: {}", merchantId, consumedStorage);
                return false;
            }
            
            boolean success = "success".equalsIgnoreCase(result);
            log.debug("商户存储空间消耗结果，商户ID: {}, 消耗存储空间: {}, 结果: {}", 
                    merchantId, consumedStorage, success);
            
            return success;
            
        } catch (Exception e) {
            log.error("消耗商户存储空间异常，商户ID: {}, 消耗存储空间: {}", merchantId, consumedStorage, e);
            return false;
        }
    }
}
