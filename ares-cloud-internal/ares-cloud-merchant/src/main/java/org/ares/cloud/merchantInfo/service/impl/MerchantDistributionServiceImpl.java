package org.ares.cloud.merchantInfo.service.impl;


import cn.hutool.crypto.digest.MD5;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.merchantInfo.convert.MerchantDistributionConvert;
import org.ares.cloud.merchantInfo.dto.MerchantDistributionDto;
import org.ares.cloud.merchantInfo.entity.MerchantDistributionEntity;
import org.ares.cloud.merchantInfo.entity.MerchantFreightEntity;
import org.ares.cloud.merchantInfo.entity.MerchantSideBarEntity;
import org.ares.cloud.merchantInfo.entity.OpeningHoursEntity;
import org.ares.cloud.merchantInfo.query.MerchantDistributionQuery;
import org.ares.cloud.merchantInfo.repository.MerchantDistributionRepository;
import org.ares.cloud.merchantInfo.service.MerchantDistributionService;
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
* @description 商户配送设置 服务实现
* @version 1.0.0
* @date 2024-11-05
*/
@Service
@AllArgsConstructor
public class MerchantDistributionServiceImpl extends BaseServiceImpl<MerchantDistributionRepository, MerchantDistributionEntity> implements MerchantDistributionService{

    @Resource
    private MerchantDistributionConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantDistributionDto dto) {
//        ApplicationContext.setIgnoreTenant(true);
//        MerchantDistributionEntity entity = convert.toEntity(dto);
//        this.baseMapper.insert(entity);
        ApplicationContext.setIgnoreTenant(true);
        MerchantDistributionEntity entity = convert.toEntity(dto);
        //查询是否存在
        MerchantDistributionEntity distributionEntity = baseMapper.selectById(dto.getTenantId());
        entity.setId(dto.getTenantId());
        if (distributionEntity != null){
            //更新
            entity.setId(dto.getTenantId());
            baseMapper.updateById(entity);
        }else {
            baseMapper.insert(entity);
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantDistributionDto> dos) {
//        List<MerchantDistributionEntity> entities = convert.listToEntities(dos);
//        this.saveBatch(entities);
        ApplicationContext.setIgnoreTenant(true);
        List<MerchantDistributionEntity> entities = convert.listToEntities(dos);
        MerchantDistributionEntity distributionEntity =null;
        MD5 md5 = new MD5();
        for (MerchantDistributionEntity entity : entities){
            String id = md5.digestHex(entity.getTenantId() + entity.getType());
            entity.setId(id);
            //根据商户id和openTime1不为空查询是否已存在
            distributionEntity = this.baseMapper.selectOne(new LambdaQueryWrapper<MerchantDistributionEntity>()
                    .eq(MerchantDistributionEntity::getId, id) );
            if (distributionEntity != null){
                break;
            }
        }
        if (distributionEntity == null){
            this.saveBatch(entities);
        }
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantDistributionDto dto) {
        MerchantDistributionEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantDistributionDto> dos) {
        List<MerchantDistributionEntity> entities = convert.listToEntities(dos);
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
    public MerchantDistributionDto loadById(String id) {
        MerchantDistributionEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantDistributionDto> loadByIds(List<String> ids) {
        List<MerchantDistributionEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantDistributionDto> loadAll() {
        LambdaQueryWrapper<MerchantDistributionEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantDistributionEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantDistributionDto> loadList(MerchantDistributionQuery query) {
        IPage<MerchantDistributionEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantDistributionDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 根据租户id获取信息
    * @param tenantId
    * @return
    */
    @Override
    public MerchantDistributionDto getInfoByTenantId(String tenantId) {
        MerchantDistributionEntity entity = this.baseMapper.selectOne(new LambdaQueryWrapper<MerchantDistributionEntity>().eq(MerchantDistributionEntity::getTenantId, tenantId));
        if (entity != null){
            return  convert.toDto(entity);
        }
        return null;
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantDistributionEntity> getWrapper(MerchantDistributionQuery query){
        LambdaQueryWrapper<MerchantDistributionEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(MerchantDistributionEntity::getId);
        }
        return wrapper;
    }
}