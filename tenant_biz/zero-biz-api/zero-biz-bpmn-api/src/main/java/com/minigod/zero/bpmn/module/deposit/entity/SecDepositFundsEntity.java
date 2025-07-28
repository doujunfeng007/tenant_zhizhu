package com.minigod.zero.bpmn.module.deposit.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;
import java.lang.Boolean;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 存入资金表 实体类
 *
 * @author 掌上智珠
 * @since 2024-03-01
 */
@Data
@TableName("sec_deposit_funds")
@ApiModel(value = "SecDepositFunds对象", description = "存入资金表")
public class SecDepositFundsEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;
    /**
     * 币种 1港币 2 美元
     */
    @ApiModelProperty(value = "币种 1港币 2 美元")
    private Integer currency;
    /**
     * {@link com.minigod.zero.biz.common.enums.BankType}
     */
    private Integer bankType;
    /**
     * 客户号
     */
    @ApiModelProperty(value = "客户号")
    private String clientId;
    /**
     * 银行名称
     */
    @ApiModelProperty(value = "银行名称")
    private String bankName;
    /**
     * 银行代码
     */
    @ApiModelProperty(value = "银行代码")
    private String bankCode;
    /**
     * 存入资金账户
     */
    @ApiModelProperty(value = "存入资金账户")
    private String fundAccount;
    /**
     * 存入资金账户名称
     */
    @ApiModelProperty(value = "存入资金账户名称")
    private String fundAccountName;
    /**
     * 存入金额
     */
    @ApiModelProperty(value = "存入金额")
    private BigDecimal depositMoney;
    /**
     * 邀请人
     */
    @ApiModelProperty(value = "邀请人")
    private Integer inviter;
    /**
     * 备注信息
     */
    @ApiModelProperty(value = "备注信息")
    private String remarks;
    /**
     * 状态 0已提交 1已受理 2已退回 3已完成 4已取消
     */
    @ApiModelProperty(value = "状态 0已提交 1已受理 2已退回 3已完成 4已取消")
    private Integer state;
    /**
     * 用户 ID
     */
    @ApiModelProperty(value = "用户 ID")
    private Long userId;
    /**
     * 是否全部加载 0 否 1 是
     */
    @ApiModelProperty(value = "是否全部加载 0 否 1 是")
    private Integer isFind;
    /**
     * 手续费
     */
    @ApiModelProperty(value = "手续费")
    private BigDecimal chargeMoney;
    /**
     * 操作人
     */
    @ApiModelProperty(value = "操作人")
    private String backPerson;
    /**
     * 退回理由
     */
    @ApiModelProperty(value = "退回理由")
    private String backReason;
    /**
     * 前端保存信息
     */
    @ApiModelProperty(value = "前端保存信息")
    private String info;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createdTime;
    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date modifyTime;
    /**
     * 收款账户号码
     */
    @ApiModelProperty(value = "收款账户号码")
    private String receivingAccount;
    /**
     * 收款人账户名
     */
    @ApiModelProperty(value = "收款人账户名")
    private String receivingAccountName;
    /**
     * 收款人地址
     */
    @ApiModelProperty(value = "收款人地址")
    private String receivingAddress;
    /**
     * 收款银行中文名
     */
    @ApiModelProperty(value = "收款银行中文名")
    private String receivingBankNameCn;
    /**
     * 收款银行英文名
     */
    @ApiModelProperty(value = "收款银行英文名")
    private String receivingBankNameEn;
    /**
     * 收款银行地址
     */
    @ApiModelProperty(value = "收款银行地址")
    private String receivingBankAddress;
    /**
     * SWIFT代码
     */
    @ApiModelProperty(value = "SWIFT代码")
    private String swiftCode;
    /**
     * 是否发放奖
     */
    @ApiModelProperty(value = "是否发放奖")
    private Boolean isReward;
    /**
     * 活动ID
     */
    @ApiModelProperty(value = "活动ID")
    private Integer actId;

    /**
     * 回调状态
     */
    @ApiModelProperty(value = "回调状态")
    private Integer pushrecved;
    /**
     * 预约号
     */
    @ApiModelProperty(value = "预约号")
    private String applicationId;
    /**
     * 回调错误次数
     */
    @ApiModelProperty(value = "回调错误次数")
    private Integer errCnt;
    /**
     * 收款银行代码
     */
    @ApiModelProperty(value = "收款银行代码")
    private String receivingBankCode;
    /**
     * 汇款银行cord简称
     */
    @ApiModelProperty(value = "汇款银行代码")
    private String remittanceBankCode;
    /**
     * 汇款银行id(edda入金需要：汇款银行bankId)
     */
    @ApiModelProperty(value = "汇款银行id(edda入金需要：汇款银行bankId)")
    private String remittanceBankId;

	/**
	 * 汇款银行SwiftCode
	 */
	private String remittanceSwiftCode;

    /**
     * 汇款银行名称 （客户）
     */
    @ApiModelProperty(value = "汇款银行名称")
    private String remittanceBankName;
    /**
     * 汇款银行账号 （客户）
     */
    @ApiModelProperty(value = "汇款银行账号")
    private String remittanceBankAccount;
    /**
     * 汇款户名(客户英文)
     */
    @ApiModelProperty(value = "汇款户名(英文)")
    private String remittanceAccountNameEn;
    /**
     * edda入金流水号
     */
    @ApiModelProperty(value = "edda入金流水号")
    private String eddaApplicationId;
    /**
     * 实际到账金额
     */
    @ApiModelProperty(value = "实际到账金额")
    private BigDecimal settlementAmt;
    /**
     * 银行到账日期
     */
    @ApiModelProperty(value = "银行到账日期")
    private Date settlementDt;
    /**
     * 银证入金流水号
     */
    @ApiModelProperty(value = "银证入金流水号")
    private String banksecApplicationId;
    /**
     * 银行业务流水号
     */
    @ApiModelProperty(value = "银行业务流水号")
    private String txnRefId;
    /**
     * 银行账户类别[1-港币 2-美元 3-人民币 0-综合账户]
     */
    @ApiModelProperty(value = "银行账户类别[1-港币 2-美元 3-人民币 0-综合账户]")
    private Integer bankAccountCategory;
    /**
     * app是否可撤销[0-否 1-是]
     */
    @ApiModelProperty(value = "app是否可撤销[0-否 1-是]")
    private Integer isCancel;
    /**
     * 租户 ID
     */
    @ApiModelProperty(value = "租户 ID")
    private String tenantId;

	/**
	 * {@link com.minigod.zero.cms.enums.SupportTypeEnum}
	 */
	private Integer supportType;

}
