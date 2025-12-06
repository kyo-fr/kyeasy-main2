package org.ares.cloud.merchantInfo.service.impl;


import org.ares.cloud.api.user.enums.UserIdentity;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.merchantInfo.convert.MerchantHoliDayConvert;
import org.ares.cloud.merchantInfo.dto.MerchantHoliDayDto;
import org.ares.cloud.merchantInfo.entity.MerchantHoliDayEntity;
import org.ares.cloud.merchantInfo.enums.MerchantError;
import org.ares.cloud.merchantInfo.query.MerchantHoliDayQuery;
import org.ares.cloud.merchantInfo.repository.MerchantHoliDayRepository;
import org.ares.cloud.merchantInfo.service.MerchantHoliDayService;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.ares.cloud.redis.cache.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author hugo tangxkwork@163.com
* @description 商户休假 服务实现
* @version 1.0.0
* @date 2025-01-03
*/
@Service
@AllArgsConstructor
public class MerchantHoliDayServiceImpl extends BaseServiceImpl<MerchantHoliDayRepository, MerchantHoliDayEntity> implements MerchantHoliDayService{

    @Resource
    private MerchantHoliDayConvert convert;
    @Resource
    private RedisCache redisCache;
    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantHoliDayDto dto) {
        ApplicationContext.setIgnoreTenant(true);
        MerchantHoliDayEntity entity = convert.toEntity(dto);
        //查询是否存在
        MerchantHoliDayEntity merchantHoliDayEntity = baseMapper.selectById(dto.getTenantId());
        entity.setId(dto.getTenantId());
        if (merchantHoliDayEntity != null){
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
    public void create(List<MerchantHoliDayDto> dos) {
        List<MerchantHoliDayEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantHoliDayDto dto) {
        MerchantHoliDayEntity entity = convert.toEntity(dto);
        String key = "merchant_holidayCount";
        if (ApplicationContext.getIdentity() == UserIdentity.Merchants.getValue() ) {
            Object countsObj = redisCache.get(key);
            int count = 0;
            if (countsObj != null) {
                if (countsObj instanceof Integer) {
                    count = (Integer) countsObj;
                } else if (countsObj instanceof String) {
                    try {
                        count = Integer.parseInt((String) countsObj);
                    } catch (NumberFormatException e) {
                        throw new RequestBadException(MerchantError.MERCHANT_UPDATE_ADVERTISED_COUNT_MORE_ERROR);
                    }
                } else {
                    throw new RequestBadException(MerchantError.MERCHANT_UPDATE_ADVERTISED_COUNT_MORE_ERROR);
                }
            }
            if (count >= 2) {
                throw new RequestBadException(MerchantError.MERCHANT_UPDATE_ADVERTISED_COUNT_MORE_ERROR);
            } else {
                count = count + 1;
                redisCache.set(key, count, RedisCache.DEFAULT_EXPIRE);
            }
        } else {
            redisCache.set(key, 1, RedisCache.DEFAULT_EXPIRE);
        }
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantHoliDayDto> dos) {
        List<MerchantHoliDayEntity> entities = convert.listToEntities(dos);
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
    public MerchantHoliDayDto loadById(String id) {
        MerchantHoliDayEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantHoliDayDto> loadByIds(List<String> ids) {
        List<MerchantHoliDayEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantHoliDayDto> loadAll() {
        LambdaQueryWrapper<MerchantHoliDayEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantHoliDayEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantHoliDayDto> loadList(MerchantHoliDayQuery query) {
        IPage<MerchantHoliDayEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantHoliDayDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantHoliDayEntity> getWrapper(MerchantHoliDayQuery query){
        LambdaQueryWrapper<MerchantHoliDayEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(MerchantHoliDayEntity::getId);
        }
        return wrapper;
    }
}