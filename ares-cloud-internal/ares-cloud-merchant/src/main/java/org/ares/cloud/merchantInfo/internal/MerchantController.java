package org.ares.cloud.merchantInfo.internal;

import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.annotation.Resource;
import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.exception.RpcCallException;
import org.ares.cloud.merchantInfo.entity.MerchantInfoEntity;
import org.ares.cloud.merchantInfo.service.MerchantInfoService;
import org.ares.cloud.merchantInfo.vo.MerchantInfoVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author hugo
 * @version 1.0
 * @description: TODO
 * @date 2024/10/7 23:19
 */
@RestController
@RequestMapping("/internal/merchant/v1")
public class MerchantController {

    @Resource
    private MerchantInfoService merchantInfoService;

    /**
     * 查询商户
     * @param id 主键
     */
    @Hidden
    @GetMapping("findById")
    public ResponseEntity<MerchantInfo> findById(@RequestParam String id){
        try {
            MerchantInfoEntity merchantInfo = merchantInfoService.getById(id);
            if (merchantInfo == null){
                return ResponseEntity.notFound().build();
            }
            MerchantInfo res = new MerchantInfo();
            res.setCurrencyScale(2);
            BeanUtil.copyProperties(merchantInfo,res);
            return ResponseEntity.ok(res);
        } catch (Exception e){
            throw new RpcCallException(e);
        }
    }

    /**
     * 根据userId查询商户
     * @param userId 主键
     */
    @Hidden
    @GetMapping("findByUserId")
    public ResponseEntity<MerchantInfo> findByUserId(@RequestParam String userId){
        try {
            MerchantInfoVo merchantInfo = merchantInfoService.findByUserId(userId);
            if (merchantInfo == null){
                return ResponseEntity.notFound().build();
            }
            MerchantInfo res = new MerchantInfo();
            res.setCurrencyScale(2);
            BeanUtil.copyProperties(merchantInfo,res);
            return ResponseEntity.ok(res);
        } catch (Exception e){
            throw new RpcCallException(e);
        }
    }

    /**
     * 根据域名查询商户
     * @param domain 主键
     */
    @Hidden
    @GetMapping("findByDomain")
    public ResponseEntity<MerchantInfo> findByDomain(@RequestParam String domain){
        try {
            MerchantInfoVo merchantInfo = merchantInfoService.findByDomain(domain);
            if (merchantInfo == null){
                return ResponseEntity.notFound().build();
            }
            MerchantInfo res = new MerchantInfo();
            res.setCurrencyScale(2);
            BeanUtil.copyProperties(merchantInfo,res);
            return ResponseEntity.ok(res);
        } catch (Exception e){
            throw new RpcCallException(e);
        }
    }

    /**
     * 根据商户名查询商户
     *
     * @param name
     */
    @Hidden
    @GetMapping("findByName")
    public ResponseEntity<List<MerchantInfo>> findByName(@RequestParam String name){
        try {
            List<MerchantInfoVo> merchantInfoByName = merchantInfoService.getMerchantInfoByName(name);

            // 查询不到数据时返回空列表，而不是404
            if (merchantInfoByName == null || merchantInfoByName.isEmpty()){
                return ResponseEntity.ok(List.of()); // 返回200状态码和空列表
            }

            // 转换为API DTO
            List<MerchantInfo> result = merchantInfoByName.stream()
                .map(vo -> {
                    MerchantInfo info = new MerchantInfo();
                    BeanUtil.copyProperties(vo, info);
                    return info;
                })
                .toList();

            return ResponseEntity.ok(result);
        } catch (Exception e){
            throw new RpcCallException(e);
        }
    }
}