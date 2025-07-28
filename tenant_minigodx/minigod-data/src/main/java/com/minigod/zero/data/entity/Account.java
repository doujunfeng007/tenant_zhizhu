package com.minigod.zero.data.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 账户实体类
 * 对应数据库表 t_account
 *
 * @author eric
 * @date 2024-10-28 16:06:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
	/**
	 * 账户ID (3位机构码+8位账户)
	 */
	private String accountId;

	/**
	 * 账户类型 0 个人户 1 机构户
	 */
	private Integer type;

	/**
	 * 账户业务类型 0 基金 64 活利得 65 债券易
	 */
	private Integer busiType;

	/**
	 * 电子邮箱
	 */
	private String email;

	/**
	 * 税收居民国家
	 */
	private String taxCountry;

	/**
	 * 税收信息修改日期
	 */
	private Date taxModifyDate;

	/**
	 * 税收编号
	 */
	private String taxNumber;

	/**
	 * 居住地址
	 */
	private String residentialAddress;

	/**
	 * 居住国家
	 */
	private String countryOfResidence;

	/**
	 * 电话（含区号）
	 */
	private String phone;

	/**
	 * 外部账户ID
	 */
	private String extAccountId;

	/**
	 * 外部账户类型（国信账户类型）
	 */
	private String extAccountType;

	/**
	 * 经纪编号
	 */
	private String aeCode;

	/**
	 * 经纪名称
	 */
	private String aeName;

	/**
	 * 账户状态 (active: 有效; inactive: 无效)
	 */
	private String status;

	/**
	 * 所属公司
	 */
	private String company;

	/**
	 * 受雇公司
	 */
	private String employedCompany;

	/**
	 * 职位
	 */
	private String position;

	/**
	 * 就业状态
	 */
	private Integer jobStatus;

	/**
	 * 通讯地址
	 */
	private String correspondenceAddress;

	/**
	 * 首选通讯方式
	 */
	private Integer preferredCommunication;

	/**
	 * 年收入
	 */
	private Integer annualIncome;

	/**
	 * 净资产
	 */
	private String netAsset;

	/**
	 * 投资目标
	 */
	private Integer investmentObjective;

	/**
	 * 教育程度
	 */
	private Integer education;

	/**
	 * PI标志
	 */
	private Integer piFlag;

	/**
	 * 保证金状态
	 */
	private Integer marginStatus;

	/**
	 * 收入来源
	 */
	private Integer sourceOfIncome;

	/**
	 * 收入来源备注
	 */
	private String sourceOfIncomeRemark;

	/**
	 * 账户持有人姓名
	 */
	private String accountHolderName;

	/**
	 * 银行账号
	 */
	private String bankAccountNumber;

	/**
	 * 风险评分
	 */
	private Integer riskScore;

	/**
	 * 风险评级
	 */
	private Integer riskLevel;

	/**
	 * 风险承受能力
	 */
	private Integer riskCapability;

	/**
	 * 电话国家代码
	 */
	private String phoneCountry;

	/**
	 * 证件类型 (1-身份证开户 2-护照开户)
	 */
	private Integer idType;

	/**
	 * 证件号码
	 */
	private String idNumber;

	/**
	 * 签发国家
	 */
	private String issueCountry;

	/**
	 * 证件有效期
	 */
	private Date idExpiryDate;

	/**
	 * 国籍
	 */
	private String nationality;

	/**
	 * 出生国家
	 */
	private String countryOfBirth;

	/**
	 * 性别
	 */
	private Integer gender;

	/**
	 * 出生日期
	 */
	private Date birthday;

	/**
	 * 客户ID
	 */
	private String custId;

	/**
	 * 中文姓名
	 */
	private String chineseName;

	/**
	 * 名字
	 */
	private String firstName;

	/**
	 * 姓氏
	 */
	private String lastName;

	/**
	 * 风险评分更新时间
	 */
	private Date riskUpdateTime;

	/**
	 * 交易权限 (1:可申购可赎回 0:不可申购不可赎回 2:可申购不可赎回 3:不可申购可赎回)
	 */
	private Integer tradePermission;

	/**
	 * 账户关闭时间
	 */
	private Date closedTime;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;
}
