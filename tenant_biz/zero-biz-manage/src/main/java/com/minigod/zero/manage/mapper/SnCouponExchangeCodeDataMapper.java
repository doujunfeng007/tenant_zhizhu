package com.minigod.zero.manage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SnCouponExchangeCodeDataEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优惠券兑换码数据统计Mapper接口
 *
 * @author eric
 * @date 2024-12-23 16:50:08
 */
@Mapper
public interface SnCouponExchangeCodeDataMapper extends BaseMapper<SnCouponExchangeCodeDataEntity> {

	/**
	 * 分页查询数据统计
	 *
	 * @param page 分页参数
	 * @param channelId 渠道ID
	 * @param cardName 卡券名称
	 * @param cardType 卡券类型
	 * @return 分页数据
	 */
	IPage<SnCouponExchangeCodeDataEntity> queryPage(IPage<SnCouponExchangeCodeDataEntity> page,
													@Param("channelId") String channelId,
													@Param("cardName") String cardName,
													@Param("cardType") Integer cardType);

	/**
	 * 查询渠道数据统计
	 *
	 * @param channelId 渠道ID
	 * @return 数据统计列表
	 */
	List<SnCouponExchangeCodeDataEntity> queryByChannel(@Param("channelId") String channelId);

	/**
	 * 查询优惠券数据统计
	 *
	 * @param manageId 优惠券管理ID
	 * @return 数据统计信息
	 */
	SnCouponExchangeCodeDataEntity getByManageId(@Param("manageId") Long manageId);

	/**
	 * 更新数据统计
	 *
	 * @param entity 数据统计实体
	 * @return 影响行数
	 */
	int updateStatistics(SnCouponExchangeCodeDataEntity entity);
}
