package com.minigod.zero.data.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户开户详细资料表
 * @TableName customer_basic_info
 */
public class CustomerBasicInfoEntity implements Serializable {
    /**
     * 自增ID
     */
    private Long id;

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
     *
     */
    private String jobPositionOther;

    /**
     * 状态
     */
    private Integer status;

    /**
     *
     */
    private String companyBusinessNatureOther;

    /**
     * 手机号
     */
    private String phoneNumber;

    /**
     * 区号
     */
    private String phoneArea;

    /**
     *
     */
    private Long custId;

    private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 预约流水号
     */
    public String getApplicationId() {
        return applicationId;
    }

    /**
     * 预约流水号
     */
    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * 开户类型[0=未知 1=互联网 2=线下开户 3=BPM]
     */
    public Integer getOpenAccountType() {
        return openAccountType;
    }

    /**
     * 开户类型[0=未知 1=互联网 2=线下开户 3=BPM]
     */
    public void setOpenAccountType(Integer openAccountType) {
        this.openAccountType = openAccountType;
    }

    /**
     * 开户接入方式: 1:H5开户 2:APP开户 3:线下开户
     */
    public Integer getOpenAccountAccessWay() {
        return openAccountAccessWay;
    }

    /**
     * 开户接入方式: 1:H5开户 2:APP开户 3:线下开户
     */
    public void setOpenAccountAccessWay(Integer openAccountAccessWay) {
        this.openAccountAccessWay = openAccountAccessWay;
    }

    /**
     * 账户类型[1=现金账户 2=融资账户]
     */
    public Integer getFundAccountType() {
        return fundAccountType;
    }

    /**
     * 账户类型[1=现金账户 2=融资账户]
     */
    public void setFundAccountType(Integer fundAccountType) {
        this.fundAccountType = fundAccountType;
    }

    /**
     * 开户客户来源渠道ID
     */
    public String getSourceChannelId() {
        return sourceChannelId;
    }

    /**
     * 开户客户来源渠道ID
     */
    public void setSourceChannelId(String sourceChannelId) {
        this.sourceChannelId = sourceChannelId;
    }

    /**
     * 开户邀请人的userId
     */
    public String getInviterId() {
        return inviterId;
    }

    /**
     * 开户邀请人的userId
     */
    public void setInviterId(String inviterId) {
        this.inviterId = inviterId;
    }

    /**
     * 客户中文名
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * 客户中文名
     */
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    /**
     * 姓氏
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * 姓氏
     */
    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    /**
     * 名字
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * 名字
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * 中文名拼音
     */
    public String getClientNameSpell() {
        return clientNameSpell;
    }

    /**
     * 中文名拼音
     */
    public void setClientNameSpell(String clientNameSpell) {
        this.clientNameSpell = clientNameSpell;
    }

    /**
     * 证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
     */
    public Integer getIdKind() {
        return idKind;
    }

    /**
     * 证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
     */
    public void setIdKind(Integer idKind) {
        this.idKind = idKind;
    }

    /**
     * 是否通过身份验证[0=否 1=是]
     */
    public Integer getIsPassIdentityAuthentication() {
        return isPassIdentityAuthentication;
    }

    /**
     * 是否通过身份验证[0=否 1=是]
     */
    public void setIsPassIdentityAuthentication(Integer isPassIdentityAuthentication) {
        this.isPassIdentityAuthentication = isPassIdentityAuthentication;
    }

    /**
     * 账户币种0综合账户 1港币账户 2美元账户 3人民币账户 4其他账户【bank_currency】
     */
    public Integer getBankCurrency() {
        return bankCurrency;
    }

    /**
     * 账户币种0综合账户 1港币账户 2美元账户 3人民币账户 4其他账户【bank_currency】
     */
    public void setBankCurrency(Integer bankCurrency) {
        this.bankCurrency = bankCurrency;
    }

    /**
     * 银行账户类型[0-香港账户 1-非香港帐号]
     */
    public Integer getBankType() {
        return bankType;
    }

    /**
     * 银行账户类型[0-香港账户 1-非香港帐号]
     */
    public void setBankType(Integer bankType) {
        this.bankType = bankType;
    }

    /**
     * 银行编号
     */
    public String getBankId() {
        return bankId;
    }

    /**
     * 银行编号
     */
    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    /**
     * 银行卡号
     */
    public String getBankNo() {
        return bankNo;
    }

    /**
     * 银行卡号
     */
    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    /**
     * 银行账户名
     */
    public String getBankAccountName() {
        return bankAccountName;
    }

    /**
     * 银行账户名
     */
    public void setBankAccountName(String bankAccountName) {
        this.bankAccountName = bankAccountName;
    }

    /**
     * 其他银行名称
     */
    public String getOtherBankName() {
        return otherBankName;
    }

    /**
     * 其他银行名称
     */
    public void setOtherBankName(String otherBankName) {
        this.otherBankName = otherBankName;
    }

    /**
     * 国籍
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * 国籍
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * 通讯地址的国家
     */
    public String getContactRepublicName() {
        return contactRepublicName;
    }

    /**
     * 通讯地址的国家
     */
    public void setContactRepublicName(String contactRepublicName) {
        this.contactRepublicName = contactRepublicName;
    }

    /**
     * 联系地址的省份
     */
    public String getContactProvinceName() {
        return contactProvinceName;
    }

    /**
     * 联系地址的省份
     */
    public void setContactProvinceName(String contactProvinceName) {
        this.contactProvinceName = contactProvinceName;
    }

    /**
     * 联系地址的城市
     */
    public String getContactCityName() {
        return contactCityName;
    }

    /**
     * 联系地址的城市
     */
    public void setContactCityName(String contactCityName) {
        this.contactCityName = contactCityName;
    }

    /**
     * 联系地址的区域
     */
    public String getContactCountyName() {
        return contactCountyName;
    }

    /**
     * 联系地址的区域
     */
    public void setContactCountyName(String contactCountyName) {
        this.contactCountyName = contactCountyName;
    }

    /**
     * 联系地址的详细地址
     */
    public String getContactDetailAddress() {
        return contactDetailAddress;
    }

    /**
     * 联系地址的详细地址
     */
    public void setContactDetailAddress(String contactDetailAddress) {
        this.contactDetailAddress = contactDetailAddress;
    }

    /**
     * 住宅地址
     */
    public String getFamilyAddress() {
        return familyAddress;
    }

