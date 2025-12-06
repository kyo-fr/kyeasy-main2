package org.ares.cloud.message.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo tangxkwork@163.com
* @description 用户 实体
* @version 1.0.0
* @date 2024-10-07
*/
@TableName("users")
@Data
@EqualsAndHashCode(callSuper = false)
public class UserEntity extends BaseEntity {
	/**
	* 手机号
	*/
	private String phone;
	/**
	* 国家代码
	*/
	private String countryCode;
	/**
	* 账号
	*/
	private String account;
	/**
	* 昵称
	*/
	private String nickname;
}