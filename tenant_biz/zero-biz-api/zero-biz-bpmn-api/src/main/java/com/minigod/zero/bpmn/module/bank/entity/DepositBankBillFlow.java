package com.minigod.zero.bpmn.module.bank.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
*@ClassName: DepositBankBillFlow
*@Description: ${description}
*@Author chenyu
*@Date 2024/3/7
*@Version 1.0
*
*/
/**
 * 入金银行流水记录表
 */
@ApiModel(description="入金银行流水记录表")
@Data
@TableName(value = "deposit_bank_bill_flow")

public class DepositBankBillFlow implements Serializable {
    /**
     * 自增ID
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty(value="自增ID")
    @NotNull(message = "自增ID不能为null")
    private Long id;

    /**
     * 银行流水号
     */
    @TableField(value = "reference_no")
    @ApiModelProperty(value="银行流水号")
    @Size(max = 32,message = "银行流水号最大长度要小于 32")
    private String referenceNo;

    /**
     * 入金预约流水号
     */
    @TableField(value = "application_id")
    @ApiModelProperty(value="入金预约流水号")
    @Size(max = 32,message = "入金预约流水号最大长度要小于 32")
    private String applicationId;

    /**
     * 发生金额
     */
    @TableField(value = "credit_mount")
    @ApiModelProperty(value="发生金额")
	@JsonSerialize(nullsUsing = NullSerializer.class)
    private BigDecimal creditMount;

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
    @TableField(value = "acc_no")
    @ApiModelProperty(value="银行账号")
    @Size(max = 256,message = "银行账号最大长度要小于 256")
    private String accNo;

    /**
     * 账户名
     */
    @TableField(value = "acc_name")
    @ApiModelProperty(value="账户名")
    @Size(max = 256,message = "账户名最大长度要小于 256")
    private String accName;

    /**
     * 子账户名
     */
    @TableField(value = "sub_acc_name")
    @ApiModelProperty(value="子账户名")
    @Size(max = 256,message = "子账户名最大长度要小于 256")
    private String subAccName;

    /**
     * 子账号
     */
    @TableField(value = "sub_acc_no")
    @ApiModelProperty(value="子账号")
    @Size(max = 256,message = "子账号最大长度要小于 256")
    private String subAccNo;

    /**
     * 币种
     */
    @TableField(value = "currency")
    @ApiModelProperty(value="币种")
    @Size(max = 32,message = "币种最大长度要小于 32")
    @NotBlank(message = "币种不能为空")
    private String currency;

    /**
     * 发生时间
     */
    @TableField(value = "value_date")
    @ApiModelProperty(value="发生时间")
    private Date valueDate;

    /**
     * 核对状态[0-未核对 1-已核对]
     */
    @TableField(value = "check_status")
    @ApiModelProperty(value="核对状态[0-未核对 1-已核对]")
    @NotNull(message = "核对状态不能为空")
    private Integer checkStatus;

    /**
     * 详情
     */
    @TableField(value = "particulars")
    @ApiModelProperty(value="详情")
    private String particulars;

    /**
     * 是否重复0-未重复 1-重复
     */
    @TableField(value = "is_repeat")
    @ApiModelProperty(value="是否重复0-未重复 1-重复")
    private Boolean isRepeat;

    /**
     * 当前处理人
     */
    @TableField(value = "assign_drafter")
    @ApiModelProperty(value="当前处理人")
    @Size(max = 32,message = "当前处理人最大长度要小于 32")
    private String assignDrafter;

    /**
     * 流水号
     */
    @TableField(value = "msg_id")
    @ApiModelProperty(value="流水号")
    @Size(max = 64,message = "流水号最大长度要小于 64")
    private String msgId;

    /**
     * 客户流水号
     */
    @TableField(value = "customer_reference")
    @ApiModelProperty(value="客户流水号")
    @Size(max = 64,message = "客户流水号最大长度要小于 64")
    private String customerReference;

    /**
     * 交易时间
     */
    @TableField(value = "time_stamp")
    @ApiModelProperty(value="交易时间")
    private Date timeStamp;

    /**
     * 交易类型
     */
    @TableField(value = "txn_type")
    @ApiModelProperty(value="交易类型")
    @Size(max = 32,message = "交易类型最大长度要小于 32")
    private String txnType;

    /**
     * 汇款账户名称
     */
    @TableField(value = "sender_acc_name")
    @ApiModelProperty(value="汇款账户名称")
    @Size(max = 128,message = "汇款账户名称最大长度要小于 128")
    private String senderAccName;

    /**
     * 汇款账户号码
     */
    @TableField(value = "sender_acc_no")
    @ApiModelProperty(value="汇款账户号码")
    @Size(max = 64,message = "汇款账户号码最大长度要小于 64")
    private String senderAccNo;

    /**
     * 汇款银行ID
     */
    @TableField(value = "sender_bank_id")
    @ApiModelProperty(value="汇款银行ID")
    @Size(max = 64,message = "汇款银行ID最大长度要小于 64")
    private String senderBankId;

    /**
     * 数据来源 [0-人工导入 1-api接入]
     */
    @TableField(value = "flow_source")
    @ApiModelProperty(value="数据来源 [0-人工导入 1-api接入]")
    private Integer flowSource;

    /**
     * 实际汇款金额
     */
    @TableField(value = "actual_money")
    @ApiModelProperty(value="实际汇款金额")
    private BigDecimal actualMoney;

    /**
     * are手续费
     */
    @TableField(value = "are_charge_money")
    @ApiModelProperty(value="are手续费")
    private BigDecimal areChargeMoney;

    /**
     * are响应状态:ACSP-查询成功；RJCT-查询失败；PART-查询成功记录超过1000
     */
    @TableField(value = "are_enq_status")
    @ApiModelProperty(value="are响应状态:ACSP-查询成功；RJCT-查询失败；PART-查询成功记录超过1000")
    @Size(max = 20,message = "are响应状态:ACSP-查询成功；RJCT-查询失败；PART-查询成功记录超过1000最大长度要小于 20")
    private String areEnqStatus;

    /**
     * are获取时间
     */
    @TableField(value = "are_time")
    @ApiModelProperty(value="are获取时间")
    private Date areTime;

    /**
     * 银行处理时间
     */
    @TableField(value = "processing_time")
    @ApiModelProperty(value="银行处理时间")
    @Size(max = 30,message = "银行处理时间最大长度要小于 30")
    private String processingTime;

    /**
     * 账户余额
     */
    @TableField(value = "account_balance")
    @ApiModelProperty(value="账户余额")
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private BigDecimal accountBalance;

    /**
     * 租户ID
     */
    @TableField(value = "tenant_id")
    @ApiModelProperty(value="租户ID")
    @Size(max = 30,message = "租户ID最大长度要小于 30")
    @NotBlank(message = "租户ID不能为空")
    private String tenantId;


	/**
	 * 是否已删除：1-已删除
	 */
	@ApiModelProperty(value = "是否已删除：1-已删除")
	@TableField(value = "is_deleted")
	private Integer isDeleted;


    private static final long serialVersionUID = 1L;
}
