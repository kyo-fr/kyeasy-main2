package org.ares.cloud.platformInfo.service.impl;


import org.ares.cloud.api.base.BusinessIdServerClient;
import org.ares.cloud.platformInfo.convert.PlatformWorkOrderContentConvert;
import org.ares.cloud.platformInfo.dto.PlatformWorkOrderContentDto;
import org.ares.cloud.platformInfo.entity.PlatformWorkOrderContentEntity;
import org.ares.cloud.platformInfo.query.PlatformWorkOrderContentQuery;
import org.ares.cloud.platformInfo.repository.PlatformWorkOrderContentRepository;
import org.ares.cloud.platformInfo.service.PlatformWorkOrderContentService;
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
* @description 工单内容 服务实现
* @version 1.0.0
* @date 2024-10-17
*/
@Service
@AllArgsConstructor
public class PlatformWorkOrderContentServiceImpl extends BaseServiceImpl<PlatformWorkOrderContentRepository, PlatformWorkOrderContentEntity> implements PlatformWorkOrderContentService{

    @Resource
    private PlatformWorkOrderContentConvert convert;



    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PlatformWorkOrderContentDto dto) {
        PlatformWorkOrderContentEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<PlatformWorkOrderContentDto> dos) {
        List<PlatformWorkOrderContentEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(PlatformWorkOrderContentDto dto) {
        PlatformWorkOrderContentEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<PlatformWorkOrderContentDto> dos) {
        List<PlatformWorkOrderContentEntity> entities = convert.listToEntities(dos);
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
    public PlatformWorkOrderContentDto loadById(String id) {
        PlatformWorkOrderContentEntity entity = this.baseMapper.selectById(id);
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
    public List<PlatformWorkOrderContentDto> loadByIds(List<String> ids) {
        List<PlatformWorkOrderContentEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<PlatformWorkOrderContentDto> loadAll() {
        LambdaQueryWrapper<PlatformWorkOrderContentEntity> wrapper = new LambdaQueryWrapper<>();
        List<PlatformWorkOrderContentEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<PlatformWorkOrderContentDto> loadList(PlatformWorkOrderContentQuery query) {
        IPage<PlatformWorkOrderContentEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<PlatformWorkOrderContentDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<PlatformWorkOrderContentEntity> getWrapper(PlatformWorkOrderContentQuery query){
        LambdaQueryWrapper<PlatformWorkOrderContentEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(query.getOrder())){
            wrapper.orderByDesc(PlatformWorkOrderContentEntity::getId);
        }
        return wrapper;
    }
}