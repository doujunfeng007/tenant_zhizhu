package com.minigod.zero.platform.dto;

import lombok.Data;

import java.util.Date;


/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/4/17 17:47
 * @description：基金开户参数
 */
@Data
public class OpenAccountDTO {
	private Long custId;
	/**
	 * 租户id
	 */
	private String tenantId;
	/**
	 * 证券客户 id
	 */
	private String extAccountId;
	/**
	 * 基金账户类型：基金账户类型：1-个人 2-联名账户 3-机构账户
	 */
	private Integer accountType;
	/**
	 * 经纪编号
	 */
	private String  aeCode;
	/**
	 * 经纪名称
	 */
	private String aeName;
	/**
	 *AE 经纪人；GIA 个人户
	 */
	private String subAccountType;
	/**
	 * 有效：active; 无效：inactive，不传为 activate
	 */
	private String status;
	/**
	 * 风险等级
	 */
	private Integer riskLevel;

	/**
	 * 开户类型 0 基金 1 活利得 2 债券易
	 */
	private Integer busiType;
	/**
	 * 0不是，1是
	 */
	private Integer  piFlag;
	/**
	 * 交易账号
	 */
	private String accountId;
	/**
	 * 中文姓名
	 */
	private String chineseName;
	/**
	 * 英文名
	 */
	private String firstName;
	/**
	 * 英文姓
	 */
	private String lastName;
	/**
	 * 测评过期时间
	 */
	private Date expiryDate;
}
