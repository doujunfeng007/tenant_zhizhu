package com.minigod.zero.bpm.vo.request;

import com.alibaba.fastjson.annotation.JSONField;
import com.minigod.zero.bpm.vo.*;
import com.minigod.zero.bpm.vo.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SecUserInfoChangeProtocol implements Serializable {


    //开户类型[0=未知 1=互联网 2=见证宝 3=BPM]
    private Integer openAccountType;

    @JSONField(name = "imgList")
    private String imgList;

    // 开户接入方式[1=H5开户 2=APP开户]
    @JSONField(name = "openAccountAccessWay")
    private Integer openAccountAccessWay;

    //账户类型[1=现金账户 2=融资账户]
    @JSONField(name = "fundAccountType")
    private Integer fundAccountType;

    // 中文名
    @JSONField(name = "clientName")
    private String clientName;

    // 中文名拼音
    @JSONField(name = "clientNameSpell")
    private String clientNameSpell;

    // 中文名字
    @JSONField(name = "givenName")
    private String givenName;

    // 中文姓氏
    @JSONField(name = "familyName")
    private String familyName;

    // 英文名字
    @JSONField(name = "givenNameSpell")
    private String givenNameSpell;

    // 英文姓氏
    @JSONField(name = "familyNameSpell")
    private String familyNameSpell;

    // 性别[0=男 1=女 2=其它]
    @JSONField(name = "sex")
    private Integer sex;

    // 生日
    @JSONField(name = "birthday")
    private String birthday;

    //国籍
    @JSONField(name = "nationality")
    private String nationality;

    //其它国籍/地区名称
    @JSONField(name = "otherNationality")
    private String otherNationality;

    // 证件类型
    @JSONField(name = "idKind")
    private Integer idKind;

    // 证件号码
    @JSONField(name = "idNo")
    private String idNo;

    //证件地址
    @JSONField(name = "idCardAddress")
    private String idCardAddress;

    // 身份证生效日期
    @JSONField(name = "idCardValidDateStart")
    private String idCardValidDateStart;

    // 身份证失效日期
    @JSONField(name = "idCardValidDateEnd")
    private String idCardValidDateEnd;

    // 身份相似百分数
    @JSONField(name = "identitySimilarityPercent")
    private Double identitySimilarityPercent;

    //住宅住址
    @JSONField(name = "familyAddress")
    private String familyAddress;

    // 通讯地址
    @JSONField(name = "contactAddress")
    private String contactAddress;

    // 手机号
    @JSONField(name = "phoneNumber")
    private String phoneNumber;

    // 邮箱
    @JSONField(name = "email")
    private String email;

    //银行编号
    @JSONField(name = "bankId")
    private String bankId;
    //银行卡号
    @JSONField(name = "bankNo")
    private String bankNo;
    //银行账户名
    @JSONField(name = "bankAccountName")
    private String bankAccountName;

    // 就业情况类型 [1=受雇 2=自营/个体户 3=退休 4=学生 5=其他 6 =农林牧副渔 7=待业 8=自由职业者 9=投资者 10=家庭主妇]
    @JSONField(name = "professionCode")
    private Integer professionCode;

    //自由职业细化选项 [字典=AO_FREELANCE_CODE]
    @JSONField(name = "freelanceCode")
    private Integer freelanceCode;

    //自由职业细化其他说明
    @JSONField(name = "freelanceOther")
    private String freelanceOther;

    //所属行业[待定]
    @JSONField(name = "professionType")
    private Integer professionType;

    //公司业务性质或行业类型
//    @JSONField(name = "industryRange")
//    private String industryRange;

    // 公司名称
    @JSONField(name = "companyName")
    private String companyName;

    //公司地址
    @JSONField(name = "companyAddress")
    private String companyAddress;

    // 职位
    @JSONField(name = "jobPosition")
    private String jobPosition;

    //年收入范围类型[1=<20万 2=20万-50万 3=50万-100万 4=100万-500万 5=>500万]
    @JSONField(name = "annualIncome")
    private Integer annualIncome;

    @JSONField(name = "netCapital")
    private Integer netCapital;

    // 收入来源
    @JSONField(name = "capitalSource")
    private String capitalSource;

    //财富来源
    @JSONField(name = "wealthSource")
    private String wealthSource;

    //财产种类
    @JSONField(name = "propertyTypeList")
    private List<OpenAccountPropertyTypeProtocol> propertyTypeList;

    // 投资目标类型 [NEW 1=股息收入 2=短期投资 3=长期投资 4=其他 5=保本 6-资本增值 7-投机 8-对冲]
    @JSONField(name = "investTarget")
    private String investTarget;

    // 投资目标其它类型说明
    @JSONField(name = "investTargetOther")
    private String investTargetOther;

    // 股票投资经验类型 [0=未知 1=没有经验 2=少于一年 3=一至三年 4=三至五年 5=五年以上]
    @JSONField(name = "stocksInvestmentExperienceType")
    private Integer stocksInvestmentExperienceType;

    //风险承受程度（必须三选一）：[1=低风险 2=中风险 3=高风险]
    @JSONField(name = "acceptRisk")
    private Integer acceptRisk;

    // 认证股权投资经验类型 [0=未知 1=没有经验 2=少于一年 3=一至三年 4=三至五年 5=五年以上]
    @JSONField(name = "warrantsInvestmentExperienceType")
    private Integer warrantsInvestmentExperienceType;

    @JSONField(name = "fundInvestmentExperienceType")
    private Integer fundInvestmentExperienceType;

    // 期货投资经验类型 [0=未知 1=没有经验 2=少于一年 3=一至三年 4=三至五年 5=五年以上]
    @JSONField(name = "futuresInvestmentExperienceType")
    private Integer futuresInvestmentExperienceType;

    // 是否了解衍生产品 [0=否 1=是]
    @JSONField(name = "isKnowDerivativeProducts")
    private Integer isKnowDerivativeProducts;

    // 衍生产品学习途径 [0=未知 1=金融机构 2=监管机构 3=交易所 4=大专院校 5=进修学院 6=线上课程 7=其它]
    @JSONField(name = "derivativeProductsStudyType")
    private Integer derivativeProductsStudyType;

    @JSONField(name = "derivativeProductsStudyTypeOther")
    private String derivativeProductsStudyTypeOther;

    // 在金融机构工作经验类型 [0=未知 1=受监管持牌人士 2=与衍生工具相关后勤 3=管理层 4=其它]
    @JSONField(name = "financingInstitutionWorkExperienceType")
    private Integer financingInstitutionWorkExperienceType;

    //  在金融机构其它工作经验类型
    @JSONField(name = "financingInstitutionWorkExperienceTypeOther")
    private String financingInstitutionWorkExperienceTypeOther;

    // 是否在过去三年曾买卖过至少五次任何衍生产品的交易 [0=否 1=是]
    @JSONField(name = "isTradedDerivativeProducts")
    private Integer isTradedDerivativeProducts;

    //您是否确认个人资料（私隐）条例通知并同意yff证券及其控股集团使用及向其他人士提供本人的个人资料作直接促销用途[0=否 1=是]
    @JSONField(name = "isAllowProvidePrivacy")
    private Integer isAllowProvidePrivacy;

    //北向交易的资料收集声明（[0=否 1=是]）必须二选一，此选项会影响到后续的中华通交易权限，选择“否”的客户，无法进行中华通交易
    @JSONField(name = "northTrade")
    private Integer northTrade;

    //FATCA声明（[0=否 1=是]）必须二选一；编辑资料改成“是”时出现弹窗，保留继续“否”的选择
    @JSONField(name = "fatca")
    private Integer fatca;

    // 税务信息
    @JSONField(name = "taxationInfoList")
    private List<OpenAccountTaxationInfoProtocol> taxationInfoList;

    //其他信息披露
    @JSONField(name = "otherDisclosureList")
    private List<OpenAccountOtherDisclosureProtocol> otherDisclosureList;

    //四要素
    @JSONField(name = "bankVerityInfo")
    private List<OpenAccountBankVerityInfoProtocol> bankVerityInfo;

    //是否开通美股市场[0=否 1=是]
    @JSONField(name = "isOpenUsaStockMarket")
    private Integer isOpenUsaStockMarket;

    //是否开通港股市场[0=否 1=是]
    @JSONField(name = "isOpenHkStockMarket")
    private Integer isOpenHkStockMarket;

    // 用户来源渠道ID
    @JSONField(name = "sourceChannelId")
    private String sourceChannelId;

    // 开户邀请人的userId
    @JSONField(name = "inviterId")
    private String inviterId;

    // 客户用户号
    @JSONField(name = "userId")
    private Integer userId;

    // 是否通过身份验证[0=否 1=是]
    @JSONField(name = "isPassIdentityAuthentication")
    private Integer isPassIdentityAuthentication;

    // 图片信息
    @JSONField(name = "openAccountImagesInfo")
    private List<OpenAccountImageInfoProtocol> openAccountImagesInfo;

    // 最后更新时间
    @JSONField(name = "lastUpdateTime")
    private Date lastUpdateTime;

    //公司电话
    @JSONField(name = "companyPhoneNumber")
    private String companyPhoneNumber;

    // 银行账户类型
    @JSONField(name = "bankType")
    private Integer bankType;

    //开户提交时间
    @JSONField(name = "isAmlSuspicious")
    private String isAmlSuspicious;

    //开户提交时间
    @JSONField(name = "applicationTime")
    private Date applicationTime;

    //其他银行名称
    @JSONField(name = "otherBankName")
    private String otherBankName;

    // 联系地址的国家
    @JSONField(name = "contactRepublicName")
    private String contactRepublicName;

    // 联系地址的国家(其它填写内容)
    @JSONField(name = "otherContactRepublic")
    private String otherContactRepublic;

    // 联系地址的省份
    @JSONField(name = "contactProvinceName")
    private String contactProvinceName;

    // 联系地址的城市
    @JSONField(name = "contactCityName")
    private String contactCityName;

    // 联系地址的区域
    @JSONField(name = "contactCountyName")
    private String contactCountyName;

    // 联系地址的详细地址
    @JSONField(name = "contactDetailAddress")
    private String contactDetailAddress;

    // 公司地址的国家
    @JSONField(name = "companyRepublicName")
    private String companyRepublicName;

    // 公司地址的国家(其它填写内容)
    @JSONField(name = "otherCompanyRepublic")
    private String otherCompanyRepublic;

    // 公司地址的省份
    @JSONField(name = "companyProvinceName")
    private String companyProvinceName;

    // 公司地址的城市
    @JSONField(name = "companyCityName")
    private String companyCityName;

    // 公司地址的区域
    @JSONField(name = "companyCountyName")
    private String companyCountyName;

    // 公司地址的详细地址
    @JSONField(name = "companyDetailAddress")
    private String companyDetailAddress;

    // 住宅地址的国家
    @JSONField(name = "familyRepublicName")
    private String familyRepublicName;

    // 住宅地址的国家(其它填写内容)
    @JSONField(name = "otherFamilyRepublic")
    private String otherFamilyRepublic;

    // 住宅地址的省份
    @JSONField(name = "familyProvinceName")
    private String familyProvinceName;

    // 住宅地址的城市
    @JSONField(name = "familyCityName")
    private String familyCityName;

    // 住宅地址的区域
    @JSONField(name = "familyCountyName")
    private String familyCountyName;

    // 住宅地址的详细地址
    @JSONField(name = "familyDetailAddress")
    private String familyDetailAddress;

    // 地址类型
    @JSONField(name = "addressType")
    private Integer addressType;

    // 签发机关
    @JSONField(name = "signingOrganization")
    private String signingOrganization;

    // 名族
    @JSONField(name = "nation")
    private String nation;

    private Integer step;

}
