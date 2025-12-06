package org.ares.cloud.product.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ares.cloud.api.base.BusinessIdServerClient;
import org.ares.cloud.api.merchant.MerchantClient;
import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.api.merchant.dto.PlatformApprovalRecordDto;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.dto.PageResult;
import org.ares.cloud.common.exception.BaseException;
import org.ares.cloud.common.exception.BusinessException;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.product.convert.*;
import org.ares.cloud.product.dto.*;
import org.ares.cloud.product.entity.*;
import org.ares.cloud.product.enums.ProductError;
import org.ares.cloud.product.mapper.OrderMapper;
import org.ares.cloud.product.query.ProductAuctionQuery;
import org.ares.cloud.product.query.ProductBaseInfoQuery;
import org.ares.cloud.product.query.ProductPreferentialQuery;
import org.ares.cloud.product.query.ProductWholesaleQuery;
import org.ares.cloud.product.repository.*;
import org.ares.cloud.product.service.*;
import org.ares.cloud.product.vo.ProductBaseInfoVo;
import org.ares.cloud.product.vo.ProductListVo;
import org.ares.cloud.product.vo.ProductSubSpecificationVo;
import org.ares.cloud.redis.cache.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hugo tangxkwork@163.com
 * @version 1.0.0
 * @description 商品基础信息 服务实现
 * @date 2024-11-06
 */
@Service
@Slf4j
public class ProductBaseInfoServiceImpl extends BaseServiceImpl<ProductBaseInfoRepository, ProductBaseInfoEntity> implements ProductBaseInfoService {

    @Resource
    private ProductBaseInfoConvert convert;
    @Resource
    private ProductPreferentialConvert productPreferentialConvert;
    @Resource
    private ProductAuctionConvert productAuctionConvert;
    @Resource
    private ProductWholesaleConvert productWholesaleConvert;

    @Resource
    private ProductWholesaleService productWholesaleService;

    @Resource
    private ProductKeywordsConvert productKeywordsConvert;


    @Resource
    private ProductSpecificationConvert productSpecificationConvert;

    @Resource
    private ProductSubSpecificationConvert productSubSpecificationConvert;

    @Resource
    private ProductMarkingConvert productMarkingConvert;


    @Resource
    private ProductPreferentialService productPreferentialService;

    @Resource
    private ProductPreferentialRepository productPreferentialRepository;


    @Resource
    private ProductAuctionRepository productAuctionRepository;

    @Resource
    private ProductWholesaleRepository productWholesaleRepository;


    @Resource
    private ProductAuctionService productAuctionService;

    @Resource
    private ProductMarkingService productMarkingService;

    @Resource
    private ProductKeywordsService productKeywordsService;

    @Resource
    private ProductKeywordsRepository productKeywordsRepository;


    @Resource
    private ProductSpecificationService productSpecificationService;

    @Resource
    private ProductSubSpecificationService productSubSpecificationService;

    @Resource
    private ProductListService productListService;

    @Resource
    private ProductSpecificationRepository productSpecificationRepository;

    @Resource
    private ProductSubSpecificationRepository productSubSpecificationRepository;

    @Resource
    private ProductMarkingRepository productMarkingRepository;

    @Resource
    private ProductService productService;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private MerchantMarkingService merchantMarkingService;

    @Resource
    private MerchantKeyWordsService merchantKeyWordsService;


    @Resource
    private MerchantSpecificationService merchantSpecificationService;

    @Resource
    private MerchantSubSpecificationService merchantSubSpecificationService;

    @Resource
    private MerchantWarehouseService merchantWarehouseService;

    @Resource
    private MerchantWarehouseSeatService merchantWarehouseSeatService;

    @Resource
    private MerchantClient merchantClient;

    @Resource
    private ProductListRepository productListRepository;

    @Resource
    private ProductListConvert productListConvert;

    @Resource
    private RedisCache redisCache;

    @Resource
    private BusinessIdServerClient idServerClient;

