package org.ares.cloud.product.service.impl;


import org.ares.cloud.product.convert.ProductSubSpecificationConvert;
import org.ares.cloud.product.dto.ProductSubSpecificationDto;
import org.ares.cloud.product.entity.ProductSpecificationEntity;
import org.ares.cloud.product.entity.ProductSubSpecificationEntity;
import org.ares.cloud.product.query.ProductSubSpecificationQuery;
import org.ares.cloud.product.repository.ProductSubSpecificationRepository;
import org.ares.cloud.product.service.ProductSubSpecificationService;
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
* @description 商品子规格 服务实现
* @version 1.0.0
* @date 2025-03-24
*/
@Service
@AllArgsConstructor
public class ProductSubSpecificationServiceImpl extends BaseServiceImpl<ProductSubSpecificationRepository, ProductSubSpecificationEntity> implements ProductSubSpecificationService{

    @Resource
    private ProductSubSpecificationConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ProductSubSpecificationDto dto) {
        ProductSubSpecificationEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<ProductSubSpecificationDto> dos) {
        List<ProductSubSpecificationEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(ProductSubSpecificationDto dto) {
        ProductSubSpecificationEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<ProductSubSpecificationDto> dos) {
        List<ProductSubSpecificationEntity> entities = convert.listToEntities(dos);
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
    public ProductSubSpecificationDto loadById(String id) {
        ProductSubSpecificationEntity entity = this.baseMapper.selectById(id);
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
    public List<ProductSubSpecificationDto> loadByIds(List<String> ids) {
        List<ProductSubSpecificationEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<ProductSubSpecificationDto> loadAll() {
        LambdaQueryWrapper<ProductSubSpecificationEntity> wrapper = new LambdaQueryWrapper<>();
        List<ProductSubSpecificationEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<ProductSubSpecificationDto> loadList(ProductSubSpecificationQuery query) {
        IPage<ProductSubSpecificationEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<ProductSubSpecificationDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<ProductSubSpecificationEntity> getWrapper(ProductSubSpecificationQuery query){
        LambdaQueryWrapper<ProductSubSpecificationEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getOrder())){
            wrapper.orderByDesc(ProductSubSpecificationEntity::getId);
        }
        if (StringUtils.isNotBlank(query.getProductId())){
            wrapper.eq(ProductSubSpecificationEntity::getProductId, query.getProductId());
        }
        return wrapper;
    }
}