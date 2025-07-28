package com.minigod.zero.trade.afe.req;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.trade.afe.req.AreateAccountReq
 * @Description: AFE开户接口全部 字段
 * @Author: linggr
 * @CreateDate: 2024/4/12 10:19
 * @Version: 1.0
 */
@Data
public class CreateAccountReq extends ReqVO{
	//特定的客户端ID或“（AUTO）”，让后台办公系统生成客户端ID
	@JSONField(name ="ClientID")
	private String clientID;

	//特定的客户端ID或“（AUTO）”让后台办公室系统生成帐户ID
	@JSONField(name ="AccountID")
	private String accountID;
	/**
	 * 帳戶類別
	 * M - Margin Client
	 * X - Custodian Client
	 */
	@JSONField(name ="AccountTypeID")
	private String accountTypeID;

	/**
	 * 0-个人   1-联合账户   5-机构
	 */
	@JSONField(name ="RegistrationType")
	private String registrationType;

	/**
	 * 英文名称最多150个字符（G3SB）
	 */
	@JSONField(name ="Name")
	private String name;

	/**
	 * Chinese Name
	 */
	@JSONField(name ="NameEx")
	private String nameEx;
	/**
	 * 性别
	 * M – Male 男
	 * F – Female 女
	 */
	@JSONField(name ="Gender")
	private String gender;
	/**
	 * 在接口文件中格式化DD/MM/YYYY
	 *
	 * 在XML节点中格式化YYYY-MM-DD
	 */
	@JSONField(name ="DateOfBirth")
	private String dateOfBirth;
	/**
	 * 国籍 请参考“国籍/国家列表”标签中的价值选项
	 */
	@JSONField(name ="NationalityID")
	private String nationalityID;
	/**
	 * 出生地
	 */
	@JSONField(name ="PlaceOfBirth")
	private String placeOfBirth;

	/**
	 * 婚姻狀況
	 * D - Divorced 離異
	 * M - Married 已婚
	 * U - Unmarried 未婚
	 * W - Widowed 喪偶
	 */
	@JSONField(name ="MaritalStatus")
	private String maritalStatus;

	/**
	 * B - BR 商業登記證號碼
	 * C - CI 公司註冊號碼
	 * I - ID 身份證
	 * P - Passport 護照
	 */
	@JSONField(name ="IDType")
	private String idType;

	/**
	 * 証件號碼
	 * Max 30 characters
	 */
	@JSONField(name ="IDNumber")
	private String idNumber;

	/**
	 * 發行地
	 * ID的签发地点，请参考标签“国籍/国家列表”的价值选项
	 */
	@JSONField(name ="IDIssuePlace")
	private String idIssuePlace;

	/**
	 * 發行日期
	 * ID的签发日期
	 */
	@JSONField(name ="IDIssueDate")
	private String idIssueDate;

	/**
	 * 住宅電話
	 * Max 30 characters (G3SB)
	 */
	@JSONField(name ="HomeNumber")
	private String homeNumber;

	/**
	 * 移动电话
	 * Max 30 characters (G3SB)
	 */
	@JSONField(name ="MobileNumber")
	private String mobileNumber;

	/**
	 * 辦公室電話
	 * Max 30 characters (G3SB)
	 */
	@JSONField(name ="OfficeNumber")
	private String officeNumber;

	/**
	 * 分行
	 * Max 30 characters (G3SB)
	 */
	@JSONField(name ="BranchID")
	private String branchID;

	/**
	 * 客戶主任編號
	 * Max 15 characters (G3SB)
	 */
	@JSONField(name ="AEID")
	private String aeID;

	/**
	 * 帳戶等級編號
	 * Max 15 characters (G3SB)
	 */
	@JSONField(name ="AccountClassID")
	private String accountClassID;

	/**
	 * 備註
	 * Max 4000 characters (G3SB)
	 */
	@JSONField(name ="Remark")
	private String remark;

	/**
	 * 聯絡名稱
	 * Max 150 characters (G3SB)
	 */
	@JSONField(name ="ContactName")
	private String contactName;

