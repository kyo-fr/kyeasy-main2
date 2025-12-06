package ${package}.${moduleName}.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
<#if dtoBaseClass?has_content>
import ${dtoBaseClass.packageName}.${dtoBaseClass.code};
</#if>
import org.ares.cloud.common.utils.DateUtils;
import com.fasterxml.jackson.annotation.JsonProperty;
<#list importList as i>
import ${i!};
</#list>
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
* @author ${author} ${email}
* @description ${dis} 数据模型
* @version ${version}
* @date ${date}
*/
@EqualsAndHashCode(callSuper = true)
@Schema(description = "${dis}")
public class ${ClassName}Dto <#if dtoBaseClass?has_content>extends ${dtoBaseClass.code}</#if> {
	private static final long serialVersionUID = 1L;

<#list fieldList as field>
<#if !field.baseField>

	<#if field.isNull?number == 2>
	@Schema(description = "${field.fieldComment}",requiredMode = Schema.RequiredMode.REQUIRED)
	</#if>
	<#if field.isNull?number == 1>
	@Schema(description = "${field.fieldComment}")
	</#if>
	<#if field.attrType == 'Date'>
	@JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
	</#if>
	@JsonProperty(value = "${field.attrName}")
	<#if field.attrType == 'String' && field.isNull?number == 2>
	@NotBlank(message = "{validation.${moduleName}.${field.attrName}}")
	</#if>
	<#if field.attrType == 'String' &&  field.length != "0">
	@Size(max = ${field.length}, message = "validation.size.max")
	</#if>
	private ${field.attrType} ${field.attrName};
</#if>
</#list>
<#-- 生成 getter and setter方法 -->
<#list fieldList as field>
	<#if !field.baseField>
	/**
	* 获取${field.fieldComment}
	* @return ${field.fieldComment}
	*/
	public ${field.attrType} get${field.attrName?cap_first}() {
	return ${field.attrName};
	}

	/**
	* 设置${field.fieldComment}
	* @param ${field.attrName} ${field.fieldComment}
	*/
	public void set${field.attrName?cap_first}(${field.attrType} ${field.attrName}) {
	this.${field.attrName} = ${field.attrName};
	}
	</#if>
</#list>
}