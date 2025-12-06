package org.ares.cloud.product.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.ares.cloud.api.merchant.MerchantClient;
import org.ares.cloud.api.merchant.dto.MerchantInfo;
import org.ares.cloud.api.user.UserServerClient;
import org.ares.cloud.api.user.dto.UserDto;
import org.ares.cloud.common.context.ApplicationContext;
import org.ares.cloud.common.exception.RequestBadException;
import org.ares.cloud.product.convert.ProductAuctionConvert;
import org.ares.cloud.product.convert.ProductAuctionPriceLogConvert;
import org.ares.cloud.product.dto.ProductAuctionDto;
import org.ares.cloud.product.dto.ProductAuctionPriceLogDto;
import org.ares.cloud.product.entity.ProductAuctionEntity;
import org.ares.cloud.product.entity.ProductAuctionPriceLogEntity;
import org.ares.cloud.product.entity.ProductBaseInfoEntity;
import org.ares.cloud.product.enums.ProductError;
import org.ares.cloud.product.query.ProductAuctionPriceLogQuery;
import org.ares.cloud.product.query.ProductBaseInfoQuery;
import org.ares.cloud.product.repository.ProductAuctionPriceLogRepository;
import org.ares.cloud.product.repository.ProductAuctionRepository;
import org.ares.cloud.product.repository.ProductBaseInfoRepository;
import org.ares.cloud.product.service.ProductAuctionPriceLogService;
import org.ares.cloud.database.service.impl.BaseServiceImpl;
import org.ares.cloud.common.dto.PageResult;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import org.ares.cloud.product.vo.ProductAuctionPriceLogVo;
import org.ares.cloud.redis.cache.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hugo tangxkwork@163.com
 * @version 1.0.0
 * @description 拍卖商品竞价记录 服务实现
 * @date 2025-09-23
 */
@Service
@AllArgsConstructor
public class ProductAuctionPriceLogServiceImpl extends BaseServiceImpl<ProductAuctionPriceLogRepository, ProductAuctionPriceLogEntity> implements ProductAuctionPriceLogService {

    @Resource
    private ProductAuctionPriceLogConvert convert;

    @Resource
    private RedisCache redisCache;

    @Resource
    private MerchantClient merchantClient;


    @Resource
    private ProductAuctionRepository productAuctionRepository;

    @Resource
    private ProductBaseInfoRepository productBaseInfoRepository;


    @Resource
    private UserServerClient userServerClient;

    /**
     * 创建
     *
     * @param dto 数据模型
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void userAddPrice(ProductAuctionPriceLogDto dto, String domainName) {
        //查询商品是否已下架
        QueryWrapper<ProductBaseInfoEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("id", dto.getProductId());
        wrapper.eq("is_enable", "enable");
        ProductBaseInfoEntity productBaseInfoEntity = productBaseInfoRepository.selectOne(wrapper);
        if (productBaseInfoEntity == null) {
            //商品已下架
            throw new RequestBadException(ProductError.PRODUCT_IS_NOT_ENABLE_ERROR);
        }

        //每次加价要比上次加的价格高
        QueryWrapper<ProductAuctionEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", dto.getProductId());
        ProductAuctionEntity productAuctionEntity = productAuctionRepository.selectOne(queryWrapper);

        if (productAuctionEntity == null) {
            throw new RequestBadException(ProductError.PRODUCT_NOT_EXIST_ERROR);
        }
        if (productAuctionEntity.getStartTime() > System.currentTimeMillis()) {
            //商品未开始拍卖
            throw new RequestBadException(ProductError.PRODUCT_NOT_START_AUCTION_ERROR);
        }
        if (productAuctionEntity.getEndTime() < System.currentTimeMillis()) {
            //商品已结束拍卖
            throw new RequestBadException(ProductError.PRODUCT_END_AUCTION_ERROR);
        }
        //每次加价不能比一口价高
        if (productAuctionEntity.getFixedPrice().compareTo(dto.getPrice()) <= 0) {
            throw new RequestBadException(ProductError.PRODUCT_ADD_PRICE_IS_HIGH_FIXED_PRICE_ERROR);
        }
        if (productAuctionEntity.getFares().compareTo(dto.getPrice()) >= 0) {
            //商品加价不能低于之前的加价价格
            throw new RequestBadException(ProductError.PRODUCT_ADD_PRICE_IS_LOW_ERROR);
        }
        if (StringUtils.isBlank(dto.getTenantId())) {
            dto.setTenantId(getMerchantInfo(domainName));
        }
        dto.setUserId(ApplicationContext.getUserId());
        ProductAuctionPriceLogEntity entity = convert.toEntity(dto);
        int insert = this.baseMapper.insert(entity);
        if (insert > 0) {
            //修改商品加价字段
            productAuctionEntity.setFares(entity.getPrice());
            productAuctionRepository.updateById(productAuctionEntity);
        }
    }

    /**
     * 批量创建
     *
     * @param dos 数据模型集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(List<ProductAuctionPriceLogDto> dos) {
        List<ProductAuctionPriceLogEntity> entities = convert.listToEntities(dos);
        this.saveBatch(entities);
    }

    /**
     * 更新
     *
     * @param dto 数据模型
     */
    @Override
    public void update(ProductAuctionPriceLogDto dto) {
        ProductAuctionPriceLogEntity entity = convert.toEntity(dto);
        this.updateById(entity);
    }

