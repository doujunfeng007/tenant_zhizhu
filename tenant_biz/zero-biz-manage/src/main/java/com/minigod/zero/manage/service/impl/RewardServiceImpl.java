package com.minigod.zero.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minigod.zero.core.mp.support.Query;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minigod.zero.core.mp.support.Condition;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.manage.entity.*;
import com.minigod.zero.manage.enums.RewardStatusEnum;
import com.minigod.zero.manage.mapper.*;
import com.minigod.zero.manage.service.*;
import com.minigod.zero.manage.vo.ChannelGiftVO;
import com.minigod.zero.manage.vo.RewardGiveOutVO;
import com.minigod.zero.manage.vo.request.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 活动配置项服务实现
 *
 * @author eric
 * @date 2024-12-23 16:50:08
 * @since 1.0.0
 */
@Slf4j
@Service
public class RewardServiceImpl implements IRewardService {

	private final IActiveService activeService;
	private final ISnChannelGiftService snChannelGiftService;
	private final ISnActiveConfigItemService snActiveConfigItemService;
	private final ISnOpenGiftRecordService snOpenGiftRecordService;
	private final ISnActiveRewardRecordService snActiveRewardRecordService;
	private final SnActiveConfigItemMapper snActiveConfigItemMapper;
	private final SnChannelGiftMapper snChannelGiftMapper;
	private final SnActiveRewardRecordMapper snActiveRewardRecordMapper;
	private final HqPackageInfoMapper hqPackageInfoMapper;
	private final SnOpenGiftRecordMapper snOpenGiftRecordMapper;

	public RewardServiceImpl(IActiveService activeService, ISnChannelGiftService snChannelGiftService,
							 ISnActiveConfigItemService snActiveConfigItemService, ISnOpenGiftRecordService snOpenGiftRecordService,
							 ISnActiveRewardRecordService snActiveRewardRecordService,
							 SnActiveConfigItemMapper snActiveConfigItemMapper, SnChannelGiftMapper snChannelGiftMapper,
							 SnActiveRewardRecordMapper snActiveRewardRecordMapper, HqPackageInfoMapper hqPackageInfoMapper,
							 SnOpenGiftRecordMapper snOpenGiftRecordMapper) {
		this.activeService = activeService;
		this.snChannelGiftService = snChannelGiftService;
		this.snActiveConfigItemService = snActiveConfigItemService;
		this.snOpenGiftRecordService = snOpenGiftRecordService;
		this.snActiveRewardRecordService = snActiveRewardRecordService;
		this.snActiveConfigItemMapper = snActiveConfigItemMapper;
		this.snChannelGiftMapper = snChannelGiftMapper;
		this.snActiveRewardRecordMapper = snActiveRewardRecordMapper;
		this.hqPackageInfoMapper = hqPackageInfoMapper;
		this.snOpenGiftRecordMapper = snOpenGiftRecordMapper;
	}

	/**
	 * 根据主键ID修改对应的渠道礼包状态
	 *
	 * @param id
	 * @param status
	 */
	@Override
	public void updateGiftStatus(Long id, Integer status) {
		LambdaQueryWrapper<SnChannelGiftEntity> lqw = Wrappers.lambdaQuery();
		lqw.eq(SnChannelGiftEntity::getId, id);
		SnChannelGiftEntity entity = snChannelGiftMapper.selectOne(lqw);
		if (entity != null) {
			entity.setStatus(status);
			snChannelGiftService.updateChannelGift(entity);
		}
	}

	/**
	 * 删除渠道奖品
	 *
	 * @param id
	 */
	@Override
	public void deleteChannelGift(Long id) {
		LambdaQueryWrapper<SnChannelGiftEntity> lqw = Wrappers.lambdaQuery();
		lqw.eq(SnChannelGiftEntity::getId, id);
		SnChannelGiftEntity entity = snChannelGiftMapper.selectOne(lqw);
		if (entity != null) {
			entity.setStatus(RewardStatusEnum.DELETED.getVaule());
			snChannelGiftService.updateChannelGift(entity);
		}
	}

