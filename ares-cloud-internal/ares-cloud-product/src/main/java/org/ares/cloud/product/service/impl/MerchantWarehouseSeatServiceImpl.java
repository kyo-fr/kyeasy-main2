package org.ares.cloud.product.service.impl;


import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.product.convert.MerchantWarehouseSeatConvert;
import org.ares.cloud.product.dto.MerchantWarehouseSeatDto;
import org.ares.cloud.product.entity.MerchantMarkingEntity;
import org.ares.cloud.product.entity.MerchantWarehouseEntity;
import org.ares.cloud.product.entity.MerchantWarehouseSeatEntity;
import org.ares.cloud.product.enums.ProductError;
import org.ares.cloud.product.query.MerchantWarehouseSeatQuery;
import org.ares.cloud.product.repository.MerchantWarehouseSeatRepository;
import org.ares.cloud.product.service.MerchantWarehouseSeatService;
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

/**
* @author hugo tangxkwork@163.com
* @description 商户仓库位子主数据 服务实现
* @version 1.0.0
* @date 2025-03-22
*/
@Service
@AllArgsConstructor
@Slf4j
public class MerchantWarehouseSeatServiceImpl extends BaseServiceImpl<MerchantWarehouseSeatRepository, MerchantWarehouseSeatEntity> implements MerchantWarehouseSeatService {

    @Resource
    private MerchantWarehouseSeatConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantWarehouseSeatDto dto) {
        ApplicationContext.setIgnoreTenant(true);
        MerchantWarehouseSeatEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantWarehouseSeatDto> dos) {
        List<MerchantWarehouseSeatEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantWarehouseSeatDto dto) {
        MerchantWarehouseSeatEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantWarehouseSeatDto> dos) {
        List<MerchantWarehouseSeatEntity> entities = convert.listToEntities(dos);
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
    public MerchantWarehouseSeatDto loadById(String id) {
        MerchantWarehouseSeatEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantWarehouseSeatDto> loadByIds(List<String> ids) {
        List<MerchantWarehouseSeatEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantWarehouseSeatDto> loadAll() {
        LambdaQueryWrapper<MerchantWarehouseSeatEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantWarehouseSeatEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantWarehouseSeatDto> loadList(MerchantWarehouseSeatQuery query) {
        IPage<MerchantWarehouseSeatEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantWarehouseSeatDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    @Override
    public void upsert(MerchantWarehouseSeatDto dto) {
        ApplicationContext.setIgnoreTenant(true);
        if (StringUtils.isEmpty(dto.getTenantId())) {
            dto.setTenantId(ApplicationContext.getTenantId());
            log.info("MerchantWarehouseSeatService...upsert...tenantId:{}", ApplicationContext.getTenantId());
        }
        //名称是否重复
        MerchantWarehouseSeatEntity entity = this.baseMapper.selectOne(
                new LambdaQueryWrapper<MerchantWarehouseSeatEntity>()
                        .eq(MerchantWarehouseSeatEntity::getSeatName, dto.getSeatName())
                        .eq(MerchantWarehouseSeatEntity::getWarehouseId, dto.getWarehouseId()));
        if (StringUtils.isNotEmpty(dto.getId())) {
            if (entity != null && !entity.getId().equals(dto.getId())) {
                throw new RequestBadException(ProductError.PRODUCT_WAREHOUSE_SEAT_NAME_EXIST_ERROR);
            }
            this.updateById(convert.toEntity(dto));
        } else {
            if (entity != null) {
                throw new RequestBadException(ProductError.PRODUCT_WAREHOUSE_SEAT_NAME_EXIST_ERROR);
            }
            this.baseMapper.insert(convert.toEntity(dto));
        }
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantWarehouseSeatEntity> getWrapper(MerchantWarehouseSeatQuery query){
        LambdaQueryWrapper<MerchantWarehouseSeatEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(query.getOrder())){
            wrapper.orderByDesc(MerchantWarehouseSeatEntity::getId);
        }

        if (StringUtils.isNotBlank(query.getWarehouseId())) {
            wrapper.eq(MerchantWarehouseSeatEntity::getWarehouseId, query.getWarehouseId());
        }

        if (StringUtils.isNotEmpty(query.getTenantId())){
            wrapper.eq(MerchantWarehouseSeatEntity::getTenantId,query.getTenantId());
        }
        return wrapper;
    }
}