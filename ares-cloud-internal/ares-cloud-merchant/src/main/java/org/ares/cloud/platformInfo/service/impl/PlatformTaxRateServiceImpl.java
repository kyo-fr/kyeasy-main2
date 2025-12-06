package org.ares.cloud.platformInfo.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.platformInfo.convert.PlatformTaxRateConvert;
import org.ares.cloud.platformInfo.dto.PlatformTaxRateDto;
import org.ares.cloud.platformInfo.entity.PlatformTaxRateEntity;
import org.ares.cloud.platformInfo.enums.PlatformError;
import org.ares.cloud.platformInfo.mapper.OrderMapper;
import org.ares.cloud.platformInfo.mapper.ProductBaseInfoMapper;
import org.ares.cloud.platformInfo.query.PlatformTaxRateQuery;
import org.ares.cloud.platformInfo.repository.PlatformTaxRateRepository;
import org.ares.cloud.platformInfo.service.PlatformTaxRateService;
import org.ares.cloud.redis.cache.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 税率 服务实现
* @version 1.0.0
* @date 2024-10-15
*/
@Service
@AllArgsConstructor
public class PlatformTaxRateServiceImpl extends BaseServiceImpl<PlatformTaxRateRepository, PlatformTaxRateEntity> implements PlatformTaxRateService{
    @Resource
    private RedisCache redisCache;

    @Resource
    private PlatformTaxRateConvert convert;

    @Resource
    private ProductBaseInfoMapper productBaseInfoMapper;