    /**
     * 住宅地址
     */
    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }

    /**
     * 通讯地址
     */
    public String getContactAddress() {
        return contactAddress;
    }

    /**
     * 通讯地址
     */
    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    /**
     * 电子邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 电子邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 就业情况类型[1-全职 2-在职 3-自雇 4-待业 5-其他]
     */
    public Integer getProfessionCode() {
        return professionCode;
    }

    /**
     * 就业情况类型[1-全职 2-在职 3-自雇 4-待业 5-其他]
     */
    public void setProfessionCode(Integer professionCode) {
        this.professionCode = professionCode;
    }

    /**
     *
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     *
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * 公司地址
     */
    public String getCompanyAddress() {
        return companyAddress;
    }

    /**
     * 公司地址
     */
    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    /**
     * 公司电话
     */
    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    /**
     * 公司电话
     */
    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    /**
     * 职位级别[1-普通员工 2-中层管理 3-高层管理]
     */
    public String getJobPosition() {
        return jobPosition;
    }

    /**
     * 职位级别[1-普通员工 2-中层管理 3-高层管理]
     */
    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    /**
     * 财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 6-家人成员提供 7-其他]
     */
    public String getCapitalSource() {
        return capitalSource;
    }

    /**
     * 财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 6-家人成员提供 7-其他]
     */
    public void setCapitalSource(String capitalSource) {
        this.capitalSource = capitalSource;
    }

    /**
     * 年收入范围类型[1=<25万 2=25万-50万 3=50万-100万 4=>100万]
     */
    public Integer getAnnualIncome() {
        return annualIncome;
    }

    /**
     * 年收入范围类型[1=<25万 2=25万-50万 3=50万-100万 4=>100万]
     */
    public void setAnnualIncome(Integer annualIncome) {
        this.annualIncome = annualIncome;
    }

    /**
     * 年收入具体金额
     */
    public String getAnnualIncomeOther() {
        return annualIncomeOther;
    }

    /**
     * 年收入具体金额
     */
    public void setAnnualIncomeOther(String annualIncomeOther) {
        this.annualIncomeOther = annualIncomeOther;
    }

    /**
     * 在金融机构工作经验类型[0=未知 1=受监管持牌人士 2=与衍生工具相关后勤 3=管理层 4=其它]
     */
    public Integer getFinancingInstitutionWorkExperienceType() {
        return financingInstitutionWorkExperienceType;
    }

    /**
     * 在金融机构工作经验类型[0=未知 1=受监管持牌人士 2=与衍生工具相关后勤 3=管理层 4=其它]
     */
    public void setFinancingInstitutionWorkExperienceType(Integer financingInstitutionWorkExperienceType) {
        this.financingInstitutionWorkExperienceType = financingInstitutionWorkExperienceType;
    }

    /**
     * 在金融机构其它工作经验类型
     */
    public String getFinancingInstitutionWorkExperienceTypeOther() {
        return financingInstitutionWorkExperienceTypeOther;
    }

    /**
     * 在金融机构其它工作经验类型
     */
    public void setFinancingInstitutionWorkExperienceTypeOther(String financingInstitutionWorkExperienceTypeOther) {
        this.financingInstitutionWorkExperienceTypeOther = financingInstitutionWorkExperienceTypeOther;
    }

    /**
     * 是否在过去三年曾买卖过至少五次任何衍生产品的交易[0=否 1=是]
     */
    public Integer getIsTradedDerivativeProducts() {
        return isTradedDerivativeProducts;
    }

    /**
     * 是否在过去三年曾买卖过至少五次任何衍生产品的交易[0=否 1=是]
     */
    public void setIsTradedDerivativeProducts(Integer isTradedDerivativeProducts) {
        this.isTradedDerivativeProducts = isTradedDerivativeProducts;
    }

    /**
     * 是否开通美股市场[0=否 1=是]
     */
    public Integer getIsOpenUsaStockMarket() {
        return isOpenUsaStockMarket;
    }

    /**
     * 是否开通美股市场[0=否 1=是]
     */
    public void setIsOpenUsaStockMarket(Integer isOpenUsaStockMarket) {
        this.isOpenUsaStockMarket = isOpenUsaStockMarket;
    }

    /**
     * 是否开通港股市场[0=否 1=是]
     */
    public Integer getIsOpenHkStockMarket() {
        return isOpenHkStockMarket;
    }

    /**
     * 是否开通港股市场[0=否 1=是]
     */
    public void setIsOpenHkStockMarket(Integer isOpenHkStockMarket) {
        this.isOpenHkStockMarket = isOpenHkStockMarket;
    }

    /**
     * 有无AML可疑信息[0=无可疑 1=有可疑]
     */
    public Integer getIsAmlSuspicious() {
        return isAmlSuspicious;
    }

    /**
     * 有无AML可疑信息[0=无可疑 1=有可疑]
     */
    public void setIsAmlSuspicious(Integer isAmlSuspicious) {
        this.isAmlSuspicious = isAmlSuspicious;
    }

    /**
     * 账户等级[0-未知 1-预批账户 2-非标准账户 3-标准账户]
     */
    public Integer getAccountLevel() {
        return accountLevel;
    }

    /**
     * 账户等级[0-未知 1-预批账户 2-非标准账户 3-标准账户]
     */
    public void setAccountLevel(Integer accountLevel) {
        this.accountLevel = accountLevel;
    }

    /**
     * 初始密码
     */
    public String getInitialAccountPassword() {
        return initialAccountPassword;
    }

    /**
     * 初始密码
     */
    public void setInitialAccountPassword(String initialAccountPassword) {
        this.initialAccountPassword = initialAccountPassword;
    }

    /**
     * 开户时间
     */
    public Date getOpenAccountTime() {
        return openAccountTime;
    }

    /**
     * 开户时间
     */
    public void setOpenAccountTime(Date openAccountTime) {
        this.openAccountTime = openAccountTime;
    }

    /**
     * 申请时间（用户实际提交申请的时间）
     */
    public Date getApplicationTime() {
        return applicationTime;
    }

    /**
     * 申请时间（用户实际提交申请的时间）
     */
    public void setApplicationTime(Date applicationTime) {
        this.applicationTime = applicationTime;
    }

    /**
     * 身份证签发机关
     */
    public String getSigningOrganization() {
        return signingOrganization;
    }

    /**
     * 身份证签发机关
     */
    public void setSigningOrganization(String signingOrganization) {
        this.signingOrganization = signingOrganization;
    }

    /**
     * 通讯地址类型 [1-大陆地址 2-香港地址 3-其他地区]
     */
    public Integer getAddressType() {
        return addressType;
    }

    /**
     * 通讯地址类型 [1-大陆地址 2-香港地址 3-其他地区]
     */
    public void setAddressType(Integer addressType) {
        this.addressType = addressType;
    }

    /**
     * CA签署码
     */
    public String getCaSignHashCode() {
        return caSignHashCode;
    }

    /**
     * CA签署码
     */
    public void setCaSignHashCode(String caSignHashCode) {
        this.caSignHashCode = caSignHashCode;
    }

    /**
     * 民族
     */
    public String getNation() {
        return nation;
    }

    /**
     * 民族
     */
    public void setNation(String nation) {
        this.nation = nation;
    }

    /**
     * 记录状态[0-无效 1-有效]
     */
    public Integer getRecordStatus() {
        return recordStatus;
    }

    /**
     * 记录状态[0-无效 1-有效]
     */
    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    /**
     * 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 北向交易资料收集声明([0=否 1=是])
     */
    public Integer getNorthTrade() {
        return northTrade;
    }

    /**
     * 北向交易资料收集声明([0=否 1=是])
     */
    public void setNorthTrade(Integer northTrade) {
        this.northTrade = northTrade;
    }

    /**
     * FATCA声明([0=否 1=是])
     */
    public Integer getFatca() {
        return fatca;
    }

    /**
     * FATCA声明([0=否 1=是])
     */
    public void setFatca(Integer fatca) {
        this.fatca = fatca;
    }

    /**
     * 风险承受程度:[3=低风险 2=中风险 1=高风险]
     */
    public Integer getAcceptRisk() {
        return acceptRisk;
    }

    /**
     * 风险承受程度:[3=低风险 2=中风险 1=高风险]
     */
    public void setAcceptRisk(Integer acceptRisk) {
        this.acceptRisk = acceptRisk;
    }

    /**
     * 其它国家名称
     */
    public String getOtherNationality() {
        return otherNationality;
    }

    /**
     * 其它国家名称
     */
    public void setOtherNationality(String otherNationality) {
        this.otherNationality = otherNationality;
    }

    /**
     * 公司地址的国家
     */
    public String getCompanyRepublicName() {
        return companyRepublicName;
    }

    /**
     * 公司地址的国家
     */
    public void setCompanyRepublicName(String companyRepublicName) {
        this.companyRepublicName = companyRepublicName;
    }

    /**
     * 公司地址的省份
     */
    public String getCompanyProvinceName() {
        return companyProvinceName;
    }

    /**
     * 公司地址的省份
     */
    public void setCompanyProvinceName(String companyProvinceName) {
        this.companyProvinceName = companyProvinceName;
    }

    /**
     * 公司地址的城市
     */
    public String getCompanyCityName() {
        return companyCityName;
    }

    /**
     * 公司地址的城市
     */
    public void setCompanyCityName(String companyCityName) {
        this.companyCityName = companyCityName;
    }

    /**
     * 公司地址的区域
     */
    public String getCompanyCountyName() {
        return companyCountyName;
    }

    /**
     * 公司地址的区域
     */
    public void setCompanyCountyName(String companyCountyName) {
        this.companyCountyName = companyCountyName;
    }

    /**
     * 公司地址的详细地址
     */
    public String getCompanyDetailAddress() {
        return companyDetailAddress;
    }

    /**
     * 公司地址的详细地址
     */
    public void setCompanyDetailAddress(String companyDetailAddress) {
        this.companyDetailAddress = companyDetailAddress;
    }

    /**
     * 住宅地址的国家
     */
    public String getFamilyRepublicName() {
        return familyRepublicName;
    }

    /**
     * 住宅地址的国家
     */
    public void setFamilyRepublicName(String familyRepublicName) {
        this.familyRepublicName = familyRepublicName;
    }

    /**
     * 住宅地址的省份
     */
    public String getFamilyProvinceName() {
        return familyProvinceName;
    }

    /**
     * 住宅地址的省份
     */
    public void setFamilyProvinceName(String familyProvinceName) {
        this.familyProvinceName = familyProvinceName;
    }

    /**
     * 住宅地址的城市
     */
    public String getFamilyCityName() {
        return familyCityName;
    }

    /**
     * 住宅地址的城市
     */
    public void setFamilyCityName(String familyCityName) {
        this.familyCityName = familyCityName;
    }

    /**
     * 住宅地址的区域
     */
    public String getFamilyCountyName() {
        return familyCountyName;
    }

    /**
     * 住宅地址的区域
     */
    public void setFamilyCountyName(String familyCountyName) {
        this.familyCountyName = familyCountyName;
    }

    /**
     * 住宅地址的详细地址
     */
    public String getFamilyDetailAddress() {
        return familyDetailAddress;
    }

    /**
     * 住宅地址的详细地址
     */
    public void setFamilyDetailAddress(String familyDetailAddress) {
        this.familyDetailAddress = familyDetailAddress;
    }

    /**
     * 住宅地址的国家(其它填写内容)
     */
    public String getOtherFamilyRepublic() {
        return otherFamilyRepublic;
    }

    /**
     * 住宅地址的国家(其它填写内容)
     */
    public void setOtherFamilyRepublic(String otherFamilyRepublic) {
        this.otherFamilyRepublic = otherFamilyRepublic;
    }

    /**
     * 公司地址的国家(其它填写内容)
     */
    public String getOtherCompanyRepublic() {
        return otherCompanyRepublic;
    }

    /**
     * 公司地址的国家(其它填写内容)
     */
    public void setOtherCompanyRepublic(String otherCompanyRepublic) {
        this.otherCompanyRepublic = otherCompanyRepublic;
    }

    /**
     * 通讯地址的国家(其它填写内容)
     */
    public String getOtherContactRepublic() {
        return otherContactRepublic;
    }

    /**
     * 通讯地址的国家(其它填写内容)
     */
    public void setOtherContactRepublic(String otherContactRepublic) {
        this.otherContactRepublic = otherContactRepublic;
    }

    /**
     * 美国纳税人识别号码
     */
    public String getTheusTaxNumber() {
        return theusTaxNumber;
    }

    /**
     * 美国纳税人识别号码
     */
    public void setTheusTaxNumber(String theusTaxNumber) {
        this.theusTaxNumber = theusTaxNumber;
    }

    /**
     * 英文名字
     */
    public String getGivenNameSpell() {
        return givenNameSpell;
    }

    /**
     * 英文名字
     */
    public void setGivenNameSpell(String givenNameSpell) {
        this.givenNameSpell = givenNameSpell;
    }

    /**
     * 英文姓氏
     */
    public String getFamilyNameSpell() {
        return familyNameSpell;
    }

    /**
     * 英文姓氏
     */
    public void setFamilyNameSpell(String familyNameSpell) {
        this.familyNameSpell = familyNameSpell;
    }

    /**
     * 0=未知 1=英文 2=繁体中文 3=简体中文
     */
    public Integer getLan() {
        return lan;
    }

    /**
     * 0=未知 1=英文 2=繁体中文 3=简体中文
     */
    public void setLan(Integer lan) {
        this.lan = lan;
    }

    /**
     * 账户类型[0、未知  1、个人账户  2、联名账户   3、公司账户]
     */
    public Integer getAccountType() {
        return accountType;
    }

    /**
     * 账户类型[0、未知  1、个人账户  2、联名账户   3、公司账户]
     */
    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    /**
     * 住所电话
     */
    public String getFamilyPhone() {
        return familyPhone;
    }

    /**
     * 住所电话
     */
    public void setFamilyPhone(String familyPhone) {
        this.familyPhone = familyPhone;
    }

    /**
     * 教育程度[1、小学或以下   2、中学   3、大学或以上]
     */
    public Integer getEducationLevel() {
        return educationLevel;
    }

    /**
     * 教育程度[1、小学或以下   2、中学   3、大学或以上]
     */
    public void setEducationLevel(Integer educationLevel) {
        this.educationLevel = educationLevel;
    }

    /**
     * 从业年限[0、未知  1、1-2年   2、2-5年   3、5-10年   4、>10年]
     */
    public Integer getWorkingSeniority() {
        return workingSeniority;
    }

    /**
     * 从业年限[0、未知  1、1-2年   2、2-5年   3、5-10年   4、>10年]
     */
    public void setWorkingSeniority(Integer workingSeniority) {
        this.workingSeniority = workingSeniority;
    }

    /**
     * 其它职业（输入）
     */
    public String getOtherProfession() {
        return otherProfession;
    }

    /**
     * 其它职业（输入）
     */
    public void setOtherProfession(String otherProfession) {
        this.otherProfession = otherProfession;
    }

    /**
     * 你是否曾经破产或被送达要将你破产的申请[0、否   1是]
     */
    public Integer getIsBankrupted() {
        return isBankrupted;
    }

    /**
     * 你是否曾经破产或被送达要将你破产的申请[0、否   1是]
     */
    public void setIsBankrupted(Integer isBankrupted) {
        this.isBankrupted = isBankrupted;
    }

    /**
     * 日结单及月结单发送方式[0、未知  1、电子邮箱  2、邮寄到住宅地址  3、邮寄到营业地址]
     */
    public Integer getStatementReceiveMode() {
        return statementReceiveMode;
    }

    /**
     * 日结单及月结单发送方式[0、未知  1、电子邮箱  2、邮寄到住宅地址  3、邮寄到营业地址]
     */
    public void setStatementReceiveMode(Integer statementReceiveMode) {
        this.statementReceiveMode = statementReceiveMode;
    }

    /**
     * 其它投资产品交易频率次/年 [0、未知 1、<10  2、10-40   3、 >40]
     */
    public Integer getTradeOtherProductsFrequency() {
        return tradeOtherProductsFrequency;
    }

    /**
     * 其它投资产品交易频率次/年 [0、未知 1、<10  2、10-40   3、 >40]
     */
    public void setTradeOtherProductsFrequency(Integer tradeOtherProductsFrequency) {
        this.tradeOtherProductsFrequency = tradeOtherProductsFrequency;
    }

    /**
     * 通讯电话
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * 通讯电话
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    /**
     * 证券交易账号
     */
    public String getStockTradeAccount() {
        return stockTradeAccount;
    }

    /**
     * 证券交易账号
     */
    public void setStockTradeAccount(String stockTradeAccount) {
        this.stockTradeAccount = stockTradeAccount;
    }

    /**
     * 投资年期 [0-未知 1、<1年  2、1-3年  3、3年以上]
     */
    public Integer getInvestmentHorizon() {
        return investmentHorizon;
    }

    /**
     * 投资年期 [0-未知 1、<1年  2、1-3年  3、3年以上]
     */
    public void setInvestmentHorizon(Integer investmentHorizon) {
        this.investmentHorizon = investmentHorizon;
    }

    /**
     * 信用额度
     */
    public String getCreditQuota() {
        return creditQuota;
    }

    /**
     * 信用额度
     */
    public void setCreditQuota(String creditQuota) {
        this.creditQuota = creditQuota;
    }

    /**
     * 信用比例
     */
    public String getCreditRatio() {
        return creditRatio;
    }

    /**
     * 信用比例
     */
    public void setCreditRatio(String creditRatio) {
        this.creditRatio = creditRatio;
    }

    /**
     * 最初资金其它收入来源描述
     */
    public String getCapitalSourceOther() {
        return capitalSourceOther;
    }

    /**
     * 最初资金其它收入来源描述
     */
    public void setCapitalSourceOther(String capitalSourceOther) {
        this.capitalSourceOther = capitalSourceOther;
    }

    /**
     * 出生地区
     */
    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    /**
     * 出生地区
     */
    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    /**
     * 永久居住地址
     */
    public String getPermanentAddress() {
        return permanentAddress;
    }

    /**
     * 永久居住地址
     */
    public void setPermanentAddress(String permanentAddress) {
        this.permanentAddress = permanentAddress;
    }

    /**
     * 永久居住地址的国家
     */
    public String getPermanentRepublicName() {
        return permanentRepublicName;
    }

    /**
     * 永久居住地址的国家
     */
    public void setPermanentRepublicName(String permanentRepublicName) {
        this.permanentRepublicName = permanentRepublicName;
    }

    /**
     * 永久居住地址的国家(其它填写内容)
     */
    public String getOtherPermanentRepublic() {
        return otherPermanentRepublic;
    }

    /**
     * 永久居住地址的国家(其它填写内容)
     */
    public void setOtherPermanentRepublic(String otherPermanentRepublic) {
        this.otherPermanentRepublic = otherPermanentRepublic;
    }

    /**
     * 持续财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 6-家人成员提供 0-其他]
     */
    public String getOngoingCapitalSource() {
        return ongoingCapitalSource;
    }

    /**
     * 持续财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 6-家人成员提供 0-其他]
     */
    public void setOngoingCapitalSource(String ongoingCapitalSource) {
        this.ongoingCapitalSource = ongoingCapitalSource;
    }

    /**
     * 预期财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 0-其他]
     */
    public String getExpectedCapitalSource() {
        return expectedCapitalSource;
    }

    /**
     * 预期财富来源[1-工资 2-业务利润 3-投资获取的资本 4-租金 5-退休金 0-其他]
     */
    public void setExpectedCapitalSource(String expectedCapitalSource) {
        this.expectedCapitalSource = expectedCapitalSource;
    }

    /**
     * 持续资金其它收入来源
     */
    public String getOngoingCapitalSourceOther() {
        return ongoingCapitalSourceOther;
    }

    /**
     * 持续资金其它收入来源
     */
    public void setOngoingCapitalSourceOther(String ongoingCapitalSourceOther) {
        this.ongoingCapitalSourceOther = ongoingCapitalSourceOther;
    }

    /**
     * 预期资金其它收入来源
     */
    public String getExpectedCapitalSourceOther() {
        return expectedCapitalSourceOther;
    }

    /**
     * 预期资金其它收入来源
     */
    public void setExpectedCapitalSourceOther(String expectedCapitalSourceOther) {
        this.expectedCapitalSourceOther = expectedCapitalSourceOther;
    }

    /**
     * 使用/交易频率[1-每日 2-每周 3-每月 4-每季 5-每半年 6-每年 0-其他]
     */
    public Integer getTradeFrequency() {
        return tradeFrequency;
    }

    /**
     * 使用/交易频率[1-每日 2-每周 3-每月 4-每季 5-每半年 6-每年 0-其他]
     */
    public void setTradeFrequency(Integer tradeFrequency) {
        this.tradeFrequency = tradeFrequency;
    }

    /**
     * 其他使用/交易频率说明
     */
    public String getTradeFrequencyOther() {
        return tradeFrequencyOther;
    }

    /**
     * 其他使用/交易频率说明
     */
    public void setTradeFrequencyOther(String tradeFrequencyOther) {
        this.tradeFrequencyOther = tradeFrequencyOther;
    }

    /**
     * 是否接受相关风险[0-否 1-是]
     */
    public Integer getIsAcceptRisksAssociated() {
        return isAcceptRisksAssociated;
    }

    /**
     * 是否接受相关风险[0-否 1-是]
     */
    public void setIsAcceptRisksAssociated(Integer isAcceptRisksAssociated) {
        this.isAcceptRisksAssociated = isAcceptRisksAssociated;
    }

    /**
     * 银行地址
     */
    public String getBankAddress() {
        return bankAddress;
    }

    /**
     * 银行地址
     */
    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    /**
     * 是否公司客户
     */
    public Integer getIsCompanyAccount() {
        return isCompanyAccount;
    }

    /**
     * 是否公司客户
     */
    public void setIsCompanyAccount(Integer isCompanyAccount) {
        this.isCompanyAccount = isCompanyAccount;
    }

    /**
     * 公司客户Id
     */
    public String getCompanyClientId() {
        return companyClientId;
    }

    /**
     * 公司客户Id
     */
    public void setCompanyClientId(String companyClientId) {
        this.companyClientId = companyClientId;
    }

    /**
     * 税务居民[0-其他 1-香港]
     */
    public String getTaxResident() {
        return taxResident;
    }

    /**
     * 税务居民[0-其他 1-香港]
     */
    public void setTaxResident(String taxResident) {
        this.taxResident = taxResident;
    }

    /**
     *
     */
    public Integer getBcan() {
        return bcan;
    }

    /**
     *
     */
    public void setBcan(Integer bcan) {
        this.bcan = bcan;
    }

    /**
     * 创建人
     */
    public Long getCreateUser() {
        return createUser;
    }

    /**
     * 创建人
     */
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    /**
     * 修改人
     */
    public Long getUpdateUser() {
        return updateUser;
    }

    /**
     * 修改人
     */
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    /**
     * 是否删除
     */
    public Integer getIsDeleted() {
        return isDeleted;
    }

    /**
     * 是否删除
     */
    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * 反对资料促销用途 0否 1是
     */
    public Integer getAgainstDataPromotion() {
        return againstDataPromotion;
    }

    /**
     * 反对资料促销用途 0否 1是
     */
    public void setAgainstDataPromotion(Integer againstDataPromotion) {
        this.againstDataPromotion = againstDataPromotion;
    }

    /**
     * 反对资料向集团用促销用途  0否 1是
     */
    public Integer getAgainstDataPromotionToCompany() {
        return againstDataPromotionToCompany;
    }

    /**
     * 反对资料向集团用促销用途  0否 1是
     */
    public void setAgainstDataPromotionToCompany(Integer againstDataPromotionToCompany) {
        this.againstDataPromotionToCompany = againstDataPromotionToCompany;
    }

    /**
     * 曾用名
     */
    public String getFormerName() {
        return formerName;
    }

    /**
     * 曾用名
     */
    public void setFormerName(String formerName) {
        this.formerName = formerName;
    }

    /**
     * 称谓[appellation]
     */
    public Integer getAppellation() {
        return appellation;
    }

    /**
     * 称谓[appellation]
     */
    public void setAppellation(Integer appellation) {
        this.appellation = appellation;
    }

    /**
     * 婚姻[marital_status]
     */
    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    /**
     * 婚姻[marital_status]
     */
    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    /**
     * 公司邮箱
     */
    public String getCompanyEmail() {
        return companyEmail;
    }

    /**
     * 公司邮箱
     */
    public void setCompanyEmail(String companyEmail) {
        this.companyEmail = companyEmail;
    }

    /**
     * 公司传真
     */
    public String getCompanyFacsimile() {
        return companyFacsimile;
    }

    /**
     * 公司传真
     */
    public void setCompanyFacsimile(String companyFacsimile) {
        this.companyFacsimile = companyFacsimile;
    }

    /**
     * 公司业务性质
     */
    public Integer getCompanyBusinessNature() {
        return companyBusinessNature;
    }

    /**
     * 公司业务性质
     */
    public void setCompanyBusinessNature(Integer companyBusinessNature) {
        this.companyBusinessNature = companyBusinessNature;
    }

    /**
     * 净资产
     */
    public Integer getNetAsset() {
        return netAsset;
    }

    /**
     * 净资产
     */
    public void setNetAsset(Integer netAsset) {
        this.netAsset = netAsset;
    }

    /**
     * 净资产具体金额
     */
    public String getNetAssetOther() {
        return netAssetOther;
    }

    /**
     * 净资产具体金额
     */
    public void setNetAssetOther(String netAssetOther) {
        this.netAssetOther = netAssetOther;
    }

    /**
     * 每月交易金额
     */
    public Integer getTradeAmount() {
        return tradeAmount;
    }

    /**
     * 每月交易金额
     */
    public void setTradeAmount(Integer tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    /**
     * 每月交易具体金额
     */
    public String getTradeAmountOther() {
        return tradeAmountOther;
    }

    /**
     * 每月交易具体金额
     */
    public void setTradeAmountOther(String tradeAmountOther) {
        this.tradeAmountOther = tradeAmountOther;
    }

    /**
     * 邀请码
     */
    public String getAeCode() {
        return aeCode;
    }

    /**
     * 邀请码
     */
    public void setAeCode(String aeCode) {
        this.aeCode = aeCode;
    }

    /**
     * 永久城市
     */
    public String getPermanentCityName() {
        return permanentCityName;
    }

    /**
     * 永久城市
     */
    public void setPermanentCityName(String permanentCityName) {
        this.permanentCityName = permanentCityName;
    }

    /**
     * 永久区域
     */
    public String getPermanentCountyName() {
        return permanentCountyName;
    }

    /**
     * 永久区域
     */
    public void setPermanentCountyName(String permanentCountyName) {
        this.permanentCountyName = permanentCountyName;
    }

    /**
     * 永久详情
     */
    public String getPermanentDetailAddress() {
        return permanentDetailAddress;
    }

    /**
     * 永久详情
     */
    public void setPermanentDetailAddress(String permanentDetailAddress) {
        this.permanentDetailAddress = permanentDetailAddress;
    }

    /**
     * 永久省份
     */
    public String getPermanentProvinceName() {
        return permanentProvinceName;
    }

    /**
     * 永久省份
     */
    public void setPermanentProvinceName(String permanentProvinceName) {
        this.permanentProvinceName = permanentProvinceName;
    }

    /**
     * 证件城市
     */
    public String getIdCardCityName() {
        return idCardCityName;
    }

    /**
     * 证件城市
     */
    public void setIdCardCityName(String idCardCityName) {
        this.idCardCityName = idCardCityName;
    }

    /**
     * 证件区域
     */
    public String getIdCardCountyName() {
        return idCardCountyName;
    }

    /**
     * 证件区域
     */
    public void setIdCardCountyName(String idCardCountyName) {
        this.idCardCountyName = idCardCountyName;
    }

    /**
     * 证件省份
     */
    public String getIdCardProvinceName() {
        return idCardProvinceName;
    }

    /**
     * 证件省份
     */
    public void setIdCardProvinceName(String idCardProvinceName) {
        this.idCardProvinceName = idCardProvinceName;
    }

    /**
     * 证件详情
     */
    public String getIdCardDetailAddress() {
        return idCardDetailAddress;
    }

    /**
     * 证件详情
     */
    public void setIdCardDetailAddress(String idCardDetailAddress) {
        this.idCardDetailAddress = idCardDetailAddress;
    }

    /**
     * 租户 ID
     */
    public String getTenantId() {
        return tenantId;
    }

    /**
     * 租户 ID
     */
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    /**
     * 创建科室
     */
    public Long getCreateDept() {
        return createDept;
    }

    /**
     * 创建科室
     */
    public void setCreateDept(Long createDept) {
        this.createDept = createDept;
    }

    /**
     *
     */
    public String getJobPositionOther() {
        return jobPositionOther;
    }

    /**
     *
     */
    public void setJobPositionOther(String jobPositionOther) {
        this.jobPositionOther = jobPositionOther;
    }

    /**
     * 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     *
     */
    public String getCompanyBusinessNatureOther() {
        return companyBusinessNatureOther;
    }

    /**
     *
     */
    public void setCompanyBusinessNatureOther(String companyBusinessNatureOther) {
        this.companyBusinessNatureOther = companyBusinessNatureOther;
    }

    /**
     * 手机号
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * 手机号
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * 区号
     */
    public String getPhoneArea() {
        return phoneArea;
    }

    /**
     * 区号
     */
    public void setPhoneArea(String phoneArea) {
        this.phoneArea = phoneArea;
    }

    /**
     *
     */
    public Long getCustId() {
        return custId;
    }

    /**
     *
     */
    public void setCustId(Long custId) {
        this.custId = custId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CustomerBasicInfoEntity other = (CustomerBasicInfoEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getApplicationId() == null ? other.getApplicationId() == null : this.getApplicationId().equals(other.getApplicationId()))
            && (this.getOpenAccountType() == null ? other.getOpenAccountType() == null : this.getOpenAccountType().equals(other.getOpenAccountType()))
            && (this.getOpenAccountAccessWay() == null ? other.getOpenAccountAccessWay() == null : this.getOpenAccountAccessWay().equals(other.getOpenAccountAccessWay()))
            && (this.getFundAccountType() == null ? other.getFundAccountType() == null : this.getFundAccountType().equals(other.getFundAccountType()))
            && (this.getSourceChannelId() == null ? other.getSourceChannelId() == null : this.getSourceChannelId().equals(other.getSourceChannelId()))
            && (this.getInviterId() == null ? other.getInviterId() == null : this.getInviterId().equals(other.getInviterId()))
            && (this.getClientName() == null ? other.getClientName() == null : this.getClientName().equals(other.getClientName()))
            && (this.getFamilyName() == null ? other.getFamilyName() == null : this.getFamilyName().equals(other.getFamilyName()))
            && (this.getGivenName() == null ? other.getGivenName() == null : this.getGivenName().equals(other.getGivenName()))
            && (this.getClientNameSpell() == null ? other.getClientNameSpell() == null : this.getClientNameSpell().equals(other.getClientNameSpell()))
            && (this.getIdKind() == null ? other.getIdKind() == null : this.getIdKind().equals(other.getIdKind()))
            && (this.getIsPassIdentityAuthentication() == null ? other.getIsPassIdentityAuthentication() == null : this.getIsPassIdentityAuthentication().equals(other.getIsPassIdentityAuthentication()))
            && (this.getBankCurrency() == null ? other.getBankCurrency() == null : this.getBankCurrency().equals(other.getBankCurrency()))
            && (this.getBankType() == null ? other.getBankType() == null : this.getBankType().equals(other.getBankType()))
            && (this.getBankId() == null ? other.getBankId() == null : this.getBankId().equals(other.getBankId()))
            && (this.getBankNo() == null ? other.getBankNo() == null : this.getBankNo().equals(other.getBankNo()))
            && (this.getBankAccountName() == null ? other.getBankAccountName() == null : this.getBankAccountName().equals(other.getBankAccountName()))
            && (this.getOtherBankName() == null ? other.getOtherBankName() == null : this.getOtherBankName().equals(other.getOtherBankName()))
            && (this.getNationality() == null ? other.getNationality() == null : this.getNationality().equals(other.getNationality()))
            && (this.getContactRepublicName() == null ? other.getContactRepublicName() == null : this.getContactRepublicName().equals(other.getContactRepublicName()))
            && (this.getContactProvinceName() == null ? other.getContactProvinceName() == null : this.getContactProvinceName().equals(other.getContactProvinceName()))
            && (this.getContactCityName() == null ? other.getContactCityName() == null : this.getContactCityName().equals(other.getContactCityName()))
            && (this.getContactCountyName() == null ? other.getContactCountyName() == null : this.getContactCountyName().equals(other.getContactCountyName()))
            && (this.getContactDetailAddress() == null ? other.getContactDetailAddress() == null : this.getContactDetailAddress().equals(other.getContactDetailAddress()))
            && (this.getFamilyAddress() == null ? other.getFamilyAddress() == null : this.getFamilyAddress().equals(other.getFamilyAddress()))
            && (this.getContactAddress() == null ? other.getContactAddress() == null : this.getContactAddress().equals(other.getContactAddress()))
            && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
            && (this.getProfessionCode() == null ? other.getProfessionCode() == null : this.getProfessionCode().equals(other.getProfessionCode()))
            && (this.getCompanyName() == null ? other.getCompanyName() == null : this.getCompanyName().equals(other.getCompanyName()))
            && (this.getCompanyAddress() == null ? other.getCompanyAddress() == null : this.getCompanyAddress().equals(other.getCompanyAddress()))
            && (this.getCompanyPhoneNumber() == null ? other.getCompanyPhoneNumber() == null : this.getCompanyPhoneNumber().equals(other.getCompanyPhoneNumber()))
            && (this.getJobPosition() == null ? other.getJobPosition() == null : this.getJobPosition().equals(other.getJobPosition()))
            && (this.getCapitalSource() == null ? other.getCapitalSource() == null : this.getCapitalSource().equals(other.getCapitalSource()))
            && (this.getAnnualIncome() == null ? other.getAnnualIncome() == null : this.getAnnualIncome().equals(other.getAnnualIncome()))
            && (this.getAnnualIncomeOther() == null ? other.getAnnualIncomeOther() == null : this.getAnnualIncomeOther().equals(other.getAnnualIncomeOther()))
            && (this.getFinancingInstitutionWorkExperienceType() == null ? other.getFinancingInstitutionWorkExperienceType() == null : this.getFinancingInstitutionWorkExperienceType().equals(other.getFinancingInstitutionWorkExperienceType()))
            && (this.getFinancingInstitutionWorkExperienceTypeOther() == null ? other.getFinancingInstitutionWorkExperienceTypeOther() == null : this.getFinancingInstitutionWorkExperienceTypeOther().equals(other.getFinancingInstitutionWorkExperienceTypeOther()))
            && (this.getIsTradedDerivativeProducts() == null ? other.getIsTradedDerivativeProducts() == null : this.getIsTradedDerivativeProducts().equals(other.getIsTradedDerivativeProducts()))
            && (this.getIsOpenUsaStockMarket() == null ? other.getIsOpenUsaStockMarket() == null : this.getIsOpenUsaStockMarket().equals(other.getIsOpenUsaStockMarket()))
            && (this.getIsOpenHkStockMarket() == null ? other.getIsOpenHkStockMarket() == null : this.getIsOpenHkStockMarket().equals(other.getIsOpenHkStockMarket()))
            && (this.getIsAmlSuspicious() == null ? other.getIsAmlSuspicious() == null : this.getIsAmlSuspicious().equals(other.getIsAmlSuspicious()))
            && (this.getAccountLevel() == null ? other.getAccountLevel() == null : this.getAccountLevel().equals(other.getAccountLevel()))
            && (this.getInitialAccountPassword() == null ? other.getInitialAccountPassword() == null : this.getInitialAccountPassword().equals(other.getInitialAccountPassword()))
            && (this.getOpenAccountTime() == null ? other.getOpenAccountTime() == null : this.getOpenAccountTime().equals(other.getOpenAccountTime()))
            && (this.getApplicationTime() == null ? other.getApplicationTime() == null : this.getApplicationTime().equals(other.getApplicationTime()))
            && (this.getSigningOrganization() == null ? other.getSigningOrganization() == null : this.getSigningOrganization().equals(other.getSigningOrganization()))
            && (this.getAddressType() == null ? other.getAddressType() == null : this.getAddressType().equals(other.getAddressType()))
            && (this.getCaSignHashCode() == null ? other.getCaSignHashCode() == null : this.getCaSignHashCode().equals(other.getCaSignHashCode()))
            && (this.getNation() == null ? other.getNation() == null : this.getNation().equals(other.getNation()))
            && (this.getRecordStatus() == null ? other.getRecordStatus() == null : this.getRecordStatus().equals(other.getRecordStatus()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()))
            && (this.getNorthTrade() == null ? other.getNorthTrade() == null : this.getNorthTrade().equals(other.getNorthTrade()))
            && (this.getFatca() == null ? other.getFatca() == null : this.getFatca().equals(other.getFatca()))
            && (this.getAcceptRisk() == null ? other.getAcceptRisk() == null : this.getAcceptRisk().equals(other.getAcceptRisk()))
            && (this.getOtherNationality() == null ? other.getOtherNationality() == null : this.getOtherNationality().equals(other.getOtherNationality()))
            && (this.getCompanyRepublicName() == null ? other.getCompanyRepublicName() == null : this.getCompanyRepublicName().equals(other.getCompanyRepublicName()))
            && (this.getCompanyProvinceName() == null ? other.getCompanyProvinceName() == null : this.getCompanyProvinceName().equals(other.getCompanyProvinceName()))
            && (this.getCompanyCityName() == null ? other.getCompanyCityName() == null : this.getCompanyCityName().equals(other.getCompanyCityName()))
            && (this.getCompanyCountyName() == null ? other.getCompanyCountyName() == null : this.getCompanyCountyName().equals(other.getCompanyCountyName()))
            && (this.getCompanyDetailAddress() == null ? other.getCompanyDetailAddress() == null : this.getCompanyDetailAddress().equals(other.getCompanyDetailAddress()))
            && (this.getFamilyRepublicName() == null ? other.getFamilyRepublicName() == null : this.getFamilyRepublicName().equals(other.getFamilyRepublicName()))
            && (this.getFamilyProvinceName() == null ? other.getFamilyProvinceName() == null : this.getFamilyProvinceName().equals(other.getFamilyProvinceName()))
            && (this.getFamilyCityName() == null ? other.getFamilyCityName() == null : this.getFamilyCityName().equals(other.getFamilyCityName()))
            && (this.getFamilyCountyName() == null ? other.getFamilyCountyName() == null : this.getFamilyCountyName().equals(other.getFamilyCountyName()))
            && (this.getFamilyDetailAddress() == null ? other.getFamilyDetailAddress() == null : this.getFamilyDetailAddress().equals(other.getFamilyDetailAddress()))
            && (this.getOtherFamilyRepublic() == null ? other.getOtherFamilyRepublic() == null : this.getOtherFamilyRepublic().equals(other.getOtherFamilyRepublic()))
            && (this.getOtherCompanyRepublic() == null ? other.getOtherCompanyRepublic() == null : this.getOtherCompanyRepublic().equals(other.getOtherCompanyRepublic()))
            && (this.getOtherContactRepublic() == null ? other.getOtherContactRepublic() == null : this.getOtherContactRepublic().equals(other.getOtherContactRepublic()))
            && (this.getTheusTaxNumber() == null ? other.getTheusTaxNumber() == null : this.getTheusTaxNumber().equals(other.getTheusTaxNumber()))
            && (this.getGivenNameSpell() == null ? other.getGivenNameSpell() == null : this.getGivenNameSpell().equals(other.getGivenNameSpell()))
            && (this.getFamilyNameSpell() == null ? other.getFamilyNameSpell() == null : this.getFamilyNameSpell().equals(other.getFamilyNameSpell()))
            && (this.getLan() == null ? other.getLan() == null : this.getLan().equals(other.getLan()))
            && (this.getAccountType() == null ? other.getAccountType() == null : this.getAccountType().equals(other.getAccountType()))
            && (this.getFamilyPhone() == null ? other.getFamilyPhone() == null : this.getFamilyPhone().equals(other.getFamilyPhone()))
            && (this.getEducationLevel() == null ? other.getEducationLevel() == null : this.getEducationLevel().equals(other.getEducationLevel()))
            && (this.getWorkingSeniority() == null ? other.getWorkingSeniority() == null : this.getWorkingSeniority().equals(other.getWorkingSeniority()))
            && (this.getOtherProfession() == null ? other.getOtherProfession() == null : this.getOtherProfession().equals(other.getOtherProfession()))
            && (this.getIsBankrupted() == null ? other.getIsBankrupted() == null : this.getIsBankrupted().equals(other.getIsBankrupted()))
            && (this.getStatementReceiveMode() == null ? other.getStatementReceiveMode() == null : this.getStatementReceiveMode().equals(other.getStatementReceiveMode()))
            && (this.getTradeOtherProductsFrequency() == null ? other.getTradeOtherProductsFrequency() == null : this.getTradeOtherProductsFrequency().equals(other.getTradeOtherProductsFrequency()))
            && (this.getContactPhone() == null ? other.getContactPhone() == null : this.getContactPhone().equals(other.getContactPhone()))
            && (this.getStockTradeAccount() == null ? other.getStockTradeAccount() == null : this.getStockTradeAccount().equals(other.getStockTradeAccount()))
            && (this.getInvestmentHorizon() == null ? other.getInvestmentHorizon() == null : this.getInvestmentHorizon().equals(other.getInvestmentHorizon()))
            && (this.getCreditQuota() == null ? other.getCreditQuota() == null : this.getCreditQuota().equals(other.getCreditQuota()))
            && (this.getCreditRatio() == null ? other.getCreditRatio() == null : this.getCreditRatio().equals(other.getCreditRatio()))
            && (this.getCapitalSourceOther() == null ? other.getCapitalSourceOther() == null : this.getCapitalSourceOther().equals(other.getCapitalSourceOther()))
            && (this.getPlaceOfBirth() == null ? other.getPlaceOfBirth() == null : this.getPlaceOfBirth().equals(other.getPlaceOfBirth()))
            && (this.getPermanentAddress() == null ? other.getPermanentAddress() == null : this.getPermanentAddress().equals(other.getPermanentAddress()))
            && (this.getPermanentRepublicName() == null ? other.getPermanentRepublicName() == null : this.getPermanentRepublicName().equals(other.getPermanentRepublicName()))
            && (this.getOtherPermanentRepublic() == null ? other.getOtherPermanentRepublic() == null : this.getOtherPermanentRepublic().equals(other.getOtherPermanentRepublic()))
            && (this.getOngoingCapitalSource() == null ? other.getOngoingCapitalSource() == null : this.getOngoingCapitalSource().equals(other.getOngoingCapitalSource()))
            && (this.getExpectedCapitalSource() == null ? other.getExpectedCapitalSource() == null : this.getExpectedCapitalSource().equals(other.getExpectedCapitalSource()))
            && (this.getOngoingCapitalSourceOther() == null ? other.getOngoingCapitalSourceOther() == null : this.getOngoingCapitalSourceOther().equals(other.getOngoingCapitalSourceOther()))
            && (this.getExpectedCapitalSourceOther() == null ? other.getExpectedCapitalSourceOther() == null : this.getExpectedCapitalSourceOther().equals(other.getExpectedCapitalSourceOther()))
            && (this.getTradeFrequency() == null ? other.getTradeFrequency() == null : this.getTradeFrequency().equals(other.getTradeFrequency()))
            && (this.getTradeFrequencyOther() == null ? other.getTradeFrequencyOther() == null : this.getTradeFrequencyOther().equals(other.getTradeFrequencyOther()))
            && (this.getIsAcceptRisksAssociated() == null ? other.getIsAcceptRisksAssociated() == null : this.getIsAcceptRisksAssociated().equals(other.getIsAcceptRisksAssociated()))
            && (this.getBankAddress() == null ? other.getBankAddress() == null : this.getBankAddress().equals(other.getBankAddress()))
            && (this.getIsCompanyAccount() == null ? other.getIsCompanyAccount() == null : this.getIsCompanyAccount().equals(other.getIsCompanyAccount()))
            && (this.getCompanyClientId() == null ? other.getCompanyClientId() == null : this.getCompanyClientId().equals(other.getCompanyClientId()))
            && (this.getTaxResident() == null ? other.getTaxResident() == null : this.getTaxResident().equals(other.getTaxResident()))
            && (this.getBcan() == null ? other.getBcan() == null : this.getBcan().equals(other.getBcan()))
            && (this.getCreateUser() == null ? other.getCreateUser() == null : this.getCreateUser().equals(other.getCreateUser()))
            && (this.getUpdateUser() == null ? other.getUpdateUser() == null : this.getUpdateUser().equals(other.getUpdateUser()))
            && (this.getIsDeleted() == null ? other.getIsDeleted() == null : this.getIsDeleted().equals(other.getIsDeleted()))
            && (this.getAgainstDataPromotion() == null ? other.getAgainstDataPromotion() == null : this.getAgainstDataPromotion().equals(other.getAgainstDataPromotion()))
            && (this.getAgainstDataPromotionToCompany() == null ? other.getAgainstDataPromotionToCompany() == null : this.getAgainstDataPromotionToCompany().equals(other.getAgainstDataPromotionToCompany()))
            && (this.getFormerName() == null ? other.getFormerName() == null : this.getFormerName().equals(other.getFormerName()))
            && (this.getAppellation() == null ? other.getAppellation() == null : this.getAppellation().equals(other.getAppellation()))
            && (this.getMaritalStatus() == null ? other.getMaritalStatus() == null : this.getMaritalStatus().equals(other.getMaritalStatus()))
            && (this.getCompanyEmail() == null ? other.getCompanyEmail() == null : this.getCompanyEmail().equals(other.getCompanyEmail()))
            && (this.getCompanyFacsimile() == null ? other.getCompanyFacsimile() == null : this.getCompanyFacsimile().equals(other.getCompanyFacsimile()))
            && (this.getCompanyBusinessNature() == null ? other.getCompanyBusinessNature() == null : this.getCompanyBusinessNature().equals(other.getCompanyBusinessNature()))
            && (this.getNetAsset() == null ? other.getNetAsset() == null : this.getNetAsset().equals(other.getNetAsset()))
            && (this.getNetAssetOther() == null ? other.getNetAssetOther() == null : this.getNetAssetOther().equals(other.getNetAssetOther()))
            && (this.getTradeAmount() == null ? other.getTradeAmount() == null : this.getTradeAmount().equals(other.getTradeAmount()))
            && (this.getTradeAmountOther() == null ? other.getTradeAmountOther() == null : this.getTradeAmountOther().equals(other.getTradeAmountOther()))
            && (this.getAeCode() == null ? other.getAeCode() == null : this.getAeCode().equals(other.getAeCode()))
            && (this.getPermanentCityName() == null ? other.getPermanentCityName() == null : this.getPermanentCityName().equals(other.getPermanentCityName()))
            && (this.getPermanentCountyName() == null ? other.getPermanentCountyName() == null : this.getPermanentCountyName().equals(other.getPermanentCountyName()))
            && (this.getPermanentDetailAddress() == null ? other.getPermanentDetailAddress() == null : this.getPermanentDetailAddress().equals(other.getPermanentDetailAddress()))
            && (this.getPermanentProvinceName() == null ? other.getPermanentProvinceName() == null : this.getPermanentProvinceName().equals(other.getPermanentProvinceName()))
            && (this.getIdCardCityName() == null ? other.getIdCardCityName() == null : this.getIdCardCityName().equals(other.getIdCardCityName()))
            && (this.getIdCardCountyName() == null ? other.getIdCardCountyName() == null : this.getIdCardCountyName().equals(other.getIdCardCountyName()))
            && (this.getIdCardProvinceName() == null ? other.getIdCardProvinceName() == null : this.getIdCardProvinceName().equals(other.getIdCardProvinceName()))
            && (this.getIdCardDetailAddress() == null ? other.getIdCardDetailAddress() == null : this.getIdCardDetailAddress().equals(other.getIdCardDetailAddress()))
            && (this.getTenantId() == null ? other.getTenantId() == null : this.getTenantId().equals(other.getTenantId()))
            && (this.getCreateDept() == null ? other.getCreateDept() == null : this.getCreateDept().equals(other.getCreateDept()))
            && (this.getJobPositionOther() == null ? other.getJobPositionOther() == null : this.getJobPositionOther().equals(other.getJobPositionOther()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getCompanyBusinessNatureOther() == null ? other.getCompanyBusinessNatureOther() == null : this.getCompanyBusinessNatureOther().equals(other.getCompanyBusinessNatureOther()))
            && (this.getPhoneNumber() == null ? other.getPhoneNumber() == null : this.getPhoneNumber().equals(other.getPhoneNumber()))
            && (this.getPhoneArea() == null ? other.getPhoneArea() == null : this.getPhoneArea().equals(other.getPhoneArea()))
            && (this.getCustId() == null ? other.getCustId() == null : this.getCustId().equals(other.getCustId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getApplicationId() == null) ? 0 : getApplicationId().hashCode());
        result = prime * result + ((getOpenAccountType() == null) ? 0 : getOpenAccountType().hashCode());
        result = prime * result + ((getOpenAccountAccessWay() == null) ? 0 : getOpenAccountAccessWay().hashCode());
        result = prime * result + ((getFundAccountType() == null) ? 0 : getFundAccountType().hashCode());
        result = prime * result + ((getSourceChannelId() == null) ? 0 : getSourceChannelId().hashCode());
        result = prime * result + ((getInviterId() == null) ? 0 : getInviterId().hashCode());
        result = prime * result + ((getClientName() == null) ? 0 : getClientName().hashCode());
        result = prime * result + ((getFamilyName() == null) ? 0 : getFamilyName().hashCode());
        result = prime * result + ((getGivenName() == null) ? 0 : getGivenName().hashCode());
        result = prime * result + ((getClientNameSpell() == null) ? 0 : getClientNameSpell().hashCode());
        result = prime * result + ((getIdKind() == null) ? 0 : getIdKind().hashCode());
        result = prime * result + ((getIsPassIdentityAuthentication() == null) ? 0 : getIsPassIdentityAuthentication().hashCode());
        result = prime * result + ((getBankCurrency() == null) ? 0 : getBankCurrency().hashCode());
        result = prime * result + ((getBankType() == null) ? 0 : getBankType().hashCode());
        result = prime * result + ((getBankId() == null) ? 0 : getBankId().hashCode());
        result = prime * result + ((getBankNo() == null) ? 0 : getBankNo().hashCode());
        result = prime * result + ((getBankAccountName() == null) ? 0 : getBankAccountName().hashCode());
        result = prime * result + ((getOtherBankName() == null) ? 0 : getOtherBankName().hashCode());
        result = prime * result + ((getNationality() == null) ? 0 : getNationality().hashCode());
        result = prime * result + ((getContactRepublicName() == null) ? 0 : getContactRepublicName().hashCode());
        result = prime * result + ((getContactProvinceName() == null) ? 0 : getContactProvinceName().hashCode());
        result = prime * result + ((getContactCityName() == null) ? 0 : getContactCityName().hashCode());
        result = prime * result + ((getContactCountyName() == null) ? 0 : getContactCountyName().hashCode());
        result = prime * result + ((getContactDetailAddress() == null) ? 0 : getContactDetailAddress().hashCode());
        result = prime * result + ((getFamilyAddress() == null) ? 0 : getFamilyAddress().hashCode());
        result = prime * result + ((getContactAddress() == null) ? 0 : getContactAddress().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getProfessionCode() == null) ? 0 : getProfessionCode().hashCode());
        result = prime * result + ((getCompanyName() == null) ? 0 : getCompanyName().hashCode());
        result = prime * result + ((getCompanyAddress() == null) ? 0 : getCompanyAddress().hashCode());
        result = prime * result + ((getCompanyPhoneNumber() == null) ? 0 : getCompanyPhoneNumber().hashCode());
        result = prime * result + ((getJobPosition() == null) ? 0 : getJobPosition().hashCode());
        result = prime * result + ((getCapitalSource() == null) ? 0 : getCapitalSource().hashCode());
        result = prime * result + ((getAnnualIncome() == null) ? 0 : getAnnualIncome().hashCode());
        result = prime * result + ((getAnnualIncomeOther() == null) ? 0 : getAnnualIncomeOther().hashCode());
        result = prime * result + ((getFinancingInstitutionWorkExperienceType() == null) ? 0 : getFinancingInstitutionWorkExperienceType().hashCode());
        result = prime * result + ((getFinancingInstitutionWorkExperienceTypeOther() == null) ? 0 : getFinancingInstitutionWorkExperienceTypeOther().hashCode());
        result = prime * result + ((getIsTradedDerivativeProducts() == null) ? 0 : getIsTradedDerivativeProducts().hashCode());
        result = prime * result + ((getIsOpenUsaStockMarket() == null) ? 0 : getIsOpenUsaStockMarket().hashCode());
        result = prime * result + ((getIsOpenHkStockMarket() == null) ? 0 : getIsOpenHkStockMarket().hashCode());
        result = prime * result + ((getIsAmlSuspicious() == null) ? 0 : getIsAmlSuspicious().hashCode());
        result = prime * result + ((getAccountLevel() == null) ? 0 : getAccountLevel().hashCode());
        result = prime * result + ((getInitialAccountPassword() == null) ? 0 : getInitialAccountPassword().hashCode());
        result = prime * result + ((getOpenAccountTime() == null) ? 0 : getOpenAccountTime().hashCode());
        result = prime * result + ((getApplicationTime() == null) ? 0 : getApplicationTime().hashCode());
        result = prime * result + ((getSigningOrganization() == null) ? 0 : getSigningOrganization().hashCode());
        result = prime * result + ((getAddressType() == null) ? 0 : getAddressType().hashCode());
        result = prime * result + ((getCaSignHashCode() == null) ? 0 : getCaSignHashCode().hashCode());
        result = prime * result + ((getNation() == null) ? 0 : getNation().hashCode());
        result = prime * result + ((getRecordStatus() == null) ? 0 : getRecordStatus().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        result = prime * result + ((getNorthTrade() == null) ? 0 : getNorthTrade().hashCode());
        result = prime * result + ((getFatca() == null) ? 0 : getFatca().hashCode());
        result = prime * result + ((getAcceptRisk() == null) ? 0 : getAcceptRisk().hashCode());
        result = prime * result + ((getOtherNationality() == null) ? 0 : getOtherNationality().hashCode());
        result = prime * result + ((getCompanyRepublicName() == null) ? 0 : getCompanyRepublicName().hashCode());
        result = prime * result + ((getCompanyProvinceName() == null) ? 0 : getCompanyProvinceName().hashCode());
        result = prime * result + ((getCompanyCityName() == null) ? 0 : getCompanyCityName().hashCode());
        result = prime * result + ((getCompanyCountyName() == null) ? 0 : getCompanyCountyName().hashCode());
        result = prime * result + ((getCompanyDetailAddress() == null) ? 0 : getCompanyDetailAddress().hashCode());
        result = prime * result + ((getFamilyRepublicName() == null) ? 0 : getFamilyRepublicName().hashCode());
        result = prime * result + ((getFamilyProvinceName() == null) ? 0 : getFamilyProvinceName().hashCode());
        result = prime * result + ((getFamilyCityName() == null) ? 0 : getFamilyCityName().hashCode());
        result = prime * result + ((getFamilyCountyName() == null) ? 0 : getFamilyCountyName().hashCode());
        result = prime * result + ((getFamilyDetailAddress() == null) ? 0 : getFamilyDetailAddress().hashCode());
        result = prime * result + ((getOtherFamilyRepublic() == null) ? 0 : getOtherFamilyRepublic().hashCode());
        result = prime * result + ((getOtherCompanyRepublic() == null) ? 0 : getOtherCompanyRepublic().hashCode());
        result = prime * result + ((getOtherContactRepublic() == null) ? 0 : getOtherContactRepublic().hashCode());
        result = prime * result + ((getTheusTaxNumber() == null) ? 0 : getTheusTaxNumber().hashCode());
        result = prime * result + ((getGivenNameSpell() == null) ? 0 : getGivenNameSpell().hashCode());
        result = prime * result + ((getFamilyNameSpell() == null) ? 0 : getFamilyNameSpell().hashCode());
        result = prime * result + ((getLan() == null) ? 0 : getLan().hashCode());
        result = prime * result + ((getAccountType() == null) ? 0 : getAccountType().hashCode());
        result = prime * result + ((getFamilyPhone() == null) ? 0 : getFamilyPhone().hashCode());
        result = prime * result + ((getEducationLevel() == null) ? 0 : getEducationLevel().hashCode());
        result = prime * result + ((getWorkingSeniority() == null) ? 0 : getWorkingSeniority().hashCode());
        result = prime * result + ((getOtherProfession() == null) ? 0 : getOtherProfession().hashCode());
        result = prime * result + ((getIsBankrupted() == null) ? 0 : getIsBankrupted().hashCode());
        result = prime * result + ((getStatementReceiveMode() == null) ? 0 : getStatementReceiveMode().hashCode());
        result = prime * result + ((getTradeOtherProductsFrequency() == null) ? 0 : getTradeOtherProductsFrequency().hashCode());
        result = prime * result + ((getContactPhone() == null) ? 0 : getContactPhone().hashCode());
        result = prime * result + ((getStockTradeAccount() == null) ? 0 : getStockTradeAccount().hashCode());
        result = prime * result + ((getInvestmentHorizon() == null) ? 0 : getInvestmentHorizon().hashCode());
        result = prime * result + ((getCreditQuota() == null) ? 0 : getCreditQuota().hashCode());
        result = prime * result + ((getCreditRatio() == null) ? 0 : getCreditRatio().hashCode());
        result = prime * result + ((getCapitalSourceOther() == null) ? 0 : getCapitalSourceOther().hashCode());
        result = prime * result + ((getPlaceOfBirth() == null) ? 0 : getPlaceOfBirth().hashCode());
        result = prime * result + ((getPermanentAddress() == null) ? 0 : getPermanentAddress().hashCode());
        result = prime * result + ((getPermanentRepublicName() == null) ? 0 : getPermanentRepublicName().hashCode());
        result = prime * result + ((getOtherPermanentRepublic() == null) ? 0 : getOtherPermanentRepublic().hashCode());
        result = prime * result + ((getOngoingCapitalSource() == null) ? 0 : getOngoingCapitalSource().hashCode());
        result = prime * result + ((getExpectedCapitalSource() == null) ? 0 : getExpectedCapitalSource().hashCode());
        result = prime * result + ((getOngoingCapitalSourceOther() == null) ? 0 : getOngoingCapitalSourceOther().hashCode());
        result = prime * result + ((getExpectedCapitalSourceOther() == null) ? 0 : getExpectedCapitalSourceOther().hashCode());
        result = prime * result + ((getTradeFrequency() == null) ? 0 : getTradeFrequency().hashCode());
        result = prime * result + ((getTradeFrequencyOther() == null) ? 0 : getTradeFrequencyOther().hashCode());
        result = prime * result + ((getIsAcceptRisksAssociated() == null) ? 0 : getIsAcceptRisksAssociated().hashCode());
        result = prime * result + ((getBankAddress() == null) ? 0 : getBankAddress().hashCode());
        result = prime * result + ((getIsCompanyAccount() == null) ? 0 : getIsCompanyAccount().hashCode());
        result = prime * result + ((getCompanyClientId() == null) ? 0 : getCompanyClientId().hashCode());
        result = prime * result + ((getTaxResident() == null) ? 0 : getTaxResident().hashCode());
        result = prime * result + ((getBcan() == null) ? 0 : getBcan().hashCode());
        result = prime * result + ((getCreateUser() == null) ? 0 : getCreateUser().hashCode());
        result = prime * result + ((getUpdateUser() == null) ? 0 : getUpdateUser().hashCode());
        result = prime * result + ((getIsDeleted() == null) ? 0 : getIsDeleted().hashCode());
        result = prime * result + ((getAgainstDataPromotion() == null) ? 0 : getAgainstDataPromotion().hashCode());
        result = prime * result + ((getAgainstDataPromotionToCompany() == null) ? 0 : getAgainstDataPromotionToCompany().hashCode());
        result = prime * result + ((getFormerName() == null) ? 0 : getFormerName().hashCode());
        result = prime * result + ((getAppellation() == null) ? 0 : getAppellation().hashCode());
        result = prime * result + ((getMaritalStatus() == null) ? 0 : getMaritalStatus().hashCode());
        result = prime * result + ((getCompanyEmail() == null) ? 0 : getCompanyEmail().hashCode());
        result = prime * result + ((getCompanyFacsimile() == null) ? 0 : getCompanyFacsimile().hashCode());
        result = prime * result + ((getCompanyBusinessNature() == null) ? 0 : getCompanyBusinessNature().hashCode());
        result = prime * result + ((getNetAsset() == null) ? 0 : getNetAsset().hashCode());
        result = prime * result + ((getNetAssetOther() == null) ? 0 : getNetAssetOther().hashCode());
        result = prime * result + ((getTradeAmount() == null) ? 0 : getTradeAmount().hashCode());
        result = prime * result + ((getTradeAmountOther() == null) ? 0 : getTradeAmountOther().hashCode());
        result = prime * result + ((getAeCode() == null) ? 0 : getAeCode().hashCode());
        result = prime * result + ((getPermanentCityName() == null) ? 0 : getPermanentCityName().hashCode());
        result = prime * result + ((getPermanentCountyName() == null) ? 0 : getPermanentCountyName().hashCode());
        result = prime * result + ((getPermanentDetailAddress() == null) ? 0 : getPermanentDetailAddress().hashCode());
        result = prime * result + ((getPermanentProvinceName() == null) ? 0 : getPermanentProvinceName().hashCode());
        result = prime * result + ((getIdCardCityName() == null) ? 0 : getIdCardCityName().hashCode());
        result = prime * result + ((getIdCardCountyName() == null) ? 0 : getIdCardCountyName().hashCode());
        result = prime * result + ((getIdCardProvinceName() == null) ? 0 : getIdCardProvinceName().hashCode());
        result = prime * result + ((getIdCardDetailAddress() == null) ? 0 : getIdCardDetailAddress().hashCode());
        result = prime * result + ((getTenantId() == null) ? 0 : getTenantId().hashCode());
        result = prime * result + ((getCreateDept() == null) ? 0 : getCreateDept().hashCode());
        result = prime * result + ((getJobPositionOther() == null) ? 0 : getJobPositionOther().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCompanyBusinessNatureOther() == null) ? 0 : getCompanyBusinessNatureOther().hashCode());
        result = prime * result + ((getPhoneNumber() == null) ? 0 : getPhoneNumber().hashCode());
        result = prime * result + ((getPhoneArea() == null) ? 0 : getPhoneArea().hashCode());
        result = prime * result + ((getCustId() == null) ? 0 : getCustId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", applicationId=").append(applicationId);
        sb.append(", openAccountType=").append(openAccountType);
        sb.append(", openAccountAccessWay=").append(openAccountAccessWay);
        sb.append(", fundAccountType=").append(fundAccountType);
        sb.append(", sourceChannelId=").append(sourceChannelId);
        sb.append(", inviterId=").append(inviterId);
        sb.append(", clientName=").append(clientName);
        sb.append(", familyName=").append(familyName);
        sb.append(", givenName=").append(givenName);
        sb.append(", clientNameSpell=").append(clientNameSpell);
        sb.append(", idKind=").append(idKind);
        sb.append(", isPassIdentityAuthentication=").append(isPassIdentityAuthentication);
        sb.append(", bankCurrency=").append(bankCurrency);
        sb.append(", bankType=").append(bankType);
        sb.append(", bankId=").append(bankId);
        sb.append(", bankNo=").append(bankNo);
        sb.append(", bankAccountName=").append(bankAccountName);
        sb.append(", otherBankName=").append(otherBankName);
        sb.append(", nationality=").append(nationality);
        sb.append(", contactRepublicName=").append(contactRepublicName);
        sb.append(", contactProvinceName=").append(contactProvinceName);
        sb.append(", contactCityName=").append(contactCityName);
        sb.append(", contactCountyName=").append(contactCountyName);
        sb.append(", contactDetailAddress=").append(contactDetailAddress);
        sb.append(", familyAddress=").append(familyAddress);
        sb.append(", contactAddress=").append(contactAddress);
        sb.append(", email=").append(email);
        sb.append(", professionCode=").append(professionCode);
        sb.append(", companyName=").append(companyName);
        sb.append(", companyAddress=").append(companyAddress);
        sb.append(", companyPhoneNumber=").append(companyPhoneNumber);
        sb.append(", jobPosition=").append(jobPosition);
        sb.append(", capitalSource=").append(capitalSource);
        sb.append(", annualIncome=").append(annualIncome);
        sb.append(", annualIncomeOther=").append(annualIncomeOther);
        sb.append(", financingInstitutionWorkExperienceType=").append(financingInstitutionWorkExperienceType);
        sb.append(", financingInstitutionWorkExperienceTypeOther=").append(financingInstitutionWorkExperienceTypeOther);
        sb.append(", isTradedDerivativeProducts=").append(isTradedDerivativeProducts);
        sb.append(", isOpenUsaStockMarket=").append(isOpenUsaStockMarket);
        sb.append(", isOpenHkStockMarket=").append(isOpenHkStockMarket);
        sb.append(", isAmlSuspicious=").append(isAmlSuspicious);
        sb.append(", accountLevel=").append(accountLevel);
        sb.append(", initialAccountPassword=").append(initialAccountPassword);
        sb.append(", openAccountTime=").append(openAccountTime);
        sb.append(", applicationTime=").append(applicationTime);
        sb.append(", signingOrganization=").append(signingOrganization);
        sb.append(", addressType=").append(addressType);
        sb.append(", caSignHashCode=").append(caSignHashCode);
        sb.append(", nation=").append(nation);
        sb.append(", recordStatus=").append(recordStatus);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", northTrade=").append(northTrade);
        sb.append(", fatca=").append(fatca);
        sb.append(", acceptRisk=").append(acceptRisk);
        sb.append(", otherNationality=").append(otherNationality);
        sb.append(", companyRepublicName=").append(companyRepublicName);
        sb.append(", companyProvinceName=").append(companyProvinceName);
        sb.append(", companyCityName=").append(companyCityName);
        sb.append(", companyCountyName=").append(companyCountyName);
        sb.append(", companyDetailAddress=").append(companyDetailAddress);
        sb.append(", familyRepublicName=").append(familyRepublicName);
        sb.append(", familyProvinceName=").append(familyProvinceName);
        sb.append(", familyCityName=").append(familyCityName);
        sb.append(", familyCountyName=").append(familyCountyName);
        sb.append(", familyDetailAddress=").append(familyDetailAddress);
        sb.append(", otherFamilyRepublic=").append(otherFamilyRepublic);
        sb.append(", otherCompanyRepublic=").append(otherCompanyRepublic);
        sb.append(", otherContactRepublic=").append(otherContactRepublic);
        sb.append(", theusTaxNumber=").append(theusTaxNumber);
        sb.append(", givenNameSpell=").append(givenNameSpell);
        sb.append(", familyNameSpell=").append(familyNameSpell);
        sb.append(", lan=").append(lan);
        sb.append(", accountType=").append(accountType);
        sb.append(", familyPhone=").append(familyPhone);
        sb.append(", educationLevel=").append(educationLevel);
        sb.append(", workingSeniority=").append(workingSeniority);
        sb.append(", otherProfession=").append(otherProfession);
        sb.append(", isBankrupted=").append(isBankrupted);
        sb.append(", statementReceiveMode=").append(statementReceiveMode);
        sb.append(", tradeOtherProductsFrequency=").append(tradeOtherProductsFrequency);
        sb.append(", contactPhone=").append(contactPhone);
        sb.append(", stockTradeAccount=").append(stockTradeAccount);
        sb.append(", investmentHorizon=").append(investmentHorizon);
        sb.append(", creditQuota=").append(creditQuota);
        sb.append(", creditRatio=").append(creditRatio);
        sb.append(", capitalSourceOther=").append(capitalSourceOther);
        sb.append(", placeOfBirth=").append(placeOfBirth);
        sb.append(", permanentAddress=").append(permanentAddress);
        sb.append(", permanentRepublicName=").append(permanentRepublicName);
        sb.append(", otherPermanentRepublic=").append(otherPermanentRepublic);
        sb.append(", ongoingCapitalSource=").append(ongoingCapitalSource);
        sb.append(", expectedCapitalSource=").append(expectedCapitalSource);
        sb.append(", ongoingCapitalSourceOther=").append(ongoingCapitalSourceOther);
        sb.append(", expectedCapitalSourceOther=").append(expectedCapitalSourceOther);
        sb.append(", tradeFrequency=").append(tradeFrequency);
        sb.append(", tradeFrequencyOther=").append(tradeFrequencyOther);
        sb.append(", isAcceptRisksAssociated=").append(isAcceptRisksAssociated);
        sb.append(", bankAddress=").append(bankAddress);
        sb.append(", isCompanyAccount=").append(isCompanyAccount);
        sb.append(", companyClientId=").append(companyClientId);
        sb.append(", taxResident=").append(taxResident);
        sb.append(", bcan=").append(bcan);
        sb.append(", createUser=").append(createUser);
        sb.append(", updateUser=").append(updateUser);
        sb.append(", isDeleted=").append(isDeleted);
        sb.append(", againstDataPromotion=").append(againstDataPromotion);
        sb.append(", againstDataPromotionToCompany=").append(againstDataPromotionToCompany);
        sb.append(", formerName=").append(formerName);
        sb.append(", appellation=").append(appellation);
        sb.append(", maritalStatus=").append(maritalStatus);
        sb.append(", companyEmail=").append(companyEmail);
        sb.append(", companyFacsimile=").append(companyFacsimile);
        sb.append(", companyBusinessNature=").append(companyBusinessNature);
        sb.append(", netAsset=").append(netAsset);
        sb.append(", netAssetOther=").append(netAssetOther);
        sb.append(", tradeAmount=").append(tradeAmount);
        sb.append(", tradeAmountOther=").append(tradeAmountOther);
        sb.append(", aeCode=").append(aeCode);
        sb.append(", permanentCityName=").append(permanentCityName);
        sb.append(", permanentCountyName=").append(permanentCountyName);
        sb.append(", permanentDetailAddress=").append(permanentDetailAddress);
        sb.append(", permanentProvinceName=").append(permanentProvinceName);
        sb.append(", idCardCityName=").append(idCardCityName);
        sb.append(", idCardCountyName=").append(idCardCountyName);
        sb.append(", idCardProvinceName=").append(idCardProvinceName);
        sb.append(", idCardDetailAddress=").append(idCardDetailAddress);
        sb.append(", tenantId=").append(tenantId);
        sb.append(", createDept=").append(createDept);
        sb.append(", jobPositionOther=").append(jobPositionOther);
        sb.append(", status=").append(status);
        sb.append(", companyBusinessNatureOther=").append(companyBusinessNatureOther);
        sb.append(", phoneNumber=").append(phoneNumber);
        sb.append(", phoneArea=").append(phoneArea);
        sb.append(", custId=").append(custId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
