package ${package}.${moduleName}.convert;

import ${package}.${moduleName}.entity.${ClassName}Entity;
import ${package}.${moduleName}.dto.${ClassName}Dto;
<#if dtoBaseClass?has_content>
import ${dtoBaseClass.packageName}.${dtoBaseClass.code};
</#if>
<#if entityBaseClass?has_content>
import ${entityBaseClass.packageName}.${entityBaseClass.code};
</#if>
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
* @author ${author} ${email}
* @description ${dis} 转换器
* @version ${version}
* @date ${date}
*/
@Mapper(componentModel = "spring")
public interface ${ClassName}Convert {
<#if dtoBaseClass?has_content && entityBaseClass?has_content>
    /**
    * 父类转换
    * @param entity 实体
    * @return  数据传输对象
    */
    ${dtoBaseClass.code} toBaseDto(${entityBaseClass.code} entity);

    /**
    * 父类转换
    * @param dto 数据传输对象
    * @return 实体
    */
    ${entityBaseClass.code} toBaseEntity(${dtoBaseClass.code} dto);

    /**
    * 将实体转换为数据传输对象。
    *
    * @param entity 实体。
    * @return 数据传输对象。
    */
    @InheritConfiguration(name = "toBaseDto")
    <#list fieldList as field>
        <#if !field.baseField>
    @Mapping(target = "${field.attrName}", source = "${field.attrName}")
        </#if>
    </#list>
    ${ClassName}Dto toDto(${ClassName}Entity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List< ${ClassName}Dto> listToDto(List<${ClassName}Entity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    <#list fieldList as field>
    <#if !field.baseField>
    @Mapping(target = "${field.attrName}", source = "${field.attrName}")
    </#if>
    </#list>
    ${ClassName}Entity toEntity( ${ClassName}Dto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<${ClassName}Entity> listToEntities(List< ${ClassName}Dto> list);
<#else>
    /**
    * 将实体转换为数据传输对象。
    *
    * @param entity 实体。
    * @return 数据传输对象。
    */
    @InheritConfiguration(name = "toBaseDto")
    <#list fieldList as field>
        <#if !field.baseField>
    @Mapping(target = "${field.attrName}", source = "${field.attrName}")
        </#if>
    </#list>
    ${ClassName}Dto toDto(${ClassName}Entity entity);

    /**
    * 将多个实体转换为数据传输对象。
    *
    * @param list 实体列表。
    * @return 数据传输对象列表。
    */
    List<${ClassName}Dto> listToDto(List<${ClassName}Entity> list);

    /**
    * 将数据传输对象转换为实体。
    *
    * @param dto 数据传输对象。
    * @return 实体。
    */
    @InheritConfiguration(name = "toBaseEntity")
    <#list fieldList as field>
        <#if !field.baseField>
    @Mapping(target = "${field.attrName}", source = "${field.attrName}")
        </#if>
    </#list>
    ${ClassName}Entity toEntity(${ClassName}Dto dto);

    /**
    * 将多个数据传输对象转换为实体。
    *
    * @param list 数据传输对象列表。
    * @return 实体列表。
    */
    List<${ClassName}Entity> listToEntities(List<${ClassName}Dto> list);
</#if>
}