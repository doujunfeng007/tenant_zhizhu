package com.minigod.zero.manage.service;


import com.minigod.zero.manage.vo.request.ManualRewardReqVO;

import java.util.List;

/**
 * 活动服务接口
 *
 * @author eric
 * @date 2024-12-24 13:43:08
 */
public interface IActiveService {
	/**
	 * 发放非活动类型的奖励
	 *
	 * @return
	 */
	boolean giveOutReward(final List<ManualRewardReqVO> rewardReqVOs);
}
