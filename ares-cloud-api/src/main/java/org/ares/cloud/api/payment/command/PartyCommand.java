package org.ares.cloud.api.payment.command;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易方信息命令
 * 用于表示发票中的付款方或收款方信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "创建交易方命令对象", title = "创建交易方命令")
public class PartyCommand {
    /**
     * 交易方ID
     * <p>
     * 交易方的唯一标识，用于在系统中唯一标识一个交易方
     * 通常由系统自动生成，可能包含前缀标识和序号
     */
    @Schema(description = "交易方ID", example = "P202410250001")
    private String partyId;

    /**
     * 交易方名称
     * <p>
     * 交易方的全称，如公司名称、个人姓名等
     * 将显示在发票的付款方或收款方部分
     */
    @Schema(description = "交易方名称", example = "北京科技有限公司", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    /**
     * 交易方类型(1:商户,2:个人)
     * <p>
     * 区分交易方的类型：
     * - 1: 商户，表示企业、组织等法人实体
     * - 2: 个人，表示自然人
     * 不同类型的交易方可能有不同的税务处理规则
     */
    @Schema(description = "交易方类型(1:商户,2:个人)", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer partyType;

    /**
     * 税号
     * <p>
     * 交易方的税务登记号码，如企业的统一社会信用代码、个人的纳税人识别号等
     * 用于税务发票的开具和税务申报
     */
    @Schema(description = "税号", example = "91110105MA00B7GT2R")
    private String taxId;

    /**
     * 地址
     * <p>
     * 交易方的详细地址，包括街道、门牌号、城市、省份等
     * 将显示在发票的地址栏中
     */
    @Schema(description = "地址", example = "北京市海淀区中关村南大街5号")
    private String address;

    /**
     * 邮编
     * <p>
     * 交易方地址对应的邮政编码
     * 用于邮寄发票等文件
     */
    @Schema(description = "邮编", example = "100080")
    private String postalCode;
    /**
     * 用户状态(0:未注册,1:已注册)
     */
    @Schema(description = "用户状态(0:未注册,1:已注册)", example = "1")
    private Integer userStatus;

    @Schema(description = "国家代码")
    @JsonProperty(value = "countryCode")
    private String countryCode;
    
    /**
     * 国家
     */
    @Schema(description = "国家", example = "中国")
    private String country;
    
    /**
     * 城市
     */
    @Schema(description = "城市", example = "北京")
    private String city;
    /**
     * 联系电话
     * <p>
     * 交易方的联系电话，可以是固定电话或移动电话
     * 用于联系交易方解决发票相关问题
     */
    @Schema(description = "联系电话", example = "010-12345678")
    private String phone;

    /**
     * 电子邮箱
     * <p>
     * 交易方的电子邮箱地址
     * 用于发送电子发票和相关通知
     */
    @Schema(description = "电子邮箱", example = "contact@example.com")
    private String email;
} 