package org.ares.cloud.merchantInfo.service.impl;


import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.api.user.enums.UserIdentity;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.merchantInfo.convert.MerchantFreightConvert;
import org.ares.cloud.merchantInfo.dto.MerchantFreightDto;
import org.ares.cloud.merchantInfo.entity.MerchantFreightEntity;
import org.ares.cloud.merchantInfo.entity.MerchantHoliDayEntity;
import org.ares.cloud.merchantInfo.entity.MerchantKeyWordsEntity;
import org.ares.cloud.merchantInfo.entity.OpeningHoursEntity;
import org.ares.cloud.merchantInfo.enums.MerchantError;
import org.ares.cloud.merchantInfo.query.MerchantFreightQuery;
import org.ares.cloud.merchantInfo.repository.MerchantFreightRepository;
import org.ares.cloud.merchantInfo.service.MerchantFreightService;
import org.ares.cloud.redis.cache.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 货运配送费用 服务实现
* @version 1.0.0
* @date 2024-11-05
*/
@Service
@AllArgsConstructor
public class MerchantFreightServiceImpl extends BaseServiceImpl<MerchantFreightRepository, MerchantFreightEntity> implements MerchantFreightService{

    @Resource
    private MerchantFreightConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantFreightDto dto) {
        ApplicationContext.setIgnoreTenant(true);
        //根据商户id和类型查询该数据是否存在
        MerchantFreightEntity entity = this.baseMapper.selectOne(new LambdaQueryWrapper<MerchantFreightEntity>()
                .eq(MerchantFreightEntity::getTenantId, dto.getTenantId())
                .eq(MerchantFreightEntity::getType, dto.getType()));
        if (entity != null){
            throw new RequestBadException(MerchantError.MERCHANT_FREIGHT_TYPE_EXIST_ERROR);
        }
        this.baseMapper.insert(convert.toEntity(dto));

//        ApplicationContext.setIgnoreTenant(true);
//        MerchantFreightEntity entity = convert.toEntity(dto);
//        //根据类型查询是否存在
//        MerchantFreightEntity freightEntity = baseMapper.selectOne(new LambdaQueryWrapper<MerchantFreightEntity>().
//                eq(MerchantFreightEntity::getTenantId, dto.getTenantId()).eq(MerchantFreightEntity::getType, dto.getType()));
////        MerchantFreightEntity freightEntity = baseMapper.selectById(dto.getTenantId());
//        entity.setId(dto.getTenantId());
//        if (freightEntity != null){
//            //更新
//            entity.setId(dto.getTenantId());
//            baseMapper.updateById(entity);
//        }else {
//            baseMapper.insert(entity);
//        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantFreightDto> dos) {
//        List<MerchantFreightEntity> entities = convert.listToEntities(dos);
//        this.saveBatch(entities);
        ApplicationContext.setIgnoreTenant(true);
        List<MerchantFreightEntity> entities = convert.listToEntities(dos);
        MerchantFreightEntity freightEntity =null;
        MD5 md5 = new MD5();
        for (MerchantFreightEntity entity : entities){
            String id = md5.digestHex(entity.getTenantId() + entity.getType());
            entity.setId(id);
            //根据商户id和type不为空查询是否已存在
            freightEntity = this.baseMapper.selectOne(new LambdaQueryWrapper<MerchantFreightEntity>()
                    .eq(MerchantFreightEntity::getId, id) );
            if (freightEntity  == null){
                baseMapper.insert(entity);
            }else {
                baseMapper.updateById(entity);
            }
        }
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantFreightDto dto) {
        //根据商户id和类型查询是否存在别的数据
        MerchantFreightEntity entity = this.baseMapper.selectOne(new LambdaQueryWrapper<MerchantFreightEntity>()
                .eq(MerchantFreightEntity::getTenantId, dto.getTenantId())
                .eq(MerchantFreightEntity::getType, dto.getType()));
        if (entity != null && !entity.getId().equals(dto.getId())){
            throw  new RequestBadException(MerchantError.MERCHANT_FREIGHT_TYPE_EXIST_ERROR);
        }
        this.updateById(convert.toEntity(dto));
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantFreightDto> dos) {
        ApplicationContext.setIgnoreTenant(true);
        List<MerchantFreightEntity> entities = convert.listToEntities(dos);
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
    public MerchantFreightDto loadById(String id) {
        MerchantFreightEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantFreightDto> loadByIds(List<String> ids) {
        List<MerchantFreightEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantFreightDto> loadAll() {
        LambdaQueryWrapper<MerchantFreightEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantFreightEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantFreightDto> loadList(MerchantFreightQuery query) {
        IPage<MerchantFreightEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantFreightDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    @Override
    public  List<MerchantFreightDto>  getInfoByTenantId(String tenantId) {
        List<MerchantFreightEntity> merchantFreightEntities = this.baseMapper.selectList(new LambdaQueryWrapper<MerchantFreightEntity>().eq(MerchantFreightEntity::getTenantId, tenantId));
        if (merchantFreightEntities != null){
            return convert.listToDto(merchantFreightEntities);
        }
        return null;
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantFreightEntity> getWrapper(MerchantFreightQuery query){
        LambdaQueryWrapper<MerchantFreightEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(query.getOrder())){
            wrapper.orderByDesc(MerchantFreightEntity::getId);
        }
        if (StringUtils.isNotBlank(query.getTenantId())){
            wrapper.eq(MerchantFreightEntity::getTenantId, query.getTenantId());
        }
        return wrapper;
    }
}