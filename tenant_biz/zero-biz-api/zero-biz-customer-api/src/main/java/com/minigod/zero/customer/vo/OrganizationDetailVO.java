package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.OrganizationBasicInfo;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName: com.minigod.zero.customer.vo.OrganizationDetailVO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/23 14:52
 * @Version: 1.0
 */
@Data
public class OrganizationDetailVO extends OrganizationBasicInfo {

	/**
	 * 理财账户id
	 */
	private String accountId;

	/**
	 * 开户时间
	 */
	private Date openAccountTime;

	private String riskLevelName;

	private Integer riskLevel;

	/**
	 * 角色名称
	 */
	private String roleName;
}
