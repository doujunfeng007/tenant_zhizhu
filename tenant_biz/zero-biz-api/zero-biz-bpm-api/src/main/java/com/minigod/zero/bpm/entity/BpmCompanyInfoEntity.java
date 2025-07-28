package com.minigod.zero.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 公司户详细资料表 实体类
 *
 * @author 掌上智珠
 * @since 2023-05-18
 */
@Data
@TableName("bpm_company_info")
@ApiModel(value = "BpmCompanyInfo对象", description = "公司户详细资料表")
public class BpmCompanyInfoEntity implements Serializable {

	private static final long serialVersionUID = 1L;

    /**
     * 自增ID
     */
    @ApiModelProperty(value = "自增ID")
    private Long id;
	/**
	 * 交易账号
	 */
	@ApiModelProperty(value = "交易账号")
	private String tradeAccount;
    /**
     * 预约流水号
     */
    @ApiModelProperty(value = "预约流水号")
    private String appointmentId;
    /**
     * 账户类型[0=现金账户 M=保证金账户]
     */
    @ApiModelProperty(value = "账户类型[0=现金账户 M=保证金账户]")
    private String accountType;
    /**
     * 开户客户来源渠道ID
     */
    @ApiModelProperty(value = "开户客户来源渠道ID")
    private String sourceChannelId;
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
     * A.E.Code
     */
    @ApiModelProperty(value = "A.E.Code")
    private String aecode;
    /**
     * 是否开立北向交易([0=否 1=是])
     */
    @ApiModelProperty(value = "是否开立北向交易([0=否 1=是])")
    private Integer northTrade;
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
     * 客户类型(公司)
     */
    @ApiModelProperty(value = "客户类型(公司)")
    private String companyType;
    /**
     * 所属行业
     */
    @ApiModelProperty(value = "所属行业")
    private String industryRange;
    /**
     * 公司名称（中文）
     */
    @ApiModelProperty(value = "公司名称（中文）")
    private String companyName;
    /**
     * *公司名称（英文）
     */
    @ApiModelProperty(value = "*公司名称（英文）")
    private String companyNameEn;
    /**
     * 联系人姓名
     */
    @ApiModelProperty(value = "联系人姓名")
    private String contactName;
    /**
     * 联系人电话
     */
    @ApiModelProperty(value = "联系人电话")
    private String contactPhone;
    /**
     * 账户名称
     */
    @ApiModelProperty(value = "账户名称")
    private String accountName;
    /**
     * 电子邮箱
     */
    @ApiModelProperty(value = "电子邮箱")
    private String email;
    /**
     * 香港商业登记编号
     */
    @ApiModelProperty(value = "香港商业登记编号")
    private String businessNo;
    /**
     * 传真号码
     */
    @ApiModelProperty(value = "传真号码")
    private String faxNo;
    /**
     * 公司主体类型
     */
    @ApiModelProperty(value = "公司主体类型")
    private String companyEntityType;
    /**
     * 公司注册日期
     */
    @ApiModelProperty(value = "公司注册日期")
    private Date companyRegDate;
    /**
     * 公司注册国家
     */
    @ApiModelProperty(value = "公司注册国家")
    private String companyRegCountry;
    /**
     * 注册成立证书编号
     */
    @ApiModelProperty(value = "注册成立证书编号")
    private String companyRegNo;
    /**
     * 通知发送方式
     */
    @ApiModelProperty(value = "通知发送方式")
    private String msgType;
    /**
     * 结单发送方式
     */
    @ApiModelProperty(value = "结单发送方式")
    private String statementType;
    /**
     * 办事处电话
     */
    @ApiModelProperty(value = "办事处电话")
    private String officePhone;
    /**
     * 办事处国家编码
     */
    @ApiModelProperty(value = "办事处国家编码")
    private String officeCountryCode;
    /**
     * 成立国家注册地址-国家
     */
    @ApiModelProperty(value = "成立国家注册地址-国家")
    private String companyRegRepublicName;
    /**
     * 成立国家注册地址-省/州
     */
    @ApiModelProperty(value = "成立国家注册地址-省/州")
    private String companyRegProvinceName;
    /**
     * 成立国家注册地址-城市
     */
    @ApiModelProperty(value = "成立国家注册地址-城市")
    private String companyRegCityName;
    /**
     * 成立国家注册地址-详细地址
     */
    @ApiModelProperty(value = "成立国家注册地址-详细地址")
    private String companyRegCountyName;
    /**
     * 办事处地址-国家
     */
    @ApiModelProperty(value = "办事处地址-国家")
    private String officeRegRepublicName;
    /**
     * 办事处地址-省/州
     */
    @ApiModelProperty(value = "办事处地址-省/州")
    private String officeRegProvinceName;
    /**
     * 办事处地址-城市
     */
    @ApiModelProperty(value = "办事处地址-城市")
    private String officeRegCityName;
    /**
     * 办事处地址-详细地址
     */
    @ApiModelProperty(value = "办事处地址-详细地址")
    private String officeRegCountyName;
    /**
     * 法定资本（HKD）
     */
    @ApiModelProperty(value = "法定资本（HKD）")
    private String statutoryCapital;
    /**
     * 缴足资本（HKD）
     */
    @ApiModelProperty(value = "缴足资本（HKD）")
    private String paidUpCapital;
    /**
     * 法定股份数量
     */
    @ApiModelProperty(value = "法定股份数量")
    private String statutoryNum;
    /**
     * 已发行股份数量
     */
    @ApiModelProperty(value = "已发行股份数量")
    private String issuedNum;
    /**
     * 法定每股价格（HKD)
     */
    @ApiModelProperty(value = "法定每股价格（HKD)")
    private String statutoryNumPrice;
    /**
     * 已发行每股价格（HKD）
     */
    @ApiModelProperty(value = "已发行每股价格（HKD）")
    private String issuedNumPrice;
    /**
     * 是否提供最近的财务账目记录？([0=否 1=是])
     */
    @ApiModelProperty(value = "是否提供最近的财务账目记录？([0=否 1=是])")
    private Integer financeFlag;
    /**
     * 付款方式
     */
    @ApiModelProperty(value = "付款方式")
    private String payType;
    /**
     * 公司-投资资金来源
     */
    @ApiModelProperty(value = "公司-投资资金来源")
    private String capitalSource;
    /**
     * 公司-财富来源
     */
    @ApiModelProperty(value = "公司-财富来源")
    private String wealthSource;
    /**
     * 投资目标类型
     */
    @ApiModelProperty(value = "投资目标类型")
    private String investTarget;
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
     * 基金投资经验类型[0=没有 1=少于一年 2=一至五年  3=五年以上]
     */
    @ApiModelProperty(value = "基金投资经验类型[0=没有 1=少于一年 2=一至五年  3=五年以上]")
    private Integer fundInvestmentExperienceType;
    /**
     * 是否了解衍生产品[0=否 1=是]
     */
    @ApiModelProperty(value = "是否了解衍生产品[0=否 1=是]")
    private Integer isKnowDerivativeProducts;
    /**
     * 我曾接受有关衍生产品的培训或修读相关课程
     */
    @ApiModelProperty(value = "我曾接受有关衍生产品的培训或修读相关课程")
    private Integer derivativeProductsStudyType;
    /**
     * 我现时或过去拥有与衍生产品有关的工作经验
     */
    @ApiModelProperty(value = "我现时或过去拥有与衍生产品有关的工作经验")
    private Integer financingInstitutionWorkExperienceType;
    /**
     * 您在过去三年是否曾进行过至少五次任何衍生产品的交易（不论是否在交易所买卖）[0=否 1=是]
     */
    @ApiModelProperty(value = "您在过去三年是否曾进行过至少五次任何衍生产品的交易（不论是否在交易所买卖）[0=否 1=是]")
    private Integer isTradedDerivativeProducts;
    /**
     * Product Risk Rating
     */
    @ApiModelProperty(value = "Product Risk Rating")
    private Integer productRiskRating;
    /**
     * delivery Risk Rating
     */
    @ApiModelProperty(value = "delivery Risk Rating")
    private Integer deliveryRiskRating;
    /**
     * client Risk Rating
     */
    @ApiModelProperty(value = "client Risk Rating")
    private Integer clientRiskRating;
    /**
     * country Risk Rating
     */
    @ApiModelProperty(value = "country Risk Rating")
    private Integer countryRiskRating;
    /**
     * over_all Risk Rating
     */
    @ApiModelProperty(value = "over_all Risk Rating")
    private Integer overAllRiskRating;
    /**
     * 最近一次kyc时间
     */
    @ApiModelProperty(value = "最近一次kyc时间")
    private Date lastKycDay;
    /**
     * 下一次kyc时间
     */
    @ApiModelProperty(value = "下一次kyc时间")
    private Date nextKycDay;
    /**
     * kyc修改意见
     */
    @ApiModelProperty(value = "kyc修改意见")
    private String kycRemark;
    /**
     * 初始交易密码（加密）
     */
    @ApiModelProperty(value = "初始交易密码（加密）")
    private String initPwd;
    /**
     * 见证人姓名
     */
    @ApiModelProperty(value = "见证人姓名")
    private String witnessUser;
    /**
     * 见证人类型
     */
    @ApiModelProperty(value = "见证人类型")
    private String witnessesType;
    /**
     * 牌照号码
     */
    @ApiModelProperty(value = "牌照号码")
    private String licenseNumber;
    /**
     * 开户时间
     */
    @ApiModelProperty(value = "开户时间")
    private Date openAccountTime;
    /**
     * 创建用户
     */
    @ApiModelProperty(value = "创建用户")
    private String createUser;
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
     * 记录状态[0-无效 1-有效]
     */
    @ApiModelProperty(value = "记录状态[0-无效 1-有效]")
    private Integer delSts;
    /**
     * 版本号
     */
    @ApiModelProperty(value = "版本号")
    private Integer lockVersion;
    /**
     * 中华通BCANNO
     */
    @ApiModelProperty(value = "中华通BCANNO")
    private String bcanNo;
    /**
     * 中华通BCANNO状态
     */
    @ApiModelProperty(value = "中华通BCANNO状态")
    private String bcanStatus;

}
