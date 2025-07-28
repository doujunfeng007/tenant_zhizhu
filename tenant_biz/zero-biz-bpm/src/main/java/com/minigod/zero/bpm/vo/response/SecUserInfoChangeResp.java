package com.minigod.zero.bpm.vo.response;

import com.minigod.zero.bpm.vo.OpenAccountOtherDisclosureProtocol;
import com.minigod.zero.bpm.vo.OpenAccountPropertyTypeProtocol;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class SecUserInfoChangeResp implements Serializable {
    private static final long serialVersionUID = 1L;

    //用户号
    private Integer userId;

    //客户中文名
    private String clientName;
    //姓氏
    private String familyName;
    private String familyNameSpell;
    //名字
    private String givenName;
    private String givenNameSpell;
    //中文名拼音
    private String clientNameSpell;

    //证件类型[0=未知 1=大陆居民身份证 2=香港居民身份证 3=护照 4=驾驶证]
    private Integer idKind;

    private String idCardValue;

    private String birthday;

    private Integer openAccountType;

	private String employYears;

    private String familyAddress;

    private String contactAddress;

    //职业类型[数据字典-AO_OCCUPATION_TYPE]
    private Integer professionCode;

    private List<OpenAccountOtherDisclosureProtocol> otherDisclosureList;


    private Integer professionType;
    //公司名称
    private String companyName;
    //公司地址
    private String companyAddress;
    //公司业务性质或行业类型
    private String industryRange;
    //职位级别[1-普通员工 2-中层管理 3-高层管理]
    private String jobPosition;
    //资金来源[0-工资和奖金 1-投资回报 2-劳务报酬 3-租金收入 4-营业收入 5-退休金 6-家人给予 7-兼职收入 8-生产收入]
    private String capitalSource;
    //年收入范围类型[1=<20万 2=20万-50万 3=50万-100万 4=100万-500万 5=>500万]
    private Integer annualIncome;
    // 财产类型
    private List<OpenAccountPropertyTypeProtocol> propertyType;

    private Boolean bankCheck;

    private String bankValue;

    private Integer bankType;

    public Integer getBankType() {
        return bankType;
    }

    public void setBankType(Integer bankType) {
        this.bankType = bankType;
    }

    private Boolean stockCheck;

    private String stockValue;

    private Boolean realCheck;

    private String realValue;

    private Integer freelanceCode;

    private String freelanceOther;

    //净资产范围类型[1=<50万 2=50万-250万 3=250万-500万 4=>500万]
    private Integer netCapital;

    private String wealthSource;

    //投资目标类型[1=股息收入 2=短期投资 3=长期投资 4=其他]
    private String investTarget;
    //投资目标其它类型说明
    private String investTargetOther;
    //股票投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]
    private Integer stocksInvestmentExperienceType;
    //基金投资经验类型[0=没有 1=少于一年 2=一至五年  3=五年以上]
    private Integer fundInvestmentExperienceType;
    //认证股权投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]
    private Integer warrantsInvestmentExperienceType;
    //期货投资经验类型[0=没有经验 1=少于一年 2=一至三年 3=三至五年 4=五年以上]
    private Integer futuresInvestmentExperienceType;
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
    // 税务信息
    private String defaultCountry;
    private String country1;
    private String country2;
    private String country3;
    private String country4;

    private String defaultPrivacyNum;
    private String canPrivacyNum1;
    private String canPrivacyNum2;
    private String canPrivacyNum3;
    private String canPrivacyNum4;

    private String offerPrivacy1;
    private String offerPrivacy2;
    private String offerPrivacy3;
    private String offerPrivacy4;

    private String noOfferPrivacy1;
    private String noOfferPrivacy2;
    private String noOfferPrivacy3;
    private String noOfferPrivacy4;

    private String reasonDesc1;
    private String reasonDesc2;
    private String reasonDesc3;
    private String reasonDesc4;

    private Map fatcaItem;

    private boolean checkboxOhter1;
    private String jobTitle1;

    private boolean checkboxOhter2;
    private String nameOther;
    private String relationOther;
    private String nameOther1;
    private String relationOther1;
    private String nameOther2;
    private String relationOther2;
    private String nameOther3;
    private String relationOther3;
    private String nameOther4;
    private String relationOther4;

    private boolean checkboxOhter3;
    private String companyOther;
    private String companyJob;

    private boolean checkboxOhter4;
    private String regulator;
    private String otherNation;

    private boolean checkboxOhter5;
    private String companyInfo0_1;
    private String companyInfo0_2;
    private String companyInfo0_3;
    private String companyInfo0_4;
    private String companyInfo1_1;
    private String companyInfo1_2;
    private String companyInfo1_3;
    private String companyInfo1_4;
    private String companyInfo2_1;
    private String companyInfo2_2;
    private String companyInfo2_3;
    private String companyInfo2_4;
    private String companyInfo3_1;
    private String companyInfo3_2;
    private String companyInfo3_3;
    private String companyInfo3_4;
    private String companyInfo4_1;
    private String companyInfo4_2;
    private String companyInfo4_3;
    private String companyInfo4_4;

    private boolean checkboxOhter6;

    private boolean checkboxOhter7;
    private boolean checkboxOhter8;
    private boolean checkboxOhter9;
    private boolean checkboxOhter10;
    private boolean checkboxOhter33;



    // 是否保证金账户
    private Boolean isMarginAccount;

    private String familyAddressRadio;
    private String familyRepublicName;
    private String familyOhterCountry;
    private String otherFamilyRepublic;

    private String familyCity;
    private String familyAddressDetail;
    private String familyAddressNum;
    private String contactAddressRadio;
    private String contactRepublicName;
    private String contactOhterCountry;
    private String ohterContactRepublic;
    private String contactAddressCity;
    private String contactAddressDetail;
    private String contactAddressNum;

    /**
     * 保障金账户相关身份申报资料
     */
    private Boolean benefitOwner;

    private Boolean additionInfoCheckbox1;

    private String accountName1;

    private String accountNum1;

    private Boolean additionInfoCheckbox2;

    private String accountName2;

    private String accountNum2;

    private Boolean additionInfoCheckbox3;

    private String accountName3;

    private String accountNum3;

    private String idCardValidDateEnd;

    private String idCardAddress;

}