    @Resource
    private OrderMapper orderMapper;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PlatformTaxRateDto dto) {
        //如果税率类型是1/2/3 必须要传商户id
        if ("1".equals(dto.getType())   || "2".equals(dto.getType())  || "3".equals(dto.getType()) ){
            if (StringUtils.isBlank(dto.getTenantId())){
                log.warn("该类型税率需要传商户id："+dto.getType());
                throw new RequestBadException(PlatformError.PLATFORM_MERCHANT_ID_NOT_EXIST_ERROR);
            }
        }

        if ("1".equals(dto.getType()) ){
            //如果是商品税率 需要查询商户下是否超过了10条
            LambdaQueryWrapper<PlatformTaxRateEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PlatformTaxRateEntity::getTenantId, dto.getTenantId()).eq(PlatformTaxRateEntity::getType, "1");
            if (this.baseMapper.selectCount(wrapper) >= 10){
                log.warn("商户商品税率不能超过10条："+dto.getType());
                throw new RequestBadException(PlatformError.PLATFORM_MERCHANT_PRODUCT_TAX_RATE_EXCEED_ERROR);
            }
        }
        LambdaQueryWrapper<PlatformTaxRateEntity> wrapper = new LambdaQueryWrapper<>();
        //根据商户id和类型 判断是否已经存在
        wrapper.eq(PlatformTaxRateEntity::getType, dto.getType()).eq(PlatformTaxRateEntity::getTenantId, dto.getTenantId());
        if ("1".equals(dto.getType())){
            wrapper.eq(PlatformTaxRateEntity::getTaxRate, dto.getTaxRate());
            PlatformTaxRateEntity platformTaxRateEntity = this.baseMapper.selectOne(wrapper);
            if (platformTaxRateEntity != null){
                log.warn("该商户商品类型税率已存在："+dto.getType());
                throw  new RequestBadException(PlatformError.PLATFORM_TAX_TATE_TYPE_EXIST_ERROR);
            }
        }
        if ("2".equals(dto.getType())  || "3".equals(dto.getType()) ){
            PlatformTaxRateEntity platformTaxRateEntity = this.baseMapper.selectOne(wrapper);
            if (platformTaxRateEntity != null){
                log.warn("该商户类型枚举已存在："+dto.getType());
                throw  new RequestBadException(PlatformError.PLATFORM_MERCHANT_TYPE_EXIST_ERROR);
            }
        }
        if ("4".equals(dto.getType())  || "5".equals(dto.getType()) ){
            LambdaQueryWrapper<PlatformTaxRateEntity> wrappers = new LambdaQueryWrapper<>();
            wrappers.eq(PlatformTaxRateEntity::getType, dto.getType());
            PlatformTaxRateEntity platformTaxRateEntity = this.baseMapper.selectOne(wrappers);
            if (platformTaxRateEntity != null){
                log.warn("该平台类型税率已存在："+dto.getType());
                throw  new RequestBadException(PlatformError.PLATFORM_TAX_TATE_TYPE_EXIST_ERROR);
            }
        }
        ApplicationContext.setIgnoreTenant(true);
        PlatformTaxRateEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<PlatformTaxRateDto> dos) {
        List<PlatformTaxRateEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities);
    }

    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(PlatformTaxRateDto dto) {
        PlatformTaxRateEntity entity = convert.toEntity(dto);
        //如果税率类型是1/2/3 必须要传商户id
        if ("1".equals(dto.getType()) || "2".equals(dto.getType()) || "3".equals(dto.getType())) {
            if (StringUtils.isEmpty(dto.getTenantId())) {
                log.warn("该类型税率需要传商户id：" + dto.getType());
                throw new RequestBadException(PlatformError.PLATFORM_MERCHANT_ID_NOT_EXIST_ERROR);
            }
            //修改商户的商品税率需要查询商户下所有商品是否已经下架且订单不能有未结束和部分结算的订单
            int productCountByTenantId = productBaseInfoMapper.getProductCountByTenantId(dto.getTenantId());
            if (productCountByTenantId > 0) {
                log.warn("该商户下存在未下架的商品，不能修改税率：" + dto.getType());
                throw new RequestBadException(PlatformError.PLATFORM_MERCHANT_PRODUCT_IS_ENABLE_ERROR);
            }
            //查询商户是否存在订单数据
            int orderCountByTenantId = orderMapper.getOrderCountByTenantId(dto.getTenantId());
            if (orderCountByTenantId > 0) {
                log.warn("该商户下存在未结束的订单，不能修改税率：" + dto.getType());
                throw new RequestBadException(PlatformError.PLATFORM_MERCHANT_ORDER_UNSETTLED_ERROR);
            }
            //根据税率类型和租户id查询该商户下是否已存在该税率值
            LambdaQueryWrapper<PlatformTaxRateEntity> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PlatformTaxRateEntity::getType, dto.getType()).
                    eq(PlatformTaxRateEntity::getTenantId, dto.getTenantId()).
                    eq(PlatformTaxRateEntity::getTaxRate, dto.getTaxRate());
                PlatformTaxRateEntity platformTaxRateEntity = this.baseMapper.selectOne(wrapper);
                if (platformTaxRateEntity != null){
                    if (!platformTaxRateEntity.getId().equals(dto.getId())){
                        log.warn("该商户商品类型税率已存在："+dto.getType());
                        throw  new RequestBadException(PlatformError.PLATFORM_TAX_TATE_TYPE_EXIST_ERROR);
                    }
                }
            this.updateById(entity);
        }else {
            this.updateById(entity);
        }
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<PlatformTaxRateDto> dos) {
        List<PlatformTaxRateEntity> entities = convert.listToEntities(dos);
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
    public PlatformTaxRateDto loadById(String id) {
        PlatformTaxRateEntity entity = this.baseMapper.selectById(id);
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
    public List<PlatformTaxRateDto> loadByIds(List<String> ids) {
        List<PlatformTaxRateEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<PlatformTaxRateDto> loadAll() {
        LambdaQueryWrapper<PlatformTaxRateEntity> wrapper = new LambdaQueryWrapper<>();
        List<PlatformTaxRateEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<PlatformTaxRateDto> loadList(PlatformTaxRateQuery query,String domainName) {
        if (domainName != null){
            String tenantId = (String) redisCache.get(domainName);
            if (tenantId != null){
                query.setTenantId(tenantId);
            }
        }
        IPage<PlatformTaxRateEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<PlatformTaxRateDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<PlatformTaxRateEntity> getWrapper(PlatformTaxRateQuery query){
        LambdaQueryWrapper<PlatformTaxRateEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(query.getOrder())){
            wrapper.orderByDesc(PlatformTaxRateEntity::getId);
        }



        if (StringUtils.isNotEmpty(query.getTenantId())){
            wrapper.eq(PlatformTaxRateEntity::getTenantId, query.getTenantId());
        }

        if (StringUtils.isNotEmpty(query.getType())){
            wrapper.eq(PlatformTaxRateEntity::getType, query.getType());
        }
        return wrapper;
    }
}