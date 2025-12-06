package org.ares.cloud.merchantInfo.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.api.user.enums.UserIdentity;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.merchantInfo.convert.MerchantAdvertisedConvert;
import org.ares.cloud.merchantInfo.dto.MerchantAdvertisedDto;
import org.ares.cloud.merchantInfo.dto.MerchantSocializeDto;
import org.ares.cloud.merchantInfo.entity.MerchantAdvertisedEntity;
import org.ares.cloud.merchantInfo.entity.MerchantFileUploadEntity;
import org.ares.cloud.merchantInfo.entity.MerchantHoliDayEntity;
import org.ares.cloud.merchantInfo.enums.MerchantError;
import org.ares.cloud.merchantInfo.query.MerchantAdvertisedQuery;
import org.ares.cloud.merchantInfo.repository.MerchantAdvertisedRepository;
import org.ares.cloud.merchantInfo.service.MerchantAdvertisedService;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.ares.cloud.merchantInfo.service.MerchantInfoService;
import org.ares.cloud.merchantInfo.vo.MerchantInfoVo;
import org.ares.cloud.redis.cache.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author hugo tangxkwork@163.com
* @description 商户广告 服务实现
* @version 1.0.0
* @date 2025-01-03
*/
@Service
@AllArgsConstructor
@Slf4j
public class MerchantAdvertisedServiceImpl extends BaseServiceImpl<MerchantAdvertisedRepository, MerchantAdvertisedEntity> implements MerchantAdvertisedService{

    @Resource
    private MerchantAdvertisedConvert convert;
    @Resource
    private  RedisCache redisCache;

    @Resource
    private MerchantInfoService merchantInfoService;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantAdvertisedDto dto) {
        ApplicationContext.setIgnoreTenant(true);
        MerchantAdvertisedEntity entity = convert.toEntity(dto);
        //查询是否存在
        MerchantAdvertisedEntity merchantAdvertisedEntity = baseMapper.selectById(dto.getTenantId());
        entity.setId(dto.getTenantId());
        if (merchantAdvertisedEntity != null){
            //更新
            entity.setId(dto.getTenantId());
            baseMapper.updateById(entity);
        }else {
            baseMapper.insert(entity);
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantAdvertisedDto> dos) {
        List<MerchantAdvertisedEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantAdvertisedDto dto) {
        MerchantAdvertisedEntity entity = convert.toEntity(dto);
        String key = "merchant_advertisedCount";
        if (ApplicationContext.getIdentity() == UserIdentity.Merchants.getValue() ) {
            Object countsObj = redisCache.get(key);
            int count = 0;
            if (countsObj != null) {
                if (countsObj instanceof Integer) {
                    count = (Integer) countsObj;
                } else if (countsObj instanceof String) {
                    try {
                        count = Integer.parseInt((String) countsObj);
                    } catch (NumberFormatException e) {
                        throw new RequestBadException(MerchantError.MERCHANT_UPDATE_ADVERTISED_COUNT_MORE_ERROR);
                    }
                } else {
                    throw new RequestBadException(MerchantError.MERCHANT_UPDATE_ADVERTISED_COUNT_MORE_ERROR);
                }
            }
            if (count >= 2) {
                throw new RequestBadException(MerchantError.MERCHANT_UPDATE_ADVERTISED_COUNT_MORE_ERROR);
            } else {
                count = count + 1;
                redisCache.set(key, count, RedisCache.DEFAULT_EXPIRE);
            }
        } else {
            redisCache.set(key, 1, RedisCache.DEFAULT_EXPIRE);
        }
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantAdvertisedDto> dos) {
        List<MerchantAdvertisedEntity> entities = convert.listToEntities(dos);
        this.saveOrUpdateBatch(entities);
    }

    /**
    * 根据id删除
    * @param id  主键
    */
    @Override
    public void deleteById(String id) {
        this.baseMapper.deleteById(id);
    }

    /**
    * 根据ids删除
    * @param ids 主键集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        this.baseMapper.deleteBatchIds(ids);
    }

    /**
    * 根据id获取详情
    * @param id 主键
    */
    @Override
    public MerchantAdvertisedDto loadById(String id) {
        MerchantAdvertisedEntity entity = this.baseMapper.selectById(id);
        if (entity != null){
            return  convert.toDto(entity);
        }
        return null;
    }
    /**
    * 根据id获取详情
    * @param ids 主键
    */
    @Override
    public List<MerchantAdvertisedDto> loadByIds(List<String> ids) {
        List<MerchantAdvertisedEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantAdvertisedDto> loadAll() {
        LambdaQueryWrapper<MerchantAdvertisedEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantAdvertisedEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantAdvertisedDto> loadList(MerchantAdvertisedQuery query, String domainName) {
        IPage<MerchantAdvertisedEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantAdvertisedDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    @Override
    public MerchantAdvertisedDto getMerchantAdvertisedByDomainName(String domainName) {
        //先去redis中查询
        Object domainNameValue = redisCache.get(domainName);
        if (domainNameValue != null) {
            log.info("getMerchantAdvertisedByDomainName...redis中查询商户信息domainNameValue：{}", domainNameValue);
            return convert.toDto(baseMapper.selectById(domainNameValue.toString()));
        } else {
            MerchantInfoVo merchantInfoByDomain = merchantInfoService.getMerchantInfoByDomainName(domainName);
            log.info("getMerchantAdvertisedByDomainName...商户信息查询结果：{}", merchantInfoByDomain);
            if (merchantInfoByDomain != null) {
                redisCache.set(domainName, merchantInfoByDomain.getId(), RedisCache.DEFAULT_EXPIRE);
                //根据租户id查询对应的商户社交数据
                return convert.toDto(baseMapper.selectById(merchantInfoByDomain.getId()));
            }
        }
        return null;
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantAdvertisedEntity> getWrapper(MerchantAdvertisedQuery query){
        LambdaQueryWrapper<MerchantAdvertisedEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(MerchantAdvertisedEntity::getId);
        }
        return wrapper;
    }

}