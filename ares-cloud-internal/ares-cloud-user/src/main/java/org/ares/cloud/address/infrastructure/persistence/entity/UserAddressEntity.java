package org.ares.cloud.address.infrastructure.persistence.entity;

import org.ares.cloud.address.domain.enums.AddressType;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * 用户地址实体
 */
@Data
@TableName("user_address")
public class UserAddressEntity {

    /**
     * 地址ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    
    /**
     * 用户ID
     */
    private String userId;
    
    /**
     * 地址类型
     */
    private AddressType type;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 联系电话
     */
    private String phone;
    
    /**
     * 公司名称
     */
    private String companyName;
    
    /**
     * 企业营业号
     */
    private String businessLicenseNumber;
    
    /**
     * 国家
     */
    private String country;
    
    /**
     * 城市
     */
    private String city;
    
    /**
     * 邮编
     */
    private String postalCode;
    
    /**
     * 详细地址
     */
    private String detail;
    
    /**
     * 纬度
     */
    private Double latitude;
    
    /**
     * 经度
     */
    private Double longitude;
    
    /**
     * 是否默认地址
     */
    private Boolean isDefault;
    
    /**
     * 是否启用
     */
    private Boolean isEnabled;
    
    /**
     * 是否发票地址
     */
    private Boolean isInvoiceAddress;
    
    /**
     * 创建时间
     */
    private Long createTime;
    
    /**
     * 更新时间
     */
    private Long updateTime;
    
    /**
     * 版本号
     */
    private Integer version;
    
    /**
     * 是否删除
     */
    /**
     * 删除标记
     */
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    protected Integer deleted;
}