package com.ares.cloud.base.service.impl;


import com.ares.cloud.base.convert.MerchantPaymentChannelConvert;
import com.ares.cloud.base.dto.MerchantPaymentChannelDto;
import com.ares.cloud.base.command.MerchantPaymentChannelCommand;
import com.ares.cloud.base.entity.MerchantPaymentChannelEntity;
import com.ares.cloud.base.query.MerchantPaymentChannelQuery;
import com.ares.cloud.base.repository.MerchantPaymentChannelRepository;
import com.ares.cloud.base.service.MerchantPaymentChannelService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道 服务实现
* @version 1.0.0
* @date 2025-05-13
*/
@Service
@AllArgsConstructor
public class MerchantPaymentChannelServiceImpl extends BaseServiceImpl<MerchantPaymentChannelRepository, MerchantPaymentChannelEntity> implements MerchantPaymentChannelService{

    @Resource
    private MerchantPaymentChannelConvert convert;
    
    @Resource
    private MerchantPaymentChannelDeleteService deleteService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveChannel(MerchantPaymentChannelCommand dto) {
        // todo 需要校验渠道存不存在
        // 先删除商户的所有支付渠道（调用独立服务的方法）
        deleteService.deleteChannelsByMerchantId(dto.getMerchantId());
        List<MerchantPaymentChannelEntity> entities = new ArrayList<>();
        if (dto.getOnlinePaymentKeys() != null){
            for (String key : dto.getOnlinePaymentKeys()) {
                MerchantPaymentChannelEntity entity = new MerchantPaymentChannelEntity();
                entity.setChannelKey(key);
                entity.setChannelType(1);
                entity.setMerchantId(dto.getMerchantId());
                entity.setStatus(1);
                entities.add(entity);
            }
        }
        if (dto.getOfflinePaymentKeys() != null){
            for (String key : dto.getOfflinePaymentKeys()) {
                MerchantPaymentChannelEntity entity = new MerchantPaymentChannelEntity();
                entity.setChannelKey(key);
                entity.setChannelType(2);
                entity.setMerchantId(dto.getMerchantId());
                entity.setStatus(1);
                entities.add(entity);
            }
        }
        if (!entities.isEmpty()){
            // 批量保存
            this.saveBatch(entities);
        }
    }

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantPaymentChannelDto dto) {
        MerchantPaymentChannelEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantPaymentChannelDto> dos) {
        List<MerchantPaymentChannelEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantPaymentChannelDto dto) {
        MerchantPaymentChannelEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantPaymentChannelDto> dos) {
        List<MerchantPaymentChannelEntity> entities = convert.listToEntities(dos);
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
    public MerchantPaymentChannelDto loadById(String id) {
        MerchantPaymentChannelEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantPaymentChannelDto> loadByIds(List<String> ids) {
        List<MerchantPaymentChannelEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantPaymentChannelDto> loadAll(MerchantPaymentChannelQuery query) {
        LambdaQueryWrapper<MerchantPaymentChannelEntity> wrapper = new LambdaQueryWrapper<>();
        if (query != null){
            if (query.getChannelType() != null){
                wrapper.eq(MerchantPaymentChannelEntity::getChannelType, query.getChannelType());
            }
            if (query.getMerchantId() != null){
                wrapper.eq(MerchantPaymentChannelEntity::getMerchantId, query.getMerchantId());
            }
        }
        List<MerchantPaymentChannelEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    // 删除操作已移至独立服务 MerchantPaymentChannelDeleteService
}