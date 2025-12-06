package org.ares.cloud.product.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.api.merchant.MerchantClient;
import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.product.convert.ProductTypeConvert;
import org.ares.cloud.product.dto.ProductDto;
import org.ares.cloud.product.dto.ProductTypeDto;
import org.ares.cloud.product.entity.ProductBaseInfoEntity;
import org.ares.cloud.product.entity.ProductTypeEntity;
import org.ares.cloud.product.enums.ProductError;
import org.ares.cloud.product.query.ProductTypeQuery;
import org.ares.cloud.product.repository.ProductTypeRepository;
import org.ares.cloud.product.service.ProductTypeService;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.ares.cloud.product.vo.ProductTypeVo;
import org.ares.cloud.redis.cache.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author hugo tangxkwork@163.com
* @description 商品分类管理 服务实现
* @version 1.0.0
* @date 2024-10-28
*/
@Service
@AllArgsConstructor
@Slf4j
public class ProductTypeServiceImpl extends BaseServiceImpl<ProductTypeRepository, ProductTypeEntity> implements ProductTypeService{

    @Resource
    private ProductTypeConvert convert;

    @Resource
    private RedisCache redisCache;

    @Resource
    private MerchantClient merchantClient;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ProductTypeDto dto) {
        //同一个租户 同一个分类级别下不能存在相同名称的分类
        ProductTypeEntity entity =
                this.baseMapper.selectOne(new QueryWrapper<ProductTypeEntity>().
                        eq("tenant_id", dto.getTenantId()).eq("name", dto.getName()).
                        eq("levels", dto.getLevels()));
        if (entity != null) {
            //该租户下已存在该分类名称
            throw new RequestBadException(ProductError.PRODUCT_TYPE_NAME_EXIST_ERROR);
        }else {
//            ApplicationContext.setIgnoreTenant(true);
            this.baseMapper.insert(convert.toEntity(dto));
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<ProductTypeDto> dos) {
        List<ProductTypeEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(ProductTypeDto dto) {
        //同一个租户 同一个分类级别下不能存在相同名称的分类
        ProductTypeEntity entity =
                this.baseMapper.selectOne(new QueryWrapper<ProductTypeEntity>().
                        eq("tenant_id", dto.getTenantId()).eq("name", dto.getName()).
                        eq("levels", dto.getLevels()));
        if (entity != null && !entity.getId().equals(dto.getId())) {
            //该租户下已存在该分类名称
            throw new RequestBadException(ProductError.PRODUCT_TYPE_NAME_EXIST_ERROR);
        }else {
            String tenantId = ApplicationContext.getTenantId();
            dto.setTenantId(tenantId);
            this.updateById(convert.toEntity(dto));
        }
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<ProductTypeDto> dos) {
        List<ProductTypeEntity> entities = convert.listToEntities(dos);
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
    public ProductTypeDto loadById(String id) {
        ProductTypeEntity entity = this.baseMapper.selectById(id);
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
    public List<ProductTypeDto> loadByIds(List<String> ids) {
        List<ProductTypeEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<ProductTypeDto> loadAll() {
        LambdaQueryWrapper<ProductTypeEntity> wrapper = new LambdaQueryWrapper<>();
        List<ProductTypeEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<ProductTypeVo> loadList(ProductTypeQuery query) {
        IPage<ProductTypeEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        if(CollectionUtil.isEmpty(page.getRecords())){
            return new PageResult<>(new ArrayList<>(), 0);
        }
        ArrayList<ProductTypeVo> list = new ArrayList<>();
        for (ProductTypeEntity entity : page.getRecords()){
                ProductTypeVo vo = new ProductTypeVo();
                String id = entity.getId();
                //根据id查询子集
            QueryWrapper<ProductTypeEntity> parentId = new QueryWrapper<ProductTypeEntity>().eq("parent_id", id);
            List<ProductTypeEntity> list1 = baseMapper.selectList(parentId);
            if (list1.size() > 0){
                vo.setHasChildren(true);
            }else {
                vo.setHasChildren(false);
            }
            vo.setName(entity.getName());
            vo.setId(entity.getId());
            vo.setPicture(entity.getPicture());
            vo.setParentId(entity.getParentId());
            vo.setLevels(entity.getLevels());
            vo.setCreateTime(entity.getCreateTime());
            vo.setUpdater(entity.getUpdater());
            vo.setUpdateTime(entity.getUpdateTime());
            vo.setTenantId(entity.getTenantId());
            list.add(vo);
        }
        return new PageResult<>(list, page.getTotal());
    }

    @Override
    public JSONObject  getSonByParentId(String parentId, int levels) {
        JSONObject jsonObject1 = new JSONObject();
        QueryWrapper<ProductTypeEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(parentId)){
            wrapper.eq("parent_id", parentId);
        }
        wrapper.eq("levels", levels);
        List<ProductTypeEntity> productTypeEntities = baseMapper.selectList(wrapper);
        if (levels != 1){
            JSONArray objects = new JSONArray();
            for (ProductTypeEntity list :productTypeEntities ){
                //根据等级查询列表返回父id查询父类信息
                String parId = list.getParentId();
                QueryWrapper<ProductTypeEntity> wrapper1 = new QueryWrapper<>();
                wrapper1.eq("id", parId);
                ProductTypeEntity productTypeEntity = baseMapper.selectOne(wrapper1);
                JSONObject jsonObject = new JSONObject();
                if(productTypeEntity != null){
                    jsonObject.set("parentId",parId);
                    jsonObject.set("id",list.getId());
                    jsonObject.set("parentName",productTypeEntity.getName());
                    jsonObject.set("name",list.getName());
                    jsonObject.set("picture",list.getPicture());
                    jsonObject.set("createTime",list.getCreateTime());
                }
                objects.put(jsonObject);
            }
            jsonObject1.set("list",objects);
        }else {
            jsonObject1.set("list",productTypeEntities);
        }
        return jsonObject1 ;
    }


    @Override
    public PageResult<ProductDto> getProductInfoByTypeId(String typeId, int levels) {
        String tenantId = ApplicationContext.getTenantId();
        QueryWrapper<ProductDto> queryWrapper = new QueryWrapper<>();
        if(levels ==1){
            queryWrapper.eq("levelOneId",typeId);
        }
        if(levels ==2){
            queryWrapper.eq("levelTwoId",typeId);
        }
        if(levels ==3){
            queryWrapper.eq("levelThreeId",typeId);
        }
        queryWrapper.eq("tenant_id",tenantId);
//        Page<ProductDto> page = baseMapper.getProductInfoByTypeId(getPage(query), queryWrapper);

        return null;
    }

    @Override
    public List<ProductTypeVo> getTypeByDomainName(String domainName) {
        String tenantId = getMerchantByDomainName(domainName);
        log.info("getTypeByDomainName...tenantId:{}",tenantId);
        List<ProductTypeEntity> productTypeEntities = this.baseMapper.selectList(new LambdaQueryWrapper<ProductTypeEntity>().eq(ProductTypeEntity::getTenantId, tenantId).orderBy(true,false, ProductTypeEntity::getCreateTime));
        //构建分类树
        Map<String, ProductTypeVo> map = new HashMap<>();
        for (ProductTypeEntity entity : productTypeEntities) {
            ProductTypeVo productTypeVo = new ProductTypeVo();
            //属性copy
            BeanUtils.copyProperties(entity, productTypeVo);
            productTypeVo.setHasChildren(false); // 默认设置为 false
            map.put(entity.getId(), productTypeVo);
        }
        List<ProductTypeVo> rootList = new ArrayList<>();
        for (ProductTypeEntity entity : productTypeEntities) {
            ProductTypeVo productTypeVo = map.get(entity.getId());
            if (entity.getParentId() == null) {
                rootList.add(productTypeVo);
            } else {
                ProductTypeVo parent = map.get(entity.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(productTypeVo);
                    parent.setHasChildren(true); // 父节点有子节点，设置为 true
                }
            }
        }
        // 设置 hasChildren 字段
        for (ProductTypeVo productTypeVo : map.values()) {
            if (productTypeVo.getChildren() != null && !productTypeVo.getChildren().isEmpty()) {
                productTypeVo.setHasChildren(true);
            }
        }
        return rootList;
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<ProductTypeEntity> getWrapper(ProductTypeQuery query){
        LambdaQueryWrapper<ProductTypeEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(ProductTypeEntity::getId);
        }
        if (StringUtils.isNotEmpty(query.getTenantId())) {
            wrapper.eq(ProductTypeEntity::getTenantId,query.getTenantId());
        }

        if (StringUtils.isNotEmpty(query.getParentId()) ) {
            wrapper.and(w ->
                    w.eq(ProductTypeEntity::getParentId, query.getParentId()));
        }else {
            wrapper.and(w ->
                    w.eq(ProductTypeEntity::getLevels, 1));
        }
        return wrapper;
    }


    public String getMerchantByDomainName(String domainName) {
        //先去redis中查询
        log.info("getMerchantByDomainName...domainName:{}", domainName);
        Object domainNameValue = redisCache.get(domainName);
        if (domainNameValue != null) {
            log.info("getMerchantByDomainName...redis中查询商户信息domainNameValue：{}", domainNameValue);
            return domainNameValue.toString();
        } else {
            MerchantInfo merchantInfo = merchantClient.getMerchantInfoByDomain(domainName);
            log.info("getMerchantByDomainName...商户信息查询结果：{}", merchantInfo);
            if (merchantInfo != null) {
                redisCache.set(domainName, merchantInfo.getId(), RedisCache.DEFAULT_EXPIRE);
                return merchantInfo.getId();
            }
        }
        return null;
    }
}