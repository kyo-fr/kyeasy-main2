package org.ares.cloud.user.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ares.cloud.api.base.BusinessIdServerClient;
import org.ares.cloud.api.user.dto.ChangePasswordReq;
import org.ares.cloud.api.user.dto.RecoverPasswordRequest;
import org.ares.cloud.api.user.errors.UserError;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.exception.NotFoundException;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.user.convert.UserConvert;
import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.user.entity.UserEntity;
import org.ares.cloud.user.properties.SuperAdminProperties;
import org.ares.cloud.user.query.UserQuery;
import org.ares.cloud.user.repository.UserRepository;
import org.ares.cloud.user.service.UserService;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author hugo tangxkwork@163.com
 * @version 1.0.0
 * @description 用户 服务实现
 * @date 2024-10-07
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends BaseServiceImpl<UserRepository, UserEntity> implements UserService {

    @Resource
    private UserConvert convert;
    @Resource
    private BusinessIdServerClient idServerClient;
    /**
     * 密码加密工具
     */
    @Resource
    private PasswordEncoder passwordEncoder;
    /**
     * 超级管理配置
     */
    @Resource
    private SuperAdminProperties superAdminProperties;

    /**
     * 创建
     *
     * @param dto 数据模型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public String create(UserDto dto) {
        UserEntity entity = convert.toEntity(dto);
        if (StringUtils.isNotBlank(dto.getPassword())) {
            String password =  passwordEncoder.encode(dto.getPassword());
            entity.setPassword(password);
        }
        String businessId = idServerClient.generateRandomBusinessId();
        entity.setContractId(businessId);
        this.baseMapper.insert(entity);
        return entity.getId();
    }

    @Override
    public UserDto getOrCreateTemporaryUser(String countryCode,String phone) {
        if (StringUtils.isNotBlank(phone) && StringUtils.isNotBlank(countryCode)) {
            String account  = this.getAccount(countryCode, phone);
            UserEntity entity = this.baseMapper.loadAllByAccount(account);
            if (entity == null || StringUtils.isBlank(entity.getId())) {
                entity = new UserEntity();
                entity.setAccount(account);
                entity.setCountryCode(countryCode);
                entity.setIsTemporary(1);
                entity.setPhone(phone);
                baseMapper.insert(entity);
            }
            return convert.toDto(entity);
        }
        return null;
    }

    /**
     * 批量创建
     *
     * @param dos 数据模型集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<UserDto> dos) {
        List<UserEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities);
    }

    /**
     * 更新
     *
     * @param dto 数据模型
     */
    @Override
    public void update(UserDto dto) {
        UserEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }
    /**
     * 更新密码
     * @param request 请求数据
     */
    @Override
    public void changePassword(ChangePasswordReq request) {
        UserEntity entity = this.baseMapper.selectById(request.getUserId());
        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            throw new NotFoundException(UserError.USER_NOT_FOUND);
        }
        if (StringUtils.isEmpty(request.getNewPassword())) {
            throw new RequestBadException(UserError.PASSWORD_IS_EMPTY);
        }
        if (StringUtils.isNoneEmpty(request.getConfirmPassword()) && !request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RequestBadException(UserError.THE_TWO_PASSWORDS_DO_NOT_MATCH);
        }
        if (StringUtils.isNoneEmpty(request.getOldPassword()) && !passwordEncoder.matches(request.getOldPassword(), entity.getPassword())) {
            throw new RequestBadException(UserError.ORIGINAL_PASSWORD_ERROR);
        }
        String password =  passwordEncoder.encode(request.getNewPassword());
        entity.setPassword(password);
        this.baseMapper.updateById(entity);
    }

    @Override
    public void recoverPassword(RecoverPasswordRequest request) {
        if (StringUtils.isEmpty(request.getNewPassword())) {
            throw new RequestBadException(UserError.PASSWORD_IS_EMPTY);
        }
        if (StringUtils.isNotEmpty(request.getConfirmPassword()) && !request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new RequestBadException(UserError.THE_TWO_PASSWORDS_DO_NOT_MATCH);
        }
        UserEntity entity = this.baseMapper.getByAccount(request.getAccount());
        if (entity == null || StringUtils.isEmpty(entity.getId())) {
            throw new RequestBadException(UserError.USER_NOT_FOUND);
        }
        String password =  passwordEncoder.encode(request.getNewPassword());
        entity.setPassword(password);
        this.baseMapper.updateById(entity);
    }

    /**
     * 批量更新
     *
     * @param dos 数据模型集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<UserDto> dos) {
        List<UserEntity> entities = convert.listToEntities(dos);
        this.saveOrUpdateBatch(entities);
    }

    /**
     * 根据id删除
     *
     * @param id 主键
     */
    @Override
    public void deleteById(String id) {
        this.baseMapper.deleteByIdPhysical(id);
    }

    /**
     * 根据ids删除
     *
     * @param ids 主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        this.baseMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id获取详情
     *
     * @param id 主键
     */
    @Override
    public UserDto loadById(String id) {
        UserEntity entity = this.baseMapper.selectById(id);
        if (entity != null) {
            return convert.toDto(entity);
        }
        return null;
    }

    @Override
    public UserDto loadByAccount(String account) {
        UserEntity entity = this.baseMapper.getByAccount(account);
        if (entity != null) {
            return convert.toDto(entity);
        }
        return null;
    }

    @Override
    public UserDto loadAndChickPassword(String account, String password) {
        if (StringUtils.isBlank(account) || StringUtils.isBlank(password)) {
            throw new BusinessException(UserError.PHONE_CANNOT_BE_EMPTY);
        }
        if (account.equals(superAdminProperties.getAccount()) && password.equals(superAdminProperties.getPassword())) {
           UserDto dto = new UserDto();
           dto.setAccount(account);
           dto.setNickname(superAdminProperties.getNickname());
           dto.setId(superAdminProperties.getAccount());
           dto.setIdentity(-1);
           return dto;
        }
        UserEntity entity = this.baseMapper.getByAccount(account);
        if (entity == null) {
            throw new RequestBadException(UserError.ACCOUNT_OR_PASSWORD_ERROR);
        }
        if (entity.getStatus() != 1) {
            throw new RequestBadException(UserError.UserBanned);
        }
        if (!passwordEncoder.matches(password, entity.getPassword())) {
            throw new RequestBadException(UserError.ACCOUNT_OR_PASSWORD_ERROR);
        }
        return convert.toDto(entity);
    }


    /**
     * 根据id获取详情
     *
     * @param ids 主键
     */
    @Override
    public List<UserDto> loadByIds(List<String> ids) {
        List<UserEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
     * 加载所有数据
     *
     * @return 数据集合
     */
    @Override
    public List<UserDto> loadAll() {
        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getIsTemporary,0);
        List<UserEntity> entities = this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    @Override
    public UserDto loadAllByAccount(String account) {
        UserEntity entity = this.baseMapper.loadAllByAccount(account);
        if (entity != null) {
            return convert.toDto(entity);
        }
        return null;
    }

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @return
     */
    @Override
    public PageResult<UserDto> loadList(UserQuery query) {
        IPage<UserEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<UserDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
     * 获取条件
     *
     * @param query
     * @return
     */
    private Wrapper<UserEntity> getWrapper(UserQuery query) {
        LambdaQueryWrapper<UserEntity> wrapper = super.getWrapper(query);
        if (query.getIdentity() != null) {
            wrapper.eq(UserEntity::getIdentity, query.getIdentity());
        }
        if (StringUtils.isNotBlank(query.getKeyword())) {
            wrapper.and(w ->
                    w.like(UserEntity::getPhone, query.getKeyword())
                            .or()
                            .like(UserEntity::getAccount, query.getKeyword())
                            .or()
                            .like(UserEntity::getNickname, query.getKeyword())
                            .or()
                            .like(UserEntity::getEmail, query.getKeyword())
            );
        }
        // 处理开始时间
        if (query.getStartTime() != null) {
            wrapper.ge(UserEntity::getCreateTime, query.getStartTime());
        }

        // 处理结束时间
        if (query.getEndTime() != null) {
            wrapper.le(UserEntity::getCreateTime, query.getEndTime());
        }
        wrapper.eq(UserEntity::getIsTemporary,0);
        return wrapper;
    }
    /**
     * 获取账号
     * @param countryCode 国家代码
     * @param phone 手机号
     * @return 账号
     */
    private String getAccount(String countryCode ,String phone) {
        if(org.ares.cloud.common.utils.StringUtils.isBlank(countryCode) || org.ares.cloud.common.utils.StringUtils.isBlank(phone)){
            return null;
        }
        if (org.ares.cloud.common.utils.StringUtils.startsWith(countryCode,"+")){
            return countryCode + phone;
        }
        return "+"+countryCode + phone;
    }

}