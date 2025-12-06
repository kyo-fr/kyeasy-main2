package org.ares.cloud.product.service.impl;


import org.ares.cloud.product.convert.ProductMarkingConvert;
import org.ares.cloud.product.dto.ProductMarkingDto;
import org.ares.cloud.product.entity.ProductMarkingEntity;
import org.ares.cloud.product.query.ProductMarkingQuery;
import org.ares.cloud.product.repository.ProductMarkingRepository;
import org.ares.cloud.product.service.ProductMarkingService;
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
* @description 商品标注 服务实现
* @version 1.0.0
* @date 2024-11-08
*/
@Service
@AllArgsConstructor
public class ProductMarkingServiceImpl extends BaseServiceImpl<ProductMarkingRepository, ProductMarkingEntity> implements ProductMarkingService{

    @Resource
    private ProductMarkingConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ProductMarkingDto dto) {
        ProductMarkingEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<ProductMarkingDto> dos) {
        List<ProductMarkingEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(ProductMarkingDto dto) {
        ProductMarkingEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<ProductMarkingDto> dos) {
        List<ProductMarkingEntity> entities = convert.listToEntities(dos);
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
    public ProductMarkingDto loadById(String id) {
        ProductMarkingEntity entity = this.baseMapper.selectById(id);
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
    public List<ProductMarkingDto> loadByIds(List<String> ids) {
        List<ProductMarkingEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<ProductMarkingDto> loadAll() {
        LambdaQueryWrapper<ProductMarkingEntity> wrapper = new LambdaQueryWrapper<>();
        List<ProductMarkingEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<ProductMarkingDto> loadList(ProductMarkingQuery query) {
        IPage<ProductMarkingEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<ProductMarkingDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<ProductMarkingEntity> getWrapper(ProductMarkingQuery query){
        LambdaQueryWrapper<ProductMarkingEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(ProductMarkingEntity::getId);
        }
        if (StringUtils.isNotBlank(query.getProductId())){
            wrapper.eq(ProductMarkingEntity::getProductId, query.getProductId());
        }
        return wrapper;
    }
}