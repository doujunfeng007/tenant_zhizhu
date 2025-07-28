package com.minigod.zero.customer.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.minigod.zero.core.tenant.mp.TenantEntity;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 客户基础资料信息表 实体类
 *
 * @author ken
 * @since 2024-04-11
 */
@Data
@TableName("customer_basic_info")
@ApiModel(value = "CustomerBasicInfo对象", description = "客户基础资料信息表")
@EqualsAndHashCode(callSuper = true)
public class CustomerBasicInfoEntity extends TenantEntity {
	private Long custId;
	/**
	 * 预约流水号
	 */
	private String applicationId;

	/**
	 * 开户类型[0=未知 1=互联网 2=线下开户 3=BPM]
	 */
	private Integer openAccountType;

	/**
	 * 开户接入方式: 1:H5开户 2:APP开户 3:线下开户
	 */
	private Integer openAccountAccessWay;

	/**
	 * 账户类型[1=现金账户 2=融资账户]
	 */
	private Integer fundAccountType;

	/**
	 * 开户客户来源渠道ID
	 */
	private String sourceChannelId;

	/**
	 * 用户ID
	 */
	private String userId;

	/**
	 * 开户邀请人的userId
	 */
	private String inviterId;

	/**
	 * 客户中文名
	 */
	private String clientName;

	/**
	 * 姓氏
	 */
	private String familyName;

	/**
	 * 名字
	 */
	private String givenName;

	/**
	 * 中文名拼音
	 */
	private String clientNameSpell;

	/**
	 * 证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
	 */
	private Integer idKind;

	/**
	 * 是否通过身份验证[0=否 1=是]
	 */
	private Integer isPassIdentityAuthentication;

	/**
	 * 账户币种0综合账户 1港币账户 2美元账户 3人民币账户 4其他账户【bank_currency】
	 */
	private Integer bankCurrency;

	/**
	 * 银行账户类型[0-香港账户 1-非香港帐号]
	 */
	private Integer bankType;

	/**
	 * 银行编号
	 */
	private String bankId;

	/**
	 * 银行卡号
	 */
	private String bankNo;

	/**
	 * 银行账户名
	 */
	private String bankAccountName;

	/**
	 * 其他银行名称
	 */
	private String otherBankName;

	/**
	 * 国籍
	 */
	private String nationality;

	/**
	 * 通讯地址的国家
	 */
	private String contactRepublicName;

	/**
	 * 联系地址的省份
	 */
	private String contactProvinceName;

	/**
	 * 联系地址的城市
	 */
	private String contactCityName;

	/**
	 * 联系地址的区域
	 */
	private String contactCountyName;

	/**
	 * 联系地址的详细地址
	 */
	private String contactDetailAddress;

	/**
	 * 住宅地址
	 */
	private String familyAddress;

	/**
	 * 通讯地址
	 */
	private String contactAddress;

	/**
	 * 电子邮箱
	 */
	private String email;

	/**
	 * 手机号
	 */
	private String phoneNumber;

	private String phoneArea;
	/**
	 * 就业情况类型[1-全职 2-在职 3-自雇 4-待业 5-其他]
	 */
	private Integer professionCode;

	/**
	 *
	 */
	private String companyName;

	/**
	 * 公司地址
	 */
	private String companyAddress;

	/**
	 * 公司电话
	 */
	private String companyPhoneNumber;

	/**
	 * 职位级别[1-普通员工 2-中层管理 3-高层管理]
	 */
	private String jobPosition;

	/**
	 * 财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 6-家人成员提供 7-其他]
	 */
	private String capitalSource;

	/**
	 * 年收入范围类型[1=<25万 2=25万-50万 3=50万-100万 4=>100万]
	 */
	private Integer annualIncome;

	/**
	 * 年收入具体金额
	 */
	private String annualIncomeOther;

	/**
	 * 在金融机构工作经验类型[0=未知 1=受监管持牌人士 2=与衍生工具相关后勤 3=管理层 4=其它]
	 */
	private Integer financingInstitutionWorkExperienceType;

	/**
	 * 在金融机构其它工作经验类型
	 */
	private String financingInstitutionWorkExperienceTypeOther;

	/**
	 * 是否在过去三年曾买卖过至少五次任何衍生产品的交易[0=否 1=是]
	 */
	private Integer isTradedDerivativeProducts;

