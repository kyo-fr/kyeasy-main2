package org.ares.cloud.product.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.api.product.ProductClient;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.product.convert.MerchantSubSpecificationConvert;
import org.ares.cloud.product.dto.MerchantSpecificationDto;
import org.ares.cloud.product.dto.MerchantSubSpecificationDto;
import org.ares.cloud.product.entity.MerchantSubSpecificationEntity;
import org.ares.cloud.product.enums.ProductError;
import org.ares.cloud.product.query.MerchantSubSpecificationQuery;
import org.ares.cloud.product.repository.MerchantSubSpecificationRepository;
import org.ares.cloud.product.service.MerchantSpecificationService;
import org.ares.cloud.product.service.MerchantSubSpecificationService;
import org.ares.cloud.product.vo.MerchantSubSpecificationVo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 子规格主数据 服务实现
* @version 1.0.0
* @date 2025-03-18
*/
@Service
@AllArgsConstructor
public class MerchantSubSpecificationServiceImpl extends BaseServiceImpl<MerchantSubSpecificationRepository, MerchantSubSpecificationEntity> implements MerchantSubSpecificationService{

    @Resource
    private MerchantSubSpecificationConvert convert;

    @Resource
    private MerchantSpecificationService merchantSpecificationService;

    @Resource
    private ProductClient productClient; ;


    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantSubSpecificationDto dto) {
        QueryWrapper<MerchantSubSpecificationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("sub_name", dto.getSubName());
        wrapper.eq("tenant_id", dto.getTenantId());
        wrapper.eq("specification_id", dto.getSpecificationId());
        MerchantSubSpecificationEntity merchantSubSpecificationEntity = baseMapper.selectOne(wrapper);
        if (merchantSubSpecificationEntity == null){
            ApplicationContext.setIgnoreTenant(true);
            MerchantSubSpecificationEntity entity = convert.toEntity(dto);
            this.baseMapper.insert(entity);
        }else {
            throw new RequestBadException(ProductError.MERCHANT_SPECIFICATION_NAME_EXIST_ERROR);
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantSubSpecificationDto> dos) {
        List<MerchantSubSpecificationEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantSubSpecificationDto dto) {
//        this.updateById(entity);

        MerchantSubSpecificationEntity entity = convert.toEntity(dto);
        QueryWrapper<MerchantSubSpecificationEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sub_name", dto.getSubName());
        queryWrapper.eq("tenant_id", dto.getTenantId());
        queryWrapper.eq("specification_id", dto.getSpecificationId());
        MerchantSubSpecificationEntity merchantSubSpecificationEntity = baseMapper.selectOne(queryWrapper);
        if (merchantSubSpecificationEntity != null){
            if (merchantSubSpecificationEntity.getId().equals(dto.getId())){
                //可以更新
                this.updateById(entity);
            }else {
                throw new RequestBadException(ProductError.MERCHANT_SPECIFICATION_NAME_EXIST_ERROR);
            }
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
    public void update(List<MerchantSubSpecificationDto> dos) {
        List<MerchantSubSpecificationEntity> entities = convert.listToEntities(dos);
        this.saveOrUpdateBatch(entities);
    }

    /**
    * 根据id删除
    * @param id  主键
    */
    @Override
    public void deleteById(String id) {
        MerchantSubSpecificationEntity merchantSubSpecificationEntity = baseMapper.selectById(id);
        if (merchantSubSpecificationEntity != null){
            String specificationId = merchantSubSpecificationEntity.getSpecificationId();
            //判断主规格是否删除
            MerchantSpecificationDto merchantSpecificationDto = merchantSpecificationService.loadById(specificationId);
            if (merchantSpecificationDto != null){
                //主规格未删除异常
                throw new RequestBadException(ProductError.MERCHANT_SUB_SPECIFICATION_IS_NOT_DELETE_ERROR);
            }
            //删除前判断商品是否已下架
            String result = productClient.checkProductIsDelete(specificationId, "subSpecification");
            if ("deleted".equals(result)) {
                this.baseMapper.deleteById(id);
            } else {
                //商品未下架异常
                throw new RequestBadException(ProductError.PRODUCT_IS_ENABLE_ERROR);
            }
        }
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
    public MerchantSubSpecificationDto loadById(String id) {
        MerchantSubSpecificationEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantSubSpecificationDto> loadByIds(List<String> ids) {
        List<MerchantSubSpecificationEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantSubSpecificationDto> loadAll() {
        LambdaQueryWrapper<MerchantSubSpecificationEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantSubSpecificationEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantSubSpecificationDto> loadList(MerchantSubSpecificationQuery query) {
        IPage<MerchantSubSpecificationEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        if(CollectionUtil.isEmpty(page.getRecords())){
            return new PageResult<>(new ArrayList<>(), 0);
        }
        List<MerchantSubSpecificationVo>  list = new ArrayList<>();
        for(MerchantSubSpecificationEntity entity : page.getRecords()){
            MerchantSubSpecificationVo vo = new MerchantSubSpecificationVo();
            //根据主规格id查询名称
            MerchantSpecificationDto productSpecificationDto = merchantSpecificationService.loadById(entity.getSpecificationId());
            if (productSpecificationDto != null){
                vo.setSpecificationName(productSpecificationDto.getName());
                vo.setSpecificationId(productSpecificationDto.getId());
                list.add(vo);
            }
            vo.setId(entity.getId());
            vo.setCreateTime(entity.getCreateTime());
            vo.setSubName(entity.getSubName());
            vo.setSubPicture(entity.getSubPicture());
            vo.setSubPrice(entity.getSubPrice());
            vo.setSubNum(entity.getSubNum());
        }
        return new PageResult<MerchantSubSpecificationDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantSubSpecificationEntity> getWrapper(MerchantSubSpecificationQuery query){
        LambdaQueryWrapper<MerchantSubSpecificationEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(query.getOrder())){
            wrapper.orderByDesc(MerchantSubSpecificationEntity::getId);
        }
        if (StringUtils.isNotBlank(query.getSpecificationId())) {
            wrapper.and(w ->
                    w.like(MerchantSubSpecificationEntity::getSpecificationId, query.getSpecificationId())
            );
        }
        return wrapper;
    }
}