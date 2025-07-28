package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.OperateActRewardPlanEntity;
import com.minigod.zero.manage.vo.RuleRewardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 运营活动奖励方案表Mapper接口
 *
 * @author eric
 * @date 2024-12-23 16:10:08
 */
@Mapper
public interface OperateActRewardPlanMapper extends BaseMapper<OperateActRewardPlanEntity> {

	/**
	 * 分页查询奖励方案
	 *
	 * @param page    分页参数
	 * @param planId  方案ID
	 * @param funType 功能类型
	 * @return 分页数据
	 */
	IPage<OperateActRewardPlanEntity> queryPage(IPage<OperateActRewardPlanEntity> page,
												@Param("planId") Long planId,
												@Param("funType") Integer funType);

	/**
	 * 查询方案下的奖励配置
	 *
	 * @param planId  方案ID
	 * @param funType 功能类型
	 * @return 奖励配置列表
	 */
	List<OperateActRewardPlanEntity> queryByPlanId(@Param("planId") Long planId,
									 @Param("funType") Integer funType);


	/**
	 * 查询方案下的奖励配置
	 *
	 * @param planId  方案ID
	 * @param funType 功能类型
	 * @return 奖励配置列表
	 */
	List<RuleRewardVO> queryRuleRewardByPlanId(@Param("planId") Long planId,
											   @Param("funType") Integer funType);

	/**
	 * 查询符合金额条件的奖励配置
	 *
	 * @param planId  方案ID
	 * @param funType 功能类型
	 * @param amount  金额
	 * @return 奖励配置
	 */
	OperateActRewardPlanEntity getMatchedReward(@Param("planId") Long planId,
												@Param("funType") Integer funType,
												@Param("amount") BigDecimal amount);

	/**
	 * 批量更新奖励方案状态
	 *
	 * @param planId 方案ID
	 * @param status 状态
	 * @return 影响行数
	 */
	int updateStatusByPlanId(@Param("planId") Long planId,
							 @Param("status") Integer status);

	/**
	 * 删除奖励方案
	 *
	 * @param planId
	 * @param funType
	 * @return
	 */
	int deleteOperateActRewardPlan(@Param("planId") Long planId,
								   @Param("funType") Integer funType);
}