    /**
     * 批量更新
     *
     * @param dos 数据模型集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(List<ProductAuctionPriceLogDto> dos) {
        List<ProductAuctionPriceLogEntity> entities = convert.listToEntities(dos);
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
    public ProductAuctionPriceLogDto loadById(String id) {
        ProductAuctionPriceLogEntity entity = this.baseMapper.selectById(id);
        if (entity != null) {
            return convert.toDto(entity);
        }
        return null;
    }

    /**
     * 根据id获取详情
     *
     * @param ids 主键
     */
    @Override
    public List<ProductAuctionPriceLogDto> loadByIds(List<String> ids) {
        List<ProductAuctionPriceLogEntity> entities = this.baseMapper.selectBatchIds(ids);
        return convert.listToDto(entities);
    }

    /**
     * 加载所有数据
     *
     * @return 数据集合
     */
    @Override
    public List<ProductAuctionPriceLogDto> loadAll() {
        LambdaQueryWrapper<ProductAuctionPriceLogEntity> wrapper = new LambdaQueryWrapper<>();
        List<ProductAuctionPriceLogEntity> entities = this.baseMapper.selectList(wrapper);
        return convert.listToDto(entities);
    }

    /**
     * 查询列表
     *
     * @param query 查询对象
     * @return
     */
    @Override
    public PageResult<ProductAuctionPriceLogVo> loadList(ProductAuctionPriceLogQuery query) {
        IPage<ProductAuctionPriceLogEntity> page = baseMapper.selectPage(getPage(query), getWrapper(query));
        if (CollectionUtil.isEmpty(page.getRecords())) {
            return new PageResult<>(new ArrayList<>(), 0);
        }
        List<ProductAuctionPriceLogVo> list = new ArrayList<>();
        //调用用户服务查询用户nick
        if (page.getRecords() != null) {
            //调用用户服务
            for (ProductAuctionPriceLogEntity entity : page.getRecords()) {
                UserDto userDto = userServerClient.get(entity.getUserId());
                ProductAuctionPriceLogVo vo = new ProductAuctionPriceLogVo();
                if (userDto != null) {
                    vo.setNickName(userDto.getNickname());
                }
                BeanUtils.copyProperties(entity, vo);
                list.add(vo);
            }
        }
        return new PageResult<>(list, page.getTotal());
    }

    /**
     * 获取条件
     *
     * @param query
     * @return
     */
    private Wrapper<ProductAuctionPriceLogEntity> getWrapper(ProductAuctionPriceLogQuery query) {
        LambdaQueryWrapper<ProductAuctionPriceLogEntity> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isBlank(query.getOrder())) {
            wrapper.orderByDesc(ProductAuctionPriceLogEntity::getId);
        }
        if (query.getProductId() != null){
            wrapper.eq(ProductAuctionPriceLogEntity::getProductId, query.getProductId());
        }
        return wrapper;
    }

    private String getMerchantInfo(String domainName) {
        //查询redis
        Object domainNameValue = redisCache.get(domainName);
        if (domainNameValue != null) {
            return domainNameValue.toString();
        } else {
            MerchantInfo merchantInfo = merchantClient.getMerchantInfoByDomain(domainName);
            if (merchantInfo != null) {
                redisCache.set(domainName, merchantInfo.getId(), RedisCache.DEFAULT_EXPIRE);
                return merchantInfo.getId();
            }
            return null;
        }
    }
}