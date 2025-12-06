package org.ares.cloud.merchantInfo.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.merchantInfo.convert.MerchantMarkingConvert;
import org.ares.cloud.merchantInfo.dto.MerchantMarkingDto;
import org.ares.cloud.merchantInfo.entity.MerchantMarkingEntity;
import org.ares.cloud.merchantInfo.entity.MerchantSpecificationEntity;
import org.ares.cloud.merchantInfo.enums.MerchantError;
import org.ares.cloud.merchantInfo.query.MerchantMarkingQuery;
import org.ares.cloud.merchantInfo.repository.MerchantMarkingRepository;
import org.ares.cloud.merchantInfo.service.MerchantMarkingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 标注主数据 服务实现
* @version 1.0.0
* @date 2025-03-19
*/
@Service
@AllArgsConstructor
public class MerchantMarkingServiceImpl extends BaseServiceImpl<MerchantMarkingRepository, MerchantMarkingEntity> implements MerchantMarkingService{

    @Resource
    private MerchantMarkingConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantMarkingDto dto) {
        QueryWrapper<MerchantMarkingEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("name", dto.getName());
        wrapper.eq("tenant_id", dto.getTenantId());
        MerchantMarkingEntity merchantMarkingEntity = baseMapper.selectOne(wrapper);
        if (merchantMarkingEntity == null){
            ApplicationContext.setIgnoreTenant(true);
            MerchantMarkingEntity entity = convert.toEntity(dto);
            this.baseMapper.insert(entity);
        }else {
            throw new RequestBadException(MerchantError.MERCHANT_MARKING_NAME_EXIST_ERROR);
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantMarkingDto> dos) {
        List<MerchantMarkingEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantMarkingDto dto) {
        MerchantMarkingEntity entity = convert.toEntity(dto);
        QueryWrapper<MerchantMarkingEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", dto.getName());
        queryWrapper.eq("tenant_id", dto.getTenantId());
        MerchantMarkingEntity merchantMarkingEntity = baseMapper.selectOne(queryWrapper);
        if (merchantMarkingEntity != null){
            if (merchantMarkingEntity.getId().equals(dto.getId())){
                //可以更新
                this.updateById(entity);
            }else {
                throw new RequestBadException(MerchantError.MERCHANT_SPECIFICATION_NAME_EXIST_ERROR);
            }
        }else {
            this.updateById(entity);
        }
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantMarkingDto> dos) {
        List<MerchantMarkingEntity> entities = convert.listToEntities(dos);
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
    public MerchantMarkingDto loadById(String id) {
        MerchantMarkingEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantMarkingDto> loadByIds(List<String> ids) {
        List<MerchantMarkingEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantMarkingDto> loadAll() {
        LambdaQueryWrapper<MerchantMarkingEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantMarkingEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantMarkingDto> loadList(MerchantMarkingQuery query) {
        IPage<MerchantMarkingEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantMarkingDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantMarkingEntity> getWrapper(MerchantMarkingQuery query){
        LambdaQueryWrapper<MerchantMarkingEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(MerchantMarkingEntity::getId);
        }
        return wrapper;
    }
}