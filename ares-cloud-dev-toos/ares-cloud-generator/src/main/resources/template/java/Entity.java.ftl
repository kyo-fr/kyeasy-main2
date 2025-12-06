package ${package}.${moduleName}.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
<#if entityBaseClass?has_content>
import ${entityBaseClass.packageName}.${entityBaseClass.code};
</#if>
<#list importList as i>
import ${i!};
</#list>

/**
* @author ${author} ${email}
* @description ${dis} 实体
* @version ${version}
* @date ${date}
*/
@TableName("${tableName}")
@EqualsAndHashCode(callSuper = false)
public class ${ClassName}Entity <#if entityBaseClass?has_content>extends ${entityBaseClass.code}</#if> {
<#list fieldList as field>
<#if !field.baseField>
	<#if field.fieldComment!?length gt 0>
	/**
	* ${field.fieldComment}
	*/
	</#if>
    <#if field.autoFill == "INSERT">
	@TableField(fill = FieldFill.INSERT)
	</#if>
	<#if field.autoFill == "INSERT_UPDATE">
	@TableField(fill = FieldFill.INSERT_UPDATE)
	</#if>
	<#if field.autoFill == "UPDATE">
		@TableField(fill = FieldFill.UPDATE)
	</#if>
    <#if field.primaryPk>
	@TableId
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