package org.ares.cloud.platformInfo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.merchantInfo.entity.MerchantFreightEntity;
import org.ares.cloud.merchantInfo.entity.MerchantKeyWordsEntity;
import org.ares.cloud.platformInfo.convert.PlatformServiceConvert;
import org.ares.cloud.platformInfo.dto.PlatformServiceDto;
import org.ares.cloud.platformInfo.entity.PlatformServiceEntity;
import org.ares.cloud.platformInfo.query.PlatformServiceQuery;
import org.ares.cloud.platformInfo.repository.PlatformServiceRepository;
import org.ares.cloud.platformInfo.service.PlatformServiceService;
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
* @description 服务和帮助 服务实现
* @version 1.0.0
* @date 2024-10-30
*/
@Service
@AllArgsConstructor
public class PlatformServiceServiceImpl extends BaseServiceImpl<PlatformServiceRepository, PlatformServiceEntity> implements PlatformServiceService{

    @Resource
    private PlatformServiceConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PlatformServiceDto dto) {
        ApplicationContext.setIgnoreTenant(true);
        PlatformServiceEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<PlatformServiceDto> dos) {
        List<PlatformServiceEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(PlatformServiceDto dto) {
        PlatformServiceEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<PlatformServiceDto> dos) {
        List<PlatformServiceEntity> entities = convert.listToEntities(dos);
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
    public PlatformServiceDto loadById(String id) {
        PlatformServiceEntity entity = this.baseMapper.selectById(id);
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
    public List<PlatformServiceDto> loadByIds(List<String> ids) {
        List<PlatformServiceEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<PlatformServiceDto> loadAll() {
        LambdaQueryWrapper<PlatformServiceEntity> wrapper = new LambdaQueryWrapper<>();
        List<PlatformServiceEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<PlatformServiceDto> loadList(PlatformServiceQuery query) {
        IPage<PlatformServiceEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<PlatformServiceDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

//    @Override
//    public List<PlatformServiceDto> getPlatformService(String tenantId) {
//        QueryWrapper<PlatformServiceEntity> wrapper =  new QueryWrapper<>();
//        if (StringUtils.isBlank(tenantId)){
//            wrapper.isNull("tenant_id");
//        }else {
//            wrapper.eq("tenant_id",tenantId);
//        }
//        List<PlatformServiceEntity> entities = baseMapper.selectList(wrapper);
//        return convert.listToDto(entities);
//    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<PlatformServiceEntity> getWrapper(PlatformServiceQuery query){
        LambdaQueryWrapper<PlatformServiceEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(query.getOrder())){
            wrapper.orderByDesc(PlatformServiceEntity::getId);
        }
        if (StringUtils.isNotBlank(query.getTenantId())){
            wrapper.eq(PlatformServiceEntity::getTenantId, query.getTenantId());
        }
        if (StringUtils.isNotBlank(query.getIdentity())){
            wrapper.eq(PlatformServiceEntity::getIdentity, query.getIdentity());
        }
        if (StringUtils.isNotBlank(query.getType())){
            wrapper.eq(PlatformServiceEntity::getType, query.getType());
        }
        return wrapper;
    }
}