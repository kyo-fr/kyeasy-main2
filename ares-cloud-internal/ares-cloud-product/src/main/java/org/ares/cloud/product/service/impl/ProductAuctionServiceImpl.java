package org.ares.cloud.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.product.convert.ProductAuctionConvert;
import org.ares.cloud.product.dto.ProductAuctionDto;
import org.ares.cloud.product.entity.ProductAuctionEntity;
import org.ares.cloud.product.entity.ProductBaseInfoEntity;
import org.ares.cloud.product.query.ProductAuctionQuery;
import org.ares.cloud.product.repository.ProductAuctionRepository;
import org.ares.cloud.product.service.ProductAuctionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 拍卖商品 服务实现
* @version 1.0.0
* @date 2024-11-08
*/
@Service
@AllArgsConstructor
public class ProductAuctionServiceImpl extends BaseServiceImpl<ProductAuctionRepository, ProductAuctionEntity> implements ProductAuctionService{

    @Resource
    private ProductAuctionConvert convert;

    @Resource
    private ProductServiceImpl productService;
    

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ProductAuctionDto dto) {
        ProductAuctionEntity entity = convert.toEntity(dto);
        int insert = this.baseMapper.insert(entity);
        if (insert > 0){
            ProductBaseInfoEntity byId = productService.getById(dto.getProductId());
            //1-普通商品;2-优惠商品;3-拍卖商品;4-批发商品
            byId.setType("auction");
            productService.update(byId);
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<ProductAuctionDto> dos) {
        List<ProductAuctionEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(ProductAuctionDto dto) {
        ProductAuctionEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<ProductAuctionDto> dos) {
        List<ProductAuctionEntity> entities = convert.listToEntities(dos);
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
    public ProductAuctionDto loadById(String id) {
        ProductAuctionEntity entity = this.baseMapper.selectById(id);
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
    public List<ProductAuctionDto> loadByIds(List<String> ids) {
        List<ProductAuctionEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<ProductAuctionDto> loadAll() {
        LambdaQueryWrapper<ProductAuctionEntity> wrapper = new LambdaQueryWrapper<>();
        List<ProductAuctionEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<ProductAuctionDto> loadList(ProductAuctionQuery query) {
        IPage<ProductAuctionEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<ProductAuctionDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<ProductAuctionEntity> getWrapper(ProductAuctionQuery query){
        LambdaQueryWrapper<ProductAuctionEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getOrder())){
            wrapper.orderByDesc(ProductAuctionEntity::getId);
        }
        return wrapper;
    }
}