	/**
	 * 删除奖励发放用户奖品
	 *
	 * @param id
	 */
	@Override
	public void deleteUserReward(Long id) {
		LambdaQueryWrapper<SnActiveRewardRecordEntity> lqw = Wrappers.lambdaQuery();
		lqw.eq(SnActiveRewardRecordEntity::getId, id);
		SnActiveRewardRecordEntity entity = snActiveRewardRecordMapper.selectOne(lqw);
		if (entity != null && entity.getRewardStatus() == RewardStatusEnum.DISPLAY.getVaule()) {
			entity.setOprName(AuthUtil.getUserName());
			entity.setOprId(AuthUtil.getUserId());
			entity.setStatus(RewardStatusEnum.UNDISPLAY.getVaule());
			snActiveRewardRecordService.updateActiveRewardRecord(entity);
		}
	}

	/**
	 * 奖品发放分页列表
	 *
	 * @param pageQuery
	 * @param searchReqVO
	 */
	@Override
	public IPage<RewardGiveOutVO> queryGiveOutList(Query pageQuery, GiveOutSearchReqVO searchReqVO) {
		IPage<RewardGiveOutVO> result = snActiveRewardRecordMapper.queryGiveOutList(Condition.getPage(pageQuery), searchReqVO);
		return result;
	}

	/**
	 * 查询奖品发放详情
	 *
	 * @param id
	 */
	@Override
	public SnActiveRewardRecordEntity findGiveOut(Long id) {
		return snActiveRewardRecordMapper.selectById(id);
	}

	/**
	 * 奖品发放
	 *
	 * @param giveOutRewardReqVO
	 * @param resultMap
	 */
	@Override
	public Boolean saveRewardRecord(GiveOutRewardReqVO giveOutRewardReqVO, Map<String, String> resultMap) {
		/**
		 * 目前设定一次发送只能选择一种奖品，但可以选择多个用户
		 */
		List<ManualRewardReqVO> rewardReqVOList = new ArrayList<>();
		if (giveOutRewardReqVO.getRewardIds() == null) {
			resultMap.put("msg", "请选择奖品");
			return false;
		}
		if (giveOutRewardReqVO.getUserIds() == null) {
			resultMap.put("msg", "请选择用户");
			return false;
		}
		List<Integer> rewardIds = Arrays.asList(giveOutRewardReqVO.getRewardIds());
		List<Integer> userIds = Arrays.asList(giveOutRewardReqVO.getUserIds());
		Map<Integer, Integer> countMap = new HashMap<>();
		for (Integer rewardId : rewardIds) {
			int count = 0;
			SnActiveConfigItemEntity reward = snActiveConfigItemMapper.selectById(rewardId);
			ManualRewardReqVO rewardReqVO = null;
			for (Integer userId : userIds) {
				rewardReqVO = new ManualRewardReqVO();
				rewardReqVO.setUserId(userId);
				rewardReqVO.setActiveCfgItemId(rewardId);
				rewardReqVO.setActiveItemName(reward.getActiveItemName());
				rewardReqVO.setRewardType(reward.getRewardType());
				rewardReqVO.setOprId(AuthUtil.getUserId());
				rewardReqVO.setOprName(AuthUtil.getUserName());
				rewardReqVO.setOprReason(rewardReqVO.getOprReason());
				rewardReqVO.setRewardCondition(rewardReqVO.getRewardCondition());
				rewardReqVO.setSendSource(rewardReqVO.getSendSource());
				rewardReqVO.setUseDay(rewardReqVO.getUseDay());
				if (reward.getCommissionDay() != null) {
					rewardReqVO.setFreeDays(reward.getCommissionDay());
				}
				if (reward.getPackageId() != null) {
					rewardReqVO.setPackageId(reward.getPackageId().intValue());
				}
				if (reward.getRewardMoney() != null) {
					rewardReqVO.setTotalMoney(reward.getRewardMoney());
				}
				if (reward.getAssetId() != null) {
					rewardReqVO.setAssetId(reward.getAssetId());
					rewardReqVO.setQuantity(reward.getStkNum());
				}
				if (reward.getRewardSubtype() != null) {
					rewardReqVO.setRewardSubtype(reward.getRewardSubtype());
					if (reward.getRewardMoney() != null) {
						rewardReqVO.setRewardMoney(reward.getRewardMoney());
					}
				}
				rewardReqVOList.add(rewardReqVO);
				count++;
			}
			//比较发放的数量有么有超出剩余数量
			if (reward.getRemainCount() != -1 && count > reward.getRemainCount()) {
				resultMap.put("msg", "剩余数量不足");
				return false;
			}
			countMap.put(rewardId, count);
		}
		boolean result = activeService.giveOutReward(rewardReqVOList);
		if (result) {
			List<SnActiveConfigItemEntity> rewardList = snActiveConfigItemMapper.selectBatchIds(rewardIds);
			//发放成功之后，需要更新剩余数量
			for (SnActiveConfigItemEntity reward : rewardList) {
				int remainCount = reward.getRemainCount();
				int count = countMap.get(reward.getId());
				if (remainCount != -1 && count != 0) {
					remainCount = remainCount - count;
					reward.setRemainCount(remainCount);
				}
			}
			snActiveConfigItemService.updateActiveConfigItems(rewardList);
			resultMap.put("msg", "发送奖品成功");
			return true;
		} else {
			resultMap.put("msg", "发送奖品异常");
			return false;
		}
	}

