package com.minigod.zero.customer.bo;

import com.minigod.zero.core.mp.support.Query;
import com.minigod.zero.customer.entity.OrganizationBasicInfo;
import lombok.Data;

/**
 * @ClassName: com.minigod.zero.customer.bo.OrganizationInfoQueryBO
 * @Description:
 * @Author: linggr
 * @CreateDate: 2024/7/23 10:42
 * @Version: 1.0
 */
@Data
public class OrganizationInfoQueryBO {
	/**
	 * 公司名称
	 */
	private String name;

	/**
	 * 联系人
	 */
	private String contacts;

	/**
	 * 联系人电话
	 */
	private String contactsMobile;

	/**
	 * 联系人邮箱
	 */
	private String contactsEmail;
}
