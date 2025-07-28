package com.minigod.zero.customer.api.service;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.customer.dto.CustomerOpenAccountDTO;
import com.minigod.zero.customer.vo.OrganizationOpenAccountVO;

/**
 * @Author：jim(liaoguangjie)
 * @Date：2024/5/8 9:55
 * @description：
 */
public interface OpenAccountService {
	/**
	 * 用户开户
	 * @param customerOpenAccount
	 * @return
	 */
	R openAccount(CustomerOpenAccountDTO customerOpenAccount);

	/**
	 * 机构开户
	 * @param organizationOpenAccount
	 * @return
	 */
	R organizationOpenAccount(OrganizationOpenAccountVO organizationOpenAccount);

	/**
	 * 账户系统开户(不包含柜台，基金，债券相关逻辑)
	 * @param customerOpenAccount
	 * @return
	 */
	R customerOpenAccount(CustomerOpenAccountDTO customerOpenAccount);

	/**
	 * 基金开户
	 * @param customerOpenAccount
	 * @return
	 */
	R customerFundOpenAccount(CustomerOpenAccountDTO customerOpenAccount);

	/**
	 * 股票开户
	 * @param customerOpenAccount
	 * @return
	 */
	R customerStockOpenAccount(CustomerOpenAccountDTO customerOpenAccount);
}