	/**
	 * 是否开通美股市场[0=否 1=是]
	 */
	private Integer isOpenUsaStockMarket;

	/**
	 * 是否开通港股市场[0=否 1=是]
	 */
	private Integer isOpenHkStockMarket;

	/**
	 * 有无AML可疑信息[0=无可疑 1=有可疑]
	 */
	private Integer isAmlSuspicious;

	/**
	 * 账户等级[0-未知 1-预批账户 2-非标准账户 3-标准账户]
	 */
	private Integer accountLevel;

	/**
	 * 初始密码
	 */
	private String initialAccountPassword;

	/**
	 * 开户时间
	 */
	private Date openAccountTime;

	/**
	 * 申请时间（用户实际提交申请的时间）
	 */
	private Date applicationTime;

	/**
	 * 身份证签发机关
	 */
	private String signingOrganization;

	/**
	 * 通讯地址类型 [1-大陆地址 2-香港地址 3-其他地区]
	 */
	private Integer addressType;

	/**
	 * CA签署码
	 */
	private String caSignHashCode;

	/**
	 * 民族
	 */
	private String nation;

	/**
	 * 记录状态[0-无效 1-有效]
	 */
	private Integer recordStatus;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 北向交易资料收集声明([0=否 1=是])
	 */
	private Integer northTrade;

	/**
	 * FATCA声明([0=否 1=是])
	 */
	private Integer fatca;

	/**
	 * 风险承受程度:[3=低风险 2=中风险 1=高风险]
	 */
	private Integer acceptRisk;

	/**
	 * 其它国家名称
	 */
	private String otherNationality;

	/**
	 * 公司地址的国家
	 */
	private String companyRepublicName;

	/**
	 * 公司地址的省份
	 */
	private String companyProvinceName;

	/**
	 * 公司地址的城市
	 */
	private String companyCityName;

	/**
	 * 公司地址的区域
	 */
	private String companyCountyName;

	/**
	 * 公司地址的详细地址
	 */
	private String companyDetailAddress;

	/**
	 * 住宅地址的国家
	 */
	private String familyRepublicName;

	/**
	 * 住宅地址的省份
	 */
	private String familyProvinceName;

	/**
	 * 住宅地址的城市
	 */
	private String familyCityName;

	/**
	 * 住宅地址的区域
	 */
	private String familyCountyName;

	/**
	 * 住宅地址的详细地址
	 */
	private String familyDetailAddress;

	/**
	 * 住宅地址的国家(其它填写内容)
	 */
	private String otherFamilyRepublic;

	/**
	 * 公司地址的国家(其它填写内容)
	 */
	private String otherCompanyRepublic;

	/**
	 * 通讯地址的国家(其它填写内容)
	 */
	private String otherContactRepublic;

	/**
	 * 美国纳税人识别号码
	 */
	private String theusTaxNumber;

	/**
	 * 英文名字
	 */
	private String givenNameSpell;

	/**
	 * 英文姓氏
	 */
	private String familyNameSpell;

	/**
	 * 0=未知 1=英文 2=繁体中文 3=简体中文
	 */
	private Integer lan;

	/**
	 * 账户类型[0、未知  1、个人账户  2、联名账户   3、公司账户]
	 */
	private Integer accountType;

	/**
	 * 住所电话
	 */
	private String familyPhone;

	/**
	 * 教育程度[1、小学或以下   2、中学   3、大学或以上]
	 */
	private Integer educationLevel;

	/**
	 * 从业年限[0、未知  1、1-2年   2、2-5年   3、5-10年   4、>10年]
	 */
	private Integer workingSeniority;

	/**
	 * 其它职业（输入）
	 */
	private String otherProfession;

	/**
	 * 你是否曾经破产或被送达要将你破产的申请[0、否   1是]
	 */
	private Integer isBankrupted;

	/**
	 * 日结单及月结单发送方式[0、未知  1、电子邮箱  2、邮寄到住宅地址  3、邮寄到营业地址]
	 */
	private Integer statementReceiveMode;

	/**
	 * 其它投资产品交易频率次/年 [0、未知 1、<10  2、10-40   3、 >40]
	 */
	private Integer tradeOtherProductsFrequency;

