package org.ares.cloud.merchantInfo.service.impl;


import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.api.user.enums.UserIdentity;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.merchantInfo.convert.OpeningHoursConvert;
import org.ares.cloud.merchantInfo.dto.OpeningHoursDto;
import org.ares.cloud.merchantInfo.entity.OpeningHoursEntity;
import org.ares.cloud.merchantInfo.enums.MerchantError;
import org.ares.cloud.merchantInfo.query.OpeningHoursQuery;
import org.ares.cloud.merchantInfo.repository.OpeningHoursRepository;
import org.ares.cloud.merchantInfo.service.OpeningHoursService;
import org.ares.cloud.redis.cache.RedisCache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 商户营业时间 服务实现
* @version 1.0.0
* @date 2024-10-08
*/
@Service
@AllArgsConstructor
public class OpeningHoursServiceImpl extends BaseServiceImpl<OpeningHoursRepository, OpeningHoursEntity> implements OpeningHoursService{

    @Resource
    private  RedisCache redisCache;

    @Resource
    private OpeningHoursConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(OpeningHoursDto dto) {
        OpeningHoursEntity entity = convert.toEntity(dto);
        ApplicationContext.setIgnoreTenant(true);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<OpeningHoursDto> dos) {
        ApplicationContext.setIgnoreTenant(true);
        List<OpeningHoursEntity> entities = convert.listToEntities(dos);
        OpeningHoursEntity openingHoursEntity =null;
        MD5 md5 = new MD5();
        for (OpeningHoursEntity entity : entities){
            String id = md5.digestHex(entity.getTenantId() + entity.getWeekDay());
            entity.setId(id);
            //根据商户id和openTime1不为空查询是否已存在
            openingHoursEntity = this.baseMapper.selectOne(new LambdaQueryWrapper<OpeningHoursEntity>()
                    .eq(OpeningHoursEntity::getId, id) );
            if (openingHoursEntity != null){
                break;
            }
        }
        if (openingHoursEntity == null){
            this.saveBatch(entities);
        }
    }

    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(OpeningHoursDto dto) {
        OpeningHoursEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<OpeningHoursDto> dos) {
        ApplicationContext.setIgnoreTenant(true);
        //批量更新时候如果timeType传3：休息 那么openTime1  openTime2 closeTime1 closeTime2等字段都传空字符串
        String key = "merchant_openHoursCount";
        if (ApplicationContext.getIdentity() == UserIdentity.Merchants.getValue()) {
            Object countsObj = redisCache.get(key);
            int count = 0;
            if (countsObj != null) {
                if (countsObj instanceof Integer) {
                    count = (Integer) countsObj;
                } else if (countsObj instanceof String) {
                    try {
                        count = Integer.parseInt((String) countsObj);
                    } catch (NumberFormatException e) {
                        throw new RequestBadException(MerchantError.MERCHANT_UPDATE_OPENING_HOURS_COUNT_MORE_ERROR);
                    }
                } else {
                    throw new RequestBadException(MerchantError.MERCHANT_UPDATE_OPENING_HOURS_COUNT_MORE_ERROR);
                }
            }
            if (count >= 2) {
                throw new RequestBadException(MerchantError.MERCHANT_UPDATE_OPENING_HOURS_COUNT_MORE_ERROR);
            } else {
                count = count + 1;
                redisCache.set(key, count, RedisCache.DEFAULT_EXPIRE);
            }
        } else {
            redisCache.set(key, 1, RedisCache.DEFAULT_EXPIRE);
        }
        List<OpeningHoursEntity> entities = convert.listToEntities(dos);
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
    public OpeningHoursDto loadById(String id) {
        OpeningHoursEntity entity = this.baseMapper.selectById(id);
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
    public List<OpeningHoursDto> loadByIds(List<String> ids) {
        List<OpeningHoursEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<OpeningHoursDto> loadAll() {
        LambdaQueryWrapper<OpeningHoursEntity> wrapper = new LambdaQueryWrapper<>();
        List<OpeningHoursEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<OpeningHoursDto> loadList(OpeningHoursQuery query) {
        IPage<OpeningHoursEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<OpeningHoursDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    @Override
    public List<OpeningHoursDto> getOpeningHoursByTenantId(String tenantId) {
        LambdaQueryWrapper<OpeningHoursEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(tenantId)){
            wrapper.eq(OpeningHoursEntity::getTenantId, tenantId);
        }else {
            wrapper.isNull(OpeningHoursEntity::getTenantId);
        }
        List<OpeningHoursEntity> entities =  this.baseMapper.selectList(wrapper);
        return  convert.listToDto(entities);
    }

    /**
     * 根据登录用户获取对应营业时间
     * @return
     */
    @Override
    public  List<OpeningHoursDto>  getOpeningHoursByRoles() {
        Integer identity = ApplicationContext.getIdentity();
        QueryWrapper<OpeningHoursEntity> queryWrapper = new QueryWrapper<>();
        if (identity == 4){
            queryWrapper.isNull("tenant_id");
        }else {
            queryWrapper.eq("tenant_id",ApplicationContext.getTenantId());
        }
        return convert.listToDto(this.baseMapper.selectList(queryWrapper));
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<OpeningHoursEntity> getWrapper(OpeningHoursQuery query){
        LambdaQueryWrapper<OpeningHoursEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(OpeningHoursEntity::getId);
        }
        return wrapper;
    }
}