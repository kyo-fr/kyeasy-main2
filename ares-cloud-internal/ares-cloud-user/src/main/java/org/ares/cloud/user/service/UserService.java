package org.ares.cloud.user.service;

import org.ares.cloud.api.user.dto.ChangePasswordReq;
import org.ares.cloud.api.user.dto.RecoverPasswordRequest;
import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.user.entity.UserEntity;
import org.ares.cloud.user.query.UserQuery;
import org.ares.cloud.database.service.BaseService;
import org.ares.cloud.common.dto.PageResult;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 用户 服务接口
* @version 1.0.0
* @date 2024-10-07
*/
public interface UserService extends BaseService<UserEntity> {
    /**
    * 将数据保存到仓库中.
    * @param dto  数据对象
    */
    String create(UserDto dto);
    /**
    * 查询不存在则创建临时用户.
     * @param phone 手机号
     * @param countryCode 国家代码
    */
    UserDto getOrCreateTemporaryUser(String countryCode,String phone);
    /**
    * 将数据保存到仓库中.
    * @param dos 数据对象集合
    */
    void create(List<UserDto> dos);
    /**
    * 更新仓库数据.
    * @param dto 数据对象集合
    */
    void update(UserDto dto);

    /**
     * 更新密码
     * @param request 请求数据
     */
    void changePassword(ChangePasswordReq request);

    /**
     * 找回密码
     * @param request 请求数据
     */
    void recoverPassword(RecoverPasswordRequest request);
    /**
    * 更新仓库数据.
    * @param dos 数据对象集合
    */
    void update(List<UserDto> dos);

    /**
    * 根据主键删除仓库数据.
    * @param id 删除数据
    */
    void deleteById(String id);

    /**
    * 根据主键删除仓库数据
    * @param ids 数据集合
    */
    void deleteByIds(List<String> ids);

    /**
    * 根据主键加载实体
    * @param id id
    * @return entity
    */
    UserDto loadById(String id);
    /**
     * 根据账号加载实体
     * @param account 长啊后
     * @return 用户
     */
    UserDto loadByAccount(String account);
    /**
     * 加载和校验账号密码
     * @param account 长啊后
     * @param password 密码
     * @return 用户
     */
    UserDto loadAndChickPassword(String account, String password);

    /**
    * 按实体ID加载实体列表.
    * @param ids 实体的id
    * @return 集合是实体
    */
    List<UserDto> loadByIds(List<String> ids);

    /**
    * 根据条件加载所有实体，该条件来自模板。
    * 例如：我想加载订单的所有项目，那么模板就是orderItem
    * 并将orderId设置为模板中的“orderId”列。
    * 然后，它将根据模板中的条件加载此订单的所有项目。
    * @return list of entities.
    */
    List<UserDto> loadAll();
    
    /**
     * 根据账号查询所有用户
     * @param account 账号
     * @return 用户
     */
    UserDto loadAllByAccount(String account);

    /**
    * 根据条件分页加载加载所有实体，该条件来自map
    * @param query 查询对象
    * @return list of entities.
    */
    PageResult<UserDto> loadList(UserQuery query);
}