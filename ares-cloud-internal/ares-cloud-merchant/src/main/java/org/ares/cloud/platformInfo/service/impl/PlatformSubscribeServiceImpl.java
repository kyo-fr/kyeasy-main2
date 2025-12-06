package org.ares.cloud.platformInfo.service.impl;


import cn.hutool.crypto.digest.MD5;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.platformInfo.convert.PlatformSubscribeConvert;
import org.ares.cloud.platformInfo.dto.PlatformSubscribeDto;
import org.ares.cloud.platformInfo.entity.PlatformSubscribeEntity;
import org.ares.cloud.platformInfo.enums.PlatformError;
import org.ares.cloud.platformInfo.query.PlatformSubscribeQuery;
import org.ares.cloud.platformInfo.repository.PlatformSubscribeRepository;
import org.ares.cloud.platformInfo.service.PlatformSubscribeService;
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
* @description 订阅基础信息 服务实现
* @version 1.0.0
* @date 2024-10-31
*/
@Service
@AllArgsConstructor
public class PlatformSubscribeServiceImpl extends BaseServiceImpl<PlatformSubscribeRepository, PlatformSubscribeEntity> implements PlatformSubscribeService{

    @Resource
    private PlatformSubscribeConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PlatformSubscribeDto dto) {
        //有则更新无责插入
        PlatformSubscribeEntity entity = convert.toEntity(dto);
        PlatformSubscribeEntity entity1 = baseMapper.selectOne(new LambdaQueryWrapper<PlatformSubscribeEntity>().eq(PlatformSubscribeEntity::getSubscribeType, dto.getSubscribeType()));
         if (entity1 == null){
            this.baseMapper.insert(entity);
        }else {
             entity.setId(entity1.getId());
             this.updateById(entity);
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<PlatformSubscribeDto> dos) {
        List<PlatformSubscribeEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(PlatformSubscribeDto dto) {
        PlatformSubscribeEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<PlatformSubscribeDto> dos) {
        List<PlatformSubscribeEntity> entities = convert.listToEntities(dos);
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
    public PlatformSubscribeDto loadById(String id) {
        PlatformSubscribeEntity entity = this.baseMapper.selectById(id);
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
    public List<PlatformSubscribeDto> loadByIds(List<String> ids) {
        List<PlatformSubscribeEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<PlatformSubscribeDto> loadAll() {
        LambdaQueryWrapper<PlatformSubscribeEntity> wrapper = new LambdaQueryWrapper<>();
        List<PlatformSubscribeEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<PlatformSubscribeDto> loadList(PlatformSubscribeQuery query) {
        IPage<PlatformSubscribeEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<PlatformSubscribeDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<PlatformSubscribeEntity> getWrapper(PlatformSubscribeQuery query){
        LambdaQueryWrapper<PlatformSubscribeEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(PlatformSubscribeEntity::getId);
        }
        return wrapper;
    }
}