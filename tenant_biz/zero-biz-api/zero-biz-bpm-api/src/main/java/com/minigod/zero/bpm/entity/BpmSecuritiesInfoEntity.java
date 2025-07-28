package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.lang.Double;
import java.math.BigDecimal;
import java.io.Serializable;

/**
 * 证券客户资料表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("bpm_securities_info")
@ApiModel(value = "BpmSecuritiesInfo对象", description = "证券客户资料表")
public class BpmSecuritiesInfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@ApiModelProperty(value = "主键ID")
	private Integer id;
	/**
	 * 预约流水号
	 */
	@ApiModelProperty(value = "预约流水号")
	private String appointmentId;
	/**
	 * 开户类型[0=未知 1=互联网 2=线下开户]
	 */
	@ApiModelProperty(value = "开户类型[0=未知 1=互联网 2=线下开户]")
	private Integer openAccountType;
	/**
	 * 开户接入方式[1=H5开户 2=APP开户]
	 */
	@ApiModelProperty(value = "开户接入方式[1=H5开户 2=APP开户]")
	private Integer openAccountAccessWay;
	/**
	 * 开户时间
	 */
	@ApiModelProperty(value = "开户时间")
	private Date openAccountTime;
	/**
	 * 用户号
	 */
	@ApiModelProperty(value = "用户号")
	private Long custId;
	/**
	 * 开户邀请人用户号
	 */
	@ApiModelProperty(value = "开户邀请人用户号")
	private String inviterId;
	/**
	 * 客户状态[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]
	 */
	@ApiModelProperty(value = "客户状态[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]")
	private String clientStatus;
	/**
	 * 机构标志[0-个人 1、4-机构 2-自营]
	 */
	@ApiModelProperty(value = "机构标志[0-个人 1、4-机构 2-自营]")
	private String organFlag;
	/**
	 * 渠道ID
	 */
	@ApiModelProperty(value = "渠道ID")
	private String sourceChannelId;
	/**
	 * 客户中文名
	 */
	@ApiModelProperty(value = "客户中文名")
	private String custName;
	/**
	 * 姓氏
	 */
	@ApiModelProperty(value = "姓氏")
	private String familyName;
	/**
	 * 名字
	 */
	@ApiModelProperty(value = "名字")
	private String givenName;
	/**
	 * 中文名拼音
	 */
	@ApiModelProperty(value = "中文名拼音")
	private String custNameSpell;
	/**
	 * 证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
	 */
	@ApiModelProperty(value = "证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]")
	private String idKind;
	/**
	 * 证件号码
	 */
	@ApiModelProperty(value = "证件号码")
	private String idCard;
	/**
	 * 性别[0=男 1=女 2=其它]
	 */
	@ApiModelProperty(value = "性别[0=男 1=女 2=其它]")
	private Integer gender;
	/**
	 * 生日日期
	 */
	@ApiModelProperty(value = "生日日期")
	private String birthday;
	/**
	 * 证件地址
	 */
	@ApiModelProperty(value = "证件地址")
	private String idCardAddress;
	/**
	 * 身份证生效日期
	 */
	@ApiModelProperty(value = "身份证生效日期")
	private String idCardValidDateStart;
	/**
	 * 身份证失效日期
	 */
	@ApiModelProperty(value = "身份证失效日期")
	private String idCardValidDateEnd;
	/**
	 * 身份相似百分数
	 */
	@ApiModelProperty(value = "身份相似百分数")
	private Double identitySimilarityPercent;
	/**
	 * 是否通过身份验证[0=否 1=是]
	 */
	@ApiModelProperty(value = "是否通过身份验证[0=否 1=是]")
	private Integer isPassIdentityAuthentication;
	/**
	 * 银行账户类型[0-香港账户 1-非香港帐号]
	 */
	@ApiModelProperty(value = "银行账户类型[0-香港账户 1-非香港帐号]")
	private Integer bankType;
	/**
	 * 银行编号
	 */
	@ApiModelProperty(value = "银行编号")
	private String bankId;
	/**
	 * 银行卡号
	 */
	@ApiModelProperty(value = "银行卡号")
	private String bankNo;
	/**
	 * 银行账户名
	 */
	@ApiModelProperty(value = "银行账户名")
	private String bankAccountName;
	/**
	 * 其他银行名称
	 */
	@ApiModelProperty(value = "其他银行名称")
	private String otherBankName;
	/**
	 * 国籍
	 */
	@ApiModelProperty(value = "国籍")
	private String nationality;
	/**
	 * 是否美国绿卡持有人[0=否 1=是]
	 */
	@ApiModelProperty(value = "是否美国绿卡持有人[0=否 1=是]")
	private Integer isAmericanGreenCardHolder;
	/**
	 * 联系地址的省份
	 */
	@ApiModelProperty(value = "联系地址的省份")
	private String contactProvinceName;
	/**
	 * 联系地址的城市
	 */
	@ApiModelProperty(value = "联系地址的城市")
	private String contactCityName;
	/**
	 * 联系地址的区域
	 */
	@ApiModelProperty(value = "联系地址的区域")
	private String contactCountyName;
	/**
	 * 联系地址的详细地址
	 */
	@ApiModelProperty(value = "联系地址的详细地址")
	private String contactDetailAddress;
	/**
	 * 住宅地址
	 */
	@ApiModelProperty(value = "住宅地址")
	private String familyAddress;
	/**
	 * 通讯地址
	 */
	@ApiModelProperty(value = "通讯地址")
	private String contactAddress;
	/**
	 * 电子邮箱
	 */
	@ApiModelProperty(value = "电子邮箱")
	private String email;
	/**
	 * 手机区号
	 */
	@ApiModelProperty(value = "手机号码")
	private String phoneArea;
	/**
	 * 手机号码
	 */
	@ApiModelProperty(value = "手机号码")
	private String phoneNumber;
	/**
	 * 就业情况类型[0-未知 1=受雇 2=自营/个体户 3=退休 4=学生 5=其他 6 =农林牧副渔 7=待业 8=自由职业者 9-投资者 10-家庭主妇]
	 */
	@ApiModelProperty(value = "就业情况类型[0-未知 1=受雇 2=自营/个体户 3=退休 4=学生 5=其他 6 =农林牧副渔 7=待业 8=自由职业者 9-投资者 10-家庭主妇]")
	private Integer professionCode;
	/**
	 * 就业情况其它说明
	 */
	@ApiModelProperty(value = "就业情况其它说明")
	private String otherProfession;
	/**
	 * 职业类型[数据字典-AO_OCCUPATION_TYPE]
	 */
	@ApiModelProperty(value = "职业类型[数据字典-AO_OCCUPATION_TYPE]")
	private Integer professionType;
	/**
	 * 公司名称
	 */
	@ApiModelProperty(value = "公司名称")
	private String companyName;
	/**
	 * 公司地址
	 */
	@ApiModelProperty(value = "公司地址")
	private String companyAddress;
	/**
	 * 公司电话
	 */
	@ApiModelProperty(value = "公司电话")
	private String companyPhoneNumber;
	/**
	 * 职位级别[1-普通员工 2-中层管理 3-高层管理]
	 */
	@ApiModelProperty(value = "职位级别[1-普通员工 2-中层管理 3-高层管理]")
	private String jobPosition;
	/**
	 * 公司业务性质或行业类型
	 */
	@ApiModelProperty(value = "公司业务性质或行业类型")
	private String industryRange;
	/**
	 * 资金来源[0-工资和奖金 1-投资回报 2-劳务报酬 3-租金收入 4-营业收入 5-退休金 6-家人给予 7-兼职收入 8-生产收入]
	 */
	@ApiModelProperty(value = "资金来源[0-工资和奖金 1-投资回报 2-劳务报酬 3-租金收入 4-营业收入 5-退休金 6-家人给予 7-兼职收入 8-生产收入]")
	private String capitalSource;
	/**
	 * 年收入范围类型[1=500万]
	 */
	@ApiModelProperty(value = "年收入范围类型[1=500万]")
	private Integer annualIncome;
	/**
	 * 净资产范围类型[1=500万]
	 */
	@ApiModelProperty(value = "净资产范围类型[1=500万]")
	private Integer netCapital;
	/**
	 * 投资目标类型[1=股息收入 2=短期投资 3=长期投资 4=其他]
	 */
	@ApiModelProperty(value = "投资目标类型[1=股息收入 2=短期投资 3=长期投资 4=其他]")
	private String investTarget;
	/**
	 * 投资目标其它类型说明
	 */
	@ApiModelProperty(value = "投资目标其它类型说明")
	private String investTargetOther;
	/**
	 * 股票投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]
	 */
	@ApiModelProperty(value = "股票投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]")
	private Integer stocksInvestmentExperienceType;
	/**
	 * 认证股权投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]
	 */
	@ApiModelProperty(value = "认证股权投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]")
	private Integer warrantsInvestmentExperienceType;
	/**
	 * 期货投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]
	 */
	@ApiModelProperty(value = "期货投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]")
	private Integer futuresInvestmentExperienceType;
	/**
	 * 是否了解衍生产品[0=否 1=是]
	 */
	@ApiModelProperty(value = "是否了解衍生产品[0=否 1=是]")
	private Integer isKnowDerivativeProducts;
	/**
	 * 衍生产品学习途径[0=未知 1=金融机构 2=监管机构 3=交易所 4=大专院校 5=进修学院 6=线上课程 7=其它]
	 */
	@ApiModelProperty(value = "衍生产品学习途径[0=未知 1=金融机构 2=监管机构 3=交易所 4=大专院校 5=进修学院 6=线上课程 7=其它]")
	private Integer derivativeProductsStudyType;
	/**
	 * 衍生产品其他学习途径
	 */
	@ApiModelProperty(value = "衍生产品其他学习途径")
	private String derivativeProductsStudyTypeOther;
	/**
	 * 在金融机构工作经验类型[0=未知 1=受监管持牌人士 2=与衍生工具相关后勤 3=管理层 4=其它]
	 */
	@ApiModelProperty(value = "在金融机构工作经验类型[0=未知 1=受监管持牌人士 2=与衍生工具相关后勤 3=管理层 4=其它]")
	private Integer financingInstitutionWorkExperienceType;
	/**
	 * 在金融机构其它工作经验类型
	 */
	@ApiModelProperty(value = "在金融机构其它工作经验类型")
	private String financingInstitutionWorkExperienceTypeOther;
	/**
	 * 是否在过去三年曾买卖过至少五次任何衍生产品的交易[0=否 1=是]
	 */
	@ApiModelProperty(value = "是否在过去三年曾买卖过至少五次任何衍生产品的交易[0=否 1=是]")
	private Integer isTradedDerivativeProducts;
	/**
	 * 是否开通美股市场[0=否 1=是]
	 */
	@ApiModelProperty(value = "是否开通美股市场[0=否 1=是]")
	private Integer isOpenUsaStockMarket;
	/**
	 * 是否开通港股市场[0=否 1=是]
	 */
	@ApiModelProperty(value = "是否开通港股市场[0=否 1=是]")
	private Integer isOpenHkStockMarket;
	/**
	 * 是否允许衍生品交易[0=否 1=是]
	 */
	@ApiModelProperty(value = "是否允许衍生品交易[0=否 1=是]")
	private Integer isAllowDerivativesTransaction;
	/**
	 * 您是否确认个人资料（私隐）条例通知并同意云锋证券及其控股集团使用及向其他人士提供本人的个人资料作直接促销用途[0=否 1=是]
	 */
	@ApiModelProperty(value = "您是否确认个人资料（私隐）条例通知并同意云锋证券及其控股集团使用及向其他人士提供本人的个人资料作直接促销用途[0=否 1=是]")
	private Integer isAllowProvidePrivacy;
	/**
	 * 有无AML可疑信息[0=无可疑 1=有可疑]
	 */
	@ApiModelProperty(value = "有无AML可疑信息[0=无可疑 1=有可疑]")
	private Integer isAmlSuspicious;
	/**
	 * 恒生地址ID
	 */
	@ApiModelProperty(value = "恒生地址ID")
	private String addressId;
	/**
	 * 注销日期
	 */
	@ApiModelProperty(value = "注销日期")
	private Date cancelDate;
	/**
	 * 记录状态[0-无效 1-有效]
	 */
	@ApiModelProperty(value = "记录状态[0-无效 1-有效]")
	private Integer recordStatus;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	/**
	 * 北向交易资料收集声明([0=否 1=是])
	 */
	@ApiModelProperty(value = "北向交易资料收集声明([0=否 1=是])")
	private Integer northTrade;
	/**
	 * FATCA声明([0=否 1=是])
	 */
	@ApiModelProperty(value = "FATCA声明([0=否 1=是])")
	private Integer fatca;
	/**
	 * 风险承受程度:[1=低风险 2=中风险 3=高风险]
	 */
	@ApiModelProperty(value = "风险承受程度:[1=低风险 2=中风险 3=高风险]")
	private Integer acceptRisk;
	/**
	 * 其它国家名称
	 */
	@ApiModelProperty(value = "其它国家名称")
	private String otherNationality;
	/**
	 * 通讯地址的国家
	 */
	@ApiModelProperty(value = "通讯地址的国家")
	private String contactRepublicName;
	/**
	 * 公司地址的国家
	 */
	@ApiModelProperty(value = "公司地址的国家")
	private String companyRepublicName;
	/**
	 * 公司地址的省份
	 */
	@ApiModelProperty(value = "公司地址的省份")
	private String companyProvinceName;
	/**
	 * 公司地址的城市
	 */
	@ApiModelProperty(value = "公司地址的城市")
	private String companyCityName;
	/**
	 * 公司地址的区域
	 */
	@ApiModelProperty(value = "公司地址的区域")
	private String companyCountyName;
	/**
	 * 公司地址的详细地址
	 */
	@ApiModelProperty(value = "公司地址的详细地址")
	private String companyDetailAddress;
	/**
	 * 住宅地址的国家
	 */
	@ApiModelProperty(value = "住宅地址的国家")
	private String familyRepublicName;
	/**
	 * 住宅地址的省份
	 */
	@ApiModelProperty(value = "住宅地址的省份")
	private String familyProvinceName;
	/**
	 * 住宅地址的城市
	 */
	@ApiModelProperty(value = "住宅地址的城市")
	private String familyCityName;
	/**
	 * 住宅地址的区域
	 */
	@ApiModelProperty(value = "住宅地址的区域")
	private String familyCountyName;
	/**
	 * 住宅地址的详细地址
	 */
	@ApiModelProperty(value = "住宅地址的详细地址")
	private String familyDetailAddress;
	/**
	 * 住宅地址的国家(其它填写内容)
	 */
	@ApiModelProperty(value = "住宅地址的国家(其它填写内容)")
	private String otherFamilyRepublic;
	/**
	 * 公司地址的国家(其它填写内容)
	 */
	@ApiModelProperty(value = "公司地址的国家(其它填写内容)")
	private String otherCompanyRepublic;
	/**
	 * 通讯地址的国家(其它填写内容)
	 */
	@ApiModelProperty(value = "通讯地址的国家(其它填写内容)")
	private String otherContactRepublic;
	/**
	 * 英文名字
	 */
	@ApiModelProperty(value = "英文名字")
	private String givenNameSpell;
	/**
	 * 英文姓氏
	 */
	@ApiModelProperty(value = "英文姓氏")
	private String familyNameSpell;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String theusTaxNumber;
	/**
	 * 大户室号[客户级别1-Common 2-Important 3-VIP 4-PI 5-Capital]
	 */
	@ApiModelProperty(value = "大户室号[客户级别1-Common 2-Important 3-VIP 4-PI 5-Capital]")
	private String roomCode;
	/**
	 * 自由职业细化选项[数据字典-AO_FREELANCE_CODE]
	 */
	@ApiModelProperty(value = "自由职业细化选项[数据字典-AO_FREELANCE_CODE]")
	private Integer freelanceCode;
	/**
	 * 自由职业细化其他说明
	 */
	@ApiModelProperty(value = "自由职业细化其他说明")
	private String freelanceOther;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String bcanNo;
	/**
	 *
	 */
	@ApiModelProperty(value = "")
	private String bcanStatus;
	/**
	 * 基金风险等级
	 */
	@ApiModelProperty(value = "基金风险等级")
	private String fundRiskRank;
	/**
	 * 信用额度（港币）
	 */
	@ApiModelProperty(value = "信用额度（港币）")
	private BigDecimal creditAmount;
	/**
	 * 信用比率（%）
	 */
	@ApiModelProperty(value = "信用比率（%）")
	private BigDecimal creditRatio;
	/**
	 * 信用额度过期日
	 */
	@ApiModelProperty(value = "信用额度过期日")
	private Date creditExpireDate;
	/**
	 * 通讯地址类型 [1-大陆地址 2-香港地址 3-其他地区]
	 */
	@ApiModelProperty(value = "通讯地址类型 [1-大陆地址 2-香港地址 3-其他地区]")
	private Integer addressType;
	/**
	 * 民族
	 */
	@ApiModelProperty(value = "民族")
	private String nation;
	/**
	 * 身份证签发机关
	 */
	@ApiModelProperty(value = "身份证签发机关")
	private String signingOrganization;
	/**
	 * 评测时间
	 */
	@ApiModelProperty(value = "评测时间")
	private Date reviewTime;
	/**
	 * RCA：0-否；1-是
	 */
	@ApiModelProperty(value = "RCA：0-否；1-是")
	private Long rcaReview;
	/**
	 * san：0-否；1-是
	 */
	@ApiModelProperty(value = "san：0-否；1-是")
	private Long sanReview;
	/**
	 * 国籍评级：3-低风险；2-中风险；1-高风险
	 */
	@ApiModelProperty(value = "国籍评级：3-低风险；2-中风险；1-高风险")
	private Long nationalityReview;
	/**
	 * 居住风险评级：3-低风险；2-中风险；1-高风险
	 */
	@ApiModelProperty(value = "居住风险评级：3-低风险；2-中风险；1-高风险")
	private Long addressReview;
	/**
	 * 国籍和居住组合评级：3-低风险；2-中风险；1-高风险
	 */
	@ApiModelProperty(value = "国籍和居住组合评级：3-低风险；2-中风险；1-高风险")
	private Long addressComposeReview;
	/**
	 * 职业评级：3-低风险；2-中风险；1-高风险
	 */
	@ApiModelProperty(value = "职业评级：3-低风险；2-中风险；1-高风险")
	private Long professionReview;
	/**
	 * pep：0-否；1-是
	 */
	@ApiModelProperty(value = "pep：0-否；1-是")
	private Long pepReview;
	/**
	 * 账户注销操作人
	 */
	@ApiModelProperty(value = "账户注销操作人")
	private String cancelOper;
	/**
	 * 证券交易费率
	 */
	@ApiModelProperty(value = "证券交易费率")
	private String tradeFee;
	/**
	 * 证券服务费率
	 */
	@ApiModelProperty(value = "证券服务费率")
	private String serviceFee;
	/**
	 * 基金投资经验类型[0=没有 1=少于一年 2=一至五年  3=五年以上]
	 */
	@ApiModelProperty(value = "基金投资经验类型[0=没有 1=少于一年 2=一至五年  3=五年以上]")
	private Integer fundInvestmentExperienceType;
	/**
	 * 财富来源
	 */
	@ApiModelProperty(value = "财富来源")
	private String wealthSource;
	/**
	 * 账户等级[0-未知 1-预批账户 2-非标准账户 3-标准账户]
	 */
	@ApiModelProperty(value = "账户等级[0-未知 1-预批账户 2-非标准账户 3-标准账户]")
	private Integer accountLevel;
	/**
	 * 客户类型[0-Individual 1-House s-ESOP 3-Corporate-Proprietary Trading 4-Corporate-Fund 5-Corporate-Fund manager&amp;others 6-Corporate 7-Staff]
	 */
	@ApiModelProperty(value = "客户类型[0-Individual 1-House s-ESOP 3-Corporate-Proprietary Trading 4-Corporate-Fund 5-Corporate-Fund manager&amp;others 6-Corporate 7-Staff]")
	private String clientType;
	/**
	 * 是否基金单边户(只有基金账户，没有股票账户)，0-否，1-是
	 */
	@ApiModelProperty(value = "是否基金单边户(只有基金账户，没有股票账户)，0-否，1-是")
	private Integer isFundUnilateralAccount;
	/**
	 * A.E.Code
	 */
	@ApiModelProperty(value = "A.E.Code")
	private String aecode;
	/**
	 * 证件签发地
	 */
	@ApiModelProperty(value = "证件签发地")
	private String issueCountry;
	/**
	 * 其他证件签发地
	 */
	@ApiModelProperty(value = "其他证件签发地")
	private String otherIssueCountry;
	/**
	 * 出生地
	 */
	@ApiModelProperty(value = "出生地")
	private String birthPlace;
	/**
	 * 其他出生地
	 */
	@ApiModelProperty(value = "其他出生地")
	private String otherBirthPlace;
	/**
	 * 从业年限
	 */
	@ApiModelProperty(value = "从业年限")
	private String employYears;

	/**
	 * 工银W8文件编号
	 */
	@ApiModelProperty(value = "工银W8文件编号")
	private String W8fileCode;

	/**
	 * 工银同步的证券账号
	 */
	@ApiModelProperty(value = "工银同步的证券账号")
	private String stockTradeAccount;

}
