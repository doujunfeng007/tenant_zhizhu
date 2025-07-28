package com.minigod.zero.bpmn.module.edda.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.NullSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * DBS edda授权申请表
 * @TableName client_edda_info_application
 */
@TableName(value ="client_edda_info_application")
@Data
public class ClientEddaInfoApplicationEntity implements Serializable {

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
     * 电话号码
     */
    private String mobile;

    /**
     * 证件号码
     */
    private String idCode;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 银行 1.大陆 2.香港 3.其他
     */
    private Integer bankType;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行代码
     */
    private String bankCode;

    /**
     * 银行bankid
     */
    private String bankId;

    /**
     * 存入银行账户
     */
    private String depositAccount;

    /**
     * 存入账户名称 要英文名
     */
    private String depositAccountName;

    /**
     * {@link com.minigod.zero.biz.common.enums.BankAccountType}
     */
    private Integer depositAccountType;

    /**
     * {@link com.minigod.zero.biz.common.enums.IdKindType}
     */
    private Integer bankIdKind;

    /**
     * 银行开户证件号码
     */
    private String bankIdNo;

    /**
     * 收款银行名称
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
     * 数据状态 0 用户自己删除 1正常 2更新删除
     */
    private Integer dataState;

    /**
     * {@link com.minigod.zero.biz.common.enums.EddaAuthStatus}
     */
    private Integer eddaState;

    /**
     * 请求流水号
     */
    private String msgId;

    /**
     * 申请编号
     */
    private String ddaRef;

    /**
     * edda请求时间
     */
    private Date reqTime;

    /**
     * 事务id
     */
    private String txnRefId;

    /**
     * 授权ID
     */
    private String mandateId;

    /**
     * 币种
     */
    private String amtCcy;

    /**
     * 拒绝代码
     */
    private String rejCorde;

    /**
     * 拒绝原因
     */
    private String rejDescription;

    /**
     * 授权生效日
     */
    private Date effDate;

    /**
     * 授权到期日
     */
    private Date expDate;

    /**
     * 单笔限额
     */
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private BigDecimal maxAmt;

    /**
     * 数据撤销时间
     */
    private Date deleteTime;

    /**
     * 数据撤销用户
     */
    private String deleteUser;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建用户
     */
    private String createUser;

    /**
     * 更新用户
     */
    private String updateUser;

    /**
     * 删除时间
     */
    private Date deleTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     *
     */
    private String birthday;

    /**
     *
     */
    private Integer bankIdKindStatus;

	/**
	 * 申请时间
	 */
	private Date applicationTime;

	//银行app端图标
	private String appIcon;
	//入金银行配置id
	private Long secDepositBankId;
	//银行分行值
	private String bankIdQuick;
	//手续费
	@JsonSerialize(nullsUsing = NullSerializer.class)
	private BigDecimal chargeMoney ;



}
