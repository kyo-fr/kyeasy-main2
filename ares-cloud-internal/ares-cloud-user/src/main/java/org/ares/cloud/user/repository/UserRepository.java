package org.ares.cloud.user.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import feign.Param;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.ares.cloud.user.entity.UserEntity;

import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description 用户 数据仓库
* @version 1.0.0
* @date 2024-10-07
*/
@Mapper
public interface UserRepository extends BaseMapper<UserEntity> {
    /**
     * 通过账户名查询用户
     * @param account 用户账户名
     * @return UserEntity 用户实体
     */
    @Select("SELECT * FROM users WHERE account = #{account} and is_temporary = 0")
    UserEntity getByAccount(@Param("account") String account);
    /**
     * 通过账户名查询用户不忽略临时账号
     * @param account 用户账户名
     * @return UserEntity 用户实体
     */
    @Select("SELECT * FROM users WHERE account = #{account}")
    UserEntity loadAllByAccount(@Param("account")String account);
    /**
     * 查询所有临时用户
     * @return 临时用户列表
     */
    @Select("SELECT * FROM users WHERE is_temporary = 1")
    List<UserEntity> getTemporaryUsers();

    // 自定义 Mapper 方法，直接写物理 SQL
    @Delete("DELETE FROM users WHERE id = #{id}")
    int deleteByIdPhysical(@Param("id") String id);

}