	/**
	 * 接口文档无 示例有  中文名称
	 */
	@JSONField(name ="ContactNameEx")
	private String contactNameEx;
	/**
	 * 地址 1
	 * Maximum 100 characters (G3SB)
	 */
	@JSONField(name ="MailAddress1")
	private String mailAddress1;
	/**
	 * 地址 2
	 * Maximum 100 characters (G3SB)
	 */
	@JSONField(name ="MailAddress2")
	private String mailAddress2;

	/**
	 * 地址 3
	 * Maximum 100 characters (G3SB)
	 */
	@JSONField(name ="MailAddress3")
	private String mailAddress3;

	/**
	 * 地址 4
	 * Maximum 100 characters (G3SB)
	 */
	@JSONField(name ="MailAddress4")
	private String mailAddress4;

	/**
	 * 邮件国家代表后台办公室帐户邮件联系人的国家设置请参阅“国籍/国家列表”的价值选项
	 */
	@JSONField(name ="MailCountryID")
	private String mailCountryID;

	/**
	 * 地址类型
	 * P is Permanent
	 * C is Correspondence
	 */
	@JSONField(name ="AddressType")
	private String addressType;

	/**
	 * 住宅地址联系人
	 *Contact Name for the Residential Address
	 * Max 60 characters (G2SB)
	 * Max 150 characters (G3SB)
	 */
	@JSONField(name ="ResidentialContactName")
	private String residentialContactName;

	/**
	 * Residential address line 1
	 * 住址 1
	 * Maximum 40 characters (G2SB)
	 * Maximum 100 characters (G3SB)
	 */
	@JSONField(name ="ResidentialAddress1")
	private String residentialAddress1;

	@JSONField(name ="ResidentialAddress2")
	private String residentialAddress2;

	@JSONField(name ="ResidentialAddress3")
	private String residentialAddress3;

	@JSONField(name ="ResidentialAddress4")
	private String residentialAddress4;

	/**
	 * Residential Address Country
	 * represent country setting of Account mail
	 * contact in back office
	 * Please refer to tab "List of Nationality /
	 * Country" for value options
	 */
	@JSONField(name ="ResidentialMailCountryID")
	private String residentialMailCountryId;

	@JSONField(name ="ResidentCountryID")
	private String residentCountryId;

	@JSONField(name ="ResidentialAddressType")
	private String residentialAddressType;
	/**
	 * Y 通过邮件接收报表/贸易确认函/其他通知
	 * N 不要收到通过邮件发送的对账单/交易确认函/其他通知
	 */
	@JSONField(name ="MailContactReportFlag")
	private String mailContactReportFlag;

	/**
	 * 電郵地址
	 * Maximum 100 characters (G3SB)
	 */
	@JSONField(name ="EMailAddress")
	private String emailAddress;

	/**
	 * Y 通过邮件接收报表/贸易确认函/其他通知
	 * N 不要收到通过邮件发送的对账单/交易确认函/其他通知
	 */
	@JSONField(name ="EMailContactReportFlag")
	private String emailContactReportFlag;

	/**
	 * CHS – Chinese Simplified
	 * CHT – Chinese Traditional
	 * EN - English
	 */
	@JSONField(name ="StatementLanguageID")
	private String statementLanguageId;

	@JSONField(name ="FaxNumber")
	private String faxNumber;

	@JSONField(name ="ReferType")
	private String referType;

	@JSONField(name ="ReferBy")
	private String referBy;

	/**
	 * 1-Primary  初级
	 * 2-Secondary   中级
	 * 3-College  学院
	 * 4-University or above  大学或者以上
	 */
	@JSONField(name ="EducationLevel")
	private String educationLevel;

	@JSONField(name ="JointClientID")
	private String jointClientId;

	@JSONField(name ="JointClientRelationship")
	private String jointClientRelationship;

	@JSONField(name ="JointClientRelationshipRemark")
	private String jointClientRelationshipRemark;

	@JSONField(name ="JointGender")
	private String jointGender;

	@JSONField(name ="JointDateOfBirth")
	private String jointDateOfBirth;

