package com.ares.cloud.order.domain.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.ares.cloud.order.domain.model.entity.OrderItem;
import com.ares.cloud.order.domain.model.valueobject.ProductInventory;
import com.ares.cloud.order.domain.repository.ProductInventoryRepository;
import com.ares.cloud.order.domain.service.InventoryService;
import com.ares.cloud.order.domain.service.DomainEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ares.cloud.common.exception.RequestBadException;
import com.ares.cloud.order.domain.enums.OrderError;
// 事件相关导入已移除，现在通过DomainEventPublisher发布MQ消息
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 库存服务实现
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {
    
    private final ProductInventoryRepository productInventoryRepository;
    private final DomainEventPublisher domainEventPublisher;
    
    @Override
    public boolean checkStockAvailability(List<OrderItem> orderItems, String merchantId) {
        if (CollectionUtil.isEmpty(orderItems)) {
            return true;
        }
        
        try {
            for (OrderItem orderItem : orderItems) {
                // 跳过服务费和配送费项目
                if ("serviceFee".equals(orderItem.getProductId()) || 
                    "service_fee".equals(orderItem.getProductId()) || 
                    "delivery_fee".equals(orderItem.getProductId())) {
                    continue;
                }
                
                // 获取第一个规格ID（如果有多个规格，这里需要根据业务逻辑处理）
                String specId = null;
                if (CollectionUtil.isNotEmpty(orderItem.getSpecifications())) {
                    specId = orderItem.getSpecifications().get(0).getProductSpecId();
                }
                
                ProductInventory inventory = checkProductStock(
                    orderItem.getProductId(), 
                    specId,
                    orderItem.getQuantity(), 
                    merchantId
                );
                
                if (inventory == null || !inventory.hasEnoughStock(orderItem.getQuantity())) {
                    log.warn("商品 {} 库存不足，需要 {}，可用 {}", 
                        orderItem.getProductName(), 
                        orderItem.getQuantity(), 
                        inventory != null ? inventory.getAvailableStock() : 0);
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            log.error("检查库存可用性失败", e);
            return false;
        }
    }

    
    @Override
    @Transactional
    public boolean reserveStock(List<OrderItem> orderItems, String merchantId) {
        if (CollectionUtil.isEmpty(orderItems)) {
            return true;
        }
        
        try {
            List<ProductInventory> inventoriesToReserve = new ArrayList<>();
            
            for (OrderItem orderItem : orderItems) {
                // 跳过服务费和配送费项目
                if ("serviceFee".equals(orderItem.getProductId()) || 
                    "service_fee".equals(orderItem.getProductId()) || 
                    "delivery_fee".equals(orderItem.getProductId())) {
                    continue;
                }
                
                // 获取第一个规格ID（如果有多个规格，这里需要根据业务逻辑处理）
                String specId = null;
                if (CollectionUtil.isNotEmpty(orderItem.getSpecifications())) {
                    specId = orderItem.getSpecifications().get(0).getProductSpecId();
                }
                
                ProductInventory currentInventory = getProductInventory(
                    orderItem.getProductId(), 
                    specId,
                    merchantId
                );
                
                if (currentInventory == null) {
                    log.error("商品 {} 库存信息不存在", orderItem.getProductId());
                    return false;
                }
                
                // 预留库存
                ProductInventory reservedInventory = currentInventory.reserveStock(orderItem.getQuantity());
                inventoriesToReserve.add(reservedInventory);
            }
            
            // 批量保存预留后的库存
            productInventoryRepository.saveAll(inventoriesToReserve);
            log.info("成功预留库存，订单项数量: {}", orderItems.size());
            return true;
            
        } catch (Exception e) {
            log.error("预留库存失败", e);
            throw new RequestBadException(OrderError.INVENTORY_RESERVE_FAILED);
        }
    }
    
    @Override
    @Transactional
    public boolean releaseStock(List<OrderItem> orderItems, String merchantId) {
        if (CollectionUtil.isEmpty(orderItems)) {
            return true;
        }
        
        try {
            List<ProductInventory> inventoriesToRelease = new ArrayList<>();
            
            for (OrderItem orderItem : orderItems) {
                // 跳过服务费和配送费项目
                if ("serviceFee".equals(orderItem.getProductId()) || 
                    "service_fee".equals(orderItem.getProductId()) || 
                    "delivery_fee".equals(orderItem.getProductId())) {
                    continue;
                }
                
                // 获取第一个规格ID（如果有多个规格，这里需要根据业务逻辑处理）
                String specId = null;
                if (CollectionUtil.isNotEmpty(orderItem.getSpecifications())) {
                    specId = orderItem.getSpecifications().get(0).getProductSpecId();
                }
                
                ProductInventory currentInventory = getProductInventory(
                    orderItem.getProductId(), 
                    specId,
                    merchantId
                );
                
                if (currentInventory == null) {
                    log.warn("商品 {} 库存信息不存在，跳过释放", orderItem.getProductId());
                    continue;
                }
                
                // 释放预留库存
                ProductInventory releasedInventory = currentInventory.releaseReservedStock(orderItem.getQuantity());
                inventoriesToRelease.add(releasedInventory);
            }
            
            // 批量保存释放后的库存
            if (!inventoriesToRelease.isEmpty()) {
                productInventoryRepository.saveAll(inventoriesToRelease);
                log.info("成功释放库存，订单项数量: {}", orderItems.size());
            }
            return true;
            
        } catch (Exception e) {
            log.error("释放库存失败", e);
            throw new RequestBadException(OrderError.INVENTORY_RELEASE_FAILED);
        }
    }
    
    @Override
    @Transactional
    public boolean deductStock(List<OrderItem> orderItems, String merchantId) {
        if (CollectionUtil.isEmpty(orderItems)) {
            return true;
        }
        
        try {
            List<ProductInventory> inventoriesToDeduct = new ArrayList<>();
            
            for (OrderItem orderItem : orderItems) {
                // 跳过服务费和配送费项目
                if ("serviceFee".equals(orderItem.getProductId()) || 
                    "service_fee".equals(orderItem.getProductId()) || 
                    "delivery_fee".equals(orderItem.getProductId())) {
                    continue;
                }
                
                // 获取第一个规格ID（如果有多个规格，这里需要根据业务逻辑处理）
                String specId = null;
                if (CollectionUtil.isNotEmpty(orderItem.getSpecifications())) {
                    specId = orderItem.getSpecifications().get(0).getProductSpecId();
                }
                
                ProductInventory currentInventory = getProductInventory(
                    orderItem.getProductId(), 
                    specId,
                    merchantId
                );
                
                if (currentInventory == null) {
                    log.error("商品 {} 库存信息不存在", orderItem.getProductId());
                    return false;
                }
                
                // 扣减实际库存
                ProductInventory deductedInventory = currentInventory.deductStock(orderItem.getQuantity());
                inventoriesToDeduct.add(deductedInventory);
            }
            
            // 批量保存扣减后的库存
            productInventoryRepository.saveAll(inventoriesToDeduct);
            log.info("成功扣减库存，订单项数量: {}", orderItems.size());
            return true;
            
        } catch (Exception e) {
            log.error("扣减库存失败", e);
            throw new RequestBadException(OrderError.INVENTORY_DEDUCT_FAILED);
        }
    }
    
    @Override
    public ProductInventory checkProductStock(String productId, String productSpecId, Integer quantity, String merchantId) {
        try {
            ProductInventory inventory = getProductInventory(productId, productSpecId, merchantId);
            
            if (inventory == null) {
                throw new RequestBadException(OrderError.PRODUCT_NOT_FOUND);
            }
            
            // 验证商品存在且可用
            inventory.validateProductExists();
            
            // 验证库存充足
            inventory.validateStockSufficient(quantity);
            
            return inventory;
            
        } catch (Exception e) {
            log.error("检查商品库存失败，商品ID: {}, 规格ID: {}, 数量: {}", productId, productSpecId, quantity, e);
            throw e;
        }
    }
    
    @Override
    public ProductInventory getProductInventory(String productId, String productSpecId, String merchantId) {
        try {
            if (productSpecId != null && !productSpecId.trim().isEmpty()) {
                return productInventoryRepository.findByProductIdAndSpecId(productId, productSpecId, merchantId)
                    .orElse(null);
            } else {
                return productInventoryRepository.findByProductId(productId, merchantId)
                    .orElse(null);
            }
        } catch (Exception e) {
            log.error("获取商品库存信息失败，商品ID: {}, 规格ID: {}, 商户ID: {}", productId, productSpecId, merchantId, e);
            return null;
        }
    }

    @Override
    public boolean reserveStock(String orderId, List<OrderItem> orderItems, String merchantId) {
        boolean result = reserveStock(orderItems, merchantId);
        if (result && orderId != null) {
            // 发布库存预留事件
            List<ProductInventory> reservedInventories = new ArrayList<>();
            for (OrderItem orderItem : orderItems) {
                if (!"serviceFee".equals(orderItem.getProductId()) && 
                    !"service_fee".equals(orderItem.getProductId()) && 
                    !"delivery_fee".equals(orderItem.getProductId())) {
                    String specId = null;
                    if (CollectionUtil.isNotEmpty(orderItem.getSpecifications())) {
                        specId = orderItem.getSpecifications().get(0).getProductSpecId();
                    }
                    ProductInventory inventory = getProductInventory(orderItem.getProductId(), specId, merchantId);
                    if (inventory != null) {
                        reservedInventories.add(inventory);
                    }
                }
            }
            if (!reservedInventories.isEmpty()) {
                // 发布库存预留事件
                try {
                    List<DomainEventPublisher.InventoryReservedItem> reservedItems = reservedInventories.stream()
                            .map(inv -> new DomainEventPublisher.InventoryReservedItem(
                                    inv.getProductId(),
                                    inv.getProductSpecId(),
                                    inv.getReservedStock()
                            ))
                            .collect(Collectors.toList());
                    
                    domainEventPublisher.publishInventoryReserved(
                            orderId,
                            merchantId,
                            null, // userId 在库存服务中不可用
                            reservedItems,
                            java.time.LocalDateTime.now(),
                            java.time.LocalDateTime.now().plusMinutes(30) // 30分钟后过期
                    );
                } catch (Exception e) {
                    log.error("发布库存预留事件失败，订单ID: {}, 错误信息: {}", orderId, e.getMessage(), e);
                }
            }
        }
        return result;
    }
    
    @Override
    public boolean releaseStock(String orderId, List<OrderItem> orderItems, String merchantId) {
        boolean result = releaseStock(orderItems, merchantId);
        if (result && orderId != null) {
            // 发布库存释放事件
            List<ProductInventory> releasedInventories = new ArrayList<>();
            for (OrderItem orderItem : orderItems) {
                if (!"serviceFee".equals(orderItem.getProductId()) && 
                    !"service_fee".equals(orderItem.getProductId()) && 
                    !"delivery_fee".equals(orderItem.getProductId())) {
                    String specId = null;
                    if (CollectionUtil.isNotEmpty(orderItem.getSpecifications())) {
                        specId = orderItem.getSpecifications().get(0).getProductSpecId();
                    }
                    ProductInventory inventory = getProductInventory(orderItem.getProductId(), specId, merchantId);
                    if (inventory != null) {
                        releasedInventories.add(inventory);
                    }
                }
            }
            if (!releasedInventories.isEmpty()) {
                // 库存释放事件将通过DomainEventPublisher发布MQ消息
            }
        }
        return result;
    }
    
    @Override
    public boolean deductStock(String orderId, List<OrderItem> orderItems, String merchantId) {
        boolean result = deductStock(orderItems, merchantId);
        if (result && orderId != null) {
            // 发布库存扣减事件
            List<ProductInventory> deductedInventories = new ArrayList<>();
            for (OrderItem orderItem : orderItems) {
                if (!"serviceFee".equals(orderItem.getProductId()) && 
                    !"service_fee".equals(orderItem.getProductId()) && 
                    !"delivery_fee".equals(orderItem.getProductId())) {
                    String specId = null;
                    if (CollectionUtil.isNotEmpty(orderItem.getSpecifications())) {
                        specId = orderItem.getSpecifications().get(0).getProductSpecId();
                    }
                    ProductInventory inventory = getProductInventory(orderItem.getProductId(), specId, merchantId);
                    if (inventory != null) {
                        deductedInventories.add(inventory);
                    }
                }
            }
            if (!deductedInventories.isEmpty()) {
                // 库存扣减事件将通过DomainEventPublisher发布MQ消息
            }
        }
        return result;
    }
}
