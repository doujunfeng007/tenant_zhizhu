package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.core.mp.base.BaseServiceImpl;
import com.minigod.zero.manage.entity.SnActiveRewardRecordEntity;
import com.minigod.zero.manage.mapper.SnActiveRewardRecordMapper;
import com.minigod.zero.manage.service.ISnActiveRewardRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 奖励发放服务接口实现
 *
 * @author eric
 * @since 2024-12-24 13:53:08
 */
@Slf4j
@Service
public class SnActiveRewardRecordServiceImpl extends BaseServiceImpl<SnActiveRewardRecordMapper, SnActiveRewardRecordEntity> implements ISnActiveRewardRecordService {
	/**
	 * 新增活动奖励
	 *
	 * @param snActiveRewardRecord
	 * @return
	 */
	@Override
	public boolean addActiveRewardRecord(SnActiveRewardRecordEntity snActiveRewardRecord) {
		boolean result = this.save(snActiveRewardRecord);
		if (result) {
			log.info("新增活动奖励成功, 主键ID:{}", snActiveRewardRecord.getId());
		} else {
			log.info("新增活动奖励失败, 主键ID:{}", snActiveRewardRecord.getId());
		}
		return result;
	}

	/**
	 * 更新活动奖励
	 *
	 * @param snActiveRewardRecord
	 * @return
	 */
	@Override
	public boolean updateActiveRewardRecord(SnActiveRewardRecordEntity snActiveRewardRecord) {
		boolean result = this.updateById(snActiveRewardRecord);
		if (result) {
			log.info("更新活动奖励成功, 主键ID:{}", snActiveRewardRecord.getId());
		} else {
			log.info("更新活动奖励失败, 主键ID:{}", snActiveRewardRecord.getId());
		}
		return result;
	}

	/**
	 * 根据兑换码ID查询奖励记录
	 *
	 * @param exchangeCodeId
	 * @return
	 */
	@Override
	public SnActiveRewardRecordEntity findActiveRewardRecordByExchangeCodeId(Long exchangeCodeId) {
		LambdaQueryWrapper<SnActiveRewardRecordEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SnActiveRewardRecordEntity::getExchangeCodeId, exchangeCodeId);
		return this.getOne(queryWrapper);
	}
}
