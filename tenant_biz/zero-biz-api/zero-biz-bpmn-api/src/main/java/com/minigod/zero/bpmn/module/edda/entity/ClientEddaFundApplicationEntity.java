package com.minigod.zero.bpmn.module.edda.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import com.minigod.zero.bpmn.module.withdraw.enums.SystemCommonEnum;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DBS edda入金流水表
 * @TableName client_edda_fund_application
 */
@TableName(value ="client_edda_fund_application")
@Data
public class ClientEddaFundApplicationEntity implements Serializable {
	@TableField(exist = false)
	private static final long serialVersionUID = 1L;
    /**
     * 自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 流水号
     */
    private String applicationId;

    /**
     * 交易账号
     */
    private String clientId;

    /**
     * 资金账号
     */
    private String fundAccount;

    /**
     * 客户英文名
     */
    private String eName;

    /**
     * 客户中文名
     */
    private String cName;

    /**
     * 00中华人民共和国居民身份证, 03 中华人民共和国往来港澳通行证, 01 香港居民身份证, 02 护照
     */
    private String idType;

    /**
     * 证件号码
     */
    private String idCode;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 电子邮件
     */
    private String email;

    /**
     * 账户类型
     */
    private String acctType;

    /**
     * 入金方式 1大陆 0香港 2其他
     */
    private Integer depositType;

    /**
     * 汇款银行
     */
    private String depositBank;

    /**
     * 汇款账号
     */
    private String depositNo;

    /**
     * 汇款账户名称
     */
    private String depositAccount;

    /**
     * 汇款银行代码
     */
    private String depositBankCode;

    /**
     * 汇款银行Id
     */
    private String depositBankId;

    /**
     * 收款银行(CUBP 字典表中维护的收款银行代码)
     */
    private String benefitBank;

    /**
     * 收款银行code
     */
    private String benefitBankCode;

    /**
     * 收款账号
     */
    private String benefitNo;

    /**
     * 收款账户名称
     */
    private String benefitAccount;

    /**
     * 申请金额
     */
    private BigDecimal depositBalance;

    /**
     * 申请时间
     */
    private Date applicationTime;

    /**
     * 币种 1港币 2美元 3人民币
     */
    private Integer moneyType;

    /**
     * 状态 0待处理 1处理中 2银行处理失败 3银行处理成功，4入账失败  5入账成功
     */
    private Integer bankState;

    /**
     * 入金上送状态[0-未上送 1-已上送]
     */
    private Integer sendStatus;

    /**
	 * 汇款方式
     */
    private Integer remittanceType;

    /**
     * swift_code
     */
    private String swiftCode;

    /**
     * 入金申请流水号
     */
    private String fundApplicationId;

    /**
     * 请求流水号
     */
    private String msgId;

    /**
     * edda申请编号
     */
    private String ddaRef;

    /**
     * 交易对账流水号
     */
    private String cusRef;

    /**
     * 请求时间
     */
    private Date reqTime;

    /**
     * 银行流水号
     */
    private String txnRefId;

    /**
     * 银行到账金额
     */
	@JsonSerialize(nullsUsing = NullSerializer.class)
    private BigDecimal settlementAmt;

    /**
     * 银行到账日期
     */
    private Date settlementDt;

    /**
     * 拒绝代码
     */
    private String rejCorde;

    /**
     * 拒绝原因
     */
    private String rejDescription;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 更新用户
     */
    private String updateUser;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

	/**
	 * 银行授权id
	 */
    private Long eddaInfoId;




}
