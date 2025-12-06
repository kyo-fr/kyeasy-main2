package org.ares.cloud.s3.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.TenantEntity;

/**
* @author hugo tangxkwork@163.com
* @description s3存储 实体
* @version 1.0.0
* @date 2024-10-13
*/
@TableName("sys_s3_storage")
@EqualsAndHashCode(callSuper = false)
public class ObjectStorageEntity extends TenantEntity {
	/**
	* 原始文件名
	*/
	private String originalFileName;
	/**
	* 文件类型（MIME 类型）
	*/
	private String fileType;
	/**
	* 存储容器名
	*/
	private String container;
	/**
	* 所属模块
	*/
	private String model;
	/**
	* 生成的文件名
	*/
	private String name;
	/**
	* 文件大小（字节）
	*/
	private Long fileSize;
	/**
	* 获取原始文件名
	* @return 原始文件名
	*/
	public String getOriginalFileName() {
		return originalFileName;
	}

	/**
	* 设置原始文件名
	* @param originalFileName 原始文件名
	*/
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	/**
	* 获取文件类型（MIME 类型）
	* @return 文件类型（MIME 类型）
	*/
	public String getFileType() {
		return fileType;
	}

	/**
	* 设置文件类型（MIME 类型）
	* @param fileType 文件类型（MIME 类型）
	*/
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	/**
	* 获取存储容器名
	* @return 存储容器名
	*/
	public String getContainer() {
		return container;
	}

	/**
	* 设置存储容器名
	* @param container 存储容器名
	*/
	public void setContainer(String container) {
		this.container = container;
	}
	/**
	* 获取所属模块
	* @return 所属模块
	*/
	public String getModel() {
		return model;
	}

	/**
	* 设置所属模块
	* @param model 所属模块
	*/
	public void setModel(String model) {
		this.model = model;
	}
	/**
	* 获取生成的文件名
	* @return 生成的文件名
	*/
	public String getName() {
		return name;
	}

	/**
	* 设置生成的文件名
	* @param name 生成的文件名
	*/
	public void setName(String name) {
		this.name = name;
	}
	/**
	* 获取文件大小（字节）
	* @return 文件大小（字节）
	*/
	public Long getFileSize() {
		return fileSize;
	}

	/**
	* 设置文件大小（字节）
	* @param fileSize 文件大小（字节）
	*/
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
}