package com.minigod.zero.customer.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/7 17:03
 * @description：
 */
@Data
public class AccountVO implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * acctid 主键
	 */
	@ApiModelProperty(value = "主键")
	private Long acctId;

	/**
	 * sAcctName 证券账户号码
	 */
	@ApiModelProperty(value = "证券账户号码")
	private String acctName;

	/**
	 * 账号类型：1-个人 2-联名 3-公司
	 */
	@ApiModelProperty(value = "账号类型：1-个人 2-联名 3-公司")
	private Integer acctType;

	/**
	 * 账户状态[0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定]
	 */
	@ApiModelProperty(value = "账户状态：0-正常 1-冻结 2-挂失 3-销户 D-休眠 E-不合格 F-锁定")
	private Integer status;

	/**
	 * 是否当前选中：0-否 1-是
	 */
	@ApiModelProperty(value = "是否当前选中：0-否 1-是")
	private Integer isCurrent;

	/**
	 * 账户绑定列表
	 */
	@ApiModelProperty(value = "账户绑定列表")
	private List<SubAccountsVO> subAccounts;

}