    /**
     * 创建
     *
     * @param dto 数据模型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(ProductBaseInfoDto dto) {
        //根据商品名称和商户id查询该商品是否已存在
        ProductBaseInfoEntity productBaseInfo = this.baseMapper.selectOne(new QueryWrapper<ProductBaseInfoEntity>().eq("name", dto.getName()).eq("tenant_id", dto.getTenantId()));
        if (productBaseInfo != null) {
            //该租户下已存在该商品名称
            throw new RequestBadException(ProductError.PRODUCT_NAME_EXIST_ERROR);
        }
        //查询存储是否充足
        Long changeStorage = 10L;
        Long availableStorage = merchantClient.getAvailableStorage(dto.getTenantId());
        if (availableStorage == null) {
            throw new RequestBadException(ProductError.MERCHANT_STORAGE_QUOTA_RESPONSE_ERROR);
        }
        if (availableStorage < changeStorage) {
            //存储不足
            throw new RequestBadException(ProductError.MERCHANT_STORAGE_NOT_ENOUGH_ERROR);
        }
        ApplicationContext.setIgnoreTenant(true);
        //common-普通商品;preferential-优惠商品;auction-拍卖商品;wholesale-批发商品
        switch (dto.getType()) {
            case "preferential":
                if (dto.getPreferential() == null) {
                    throw new RequestBadException(ProductError.PRODUCT_PREFERENTIAL_IS_NULL_ERROR);
                }
                break;
            case "auction":
                if (dto.getAuction() == null) {
                    throw new RequestBadException(ProductError.PRODUCT_AUCTION_IS_NULL_ERROR);
                }
                break;
            case "wholesale":
                if (dto.getWholesale() == null) {
                    throw new RequestBadException(ProductError.PRODUCT_WHOLESALE_IS_NULL_ERROR);
                }
                break;
            default:
                break;
        }
        dto.setId(idServerClient.generateSnowflakeId());
        ProductBaseInfoEntity entity = convert.toEntity(dto);
        int insert = this.baseMapper.insert(entity);
        if (insert > 0) {
            String productId = entity.getId();
            switch (dto.getType()) {
                case "preferential":
                    ProductPreferentialDto productPreferential = dto.getPreferential();
                    productPreferential.setProductId(entity.getId());
                    productPreferential.setTenantId(dto.getTenantId());
                    productPreferentialService.create(productPreferential);
                    break;
                case "auction":
                    ProductAuctionDto productAuction = dto.getAuction();
                    productAuction.setProductId(entity.getId());
                    productAuction.setTenantId(dto.getTenantId());
                    productAuctionService.create(productAuction);
                    break;
                case "wholesale":
                    ProductWholesaleDto productWholesale = dto.getWholesale();
                    productWholesale.setProductId(entity.getId());
                    productWholesale.setTenantId(dto.getTenantId());
                    productWholesaleService.create(productWholesale);
                    break;
                default:
                    break;
            }
            // 保存标注
            if (dto.getProductKeyWordsList() != null) {
                for (ProductKeywordsDto productKeywordsDto : dto.getProductKeyWordsList()) {
                    productKeywordsDto.setProductId(productId);
                    productKeywordsDto.setKeyWordsId(productKeywordsDto.getKeyWordsId());
                    productKeywordsService.create(productKeywordsDto);
                }
            }
            //保存标注
            if (dto.getProductMarkingList() != null) {
                for (ProductMarkingDto productMarking : dto.getProductMarkingList()) {
                    productMarking.setProductId(productId);
                    productMarking.setMarkingId(productMarking.getMarkingId());
                    productMarkingService.create(productMarking);
                }
            }
            //保存主规格
            if (dto.getProductSpecificationList() != null) {
                for (ProductSpecificationDto productSpecification : dto.getProductSpecificationList()) {
                    productSpecification.setProductId(productId);
                    productSpecificationService.create(productSpecification);
                }
            }
            //保存子规格
            if (dto.getProductSubSpecificationList() != null) {
                for (ProductSubSpecificationDto productSubSpecification : dto.getProductSubSpecificationList()) {
                    ProductSubSpecificationDto productSubSpecificationEntity = new ProductSubSpecificationDto();
                    productSubSpecificationEntity.setProductId(productId);
                    productSubSpecificationEntity.setSubSpecificationId(productSubSpecification.getSubSpecificationId());
                    productSubSpecificationEntity.setSpecificationId(productSubSpecification.getSpecificationId());
                    productSubSpecificationService.create(productSubSpecificationEntity);
                }
            }
            //保存商品清单
            if (dto.getProductListDtoList() != null) {
                for (ProductListDto productListDto : dto.getProductListDtoList()) {
                    ProductListDto productListDto1 = new ProductListDto();
                    productListDto1.setProductId(productId);
                    productListDto1.setSubProductId(productListDto.getSubProductId());
                    productListService.create(productListDto1);
                }
            }
            //调用存储变更
            PlatformApprovalRecordDto platformApprovalRecordDto = new PlatformApprovalRecordDto();
            platformApprovalRecordDto.setDataSource("product");
            platformApprovalRecordDto.setDataType("data");
            platformApprovalRecordDto.setTenantId(dto.getTenantId());
            platformApprovalRecordDto.setDescription("创建商品");
            //固定占用存储10kb
            platformApprovalRecordDto.setChangeMemory(changeStorage);
            try {
                merchantClient.updatePlatformApprovalRecord(platformApprovalRecordDto);
            } catch (Exception e) {
                log.error("调用存储变更失败:{}", e.getMessage());
            }
        }
    }

    /**
     * 批量创建
     *
     * @param dos 数据模型集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<ProductBaseInfoDto> dos) {
        List<ProductBaseInfoEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities);
    }

    /**
     * 更新
     *
     * @param dto 数据模型
     */
    @Override
    public void update(ProductBaseInfoDto dto) {
        //根据商品名称和商户id查询该商品是否已存在
        ProductBaseInfoEntity productBaseInfo = this.baseMapper.selectOne(new QueryWrapper<ProductBaseInfoEntity>().eq("name", dto.getName()).eq("tenant_id", dto.getTenantId()));
        if (productBaseInfo != null && !productBaseInfo.getId().equals(dto.getId())) {
            //该租户下已存在该商品名称
            throw new RequestBadException(ProductError.PRODUCT_NAME_EXIST_ERROR);
        }
        //商品是否下架
        ProductBaseInfoEntity productBaseInfoEntity = this.baseMapper.selectOne(new QueryWrapper<ProductBaseInfoEntity>().eq("id", dto.getId()));
        if (productBaseInfoEntity.getIsEnable().equals("enable")) {
            //商品暂未下架 不能操作编辑
            throw new RequestBadException(ProductError.PRODUCT_IS_ENABLE_ERROR);
        }
        ProductBaseInfoEntity entity = convert.toEntity(dto);
        String type = dto.getType();
        switch (type) {
            case "preferential":
                if (dto.getPreferential() == null) {
                    throw new BusinessException("优惠信息不能为空");
                }
                //根据商品id查询优惠商品信息
                QueryWrapper<ProductPreferentialEntity> PreferentialWrapper = new QueryWrapper<>();
                PreferentialWrapper.eq("product_id", entity.getId());
                ProductPreferentialEntity productPreferentialEntity = productPreferentialRepository.selectOne(PreferentialWrapper);
                if (productPreferentialEntity != null) {
                    ProductPreferentialDto productPreferentialDto = productPreferentialConvert.toDto(productPreferentialEntity);
                    ProductPreferentialDto preferential = dto.getPreferential();
                    preferential.setId(productPreferentialDto.getId());
                    preferential.setProductId(entity.getId());
                    productPreferentialService.update(preferential);
                }
                break;
            case "auction":
                if (dto.getAuction() == null) {
                    throw new BusinessException("拍卖信息不能为空");
                }
                //根据商品id查询拍卖商品信息
                QueryWrapper<ProductAuctionEntity> auctionWrapper = new QueryWrapper<>();
                auctionWrapper.eq("product_id", entity.getId());
                ProductAuctionEntity productAuctionEntity = productAuctionRepository.selectOne(auctionWrapper);
                if (productAuctionEntity != null) {
                    ProductAuctionDto auction = dto.getAuction();
                    auction.setId(productAuctionEntity.getId());
                    auction.setProductId(entity.getId());
                    productAuctionService.update(auction);
                }
                break;
            case "wholesale":
                if (dto.getWholesale() == null) {
                    throw new BusinessException("批发信息不能为空");
                }
                //根据商品id查询拍卖商品信息
                QueryWrapper<ProductWholesaleEntity> wholesaleWrapper = new QueryWrapper<>();
                wholesaleWrapper.eq("product_id", entity.getId());
                ProductWholesaleEntity productWholesaleEntity = productWholesaleRepository.selectOne(wholesaleWrapper);
                if (productWholesaleEntity != null) {
                    ProductWholesaleDto wholesale = dto.getWholesale();
                    wholesale.setId(productWholesaleEntity.getId());
                    wholesale.setProductId(entity.getId());
                    productWholesaleService.update(wholesale);
                }
                break;
            default:
                break;
        }
        //先删除原来标注、关键字再保存新的
        if (dto.getProductMarkingList() != null && dto.getProductMarkingList().size() > 0) {
            //先根据商品id查询出来对应的多条标注信息
            productMarkingRepository.delete(new QueryWrapper<ProductMarkingEntity>().eq("product_id", entity.getId()));
            //在批量更新标注信息
            for (ProductMarkingDto productMarking : dto.getProductMarkingList()) {
                productMarking.setProductId(entity.getId());
                productMarkingService.create(productMarking);
            }
        }
        if (dto.getProductKeyWordsList() != null && dto.getProductKeyWordsList().size() > 0) {
            productKeywordsRepository.delete(new QueryWrapper<ProductKeywordsEntity>().eq("product_id", entity.getId()));
            for (ProductKeywordsDto productKeywords : dto.getProductKeyWordsList()) {
                productKeywords.setProductId(entity.getId());
                productKeywordsService.create(productKeywords);
            }
        }
        //更新商品主规格
        if (dto.getProductSpecificationList() != null && dto.getProductSpecificationList().size() > 0) {
            productSpecificationRepository.delete(new QueryWrapper<ProductSpecificationEntity>().eq("product_id", entity.getId()));
            for (ProductSpecificationDto productSpecificationDto : dto.getProductSpecificationList()) {
                productSpecificationDto.setProductId(entity.getId());
                productSpecificationService.create(productSpecificationDto);
            }
        }
        //更新商品子规格
        if (dto.getProductSubSpecificationList() != null && dto.getProductSubSpecificationList().size() > 0) {
            productSubSpecificationRepository.delete(new QueryWrapper<ProductSubSpecificationEntity>().eq("product_id", entity.getId()));
            for (ProductSubSpecificationDto productSubSpecification : dto.getProductSubSpecificationList()) {
                productSubSpecification.setProductId(entity.getId());
                productSubSpecification.setSubSpecificationId(productSubSpecification.getSubSpecificationId());
                productSubSpecificationService.create(productSubSpecification);
            }
        }
        //更新商品清单
        if (dto.getProductListDtoList() != null) {
            productListRepository.delete(new QueryWrapper<ProductListEntity>().eq("product_id", entity.getId()));
            for (ProductListDto productList : dto.getProductListDtoList()) {
                productList.setProductId(dto.getId());
                productList.setSubProductId(productList.getSubProductId());
                productListService.create(productList);
            }
        }
        this.productService.update(entity);

    }

