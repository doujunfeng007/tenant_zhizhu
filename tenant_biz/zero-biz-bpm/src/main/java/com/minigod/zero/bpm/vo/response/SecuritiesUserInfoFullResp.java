package com.minigod.zero.bpm.vo.response;

import com.minigod.zero.bpm.vo.OpenAccountOtherDisclosureProtocol;
import com.minigod.zero.bpm.vo.OpenAccountPropertyTypeProtocol;
import com.minigod.zero.bpm.vo.OpenAccountTaxationInfoProtocol;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SecuritiesUserInfoFullResp implements Serializable {

	private Integer id;
	//预约流水号
	private String applicationId;
	//开户类型[0=未知 1=互联网 2=线下开户 3=BPM]
	private Integer openAccountType;
	//开户接入方式[1=H5开户 2=APP开户]
	private Integer openAccountAccessWay;
	//开户时间
	private Date openAccountTime;
	//智珠号
	private Long userId;
	//开户邀请人智珠号
	private String inviterId;
	//交易账号
	private String tradeAccount;
	//资金账号
	private String fundAccount;
	//客户状态[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]
	private String clientStatus;
	//账户类型[0=现金账户 M=融资账户]
	private String fundAccountType;
	//渠道ID
	private String sourceChannelId;
	//客户中文名
	private String clientName;
	//姓氏
	private String familyName;
	//名字
	private String givenName;
	private String familyNameSpell;
	private String givenNameSpell;
	//中文名拼音
	private String clientNameSpell;
	//证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
	private Integer idKind;
	//证件号码
	private String idNo;
	//性别[0=男 1=女 2=其它]
	private Integer sex;
	//生日日期
	private String birthday;
	//证件地址
	private String idCardAddress;
	//身份证生效日期
	private String idCardValidDateStart;
	//身份证失效日期
	private String idCardValidDateEnd;
	//身份相似百分数
	private Double identitySimilarityPercent;
	//是否通过身份验证[0=否 1=是]
	private Integer isPassIdentityAuthentication;
	//银行账户类型[0-香港账户 1-非香港帐号]
	private Integer bankType;
	//银行编号
	private String bankId;
	//银行卡号
	private String bankNo;
	//银行账户名
	private String bankAccountName;
	//其他银行名称
	private String otherBankName;
	//国籍
	private String nationality;
	//是否美国绿卡持有人[0=否 1=是]
	private Integer isAmericanGreenCardHolder;
	private String familyProvinceName;
	private String familyCityName;
	private String familyCountyName;
	//联系地址的省份
	private String contactProvinceName;
	//联系地址的城市
	private String contactCityName;
	//联系地址的区域
	private String contactCountyName;
	//联系地址的详细地址
	private String contactDetailAddress;
	//住宅地址
	private String familyAddress;
	//通讯地址
	private String contactAddress;
	//电子邮箱
	private String email;
	//手机号码
	private String phoneNumber;
	//就业情况类型[0-未知 1=受雇 2=自营/个体户 3=退休 4=学生 5=其他 6 =农林牧副渔 7=待业 8=自由职业者 9-投资者 10-家庭主妇]
	private Integer professionCode;
	//就业情况其它说明
	private String otherProfession;
	//职业类型[数据字典-AO_OCCUPATION_TYPE]
	private Integer professionType;
	//公司名称
	private String companyName;
	//公司地址
	private String companyAddress;
	//公司电话
	private String companyPhoneNumber;
	//职位级别[1-普通员工 2-中层管理 3-高层管理]
	private String jobPosition;
	//公司业务性质或行业类型
	private String industryRange;
	//资金来源[0-工资和奖金 1-投资回报 2-劳务报酬 3-租金收入 4-营业收入 5-退休金 6-家人给予 7-兼职收入 8-生产收入]
	private String capitalSource;
	//年收入范围类型[1=<20万 2=20万-50万 3=50万-100万 4=100万-500万 5=>500万]
	private Integer annualIncome;
	//净资产范围类型[1=<50万 2=50万-250万 3=250万-500万 4=>500万]
	private Integer netCapital;
	//投资目标类型[1=股息收入 2=短期投资 3=长期投资 4=其他]
	private String investTarget;
	//投资目标其它类型说明
	private String investTargetOther;
	//股票投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]
	private Integer stocksInvestmentExperienceType;
	//认证股权投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]
	private Integer warrantsInvestmentExperienceType;
	//期货投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]
	private Integer futuresInvestmentExperienceType;

	private Integer fundInvestmentExperienceType;

	// 财富来源
	private String wealthSource;

	//是否了解衍生产品[0=否 1=是]
	private Integer isKnowDerivativeProducts;
	//衍生产品学习途径[0=未知 1=金融机构 2=监管机构 3=交易所 4=大专院校 5=进修学院 6=线上课程 7=其它]
	private Integer derivativeProductsStudyType;
	//衍生产品其他学习途径
	private String derivativeProductsStudyTypeOther;
	//在金融机构工作经验类型[0=未知 1=受监管持牌人士 2=与衍生工具相关后勤 3=管理层 4=其它]
	private Integer financingInstitutionWorkExperienceType;
	//在金融机构其它工作经验类型
	private String financingInstitutionWorkExperienceTypeOther;
	//是否在过去三年曾买卖过至少五次任何衍生产品的交易[0=否 1=是]
	private Integer isTradedDerivativeProducts;
	//是否开通美股市场[0=否 1=是]
	private Integer isOpenUsaStockMarket;
	//是否开通港股市场[0=否 1=是]
	private Integer isOpenHkStockMarket;
	//是否允许衍生品交易[0=否 1=是]
	private Integer isAllowDerivativesTransaction;
	//您是否确认个人资料（私隐）条例通知并同意yff证券及其控股集团使用及向其他人士提供本人的个人资料作直接促销用途[0=否 1=是]
	private Integer isAllowProvidePrivacy;
	//有无AML可疑信息[0=无可疑 1=有可疑]
	private Integer isAmlSuspicious;
	//记录状态[0-无效 1-有效]
	private Integer recordStatus;
	//创建时间
	private Date createTime;
	//更新时间
	private Date updateTime;

	// 开户开始时间
	private Date openAccountStartTime;
	// 开户结束时间
	private Date openAccountEndTime;
	// 记录数
	private Integer count;
	// 账户等级[0-未知 1-预批账户 2-非标准账户 3-标准账户]
	private Integer accountLevel;

	private String employYears;

	/**
	 * BCAN码
	 *
	 * @return
	 */
	private String bcanNo;

	/**
	 * BCAN状态
	 *
	 * @return
	 */
	private String bcanStatus;

	// 财产类型
	private List<OpenAccountPropertyTypeProtocol> propertyType;

	//自由职业细化选项 [字典=AO_FREELANCE_CODE]
	private Integer freelanceCode;

	//自由职业细化其他说明
	private String freelanceOther;

	// 税务信息
	private List<OpenAccountTaxationInfoProtocol> taxationInfo;

	//FATCA声明（[0=否 1=是]）必须二选一；编辑资料改成“是”时出现弹窗，保留继续“否”的选择
	private Integer fatca;

	//其他信息披露
	private List<OpenAccountOtherDisclosureProtocol> otherDisclosure;

	private Integer northTrade;

	private String issueCountry;
}
