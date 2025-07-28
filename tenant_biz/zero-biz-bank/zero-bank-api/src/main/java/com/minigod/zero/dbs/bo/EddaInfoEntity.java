package com.minigod.zero.dbs.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * DBS edda申请表
 *
 * @author sunline
 * @email aljqiang@163.com
 * @date 2020-06-09 22:50:38
 */
@Getter
@Setter
public class EddaInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//自增ID
	private Long id;
	//流水号
	private String applicationId;
	//交易帐号
	private String clientId;
	//资金帐号
	private String fundAccount;
	//银行 1大陆 0香港 2其他
	private Integer bankType;
	//银行名称
	private String bankName;
	//银行代码
	private String bankCode;
	//银行bankid
	private String bankId;
	//存入银行账户
	private String depositAccount;
	//存入账户名称
	private String depositAccountName;
	//存入账户类型:1 港币账户; 2 综合多币种账户
	private Integer depositAccountType;
	//银行开户证件类型:1 中华人民共和国居民身份证, 2 中华人民共和国往来港澳通行证, 3 香港居民身份证, 4 护照
	private Integer bankIdKind;
	//银行开户证件号码
	private String bankIdNo;
	//收款银行(bpm 字典表中维护的收款银行代码)
	private String benefitBank;
	//收款银行core
	private String benefitBankCode;
	//收款账号
	private String benefitNo;
	//收款账户名称
	private String benefitAccount;
	//状态 0未授权 1授权中 2授权失败 3授权成功 4解除授权
	private Integer eddaState;
	//请求流水号
	private String msgId;
	//申请编号
	private String ddaRef;
	//edda请求时间
	private Date reqTime;
	//事务id
	private String txnRefId;
	//授权ID
	private String mandateId;
	//币种
	private String amtCcy;
	//拒绝代码
	private String rejCorde;
	//拒绝原因
	private String rejDescription;
	//授权生效日
	private Date effDate;
	//授权到期日
	private Date expDate;
	//单笔限额
	private BigDecimal maxAmt;
	//数据撤销时间
	private Date deleteTime;
	//数据撤销用户
	private String deleteUser;
	//备注
	private String remark;
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
	//操作撤销人员名称
	private String deleteDealName;

	//DBS回调更新原状态 0未提交 1授权中 2已授权 3授权失败 4 撤销授权
	private Integer oldEddaState;

}
