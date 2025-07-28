package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.manage.entity.SnCouponExchangeCodeDataEntity;
import com.minigod.zero.manage.vo.request.ExchangeCodeReqVO;

import java.util.List;

/**
 * 优惠券兑换码数据统计服务接口定义
 *
 * @author eric
 * @date 2024-12-26 10:48:08
 * @TableName sn_coupon_exchange_code_data
 */
public interface ISnCouponExchangeCodeDataService extends BaseService<SnCouponExchangeCodeDataEntity> {
	/**
	 * 分页查询优惠券兑换码数据统计
	 *
	 * @param page
	 * @param exchangeCodeReqVo
	 * @return
	 */
	IPage<SnCouponExchangeCodeDataEntity> findCouponExchangeCodeDataPage(IPage<SnCouponExchangeCodeDataEntity> page, ExchangeCodeReqVO exchangeCodeReqVo);

	/**
	 * 查询优惠券兑换码数据统计列表
	 *
	 * @param exchangeCodeReqVo
	 * @return
	 */
	List<SnCouponExchangeCodeDataEntity> findCouponExchangeCodeDataList(ExchangeCodeReqVO exchangeCodeReqVo);

	/**
	 * 保存优惠券兑换码数据统计
	 *
	 * @param entity
	 * @return
	 */
	boolean saveCouponExchangeCodeData(SnCouponExchangeCodeDataEntity entity);
}
