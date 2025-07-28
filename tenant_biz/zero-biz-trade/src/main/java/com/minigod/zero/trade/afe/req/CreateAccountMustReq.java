package com.minigod.zero.trade.afe.req;

import lombok.Data;

/**
 * @ClassName: com.minigod.zero.trade.afe.req.AreateAccountReq
 * @Description:  AFE开户接口必传 字段
 * @Author: linggr
 * @CreateDate: 2024/4/12 10:19
 * @Version: 1.0
 */
@Data
public class CreateAccountMustReq extends ReqVO{
	//特定的客户端ID或“（AUTO）”，让后台办公系统生成客户端ID
	private String clientID;

	//特定的客户端ID或“（AUTO）”让后台办公室系统生成帐户ID
	private String accountID;
	/**
	 * 帳戶類別
	 * M - Margin Client
	 * X - Custodian Client
	 */
	private String accountTypeID;

	/**
	 * 0-个人   1-联合账户   5-机构
	 */
	private String registrationType;

	/**
	 * 英文名称最多150个字符（G3SB）
	 */
	private String name;

	/**
	 * Chinese Name
	 */
	private String nameEx;
	/**
	 * 性别
	 * M – Male 男
	 * F – Female 女
	 */
	private String gender;
	/**
	 * 在接口文件中格式化DD/MM/YYYY
	 *
	 * 在XML节点中格式化YYYY-MM-DD
	 */
	private String dateOfBirth;
	/**
	 * 请参考“国籍/国家列表”标签中的价值选项
	 */
	private String nationalityID;
	/**
	 * 出生地
	 */
	private String placeOfBirth;


	/**
	 * B - BR 商業登記證號碼
	 * C - CI 公司註冊號碼
	 * I - ID 身份證
	 * P - Passport 護照
	 */
	private String idType;

	/**
	 * 証件號碼
	 * Max 30 characters
	 */
	private String idNumber;

	/**
	 * 發行地
	 * ID的签发地点，请参考标签“国籍/国家列表”的价值选项
	 */
	private String idIssuePlace;

	/**
	 * 發行日期
	 * ID的签发日期
	 */
	private String idIssueDate;

	/**
	 * 住宅電話
	 * Max 30 characters (G3SB)
	 */
	private String homeNumber;

	/**
	 * 移动电话
	 * Max 30 characters (G3SB)
	 */
	private String mobileNumber;

	/**
	 * 客戶主任編號
	 * Max 15 characters (G3SB)
	 */
	private String aeID;

	/**
	 * 聯絡名稱
	 * Max 150 characters (G3SB)
	 */
	private String contactName;

	/**
	 * 地址 1
	 * Maximum 100 characters (G3SB)
	 */
	private String mailAddress1;

	/**
	 * Y 通过邮件接收报表/贸易确认函/其他通知
	 * N 不要收到通过邮件发送的对账单/交易确认函/其他通知
	 */
	private String mailContactReportFlag;

	/**
	 * 電郵地址
	 * Maximum 100 characters (G3SB)
	 */
	private String emailAddress;

	/**
	 * Y 通过邮件接收报表/贸易确认函/其他通知
	 * N 不要收到通过邮件发送的对账单/交易确认函/其他通知
	 */
	private String emailContactReportFlag;

	/**
	 * 教育程度
	 * 1-Primary 小学
	 * 2-Secondary 中学
	 * 3-College  学院
	 * 4-University or above 大学
	 */
	private String educationLevel;

	/**
	 * 就業狀況
	 * E - Employed 在職
	 * M - Self-Employment 创业
	 * U - Unemployed 待業
	 * O - Other 其他
	 */
	private String employmentStatus;

	/**
	 * 每年收入 (港幣)
	 * 0 - ≤ $150,000
	 * 1 - $150,001 - $500,000
	 * 2 - $500,001 - $1,000,000
	 * 3 - $1,000,001 - $5,000,000
	 * 4 - >$5,000,001
	 */
	private String annualSalary;

	/**
	 * 總資產淨值 (港幣)
	 * 0 - ≤ HKD 250,000
	 * 1 - HKD 250,001 to 1,000,000
	 * 2 - HKD 1,000,001 to 5,000,000
	 * 3 - HKD 5,000,001 to 20,000,000
	 * 4 - More than HKD 20,000,001
	 */
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
	private String sourceOfIncome;

	/**
	 * 资金来源
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
	private String sourceOfFunds;

	/**
	 * z住宅
	 * T - Others 其他
	 * O - Owned 自置
	 * R - Rented/Monthly Rental (HK$) 租用/
	 * 每月供款 (港元)
	 * P - With Parents 與父母同住
	 */
	private String ownershipOfResidence;



	/**
	 * 投資目標
	 * A – Aggressive 進取
	 * G - Growth 增長
	 * C - Conservative 保守
	 * O - Others 其他
	 */
	private String investmentTarget;

	/**
	 * 投資頻率 每月交易頻率
	 * 0 - 0-5 / Month 0-5
	 * 1 - 6-30 / Month 6-30
	 * 2 - 31-100 / Month 31-100
	 * 3 - >100 / Month >100
	 */
	private String investmentFrequency;

	/**
	 * 税务居民数量（最多5个税务居住地）
	 */
	private String numberOfTaxResidency;

	/**
	 * 第一次税务居住地请参阅标签“国籍/国家名单”中的价值选项
	 */
	private String taxResidency1;


	/**
	 * 签名图片 值需要以前缀#base64编码的#开始
	 */
	private String signatureImage;


	/**
	 * 地址证明是否为PDF文件 地址证明文件是否为PDF或不为  N-image Y – PDF
	 */
	private String addressProveIsPdfFile;

	/**
	 * 身份证pdf  值需要以前缀#base64编码的#开始
	 */
	private String hkIdIsPdfFile;

	/**
	 * IP地址
	 */
	private String ipAddress;

	/**
	 * “O”为已成功通过“THS OCO”创建的帐户
	 */
	private String creationMethod;


}
