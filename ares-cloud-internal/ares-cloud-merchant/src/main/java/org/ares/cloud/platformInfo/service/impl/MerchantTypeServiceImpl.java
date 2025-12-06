package org.ares.cloud.platformInfo.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.platformInfo.convert.MerchantTypeConvert;
import org.ares.cloud.platformInfo.dto.MerchantTypeDto;
import org.ares.cloud.platformInfo.entity.MerchantTypeEntity;
import org.ares.cloud.platformInfo.enums.PlatformError;
import org.ares.cloud.platformInfo.query.MerchantTypeQuery;
import org.ares.cloud.platformInfo.repository.MerchantTypeRepository;
import org.ares.cloud.platformInfo.service.MerchantTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户类型 服务实现
* @version 1.0.0
* @date 2024-10-15
*/
@Service
@AllArgsConstructor
public class MerchantTypeServiceImpl extends BaseServiceImpl<MerchantTypeRepository, MerchantTypeEntity> implements MerchantTypeService{

    @Resource
    private MerchantTypeConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantTypeDto dto) {
        QueryWrapper<MerchantTypeEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("type", dto.getType());
        MerchantTypeEntity merchantTypeEntity = baseMapper.selectOne(wrapper);
        if (merchantTypeEntity == null){
            MerchantTypeEntity entity = convert.toEntity(dto);
            this.baseMapper.insert(entity);
        }else {
            System.out.println("该商户类型枚举已存在："+dto.getType());
            throw  new RequestBadException(PlatformError.PLATFORM_MERCHANT_TYPE_EXIST_ERROR);
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantTypeDto> dos) {
        List<MerchantTypeEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantTypeDto dto) {
        QueryWrapper<MerchantTypeEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("type", dto.getType());
        MerchantTypeEntity merchantTypeEntity = baseMapper.selectOne(wrapper);
        MerchantTypeEntity entity = convert.toEntity(dto);
        if (merchantTypeEntity != null){
            if (merchantTypeEntity.getId().equals(dto.getId())){
                //可以更新
                this.updateById(entity);
            }else {
                System.out.println("该商户类型枚举已存在："+dto.getType());
                throw  new RequestBadException(PlatformError.PLATFORM_MERCHANT_TYPE_EXIST_ERROR);
            }
        }else{
            this.updateById(entity);
        }
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantTypeDto> dos) {
        List<MerchantTypeEntity> entities = convert.listToEntities(dos);
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
    public MerchantTypeDto loadById(String id) {
        MerchantTypeEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantTypeDto> loadByIds(List<String> ids) {
        List<MerchantTypeEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantTypeDto> loadAll() {
        LambdaQueryWrapper<MerchantTypeEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantTypeEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantTypeDto> loadList(MerchantTypeQuery query) {
        IPage<MerchantTypeEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantTypeDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantTypeEntity> getWrapper(MerchantTypeQuery query){
        LambdaQueryWrapper<MerchantTypeEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(MerchantTypeEntity::getId);
        }
        return wrapper;
    }
}