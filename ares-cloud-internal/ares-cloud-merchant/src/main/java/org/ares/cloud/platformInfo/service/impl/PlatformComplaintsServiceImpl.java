package org.ares.cloud.platformInfo.service.impl;


import org.ares.cloud.platformInfo.convert.PlatformComplaintsConvert;
import org.ares.cloud.platformInfo.dto.PlatformComplaintsDto;
import org.ares.cloud.platformInfo.entity.PlatformComplaintsEntity;
import org.ares.cloud.platformInfo.query.PlatformComplaintsQuery;
import org.ares.cloud.platformInfo.repository.PlatformComplaintsRepository;
import org.ares.cloud.platformInfo.service.PlatformComplaintsService;
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
* @description 平台投诉建议 服务实现
* @version 1.0.0
* @date 2024-10-17
*/
@Service
@AllArgsConstructor
public class PlatformComplaintsServiceImpl extends BaseServiceImpl<PlatformComplaintsRepository, PlatformComplaintsEntity> implements PlatformComplaintsService{

    @Resource
    private PlatformComplaintsConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PlatformComplaintsDto dto) {
        PlatformComplaintsEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<PlatformComplaintsDto> dos) {
        List<PlatformComplaintsEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(PlatformComplaintsDto dto) {
        PlatformComplaintsEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<PlatformComplaintsDto> dos) {
        List<PlatformComplaintsEntity> entities = convert.listToEntities(dos);
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
    public PlatformComplaintsDto loadById(String id) {
        PlatformComplaintsEntity entity = this.baseMapper.selectById(id);
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
    public List<PlatformComplaintsDto> loadByIds(List<String> ids) {
        List<PlatformComplaintsEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<PlatformComplaintsDto> loadAll() {
        LambdaQueryWrapper<PlatformComplaintsEntity> wrapper = new LambdaQueryWrapper<>();
        List<PlatformComplaintsEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<PlatformComplaintsDto> loadList(PlatformComplaintsQuery query) {
        IPage<PlatformComplaintsEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<PlatformComplaintsDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<PlatformComplaintsEntity> getWrapper(PlatformComplaintsQuery query){
        LambdaQueryWrapper<PlatformComplaintsEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(PlatformComplaintsEntity::getId);
        }
        return wrapper;
    }
}