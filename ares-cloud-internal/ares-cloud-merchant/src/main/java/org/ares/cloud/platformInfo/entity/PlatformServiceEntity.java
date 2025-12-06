package org.ares.cloud.platformInfo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.EqualsAndHashCode;
import org.ares.cloud.database.entity.BaseEntity;

/**
* @author hugo tangxkwork@163.com
* @description 服务和帮助 实体
* @version 1.0.0
* @date 2024-10-30
*/
@TableName("platform_service")
@EqualsAndHashCode(callSuper = false)
public class PlatformServiceEntity extends BaseEntity {
	/**
	* 内容
	*/
	private String content;
	/**
	* 租户
	*/
	private String tenantId;
	/**
	* 类型:1-帮助；2-服务条款
	*/
	private Integer type;
	/**
	* 标题
	*/
	private String title;

	//用户身份(1:普通会员;2:骑士;3:商户;4:平台用户)
	private String identity;

	/**
	* 获取内容
	* @return 内容
	*/
	public String getContent() {
		return content;
	}

	/**
	* 设置内容
	* @param content 内容
	*/
	public void setContent(String content) {
		this.content = content;
	}
	/**
	* 获取租户
	* @return 租户
	*/
	public String getTenantId() {
		return tenantId;
	}

	/**
	* 设置租户
	* @param tenantId 租户
	*/
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	/**
	* 获取类型:1-帮助；2-服务条款
	* @return 类型:1-帮助；2-服务条款
	*/
	public Integer getType() {
		return type;
	}

	/**
	* 设置类型:1-帮助；2-服务条款
	* @param type 类型:1-帮助；2-服务条款
	*/
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	* 获取标题
	* @return 标题
	*/
	public String getTitle() {
		return title;
	}

	/**
	* 设置标题
	* @param title 标题
	*/
	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

}