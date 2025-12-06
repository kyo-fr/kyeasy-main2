package org.ares.cloud.merchantInfo.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.merchantInfo.convert.MerchantBannerConvert;
import org.ares.cloud.merchantInfo.dto.MerchantBannerDto;
import org.ares.cloud.merchantInfo.entity.MerchantBannerEntity;
import org.ares.cloud.merchantInfo.query.MerchantBannerQuery;
import org.ares.cloud.merchantInfo.repository.MerchantBannerRepository;
import org.ares.cloud.merchantInfo.service.MerchantBannerService;
import org.ares.cloud.merchantInfo.service.MerchantInfoService;
import org.ares.cloud.merchantInfo.vo.MerchantInfoVo;
import org.ares.cloud.redis.cache.RedisCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author hugo tangxkwork@163.com
 * @version 1.0.0
 * @description 轮播图 服务实现
 * @date 2025-03-18
 */
@Service
@AllArgsConstructor
@Slf4j
public class MerchantBannerServiceImpl extends BaseServiceImpl<MerchantBannerRepository, MerchantBannerEntity> implements MerchantBannerService {

    @Resource
    private MerchantBannerConvert convert;


    @Resource
    private MerchantInfoService merchantInfoService;

    @Resource
    private RedisCache redisCache;

    /**
     * 创建
     *
     * @param dto 数据模型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantBannerDto dto) {
        ApplicationContext.setIgnoreTenant(true);
        MerchantBannerEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
     * 批量创建
     *
     * @param dos 数据模型集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantBannerDto> dos) {
        List<MerchantBannerEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities);
    }

    /**
     * 更新
     *
     * @param dto 数据模型
     */
    @Override
    public void update(MerchantBannerDto dto) {
        MerchantBannerEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
     * 批量更新
     *
     * @param dos 数据模型集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantBannerDto> dos) {
        List<MerchantBannerEntity> entities = convert.listToEntities(dos);
        this.saveOrUpdateBatch(entities);
    }

    /**
     * 根据id删除
     *
     * @param id 主键
     */
    @Override
    public void deleteById(String id) {
        this.baseMapper.deleteById(id);
    }

    /**
     * 根据ids删除
     *
     * @param ids 主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        this.baseMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id获取详情
     *
     * @param id 主键
     */
    @Override
    public MerchantBannerDto loadById(String id) {
        MerchantBannerEntity entity = this.baseMapper.selectById(id);
        if (entity != null) {
            return convert.toDto(entity);
        }
        return null;
    }

    /**
     * 根据id获取详情
     *
     * @param ids 主键
     */
    @Override
    public List<MerchantBannerDto> loadByIds(List<String> ids) {
        List<MerchantBannerEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
     * 加载所有数据
     *
     * @return 数据集合
     */
    @Override
    public List<MerchantBannerDto> loadAll() {
        LambdaQueryWrapper<MerchantBannerEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantBannerEntity> entities = this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @return
     */
    @Override
    public PageResult<MerchantBannerDto> loadList(MerchantBannerQuery query, String domainName) {
        getMerchantInfo(query, domainName);
        IPage<MerchantBannerEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantBannerDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
     * 获取条件
     *
     * @param query
     * @return
     */
    private Wrapper<MerchantBannerEntity> getWrapper(MerchantBannerQuery query) {
        LambdaQueryWrapper<MerchantBannerEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getOrder())) {
            wrapper.orderByDesc(MerchantBannerEntity::getId);
        }
        if (StringUtils.isNotBlank(query.getTenantId())) {
            wrapper.and(w ->
                    w.like(MerchantBannerEntity::getTenantId, query.getTenantId())
            );
        }
        return wrapper;
    }

//    private void getMerchantInfo(MerchantBannerQuery query, String domainName) {
//        if (query.getTenantId() == null || StringUtils.isBlank(query.getTenantId()) ) {
//            MerchantInfoVo merchantInfoByDomainName = merchantInfoService.getMerchantInfoByDomainName(domainName);
//            if (merchantInfoByDomainName != null) {
//                query.setTenantId(merchantInfoByDomainName.getId());
//            }
//        }
//    }



    private void getMerchantInfo(MerchantBannerQuery query, String domainName) {
        if (query.getTenantId() == null || StringUtils.isBlank(query.getTenantId()) ) {
            //先去redis中查询
            Object domainNameValue = redisCache.get(domainName);
            if (domainNameValue != null) {
                log.info("getMerchantInfo...redis中查询商户信息domainNameValue：{}", domainNameValue);
                query.setTenantId(domainNameValue.toString());
            } else {
                MerchantInfoVo merchantInfoByDomain = merchantInfoService.getMerchantInfoByDomainName(domainName);
                log.info("getMerchantInfo...商户信息查询结果：{}", merchantInfoByDomain);
                if (merchantInfoByDomain != null) {
                    query.setTenantId(merchantInfoByDomain.getId());
                    redisCache.set(domainName, merchantInfoByDomain.getId(), RedisCache.DEFAULT_EXPIRE);
                }
            }
        }
    }
}