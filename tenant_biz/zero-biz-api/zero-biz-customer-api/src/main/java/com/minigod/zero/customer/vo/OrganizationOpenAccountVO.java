package com.minigod.zero.customer.vo;

import com.minigod.zero.customer.entity.OrganizationBasicInfo;
import lombok.Data;

import java.util.Date;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/31 9:47
 * @description：
 */
@Data
public class OrganizationOpenAccountVO extends OrganizationBasicInfo {
	/**
	 * 测评过期时间
	 */
	private Date expiryDate;

}
