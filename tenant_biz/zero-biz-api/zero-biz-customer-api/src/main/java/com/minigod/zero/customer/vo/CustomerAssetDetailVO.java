package com.minigod.zero.customer.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName: com.minigod.zero.bpmn.module.feign.vo.CustomerAccountDetailVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/9/2 16:15
 * @Version: 1.0
 */
@Data
public class CustomerAssetDetailVO {
	/**
	 * 客户id
	 */
	private Long custId;
	/**
	 * 客户中文名
	 */
	private String clientName;
	/**
	 * 英文名字
	 */
	private String givenNameSpell;


	/**
	 * 账户类型，0个人户，1机构户
	 */
	private Integer accountType;
	/**
	 * 手机号
	 */
	private String phoneNumber;
	/**
	 * 手机区号
	 */
	private String phoneArea;

	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 租户id
	 */
	private String tenantId;
	/**
	 * 理财账号
	 */
	private String accountId;

	/**
	 * 账户创建时间
	 */
	private Date createTime;

	/**
	 * 平均资产
	 */
	private BigDecimal averageAssets ;



}
