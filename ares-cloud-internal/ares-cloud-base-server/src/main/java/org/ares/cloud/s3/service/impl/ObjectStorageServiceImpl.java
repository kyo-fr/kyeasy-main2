package org.ares.cloud.s3.service.impl;


import com.alibaba.fastjson.JSONObject;
import org.ares.cloud.api.merchant.MerchantClient;
import org.ares.cloud.api.merchant.dto.PlatformApprovalRecordDto;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.file.domain.BasicFile;
import org.ares.cloud.file.enums.FileError;
import org.ares.cloud.file.enums.FileType;
import org.ares.cloud.file.service.FileService;
import org.ares.cloud.file.utils.FileValidationUtils;
import org.ares.cloud.s3.convert.ObjectStorageConvert;
import org.ares.cloud.s3.dto.ObjectStorageDto;
import org.ares.cloud.s3.entity.ObjectStorageEntity;
import org.ares.cloud.s3.query.ObjectStorageQuery;
import org.ares.cloud.s3.repository.ObjectStorageRepository;
import org.ares.cloud.s3.service.ObjectStorageService;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

/**
* @author hugo tangxkwork@163.com
* @description s3存储 服务实现
* @version 1.0.0
* @date 2024-10-12
*/
@Slf4j
@Service
@AllArgsConstructor
public class ObjectStorageServiceImpl extends BaseServiceImpl<ObjectStorageRepository, ObjectStorageEntity> implements ObjectStorageService, FileService {