	@JSONField(name ="JointNationalityID")
	private String jointNationalityId;

	@JSONField(name ="JointPlaceOfBirth")
	private String jointPlaceOfBirth;

	@JSONField(name ="JointMaritalStatus")
	private String jointMaritalStatus;

	@JSONField(name ="JointIDType")
	private String jointIDType;

	@JSONField(name ="JointIDNumber")
	private String jointIDNumber;

	@JSONField(name ="JointMailAddress")
	private String jointMailAddress;

	@JSONField(name ="JointMailAddress2")
	private String jointMailAddress2;


	@JSONField(name ="JointMailAddress3")
	private String jointMailAddress3;

	@JSONField(name ="JointMailAddress4")
	private String jointMailAddress4;

	@JSONField(name ="JointMailCountryID")
	private String jointMailCountryId;

	@JSONField(name ="JointResidentCountryID")
	private String jointResidentCountryId;

	@JSONField(name ="JointIDIssuePlace")
	private String jointIDIssuePlace;

	@JSONField(name ="JointIDIssueDate")
	private String jointIDIssueDate;

	@JSONField(name ="JointHomeNumber")
	private String jointHomeNumber;

	@JSONField(name ="JointMobileNumber")
	private String jointMobileNumber;

	@JSONField(name ="JointOfficeNumber")
	private String jointOfficeNumber;

	@JSONField(name ="JointEMailAddress")
	private String jointEMailAddress;

	@JSONField(name ="JointEMailContactReportFlag")
	private String jointEMailContactReportFlag;

	@JSONField(name ="JointEducationLevel")
	private String jointEducationLevel;

	/**
	 * Are you the ultimate beneficial owner(s) of
	 * the Account who benefiting from the
	 * transactions and bearing the risk?
	 * 閣下是否受益於交易及承擔其風險的帳戶
	 * 的最終實益擁有人?
	 * Y - Ultimate beneficial owner
	 * N - Not ultimate beneficial owner
	 */
	@JSONField(name ="IdentityVerificationQ01")
	private String identityVerificationQ01;

	@JSONField(name ="IdentityVerificationQ01Name")
	private String identityVerificationQ01Name;

	@JSONField(name ="IdentityVerificationQ01NameEx")
	private String identityVerificationQ01NameEx;

	@JSONField(name ="IdentityVerificationQ01Relationship")
	private String identityVerificationQ01Relationship;

	@JSONField(name ="IdentityVerificationQ01IDNumber")
	private String identityVerificationQ01IDNumber;

	@JSONField(name ="IdentityVerificationQ01IDIssuePlace")
	private String identityVerificationQ01IDIssuePlace;

	@JSONField(name ="IdentityVerificationQ01Address")
	private String identityVerificationQ01Address;

	@JSONField(name ="IdentityVerificationQ01Birthday")
	private String identityVerificationQ01Birthday;

	@JSONField(name ="IdentityVerificationQ01Employer")
	private String identityVerificationQ01Employer;

	@JSONField(name ="IdentityVerificationQ01Industry")
	private String identityVerificationQ01Industry;

	@JSONField(name ="IdentityVerificationQ01Nationality")
	private String identityVerificationQ01Nationality;

	@JSONField(name ="IdentityVerificationQ02")
	private String identityVerificationQ02;

	@JSONField(name ="IdentityVerificationQ02RegisterPerson")
	private String identityVerificationQ02RegisterPerson;

	@JSONField(name ="IdentityVerificationQ02RegisterCorporation")
	private String identityVerificationQ02RegisterCorporation;

	@JSONField(name ="IdentityVerificationQ03")
	private String identityVerificationQ03;

	@JSONField(name ="IdentityVerificationQ03Corporation")
	private String identityVerificationQ03Corporation;

	@JSONField(name ="IdentityVerificationQ04")
	private String identityVerificationQ04;

	@JSONField(name ="IdentityVerificationQ04Name")
	private String identityVerificationQ04Name;

	@JSONField(name ="IdentityVerificationQ04Relationship")
	private String identityVerificationQ04Relationship;

