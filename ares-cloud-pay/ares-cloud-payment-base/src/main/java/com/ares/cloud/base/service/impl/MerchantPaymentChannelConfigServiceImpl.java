package com.ares.cloud.base.service.impl;


import com.alibaba.fastjson.JSON;
import com.ares.cloud.base.command.MerchantBraintreePaymentConfigCommand;
import com.ares.cloud.base.convert.MerchantPaymentChannelConfigConvert;
import com.ares.cloud.base.dto.MerchantBraintreePaymentConfigDto;
import com.ares.cloud.base.dto.MerchantPaymentChannelConfigDto;
import com.ares.cloud.base.entity.MerchantPaymentChannelConfigEntity;
import com.ares.cloud.base.enums.PaymentType;
import com.ares.cloud.base.query.MerchantPaymentChannelConfigQuery;
import com.ares.cloud.base.repository.MerchantPaymentChannelConfigRepository;
import com.ares.cloud.base.service.MerchantPaymentChannelConfigService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户支付渠道配置 服务实现
* @version 1.0.0
* @date 2025-05-13
*/
@Service
public class MerchantPaymentChannelConfigServiceImpl extends BaseServiceImpl<MerchantPaymentChannelConfigRepository, MerchantPaymentChannelConfigEntity> implements MerchantPaymentChannelConfigService{

    @Resource
    private MerchantPaymentChannelConfigConvert convert;

    @Override
    public void saveBraintreePaymentConfig(MerchantBraintreePaymentConfigCommand command) {
        MerchantPaymentChannelConfigEntity entity = new MerchantPaymentChannelConfigEntity();
        entity.setMerchantId(command.getMerchantId());
        entity.setPaymentMerchant(PaymentType.Braintree.getKey());
        String configData = JSON.toJSONString(command);
        entity.setConfigData(configData);
        this.saveEntity(entity);
    }

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantPaymentChannelConfigDto dto) {
        MerchantPaymentChannelConfigEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantPaymentChannelConfigDto> dos) {
        List<MerchantPaymentChannelConfigEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantPaymentChannelConfigDto dto) {
        MerchantPaymentChannelConfigEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantPaymentChannelConfigDto> dos) {
        List<MerchantPaymentChannelConfigEntity> entities = convert.listToEntities(dos);
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
    public MerchantPaymentChannelConfigDto loadById(String id) {
        MerchantPaymentChannelConfigEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantPaymentChannelConfigDto> loadByIds(List<String> ids) {
        List<MerchantPaymentChannelConfigEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantPaymentChannelConfigDto> loadAll() {
        LambdaQueryWrapper<MerchantPaymentChannelConfigEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantPaymentChannelConfigEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantPaymentChannelConfigDto> loadList(MerchantPaymentChannelConfigQuery query) {
        IPage<MerchantPaymentChannelConfigEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantPaymentChannelConfigDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }
    /**
     * 根据商户ID和支付渠道key 查询支付渠道配置
     * @param merchantId 商户ID
     * @param paymentMerchant  渠道key
     * @return 配置
     */
    @Override
    public MerchantPaymentChannelConfigDto selectByMerchantIdAndPaymentMerchant(String merchantId, String paymentMerchant) {
        MerchantPaymentChannelConfigEntity merchantPaymentChannelConfigEntity = this.baseMapper.selectByMerchantIdAndPaymentMerchant(merchantId, paymentMerchant);
        if (merchantPaymentChannelConfigEntity != null) {
            return convert.toDto(merchantPaymentChannelConfigEntity);
        }
        return null;
    }
    /**
     * 获取商户braintree支付配置
     * @param merchantId 商户ID
     * @return 配置
     */
    @Override
    public MerchantBraintreePaymentConfigDto getBraintreePaymentConfig(String merchantId) {
        MerchantPaymentChannelConfigEntity merchantPaymentChannelConfigEntity = this.baseMapper.selectByMerchantIdAndPaymentMerchant(merchantId, PaymentType.Braintree.getKey());
        if (merchantPaymentChannelConfigEntity != null) {
            MerchantBraintreePaymentConfigDto merchantBraintreePaymentConfigDto = JSON.parseObject(merchantPaymentChannelConfigEntity.getConfigData(), MerchantBraintreePaymentConfigDto.class);
            merchantBraintreePaymentConfigDto.setMerchantId(merchantId);
            return merchantBraintreePaymentConfigDto;
        }
        return null;
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantPaymentChannelConfigEntity> getWrapper(MerchantPaymentChannelConfigQuery query){
        LambdaQueryWrapper<MerchantPaymentChannelConfigEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(MerchantPaymentChannelConfigEntity::getId);
        }
        return wrapper;
    }
    /**
     * 保存实体
     */
    private void saveEntity(MerchantPaymentChannelConfigEntity entity) {
        if (entity == null) {
            return;
        }
        MerchantPaymentChannelConfigEntity merchantPaymentChannelConfigEntity = this.baseMapper.selectByMerchantIdAndPaymentMerchant(entity.getMerchantId(), entity.getPaymentMerchant());
        if (merchantPaymentChannelConfigEntity != null) {
            entity.setId(merchantPaymentChannelConfigEntity.getId());
        }
        if (StringUtils.isBlank(entity.getId())) {
            this.baseMapper.insert(entity);
        } else {
            this.baseMapper.updateById(entity);
        }
    }
}