    @Resource
    private ObjectStorageConvert convert;
    @Resource
    private MerchantClient merchantClient;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ObjectStorageDto dto) {
        ObjectStorageEntity entity = convert.toEntity(dto);
        this.baseMapper.insert(entity);
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<ObjectStorageDto> dos) {
        List<ObjectStorageEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities);
    }

    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(ObjectStorageDto dto) {
        ObjectStorageEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<ObjectStorageDto> dos) {
        List<ObjectStorageEntity> entities = convert.listToEntities(dos);
        this.saveOrUpdateBatch(entities);
    }

    /**
    * 根据id删除
    * @param id  主键
    */
    @Override
    public void deleteById(String id) {
        this.baseMapper.permanentlyDeleteById(id);
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
    public ObjectStorageDto loadById(String id) {
        ObjectStorageEntity entity = this.baseMapper.selectById(id);
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
    public List<ObjectStorageDto> loadByIds(List<String> ids) {
        List<ObjectStorageEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<ObjectStorageDto> loadAll() {
        LambdaQueryWrapper<ObjectStorageEntity> wrapper = new LambdaQueryWrapper<>();
        List<ObjectStorageEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<ObjectStorageDto> loadList(ObjectStorageQuery query) {
        IPage<ObjectStorageEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<ObjectStorageEntity> getWrapper(ObjectStorageQuery query){
        LambdaQueryWrapper<ObjectStorageEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())){
            wrapper.orderByDesc(ObjectStorageEntity::getId);
        }
        return wrapper;
    }

    @Override
    public BasicFile getFileInfo(String path) {
        ObjectStorageEntity storageEntity = this.baseMapper.findByName(path);
        if (storageEntity != null){
            BasicFile file = new BasicFile();
            file.setContainer(storageEntity.getContainer());
            file.setName(storageEntity.getName());
            file.setFileSize(storageEntity.getFileSize());
            file.setFileType(storageEntity.getFileType());
            file.setOriginalFileName(storageEntity.getOriginalFileName());
            return file;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveFile(BasicFile file) {
        try {
            // 1. 基础文件校验
            FileValidationUtils.ValidationResult validationResult = FileValidationUtils.validateFile(file);
            if (!validationResult.isSuccess()) {
                log.warn("文件校验失败: {}", validationResult.getMessage());
                if (validationResult.getError() != null) {
                    throw new RequestBadException(validationResult.getError());
                } else {
                    throw new BusinessException(validationResult.getMessage());
                }
            }

            // 2. 获取当前租户ID
            String tenantId = ApplicationContext.getTenantId();
            if (StringUtils.isBlank(tenantId)) {
                log.warn("租户ID为空，无法进行存储配额检查");
                throw new RequestBadException(FileError.TENANT_INFO_INCOMPLETE);
            }

            // 3. 检查存储配额
            checkStorageQuota(file, tenantId);

            // 4. 保存文件信息到数据库
            ObjectStorageEntity entity = new ObjectStorageEntity();
            entity.setContainer(file.getContainer());
            entity.setName(file.getName());
            entity.setOriginalFileName(file.getOriginalFileName());
            entity.setFileSize(file.getFileSize());
            entity.setFileType(file.getFileType());
            entity.setTenantId(tenantId);
            this.baseMapper.insert(entity);

            FileType fileType = FileType.fromMimeType(file.getFileType());

            // 5. 扣减存储单位（KB）
            deductStorageQuota(file, fileType);
            
            // 6. 记录文件类型信息

            log.info("文件保存成功，文件名: {}, 文件大小: {} bytes, 文件类型: {}", 
                file.getName(), file.getFileSize(), fileType.getDescription());
                
        } catch (BusinessException | RequestBadException e) {
            // 直接重新抛出已知的业务异常
            log.error("文件保存失败，业务异常: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("保存文件失败，文件名: {}, 错误信息: {}", file.getName(), e.getMessage(), e);
            throw new BusinessException("文件保存失败: " + e.getMessage());
        }
    }


    @Override
    public void deleteFile(String objectName) {
        ObjectStorageEntity storageEntity = this.baseMapper.findByName(objectName);
        if (storageEntity != null){
            this.deleteById(storageEntity.getId());
        }
    }
    /**
     * 检查存储配额是否足够
     * @param file 文件信息
     * @param tenantId 租户ID
     */
    private void checkStorageQuota(BasicFile file, String tenantId) {
        try {
            // 调用商户服务获取可用存储配额
            Long availableStorage = merchantClient.getAvailableStorage(tenantId);

            // 检查响应体是否为空
            if (availableStorage == null) {
                log.warn("存储配额响应为空，租户ID: {}", tenantId);
                throw new RequestBadException(FileError.STORAGE_QUOTA_RESPONSE_ERROR);
            }

            Long availableQuota = availableStorage;

            // 检查配额值是否合理
            if (availableQuota <  file.getFileSize()) {
                log.warn("存储配额值异常，租户ID: {}, 配额值: {}", tenantId, availableQuota);
                throw new RequestBadException(FileError.STORAGE_QUOTA_VALUE_INVALID);
            }

            // 校验存储配额是否足够
            FileValidationUtils.ValidationResult quotaResult = FileValidationUtils.validateStorageQuota(
                file.getFileSize(), availableQuota);

            if (!quotaResult.isSuccess()) {
                log.warn("存储配额不足，租户ID: {}, 错误信息: {}", tenantId, quotaResult.getMessage());
                if (quotaResult.getError() != null) {
                    throw new RequestBadException(quotaResult.getError());
                } else {
                    throw new BusinessException(quotaResult.getMessage());
                }
            }

            log.info("存储配额检查通过，租户ID: {}, 可用配额: {} KB, 文件大小: {} KB",
                tenantId, availableQuota, (file.getFileSize() + 1023) / 1024);

        } catch (Exception e) {
            log.error("检查存储配额失败，租户ID: {}, 错误信息: {}", tenantId, e.getMessage(), e);
            throw new RequestBadException(FileError.STORAGE_QUOTA_CHECK_FAILED);
        }
    }

    /**
     * 扣减存储配额
     * @param file 文件信息
     */
    private void deductStorageQuota(BasicFile file, FileType fileType) {
        try {
            // 获取当前租户ID
            String tenantId = ApplicationContext.getTenantId();
            if (StringUtils.isBlank(tenantId)) {
                log.warn("租户ID为空，跳过存储配额扣减");
                return;
            }

            // 将文件大小从字节转换为KB（向上取整）
            long fileSizeInKB = (file.getFileSize() + 1023) / 1024;

            // 创建存储变更记录
            PlatformApprovalRecordDto recordDto = new PlatformApprovalRecordDto();
            recordDto.setChangeMemory(fileSizeInKB);
            recordDto.setDataSource(fileType.getCode());
            recordDto.setDataType("file");
            recordDto.setTenantId(tenantId);
            recordDto.setDescription(String.format("文件上传扣减存储配额，文件名: %s, 大小: %d KB",
                    file.getOriginalFileName(), fileSizeInKB));

            // 调用商户服务扣减存储配额
            String result = merchantClient.updatePlatformApprovalRecord(recordDto);

            if ("success".equalsIgnoreCase(result)) {
                log.info("存储配额扣减成功，租户ID: {}, 扣减大小: {} KB", tenantId, fileSizeInKB);
            } else {
                log.warn("存储配额扣减失败，租户ID: {}, 结果: {}", tenantId, result);
                throw new BusinessException("存储配额扣减失败，结果: " + result);
            }

        } catch (BusinessException e) {
            log.error("扣减存储配额失败，业务异常: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("扣减存储配额失败，租户ID: {}, 错误信息: {}",
                    ApplicationContext.getTenantId(), e.getMessage(), e);
            throw new BusinessException("扣减存储配额失败: " + e.getMessage());
        }
    }
}