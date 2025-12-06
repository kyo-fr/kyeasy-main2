package org.ares.cloud.platformInfo.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ares.cloud.api.user.enums.UserIdentity;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.platformInfo.convert.PlatformSocializeConvert;
import org.ares.cloud.platformInfo.dto.PlatformSocializeDto;
import org.ares.cloud.platformInfo.entity.PlatformInfoEntity;
import org.ares.cloud.platformInfo.entity.PlatformSocializeEntity;
import org.ares.cloud.platformInfo.enums.PlatformError;
import org.ares.cloud.platformInfo.query.PlatformSocializeQuery;
import org.ares.cloud.platformInfo.repository.PlatformSocializeRepository;
import org.ares.cloud.platformInfo.service.PlatformSocializeService;
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
* @description 平台海外社交 服务实现
* @version 1.0.0
* @date 2024-10-15
*/
@Service
@AllArgsConstructor
public class PlatformSocializeServiceImpl extends BaseServiceImpl<PlatformSocializeRepository, PlatformSocializeEntity> implements PlatformSocializeService{

    @Resource
    private PlatformSocializeConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(PlatformSocializeDto dto) {
        if (ApplicationContext.getIdentity() == UserIdentity.PlatformUsers.getValue()) {
            PlatformSocializeEntity entity = convert.toEntity(dto);
            //查询平台信息是否存在
            PlatformSocializeEntity platformSocialize = baseMapper.selectOne(null);
            if (platformSocialize != null) {
                baseMapper.updateById(entity);
            } else {
                baseMapper.insert(entity);
            }
        }else {
            throw new RequestBadException(PlatformError.PLATFORM_ACCOUNT_PERMISSIONS_ERROR);
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<PlatformSocializeDto> dos) {
        List<PlatformSocializeEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(PlatformSocializeDto dto) {
        PlatformSocializeEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<PlatformSocializeDto> dos) {
        List<PlatformSocializeEntity> entities = convert.listToEntities(dos);
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
    public PlatformSocializeDto loadById(String id) {
        PlatformSocializeEntity entity = this.baseMapper.selectById(id);
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
    public List<PlatformSocializeDto> loadByIds(List<String> ids) {
        List<PlatformSocializeEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<PlatformSocializeDto> loadAll() {
        LambdaQueryWrapper<PlatformSocializeEntity> wrapper = new LambdaQueryWrapper<>();
        List<PlatformSocializeEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<PlatformSocializeDto> loadList(PlatformSocializeQuery query) {
        IPage<PlatformSocializeEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<PlatformSocializeDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    @Override
    public PlatformSocializeDto getInfoByUserId() {
//        Integer identity = ApplicationContext.getIdentity();
//        if ( UserIdentity.PlatformUsers.getValue() == identity){
            PlatformSocializeEntity entity = baseMapper.selectOne(null);
            return   convert.toDto(entity);
//        }
//        return null;
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<PlatformSocializeEntity> getWrapper(PlatformSocializeQuery query){
        LambdaQueryWrapper<PlatformSocializeEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(PlatformSocializeEntity::getId);
        }
        return wrapper;
    }
}