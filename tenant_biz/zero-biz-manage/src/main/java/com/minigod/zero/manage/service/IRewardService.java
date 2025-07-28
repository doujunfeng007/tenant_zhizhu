package com.minigod.zero.manage.service;

import com.minigod.zero.core.mp.support.Query;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.HqPackageInfoEntity;
import com.minigod.zero.manage.entity.SnActiveRewardRecordEntity;
import com.minigod.zero.manage.entity.SnChannelGiftEntity;
import com.minigod.zero.manage.entity.SnOpenGiftRecordEntity;
import com.minigod.zero.manage.vo.ChannelGiftVO;
import com.minigod.zero.manage.vo.RewardGiveOutVO;
import com.minigod.zero.manage.vo.request.GiftSearchReqVO;
import com.minigod.zero.manage.vo.request.GiveOutRewardReqVO;
import com.minigod.zero.manage.vo.request.GiveOutSearchReqVO;
import com.minigod.zero.manage.vo.request.OpenChannelSearchReqVO;

import java.util.List;
import java.util.Map;

/**
 * 奖品配置服务
 *
 * @author eric
 * @date 2024-12-23 16:19:08
 */
public interface IRewardService {
	/**
	 * 根据主键ID修改对应的渠道礼包状态
	 *
	 * @param id
	 * @param status
	 */
	void updateGiftStatus(Long id, Integer status);

	/**
	 * 删除渠道奖品
	 *
	 * @param id
	 */
	void deleteChannelGift(Long id);

	/**
	 * 删除用户奖品
	 *
	 * @param id
	 */
	void deleteUserReward(Long id);


	/**
	 * 奖品发放分页列表
	 *
	 * @param
	 */
	IPage<RewardGiveOutVO> queryGiveOutList(Query pageQuery, GiveOutSearchReqVO searchReqVO);


	/**
	 * 查询奖品发放详情
	 *
	 * @param
	 */
	SnActiveRewardRecordEntity findGiveOut(Long id);

	/**
	 * 奖品发放
	 *
	 * @param
	 */
	Boolean saveRewardRecord(GiveOutRewardReqVO giveOutRewardReqVO, Map<String, String> resultMap);

	/**
	 * 获取行情类产品明细
	 *
	 * @param
	 */
	HqPackageInfoEntity getPackagesByPK(Long id);

	/**
	 * 获取行情类产品列表
	 *
	 * @param
	 */
	List<HqPackageInfoEntity> getPackages();

	/**
	 * 获取渠道礼包列表
	 *
	 * @return
	 */
	IPage<ChannelGiftVO> queryChannelGiftList(IPage page, GiftSearchReqVO searchReqVO);

	/**
	 * 获取渠道礼包详情
	 *
	 * @param
	 */
	SnChannelGiftEntity getSnChannelGift(Long id);

	/**
	 * 保存渠道礼包
	 *
	 * @param entity
	 */
	void saveChannelGift(SnChannelGiftEntity entity);

	/**
	 * 更新渠道礼包
	 *
	 * @param entity
	 */
	void updateChannelGift(SnChannelGiftEntity entity);


	/**
	 * 获取开放渠道礼包入口记录
	 *
	 * @param page
	 * @param searchReqVO
	 * @return
	 */
	IPage<SnOpenGiftRecordEntity> queryOpenChannelGiftList(IPage page, OpenChannelSearchReqVO searchReqVO);

	/**
	 * 保存开放渠道礼包入口记录
	 *
	 * @param entity
	 */
	void saveOpenGiftRecord(SnOpenGiftRecordEntity entity);

	/**
	 * 查询渠道礼包是否开启手气红包
	 *
	 * @param channelId
	 * @return
	 */
	Integer findChannelIdHandselStock(String channelId);

	/**
	 * 更新渠道礼包是否开启手气红包
	 *
	 * @param channelId
	 * @param status
	 */
	void updateHandselStock(String channelId, Integer status);
}
