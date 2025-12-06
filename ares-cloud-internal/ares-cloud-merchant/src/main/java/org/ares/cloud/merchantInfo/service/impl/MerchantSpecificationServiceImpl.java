package org.ares.cloud.merchantInfo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.merchantInfo.convert.MerchantSpecificationConvert;
import org.ares.cloud.merchantInfo.dto.MerchantSpecificationDto;
import org.ares.cloud.merchantInfo.entity.MerchantKeyWordsEntity;
import org.ares.cloud.merchantInfo.entity.MerchantSpecificationEntity;
import org.ares.cloud.merchantInfo.enums.MerchantError;
import org.ares.cloud.merchantInfo.query.MerchantSpecificationQuery;
import org.ares.cloud.merchantInfo.repository.MerchantSpecificationRepository;
import org.ares.cloud.merchantInfo.service.MerchantSpecificationService;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author hugo tangxkwork@163.com
* @description 主规格主数据 服务实现
* @version 1.0.0
* @date 2025-03-18
*/
@Service
@AllArgsConstructor
public class MerchantSpecificationServiceImpl extends BaseServiceImpl<MerchantSpecificationRepository, MerchantSpecificationEntity> implements MerchantSpecificationService{

    @Resource
    private MerchantSpecificationConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantSpecificationDto dto) {
        QueryWrapper<MerchantSpecificationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("name", dto.getName());
        wrapper.eq("tenant_id", dto.getTenantId());
        MerchantSpecificationEntity merchantSpecificationEntity = baseMapper.selectOne(wrapper);
        if (merchantSpecificationEntity == null){
            ApplicationContext.setIgnoreTenant(true);
            MerchantSpecificationEntity entity = convert.toEntity(dto);
            this.baseMapper.insert(entity);
        }else {
            throw new RequestBadException(MerchantError.MERCHANT_SPECIFICATION_NAME_EXIST_ERROR);
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantSpecificationDto> dos) {
        List<MerchantSpecificationEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantSpecificationDto dto) {
        MerchantSpecificationEntity entity = convert.toEntity(dto);
        QueryWrapper<MerchantSpecificationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", dto.getName());
        queryWrapper.eq("tenant_id", dto.getTenantId());
        MerchantSpecificationEntity specificationEntity = baseMapper.selectOne(queryWrapper);
        if (specificationEntity != null){
            if (specificationEntity.getId().equals(dto.getId())){
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
    public void update(List<MerchantSpecificationDto> dos) {
        List<MerchantSpecificationEntity> entities = convert.listToEntities(dos);
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
    public MerchantSpecificationDto loadById(String id) {
        MerchantSpecificationEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantSpecificationDto> loadByIds(List<String> ids) {
        List<MerchantSpecificationEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantSpecificationDto> loadAll() {
        LambdaQueryWrapper<MerchantSpecificationEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantSpecificationEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantSpecificationDto> loadList(MerchantSpecificationQuery query) {
        IPage<MerchantSpecificationEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantSpecificationDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantSpecificationEntity> getWrapper(MerchantSpecificationQuery query){
        LambdaQueryWrapper<MerchantSpecificationEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(MerchantSpecificationEntity::getId);
        }

//        if (StringUtils.isNotBlank(query.getTenantId())) {
//            wrapper.and(w ->
//                    w.like(MerchantSpecificationEntity::getTenantId, query.getTenantId())
//            );
//        }
        return wrapper;
    }
}