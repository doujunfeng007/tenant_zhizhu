package com.minigod.zero.bpmn.module.deposit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
*@ClassName: BankCardReviewInfo
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/13
*@Version 1.0
*
*/
/**
 * 银行卡信息审核记录表
 */
@ApiModel(description="银行卡信息审核记录表")
@Data
@TableName(value = "client_bank_card_review_info")
public class BankCardReviewInfo implements Serializable {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="自增ID")
    @NotNull(message = "自增ID不能为null")
    private Long id;

    /**
     * 交易帐号
     */
    @TableField(value = "client_id")
    @ApiModelProperty(value="交易帐号")
    @Size(max = 32,message = "交易帐号最大长度要小于 32")
    @NotBlank(message = "交易帐号不能为空")
    private String clientId;

    /**
     * 银行卡类型[1-香港本地银行 2-中国大陆银行 3-海外银行]
     */
    @TableField(value = "bank_type")
    @ApiModelProperty(value="银行卡类型[1-香港本地银行 2-中国大陆银行 3-海外银行]")
    @NotNull(message = "银行卡类型[0-香港银行卡 1-大陆银行卡]不能为null")
    private Integer bankType;

    /**
     * 银行名称
     */
    @TableField(value = "bank_name")
    @ApiModelProperty(value="银行名称")
    @Size(max = 256,message = "银行名称最大长度要小于 256")
    private String bankName;

    /**
     * 银行账号
     */
    @TableField(value = "bank_no")
    @ApiModelProperty(value="银行账号")
    @Size(max = 32,message = "银行账号最大长度要小于 32")
    private String bankNo;

    /**
     * 银行账户名
     */
    @TableField(value = "bank_account")
    @ApiModelProperty(value="银行账户名")
    @Size(max = 256,message = "银行账户名最大长度要小于 256")
    private String bankAccount;

    /**
     * 银行代码
     */
    @TableField(value = "bank_code")
    @ApiModelProperty(value="银行代码")
    @Size(max = 64,message = "银行代码最大长度要小于 64")
    private String bankCode;

    /**
     * 银行账户类别[1-港币 2-美元 3-人民币 0-综合账户]
     */
    @TableField(value = "bank_account_category")
    @ApiModelProperty(value="银行账户类别[1-港币 2-美元 3-人民币 0-综合账户]")
    private Integer bankAccountCategory;

    /**
     * 认证标识[0-未认证 1-待认证 2-已认证]
     */
    @TableField(value = "auth_sign")
    @ApiModelProperty(value="认证标识[0-未认证 1-待认证 2-已认证]")
    private Integer authSign;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户ID")
    @Size(max = 20,message = "租户ID最大长度要小于 20")
    @NotBlank(message = "租户ID不能为空")
    private String tenantId;

    /**
     * 流水号
     */
    @TableField(value = "application_id")
    @ApiModelProperty(value="流水号")
    @Size(max = 32,message = "流水号最大长度要小于 32")
    @NotBlank(message = "流水号不能为空")
    private String applicationId;

    /**
     * 审核类型 1新增 2 删除 3 修改
     */
    @TableField(value = "application_type")
    @ApiModelProperty(value="审核类型 1新增 2 删除 3 修改")
    @NotNull(message = "审核类型 1新增 2 删除 3 修改不能为null")
    private Integer applicationType;

    /**
     * 银行卡 ID
     */
    @TableField(value = "bank_card_id")
    @ApiModelProperty(value="银行卡 ID")
    private Long bankCardId;

    @TableField(value = "is_finish")
    @ApiModelProperty(value="是否已完成 0未完成 1已完成")
    private Integer isFinish;

    @TableField(value = "swift_code")
    @ApiModelProperty(value="电汇代码")
    private String swiftCode;

    @TableField(value = "bank_id")
    @ApiModelProperty(value="银行代码")
    private String bankId;

    @TableField(value = "receive_region")
    @ApiModelProperty(value="收款地区")
    private String receiveRegion;

    @TableField(value = "bind_source")
    private String bindSource;

    @TableField(value = "user_id")
    private Long userId;

    private static final long serialVersionUID = 1L;
}
