package org.ares.cloud.platformInfo.service.impl;


import org.ares.cloud.api.user.enums.UserIdentity;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.platformInfo.convert.PlatformInfoConvert;
import org.ares.cloud.platformInfo.dto.PlatformInfoDto;
import org.ares.cloud.platformInfo.entity.PlatformInfoEntity;
import org.ares.cloud.platformInfo.query.PlatformInfoQuery;
import org.ares.cloud.platformInfo.repository.PlatformInfoRepository;
import org.ares.cloud.platformInfo.service.PlatformInfoService;
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
* @description 平台信息 服务实现
* @version 1.0.0
* @date 2024-10-15
*/
@Service
@AllArgsConstructor
public class PlatformInfoServiceImpl extends BaseServiceImpl<PlatformInfoRepository, PlatformInfoEntity> implements PlatformInfoService{

    @Resource
    private PlatformInfoConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)

    public void create(PlatformInfoDto dto) {
        if (ApplicationContext.getIdentity() == UserIdentity.PlatformUsers.getValue()) {
            PlatformInfoEntity entity = convert.toEntity(dto);
            //查询平台信息是否存在
            PlatformInfoEntity platformInfo = baseMapper.selectOne(null);
            if (platformInfo != null) {
                baseMapper.updateById(entity);
            } else {
                baseMapper.insert(entity);
            }
        }else {
            throw new RequestBadException("该账号权限不足");
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<PlatformInfoDto> dos) {
        List<PlatformInfoEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(PlatformInfoDto dto) {
        PlatformInfoEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<PlatformInfoDto> dos) {
        List<PlatformInfoEntity> entities = convert.listToEntities(dos);
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
    public PlatformInfoDto loadById(String id) {
        PlatformInfoEntity entity = this.baseMapper.selectById(id);
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
    public List<PlatformInfoDto> loadByIds(List<String> ids) {
        List<PlatformInfoEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<PlatformInfoDto> loadAll() {
        LambdaQueryWrapper<PlatformInfoEntity> wrapper = new LambdaQueryWrapper<>();
        List<PlatformInfoEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<PlatformInfoDto> loadList(PlatformInfoQuery query) {
        IPage<PlatformInfoEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<PlatformInfoDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    @Override
    public PlatformInfoDto getInfoByUserId() {
        PlatformInfoEntity platformInfoEntity = baseMapper.selectOne(null);
        if (platformInfoEntity != null){
            return convert.toDto(platformInfoEntity);
        }
        return null;
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<PlatformInfoEntity> getWrapper(PlatformInfoQuery query){
        LambdaQueryWrapper<PlatformInfoEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(PlatformInfoEntity::getId);
        }
        return wrapper;
    }
}