	@JSONField(name ="IdentityVerificationQ05")
	private String identityVerificationQ05;

	@JSONField(name ="IdentityVerificationQ05Name")
	private String identityVerificationQ05Name;

	@JSONField(name ="IdentityVerificationQ05PublicFunction")
	private String identityVerificationQ05PublicFunction;

	@JSONField(name ="IdentityVerificationQ05Relationship")
	private String identityVerificationQ05Relationship;

	@JSONField(name ="Position")
	private String position;

	/**
	 * 職業 请参考“职业列表”选项
	 */
	@JSONField(name ="Occupation")
	private String occupation;


	private String otherOccupation;
	/**
	 * 服務年資
	 */
	@JSONField(name ="YearOfService")
	private String yearOfService;

	/**
	 * 公司名称 Max. 250 characters
	 */
	@JSONField(name ="CompanyName")
	private String companyName;

	/**
	 * 公司地址 Max. 600 characters
	 */
	@JSONField(name ="CompanyAddress")
	private String companyAddress;

	/**
	 * 就業狀況
	 * E - Employed 在職
	 * M - Self-Employment 创业
	 * U - Unemployed 待業
	 * O - Other 其他
	 */
	@JSONField(name ="EmploymentStatus")
	private String employmentStatus;

	@JSONField(name ="OtherEmploymentStatus")
	private String otherEmploymentStatus;

	/**
	 * 職業
	 */
	@JSONField(name ="Industry")
	private String industry;

	/**
	 * 每年收入 (港幣)
	 * 0 - ≤ $150,000
	 * 1 - $150,001 - $500,000
	 * 2 - $500,001 - $1,000,000
	 * 3 - $1,000,001 - $5,000,000
	 * 4 - >$5,000,001
	 */
	@JSONField(name ="AnnualSalary")
	private String annualSalary;

	/**
	 * 總資產淨值 (港幣)
	 * 0 - ≤ HKD 250,000
	 * 1 - HKD 250,001 to 1,000,000
	 * 2 - HKD 1,000,001 to 5,000,000
	 * 3 - HKD 5,000,001 to 20,000,000
	 * 4 - More than HKD 20,000,001
	 */
	@JSONField(name ="TotalAssetValue")
	private String totalAssetValue;

	/**
	 * 收入來源備註
	 * B - Business Income 營商收益
	 * N - Inheritance/ Gift 遺產/餽贈
	 * I - Investment Return 投資收益
	 * A - Salary 薪酬
	 * P - Pension 退休金
	 * S - Saving 儲蓄
	 * R - Rental Income 租金收入
	 * O - Others 其他(Please provide the other
	 * source of funds in
	 * SourceOfIncomeRemark)
	 * If multiple options are chosen, please add
	 * separator “|” between options, e.g.:
	 * A|B
	 */
	@JSONField(name ="SourceOfIncome")
	private String sourceOfIncome;

	/**
	 * 資產來源備註
	 */
	@JSONField(name ="SourceOfIncomeRemark")
	private String sourceOfIncomeRemark;

	/**
	 * B - Business Income 營商收益
	 * N - Inheritance/ Gift 遺產/餽贈
	 * I - Investment Return 投資收益
	 * A - Salary 薪酬
	 * P - Pension 退休金
	 * S - Saving 儲蓄
	 * R - Rental Income 租金收入
	 * T - Others 其他 (Please provide the other
	 * source of funds in
	 * SourceOfFundsRemark)
	 * If multiple options are chosen, please add
	 * separator “|” between options, e.g.:
	 * F|A|I
	 */
	@JSONField(name ="SourceOfFunds")
	private String sourceOfFunds;

	/**
	 * 資產來源備註
	 */
	@JSONField(name ="SourceOfFundsRemark")
	private String sourceOfFundsRemark;

