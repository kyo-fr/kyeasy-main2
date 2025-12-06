package ${package}.${moduleName}.service.impl;


import ${package}.${moduleName}.convert.${ClassName}Convert;
import ${package}.${moduleName}.dto.${ClassName}Dto;
import ${package}.${moduleName}.entity.${ClassName}Entity;
import ${package}.${moduleName}.query.${ClassName}Query;
import ${package}.${moduleName}.repository.${ClassName}Repository;
import ${package}.${moduleName}.service.${ClassName}Service;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author ${author} ${email}
* @description ${dis} 服务实现
* @version ${version}
* @date ${date}
*/
@Service
@AllArgsConstructor
public class ${ClassName}ServiceImpl extends BaseServiceImpl<${ClassName}Repository, ${ClassName}Entity> implements ${ClassName}Service{

    @Resource
    private ${ClassName}Convert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(${ClassName}Dto dto) {
        ${ClassName}Entity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<${ClassName}Dto> dos) {
        List<${ClassName}Entity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(${ClassName}Dto dto) {
        ${ClassName}Entity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<${ClassName}Dto> dos) {
        List<${ClassName}Entity> entities = convert.listToEntities(dos);
        this.saveOrUpdateBatch(entities);
    }

    /**
    * 根据id删除
    * @param id  主键
    */
    @Override
    public void deleteById(String id) {
        this.baseMapper.deleteById(id);
    }

    /**
    * 根据ids删除
    * @param ids 主键集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        this.baseMapper.deleteBatchIds(ids);
    }

    /**
    * 根据id获取详情
    * @param id 主键
    */
    @Override
    public ${ClassName}Dto loadById(String id) {
        ${ClassName}Entity entity = this.baseMapper.selectById(id);
        if (entity != null){
            return  convert.toDto(entity);
        }
        return null;
    }
    /**
    * 根据id获取详情
    * @param ids 主键
    */
    @Override
    public List<${ClassName}Dto> loadByIds(List<String> ids) {
        List<${ClassName}Entity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<${ClassName}Dto> loadAll() {
        LambdaQueryWrapper<${ClassName}Entity> wrapper = new LambdaQueryWrapper<>();
        List<${ClassName}Entity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<${ClassName}Dto> loadList(${ClassName}Query query) {
        IPage<${ClassName}Entity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<${ClassName}Dto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<${ClassName}Entity> getWrapper(${ClassName}Query query){
        LambdaQueryWrapper<${ClassName}Entity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(${ClassName}Entity::getId);
        }
        return wrapper;
    }
}