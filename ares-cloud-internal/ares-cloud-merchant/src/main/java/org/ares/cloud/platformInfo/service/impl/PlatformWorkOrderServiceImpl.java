package org.ares.cloud.platformInfo.service.impl;


import org.ares.cloud.api.base.BusinessIdServerClient;
import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.platformInfo.convert.PlatformWorkOrderConvert;
import org.ares.cloud.platformInfo.dto.PlatformWorkOrderContentDto;
import org.ares.cloud.platformInfo.dto.PlatformWorkOrderDto;
import org.ares.cloud.platformInfo.entity.PlatformServiceEntity;
import org.ares.cloud.platformInfo.entity.PlatformWorkOrderEntity;
import org.ares.cloud.platformInfo.mapper.UserMapper;
import org.ares.cloud.platformInfo.query.PlatformWorkOrderQuery;
import org.ares.cloud.platformInfo.repository.PlatformWorkOrderRepository;
import org.ares.cloud.platformInfo.service.PlatformWorkOrderService;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.ares.cloud.platformInfo.vo.PlatformWorkOrderVo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author hugo tangxkwork@163.com
* @description 工单 服务实现
* @version 1.0.0
* @date 2024-10-16
*/
@Service
@AllArgsConstructor
public class PlatformWorkOrderServiceImpl extends BaseServiceImpl<PlatformWorkOrderRepository, PlatformWorkOrderEntity> implements PlatformWorkOrderService{

    @Resource
    private PlatformWorkOrderConvert convert;

    @Resource
    private BusinessIdServerClient businessIdServerClient;

    @Resource
    private UserMapper  userMapper;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PlatformWorkOrderDto dto) {
        try {
            String platformWorkOrderId = businessIdServerClient.generateBusinessId("platform_workOrder");
            if (platformWorkOrderId != null && !platformWorkOrderId.isEmpty()) {
                ApplicationContext.setIgnoreTenant(true);
                PlatformWorkOrderEntity entity = convert.toEntity(dto);
                entity.setId(platformWorkOrderId);
                this.baseMapper.insert(entity);
            }
        }catch (Exception e){
           log.error("创建工单失败",e);
        }

    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<PlatformWorkOrderDto> dos) {
        List<PlatformWorkOrderEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(PlatformWorkOrderDto dto) {
        PlatformWorkOrderEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<PlatformWorkOrderDto> dos) {
        List<PlatformWorkOrderEntity> entities = convert.listToEntities(dos);
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
    public PlatformWorkOrderDto loadById(String id) {
        PlatformWorkOrderEntity entity = this.baseMapper.selectById(id);
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
    public List<PlatformWorkOrderDto> loadByIds(List<String> ids) {
        List<PlatformWorkOrderEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<PlatformWorkOrderDto> loadAll() {
        LambdaQueryWrapper<PlatformWorkOrderEntity> wrapper = new LambdaQueryWrapper<>();
        List<PlatformWorkOrderEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<PlatformWorkOrderVo> loadList(PlatformWorkOrderQuery query) {
        IPage<PlatformWorkOrderEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        if (page.getRecords().size() > 0) {
            List<PlatformWorkOrderVo> list = new ArrayList<>();
            page.getRecords().forEach(entity -> {
                PlatformWorkOrderVo vo = new PlatformWorkOrderVo();
                vo.setTenantId(entity.getTenantId());
                vo.setUserId(entity.getUserId());
                //根据userId查询手机号
                UserDto userByUserId = userMapper.getUserByUserId(entity.getUserId());
                if (userByUserId != null) {
                    vo.setPhone(userByUserId.getPhone());
                }
                vo.setWorkOrderType(entity.getWorkOrderType());
                vo.setStatus(entity.getStatus());
                vo.setCreateTime(entity.getCreateTime());
                vo.setUpdateTime(entity.getUpdateTime());
                list.add(vo);
            });
            return new PageResult<>(list, page.getTotal());
        }
        return null;
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<PlatformWorkOrderEntity> getWrapper(PlatformWorkOrderQuery query){
        LambdaQueryWrapper<PlatformWorkOrderEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(query.getOrder())){
            wrapper.orderByDesc(PlatformWorkOrderEntity::getId);
        }
        if (StringUtils.isNotEmpty(query.getTenantId())){
            wrapper.eq(PlatformWorkOrderEntity::getTenantId, query.getTenantId());
        }
        if (StringUtils.isNotEmpty(query.getUserId())){
            wrapper.eq(PlatformWorkOrderEntity::getUserId, query.getUserId());
        }
        return wrapper;
    }
}