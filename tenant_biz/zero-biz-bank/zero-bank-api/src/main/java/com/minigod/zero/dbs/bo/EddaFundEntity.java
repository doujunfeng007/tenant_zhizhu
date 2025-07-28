package com.minigod.zero.dbs.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * DBS edda入金流水表
 *
 * @author sunline
 * @email aljqiang@163.com
 * @date 2020-06-17 10:55:06
 */
@Getter
@Setter
public class EddaFundEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//自增ID
	private Long id;
	//流水号
	private String applicationId;
	//交易帐号
	private String clientId;
	//资金帐号
	private String fundAccount;
	//入金方式 1大陆 0香港 2其他
	private Integer depositType;
	//汇款银行
	private String depositBank;
	//汇款账号
	private String depositNo;
	//汇款账户名称
	private String depositAccount;
	//汇款银行代码
	private String depositBankCode;
	//汇款银行Id
	private String depositBankId;
	//收款银行(bpm 字典表中维护的收款银行代码)
	private String benefitBank;
	//收款银行core
	private String benefitBankCode;
	//收款账号
	private String benefitNo;
	//收款账户名称
	private String benefitAccount;
	//申请金额
	private BigDecimal depositBalance;
	//申请时间
	private Date applicationTime;
	//币种代码[0-人民币 1-美元 2-港币]
	private String moneyType;
	//银行状态 0未提交 1处理中 2失败 3成功
	private Integer bankState;
	//入金上送状态[0-未上送 1-已上送]
	private Integer sendStatus;
	//入金申请流水号
	private String fundApplicationId;
	//请求流水号
	private String msgId;
	//交易对账流水号
	private String cusRef;
	//申请编号
	private String ddaRef;
	//请求时间
	private Date reqTime;
	//银行流水号
	private String txnRefId;
	//银行到账金额
	private BigDecimal settlementAmt;
	//银行到账日期
	private Date settlementDt;
	//拒绝代码
	private String rejCorde;
	//拒绝原因
	private String rejDescription;
	//创建用户
	private String createUser;
	//更新用户
	private String updateUser;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

	//用户号
	private String userId;
	//客户姓名
	private String clientName;
	//英文名
	private String clientNameSpell;
	//手机号码
	private String phoneNumber;
	//证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
	private Integer idkind;
	//证件号码
	private String idNo;
	//性别
	private Integer sex;
	//申请开始结束日期
	private String beginTime;
	private String endTime;
	//渠道编号
	private String sourceChannelId;
	//开户途径
	private String openAccountType;
	//开户时间
	private Date openAccountTime;
	//是否首次入金
	private String firstDepFlag;
	//入账时间
	private Date entryTime;
	//恒生处理状态[0-未知 1-处理成功 2-处理失败]
	private Integer hsDealStatus;

}
