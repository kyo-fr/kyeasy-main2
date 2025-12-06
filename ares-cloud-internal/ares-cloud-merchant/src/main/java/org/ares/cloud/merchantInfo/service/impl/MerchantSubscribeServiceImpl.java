package org.ares.cloud.merchantInfo.service.impl;


import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import oracle.ons.rpc.RpcServerException;
import org.ares.cloud.api.base.BusinessIdServerClient;
import org.ares.cloud.api.user.errors.UserError;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.BaseException;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.model.Result;
import org.ares.cloud.exception.RpcCallException;
import org.ares.cloud.merchantInfo.convert.MerchantSubscribeConvert;
import org.ares.cloud.merchantInfo.dto.MerchantSubscribeDto;
import org.ares.cloud.merchantInfo.entity.MerchantSubscribeEntity;
import org.ares.cloud.merchantInfo.query.MerchantSubscribeQuery;
import org.ares.cloud.merchantInfo.repository.MerchantSubscribeRepository;
import org.ares.cloud.merchantInfo.service.MerchantSubscribeService;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.ares.cloud.platformInfo.dto.PlatformApprovalDto;
import org.ares.cloud.platformInfo.dto.PlatformServiceDto;
import org.ares.cloud.platformInfo.entity.PlatformTaxRateEntity;
import org.ares.cloud.platformInfo.repository.PlatformTaxRateRepository;
import org.ares.cloud.platformInfo.service.PlatformTaxRateService;
import org.ares.cloud.platformInfo.service.impl.PlatformApprovalServiceImpl;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author hugo tangxkwork@163.com
* @description 商户订阅 服务实现
* @version 1.0.0
* @date 2024-10-11
*/
@Service
@AllArgsConstructor
public class MerchantSubscribeServiceImpl extends BaseServiceImpl<MerchantSubscribeRepository, MerchantSubscribeEntity> implements MerchantSubscribeService{

    @Resource
    private MerchantSubscribeConvert convert;

    /**
    * 创建
    * @param dto 数据模型
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public  void create(MerchantSubscribeDto dto) {
//        MerchantSubscribeEntity entity = convert.toEntity(dto);
//        try {
//            //订阅id
//            ResponseEntity<String> merchantSubscribe = businessIdServerClient.generateBusinessId("merchant_subscribe_id");
//            //订阅合同id
//            ResponseEntity<String> merchantSubscribeInvoice = businessIdServerClient.generateBusinessId("merchant_subscribe_invoiceId_id");
//            if (merchantSubscribe.getStatusCode().equals(HttpStatus.OK) && merchantSubscribeInvoice.getStatusCode().equals(HttpStatus.OK)) {
//                ApplicationContext.setIgnoreTenant(true);
//                //订阅id
//                entity.setSubscribeId(merchantSubscribe.getBody());
//                entity.setInvoiceId(merchantSubscribeInvoice.getBody());
//                QueryWrapper<PlatformTaxRateEntity> queryWrapper = new QueryWrapper<>();
//                queryWrapper.eq("type", "4");
//                PlatformTaxRateEntity platformTaxRateEntity = platformTaxRateRepository.selectOne(queryWrapper);
//                if (platformTaxRateEntity != null) {
//                    //税率id
//                    entity.setTaxRateId(platformTaxRateEntity.getId());
//                }
//                //保存商户订阅信息
//                int insert = this.baseMapper.insert(entity);
//                //根据线上或者线下付款类型保存审批单
//                //线上、线下为未支付；线上单需要支付回调后更改审批单状态，线下需要手动核对后修改单状态
//                if (insert > 0) {
//                    //生成审批编号
//                    ResponseEntity<String> platformApproval = businessIdServerClient.generateBusinessId("platform_approval_id");
//                    if (platformApproval.getStatusCode().equals(HttpStatus.OK)  ) {
//                        PlatformApprovalDto approvalDto = new PlatformApprovalDto();
//                        approvalDto.setApprovalId(platformApproval.getBody());
//                        approvalDto.setStatus(1);
//                        //商户订阅id
//                        approvalDto.setMerchantSubscribeId(entity.getId());
//                        approvalDto.setTenantId(dto.getTenantId());
//                        approvalService.create(approvalDto);
//                    }
//                }
//            }
//        } catch (DataIntegrityViolationException e) {
//            log.error("e:{}", e);
//        }
    }

    /**
    * 批量创建
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<MerchantSubscribeDto> dos) {
        List<MerchantSubscribeEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities); 
    }
        
    /**
    * 更新
    * @param dto 数据模型
    */
    @Override
    public void update(MerchantSubscribeDto dto) {
        MerchantSubscribeEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
    * 批量更新
    * @param dos 数据模型集合
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<MerchantSubscribeDto> dos) {
        List<MerchantSubscribeEntity> entities = convert.listToEntities(dos);
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
    public MerchantSubscribeDto loadById(String id) {
        MerchantSubscribeEntity entity = this.baseMapper.selectById(id);
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
    public List<MerchantSubscribeDto> loadByIds(List<String> ids) {
        List<MerchantSubscribeEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
    * 加载所有数据
    * @return 数据集合
    */
    @Override
    public List<MerchantSubscribeDto> loadAll() {
        LambdaQueryWrapper<MerchantSubscribeEntity> wrapper = new LambdaQueryWrapper<>();
        List<MerchantSubscribeEntity> entities =  this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
    * 查询列表
    * @param query 查询对象
    * @return
    */
    @Override
    public PageResult<MerchantSubscribeDto> loadList(MerchantSubscribeQuery query) {
        IPage<MerchantSubscribeEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        return new PageResult<MerchantSubscribeDto>(convert.listToDto(page.getRecords()), page.getTotal());
    }

    /**
    * 获取条件
    * @param query
    * @return
    */
    private Wrapper<MerchantSubscribeEntity> getWrapper(MerchantSubscribeQuery query){
        LambdaQueryWrapper<MerchantSubscribeEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(query.getOrder())){
            wrapper.orderByDesc(MerchantSubscribeEntity::getId);
        }
        if (StringUtils.isNotBlank(query.getTenantId())){
            wrapper.eq(MerchantSubscribeEntity::getTenantId, query.getTenantId());
        }
        return wrapper;
    }
}