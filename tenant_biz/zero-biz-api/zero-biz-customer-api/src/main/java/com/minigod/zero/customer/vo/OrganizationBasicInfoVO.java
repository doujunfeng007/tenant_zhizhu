package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.OrganizationBasicInfo;
import lombok.Data;

import java.util.Date;

/**
 * @author dell
 * @TableName organization_basic_info
 */
@Data
public class OrganizationBasicInfoVO extends OrganizationBasicInfo {
	private Integer riskLevel;
	private Date expiryDate;
	private String riskLevelName;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 状态名称
	 */
	private String statusName;

	private String accountId;
}
