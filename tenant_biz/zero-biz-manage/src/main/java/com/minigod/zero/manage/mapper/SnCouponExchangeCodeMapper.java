package com.minigod.zero.manage.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SnCouponExchangeCodeEntity;
import com.minigod.zero.manage.vo.ExchangeCodeRespVO;
import com.minigod.zero.manage.vo.request.ExchangeCodeReqVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优惠券兑换码Mapper接口
 *
 * @author eric
 * @date 2024-12-23 16:40:08
 */
@Mapper
public interface SnCouponExchangeCodeMapper extends BaseMapper<SnCouponExchangeCodeEntity> {

	/**
	 * 分页查询兑换码
	 *
	 * @param page         分页参数
	 * @param manageId     优惠券管理ID
	 * @param code         激活码
	 * @param useStatus    使用状态
	 * @param recordStatus 兑换状态
	 * @return 分页数据
	 */
	IPage<SnCouponExchangeCodeEntity> queryPage(IPage<SnCouponExchangeCodeEntity> page,
												@Param("manageId") Long manageId,
												@Param("code") String code,
												@Param("useStatus") Integer useStatus,
												@Param("recordStatus") Integer recordStatus);

	/**
	 * 分页查询兑换码
	 *
	 * @param exchangeCodeReqVo
	 * @return
	 */
	IPage<ExchangeCodeRespVO> findExchangeCodePage(IPage<ExchangeCodeRespVO> page,
												   @Param("exchangeCodeReqVo") ExchangeCodeReqVO exchangeCodeReqVo);

	/**
	 * 根据激活码查询
	 *
	 * @param code 激活码
	 * @return 兑换码信息
	 */
	SnCouponExchangeCodeEntity getByCode(@Param("code") String code);

	/**
	 * 查询用户的兑换记录
	 *
	 * @param useUid   用户ID
	 * @param manageId 优惠券管理ID
	 * @return 兑换记录列表
	 */
	List<SnCouponExchangeCodeEntity> queryUserExchanges(@Param("useUid") Integer useUid,
														@Param("manageId") Long manageId);

	/**
	 * 查询兑换码列表
	 *
	 * @param exchangeCodeReqVo
	 * @return
	 */
	List<ExchangeCodeRespVO> findExchangeCodeList(@Param("exchangeCodeReqVo") ExchangeCodeReqVO exchangeCodeReqVo);

	/**
	 * 更新兑换码状态
	 *
	 * @param id           兑换码ID
	 * @param useStatus    使用状态
	 * @param recordStatus 兑换状态
	 * @param oldVersion   原版本号
	 * @return 影响行数
	 */
	int updateStatus(@Param("id") Long id,
					 @Param("useStatus") Integer useStatus,
					 @Param("recordStatus") Integer recordStatus,
					 @Param("oldVersion") Integer oldVersion);
}
