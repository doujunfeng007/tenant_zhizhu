package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.OperateRuleRewardRelationEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 运营规则奖励关系表Mapper接口
 *
 * @author eric
 * @date 2024-12-23 16:20:08
 */
@Mapper
public interface OperateRuleRewardRelationMapper extends BaseMapper<OperateRuleRewardRelationEntity> {

	/**
	 * 分页查询规则奖励关系
	 *
	 * @param page      分页参数
	 * @param ruleId    规则ID
	 * @param claimType 领取方式
	 * @return 分页数据
	 */
	IPage<OperateRuleRewardRelationEntity> queryPage(IPage<OperateRuleRewardRelationEntity> page,
													 @Param("ruleId") Long ruleId,
													 @Param("claimType") Integer claimType);

	/**
	 * 查询规则下的奖励配置
	 *
	 * @param ruleId 规则ID
	 * @return 奖励配置列表
	 */
	List<OperateRuleRewardRelationEntity> queryByRuleId(@Param("ruleId") Long ruleId);

	/**
	 * 查询奖品关联的规则
	 *
	 * @param rewardId 奖品ID
	 * @return 规则奖励关系列表
	 */
	List<OperateRuleRewardRelationEntity> queryByRewardId(@Param("rewardId") Long rewardId);

	/**
	 * 批量更新规则奖励关系状态
	 *
	 * @param ruleId 规则ID
	 * @param status 状态
	 * @return 影响行数
	 */
	int updateStatusByRuleId(@Param("ruleId") Long ruleId,
							 @Param("status") Integer status);

	/**
	 * 查询规则奖励关系
	 *
	 * @param ruleId
	 * @return
	 */
	List<OperateRuleRewardRelationEntity> findOperateRuleRewardRelation(@Param("ruleId") Long ruleId);

	/**
	 * 删除规则奖励关系
	 *
	 * @param ruleIds
	 * @return
	 */
	int deleteOperateRuleRewardRelation(@Param("ruleIds") Long[] ruleIds);
}
