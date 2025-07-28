package com.minigod.zero.trade.vo.sjmb.req;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chen
 * @ClassName OpenAccountReq.java
 * @Description TODO
 * @createTime 2024年02月02日 10:32:00
 */
@Data
public class OpenAccountReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 客户号
	 */
	private String tradeAccount;

	/**
	 * 资金账号
	 */
	private String fundAccount;

	/**
	 * 婚姻：1.单身 2.已婚
	 */
	private Integer maritalStatus;

	/**
	 * 客户姓名
	 */
	private String clientName;

	/**
	 * 中文名
	 */
	private String firstName;

	/**
	 * 中间名
	 */
	private String middleName;

	/**
	 * 中文姓
	 */
	private String lastName;

	/**
	 * 英文名
	 */
	private String firstNameEn;

	/**
	 * 英文姓
	 */
	private String lastNameEn;

	/**
	 * 国籍 三位国家编码
	 */
	private String nationality;

	/**
	 * 生日  yyyy-MM-dd
	 */
	private String birthDate;


	/**
	 * 性别 MALE  男  FEMALE  女
	 */
	private String gender;

	/**
	 * 手机区号  +86 +852 +853
	 */
	private String areaCode;

	/**
	 *  电话号码
	 */
	private String phoneNumber;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 证件类型  1 内地身份证  2 香港身份证  3 澳门身份证 4 护照  5 驾照 6 其他
	 */
	private Integer idType;

	/**
	 * 证件号码
	 */
	private String idNo;

	/**
	 * 通讯地址国家  三位字母国家编码
	 */
	private String contactCountry;

	/**
	 * 通讯省份
	 */
	private String contactState;

	/**
	 * 通讯城市
	 */
	private String contactCity;

	/**
	 * 通讯区
	 */
	private String contactDistrict;

	/**
	 * 通讯地址
	 */
	private String contactAddress;

	/**
	 * 通讯邮编
	 */
	private String contactPostal;


	/**
	 * 资金账号账号类型  1 现金  2 融资
	 */
    private Integer fundAccountType;


	/**
	 * 是否为证券从业者
	 */
	private Boolean brokerPractitioner;

	/**
	 * 是否为控股股东
	 */
	private Boolean decisionMaker;

	/**
	 * 语言 en_US zh_CN zh_HK
	 */
	private String language;

	/**
	 * 地区 CN   HK   OTHERS
	 */
	private String userRegion;


	/**
	 * 是否开通A股
	 */
	private Integer isOpenCnMarket;

	/**
	 * 是否开通港股
	 */
	private Integer isOpenHkMarket;

	/**
	 * 是否开通美股
	 */
	private Integer isOpenUsMarket;

	/**
	 * 纳税国家
	 */
	private String countryOfTax;



	/**
	 * 税务编号
	 */
	private String taxNumber;

	/**
	 * 佣金模板id，不填绑定系统默认模板
	 */
	private String commissionTId;

	/**
	 * 费用模板id，不填绑定系统默认模板
	 */
	private String feeTId;

	/**
	 * 保证金模板id，不填绑定系统默认模板
	 */
	private String marginTId;

	/**
	 * 利率模板id，不填绑定系统默认模板
	 */
	private String interestTId;


	/**
	 * 账户类型[0、未知  1、个人账户  2、联名账户   3、公司账户]
	 */
	private Integer accountType;

	/**
	 * 出生地区  字典 country_or_region
	 */
	private String placeOfBirth;

	/**
	 * yyyy-mm-dd 身份证生效日期
	 */
	private String idCardValidDateStart;
	/**
	 * yyyy-mm-dd 身份证失效日期
	 */
	private String idCardValidDateEnd;

	/**
	 * 英文名
	 */
	private String clientNameSpell;

	/**
	 * 教育程度 字典 education_level  1 小学 2 中学 3 大学或以上 4 大专
	 */
	private Integer educationLevel;

	/**
	 * 就业情况 字典 profession_code  1 受雇 2 自雇 3 无业 4 退休 5 家庭主妇 6 学生
	 */
	private Integer professionCode;

	/**
	 * 年收入范围类型[1=<25万 2=25万-50万 3=50万-100万 4=100万-500万   5=500万到1000万 6=1000万以上]
	 */
	private Integer annualIncome;

	/**
	 * 净资产  1 ≤50 万
	 * 2 50–100(含) 万
	 * 3 100-500(含) 万
	 * 4 500-1000(含) 万
	 * 5 1000-5000(含) 万  6 5000万-1亿(含) 7 >1亿
	 */
	private Integer netAsset;

	/**
	 * 	财富来源  多个用逗号隔开  1 薪俸 2 储蓄 3  投资回报 4 遗产/赠物 5 退休金 6 出售物业/资产 7  营业收入 8 租金收入 99 其他
	 */
	private String expectedCapitalSource;


	/**
	 * 	资金来源 多个用逗号隔开  1 薪俸/营业收入 2 退休金 3 投资回报  99  其他
	 */
	private String capitalOptions;

	/**
	 * 投资目标 多个用逗号隔开
	 */
	private String investTarget;

	/**
	 * 交易频率 1 ==  0-20(含) 2 == 20-50(含) 3 == 50-100(含) 4 == 100-200(含) 5 == >200
	 */
	private Integer tradeFrequency;

	/**
	 * 税务信息
	 */
	private List<AccountTaxationInfo> taxationInfoList;

	/**
	 * 图片的url
	 */
	private String signImage;

	/**
	 * 地址图片 是地址图片 不是pdf
	 */
	private String addressImageUrl;

	/**
	 * 身份证图片url
	 */
	private String idCardIdFrontUrl;

	/**
	 * ip
	 */
	private String ipAddress;

	/**
	 * 证件签发日期
	 */
	private String idIssueDate;








}
