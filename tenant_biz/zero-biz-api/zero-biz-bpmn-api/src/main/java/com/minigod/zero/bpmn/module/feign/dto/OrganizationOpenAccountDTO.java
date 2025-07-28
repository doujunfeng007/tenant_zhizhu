package com.minigod.zero.bpmn.module.feign.dto;

import lombok.Data;

import java.util.Date;

@Data
public class OrganizationOpenAccountDTO {
	/**
	 * 主键ID
	 */
	private Integer id;

	/**
	 * 客户id
	 */
	private Long custId;
	/**
	 * 租户id
	 */
	private String tenantId;

	/**
	 * 风险等级
	 */
	private Integer riskLevel;

	/**
	 * 有效期
	 */
	private Date expiryDate;

	/**
	 * 开户渠道
	 */
	private Integer openChannel;

	/**
	 * 公司名称
	 */
	private String name;

	/**
	 * 公司地址
	 */
	private String address;

	/**
	 * 注册地址
	 */
	private String registrationAddress;

	/**
	 *
	 */
	private String registrationCertificate;

	/**
	 * 商业登记证
	 */
	private String businessRegistrationCertificate;

	/**
	 * 业务性质
	 */
	private String businessNature;

	/**
	 * 资金来源
	 */
	private String fundingSource;

	/**
	 * 联系人
	 */
	private String contacts;

	/**
	 * 联系人电话区号
	 */
	private String areaCode;

	/**
	 * 联系人电话
	 */
	private String contactsMobile;

	/**
	 * 联系人邮箱
	 */
	private String contactsEmail;

	/**
	 * 开户目的，0股票基金交易，1发行产品
	 */
	private String target;

	/**
	 * 银行名称
	 */
	private String bankName;

	/**
	 * 银行代码
	 */
	private String swiftCode;

	/**
	 * 银行账户名
	 */
	private String accountName;

	/**
	 * 银行账户号码
	 */
	private String bankAccountNumber;

	/**
	 * 创建日期
	 */
	private Date createTime;

	/**
	 * 更新日期
	 */
	private Date updateTime;

	/**
	 * 状态
	 */
	private Integer status;

	/**
	 * 是否删除
	 */
	private Integer isDeleted;

	private static final long serialVersionUID = 1L;
}

