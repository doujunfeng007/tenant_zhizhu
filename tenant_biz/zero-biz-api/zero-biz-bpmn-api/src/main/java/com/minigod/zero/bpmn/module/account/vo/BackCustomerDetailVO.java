package com.minigod.zero.bpmn.module.account.vo;

import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/6/20 11:06
 * @description：
 */
@Data
public class BackCustomerDetailVO {
	/**
	 * 客戶id
	 */
	private Long custId;
	/**
	 * 客戶性別
	 */
	private String gender;
	/**
	 * 客戶中文名字
	 */
	private String customerName;
	/**
	 * 客戶英文名字
	 */
	private String customerEName;
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
	 * 英文名字
	 */
	private String givenNameSpell;
	/**
	 * 英文姓氏
	 */
	private String familyNameSpell;
	/**
	 * 区号
	 */
	private String phoneArea;
	/**
	 * 客户类型
	 */
	private String customerTypeName;
	/**
	 * 手机号
	 */
	private String phoneNumber;
	/**
	 * 是否pi认证
	 */
	private String certificationPi;
	/**
	 * 开户邮箱
	 */
	private String openAccountEmail;
	/**
	 * 风险等级
	 */
	private String riskLevel;
	/**
	 * 国籍
	 */
	private String nationality;
	/**
	 * 状态
	 */
	private String statusName;
	/**
	 * 证件类型
	 */
	private String idKindType;
	/**
	 * 开户类型
	 */
	private String openAccountType;
	/**
	 * 证件号码
	 */
	private String IdCardNo;
	/**
	 * 账户类型
	 */
	private String accountType;
	/**
	 * 证件详细地址
	 */
	private String address;
	/**
	 * 开户成功时间
	 */
	private String openAccountTime;
	/**
	 * 注册时间
	 */
	private String registerTime;
	/**
	 * 更新时间
	 */
	private String updateTime;

}
