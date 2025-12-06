package ${package}.${moduleName}.query;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.ares.cloud.common.query.Query;

<#list importList as i>
import ${i!};
</#list>

/**
* @author ${author} ${email}
* @description ${dis} 查询原型
* @version ${version}
* @date ${date}
*/
@Data
@EqualsAndHashCode(callSuper = false)
@Schema(description = "${tableComment}查询")
public class ${ClassName}Query extends Query {
<#list queryList as field>
    <#if field.fieldComment!?length gt 0>
    @Schema(description = "${field.fieldComment}")
    </#if>
    private ${field.attrType} ${field.attrName};

</#list>
}