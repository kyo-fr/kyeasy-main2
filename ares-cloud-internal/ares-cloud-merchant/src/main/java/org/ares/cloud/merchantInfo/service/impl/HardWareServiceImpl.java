package org.ares.cloud.merchantInfo.service.impl;


import org.ares.cloud.merchantInfo.convert.HardWareConvert;
import org.ares.cloud.merchantInfo.dto.HardWareDto;
import org.ares.cloud.merchantInfo.entity.HardWareEntity;
import org.ares.cloud.merchantInfo.query.HardWareQuery;
import org.ares.cloud.merchantInfo.repository.HardWareRepository;
import org.ares.cloud.merchantInfo.service.HardWareService;
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
* @description 硬件管理 服务实现
* @version 1.0.0
* @date 2024-10-12
*/
@Service
@AllArgsConstructor
public class HardWareServiceImpl extends BaseServiceImpl<HardWareRepository, HardWareEntity> implements HardWareService{

    @Resource
    private HardWareConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(HardWareDto dto) {
        HardWareEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<HardWareDto> dos) {
        List<HardWareEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(HardWareDto dto) {
        HardWareEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<HardWareDto> dos) {
        List<HardWareEntity> entities = convert.listToEntities(dos);
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
    public HardWareDto loadById(String id) {
        HardWareEntity entity = this.baseMapper.selectById(id);
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
    public List<HardWareDto> loadByIds(List<String> ids) {
        List<HardWareEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<HardWareDto> loadAll() {
        LambdaQueryWrapper<HardWareEntity> wrapper = new LambdaQueryWrapper<>();
        List<HardWareEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<HardWareDto> loadList(HardWareQuery query) {
        IPage<HardWareEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<HardWareDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<HardWareEntity> getWrapper(HardWareQuery query){
        LambdaQueryWrapper<HardWareEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(HardWareEntity::getId);
        }
        return wrapper;
    }
}