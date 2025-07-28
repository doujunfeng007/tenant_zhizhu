package com.minigod.zero.bpmn.module.deposit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 客户入金申请信息表 实体类
 *
 * @author taro
 * @since 2024-02-29
 */
@Data
@TableName("client_fund_deposit_info")
@ApiModel(value = "FundDepositInfo对象", description = "客户入金申请信息表")

public class FundDepositInfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID")
    private Long id;
    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    private String applicationId;
    /**
     * 租户ID
     */
    @ApiModelProperty(value = "租户ID")
    private String tenantId;
    /**
     * 中台入金记录ID
     */
    @ApiModelProperty(value = "中台入金记录ID")
    private Long bizId;
    /**
     * 用户ID
     */
    @ApiModelProperty(value = "用户ID")
    private Long userId;
    /**
     * 交易帐号
     */
    @ApiModelProperty(value = "交易帐号")
    private String clientId;
    /**
     * 资金帐号
     */
    @ApiModelProperty(value = "资金帐号")
    private String fundAccount;
    /**
     * SWIFT代码
     */
    @ApiModelProperty(value = "SWIFT代码")
    private String swiftCode;
    /**
     * 银行卡类型[银行 1大陆 2香港]
     */
    private Integer bankType;
    /**
     * 汇款方式[0-未知 1-网银汇款 2-支票汇款]
     */
    @ApiModelProperty(value = "汇款方式[0-未知 1-网银汇款 2-支票汇款]")
    private Integer remittanceType;
    /**
     * 汇款银行
     */
    @ApiModelProperty(value = "汇款银行")
    private String remittanceBankName;
    /**
     * 汇款账号
     */
    @ApiModelProperty(value = "汇款账号")
    private String remittanceAccount;
    /**
     * 汇款账户名称
     */
    @ApiModelProperty(value = "汇款账户名称")
    private String remittanceAccountName;

    /**
     * 汇款银行代码
     */
    @ApiModelProperty(value = "汇款银行代码")
    private String remittanceBankCode;
    /**
     * 汇款银行机构号
     */
    @ApiModelProperty(value = "汇款银行机构号")
    private String remittanceBankId;
    /**
     * 收款银行
     */
    @ApiModelProperty(value = "收款银行")
    private String receivingBankCode;

    @ApiModelProperty(value = "收款银行名称")
    private String receivingBankName;
    /**
     * 收款账号
     */
    @ApiModelProperty(value = "收款账号")
    private String receivingAccount;
    /**
     * 收款账户名称
     */
    @ApiModelProperty(value = "收款账户名称")
    private String receivingAccountName;
    /**
     * 申请金额
     */
    @ApiModelProperty(value = "申请金额")
    private BigDecimal depositBalance;
    /**
     * 联系地址
     */
    @ApiModelProperty(value = "联系地址")
    private String receivingAddress;
    /**
     * 申请时间
     */
    @ApiModelProperty(value = "申请时间")
    private Date applicationTime;
    /**
     * 币种代码[0-人民币 1-美元 2-港币]
     */
    @ApiModelProperty(value = "币种代码[0-人民币 1-美元 2-港币]")
    private Integer moneyType;
    /**
     * 恒生银行编号
     */
    @ApiModelProperty(value = "恒生银行编号")
    private String hsBankId;
    /**
     * 恒生银行帐户
     */
    @ApiModelProperty(value = "恒生银行帐户")
    private String hsBankAccount;
    /**
     * 恒生处理状态[0-未知 1-处理成功 2-处理失败]
     */
    @ApiModelProperty(value = "恒生处理状态[0-未知 1-处理成功 2-处理失败]")
    private Integer hsDealStatus;

    /**
     * 入账时间
     */
    @ApiModelProperty(value = "入账时间")
    private Date entryTime;

    /**
     * 入账失败重试次数
     */
    @ApiModelProperty(value = "入账失败重试次数")
    private Integer retryCount;
    /**
     * 是否是有效首金
     */
    @ApiModelProperty(value = "是否是有效首金")
    private String firstDepFlag;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "到账金额")
	@JsonSerialize(nullsUsing = NullSerializer.class)
    private BigDecimal receivingAmount;

}
