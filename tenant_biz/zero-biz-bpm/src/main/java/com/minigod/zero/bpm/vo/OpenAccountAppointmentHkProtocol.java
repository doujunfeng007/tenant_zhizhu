package com.minigod.zero.bpm.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: sunline
 * @date: 2018/7/31 15:22
 * @version: v1.0
 */
@Data
public class OpenAccountAppointmentHkProtocol {

    // 开户接入方式[1=H5开户 2=APP开户 3=BPM后台开户 4=香港H5开户]
    @JSONField(name = "openAccountAccessWay")
    private Integer openAccountAccessWay;

    // 客户用户号
    @JSONField(name = "userId")
    private String userId;

    // 客户中文名
    @JSONField(name = "clientNameCn")
    private String clientNameCn;

    // 中文名拼音
    @JSONField(name = "clientName")
    private String clientName;

    // 证件类型
    @JSONField(name = "idKind")
    private Integer idKind;

    // 证件号码
    @JSONField(name = "idNo")
    private String idNo;

    // 性别[0=男 1=女 2=其它]
    @JSONField(name = "sex")
    private Integer sex;

    // 邮箱
    @JSONField(name = "email")
    private String email;


    @JSONField(name = "contactAddress")
    private String contactAddress;

    // 联系地址中文
    @JSONField(name = "contactAddressCn")
    private String contactAddressCn;

    // old 就业情况类型 [1=受雇 2=自营 3=退休 4=学生 5=其他]
    // new 就业情况类型 [1=受雇 2=自营 3=退休 4=学生 5=其他 6 =务农 7=待业 8=自由职业者]
    @JSONField(name = "professionCode")
    private Integer professionCode;

    // 就业情况其它说明
    @JSONField(name = "otherProfession")
    private String otherProfession;

    // 公司名称
    @JSONField(name = "companyName")
    private String companyName;

    // 职位
    @JSONField(name = "jobPosition")
    private String jobPosition;

    // 公司业务性质或行业类型
    @JSONField(name = "industryRange")
    private String industryRange;

    // 年收入范围类型  [1=<20万 2=20万-50万 3=50万-100万 4=100万-500万 5=>500万]
    @JSONField(name = "income")
    private Integer income;

    // 净资产范围类型   [1=<50万 2=50万-250万 3=250万-500万 4=>500万]
    @JSONField(name = "netCapital")
    private Integer netCapital;

    // 投资目标类型 [NEW 1=股息收入 2=短期投资 3=长期投资 4=其他]
    @JSONField(name = "investTarget")
    private List<Integer> investTarget;

    // 投资目标其它类型说明
    @JSONField(name = "investTargetOther")
    private String investTargetOther;

    // 股票投资经验类型 [0=未知 1=没有经验 2=少于一年 3=一至三年 4=三至五年 5=五年以上]
    @JSONField(name = "stocksInvestmentExperienceType")
    private Integer stocksInvestmentExperienceType;

    // 认证股权投资经验类型 [0=未知 1=没有经验 2=少于一年 3=一至三年 4=三至五年 5=五年以上]
    @JSONField(name = "warrantsInvestmentExperienceType")
    private Integer warrantsInvestmentExperienceType;

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

    // 账户受益人类型[0=自己 1=其他]
    @JSONField(name = "ownerOfAccountType")
    private Integer ownerOfAccountType;

    // 账户其他受益人详细信息，json[{ownName:xxx, ownAddress:xxx},{ownName:xxx, ownAddress:xxx}]
    @JSONField(name = "ownerOfAccountsDetail")
    private List<OwnerOfAccountDetail> ownerOfAccountsDetail;

    // 是否证券及期货事务监察委员会之注册公司雇员或注册人 [0=否 1=是]
    @JSONField(name = "isSfcEmployee")
    private Integer isSfcEmployee;

    // 注册人名称
    @JSONField(name = "registeredPersonName")
    private String registeredPersonName;

    // 是否与yff集团之任何董事、职员或代表有亲属关系 [0=否 1=是]
    @JSONField(name = "isClerkRelation")
    private Integer isClerkRelation;

    // 职员关系
    @JSONField(name = "clerkRelationInfo")
    private String clerkRelationInfo;

    // 关联的职员名称
    @JSONField(name = "clerkName")
    private String clerkName;

    // 是否有其他股票行或银行的股票户口资料[0=否 1=是]
    @JSONField(name = "hasOtherAccount")
    private Integer hasOtherAccount;

    // 其它账号详细信息,json[{bankName:xxx, accountNumber:xxx},{bankName:xxx, accountNumber:xxx}]
    @JSONField(name = "otherAccountsDetailInfo")
    private List<OtherAccountDetailInfo> otherAccountsDetailInfo;

    // 手机号
    @JSONField(name = "phoneNumber")
    private String phoneNumber;

    // 图片信息
    @JSONField(name = "openAccountImagesInfo")
    private List<OpenAccountImageInfo> openAccountImagesInfo;

    // 四要素验证信息
    @JSONField(name = "openAccountBankVerityInfo")
    private List<OpenAccountBankVerityInfo> openAccountBankVerityInfo;

    // 公司电话
    @JSONField(name = "companyPhoneNumber")
    private String companyPhoneNumber;

    // 生日
    @JSONField(name = "birthday")
    private String birthday;

    // 最后更新时间
    @JSONField(name = "lastUpdateTime")
    private Long lastUpdateTime;

    // 用户来源渠道ID
    @JSONField(name = "userSourceChannelId")
    private Long userSourceChannelId;

    // 推荐人用户ID
    @JSONField(name = "recommendUserId")
    private String recommendUserId;

    // 资金来源
    @JSONField(name = "fundSourceChannelType")
    private List<Integer> fundSourceChannelType;

    // 银行名称
    @JSONField(name = "bankNo")
    private String bankNo;

    // 银行账号
    @JSONField(name = "bankAccount")
    private String bankAccount;

    // firstIncome  首次存入
    @JSONField(name = "firstIncome")
    private String firstIncome;

}
