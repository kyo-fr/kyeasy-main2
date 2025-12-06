package org.ares.cloud.merchantInfo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.baomidou.mybatisplus.annotation.*;
import org.ares.cloud.database.entity.BaseEntity;

import java.math.BigDecimal;
import java.math.BigDecimal;
import java.util.function.LongToDoubleFunction;

/**
* @author hugo tangxkwork@163.com
* @description 商户信息 实体
* @version 1.0.0
* @date 2024-10-08
*/
@TableName("merchant_info")
@EqualsAndHashCode(callSuper = false)
public class MerchantInfoEntity extends BaseEntity {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 商户注册手机号
     */
    private String registerPhone;

    /**
     * 企业名称
     */
    private String name;


    /**
     * 企业域名
     */
    private String domainName;

    /**
     * 税务号
     */
    private String taxNum;

    /**
     * 企业电话
     */
    private String phone;


    /**
     * 企业邮箱
     */
    private String enterpriseEmail;

    /**
     * 企业iBan
     */
    @TableField("I_BAN")
    private String iBan;

    /**
     * 企业BIC
     */
    private String bic;

    /**
     * 商户所在国家
     */
    private String countryCode;

    /**
     * 网站语言
     */
    private String language;

    /**
     * 收款货币
     */
    private String currency;


    /**
     * 首页展示
     */
    private Integer pageDisplay;


    /**
     * 地址
     */
    private String address;

    /**
     * 商户状态 1-注册中；2-正常；3-冻结
     */
    private Integer status;


    /**
     * 是否开启礼物点
     */
    private int isOpenGift;

    /**
     * 商户合同编号 系统生生
     */
    private String contractId;

    /**
     * 总存储量
     */
    private BigDecimal sumMemory;

    /**
     * 已使用存储量
     */
    private BigDecimal usedMemory;

    /**
     * 存储过期时间
     */
    private Long overdueDate;

    /**
     * siren
     */
    private String siren;


    /**
     * code naf
     */
    private String naf;



    private String country;



    private String city;

    /**
     * 邮编
     */
    private String postalCode;

    /**
     * 是否已维护
     * @return
     */
    public String isUpdated="N";

    public Integer getPageDisplay() {
        return pageDisplay;
    }

    public String getUserId() {
        return userId;
    }

    public String getRegisterPhone() {
        return registerPhone;
    }

    public String getName() {
        return name;
    }

    public String getDomainName() {
        return domainName;
    }

    public String getTaxNum() {
        return taxNum;
    }

    public String getPhone() {
        return phone;
    }

    public String getEnterpriseEmail() {
        return enterpriseEmail;
    }

    public String getiBan() {
        return iBan;
    }

    public String getBic() {
        return bic;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getLanguage() {
        return language;
    }

    public String getCurrency() {
        return currency;
    }

    public String getAddress() {
        return address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setRegisterPhone(String registerPhone) {
        this.registerPhone = registerPhone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public void setTaxNum(String taxNum) {
        this.taxNum = taxNum;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEnterpriseEmail(String enterpriseEmail) {
        this.enterpriseEmail = enterpriseEmail;
    }

    public void setiBan(String iBan) {
        this.iBan = iBan;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setPageDisplay(Integer pageDisplay) {
        this.pageDisplay = pageDisplay;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getOpenGift() {
        return isOpenGift;
    }

    public void setOpenGift(int openGift) {
        isOpenGift = openGift;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractId() {
        return contractId;
    }

    public int getIsOpenGift() {
        return isOpenGift;
    }

    public void setIsOpenGift(int isOpenGift) {
        this.isOpenGift = isOpenGift;
    }

    public BigDecimal getSumMemory() {
        return sumMemory;
    }
    public void setSumMemory(BigDecimal sumMemory) {
        this.sumMemory = sumMemory;
    }
    public BigDecimal getUsedMemory() {
        return usedMemory;
    }
    public void setUsedMemory(BigDecimal usedMemory) {
        this.usedMemory = usedMemory;
    }

    public Long getOverdueDate() {
        return overdueDate;
    }

    public void setOverdueDate(Long overdueDate) {
        this.overdueDate = overdueDate;
    }


    public String getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(String isUpdated) {
        this.isUpdated = isUpdated;
    }

    public String getSiren() {
        return siren;
    }
    public void setSiren(String siren) {
        this.siren = siren;
    }
    public String getNaf() {
        return naf;
    }
    public void setNaf(String naf) {
        this.naf = naf;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}