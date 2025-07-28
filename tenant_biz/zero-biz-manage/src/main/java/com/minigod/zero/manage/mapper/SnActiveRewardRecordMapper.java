package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SnActiveRewardRecordEntity;
import com.minigod.zero.manage.vo.RewardGiveOutVO;
import com.minigod.zero.manage.vo.request.GiveOutSearchReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 奖励发放表Mapper接口
 *
 * @author eric
 * @date 2024-12-23 15:00:08
 */
@Mapper
public interface SnActiveRewardRecordMapper extends BaseMapper<SnActiveRewardRecordEntity> {

	/**
	 * 分页查询奖励记录
	 *
	 * @param page 分页参数
	 * @param userId 用户ID
	 * @param rewardType 奖励类型
	 * @param rewardStatus 奖励状态
	 * @return 分页数据
	 */
	IPage<SnActiveRewardRecordEntity> queryPage(IPage<SnActiveRewardRecordEntity> page,
												@Param("userId") Integer userId,
												@Param("rewardType") Integer rewardType,
												@Param("rewardStatus") Integer rewardStatus);

	/**
	 * 查询用户未过期的奖励
	 *
	 * @param userId 用户ID
	 * @param rewardType 奖励类型
	 * @return 奖励列表
	 */
	List<SnActiveRewardRecordEntity> queryValidRewards(@Param("userId") Integer userId,
													   @Param("rewardType") Integer rewardType);

	/**
	 * 查询奖励发放列表
	 * @param searchReqVO
	 * @return
	 */
	IPage<RewardGiveOutVO> queryGiveOutList(IPage page, @Param("searchReqVO") GiveOutSearchReqVO searchReqVO);

	/**
	 * 更新奖励状态
	 *
	 * @param id 奖励ID
	 * @param oldStatus 原状态
	 * @param newStatus 新状态
	 * @return 影响行数
	 */
	int updateRewardStatus(@Param("id") Long id,
						   @Param("oldStatus") Integer oldStatus,
						   @Param("newStatus") Integer newStatus);

	/**
	 * 批量更新过期奖励状态
	 *
	 * @param rewardStatus 奖励状态
	 * @return 影响行数
	 */
	int batchUpdateExpiredStatus(@Param("rewardStatus") Integer rewardStatus);
}
