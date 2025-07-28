package com.minigod.zero.trade.sjmb.req.brokerapi;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author chen
 * @ClassName AccountAddReq.java
 * @Description TODO
 * @createTime 2024年02月02日 17:51:00
 */
@Data
public class AccountAddReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 账号交易状态
	 */
	private String accountTradeStatus;
	/**
	 * 账号类型
	 */
	private String  accountType;
	/**
	 * 是否自动分配HKIDR BCAN码，可以不填写，不填写表示不对HKIDR BCAN码进行任何操作；若填写为true，需要公司支持自动分配HKIDR
	 * BCAN码；若填写为false，必须填写hbcn。
	 */
	private Boolean autoHBCAN;
	/**
	 * 是否自动分配中华通BCAN码，可以不填写，不填写表示不对中华通BCAN码进行任何操作；若填写为true，需要公司支持自动分配中华通BCAN码；若填写为false，必须填写bcan。
	 */
	private Boolean autoNBCAN;
	/**
	 * 基础币种，目前仅支持USD或HKD
	 */
	private String baseCurrency;
	/**
	 * 出生日期，格式：yyyy/MM/dd
	 */
	private String birthDate;
	/**
	 * 券商客户的账号ID，券商用来识别用户的唯一编号，用于与开户成功后生成的泰坦系统账号做关联
	 */
	private String brokerAccountId;
	/**
	 * 是否为证券行业从业者
	 */
	private boolean brokerPractitioner;
	/**
	 * 佣金模板id，不填绑定系统默认模板
	 */
	private String commissionTId;
	/**
	 * 通讯地址
	 */
	private String contactAddress;
	/**
	 * 通讯城市
	 */
	private String contactCity;
	/**
	 * 通讯区
	 */
	private String contactDistrict;
	/**
	 * 通讯邮编
	 */
	private String contactPostal;
	/**
	 * 通讯省份
	 */
	private String contactState;
	/**
	 * 国家呼叫码，国家或地区呼叫码
	 */
	private String countryCallingCode;
	/**
	 * 纳税国
	 */
	private String countryOfTax;
	/**
	 * 是否为控股股东
	 */
	private boolean decisionMaker;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 费用模板id，不填绑定系统默认模板
	 */
	private String feeTId;
	/**
	 * 名字，姓名全称长度不能超过39个字符(UTF-8)
	 */
	private String firstName;
	/**
	 * 英文名，非英文名，可选填此字段，长度不能超过18个字符(UTF-8)
	 */
	private String firstNameEn;
	/**
	 * 性别
	 */
	private String gender;
	/**
	 * 是否有税务识别号
	 */
	private boolean hasTaxNumber;
	/**
	 * HKIDR BCAN码
	 */
	private Double hbcn;
	/**
	 * 证件号，长度不能超过39个字符(UTF-8)
	 */
	private String idNumber;
	/**
	 * 证件类型
	 */
	private String idType;
	/**
	 * 利率模板id，不填绑定系统默认模板
	 */
	private String interestTId;
	/**
	 * 常用语言
	 */
	private String language;
	/**
	 * 姓氏，姓名全称长度不能超过39个字符(UTF-8)
	 */
	private String lastName;
	/**
	 * 英文姓，非英文姓，可选填此字段，长度不能超过18个字符(UTF-8)
	 */
	private String lastNameEn;
	/**
	 * 保证金模板id，不填绑定系统默认模板
	 */
	private String marginTId;
	/**
	 * 中间名，中文姓名无需填写此字段，姓名全称长度不能超过39个字符(UTF-8)
	 */
	private String middleName;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 国籍/区籍，三位大写英文表示的国家或地区码
	 */
	private String nationality;
	/**
	 * 手动分配中华通BCAN码列表，支持中华通交易的上手券商，均可以分配中华通BCAN码
	 */
	private List<NBcan> nBcans;
	/**
	 * 无税号原因
	 */
	private String noTaxReason;
	/**
	 * 无税号原因描述
	 */
	private String noTaxReasonDesc;
	/**
	 * 不同资金组间是否共享购买力
	 */
	private boolean sharingBp;
	/**
	 * 税号
	 */
	private String taxNumber;
	/**
	 * 交易权限
	 */
	private List<String> tradePermissions;
	/**
	 * 地区
	 */
	private String userRegion;


	@Data
	public static class NBcan implements Serializable{

		/**
		 * 上手券商
		 */
		private String broker;

		/**
		 * 中华通BCAN码
		 */
		private String bcan;
	}
}
