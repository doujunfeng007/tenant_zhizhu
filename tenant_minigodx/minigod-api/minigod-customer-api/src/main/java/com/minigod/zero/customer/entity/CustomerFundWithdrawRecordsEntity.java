package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

import java.math.BigDecimal;
import java.lang.Boolean;
import lombok.EqualsAndHashCode;
import com.minigod.zero.core.mp.base.BaseEntity;

/**
 * 客户出金申请信息表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_fund_withdraw_records")
@ApiModel(value = "CustomerFundWithdrawRecords对象", description = "客户出金申请信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomerFundWithdrawRecordsEntity extends BaseEntity {
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
     * 客户id
     */
    @ApiModelProperty(value = "客户id")
    private String custId;
    /**
     * 理财账户ID
     */
    @ApiModelProperty(value = "理财账户ID")
    private String accountId;
    /**
     * 交易账户
     */
    @ApiModelProperty(value = "交易账户")
    private String tradeAccount;
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
     * 委托日期
     */
    @ApiModelProperty(value = "委托日期")
    private Date entrustTime;
    /**
     * 证件类型
     */
    @ApiModelProperty(value = "证件类型")
    private Integer idKind;
    /**
     * 证件号码
     */
    @ApiModelProperty(value = "证件号码")
    private String idNode;
    /**
     * 中文名
     */
    @ApiModelProperty(value = "中文名")
    private String clientName;
    /**
     * 英文名
     */
    @ApiModelProperty(value = "英文名")
    private String clientNameSpell;
    /**
     * 银行账号类型
     */
    @ApiModelProperty(value = "银行账号类型")
    private String bankAcctType;
    /**
     * 性别[0-男 1-女 2-其它]
     */
    @ApiModelProperty(value = "性别[0-男 1-女 2-其它]")
    private String sex;
    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码")
    private String mobile;
    /**
     * 取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账 4-支票]
     */
    @ApiModelProperty(value = "取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账 4-支票]")
    private Short transferType;
    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    @ApiModelProperty(value = "币种代码[CNY-人民币 USD-美元 HKD-港币]")
    private String ccy;
    /**
     * 提取金额
     */
    @ApiModelProperty(value = "提取金额")
    private BigDecimal withdrawAmount;
    /**
     * 冻结资金
     */
    @ApiModelProperty(value = "冻结资金")
    private BigDecimal frozenBalance;
    /**
     * 可提余额
     */
    @ApiModelProperty(value = "可提余额")
    private BigDecimal drawableBalance;
    /**
     * 手续费扣除方式[0-从提取金额中扣除 1-从余额中扣除]
     */
    @ApiModelProperty(value = "手续费扣除方式[0-从提取金额中扣除 1-从余额中扣除]")
    private Boolean deductWay;
    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private BigDecimal chargeFee;
    /**
     * 实际提取金额
     */
    @ApiModelProperty(value = "实际提取金额")
    private BigDecimal actualAmount;
    /**
     * 已退款金额
     */
    @ApiModelProperty(value = "已退款金额")
    private BigDecimal refundedAmount;
    /**
     * 已退款日期
     */
    @ApiModelProperty(value = "已退款日期")
    private Date refundedDate;
    /**
     * 资金冻结日期
     */
    @ApiModelProperty(value = "资金冻结日期")
    private Date initDate;
    /**
     * 资金冻结事务流水
     */
    @ApiModelProperty(value = "资金冻结事务流水")
    private String frozenRefId;
    /**
     * 原手续费
     */
    @ApiModelProperty(value = "原手续费")
    private BigDecimal oldChargeFee;
    /**
     * 免除手续费标识标识[0-否 1-是]
     */
    @ApiModelProperty(value = "免除手续费标识标识[0-否 1-是]")
    private Boolean freeFeeFlag;
    /**
     * 收款账户类型[1-银行卡 2-fps id 3-fps phone number 4-fps mail]
     */
    @ApiModelProperty(value = "收款账户类型[1-银行卡 2-fps id 3-fps phone number 4-fps mail]")
    private Boolean recvAccountType;
    /**
     * 收款银行代码
     */
    @ApiModelProperty(value = "收款银行代码")
    private String recvBankCode;
    /**
     * 收款bankId
     */
    @ApiModelProperty(value = "收款bankId")
    private String recvBankId;
    /**
     * 收款银行名称
     */
    @ApiModelProperty(value = "收款银行名称")
    private String recvBankName;
    /**
     * 收款银行帐户
     */
    @ApiModelProperty(value = "收款银行帐户")
    private String recvBankAcct;
    /**
     * 收款银行帐户名称
     */
    @ApiModelProperty(value = "收款银行帐户名称")
    private String recvBankAcctName;
    /**
     * 收款SWIFT代码
     */
    @ApiModelProperty(value = "收款SWIFT代码")
    private String recvSwiftCode;
    /**
     * 客户联系地址
     */
    @ApiModelProperty(value = "客户联系地址")
    private String recvContactAddress;
    /**
     * 收款银行开户支行名称
     */
    @ApiModelProperty(value = "收款银行开户支行名称")
    private String recvBankBranchName;
    /**
     * 开户支行代码
     */
    @ApiModelProperty(value = "开户支行代码")
    private String recvBankBranchCode;
    /**
     * 第三者收款标记[0-否 1-是]
     */
    @ApiModelProperty(value = "第三者收款标记[0-否 1-是]")
    private Boolean thirdRecvFlag;
    /**
     * 与第三者收款人关系
     */
    @ApiModelProperty(value = "与第三者收款人关系")
    private String thirdRecvReal;
    /**
     * 第三者收款原因
     */
    @ApiModelProperty(value = "第三者收款原因")
    private String thirdRecvReason;
    /**
     * 付款方式[0-未知 1-香港银行普通转账 2-香港银行本地转账 3-电汇至香港以外银行 4-支票]
     */
    @ApiModelProperty(value = "付款方式[0-未知 1-香港银行普通转账 2-香港银行本地转账 3-电汇至香港以外银行 4-支票]")
    private Short payWay;
    /**
     * 银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]
     */
    @ApiModelProperty(value = "银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]")
    private Short payType;
    /**
     * 付款银行代码
     */
    @ApiModelProperty(value = "付款银行代码")
    private String payBankCode;
    /**
     * 付款银行名称
     */
    @ApiModelProperty(value = "付款银行名称")
    private String payBankName;
    /**
     * 付款银行账户名称
     */
    @ApiModelProperty(value = "付款银行账户名称")
    private String payAccountName;
    /**
     * 付款银行账户号码
     */
    @ApiModelProperty(value = "付款银行账户号码")
    private String payBankAcct;
    /**
     * 付款银行信息流水号
     */
    @ApiModelProperty(value = "付款银行信息流水号")
    private Long payBankId;
    /**
     * 银行状态[0-未提交 1-成功 2-失败]
     */
    @ApiModelProperty(value = "银行状态[0-未提交 1-成功 2-失败]")
    private Boolean bankState;
    /**
     * 银行事务id
     */
    @ApiModelProperty(value = "银行事务id")
    private String bankRefId;
    /**
     * 银行响应编码
     */
    @ApiModelProperty(value = "银行响应编码")
    private String bankRtCode;
    /**
     * 银行响应消息
     */
    @ApiModelProperty(value = "银行响应消息")
    private String bankRtMsg;
    /**
     * 汇款编号·手工录入
     */
    @ApiModelProperty(value = "汇款编号·手工录入")
    private String remittanceId;
    /**
     * 调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    @ApiModelProperty(value = "调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]")
    private Short callbackStatus;
    /**
     * 柜台业务处理步骤[10000-资金冻结 10002-资金取出 10001-资金解冻 10003-存入资金]
     */
    @ApiModelProperty(value = "柜台业务处理步骤[10000-资金冻结 10002-资金取出 10001-资金解冻 10003-存入资金]")
    private Integer gtBusinessStep;
    /**
     * 柜台处理状态[0-未知 1-处理成功 2-处理失败]
     */
    @ApiModelProperty(value = "柜台处理状态[0-未知 1-处理成功 2-处理失败]")
    private Boolean gtDealStatus;
    /**
     * 柜台处理时间
     */
    @ApiModelProperty(value = "柜台处理时间")
    private Date gtDealDate;
    /**
     * 柜台响应编码
     */
    @ApiModelProperty(value = "柜台响应编码")
    private String gtRtCode;
    /**
     * 柜台响应消息
     */
    @ApiModelProperty(value = "柜台响应消息")
    private String gtRtMsg;
    /**
     * 导出状态[0-未导出 1-已导出]
     */
    @ApiModelProperty(value = "导出状态[0-未导出 1-已导出]")
    private Boolean exportStatus;
    /**
     * 导出时间
     */
    @ApiModelProperty(value = "导出时间")
    private Date exportDate;
    /**
     * 打印状态[0-未打印 1-已打印]
     */
    @ApiModelProperty(value = "打印状态[0-未打印 1-已打印]")
    private Boolean printStatus;
    /**
     * 打印时间
     */
    @ApiModelProperty(value = "打印时间")
    private Date printDate;
    /**
     * 申请来源[1-客户提交  2-后台录入]
     */
    @ApiModelProperty(value = "申请来源[1-客户提交  2-后台录入]")
    private Boolean applySource;
    /**
     * 客服备注
     */
    @ApiModelProperty(value = "客服备注")
    private String custRemark;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 银行参考流水
     */
    @ApiModelProperty(value = "银行参考流水")
    private String bankReference;

}
