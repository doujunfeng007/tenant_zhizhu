package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.manage.entity.SnCouponExchangeCodeEntity;
import com.minigod.zero.manage.vo.ExchangeCodeRespVO;
import com.minigod.zero.manage.vo.request.ExchangeCodeReqVO;

import java.util.List;

/**
 * 优惠券兑换码服务接口
 *
 * @author eric
 * @since 2024-12-26 10:08:01
 */
public interface ISnCouponExchangeCodeService extends BaseService<SnCouponExchangeCodeEntity> {

	/**
	 * 分页查询优惠券兑换码列表
	 *
	 * @param page
	 * @param exchangeCodeReqVo
	 * @return
	 */
	IPage<ExchangeCodeRespVO> findExchangeCodePage(IPage<ExchangeCodeRespVO> page, ExchangeCodeReqVO exchangeCodeReqVo);

	/**
	 * 批量插入优惠券兑换码
	 *
	 * @param list
	 * @return
	 */
	boolean batchInsert(List<SnCouponExchangeCodeEntity> list);

	/**
	 * 更新优惠券兑换码
	 *
	 * @param id
	 */
	void updateExchangeCode(Long id);

	/**
	 * 更新优惠券兑换码状态延长天数
	 *
	 * @param id
	 * @param recordStatus
	 * @param days
	 */
	void updateExchangeCode(Long id, Integer recordStatus, Integer days);

	/**
	 * 查询优惠券兑换码列表
	 *
	 * @param manageId 优惠券管理ID
	 * @return
	 */
	List<SnCouponExchangeCodeEntity> findCouponExchangeCodeList(Long manageId);

	/**
	 * 查询优惠券兑换码列表
	 *
	 * @param exchangeCodeReqVo
	 * @return
	 */
	List<ExchangeCodeRespVO> findExchangeCodeList(ExchangeCodeReqVO exchangeCodeReqVo);

}
