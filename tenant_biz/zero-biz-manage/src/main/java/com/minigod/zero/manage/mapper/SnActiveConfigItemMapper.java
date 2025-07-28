package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SnActiveConfigItemEntity;
import com.minigod.zero.manage.vo.SnActiveConfigItemVO;
import com.minigod.zero.manage.vo.request.RewardSearchReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 活动营销奖品库Mapper接口
 *
 * @author eric
 * @date 2024-12-23 14:38:08
 */
@Mapper
public interface SnActiveConfigItemMapper extends BaseMapper<SnActiveConfigItemEntity> {

	/**
	 * 分页查询活动奖品列表
	 *
	 * @param page             分页参数
	 * @param snActiveConfigId 活动ID
	 * @param rewardType       奖励类型
	 * @return 分页数据
	 */
	IPage<SnActiveConfigItemEntity> queryPage(IPage<SnActiveConfigItemEntity> page,
											  @Param("snActiveConfigId") Long snActiveConfigId,
											  @Param("rewardType") Integer rewardType);

	/**
	 * 分页查询活动奖品列表
	 *
	 * @param page
	 * @param searchReqVO
	 * @return
	 */
	List<SnActiveConfigItemVO> queryPageBySearch(IPage page, RewardSearchReqVO searchReqVO);

	/**
	 * 根据活动ID查询奖品列表
	 *
	 * @param snActiveConfigId 活动ID
	 * @return 奖品列表
	 */
	List<SnActiveConfigItemEntity> queryByActiveConfigId(@Param("snActiveConfigId") Long snActiveConfigId);

	/**
	 * 更新奖品库存
	 *
	 * @param id    奖品ID
	 * @param count 更新数量(正数增加，负数减少)
	 * @return 影响行数
	 */
	int updateRemainCount(@Param("id") Long id, @Param("count") Integer count);

	/**
	 * 根据规则ID获取奖品列表
	 *
	 * @param ruleId 规则ID
	 * @return 奖品列表
	 */
	List<SnActiveConfigItemEntity> getRewardsListByRule(@Param("ruleId") Long ruleId);
}
