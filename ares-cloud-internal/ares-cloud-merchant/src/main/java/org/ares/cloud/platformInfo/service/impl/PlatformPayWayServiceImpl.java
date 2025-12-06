package org.ares.cloud.platformInfo.service.impl;


import org.ares.cloud.platformInfo.convert.PlatformPayWayConvert;
import org.ares.cloud.platformInfo.dto.PlatformPayWayDto;
import org.ares.cloud.platformInfo.entity.PlatformPayWayEntity;
import org.ares.cloud.platformInfo.query.PlatformPayWayQuery;
import org.ares.cloud.platformInfo.repository.PlatformPayWayRepository;
import org.ares.cloud.platformInfo.service.PlatformPayWayService;
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
* @description 平台支付类型 服务实现
* @version 1.0.0
* @date 2024-10-15
*/
@Service
@AllArgsConstructor
public class PlatformPayWayServiceImpl extends BaseServiceImpl<PlatformPayWayRepository, PlatformPayWayEntity> implements PlatformPayWayService{

    @Resource
    private PlatformPayWayConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PlatformPayWayDto dto) {
        PlatformPayWayEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<PlatformPayWayDto> dos) {
        List<PlatformPayWayEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(PlatformPayWayDto dto) {
        PlatformPayWayEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<PlatformPayWayDto> dos) {
        List<PlatformPayWayEntity> entities = convert.listToEntities(dos);
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
    public PlatformPayWayDto loadById(String id) {
        PlatformPayWayEntity entity = this.baseMapper.selectById(id);
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
    public List<PlatformPayWayDto> loadByIds(List<String> ids) {
        List<PlatformPayWayEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<PlatformPayWayDto> loadAll() {
        LambdaQueryWrapper<PlatformPayWayEntity> wrapper = new LambdaQueryWrapper<>();
        List<PlatformPayWayEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<PlatformPayWayDto> loadList(PlatformPayWayQuery query) {
        IPage<PlatformPayWayEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<PlatformPayWayDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<PlatformPayWayEntity> getWrapper(PlatformPayWayQuery query){
        LambdaQueryWrapper<PlatformPayWayEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(PlatformPayWayEntity::getId);
        }
        return wrapper;
    }
}