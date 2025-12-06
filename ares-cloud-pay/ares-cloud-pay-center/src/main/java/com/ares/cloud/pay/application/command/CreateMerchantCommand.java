package com.ares.cloud.pay.application.command;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * 创建商户命令
 * 封装创建商户所需的所有参数，用于在系统中创建新的商户记录
 * <p>
 * 该命令包含商户基本信息、联系信息、业务信息等，用于商户注册和开通
 * <p>
 * 使用场景：
 * 1. 商户注册
 * 2. 商户信息录入
 * 3. 商户开通申请
 */
@Data
@Schema(description = "创建商户命令对象", title = "创建商户命令")
public class CreateMerchantCommand {
    
    /**
     * 商户号
     * <p>
     * 商户的唯一标识号，用于系统内部识别和管理商户。
     * 格式通常为字母MCH开头加上日期和序号，确保全局唯一性。
     */
    @Schema(description = "商户号", example = "MCH20231201001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String merchantNo;
    
    /**
     * 商户名称
     * <p>
     * 商户的正式名称，用于显示和识别商户身份。
     * 建议使用商户的营业执照上的正式名称。
     */
    @Schema(description = "商户名称", example = "示例商户有限公司", requiredMode = Schema.RequiredMode.REQUIRED)
    private String merchantName;
    
    /**
     * 商户类型
     * <p>
     * 商户的类型分类，用于区分不同类型的商户。
     * 支持的类型包括：BUSINESS（企业商户）、INDIVIDUAL（个人商户）。
     */
    @Schema(description = "商户类型", example = "BUSINESS", allowableValues = {"BUSINESS", "INDIVIDUAL"}, requiredMode = Schema.RequiredMode.REQUIRED)
    private String merchantType;
    
    /**
     * 联系人
     * <p>
     * 商户的主要联系人姓名，用于业务沟通和联系。
     */
    @Schema(description = "联系人", example = "张三", requiredMode = Schema.RequiredMode.REQUIRED)
    private String contactPerson;
    
    /**
     * 联系电话
     * <p>
     * 联系人的电话号码，用于业务沟通和紧急联系。
     * 建议使用手机号码，确保能够及时联系。
     */
    @Schema(description = "联系电话", example = "13800138000", requiredMode = Schema.RequiredMode.REQUIRED)
    private String contactPhone;
    
    /**
     * 联系邮箱
     * <p>
     * 联系人的电子邮箱地址，用于业务沟通和通知发送。
     * 建议使用企业邮箱，确保邮箱的有效性。
     */
    @Schema(description = "联系邮箱", example = "contact@example.com")
    private String contactEmail;
    
    /**
     * 商户地址
     * <p>
     * 商户的注册地址或经营地址，用于商户信息记录。
     * 建议使用详细的地址信息，便于后续的地址验证。
     */
    @Schema(description = "商户地址", example = "北京市朝阳区xxx街道xxx号")
    private String address;
    
    /**
     * 营业执照号
     * <p>
     * 商户的营业执照号码，用于商户身份验证和合规性检查。
     * 企业商户必须提供，个人商户可为空。
     */
    @Schema(description = "营业执照号", example = "91110000123456789X")
    private String businessLicense;
    
    /**
     * 法人代表
     * <p>
     * 商户的法人代表姓名，用于商户身份验证和合规性检查。
     * 企业商户必须提供，个人商户可为空。
     */
    @Schema(description = "法人代表", example = "李四")
    private String legalRepresentative;
    
    /**
     * 支持的支付区域
     * <p>
     * 商户支持的支付货币区域，决定商户可以接收哪些货币的支付。
     * 支持的区域包括：EUR（欧元）、USD（美元）、CNY（人民币）、CHF（瑞士法郎）、GBP（英镑）。
     */
    @Schema(description = "支持的支付区域", example = "[\"EUR\", \"USD\", \"CNY\"]", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> supportedRegions;
    
    /**
     * 支付密码
     * <p>
     * 商户的支付密码，用于支付操作的安全验证。
     * 密码必须包含至少6位字符，包含数字和字母。
     */
    @Schema(description = "支付密码", example = "123456", requiredMode = Schema.RequiredMode.REQUIRED)
    private String payPassword;
} 