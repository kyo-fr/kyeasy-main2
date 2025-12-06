package org.ares.cloud.s3.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.dto.TenantDto;
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author hugo tangxkwork@163.com
* @description s3存储 数据模型
* @version 1.0.0
* @date 2024-10-13
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "s3存储")
public class ObjectStorageDto extends TenantDto {
	private static final long serialVersionUID = 1L;


	@Schema(description = "原始文件名")
	@JsonProperty(value = "originalFileName")
	@Size(max = 255, message = "validation.size.max")
	private String originalFileName;

	@Schema(description = "文件类型（MIME 类型）")
	@JsonProperty(value = "fileType")
	@Size(max = 100, message = "validation.size.max")
	private String fileType;

	@Schema(description = "存储容器名",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "container")
	@NotBlank(message = "{validation.s3.container}")
	@Size(max = 50, message = "validation.size.max")
	private String container;

	@Schema(description = "所属模块")
	@JsonProperty(value = "model")
	@Size(max = 50, message = "validation.size.max")
	private String model;

	@Schema(description = "生成的文件名",requiredMode = Schema.RequiredMode.REQUIRED)
	@JsonProperty(value = "name")
	@NotBlank(message = "{validation.s3.name}")
	@Size(max = 255, message = "validation.size.max")
	private String name;

	@Schema(description = "文件大小（字节）")
	@JsonProperty(value = "fileSize")
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