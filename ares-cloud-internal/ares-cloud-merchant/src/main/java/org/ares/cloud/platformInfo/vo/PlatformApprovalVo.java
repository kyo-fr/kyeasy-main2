package org.ares.cloud.platformInfo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.v3.oas.annotations.media.Schema;
import org.ares.cloud.common.serializer.CustomBigDecimalSerializer;

import java.math.BigDecimal;


public class PlatformApprovalVo {

    @Schema(description = "主键id")
    private String id;


    @Schema(description = "用户手机号")
    private String phone;

    @Schema(description = "审批编号")
    private String approvalId;


    @Schema(description = "订单编号")
    private String orderId;

    @Schema(description = "审批状态 1:未处理；2:已处理；")
    private Integer status;

    @Schema(description = "订阅类型key: cash-收银系统订阅；storage-存储空间订阅")
    private String subscribeType;

    @Schema(description = "单价")
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal price;


    @Schema(description = "创建时间")
    private Long   createTime;

    @Schema(description = "用户id")
    private String  userId;

    @Schema(description = "税率值")
    private BigDecimal taxRate;

    @Schema(description ="月份")
    private Integer month;

    @Schema(description ="总价")
    @JsonSerialize(using = CustomBigDecimalSerializer.class)
    private BigDecimal totalPrice;


    @Schema(description ="企业名称")
    private String name;

    @Schema(description ="存储量,单位G")
    private Integer memory;

    @Schema(description = "合同id")
    private String contractId;

    @Schema(description ="发票id")
    private String invoiceId;

    /**
     * 订阅状态
     */
    @Schema(description ="订阅状态 sub:订阅；over:到期" )
    private String subStatus;

    /**
     * 订阅状态
     */
    @Schema(description ="账号",example = "+8613333333333")
    private String account;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setApprovalId(String approvalId) {
        this.approvalId = approvalId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setSubscribeType(String subscribeType) {
        this.subscribeType = subscribeType;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public String getApprovalId() {
        return approvalId;
    }

    public String getOrderId() {
        return orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public String getSubscribeType() {
        return subscribeType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public Integer getMonth() {
        return month;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getSubStatus() {
        return subStatus;
    }
    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }

    public String getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
