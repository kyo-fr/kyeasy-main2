package org.ares.cloud.product.service.impl;


import org.ares.cloud.product.convert.ProductBaseInfoConvert;
import org.ares.cloud.product.convert.ProductPreferentialConvert;
import org.ares.cloud.product.dto.ProductPreferentialDto;
import org.ares.cloud.product.entity.ProductBaseInfoEntity;
import org.ares.cloud.product.entity.ProductPreferentialEntity;
import org.ares.cloud.product.query.ProductPreferentialQuery;
import org.ares.cloud.product.repository.ProductPreferentialRepository;
import org.ares.cloud.product.service.ProductPreferentialService;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.ares.cloud.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author hugo tangxkwork@163.com
* @description 优惠商品 服务实现
* @version 1.0.0
* @date 2024-11-07
*/
@Service
@AllArgsConstructor
public class ProductPreferentialServiceImpl extends BaseServiceImpl<ProductPreferentialRepository, ProductPreferentialEntity> implements ProductPreferentialService{

    @Resource
    private ProductPreferentialConvert convert;

    @Resource
    private ProductService productService;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ProductPreferentialDto dto) {
        ProductPreferentialEntity entity = convert.toEntity(dto);
        int insert = this.baseMapper.insert(entity);
        if (insert > 0){
            ProductBaseInfoEntity byId = productService.getById(dto.getProductId());
            //1-普通商品;2-优惠商品;3-拍卖商品;4-批发商品
            byId.setType("preferential");
            productService.update(byId);
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<ProductPreferentialDto> dos) {
        List<ProductPreferentialEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(ProductPreferentialDto dto) {
        ProductPreferentialEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<ProductPreferentialDto> dos) {
        List<ProductPreferentialEntity> entities = convert.listToEntities(dos);
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
    public ProductPreferentialDto loadById(String id) {
        ProductPreferentialEntity entity = this.baseMapper.selectById(id);
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
    public List<ProductPreferentialDto> loadByIds(List<String> ids) {
        List<ProductPreferentialEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<ProductPreferentialDto> loadAll() {
        LambdaQueryWrapper<ProductPreferentialEntity> wrapper = new LambdaQueryWrapper<>();
        List<ProductPreferentialEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<ProductPreferentialDto> loadList(ProductPreferentialQuery query) {
        IPage<ProductPreferentialEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<ProductPreferentialDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<ProductPreferentialEntity> getWrapper(ProductPreferentialQuery query){
        LambdaQueryWrapper<ProductPreferentialEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(query.getOrder())){
            wrapper.orderByDesc(ProductPreferentialEntity::getId);
        }
        return wrapper;
    }
}