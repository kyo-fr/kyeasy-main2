package org.ares.cloud.product.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.api.base.BusinessIdServerClient;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.product.convert.MerchantKeyWordsConvert;
import org.ares.cloud.product.dto.MerchantKeyWordsDto;
import org.ares.cloud.product.entity.MerchantKeyWordsEntity;
import org.ares.cloud.product.enums.ProductError;
import org.ares.cloud.product.query.MerchantKeyWordsQuery;
import org.ares.cloud.product.repository.MerchantKeyWordsRepository;
import org.ares.cloud.product.service.MerchantKeyWordsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户关键字 服务实现
* @version 1.0.0
* @date 2024-10-11
*/
@Service
@AllArgsConstructor
public class MerchantKeyWordsServiceImpl extends BaseServiceImpl<MerchantKeyWordsRepository, MerchantKeyWordsEntity> implements MerchantKeyWordsService{

    @Resource
    private MerchantKeyWordsConvert convert;

    @Resource
    private BusinessIdServerClient businessIdServerClient;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantKeyWordsDto dto) {
        MerchantKeyWordsEntity entity = convert.toEntity(dto);
        QueryWrapper<MerchantKeyWordsEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("key_name", dto.getKeyName());
        wrapper.eq("tenant_id", dto.getTenantId());
        MerchantKeyWordsEntity merchantKeyWordsEntity = baseMapper.selectOne(wrapper);
        if (merchantKeyWordsEntity == null){
//            ResponseEntity<String> merchantKeyWordsId = businessIdServerClient.generateBusinessId("merchant_keyWords_id");
//            if (merchantKeyWordsId.getStatusCode().equals(HttpStatus.OK)){
                ApplicationContext.setIgnoreTenant(true);
//                entity.setKeyId(merchantKeyWordsId.getBody());
                this.baseMapper.insert(entity);
//            }
        }else {
            System.out.println("该商户下关键字已存在："+entity.getKeyName());
            throw new RequestBadException(ProductError.MERCHANT_KEYWORDS_EXIST_ERROR);
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantKeyWordsDto> dos) {
        List<MerchantKeyWordsEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantKeyWordsDto dto) {
        MerchantKeyWordsEntity entity = convert.toEntity(dto);
        QueryWrapper<MerchantKeyWordsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("key_name", dto.getKeyName());
        queryWrapper.eq("tenant_id", dto.getTenantId());
        MerchantKeyWordsEntity merchantKeyWordsEntity = baseMapper.selectOne(queryWrapper);
        if (merchantKeyWordsEntity != null){
            if (merchantKeyWordsEntity.getId().equals(dto.getId())){
                //可以更新
                this.updateById(entity);
            }else {
                System.out.println("该关键字已存在,请勿重复添加");
                throw new RequestBadException(ProductError.MERCHANT_KEYWORDS_EXIST_ERROR);
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
    public void update(List<MerchantKeyWordsDto> dos) {
        List<MerchantKeyWordsEntity> entities = convert.listToEntities(dos);
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
    public MerchantKeyWordsDto loadById(String id) {
        MerchantKeyWordsEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantKeyWordsDto> loadByIds(List<String> ids) {
        List<MerchantKeyWordsEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantKeyWordsDto> loadAll() {
        LambdaQueryWrapper<MerchantKeyWordsEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantKeyWordsEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantKeyWordsDto> loadList(MerchantKeyWordsQuery query) {
        IPage<MerchantKeyWordsEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantKeyWordsDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantKeyWordsEntity> getWrapper(MerchantKeyWordsQuery query){
        LambdaQueryWrapper<MerchantKeyWordsEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(query.getOrder())){
            wrapper.orderByDesc(MerchantKeyWordsEntity::getId);
        }
        if (StringUtils.isNotBlank(query.getTenantId())){
            wrapper.eq(MerchantKeyWordsEntity::getTenantId, query.getTenantId());
        }
        return wrapper;
    }
}