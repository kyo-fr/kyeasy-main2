package org.ares.cloud.rider.service.impl;


import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.api.user.enums.UserIdentity;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.utils.UserUtils;
import org.ares.cloud.rider.convert.RiderConvert;
import org.ares.cloud.rider.dto.CreateRiderDto;
import org.ares.cloud.api.user.dto.RiderDto;
import org.ares.cloud.rider.entity.RiderEntity;
import org.ares.cloud.rider.enums.RiderError;
import org.ares.cloud.rider.query.RiderQuery;
import org.ares.cloud.rider.repository.RiderRepository;
import org.ares.cloud.rider.service.RiderService;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.ares.cloud.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 骑手 服务实现
* @version 1.0.0
* @date 2025-08-26
*/
@Service
@AllArgsConstructor
public class RiderServiceImpl extends BaseServiceImpl<RiderRepository, RiderEntity> implements RiderService{

    @Resource
    private RiderConvert convert;

    @Resource
    private UserService userService;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(CreateRiderDto dto) {
        //1.检查账号是否存在
        RiderEntity riderEntity = baseMapper.findByCountryCodeAndPhone(dto.getCountryCode(), dto.getPhone());
        if (riderEntity != null && StringUtils.isNoneBlank(riderEntity.getId())){
            throw new BusinessException(RiderError.RIDER_EXIST);
        }
        // 2.构建实体
        String merchantId = dto.getMerchantId();
        if (StringUtils.isBlank(merchantId)){
            merchantId = ApplicationContext.getTenantId();
        }
        if (StringUtils.isBlank(merchantId)){
            throw new BusinessException(RiderError.NON_MERCHANTS_CANNOT_ADD_KNIGHTS);
        }
        ApplicationContext.setTenantId(merchantId);
        // 2, 检查用户是否存在
        String account = UserUtils.getAccount(dto.getCountryCode(), dto.getPhone());
        UserDto userDto = userService.loadAllByAccount(account);
        String userId = null;
        if (userDto != null && StringUtils.isNoneBlank(userDto.getId())){
            userId = userDto.getId();
        }else {
            // 先创建用户
            userDto = new UserDto();
            userDto.setIdentity(UserIdentity.Knight.getValue());
            userDto.setPhone(dto.getPhone());
            userDto.setAccount(account);
            userDto.setCountryCode(dto.getCountryCode());
            userDto.setNickname(dto.getName());
            userDto.setPassword(dto.getPassword());
            userDto.setEmail(dto.getEmail());
            userDto.setTenantId(merchantId);
            userId = userService.create(userDto);
        }
        ApplicationContext.setTenantId(merchantId);
        RiderEntity entity = new RiderEntity();
        entity.setId(userId);
        entity.setCountryCode(dto.getCountryCode());
        entity.setPhone(dto.getPhone());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setTenantId(merchantId);
        entity.setUserId(userId);
        entity.setCreator(ApplicationContext.getUserId());
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<RiderDto> dos) {
        List<RiderEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(RiderDto dto) {
        RiderEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<RiderDto> dos) {
        List<RiderEntity> entities = convert.listToEntities(dos);
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
    public RiderDto loadById(String id) {
        RiderEntity entity = this.baseMapper.selectById(id);
        if (entity != null){
            return  convert.toDto(entity);
        }
        return null;
    }
    /**
    * 根据账号获取详情
    */
    @Override
    public RiderDto loadByAccount(String countryCode, String phone) {
        RiderEntity entity = this.baseMapper.findByCountryCodeAndPhone(countryCode, phone);
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
    public List<RiderDto> loadByIds(List<String> ids) {
        List<RiderEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<RiderDto> loadAll() {
        LambdaQueryWrapper<RiderEntity> wrapper = new LambdaQueryWrapper<>();
        List<RiderEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<RiderDto> loadList(RiderQuery query) {
        IPage<RiderEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<RiderDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<RiderEntity> getWrapper(RiderQuery query){
        LambdaQueryWrapper<RiderEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(RiderEntity::getId);
        }
        return wrapper;
    }
}