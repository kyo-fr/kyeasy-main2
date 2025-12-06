package org.ares.cloud.merchantInfo.service.impl;


import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.merchantInfo.convert.MerchantFileUploadConvert;
import org.ares.cloud.merchantInfo.dto.MerchantFileUploadDto;
import org.ares.cloud.merchantInfo.entity.MerchantFileUploadEntity;
import org.ares.cloud.merchantInfo.query.MerchantFileUploadQuery;
import org.ares.cloud.merchantInfo.repository.MerchantFileUploadRepository;
import org.ares.cloud.merchantInfo.service.MerchantFileUploadService;
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
* @description 商户文件上传 服务实现
* @version 1.0.0
* @date 2024-10-09
*/
@Service
@AllArgsConstructor
public class MerchantFileUploadServiceImpl extends BaseServiceImpl<MerchantFileUploadRepository, MerchantFileUploadEntity> implements MerchantFileUploadService{

    @Resource
    private MerchantFileUploadConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(MerchantFileUploadDto dto) {
        MerchantFileUploadEntity entity = convert.toEntity(dto);
        //先查询是否存在
        MerchantFileUploadEntity merchantFileUploadEntity = baseMapper.selectById(dto.getTenantId());
        ApplicationContext.setIgnoreTenant(true);
        entity.setId(dto.getTenantId());
        if (merchantFileUploadEntity != null){
            //更新
            entity.setId(dto.getTenantId());
            this.baseMapper.updateById(entity);
        }else {
            this.baseMapper.insert(entity);
        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantFileUploadDto> dos) {
        List<MerchantFileUploadEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantFileUploadDto dto) {
        MerchantFileUploadEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantFileUploadDto> dos) {
        List<MerchantFileUploadEntity> entities = convert.listToEntities(dos);
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
    public MerchantFileUploadDto loadById(String id) {
        MerchantFileUploadEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantFileUploadDto> loadByIds(List<String> ids) {
        List<MerchantFileUploadEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantFileUploadDto> loadAll() {
        LambdaQueryWrapper<MerchantFileUploadEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantFileUploadEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantFileUploadDto> loadList(MerchantFileUploadQuery query) {
        IPage<MerchantFileUploadEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantFileUploadDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantFileUploadEntity> getWrapper(MerchantFileUploadQuery query){
        LambdaQueryWrapper<MerchantFileUploadEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(MerchantFileUploadEntity::getId);
        }
        return wrapper;
    }
}