	/**
	 * 获取行情类产品明细
	 *
	 * @param id
	 */
	@Override
	public HqPackageInfoEntity getPackagesByPK(Long id) {
		return hqPackageInfoMapper.selectById(id);
	}

	/**
	 * 获取行情类产品列表
	 */
	@Override
	public List<HqPackageInfoEntity> getPackages() {
		LambdaQueryWrapper<HqPackageInfoEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(HqPackageInfoEntity::getStatus, 1);
		queryWrapper.orderByDesc(HqPackageInfoEntity::getCreateTime);
		return hqPackageInfoMapper.selectList(queryWrapper);
	}

	/**
	 * 获取渠道礼包列表
	 *
	 * @param searchReqVO
	 * @return
	 */
	@Override
	public IPage<ChannelGiftVO> queryChannelGiftList(IPage page, GiftSearchReqVO searchReqVO) {
		return snChannelGiftMapper.queryPageChannelGift(page, searchReqVO);
	}

	/**
	 * 获取渠道礼包详情
	 *
	 * @param id
	 */
	@Override
	public SnChannelGiftEntity getSnChannelGift(Long id) {
		return snChannelGiftMapper.selectById(id);
	}

	/**
	 * 保存渠道礼包
	 *
	 * @param entity
	 */
	@Override
	public void saveChannelGift(SnChannelGiftEntity entity) {
		snChannelGiftService.saveChannelGift(entity);
	}

	/**
	 * 更新渠道礼包
	 *
	 * @param entity
	 */
	@Override
	public void updateChannelGift(SnChannelGiftEntity entity) {
		snChannelGiftService.updateChannelGift(entity);
	}

	/**
	 * 获取开放渠道礼包入口记录
	 *
	 * @param page
	 * @param searchReqVO
	 * @return
	 */
	@Override
	public IPage<SnOpenGiftRecordEntity> queryOpenChannelGiftList(IPage page, OpenChannelSearchReqVO searchReqVO) {
		return snOpenGiftRecordMapper.queryOpenChannelGiftList(page, searchReqVO);
	}

	/**
	 * 保存开放渠道礼包入口记录
	 *
	 * @param entity
	 */
	@Override
	public void saveOpenGiftRecord(SnOpenGiftRecordEntity entity) {
		snOpenGiftRecordService.saveOpenGiftRecord(entity);
	}

	/**
	 * 查询渠道礼包是否开启手气红包
	 *
	 * @param channelId
	 * @return
	 */
	@Override
	public Integer findChannelIdHandselStock(String channelId) {
		// todo 通过字典实现是否开启手气红包 snactiv;handselstock
		return 0;
	}

	/**
	 * 更新渠道礼包是否开启手气红包
	 *
	 * @param channelId
	 * @param status
	 */
	@Override
	public void updateHandselStock(String channelId, Integer status) {
		// todo 通过字典实现是否开启手气红包 snactiv;handselstock
	}
}
