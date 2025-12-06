package org.ares.cloud.platformInfo.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.platformInfo.convert.PlatformApprovalRecordConvert;
import org.ares.cloud.platformInfo.dto.PlatformApprovalRecordDto;
import org.ares.cloud.platformInfo.entity.PlatformApprovalEntity;
import org.ares.cloud.platformInfo.entity.PlatformApprovalRecordEntity;
import org.ares.cloud.platformInfo.entity.PlatformInfoEntity;
import org.ares.cloud.platformInfo.enums.ApprovalRecordType;
import org.ares.cloud.platformInfo.enums.ApprovalType;
import org.ares.cloud.platformInfo.enums.PlatformError;
import org.ares.cloud.platformInfo.enums.SubscribeStatus;
import org.ares.cloud.platformInfo.query.PlatformApprovalRecordQuery;
import org.ares.cloud.platformInfo.repository.PlatformApprovalRecordRepository;
import org.ares.cloud.platformInfo.repository.PlatformApprovalRepository;
import org.ares.cloud.platformInfo.repository.PlatformInfoRepository;
import org.ares.cloud.platformInfo.service.PlatformApprovalRecordService;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.ares.cloud.platformInfo.service.PlatformApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author hugo tangxkwork@163.com
* @description 存储变更明细表 服务实现
* @version 1.0.0
* @date 2025-06-16
*/
@Service
@AllArgsConstructor
@Slf4j
public class PlatformApprovalRecordServiceImpl extends BaseServiceImpl<PlatformApprovalRecordRepository, PlatformApprovalRecordEntity> implements PlatformApprovalRecordService{

    @Resource
    private PlatformApprovalRecordConvert convert;


    @Resource
    private PlatformApprovalRepository platformApprovalRepository;
    @Autowired
    private PlatformApprovalRecordRepository platformApprovalRecordRepository;