	/**
	 * Options configurable in back office
	 * M - Mortgaged/Monthly Instalment (HK$)
	 * 按揭/每月供款 (港元)
	 * T - Others 其他
	 * O - Owned 自置
	 * R - Rented/Monthly Rental (HK$) 租用/每
	 * 月供款 (港元)
	 * P - With Parents 與父母同住
	 */
	@JSONField(name ="OwnershipOfResidence")
	private String ownershipOfResidence;
	/**
	 * 投资经验-股票證券
	 * 0 - <1 Year <1
	 * 1 - 1-3 Year(s) 1-3
	 * 2 - 3-5 Year(s) 3-5
	 * 3 - >5 Year(s) >5
	 * 4 - Nil 沒有
	 */
	@JSONField(name ="InvestmentType01")
	private String investmentType01;
	/**
	 * 投资经验-未来和期权期貨及期權合約
	 * 0 - <1 Year <1
	 * 1 - 1-3 Year(s) 1-3
	 * 2 - 3-5 Year(s) 3-5
	 * 3 - >5 Year(s) >5
	 * 4 - Nil 沒有
	 */
	@JSONField(name ="InvestmentType02")
	private String investmentType02;

	/**
	 * 投资经验-存款证明/债券存款證/債券
	 * 0 - <1 Year <1
	 * 1 - 1-3 Year(s) 1-3
	 * 2 - 3-5 Year(s) 3-5
	 * 3 - >5 Year(s) >5
	 * 4 - Nil 沒有
	 */
	@JSONField(name ="InvestmentType03")
	private String investmentType03;

	/**
	 * 投资经验-单位信托/共同基金單位信託/互惠基金
	 * 0 - <1 Year <1
	 * 1 - 1-3 Year(s) 1-3
	 * 2 - 3-5 Year(s) 3-5
	 * 3 - >5 Year(s) >5
	 * 4 - Nil 沒有
	 */
	@JSONField(name ="InvestmentType04")
	private String investmentType04;

	/**
	 * 投资经验-杠杆外汇
	 * 0 - <1 Year <1
	 * 1 - 1-3 Year(s) 1-3
	 * 2 - 3-5 Year(s) 3-5
	 * 3 - >5 Year(s) >5
	 * 4 - Nil 沒有
	 */
	@JSONField(name ="InvestmentType05")
	private String investmentType05;

	/**
	 * Investment Experience - Callable Bull/Bear
	 * Contract (CBBC) or Warrants 牛熊證/窩輪
	 * 0 - <1 Year <1
	 * 1 - 1-3 Year(s) 1-3
	 * 2 - 3-5 Year(s) 3-5
	 * 3 - >5 Year(s) >5
	 * 4 - Nil 沒有
	 */
	@JSONField(name ="InvestmentType06")
	private String investmentType06;

	/**
	 * Investment Experience - Leveraged/Inverse
	 * Products 槓桿及逆向產品
	 * 0 - <1 Year <1
	 * 1 - 1-3 Year(s) 1-3
	 * 2 - 3-5 Year(s) 3-5
	 * 3 - >5 Year(s) >5
	 * 4 - Nil 沒有
	 */
	@JSONField(name ="InvestmentType07")
	private String investmentType07;

	/**
	 * 投資目標
	 * A – Aggressive 進取
	 * G - Growth 增長
	 * C - Conservative 保守
	 * O - Others 其他
	 */
	@JSONField(name ="InvestmentTarget")
	private String investmentTarget;

	/**
	 * 每月交易頻率 备注
	 *
	 */
	@JSONField(name ="InvestmentFrequency")
	private String investmentFrequency;

	/**
	 * 税务居民数量（最多5个税务居住地）
	 */
	@JSONField(name ="NumberOfTaxResidency")
	private String numberOfTaxResidency;

	/**
	 * 第一次税务居住地请参阅标签“国籍/国家名单”中的价值选项
	 */
	@JSONField(name ="TaxResidency1")
	private String taxResidency1;

