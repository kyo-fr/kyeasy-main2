package com.ares.cloud.base.service.impl;


import com.ares.cloud.base.convert.SysPaymentChannelConvert;
import com.ares.cloud.base.dto.SysPaymentChannelDto;
import com.ares.cloud.base.entity.SysPaymentChannelEntity;
import com.ares.cloud.base.query.PaymentChannelQuery;
import com.ares.cloud.base.query.SysPaymentChannelQuery;
import com.ares.cloud.base.repository.SysPaymentChannelRepository;
import com.ares.cloud.base.service.SysPaymentChannelService;
import org.ares.cloud.common.context.ApplicationContext;
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
* @description 支付渠道 服务实现
* @version 1.0.0
* @date 2025-05-13
*/
@Service
@AllArgsConstructor
public class SysPaymentChannelServiceImpl extends BaseServiceImpl<SysPaymentChannelRepository, SysPaymentChannelEntity> implements SysPaymentChannelService{

    @Resource
    private SysPaymentChannelConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(SysPaymentChannelDto dto) {
        SysPaymentChannelEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<SysPaymentChannelDto> dos) {
        List<SysPaymentChannelEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(SysPaymentChannelDto dto) {
        SysPaymentChannelEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<SysPaymentChannelDto> dos) {
        List<SysPaymentChannelEntity> entities = convert.listToEntities(dos);
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
    public SysPaymentChannelDto loadById(String id) {
        SysPaymentChannelEntity entity = this.baseMapper.selectById(id);
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
    public List<SysPaymentChannelDto> loadByIds(List<String> ids) {
        List<SysPaymentChannelEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<SysPaymentChannelDto> loadAll(PaymentChannelQuery query) {
        ApplicationContext.setTenantId(null);
        ApplicationContext.setUserId(null);
        LambdaQueryWrapper<SysPaymentChannelEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(SysPaymentChannelEntity::getId);
        wrapper.eq(SysPaymentChannelEntity::getStatus, 1);
        if (query != null){
            if (query.getChannelType() != null){
                wrapper.eq(SysPaymentChannelEntity::getChannelType, query.getChannelType());
            }
        }
        List<SysPaymentChannelEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<SysPaymentChannelDto> loadList(SysPaymentChannelQuery query) {
        IPage<SysPaymentChannelEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<SysPaymentChannelDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }
    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<SysPaymentChannelDto> loadAll() {
        ApplicationContext.setIgnoreTenant(true);
        LambdaQueryWrapper<SysPaymentChannelEntity> wrapper = new LambdaQueryWrapper<>();
        List<SysPaymentChannelEntity> sysPaymentChannelEntities = baseMapper.selectList(wrapper);
        if (sysPaymentChannelEntities == null|| sysPaymentChannelEntities.isEmpty()){
            return null;
        }
        return convert.listToDto(sysPaymentChannelEntities);
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<SysPaymentChannelEntity> getWrapper(SysPaymentChannelQuery query){
        LambdaQueryWrapper<SysPaymentChannelEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(SysPaymentChannelEntity::getId);
        }
        return wrapper;
    }
}