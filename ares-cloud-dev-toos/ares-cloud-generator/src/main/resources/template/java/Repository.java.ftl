package ${package}.${moduleName}.repository;

import ${package}.${moduleName}.entity.${ClassName}Entity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author ${author} ${email}
* @description ${dis} 数据仓库
* @version ${version}
* @date ${date}
*/
@Mapper
public interface ${ClassName}Repository extends BaseMapper<${ClassName}Entity> {
	
}