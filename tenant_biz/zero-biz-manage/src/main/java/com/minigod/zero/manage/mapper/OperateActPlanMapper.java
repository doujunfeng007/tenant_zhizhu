package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.OperateActPlanEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 运营活动方案表Mapper接口
 *
 * @author eric
 * @date 2024-12-23 16:00:08
 */
@Mapper
public interface OperateActPlanMapper extends BaseMapper<OperateActPlanEntity> {

	/**
	 * 分页查询活动方案
	 *
	 * @param page     分页参数
	 * @param planId   方案ID
	 * @param planName 方案名称
	 * @return 分页数据
	 */
	IPage<OperateActPlanEntity> queryPage(IPage<OperateActPlanEntity> page,
										  @Param("planId") Long planId,
										  @Param("planName") String planName);

	/**
	 * 查询有效的活动方案列表
	 *
	 * @param isBusinessReward 是否商务渠道奖励
	 * @param isTransferReward 是否转仓奖励
	 * @return 方案列表
	 */
	List<OperateActPlanEntity> queryValidPlans(@Param("isBusinessReward") Boolean isBusinessReward,
											   @Param("isTransferReward") Boolean isTransferReward);

	/**
	 * 更新方案状态
	 *
	 * @param id     方案ID
	 * @param status 状态
	 * @return 影响行数
	 */
	int updateStatus(@Param("id") Long id, @Param("status") Integer status);

	/**
	 * 更新奖励开关状态
	 *
	 * @param id               方案ID
	 * @param isBusinessReward 商务渠道奖励状态
	 * @param isTransferReward 转仓奖励状态
	 * @return 影响行数
	 */
	int updateRewardSwitch(@Param("id") Long id,
						   @Param("isBusinessReward") Boolean isBusinessReward,
						   @Param("isTransferReward") Boolean isTransferReward);

}