	/**
	 * 通讯电话
	 */
	private String contactPhone;

	/**
	 * 证券交易账号
	 */
	private String stockTradeAccount;

	/**
	 * 投资年期 [0-未知 1、<1年  2、1-3年  3、3年以上]
	 */
	private Integer investmentHorizon;

	/**
	 * 信用额度
	 */
	private String creditQuota;

	/**
	 * 信用比例
	 */
	private String creditRatio;

	/**
	 * 最初资金其它收入来源描述
	 */
	private String capitalSourceOther;

	/**
	 * 出生地区
	 */
	private String placeOfBirth;

	/**
	 * 永久居住地址
	 */
	private String permanentAddress;

	/**
	 * 永久居住地址的国家
	 */
	private String permanentRepublicName;

	/**
	 * 永久居住地址的国家(其它填写内容)
	 */
	private String otherPermanentRepublic;

	/**
	 * 持续财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 6-家人成员提供 0-其他]
	 */
	private String ongoingCapitalSource;

	/**
	 * 预期财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 0-其他]
	 */
	private String expectedCapitalSource;

	/**
	 * 持续资金其它收入来源
	 */
	private String ongoingCapitalSourceOther;

	/**
	 * 预期资金其它收入来源
	 */
	private String expectedCapitalSourceOther;

	/**
	 * 使用/交易频率[1-每日 2-每周 3-每月 4-每季 5-每半年 6-每年 0-其他]
	 */
	private Integer tradeFrequency;

	/**
	 * 其他使用/交易频率说明
	 */
	private String tradeFrequencyOther;

	/**
	 * 是否接受相关风险[0-否 1-是]
	 */
	private Integer isAcceptRisksAssociated;

	/**
	 * 银行地址
	 */
	private String bankAddress;

	/**
	 * 是否公司客户
	 */
	private Integer isCompanyAccount;

	/**
	 * 公司客户Id
	 */
	private String companyClientId;

	/**
	 * 税务居民[0-其他 1-香港]
	 */
	private String taxResident;

	/**
	 *
	 */
	private Integer bcan;

	/**
	 * 创建人
	 */
	private Long createUser;

	/**
	 * 修改人
	 */
	private Long updateUser;

	/**
	 * 是否删除
	 */
	private Integer isDeleted;

	/**
	 * 反对资料促销用途 0否 1是
	 */
	private Integer againstDataPromotion;

	/**
	 * 反对资料向集团用促销用途  0否 1是
	 */
	private Integer againstDataPromotionToCompany;

	/**
	 * 曾用名
	 */
	private String formerName;

	/**
	 * 称谓[appellation]
	 */
	private Integer appellation;

	/**
	 * 婚姻[marital_status]
	 */
	private Integer maritalStatus;

	/**
	 * 公司邮箱
	 */
	private String companyEmail;

	/**
	 * 公司传真
	 */
	private String companyFacsimile;

	/**
	 * 公司业务性质
	 */
	private Integer companyBusinessNature;

	/**
	 * 净资产
	 */
	private Integer netAsset;

	/**
	 * 净资产具体金额
	 */
	private String netAssetOther;

	/**
	 * 每月交易金额
	 */
	private Integer tradeAmount;

	/**
	 * 每月交易具体金额
	 */
	private String tradeAmountOther;

	/**
	 * 邀请码
	 */
	private String aeCode;

	/**
	 * 永久城市
	 */
	private String permanentCityName;

	/**
	 * 永久区域
	 */
	private String permanentCountyName;

	/**
	 * 永久详情
	 */
	private String permanentDetailAddress;

	/**
	 * 永久省份
	 */
	private String permanentProvinceName;

	/**
	 * 证件城市
	 */
	private String idCardCityName;

	/**
	 * 证件区域
	 */
	private String idCardCountyName;

	/**
	 * 证件省份
	 */
	private String idCardProvinceName;

	/**
	 * 证件详情
	 */
	private String idCardDetailAddress;

	/**
	 * 租户 ID
	 */
	private String tenantId;

	/**
	 * 创建科室
	 */
	private Long createDept;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 *
	 */
	private String jobPositionOther;

	/**
	 *
	 */
	private String companyBusinessNatureOther;

	private static final long serialVersionUID = 1L;

}
