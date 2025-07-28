package com.minigod.zero.customer.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/12/4 15:17
 * @description：
 */
@Data
public class OrganizationInfoVO {
	/**
	 * 机构号
	 */
	private Long custId;
	/**
	 * 理财账号
	 */
	private String accountId;
	/**
	 * 机构名称
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
	 * 联系人电话
	 */
	private String contactsMobile;
	/**
	 * 区号
	 */
	@TableField(exist = false)
	private String areaCode;

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
}