	/**
	 * 第1个税务识别号
	 */
	@JSONField(name ="TIN1")
	private String tin1;
	/**
	 * a 賬戶持有人的居留司法管轄區並沒有向其居民發出稅務編號
	 * b 賬戶持有人不能取得稅務編號. 如選取這一理由, 解釋賬戶持有人不能取得稅務編號的原因
	 * c 賬戶持有人毋須提供稅務編號. 居留司法管轄區的主管機關不需要賬戶持有人披露稅務編號
	 */
	@JSONField(name ="TINUnavailableReason1")
	private String tinUnavailableReason1;

	/**
	 * 适用于不可用的原因B
	 */
	@JSONField(name ="TINUnavailableReasonRemark1")
	private String tinUnavailableReasonRemark1;

	/**
	 * 第二个税务居住地请参阅标签“国籍/国家名单”中的价值选项
	 */
	@JSONField(name ="TaxResidency2")
	private String taxResidency2;

	/**
	 * 第2个税务识别号
	 */
	@JSONField(name ="TIN2")
	private String tin2;

	/**
	 * 适用于不可用的原因B
	 */
	@JSONField(name ="TINUnavailableReason2")
	private String tinUnavailableReason2;

	/**
	 * 适用于不可用的原因B
	 */
	@JSONField(name ="TINUnavailableReasonRemark2")
	private String tinUnavailableReasonRemark2;

	/**
	 * 第三个税务居住地请参阅标签“国籍/国家名单”中的价值选项
	 */
	@JSONField(name ="TaxResidency3")
	private String taxResidency3;

	/**
	 * 第3个税务识别号
	 */
	@JSONField(name ="TIN3")
	private String tin3;

	/**
	 * 适用于不可用的原因B
	 */
	@JSONField(name ="TINUnavailableReason3")
	private String tinUnavailableReason3;

	/**
	 * 适用于不可用的原因B
	 */
	@JSONField(name ="TINUnavailableReasonRemark3")
	private String tinUnavailableReasonRemark3;

	/**
	 * 第四个税务居住地请参阅标签“国籍/国家名单”中的价值选项
	 */
	@JSONField(name ="TaxResidency4")
	private String taxResidency4;

	/**
	 * 第4个税务识别号
	 */
	@JSONField(name ="TIN4")
	private String tin4;

	/**
	 * 适用于不可用的原因B
	 */
	@JSONField(name ="TINUnavailableReason4")
	private String tinUnavailableReason4;

	/**
	 * 适用于不可用的原因B
	 */
	@JSONField(name ="TINUnavailableReasonRemark4")
	private String tinUnavailableReasonRemark4;

	/**
	 * 第五个税务居住地请参阅标签“国籍/国家名单”中的价值选项
	 */
	@JSONField(name ="TaxResidency5")
	private String taxResidency5;

	/**
	 * 第5个税务识别号
	 */
	@JSONField(name ="TIN5")
	private String tin5;

	/**
	 * 适用于不可用的原因B
	 */
	@JSONField(name ="TINUnavailableReason5")
	private String tinUnavailableReason5;

	/**
	 * 适用于不可用的原因B
	 */
	@JSONField(name ="TINUnavailableReasonRemark5")
	private String tinUnavailableReasonRemark5;

	@JSONField(name ="ChequeACBankName")
	private String chequeACBankName;

	/**
	 * 银行账户名称（由于应与证券账户名称相同，请提供本文件中的客户名称）
	 */
	@JSONField(name ="ChequeACName")
	private String chequeACName;

	/**
	 * 银行账户号码
	 */
	@JSONField(name ="ChequeACNo")
	private String chequeACNo;

	/**
	 * 银行账户银行名称
	 */
	@JSONField(name ="BankAccountType")
	private String bankAccountType;

	@JSONField(name ="ClientRiskLevel")
	private String clentRiskLevel;

	/**
	 * 签名图片 值需要以前缀#base64编码的#开始
	 */
	@JSONField(name ="SignatureImage")
	private String signatureImage;

	/**
	 * 地址证明图片 值需要以前缀#base64编码的#开始
	 */
	@JSONField(name ="AddressProveImage")
	private String addressProveImage;

	/**
	 * 地址证明是否为PDF文件 地址证明文件是否为PDF或不为  N-image Y – PDF
	 */
	@JSONField(name ="AddressProveIsPdfFile")
	private String addressProveIsPdfFile;

