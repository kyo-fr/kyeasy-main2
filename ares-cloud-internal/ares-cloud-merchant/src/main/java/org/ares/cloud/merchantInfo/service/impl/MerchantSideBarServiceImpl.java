package org.ares.cloud.merchantInfo.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.merchantInfo.convert.MerchantSideBarConvert;
import org.ares.cloud.merchantInfo.convert.MerchantSocializeConvert;
import org.ares.cloud.merchantInfo.dto.MerchantSideBarDto;
import org.ares.cloud.merchantInfo.entity.MerchantSideBarEntity;
import org.ares.cloud.merchantInfo.query.MerchantSideBarQuery;
import org.ares.cloud.merchantInfo.repository.MerchantSideBarRepository;
import org.ares.cloud.merchantInfo.service.MerchantInfoService;
import org.ares.cloud.merchantInfo.service.MerchantSideBarService;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.ares.cloud.merchantInfo.vo.MerchantInfoVo;
import org.ares.cloud.redis.cache.RedisCache;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author hugo tangxkwork@163.com
* @description 商户侧栏 服务实现
* @version 1.0.0
* @date 2024-10-09
*/
@Service
@AllArgsConstructor
@Slf4j
public class MerchantSideBarServiceImpl extends BaseServiceImpl<MerchantSideBarRepository, MerchantSideBarEntity> implements MerchantSideBarService{

    @Resource
    private MerchantSideBarConvert convert;
    @Resource
    private RedisCache redisCache;
    @Resource
    private MerchantInfoService merchantInfoService;
    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantSideBarDto dto) {
        ApplicationContext.setIgnoreTenant(true);
        MerchantSideBarEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantSideBarDto> dos) {
        ApplicationContext.setIgnoreTenant(true);
        List<MerchantSideBarEntity> entities = convert.listToEntities(dos);
        MerchantSideBarEntity entity =null;
        for (MerchantSideBarEntity entity1 : entities){
            //根据商户id和code查询是否已存在
            entity = this.baseMapper.selectOne(new LambdaQueryWrapper<MerchantSideBarEntity>()
                    .eq(MerchantSideBarEntity::getTenantId, entity1.getTenantId())
                    .eq(MerchantSideBarEntity::getCode, entity1.getCode()));
            if (entity != null){
               break;
            }
        }
        if (entity == null){
            this.saveBatch(entities);
        }
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantSideBarDto dto) {
        MerchantSideBarEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantSideBarDto> dos) {
        ApplicationContext.setIgnoreTenant(true);
        List<MerchantSideBarEntity> entities = convert.listToEntities(dos);
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
    public MerchantSideBarDto loadById(String id) {
        MerchantSideBarEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantSideBarDto> loadByIds(List<String> ids) {
        List<MerchantSideBarEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantSideBarDto> loadAll() {
        LambdaQueryWrapper<MerchantSideBarEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantSideBarEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantSideBarDto> loadList(MerchantSideBarQuery query,String domainName) {
        getMerchantInfo(query, domainName);
        IPage<MerchantSideBarEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantSideBarDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    private void getMerchantInfo(MerchantSideBarQuery query, String domainName) {
        if (query.getTenantId() == null || StringUtils.isBlank(query.getTenantId())) {
            //查询redis
            Object domainNameValue = redisCache.get(domainName);
            if (domainNameValue != null) {
                log.info("getMerchantInfo...redis中查询商户信息domainNameValue：{}", domainNameValue);
                query.setTenantId(domainNameValue.toString());
            } else {
                MerchantInfoVo merchantInfoByDomainName = merchantInfoService.getMerchantInfoByDomainName(domainName);
                log.info("getMerchantInfo...商户信息查询结果：{}", merchantInfoByDomainName);
                    if (merchantInfoByDomainName != null) {
                        query.setTenantId(merchantInfoByDomainName.getId());
                        redisCache.set(domainName, merchantInfoByDomainName.getId(), RedisCache.DEFAULT_EXPIRE);
                }
            }
        }
    }
//    @Override
//    public List<MerchantSideBarDto> getMerchantSideBarByDomainName(String domainName) {
//        //先去redis中查询
//        Object domainNameValue = redisCache.get(domainName);
//        if (domainNameValue != null) {
//            log.info("getMerchantSideBarByDomainName...redis中查询商户信息domainNameValue：{}", domainNameValue);
//        } else {
//            MerchantInfoVo merchantInfoByDomain = merchantInfoService.getMerchantInfoByDomainName(domainName);
//            log.info("getMerchantSideBarByDomainName...商户信息查询结果：{}", merchantInfoByDomain);
//            if (merchantInfoByDomain != null) {
//                redisCache.set(domainName, merchantInfoByDomain.getId(), RedisCache.DEFAULT_EXPIRE);
//                //根据租户id查询对应的商户社交数据
//                return convert.listToDto(baseMapper.selectList(new LambdaQueryWrapper<MerchantSideBarEntity>().eq(MerchantSideBarEntity::getTenantId, merchantInfoByDomain.getId())));
//            }
//        }
//        return null;
//    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantSideBarEntity> getWrapper(MerchantSideBarQuery query){
        LambdaQueryWrapper<MerchantSideBarEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(MerchantSideBarEntity::getId);
        }
        if (StringUtils.isNotBlank(query.getKeyword())) {
            wrapper.and(w ->
                    w.like(MerchantSideBarEntity::getTenantId, query.getKeyword())
            );
        }
        if (query.getTenantId() != null){
            wrapper.eq(MerchantSideBarEntity::getTenantId, query.getTenantId());
        }
        return wrapper;
    }
}