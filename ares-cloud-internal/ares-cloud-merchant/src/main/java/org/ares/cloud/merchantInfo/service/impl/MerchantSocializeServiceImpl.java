package org.ares.cloud.merchantInfo.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.merchantInfo.convert.MerchantSocializeConvert;
import org.ares.cloud.merchantInfo.dto.MerchantSocializeDto;
import org.ares.cloud.merchantInfo.entity.MerchantSocializeEntity;
import org.ares.cloud.merchantInfo.query.MerchantSocializeQuery;
import org.ares.cloud.merchantInfo.repository.MerchantSocializeRepository;
import org.ares.cloud.merchantInfo.service.MerchantInfoService;
import org.ares.cloud.merchantInfo.service.MerchantSocializeService;
import org.ares.cloud.merchantInfo.vo.MerchantInfoVo;
import org.ares.cloud.redis.cache.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户社交 服务实现
* @version 1.0.0
* @date 2024-10-09
*/
@Service
@AllArgsConstructor
@Slf4j
public class MerchantSocializeServiceImpl extends BaseServiceImpl<MerchantSocializeRepository, MerchantSocializeEntity> implements MerchantSocializeService{
    @Resource
    private RedisCache redisCache;
    @Resource
    private MerchantSocializeConvert convert;
    @Resource
    private MerchantInfoService merchantInfoService;
    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantSocializeDto dto) {
        //先查询是否存在
        ApplicationContext.setIgnoreTenant(true);
        MerchantSocializeEntity entity = convert.toEntity(dto);
        //查询是否存在
        MerchantSocializeEntity merchantSocializeEntity = baseMapper.selectById(dto.getTenantId());
        entity.setId(dto.getTenantId());
        if (merchantSocializeEntity != null){
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
    public void create(List<MerchantSocializeDto> dos) {
        List<MerchantSocializeEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantSocializeDto dto) {
        MerchantSocializeEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantSocializeDto> dos) {
        List<MerchantSocializeEntity> entities = convert.listToEntities(dos);
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
    public MerchantSocializeDto loadById(String id) {
        MerchantSocializeEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantSocializeDto> loadByIds(List<String> ids) {
        List<MerchantSocializeEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantSocializeDto> loadAll() {
        LambdaQueryWrapper<MerchantSocializeEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantSocializeEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantSocializeDto> loadList(MerchantSocializeQuery query) {
        IPage<MerchantSocializeEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantSocializeDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    @Override
    public MerchantSocializeDto getMerchantSocializeByDomainName(String domainName) {
        //先去redis中查询
        Object domainNameValue = redisCache.get(domainName);
        String tenantId = "";
        if (domainNameValue != null) {
            log.info("getMerchantSocializeByDomainName...redis中查询商户信息domainNameValue：{}", domainNameValue);
            // 修复：从Redis获取的是商户ID字符串，需要根据ID查询商户社交信息
            tenantId = (String) domainNameValue;
        } else {
            MerchantInfoVo merchantInfoByDomain = merchantInfoService.getMerchantInfoByDomainName(domainName);
            log.info("getMerchantSocializeByDomainName...商户信息查询结果：{}", merchantInfoByDomain);
            if (merchantInfoByDomain != null) {
                redisCache.set(domainName, merchantInfoByDomain.getId(), RedisCache.DEFAULT_EXPIRE);
                //根据租户id查询对应的商户社交数据
                tenantId = merchantInfoByDomain.getId();
            }
        }
        MerchantSocializeEntity entity = baseMapper.selectById(tenantId);
        if (entity == null){
            return null;
        }
        return convert.toDto(entity);
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantSocializeEntity> getWrapper(MerchantSocializeQuery query){
        LambdaQueryWrapper<MerchantSocializeEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(MerchantSocializeEntity::getId);
        }
        return wrapper;
    }
}