	/**
	 * 身份证图片 值需要以前缀#base64编码的#开始
	 */
	@JSONField(name ="HKIDCardImage")
	private String hkIdCardImage;

	/**
	 * 身份证pdf  值需要以前缀#base64编码的#开始
	 */
	@JSONField(name ="HKIDIsPdfFile")
	private String hkIdIsPdfFile;

	/**
	 * 身份证反面图片 值需要以前缀#base64编码的#开始
	 */
	@JSONField(name ="HKIDCardImageBackSide")
	private String hkIdCardImageBackSide;

	@JSONField(name ="InitialDepositMethod")
	private String initialDepositMethod;

	@JSONField(name ="InitialDepositProofIamge")
	private String initialDepositProofIamge;

	@JSONField(name ="InitialDepositProofIsPdfFile")
	private String initialDepositProofIsPdfFile;

	@JSONField(name ="JointSigningInstruction")
	private String jointSigningInstruction;

	@JSONField(name ="JointIDCardImage")
	private String jointIDCardImage;

	@JSONField(name ="JointHKIDIsPdfFile")
	private String jointIDCardImageBackSide;

	@JSONField(name ="JointAddressProveImage")
	private String jointAddressProveImage;

	@JSONField(name ="JointAddressProveIsPdfFile")
	private String jointAddressProveIsPdfFile;

	@JSONField(name ="JointIdentityVerificationQ02")
	private String jointIdentityVerificationQ02;

	@JSONField(name ="JointIdentityVerificationQ02RegisterPerson")
	private String jointIdentityVerificationQ02RegisterPerson;

	@JSONField(name ="JointIdentityVerificationQ02RegisterCorporation")
	private String jointIdentityVerificationQ02RegisterCorporation;

	@JSONField(name ="JointIdentityVerificationQ03")
	private String jointIdentityVerificationQ03;

	@JSONField(name ="JointIdentityVerificationQ03RegisterCorporation")
	private String jointIdentityVerificationQ03RegisterCorporation;

	@JSONField(name ="JointIdentityVerificationQ04")
	private String jointIdentityVerificationQ04;

	@JSONField(name ="JointIdentityVerificationQ04Name")
	private String jointIdentityVerificationQ04Name;

	@JSONField(name ="JointIdentityVerificationQ04Relationship")
	private String jointIdentityVerificationQ04Relationship;

	@JSONField(name ="JointIdentityVerificationQ05")
	private String jointIdentityVerificationQ05;

	@JSONField(name ="JointIdentityVerificationQ05Name")
	private String jointIdentityVerificationQ05Name;

	@JSONField(name ="JointIdentityVerificationQ05PublicFunction")
	private String jointIdentityVerificationQ05PublicFunction;

	@JSONField(name ="JointIdentityVerificationQ05Relationship")
	private String jointIdentityVerificationQ05Relationship;

	@JSONField(name ="JointCompanyName")
	private String jointCompanyName;

	@JSONField(name ="JointCompanyAddress")
	private String jointCompanyAddress;

	@JSONField(name ="JointEmploymentStatus")
	private String jointEmploymentStatus;

	@JSONField(name ="JointClientRiskLevel")
	private String jointClientRiskLevel;

	@JSONField(name ="RiskLevelQuestionnaireQuestion01")
	private String riskLevelQuestionnaireQuestion01;

	@JSONField(name ="RiskLevelQuestionnaireQuestion01Answer")
	private String riskLevelQuestionnaireQuestion01Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion02")
	private String riskLevelQuestionnaireQuestion02;

	@JSONField(name ="RiskLevelQuestionnaireQuestion02Answer")
	private String riskLevelQuestionnaireQuestion02Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion03")
	private String riskLevelQuestionnaireQuestion03;

	@JSONField(name ="RiskLevelQuestionnaireQuestion03Answer")
	private String riskLevelQuestionnaireQuestion03Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion04")
	private String riskLevelQuestionnaireQuestion04;

