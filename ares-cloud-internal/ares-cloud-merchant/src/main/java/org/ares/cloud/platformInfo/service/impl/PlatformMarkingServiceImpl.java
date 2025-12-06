package org.ares.cloud.platformInfo.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ares.cloud.common.exception.BaseException;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.merchantInfo.enums.MerchantError;
import org.ares.cloud.platformInfo.convert.PlatformMarkingConvert;
import org.ares.cloud.platformInfo.dto.PlatformMarkingDto;
import org.ares.cloud.platformInfo.entity.PlatformMarkingEntity;
import org.ares.cloud.platformInfo.enums.PlatformError;
import org.ares.cloud.platformInfo.query.PlatformMarkingQuery;
import org.ares.cloud.platformInfo.repository.PlatformMarkingRepository;
import org.ares.cloud.platformInfo.service.PlatformMarkingService;
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
* @author hugo platformInfo
* @description 商品标注 服务实现
* @version 1.0.0
* @date 2024-11-04
*/
@Service
@AllArgsConstructor
public class PlatformMarkingServiceImpl extends BaseServiceImpl<PlatformMarkingRepository, PlatformMarkingEntity> implements PlatformMarkingService{

    @Resource
    private PlatformMarkingConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PlatformMarkingDto dto) {
        //查询该标注是否存在
        PlatformMarkingEntity entity = convert.toEntity(dto);
        QueryWrapper<PlatformMarkingEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("marking_name",dto.getMarkingName());
        PlatformMarkingEntity selectOne = baseMapper.selectOne(wrapper);
        if (selectOne == null){
            this.baseMapper.insert(entity);
        }else {
            System.out.println("该标注已存在："+dto.getMarkingName());
            throw new RequestBadException(PlatformError.PLATFORM_MARKING_EXIST_ERROR);
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<PlatformMarkingDto> dos) {
        List<PlatformMarkingEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(PlatformMarkingDto dto) {
        PlatformMarkingEntity entity = convert.toEntity(dto);
        QueryWrapper<PlatformMarkingEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("marking_name",dto.getMarkingName());
        PlatformMarkingEntity selectOne = baseMapper.selectOne(wrapper);
        if (selectOne != null) {
            if (selectOne.getId().equals(dto.getId())) {
                //可以更新
                this.updateById(entity);
            } else {
                System.out.println("该标注已存在,请勿重复添加");
                throw new RequestBadException(PlatformError.PLATFORM_MARKING_EXIST_ERROR);
            }
        }else {
            this.updateById(entity);
        }
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<PlatformMarkingDto> dos) {
        List<PlatformMarkingEntity> entities = convert.listToEntities(dos);
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
    public PlatformMarkingDto loadById(String id) {
        PlatformMarkingEntity entity = this.baseMapper.selectById(id);
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
    public List<PlatformMarkingDto> loadByIds(List<String> ids) {
        List<PlatformMarkingEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<PlatformMarkingDto> loadAll() {
        LambdaQueryWrapper<PlatformMarkingEntity> wrapper = new LambdaQueryWrapper<>();
        List<PlatformMarkingEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<PlatformMarkingDto> loadList(PlatformMarkingQuery query) {
        IPage<PlatformMarkingEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<PlatformMarkingDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<PlatformMarkingEntity> getWrapper(PlatformMarkingQuery query){
        LambdaQueryWrapper<PlatformMarkingEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(PlatformMarkingEntity::getId);
        }
        return wrapper;
    }
}