    /**
     * 批量更新
     *
     * @param dos 数据模型集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<ProductBaseInfoDto> dos) {
        List<ProductBaseInfoEntity> entities = convert.listToEntities(dos);
        this.saveOrUpdateBatch(entities);
    }

    /**
     * 根据id删除
     *
     * @param id 主键
     */
    @Override
    public void deleteById(String id) {
        this.baseMapper.deleteById(id);
        //TODO 删除其他信息
    }

    /**
     * 根据ids删除
     *
     * @param ids 主键集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByIds(List<String> ids) {
        this.baseMapper.deleteBatchIds(ids);
    }

    /**
     * 根据id获取详情
     *
     * @param id 主键
     */
    @Override
    public ProductBaseInfoVo loadById(String id) {
        ProductBaseInfoEntity entity = this.productService.getById(id);
        if (entity != null) {
            ProductBaseInfoDto dto = convert.toDto(entity);
            ProductBaseInfoVo productBaseInfoVo = new ProductBaseInfoVo();
            BeanUtils.copyProperties(dto, productBaseInfoVo);
            switch (dto.getType()) {
                case "preferential":
                    QueryWrapper<ProductPreferentialEntity> objectQueryWrapper = new QueryWrapper<>();
                    objectQueryWrapper.eq("product_id", id);
                    ProductPreferentialEntity productPreferentialEntity = productPreferentialRepository.selectOne(objectQueryWrapper);
                    if (productPreferentialEntity != null) {
                        ProductPreferentialDto productPreferential = productPreferentialConvert.toDto(productPreferentialEntity);
                        productBaseInfoVo.setPreferential(productPreferential);
                    }
                    break;
                case "auction":
                    QueryWrapper<ProductAuctionEntity> wrapper = new QueryWrapper<>();
                    wrapper.eq("product_id", id);
                    ProductAuctionEntity productAuctionEntity = productAuctionRepository.selectOne(wrapper);
                    if (productAuctionEntity != null) {
                        ProductAuctionDto productAuction = productAuctionConvert.toDto(productAuctionEntity);
                        productBaseInfoVo.setAuction(productAuction);
                    }
                    break;
                case "wholesale":
                    QueryWrapper<ProductWholesaleEntity> wholesaleWrapper = new QueryWrapper<>();
                    wholesaleWrapper.eq("product_id", id);
                    ProductWholesaleEntity productWholesaleEntity = productWholesaleRepository.selectOne(wholesaleWrapper);
                    if (productWholesaleEntity != null) {
                        ProductWholesaleDto productWholesale = productWholesaleConvert.toDto(productWholesaleEntity);
                        productBaseInfoVo.setWholesale(productWholesale);
                    }
                    break;
                default:
                    break;
            }
            //根据商品id加载主规格
            QueryWrapper<ProductSpecificationEntity> specificationWrapper = new QueryWrapper<>();
            specificationWrapper.eq("product_id", id);
            List<ProductSpecificationEntity> productSpecificationList = productSpecificationRepository.selectList(specificationWrapper);
            if (productSpecificationList != null) {
                List<ProductSpecificationDto> productSpecificationDtoList = productSpecificationConvert.listToDto(productSpecificationList);
                ArrayList<ProductSpecificationDto> updateProductSpecificationDtoList = new ArrayList<>();
                for (ProductSpecificationDto productSpecificationDto : productSpecificationDtoList) {
                    MerchantSpecificationDto merchantSpecificationDto = merchantSpecificationService.loadById(productSpecificationDto.getSpecificationId());
                    if (merchantSpecificationDto != null) {
                        productSpecificationDto.setSpecificationName(merchantSpecificationDto.getName());
                        productSpecificationDto.setSpecificationId(merchantSpecificationDto.getId());
                        productSpecificationDto.setId(productSpecificationDto.getId());
                        productSpecificationDto.setType(merchantSpecificationDto.getType());
                        updateProductSpecificationDtoList.add(productSpecificationDto);
                    }
                }
                productBaseInfoVo.setProductSpecificationList(updateProductSpecificationDtoList);
            }


            //根据商品id加载子规格
            QueryWrapper<ProductSubSpecificationEntity> subSpecificationWrapper = new QueryWrapper<>();
            subSpecificationWrapper.eq("product_id", id);
            List<ProductSubSpecificationEntity> productSubSpecificationList = productSubSpecificationRepository.selectList(subSpecificationWrapper);
            if (productSubSpecificationList != null) {
                List<ProductSubSpecificationVo> updateProductSubSpecificationDtoList = new ArrayList<>();
                List<ProductSubSpecificationDto> productSubSpecificationDtoList = productSubSpecificationConvert.listToDto(productSubSpecificationList);
                for (ProductSubSpecificationDto productSubSpecificationDto : productSubSpecificationDtoList) {
                    MerchantSubSpecificationDto merchantSubSpecificationDto = merchantSubSpecificationService.loadById(productSubSpecificationDto.getSubSpecificationId());
                    if (merchantSubSpecificationDto != null) {
                        ProductSubSpecificationVo productSubSpecificationVo = new ProductSubSpecificationVo();
                        productSubSpecificationVo.setSubSpecificationName(merchantSubSpecificationDto.getSubName());
                        productSubSpecificationVo.setSpecificationId(merchantSubSpecificationDto.getSpecificationId());
                        productSubSpecificationVo.setSubSpecificationId(merchantSubSpecificationDto.getId());
                        productSubSpecificationVo.setId(productSubSpecificationDto.getId());
                        productSubSpecificationVo.setSubNum(merchantSubSpecificationDto.getSubNum());
                        productSubSpecificationVo.setSubPrice(merchantSubSpecificationDto.getSubPrice());
                        productSubSpecificationVo.setSubPicture(merchantSubSpecificationDto.getSubPicture());
                        updateProductSubSpecificationDtoList.add(productSubSpecificationVo);
                    }
                }
                productBaseInfoVo.setProductSubSpecificationList(updateProductSubSpecificationDtoList);
            }

            //根据商品id加载商品标注
            QueryWrapper<ProductMarkingEntity> markingWrapper = new QueryWrapper<>();
            markingWrapper.eq("product_id", id);
            List<ProductMarkingEntity> productMarkingList = productMarkingRepository.selectList(markingWrapper);
            if (productMarkingList != null) {
                List<ProductMarkingDto> productMarkingDtoList = productMarkingConvert.listToDto(productMarkingList);
                ArrayList<ProductMarkingDto> updatedProductMarkingDtoList = new ArrayList<>();
                for (ProductMarkingDto productMarkingDto : productMarkingDtoList) {
                    MerchantMarkingDto merchantMarkingDto = merchantMarkingService.loadById(productMarkingDto.getMarkingId());
                    if (merchantMarkingDto != null) {
                        productMarkingDto.setMarkingName(merchantMarkingDto.getName());
                        productMarkingDto.setMarkingId(merchantMarkingDto.getId());
                        updatedProductMarkingDtoList.add(productMarkingDto);
                    }
                }
                productBaseInfoVo.setProductMarkingList(updatedProductMarkingDtoList);
            }

            //根据商品id加载关键字
            QueryWrapper<ProductKeywordsEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("product_id", id);
            List<ProductKeywordsEntity> keywordsList = productKeywordsRepository.selectList(wrapper);
            if (keywordsList != null) {
                List<ProductKeywordsDto> keywordsDtoList = productKeywordsConvert.listToDto(keywordsList);
                ArrayList<ProductKeywordsDto> updateProductKeywordsDtoList = new ArrayList<>();
                for (ProductKeywordsDto productKeywordsDto : keywordsDtoList) {
                    MerchantKeyWordsDto merchantKeyWordsDto = merchantKeyWordsService.loadById(productKeywordsDto.getKeyWordsId());
                    if (merchantKeyWordsDto != null) {
                        productKeywordsDto.setKeyWordsName(merchantKeyWordsDto.getKeyName());
                        productKeywordsDto.setKeyWordsId(merchantKeyWordsDto.getId());
                        updateProductKeywordsDtoList.add(productKeywordsDto);
                    }
                }
                productBaseInfoVo.setProductKeyWordsList(updateProductKeywordsDtoList);
            }
            //根据商品id加载仓库
            if (dto.getWarehouseId() != null) {
                MerchantWarehouseDto merchantWarehouseDto = merchantWarehouseService.loadById(dto.getWarehouseId());
                if (merchantWarehouseDto != null) {
                    productBaseInfoVo.setWarehouseId(merchantWarehouseDto.getId());
                    productBaseInfoVo.setWarehouseName(merchantWarehouseDto.getName());
                }
            }
            //根据商品id加载位子
            if (dto.getWarehouseSeatId() != null) {
                MerchantWarehouseSeatDto merchantWarehouseSeatDto = merchantWarehouseSeatService.loadById(dto.getWarehouseSeatId());
                if (merchantWarehouseSeatDto != null) {
                    productBaseInfoVo.setWarehouseSeatId(merchantWarehouseSeatDto.getId());
                    productBaseInfoVo.setWarehouseSeatName(merchantWarehouseSeatDto.getSeatName());
                }
            }

            //查询商品id查询商品清单
            QueryWrapper<ProductListEntity> listWrapper = new QueryWrapper<>();
            listWrapper.eq("product_id", id);
            List<ProductListEntity> productListEntityList = productListRepository.selectList(listWrapper);
            if (productListEntityList != null) {
                List<ProductListDto> productListDtoList = productListConvert.listToDto(productListEntityList);
                ArrayList<ProductListVo> updateProductListVo = new ArrayList<>();
                for (ProductListDto productListDto : productListDtoList) {
                    //根据商品清单id查询商品名称
                    ProductBaseInfoEntity productBaseInfo = productService.getById(productListDto.getSubProductId());
                    if (productBaseInfo != null) {
                        ProductListVo productListVo = new ProductListVo();
                        productListVo.setSubProductName(productBaseInfo.getName());
                        productListVo.setSubProductId(productListDto.getSubProductId());
                        productListVo.setPictureUrl(productBaseInfo.getPictureUrl());
                        productListVo.setPrice(productBaseInfo.getPrice());
                        updateProductListVo.add(productListVo);
                    }
                }
                productBaseInfoVo.setProductListVoList(updateProductListVo);
            }
            return productBaseInfoVo;
        }
        return null;
    }