	@JSONField(name ="RiskLevelQuestionnaireQuestion04Answer")
	private String riskLevelQuestionnaireQuestion04Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion05")
	private String riskLevelQuestionnaireQuestion05;

	@JSONField(name ="RiskLevelQuestionnaireQuestion05Answer")
	private String riskLevelQuestionnaireQuestion05Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion06")
	private String riskLevelQuestionnaireQuestion06;

	@JSONField(name ="RiskLevelQuestionnaireQuestion06Answer")
	private String riskLevelQuestionnaireQuestion06Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion07")
	private String riskLevelQuestionnaireQuestion07;

	@JSONField(name ="RiskLevelQuestionnaireQuestion07Answer")
	private String riskLevelQuestionnaireQuestion07Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion08")
	private String riskLevelQuestionnaireQuestion08;

	@JSONField(name ="RiskLevelQuestionnaireQuestion08Answer")
	private String riskLevelQuestionnaireQuestion08Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion09")
	private String riskLevelQuestionnaireQuestion09;

	@JSONField(name ="RiskLevelQuestionnaireQuestion09Answer")
	private String riskLevelQuestionnaireQuestion09Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion10")
	private String riskLevelQuestionnaireQuestion10;

	@JSONField(name ="RiskLevelQuestionnaireQuestion10Answer")
	private String riskLevelQuestionnaireQuestion10Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion11")
	private String riskLevelQuestionnaireQuestion11;

	@JSONField(name ="RiskLevelQuestionnaireQuestion11Answer")
	private String riskLevelQuestionnaireQuestion11Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion12")
	private String riskLevelQuestionnaireQuestion12;

	@JSONField(name ="RiskLevelQuestionnaireQuestion12Answer")
	private String riskLevelQuestionnaireQuestion12Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion13")
	private String riskLevelQuestionnaireQuestion13;

	@JSONField(name ="RiskLevelQuestionnaireQuestion13Answer")
	private String riskLevelQuestionnaireQuestion13Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion14")
	private String riskLevelQuestionnaireQuestion14;

	@JSONField(name ="RiskLevelQuestionnaireQuestion14Answer")
	private String riskLevelQuestionnaireQuestion14Answer;

	@JSONField(name ="RiskLevelQuestionnaireQuestion15")
	private String riskLevelQuestionnaireQuestion15;

	@JSONField(name ="RiskLevelQuestionnaireQuestion15Answer")
	private String riskLevelQuestionnaireQuestion15Answer;

	@JSONField(name ="AgreementDisclaimer1")
	private String agreementDisclaimer1;

	@JSONField(name ="AgreementDisclaimer2")
	private String agreementDisclaimer2;

	@JSONField(name ="AgreementDisclaimer3")
	private String agreementDisclaimer3;


	/**
	 * IP地址
	 */
	@JSONField(name ="IPAddress")
	private String ipAddress;

	/**
	 * 語言
	 * EN - English
	 * CHT - Chinese Traditional 繁體中文
	 * CHS - Chinese Simplified 简体中文
	 */
	@JSONField(name ="LanguageID")
	private String languageID;

	/**
	 * “O”为已成功通过“THS OCO”创建的帐户
	 */
	@JSONField(name ="CreationMethod")
	private String creationMethod;

	/**
	 * 開戶申請書 accountOpeningForm
	 */
	@JSONField(name ="AccountOpeningForm")
	private String accountOpeningForm;
	/**
	 * 開戶申請書是否為PDF檔
	 * N – Image
	 * Y – PDF
	 */
	@JSONField(name ="AccountOpeningFormIsPdfFile")
	private String accountOpeningFormIsPdfFile;

	/**
	 * 自动批准标志 Y /N
	 */
	@JSONField(name ="AutoApprovalFlag")
	private String autoApprovalFlag;

	/**
	 * WS /WU  渠道
	 */
	@JSONField(name ="ChannelID")
	private String channelId;

	@JSONField(name ="LoginID")
	private String loginId;

	/**
	 * HKEX|NYSE
	 */
	@JSONField(name ="MarketIDList")
	private String marketIdList;


}
