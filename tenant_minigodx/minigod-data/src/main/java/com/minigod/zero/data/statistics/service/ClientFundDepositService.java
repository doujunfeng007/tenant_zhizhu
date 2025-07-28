package com.minigod.zero.data.statistics.service;

import com.minigod.zero.data.vo.ClientDepositStatVO;

/**
 * 客户入金统计服务-入金申请信息
 *
 * @author eric
 * @since 2024-10-29 14:04:08
 */
public interface ClientFundDepositService {
	/**
	 * 客户入金统计(入金申请信息)
	 *
	 * @return
	 */
	ClientDepositStatVO calculateDepositStats();
}