    @Autowired
    private PlatformInfoRepository platformInfoRepository;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PlatformApprovalRecordDto dto) {
        PlatformApprovalRecordEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<PlatformApprovalRecordDto> dos) {
        List<PlatformApprovalRecordEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(PlatformApprovalRecordDto dto) {
        PlatformApprovalRecordEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<PlatformApprovalRecordDto> dos) {
        List<PlatformApprovalRecordEntity> entities = convert.listToEntities(dos);
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
    public PlatformApprovalRecordDto loadById(String id) {
        PlatformApprovalRecordEntity entity = this.baseMapper.selectById(id);
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
    public List<PlatformApprovalRecordDto> loadByIds(List<String> ids) {
        List<PlatformApprovalRecordEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<PlatformApprovalRecordDto> loadAll() {
        LambdaQueryWrapper<PlatformApprovalRecordEntity> wrapper = new LambdaQueryWrapper<>();
        List<PlatformApprovalRecordEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<PlatformApprovalRecordDto> loadList(PlatformApprovalRecordQuery query) {
        IPage<PlatformApprovalRecordEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<PlatformApprovalRecordDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePlatformApprovalRecord(PlatformApprovalRecordDto dto) {
        try {
            log.info("更新平台审批记录开始:{}", dto != null ? JSONObject.toJSONString(dto) : "null");
        } catch (Exception e) {
            log.info("更新平台审批记录开始，dto序列化失败:{}", dto != null ? dto.toString() : "null");
        }
        PlatformApprovalEntity platformApprovalEntity = getPlatformApprovalEntity(dto.getTenantId());
        if (platformApprovalEntity != null){
            //本次需要扣除的存储大小
            Long changeMemory = dto.getChangeMemory();
            String id = platformApprovalEntity.getId();
            //最新的总存储大小
            Long totalMemory  = (long) (platformApprovalEntity.getMemory() * 1024 * 1024);
            //根据id查询扣除记录sum record_type in ('used','over')然后求sum（changeMemory） 已使用的总存储
            Long reduce = baseMapper.selectList(new QueryWrapper<PlatformApprovalRecordEntity>().eq("approval_id", id).
                            in("record_type", ApprovalRecordType.USED.getValue(), ApprovalRecordType.OVER.getValue()))
                    .stream().map(item -> item.getChangeMemory()).reduce(0L, Long::sum);
            //判断changeMemory和reduce大小 如果超过了剩余存储 则进行全部扣除
            long subMemory = totalMemory - reduce - changeMemory;
            if (subMemory>0){
                updatePlatformApprovalRecord(dto, changeMemory, platformApprovalEntity);
            }else {
                //说明本条审批存储不够用了需要启用下一条 先吧本条的状态更新为已使用 再查下下一条了
                platformApprovalEntity.setSubStatus(SubscribeStatus.USED.getValue());
                int update= platformApprovalRepository.updateById(platformApprovalEntity);
                //更新完本条数据再继续查询下一条可用的存储审批单
                if (update > 0){
                    //先更新本条剩余的存储扣减完
                    updatePlatformApprovalRecord(dto, totalMemory - reduce, platformApprovalEntity);
                    PlatformApprovalEntity entity = getPlatformApprovalEntity(dto.getTenantId());
                    if (entity != null){
                        //再更新下一条审批存储
                        updatePlatformApprovalRecord(dto, changeMemory, entity);
                    }
                }
            }
        }else {
            //没有可用的存储审批单也就是 订阅中的 sub
            throw new RequestBadException(PlatformError.PLATFORM_USER_APPROVAL_NOT_SUB_ERROR);
        }
    }

    @Override
    public Long getAvailableStorage(String tenantId) {
        //根据tenantId查询平台信息
        PlatformInfoEntity platformInfoEntity = platformInfoRepository.selectById(tenantId);
        if (platformInfoEntity != null){
            //返回100G
         return 1024*1024*100L;
        }
        //商户总存储 用审批单查询memory-审批记录是use和over的
        Long sumMemory = platformApprovalRecordRepository.selectList(new LambdaQueryWrapper<PlatformApprovalRecordEntity>().
                        eq(PlatformApprovalRecordEntity::getTenantId, tenantId).eq(PlatformApprovalRecordEntity::getRecordType,ApprovalRecordType.SEND.getValue()))
                .stream().map(item -> item.getChangeMemory()).reduce(0L, Long::sum);

        Long usedMemory = platformApprovalRecordRepository.selectList(new LambdaQueryWrapper<PlatformApprovalRecordEntity>().
                        eq(PlatformApprovalRecordEntity::getTenantId, tenantId).in(PlatformApprovalRecordEntity::getRecordType,
                                Arrays.asList(ApprovalRecordType.USED.getValue(), ApprovalRecordType.OVER.getValue())))
                .stream().map(item -> item.getChangeMemory()).reduce(0L, Long::sum);
        long available = sumMemory - usedMemory;
        log.info("getAvailableStorage...tenantId:{},sumMemory:{},usedMemory:{},available:{}", tenantId, sumMemory, usedMemory, available);
        return available;
    }

    private void updatePlatformApprovalRecord(PlatformApprovalRecordDto dto, Long changeMemory, PlatformApprovalEntity platformApprovalEntity) {
        PlatformApprovalRecordEntity entity = new PlatformApprovalRecordEntity();
        //插入1条明细
        entity.setRecordType(ApprovalRecordType.USED.getValue());
        entity.setChangeMemory(changeMemory);
        entity.setTenantId(dto.getTenantId());
        entity.setDescription(dto.getDescription());
        entity.setDataSource(dto.getDataSource());
        entity.setDataType(dto.getDataType());
        entity.setApprovalId(platformApprovalEntity.getId());
        this.baseMapper.insert(entity);
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<PlatformApprovalRecordEntity> getWrapper(PlatformApprovalRecordQuery query){
        LambdaQueryWrapper<PlatformApprovalRecordEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(PlatformApprovalRecordEntity::getId);
        }
        return wrapper;
    }

    private PlatformApprovalEntity getPlatformApprovalEntity(String tenantId) {
        if (tenantId == null){
            return null;
        }
        //查询最新的生效的审批存储记录
        QueryWrapper<PlatformApprovalEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("tenant_id",tenantId);
        wrapper.eq("status","2"); //审核通过
        wrapper.eq("sub_status","sub");// 订阅
        wrapper.eq("ROWNUM","1");
        wrapper.orderByAsc("create_time");
        return platformApprovalRepository.selectOne(wrapper);
    }
}