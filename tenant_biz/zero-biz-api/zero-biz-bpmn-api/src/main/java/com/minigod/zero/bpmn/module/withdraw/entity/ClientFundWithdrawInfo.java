package com.minigod.zero.bpmn.module.withdraw.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
*@ClassName: FundWithdrawInfo
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/18
*@Version 1.0
*
*/
/**
 * 客户出金申请信息表
 */
@ApiModel(description="客户出金申请信息表")
@Data
@TableName(value = "client_fund_withdraw_info")
public class ClientFundWithdrawInfo implements Serializable {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="自增ID")
    @NotNull(message = "自增ID不能为null")
    private Long id;

    /**
     * 流水号
     */
    @TableField(value = "application_id")
    @ApiModelProperty(value="流水号")
    @Size(max = 32,message = "流水号最大长度要小于 32")
    @NotBlank(message = "流水号不能为空")
    private String applicationId;

    /**
     * 客户帐号
     */
    @TableField(value = "client_id")
    @ApiModelProperty(value="客户帐号")
    @Size(max = 32,message = "客户帐号最大长度要小于 32")
    private String clientId;

    /**
     * 资金帐号
     */
    @TableField(value = "fund_account")
    @ApiModelProperty(value="资金帐号")
    @Size(max = 32,message = "资金帐号最大长度要小于 32")
    private String fundAccount;

    /**
     * 委托日期
     */
    @TableField(value = "entrust_time")
    @ApiModelProperty(value="委托日期")
    private Date entrustTime;

    /**
     * 证件类型
     */
    @TableField(value = "id_kind")
    @ApiModelProperty(value="证件类型")
    private Integer idKind;

    /**
     * 证件号码
     */
    @TableField(value = "id_node")
    @ApiModelProperty(value="证件号码")
    @Size(max = 32,message = "证件号码最大长度要小于 32")
    private String idNo;

    /**
     * 中文名
     */
    @TableField(value = "client_name")
    @ApiModelProperty(value="中文名")
    @Size(max = 100,message = "中文名最大长度要小于 100")
    private String clientName;

    /**
     * 英文名
     */
    @TableField(value = "client_name_spell")
    @ApiModelProperty(value="英文名")
    @Size(max = 100,message = "英文名最大长度要小于 100")
    private String clientNameSpell;

    /**
     * 银行账号类型
     */
    @TableField(value = "bank_acct_type")
    @ApiModelProperty(value="银行账号类型")
    @Size(max = 8,message = "银行账号类型最大长度要小于 8")
    private String bankAcctType;

    /**
     * 性别[0-男 1-女 2-其它]
     */
    @TableField(value = "sex")
    @ApiModelProperty(value="性别[0-男 1-女 2-其它]")
    @Size(max = 2,message = "性别[0-男 1-女 2-其它]最大长度要小于 2")
    private String sex;

    /**
     * 手机号码
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value="手机号码")
    @Size(max = 50,message = "手机号码最大长度要小于 50")
    private String mobile;

    /**
     * 取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账 4-支票]
     */
    @TableField(value = "transfer_type")
    @ApiModelProperty(value="取款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行特快转账 4-支票]")
    private Integer transferType;

    /**
     * 币种代码[CNY-人民币 USD-美元 HKD-港币]
     */
    @TableField(value = "ccy")
    @ApiModelProperty(value="币种代码[CNY-人民币 USD-美元 HKD-港币]")
    @Size(max = 10,message = "币种代码[CNY-人民币 USD-美元 HKD-港币]最大长度要小于 10")
    private String ccy;

    /**
     * 提取金额
     */
    @TableField(value = "withdraw_amount")
    @ApiModelProperty(value="提取金额")
    private BigDecimal withdrawAmount;

    /**
     * 冻结资金
     */
    @TableField(value = "frozen_balance")
    @ApiModelProperty(value="冻结资金")
    private BigDecimal frozenBalance;

    /**
     * 可提余额
     */
    @TableField(value = "drawable_balance")
    @ApiModelProperty(value="可提余额")
    private BigDecimal drawableBalance;

    /**
     * 手续费扣除方式[0-从提取金额中扣除 1-从余额中扣除]
     */
    @TableField(value = "deduct_way")
    @ApiModelProperty(value="手续费扣除方式[0-从提取金额中扣除 1-从余额中扣除]")
    private Integer deductWay;

    /**
     * 手续费
     */
    @TableField(value = "charge_fee")
    @ApiModelProperty(value="手续费")
    private BigDecimal chargeFee;

    /**
     * 实际提取金额
     */
    @TableField(value = "actual_amount")
    @ApiModelProperty(value="实际提取金额")
    private BigDecimal actualAmount;

    /**
     * 已退款金额
     */
    @TableField(value = "refunded_amount")
    @ApiModelProperty(value="已退款金额")
    private BigDecimal refundedAmount;

    /**
     * 已退款日期
     */
    @TableField(value = "refunded_date")
    @ApiModelProperty(value="已退款日期")
    private Date refundedDate;

    /**
     * 资金冻结日期
     */
    @TableField(value = "init_date")
    @ApiModelProperty(value="资金冻结日期")
    private Date initDate;

    /**
     * 资金冻结事务流水
     */
    @TableField(value = "frozen_ref_id")
    @ApiModelProperty(value="资金冻结事务流水")
    @Size(max = 40,message = "资金冻结事务流水最大长度要小于 40")
    private String frozenRefId;

    /**
     * 原手续费
     */
    @TableField(value = "old_charge_fee")
    @ApiModelProperty(value="原手续费")
    private BigDecimal oldChargeFee;

    /**
     * 免除手续费标识标识[0-否 1-是]
     */
    @TableField(value = "free_fee_flag")
    @ApiModelProperty(value="免除手续费标识标识[0-否 1-是]")
    private Integer freeFeeFlag;

    /**
     * 收款账户类型[1-银行卡 2-fps id 3-fps phone number 4-fps mail]
     */
    @TableField(value = "recv_account_type")
    @ApiModelProperty(value="收款账户类型[1-银行卡 2-fps id 3-fps phone number 4-fps mail]")
    private Integer recvAccountType;

    /**
     * 收款银行代码
     */
    @TableField(value = "recv_bank_code")
    @ApiModelProperty(value="收款银行代码")
    @Size(max = 64,message = "收款银行代码最大长度要小于 64")
    private String recvBankCode;

    /**
     * 收款bankId
     */
    @TableField(value = "recv_bank_id")
    @ApiModelProperty(value="收款bankId")
    @Size(max = 50,message = "收款bankId最大长度要小于 50")
    private String recvBankId;

    /**
     * 收款银行名称
     */
    @TableField(value = "recv_bank_name")
    @ApiModelProperty(value="收款银行名称")
    @Size(max = 256,message = "收款银行名称最大长度要小于 256")
    private String recvBankName;

    /**
     * 收款银行帐户
     */
    @TableField(value = "recv_bank_acct")
    @ApiModelProperty(value="收款银行帐户")
    @Size(max = 32,message = "收款银行帐户最大长度要小于 32")
    private String recvBankAcct;

    /**
     * 收款银行帐户名称
     */
    @TableField(value = "recv_bank_acct_name")
    @ApiModelProperty(value="收款银行帐户名称")
    @Size(max = 100,message = "收款银行帐户名称最大长度要小于 100")
    private String recvBankAcctName;

    /**
     * 收款SWIFT代码
     */
    @TableField(value = "recv_swift_code")
    @ApiModelProperty(value="收款SWIFT代码")
    @Size(max = 32,message = "收款SWIFT代码最大长度要小于 32")
    private String recvSwiftCode;

    /**
     * 客户联系地址
     */
    @TableField(value = "recv_contact_address")
    @ApiModelProperty(value="客户联系地址")
    @Size(max = 512,message = "客户联系地址最大长度要小于 512")
    private String recvContactAddress;

    /**
     * 收款银行开户支行名称
     */
    @TableField(value = "recv_bank_branch_name")
    @ApiModelProperty(value="收款银行开户支行名称")
    @Size(max = 255,message = "收款银行开户支行名称最大长度要小于 255")
    private String recvBankBranchName;

    /**
     * 开户支行代码
     */
    @TableField(value = "recv_bank_branch_code")
    @ApiModelProperty(value="开户支行代码")
    @Size(max = 50,message = "开户支行代码最大长度要小于 50")
    private String recvBankBranchCode;

    /**
     * 第三者收款标记[0-否 1-是]
     */
    @TableField(value = "third_recv_flag")
    @ApiModelProperty(value="第三者收款标记[0-否 1-是]")
    private Boolean thirdRecvFlag;

    /**
     * 与第三者收款人关系
     */
    @TableField(value = "third_recv_real")
    @ApiModelProperty(value="与第三者收款人关系")
    @Size(max = 50,message = "与第三者收款人关系最大长度要小于 50")
    private String thirdRecvReal;

    /**
     * 第三者收款原因
     */
    @TableField(value = "third_recv_reason")
    @ApiModelProperty(value="第三者收款原因")
    @Size(max = 100,message = "第三者收款原因最大长度要小于 100")
    private String thirdRecvReason;

    /**
     * 付款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行本地转账 4-支票 5-FPSID]
     */
    @TableField(value = "pay_way")
    @ApiModelProperty(value="付款方式[1-电汇至香港以外银行 2-香港银行普通转账 3-香港银行本地转账 4-支票 5-FPSID]")
    private Integer payWay;

    /**
     * 银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]
     */
    @TableField(value = "pay_type")
    @ApiModelProperty(value="银行打款方式[0-未知 1-FPS PPP 2-FPS GCP 3-ACT DBS同行转账 4-RTGS 香港本地转账 5-TT 海外电汇]")
    private Integer payType;

    /**
     * 付款银行代码
     */
    @TableField(value = "pay_bank_code")
    @ApiModelProperty(value="付款银行代码")
    @Size(max = 64,message = "付款银行代码最大长度要小于 64")
    private String payBankCode;

    /**
     * 付款银行名称
     */
    @TableField(value = "pay_bank_name")
    @ApiModelProperty(value="付款银行名称")
    @Size(max = 256,message = "付款银行名称最大长度要小于 256")
    private String payBankName;

    /**
     * 付款银行账户名称
     */
    @TableField(value = "pay_account_name")
    @ApiModelProperty(value="付款银行账户名称")
    @Size(max = 256,message = "付款银行账户名称最大长度要小于 256")
    private String payAccountName;

    /**
     * 付款银行账户号码
     */
    @TableField(value = "pay_bank_acct")
    @ApiModelProperty(value="付款银行账户号码")
    @Size(max = 32,message = "付款银行账户号码最大长度要小于 32")
    private String payBankAcct;

    /**
     * 付款银行信息流水号
     */
    @TableField(value = "pay_bank_id")
    @ApiModelProperty(value="付款银行信息流水号")
    private Long payBankId;

    /**
     * 银行状态[0-未提交 1-失败 2-成功]
	 * {@link SystemCommonEnum.BankStateTypeEnum}
     */
    @TableField(value = "bank_state")
    @ApiModelProperty(value="银行状态[0-未提交 1-失败 2-成功]")
    private Integer bankState;

    /**
     * 银行事务id
     */
    @TableField(value = "bank_ref_id")
    @ApiModelProperty(value="银行事务id")
    @Size(max = 40,message = "银行事务id最大长度要小于 40")
    private String bankRefId;

    /**
     * 银行响应编码
     */
    @TableField(value = "bank_rt_code")
    @ApiModelProperty(value="银行响应编码")
    @Size(max = 50,message = "银行响应编码最大长度要小于 50")
    private String bankRtCode;

    /**
     * 银行响应消息
     */
    @TableField(value = "bank_rt_msg")
    @ApiModelProperty(value="银行响应消息")
    @Size(max = 500,message = "银行响应消息最大长度要小于 500")
    private String bankRtMsg;

    /**
     * 汇款编号·手工录入
     */
    @TableField(value = "remittance_id")
    @ApiModelProperty(value="汇款编号·手工录入")
    @Size(max = 100,message = "汇款编号·手工录入最大长度要小于 100")
    private String remittanceId;

    /**
     * 调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]
     */
    @TableField(value = "callback_status")
    @ApiModelProperty(value="调度柜台操作状态[0-未知 1-待处理 2-成功 3-失败 4-处理中 5-未处理]")
    private Integer callbackStatus;

    /**
     * 柜台业务处理步骤[10000-资金冻结 10002-资金取出 10001-资金解冻 10003-存入资金]
     */
    @TableField(value = "gt_business_step")
    @ApiModelProperty(value="柜台业务处理步骤[10000-资金冻结 10002-资金取出 10001-资金解冻 10003-存入资金]")
    private Integer gtBusinessStep;

    /**
     * 柜台处理状态[0-未知 1-处理成功 2-处理失败]
     */
    @TableField(value = "gt_deal_status")
    @ApiModelProperty(value="柜台处理状态[0-未知 1-处理成功 2-处理失败]")
    private Integer gtDealStatus;

    /**
     * 柜台处理时间
     */
    @TableField(value = "gt_deal_date")
    @ApiModelProperty(value="柜台处理时间")
    private Date gtDealDate;

    /**
     * 柜台响应编码
     */
    @TableField(value = "gt_rt_code")
    @ApiModelProperty(value="柜台响应编码")
    @Size(max = 50,message = "柜台响应编码最大长度要小于 50")
    private String gtRtCode;

    /**
     * 柜台响应消息
     */
    @TableField(value = "gt_rt_msg")
    @ApiModelProperty(value="柜台响应消息")
    @Size(max = 500,message = "柜台响应消息最大长度要小于 500")
    private String gtRtMsg;

    /**
     * 导出状态[0-未导出 1-已导出]
     */
    @TableField(value = "export_status")
    @ApiModelProperty(value="导出状态[0-未导出 1-已导出]")
    private Integer exportStatus;

    /**
     * 导出时间
     */
    @TableField(value = "export_date")
    @ApiModelProperty(value="导出时间")
    private Date exportDate;

    /**
     * 打印状态[0-未打印 1-已打印]
     */
    @TableField(value = "print_status")
    @ApiModelProperty(value="打印状态[0-未打印 1-已打印]")
    private Integer printStatus;

    /**
     * 打印时间
     */
    @TableField(value = "print_date")
    @ApiModelProperty(value="打印时间")
    private Date printDate;

    /**
     * 申请来源[1-客户提交  2-后台录入]
     */
    @TableField(value = "apply_source")
    @ApiModelProperty(value="申请来源[1-客户提交  2-后台录入]")
    private Integer applySource;

    /**
     * 客服备注
     */
    @TableField(value = "cust_remark")
    @ApiModelProperty(value="客服备注")
    @Size(max = 500,message = "客服备注最大长度要小于 500")
    private String custRemark;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    @Size(max = 500,message = "备注最大长度要小于 500")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private Date createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_user")
    @ApiModelProperty(value="创建人")
    private Long createUser;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    @ApiModelProperty(value="更新时间")
    private Date updateTime;

    /**
     * 修改人
     */
    @TableField(value = "update_user")
    @ApiModelProperty(value="修改人")
    private Long updateUser;

    /**
     * 银行参考流水
     */
    @TableField(value = "bank_reference")
    @ApiModelProperty(value="银行参考流水")
    @Size(max = 255,message = "银行参考流水最大长度要小于 255")
    private String bankReference;

    /**
     * 租户 ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户 ID")
    @Size(max = 20,message = "租户 ID最大长度要小于 20")
    @NotBlank(message = "租户 ID不能为空")
    private String tenantId;

    /**
     * 组织 ID
     */
    @TableField(value = "create_dept")
    @ApiModelProperty(value="组织 ID")
    private Long createDept;

    /**
     * 状态
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value="状态")
    private Integer status;

    /**
     * 删除
     */
    @TableField(value = "is_deleted")
    @ApiModelProperty(value="删除")
    private Integer isDeleted;

    /**
     * 银行卡类型[1香港  2大陆 3海外]
     */
    @TableField(value = "recv_bank_type")
    @ApiModelProperty(value="银行卡类型[1香港  2大陆 3海外]")
    private Integer recvBankType;

	/**
	 * 汇款凭证地址
	 */
	private String remittanceVoucher;

    private Long userId;

    private static final long serialVersionUID = 1L;
}
