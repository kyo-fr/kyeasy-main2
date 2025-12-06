package org.ares.cloud.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.product.convert.MerchantWarehouseConvert;
import org.ares.cloud.product.dto.MerchantWarehouseDto;
import org.ares.cloud.product.entity.MerchantWarehouseEntity;
import org.ares.cloud.product.entity.ProductBaseInfoEntity;
import org.ares.cloud.product.enums.ProductError;
import org.ares.cloud.product.query.MerchantWarehouseQuery;
import org.ares.cloud.product.repository.MerchantWarehouseRepository;
import org.ares.cloud.product.repository.ProductBaseInfoRepository;
import org.ares.cloud.product.service.MerchantWarehouseService;
import org.ares.cloud.product.vo.MerchantWarehouseVo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户仓库主数据 服务实现
* @version 1.0.0
* @date 2025-03-22
*/
@Service
@AllArgsConstructor
public class MerchantWarehouseServiceImpl extends BaseServiceImpl<MerchantWarehouseRepository, MerchantWarehouseEntity> implements MerchantWarehouseService{

    @Resource
    private MerchantWarehouseConvert convert;


    @Resource
    private ProductBaseInfoRepository productBaseInfoRepository;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantWarehouseDto dto) {
        ApplicationContext.setIgnoreTenant(true);
        MerchantWarehouseEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantWarehouseDto> dos) {
        List<MerchantWarehouseEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantWarehouseDto dto) {
        MerchantWarehouseEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantWarehouseDto> dos) {
        List<MerchantWarehouseEntity> entities = convert.listToEntities(dos);
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
    public MerchantWarehouseDto loadById(String id) {
        MerchantWarehouseEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantWarehouseDto> loadByIds(List<String> ids) {
        List<MerchantWarehouseEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantWarehouseDto> loadAll() {
        LambdaQueryWrapper<MerchantWarehouseEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantWarehouseEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantWarehouseVo> loadList(MerchantWarehouseQuery query) {
        IPage<MerchantWarehouseEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        List<MerchantWarehouseVo> voList = new ArrayList<>();
        if (page != null){
            page.getRecords().forEach(entity -> {
                MerchantWarehouseVo merchantWarehouseVo = new MerchantWarehouseVo();
                BeanUtils.copyProperties(entity, merchantWarehouseVo);
                //查询该商仓库下可用商品的库存
                QueryWrapper<ProductBaseInfoEntity> productWrapper = new QueryWrapper<>();
                productWrapper.eq("WAREHOUSE_ID", entity.getId());
                productWrapper.eq("DELETED", 0);
                productWrapper.eq("IS_ENABLE", "enable");
                List<ProductBaseInfoEntity> productBaseInfoEntities = productBaseInfoRepository.selectList(productWrapper);
                if (productBaseInfoEntities != null){
                    //获取到该仓库下的所有商品库存总和
                    merchantWarehouseVo.setInventory(productBaseInfoEntities.stream().mapToInt(ProductBaseInfoEntity::getInventory).sum());
                }
                voList.add(merchantWarehouseVo);
            });
         }
        return new PageResult<>(voList, page.getTotal());
    }

    @Override
    public void upsert(MerchantWarehouseDto dto) {
        ApplicationContext.setIgnoreTenant(true);
        if (StringUtils.isEmpty(dto.getTenantId())){
            dto.setTenantId(ApplicationContext.getTenantId());
        }
        //名称是否重复
        MerchantWarehouseEntity entity = this.baseMapper.selectOne(
                new LambdaQueryWrapper<MerchantWarehouseEntity>()
                        .eq(MerchantWarehouseEntity::getName, dto.getName())
                        .eq(MerchantWarehouseEntity::getTenantId, dto.getTenantId()));
        if (StringUtils.isNotEmpty(dto.getId())){
            if (entity != null && !entity.getId().equals(dto.getId())){
                throw new RequestBadException(ProductError.PRODUCT_WAREHOUSE_NAME_EXIST_ERROR);
            }
            this.updateById(convert.toEntity(dto));
        }else{
            if (entity != null){
                throw new RequestBadException(ProductError.PRODUCT_WAREHOUSE_NAME_EXIST_ERROR);
            }
            this.baseMapper.insert(convert.toEntity(dto));
        }
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantWarehouseEntity> getWrapper(MerchantWarehouseQuery query){
        LambdaQueryWrapper<MerchantWarehouseEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getOrder())){
            wrapper.orderByDesc(MerchantWarehouseEntity::getId);
        }
        if (StringUtils.isNotEmpty(query.getTenantId())){
            wrapper.eq(MerchantWarehouseEntity::getTenantId,query.getTenantId());
        }
        if (StringUtils.isNotEmpty(query.getKeyword())){
            wrapper.like(MerchantWarehouseEntity::getName, query.getKeyword()) .or().like(MerchantWarehouseEntity::getId, query.getKeyword());
        }
        return wrapper;
    }
}