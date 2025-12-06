package org.ares.cloud.platformInfo.service.impl;


import org.ares.cloud.platformInfo.convert.PlatformLanguageConvert;
import org.ares.cloud.platformInfo.dto.PlatformLanguageDto;
import org.ares.cloud.platformInfo.entity.PlatformLanguageEntity;
import org.ares.cloud.platformInfo.query.PlatformLanguageQuery;
import org.ares.cloud.platformInfo.repository.PlatformLanguageRepository;
import org.ares.cloud.platformInfo.service.PlatformLanguageService;
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
* @author hugo tangxkwork@163.com
* @description 平台设置语言 服务实现
* @version 1.0.0
* @date 2024-10-15
*/
@Service
@AllArgsConstructor
public class PlatformLanguageServiceImpl extends BaseServiceImpl<PlatformLanguageRepository, PlatformLanguageEntity> implements PlatformLanguageService{

    @Resource
    private PlatformLanguageConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PlatformLanguageDto dto) {
        PlatformLanguageEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<PlatformLanguageDto> dos) {
        List<PlatformLanguageEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(PlatformLanguageDto dto) {
        PlatformLanguageEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<PlatformLanguageDto> dos) {
        List<PlatformLanguageEntity> entities = convert.listToEntities(dos);
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
    public PlatformLanguageDto loadById(String id) {
        PlatformLanguageEntity entity = this.baseMapper.selectById(id);
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
    public List<PlatformLanguageDto> loadByIds(List<String> ids) {
        List<PlatformLanguageEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<PlatformLanguageDto> loadAll() {
        LambdaQueryWrapper<PlatformLanguageEntity> wrapper = new LambdaQueryWrapper<>();
        List<PlatformLanguageEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<PlatformLanguageDto> loadList(PlatformLanguageQuery query) {
        IPage<PlatformLanguageEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<PlatformLanguageDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<PlatformLanguageEntity> getWrapper(PlatformLanguageQuery query){
        LambdaQueryWrapper<PlatformLanguageEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(PlatformLanguageEntity::getId);
        }
        return wrapper;
    }
}