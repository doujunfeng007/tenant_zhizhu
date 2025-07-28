package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SnCouponManageEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优惠券管理Mapper接口
 *
 * @author eric
 * @date 2024-12-23 16:30:08
 */
@Mapper
public interface SnCouponManageMapper extends BaseMapper<SnCouponManageEntity> {

	/**
	 * 分页查询优惠券
	 *
	 * @param page 分页参数
	 * @param cardName 卡券名称
	 * @param channelId 渠道ID
	 * @param useType 使用条件
	 * @return 分页数据
	 */
	IPage<SnCouponManageEntity> queryPage(IPage<SnCouponManageEntity> page,
										  @Param("cardName") String cardName,
										  @Param("channelId") String channelId,
										  @Param("useType") Integer useType);

	/**
	 * 查询有效的优惠券
	 *
	 * @param channelId 渠道ID
	 * @param useType 使用条件
	 * @return 优惠券列表
	 */
	List<SnCouponManageEntity> queryValidCoupons(@Param("channelId") String channelId,
												 @Param("useType") Integer useType);

	/**
	 * 根据兑换码查询优惠券
	 *
	 * @param code 兑换码
	 * @return 优惠券信息
	 */
	SnCouponManageEntity getByCode(@Param("code") String code);

	/**
	 * 更新优惠券使用状态
	 *
	 * @param id 优惠券ID
	 * @param useStatus 使用状态
	 * @return 影响行数
	 */
	int updateUseStatus(@Param("id") Long id,
						@Param("useStatus") Integer useStatus);
}
