package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 客户入金申请信息表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_gold_deposit_records")
@ApiModel(value = "CustomerGoldDepositRecords对象", description = "客户入金申请信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomerGoldDepositRecordsEntity extends BaseEntity {
	/**
	 * 租户id
	 */
	@ApiModelProperty(value = "租户id")
	private String tenantId;
    /**
     * 流水号
     */
    @ApiModelProperty(value = "流水号")
    private String applicationId;
    /**
     * 客户ID
     */
    @ApiModelProperty(value = "客户ID")
    private Long custId;
    /**
     * 理财账户ID
     */
    @ApiModelProperty(value = "理财账户ID")
    private String accountId;
    /**
     * 交易账户
     */
    @ApiModelProperty(value = "交易账户")
    private String transactionAccount;
    /**
     * 交易账户类型，0股票，1基金
     */
    @ApiModelProperty(value = "交易账户类型，0股票，1基金")
    private Integer accountType;
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
     * 入金方式[0-香港银行卡 1-大陆银行卡]
     */
    @ApiModelProperty(value = "入金方式[0-香港银行卡 1-大陆银行卡]")
    private Integer depositType;
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
    private String receivingBankName;
    /**
     * 收款银行代码
     */
    @ApiModelProperty(value = "收款银行代码")
    private String receivingBankCode;
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
     * 联系地址
     */
    @ApiModelProperty(value = "联系地址")
    private String receivingAddress;
    /**
     * 申请金额
     */
    @ApiModelProperty(value = "申请金额")
    private BigDecimal depositBalance;
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
     * 实际到账金额
     */
    @ApiModelProperty(value = "实际到账金额")
    private BigDecimal receivingAmount;

}
