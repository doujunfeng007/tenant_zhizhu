package com.minigod.zero.manage.service;

import com.minigod.zero.manage.entity.SnActiveRewardRecordEntity;

/**
 * 奖励发放服务接口
 *
 * @author eric
 * @since 2024-12-24 13:53:08
 */
public interface ISnActiveRewardRecordService {
	/**
	 * 新增活动奖励
	 *
	 * @param snActiveRewardRecord
	 * @return
	 */
	boolean addActiveRewardRecord(SnActiveRewardRecordEntity snActiveRewardRecord);

	/**
	 * 更新活动奖励
	 *
	 * @param snActiveRewardRecord
	 * @return
	 */
	boolean updateActiveRewardRecord(SnActiveRewardRecordEntity snActiveRewardRecord);

	/**
	 * 根据兑换码ID查询奖励记录
	 *
	 * @param exchangeCodeId
	 * @return
	 */
	SnActiveRewardRecordEntity findActiveRewardRecordByExchangeCodeId(Long exchangeCodeId);
}
