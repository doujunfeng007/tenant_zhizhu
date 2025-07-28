package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.manage.entity.SnActiveConfigItemEntity;
import com.minigod.zero.manage.vo.SnActiveConfigItemVO;
import com.minigod.zero.manage.vo.request.RewardSearchReqVO;

import java.util.List;

/**
 * 活动营销奖品库服务接口定义
 *
 * @author eric
 * @since 2024-12-25 17:23:08
 */
public interface ISnActiveConfigItemService extends IService<SnActiveConfigItemEntity> {
	/**
	 * 分页查询奖品配置
	 *
	 * @param page
	 * @param searchReqVO
	 * @return
	 */
	IPage<SnActiveConfigItemVO> queryRewardList(IPage<SnActiveConfigItemEntity> page, RewardSearchReqVO searchReqVO);

	/**
	 * 查询奖品配置列表
	 *
	 * @param searchReqVO
	 * @return
	 */
	List<SnActiveConfigItemEntity> queryRewardList(RewardSearchReqVO searchReqVO);

	/**
	 * 查询奖品配置详情
	 *
	 * @param id
	 * @return
	 */
	SnActiveConfigItemEntity getRewardByPk(Long id);

	/**
	 * 新增奖品配置
	 *
	 * @param entity
	 * @return
	 */
	boolean saveActiveConfigItem(SnActiveConfigItemEntity entity);

	/**
	 * 更新奖品配置
	 *
	 * @param entity
	 * @return
	 */
	boolean updateActiveConfigItem(SnActiveConfigItemEntity entity);

	/**
	 * 批量更新奖品配置
	 *
	 * @param entityList
	 * @return
	 */
	boolean updateActiveConfigItems(List<SnActiveConfigItemEntity> entityList);

	/**
	 * 更新奖品配置状态
	 *
	 * @param id
	 * @param status
	 */
	void updateStatus(Long id, Integer status);

	/**
	 * 更新奖品配置
	 *
	 * @param snActiveConfigItem
	 */
	void updateReward(SnActiveConfigItemVO snActiveConfigItem);

	/**
	 * 新增奖品配置
	 *
	 * @param snActiveConfigItem
	 * @return
	 */
	void saveReward(SnActiveConfigItemVO snActiveConfigItem);

	/**
	 * 查询奖品配置详情
	 *
	 * @param id
	 * @return
	 */
	SnActiveConfigItemEntity findReward(Long id);

	/**
	 * 删除奖品配置
	 *
	 * @param id
	 */
	void deleteReward(Long id);
}