    /**
     * 根据id获取详情
     *
     * @param ids 主键
     */
    @Override
    public List<ProductBaseInfoDto> loadByIds(List<String> ids) {
        List<ProductBaseInfoEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
     * 加载所有数据
     *
     * @return 数据集合
     */
    @Override
    public List<ProductBaseInfoDto> loadAll() {
        LambdaQueryWrapper<ProductBaseInfoEntity> wrapper = new LambdaQueryWrapper<>();
        List<ProductBaseInfoEntity> entities = this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @return
     */
    @Override
    public PageResult<ProductDto> loadList(ProductBaseInfoQuery query, String domainName) {
        getMerchantInfo(query, domainName);
        log.info("loadList...getProductInfoByTypeId:{}", JSONObject.toJSONString(query));
        int size = 0;
        if (query.getLevels() != null) {
            //如果分类等级不为空则需要传分类id主键
            if (query.getTypeId() == null) {
                throw new BaseException(ProductError.PRODUCT_TYPE_ID_NOT_BLANK_ERROR);
            }
        }
        IPage<ProductBaseInfoEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        PageResult<ProductBaseInfoDto> productBaseInfoDtoPageResult = new PageResult<>(convert.listToDto(page.getRecords()), page.getTotal());
        String type = query.getType();
        // 获取基础产品信息
        // 合并结果
        List<ProductDto> combinedList = new ArrayList<>();
        if ("common".equals(type)) {//1-基础商品
            size = productBaseInfoDtoPageResult.getList().stream().filter(x -> x.getType().equals("common")).toList().size();
            for (int i = 0; i < size; i++) {
                ProductBaseInfoDto baseInfo = productBaseInfoDtoPageResult.getList().get(i);
                // 创建一个新的 DTO 用于合并产品和批发信息
                ProductDto combinedDto = new ProductDto();
                // 填充合并后的字段
                BeanUtils.copyProperties(baseInfo, combinedDto);
                combinedDto.setProductName(baseInfo.getName()); // 商品名称
                this.getMerchantMainDataInfo(baseInfo, combinedDto);//仓库名称/仓库位子/税率字段处理
                // 将合并后的 DTO 添加到合并列表中
                combinedList.add(combinedDto);
            }
        } else if ("preferential".equals(type)) {// 2-优惠商品
            ProductPreferentialQuery query1 = new ProductPreferentialQuery();
            query1.setPage(query.getPage());
            query1.setLimit(query.getLimit());
            query1.setAsc(query.isAsc());
            query1.setOrder(query.getOrder());
            PageResult<ProductPreferentialDto> result = productPreferentialService.loadList(query1);
            // 取两个分页结果中记录数最小的作为合并的数量
            size = Math.min(productBaseInfoDtoPageResult.getList().size(),
                    result.getList().size());
            // 合并数据
            for (int i = 0; i < size; i++) {
                ProductBaseInfoDto baseInfo = productBaseInfoDtoPageResult.getList().get(i);
                // 获取优惠商品信息
                ProductPreferentialDto productPreferentialDto = result.getList().get(i);
                // 创建一个新的 DTO 用于合并产品和批发信息
                ProductDto combinedDto = new ProductDto();
                // 填充合并后的字段
                BeanUtils.copyProperties(baseInfo, combinedDto);
                combinedDto.setProductName(baseInfo.getName()); // 商品名称
                combinedDto.setProductId(baseInfo.getId());     // 商品ID
                combinedDto.setStartTime(productPreferentialDto.getStartTime());
                combinedDto.setEndTime(productPreferentialDto.getEndTime());
                combinedDto.setPricePre(productPreferentialDto.getPreferentialPrice());
                this.getMerchantMainDataInfo(baseInfo, combinedDto);//仓库名称/仓库位子/税率字段处理
                // 将合并后的 DTO 添加到合并列表中
                combinedList.add(combinedDto);
            }
        } else if ("auction".equals(type)) {// 4-拍卖商品
            ProductAuctionQuery query1 = new ProductAuctionQuery();
            query1.setPage(query.getPage());
            query1.setLimit(query.getLimit());
            query1.setAsc(query.isAsc());
            query1.setOrder(query.getOrder());
            PageResult<ProductAuctionDto> result = productAuctionService.loadList(query1);
            // 取两个分页结果中记录数最小的作为合并的数量
            size = Math.min(productBaseInfoDtoPageResult.getList().size(),
                    result.getList().size());
            // 合并数据
            for (int i = 0; i < size; i++) {
                ProductBaseInfoDto baseInfo = productBaseInfoDtoPageResult.getList().get(i);
                // 获取优惠商品信息
                ProductAuctionDto productAuctionDto = result.getList().get(i);
                // 创建一个新的 DTO 用于合并产品和批发信息
                ProductDto combinedDto = new ProductDto();
                // 填充合并后的字段
                BeanUtils.copyProperties(baseInfo, combinedDto);
                combinedDto.setProductName(baseInfo.getName()); // 商品名称
                combinedDto.setProductId(baseInfo.getId());     // 商品ID
                combinedDto.setStartTime(productAuctionDto.getStartTime());
                combinedDto.setEndTime(productAuctionDto.getEndTime());
                combinedDto.setFixedPrice(productAuctionDto.getFixedPrice());
                this.getMerchantMainDataInfo(baseInfo, combinedDto);//仓库名称/仓库位子/税率字段处理
                // 将合并后的 DTO 添加到合并列表中
                combinedList.add(combinedDto);
            }
        } else if ("wholesale".equals(type)) {// 4-批发商品
            //批发商品查询
            ProductWholesaleQuery query1 = new ProductWholesaleQuery();
            query1.setPage(query.getPage());
            query1.setLimit(query.getLimit());
            query1.setAsc(query.isAsc());
            query1.setOrder(query.getOrder());
            PageResult<ProductWholesaleDto> result = productWholesaleService.loadList(query1);
            // 取两个分页结果中记录数最小的作为合并的数量
            size = Math.min(productBaseInfoDtoPageResult.getList().size(),
                    result.getList().size());
            // 合并数据
            for (int i = 0; i < size; i++) {
                ProductBaseInfoDto baseInfo = productBaseInfoDtoPageResult.getList().get(i);
                // 获取批发信息
                ProductWholesaleDto wholesaleInfo = result.getList().get(i);
                // 创建一个新的 DTO 用于合并产品和批发信息
                ProductDto combinedDto = new ProductDto();
                // 填充合并后的字段
                BeanUtils.copyProperties(baseInfo, combinedDto);
                combinedDto.setProductName(baseInfo.getName()); // 商品名称
                combinedDto.setProductId(baseInfo.getId());     // 商品ID
                combinedDto.setPriceOne(wholesaleInfo.getPriceOne());// 单价1
                combinedDto.setPriceTwo(wholesaleInfo.getPriceTwo());// 单价2
                combinedDto.setPriceThree(wholesaleInfo.getPriceThree()); // 单价3
                combinedDto.setNumOne(wholesaleInfo.getNumOne());
                combinedDto.setNumTwo(wholesaleInfo.getNumTwo());
                combinedDto.setNumThree(wholesaleInfo.getNumThree());
                //处理仓库名称字段
                MerchantWarehouseDto merchantWarehouseDto = merchantWarehouseService.loadById(baseInfo.getWarehouseId());
                if (merchantWarehouseDto != null) {
                    combinedDto.setWarehouseName(merchantWarehouseDto.getName());
                }
                //处理仓库位子字段
                MerchantWarehouseSeatDto merchantWarehouseSeatDto = merchantWarehouseSeatService.loadById(baseInfo.getWarehouseSeatId());
                if (merchantWarehouseSeatDto != null) {
                    combinedDto.setWarehouseSeatName(merchantWarehouseSeatDto.getSeatName());
                }
                this.getMerchantMainDataInfo(baseInfo, combinedDto);
                // 将合并后的 DTO 添加到合并列表中
                combinedList.add(combinedDto);
            }
        } else {
            // 按照指定顺序排序：1.拍卖商品 → 2.优惠商品 → 3.批发商品 → 4.普通商品
            // 并且传入分页参数
            List<ProductBaseInfoDto> products = productBaseInfoDtoPageResult.getList();
            if (products != null) {
                for (int i = 0; i < productBaseInfoDtoPageResult.getList().size(); i++) {
                    ProductBaseInfoDto baseInfo = products.get(i);
                    // 创建一个新的 DTO 用于合并产品和批发信息
                    ProductDto combinedDto = new ProductDto();
                    // 填充合并后的字段
                    BeanUtils.copyProperties(baseInfo, combinedDto);
                    combinedDto.setProductName(baseInfo.getName()); // 商品名称
                    combinedDto.setProductId(baseInfo.getId());     // 商品ID
                    if ("auction".equals(baseInfo.getType())) {
                        ProductAuctionEntity productAuctionEntity = productAuctionRepository.selectOne(new LambdaQueryWrapper<ProductAuctionEntity>().eq(ProductAuctionEntity::getProductId, baseInfo.getId()));
                        if (productAuctionEntity != null) {
                            combinedDto.setStartTime(productAuctionEntity.getStartTime());
                            combinedDto.setEndTime(productAuctionEntity.getEndTime());
                            combinedDto.setFixedPrice(productAuctionEntity.getFixedPrice());
                        }
                    }
                    if ("preferential".equals(baseInfo.getType())) {
                        ProductPreferentialEntity productPreferentialEntity = productPreferentialRepository.selectOne(new LambdaQueryWrapper<ProductPreferentialEntity>().eq(ProductPreferentialEntity::getProductId, baseInfo.getId()));
                        if (productPreferentialEntity != null) {
                            combinedDto.setStartTime(productPreferentialEntity.getStartTime());
                            combinedDto.setEndTime(productPreferentialEntity.getEndTime());
                            BigDecimal preferentialPrice = productPreferentialEntity.getPreferentialPrice();
                            combinedDto.setPricePre(preferentialPrice);
                        }
                    }
                    if ("wholesale".equals(baseInfo.getType())) {
                        ProductWholesaleEntity productWholesaleEntity = productWholesaleRepository.selectOne(new LambdaQueryWrapper<ProductWholesaleEntity>().eq(ProductWholesaleEntity::getProductId, baseInfo.getId()));
                        if (productWholesaleEntity != null) {
                            combinedDto.setNumOne(productWholesaleEntity.getNumOne());
                            combinedDto.setNumTwo(productWholesaleEntity.getNumTwo());
                            combinedDto.setNumThree(productWholesaleEntity.getNumThree());
                            combinedDto.setPriceOne(productWholesaleEntity.getPriceOne());
                            combinedDto.setPriceTwo(productWholesaleEntity.getPriceTwo());
                            combinedDto.setPriceThree(productWholesaleEntity.getPriceThree());
                        }
                    }
                    this.getMerchantMainDataInfo(baseInfo, combinedDto);//仓库名称/仓库位子/税率字段处理
                    // 将合并后的 DTO 添加到合并列表中
                    combinedList.add(combinedDto);
                }
            }
        }
        // 检查 combinedList 是否为空
        if (combinedList.isEmpty()) {
            combinedList = convertToProductDtoList(productBaseInfoDtoPageResult.getList());
        }
        // 返回合并后的分页结果
        return new PageResult<>(combinedList, page.getTotal());
    }


    //商品关联处理商户主数据
    private void getMerchantMainDataInfo(ProductBaseInfoDto baseInfo, ProductDto combinedDto) {
        //处理仓库名称字段
        if (baseInfo.getWarehouseId() != null) {
            MerchantWarehouseDto merchantWarehouseDto = merchantWarehouseService.loadById(baseInfo.getWarehouseId());
            if (merchantWarehouseDto != null) {
                combinedDto.setWarehouseName(merchantWarehouseDto.getName());
            }
        }
        if (baseInfo.getWarehouseSeatId() != null) {
            //处理仓库位子字段
            MerchantWarehouseSeatDto merchantWarehouseSeatDto = merchantWarehouseSeatService.loadById(baseInfo.getWarehouseSeatId());
            if (merchantWarehouseSeatDto != null) {
                combinedDto.setWarehouseSeatName(merchantWarehouseSeatDto.getSeatName());
            }
        }
//        if (baseInfo.getTaxId() != null) {
//            //查询税率
//            BigDecimal taxRateByTaxId = merchantTaxMapper.getTaxRateByTaxId(baseInfo.getTaxId());
//            if (taxRateByTaxId != null) {
//                combinedDto.setTaxRate(taxRateByTaxId);
//            }
//        }
    }

    @Override
    public void updateProductByStatus(String isEnable, String productId) {
        if ("not_enable".equals(isEnable)) {
            //根据商品id查询订单是否全部是完成或者取消状态
            int orderByProductId = orderMapper.getOrderByProductId(productId);
            if (orderByProductId > 0) {
                throw new RequestBadException(ProductError.PRODUCT_HAS_ORDER_EXIST_ERROR);
            }
        }
        ProductBaseInfoEntity byId = productService.getById(productId);
        byId.setIsEnable(isEnable);
        productService.update(byId);
    }

    @Override
    public boolean checkProductIsDelete(String id, String type) {
        String productId = null;
        switch (type) {
            case "marking":
                ProductMarkingDto productMarkingDto = productMarkingService.loadById(id);
                if (productMarkingDto != null) {
                    productId = productMarkingDto.getProductId();
                }

            case "keywords":
                ProductKeywordsDto productKeywordsDto = productKeywordsService.loadById(id);
                if (productKeywordsDto != null) {
                    productId = productKeywordsDto.getProductId();
                }

            case "specification":
                ProductSpecificationDto productSpecificationDto = productSpecificationService.loadById(id);
                if (productSpecificationDto != null) {
                    productId = productSpecificationDto.getProductId();
                }
        }
        if (productId != null) {
            QueryWrapper<ProductBaseInfoEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("id", productId);
            wrapper.eq("is_enable", "enable");
            ProductBaseInfoEntity productBaseInfoEntity = baseMapper.selectOne(wrapper);
            if (productBaseInfoEntity != null) {
                return true;
            }
        }
        return false;
    }


    @Override
    public PageResult<ProductBaseInfoDto> getProductInfoByTypeId(ProductBaseInfoQuery query, String domainName) {
        //根据域名查询对应商户id
        getMerchantInfo(query, domainName);
        log.info("getProductInfoByTypeId...getProductInfoByTypeId:{}", JSONObject.toJSONString(query));
        IPage<ProductBaseInfoEntity> page = baseMapper.selectPage(getPage(query), getWrapperByTypeId(query));
        PageResult<ProductBaseInfoDto> productBaseInfoDtoPageResult = new PageResult<>(convert.listToDto(page.getRecords()), page.getTotal());
        return productBaseInfoDtoPageResult;
    }

    @Override
    public void updateProductIsEnable(String productId) {
        //根据商品id查询商品详情
        ProductBaseInfoEntity entity = baseMapper.selectById(productId);
        if (entity != null) {
            if (entity.getInventory() == 0) {
                try {
                    updateProductByStatus("not_enable", productId);
                } catch (RequestBadException e) {
                    // 重新抛出原始异常
                    throw e;
                } catch (Exception e) {
                    // 处理其他未预期的异常
                    log.error("更新商品状态时发生未预期的错误", e);
                    throw new RequestBadException(ProductError.PRODUCT_HAS_ORDER_EXIST_ERROR);
                }
            } else {
                log.info("商品库存不为0，无需自动下架，当前库存: {}", entity.getInventory());
            }
        }
    }


    private void getMerchantInfo(ProductBaseInfoQuery query, String domainName) {
        if (query.getTenantId() == null || StringUtils.isBlank(query.getTenantId())) {
            //查询redis
            Object domainNameValue = redisCache.get(domainName);
            if (domainNameValue != null) {
                log.info("getMerchantInfo...redis中查询商户信息domainNameValue：{}", domainNameValue);
                query.setTenantId(domainNameValue.toString());
            } else {
                MerchantInfo merchantInfo = merchantClient.getMerchantInfoByDomain(domainName);
                log.info("getMerchantInfo...商户信息查询结果：{}", merchantInfo);
                if (merchantInfo != null) {
                    query.setTenantId(merchantInfo.getId());
                    redisCache.set(domainName, merchantInfo.getId(), RedisCache.DEFAULT_EXPIRE);
                }
            }
        } else {
            query.setTenantId(query.getTenantId().trim());
        }
    }

    /**
     * 获取条件
     *
     * @param query
     * @return
     */
    private Wrapper<ProductBaseInfoEntity> getWrapper(ProductBaseInfoQuery query) {
        LambdaQueryWrapper<ProductBaseInfoEntity> wrapper = super.getWrapper(query);
        if (StringUtils.isNotEmpty(query.getOrder())) {
            if (query.isAsc()) {
                wrapper.orderByDesc(ProductBaseInfoEntity::getPrice);
            } else {
                wrapper.orderByAsc(ProductBaseInfoEntity::getPrice);
            }
        }
        if (StringUtils.isNotEmpty(query.getTenantId())) {
            wrapper.eq(ProductBaseInfoEntity::getTenantId, query.getTenantId().trim()); // 修复：去除可能的空格
        }
        if (StringUtils.isNotEmpty(query.getKeyword())) {
            wrapper.and(w ->
                    //商品名称模糊查询
                    w.like(ProductBaseInfoEntity::getName, query.getKeyword())
                            .or()
                            .like(ProductBaseInfoEntity::getId, query.getKeyword()).or()
                            .eq(ProductBaseInfoEntity::getIsEnable, query.getKeyword()));
        }
        if (query.getType() != null) {
            wrapper.eq(ProductBaseInfoEntity::getType, query.getType());
        } else {
            wrapper.last(
                    "ORDER BY CASE type " +
                            "WHEN 'auction' THEN 1 " +        // 拍卖商品优先级最高
                            "WHEN 'preferential' THEN 2 " +   // 其次优惠商品
                            "WHEN 'wholesale' THEN 3 " +      // 然后批发商品
                            "WHEN 'common' THEN 4 " +         // 最后普通商品
                            "ELSE 5 END"
            );
        }
        if (query.getIsEnable() != null) {
            wrapper.eq(ProductBaseInfoEntity::getIsEnable, query.getIsEnable());
        }
        if (query.getLevels() != null) {
            Integer levels = query.getLevels();
            if (levels == 1) {
                wrapper.eq(ProductBaseInfoEntity::getLevelOneId, query.getTypeId());
            }
            if (levels == 2) {
                wrapper.eq(ProductBaseInfoEntity::getLevelTwoId, query.getTypeId());
            }
            if (levels == 3) {
                wrapper.eq(ProductBaseInfoEntity::getLevelThreeId, query.getTypeId());
            }
        }

        if (query.getKeyWordsList() != null) {
            Set<String> productIds = new HashSet<>();
            for (String keyWithBrackets : query.getKeyWordsList()) {
                try {
                    // 根据传的关键字id查询对应的商品id
                    List<ProductKeywordsEntity> productKeywordsEntities = productKeywordsRepository.selectList(
                            new LambdaQueryWrapper<ProductKeywordsEntity>().eq(ProductKeywordsEntity::getId, keyWithBrackets)
                    );

                    if (productKeywordsEntities != null) {
                        for (ProductKeywordsEntity productKeywordsEntity : productKeywordsEntities) {
                            productIds.add(productKeywordsEntity.getProductId());
                        }
                    }
                } catch (StringIndexOutOfBoundsException e) {
                    log.error("格式错误的关键词:  ", e);
                }
            }
            wrapper.in(ProductBaseInfoEntity::getId, productIds);
        }

        return wrapper;
    }


    private Wrapper<ProductBaseInfoEntity> getWrapperByTypeId(ProductBaseInfoQuery query) {
        LambdaQueryWrapper<ProductBaseInfoEntity> wrapper = super.getWrapper(query);
        if (StringUtils.isNotEmpty(query.getOrder())) {
            if (query.isAsc()) {
                wrapper.orderByDesc(ProductBaseInfoEntity::getPrice);
            } else {
                wrapper.orderByAsc(ProductBaseInfoEntity::getPrice);
            }
        }
        if (StringUtils.isNotEmpty(query.getTenantId())) {
            wrapper.eq(ProductBaseInfoEntity::getTenantId, query.getTenantId());
        }
        if (StringUtils.isNotEmpty(query.getKeyword())) {
            wrapper.and(w ->
                    //商品名称模糊查询
                    w.like(ProductBaseInfoEntity::getName, query.getKeyword())
                            .or()
                            .like(ProductBaseInfoEntity::getId, query.getKeyword()).or()
                            .eq(ProductBaseInfoEntity::getIsEnable, query.getKeyword()));
        }
        if (query.getType() != null) {
            wrapper.eq(ProductBaseInfoEntity::getType, query.getType());
        }

        if (query.getLevels() != null) {
            Integer levels = query.getLevels();
            if (levels == 1) {
                wrapper.eq(ProductBaseInfoEntity::getLevelOneId, query.getTypeId());
            }
            if (levels == 2) {
                wrapper.eq(ProductBaseInfoEntity::getLevelTwoId, query.getTypeId());
            }
            if (levels == 3) {
                wrapper.eq(ProductBaseInfoEntity::getLevelThreeId, query.getTypeId());
            }
        }


        return wrapper;
    }

    private List<ProductDto> convertToProductDtoList(List<ProductBaseInfoDto> productBaseInfoDtoList) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for (ProductBaseInfoDto baseInfo : productBaseInfoDtoList) {
            ProductDto productDto = new ProductDto();
            // 填充合并后的字段 属性拷贝
            BeanUtils.copyProperties(baseInfo, productDto);

            productDto.setProductName(baseInfo.getName()); // 商品名称
            productDto.setProductId(baseInfo.getId());     // 商品ID
            this.getMerchantMainDataInfo(baseInfo, productDto);//仓库名称/仓库位子/税率字段处理
            productDtoList.add(productDto);
        }
        return productDtoList;
    }
}