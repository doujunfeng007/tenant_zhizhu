package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.manage.entity.SnCouponManageEntity;
import com.minigod.zero.manage.vo.SnCouponManageVO;
import com.minigod.zero.manage.vo.request.ExchangeBulkListReqVO;

/**
 * 优惠券管理服务接口
 *
 * @author eric
 * @since 2024-12-26 10:11:08
 */
public interface ISnCouponManageService extends BaseService<SnCouponManageEntity> {
	/**
	 * 分页查询优惠券管理
	 *
	 * @param page
	 * @return
	 */
	IPage<SnCouponManageEntity> findCouponManagePage(IPage<SnCouponManageEntity> page);

	/**
	 * 批量兑换码列表
	 *
	 * @param exchangeBulkListReqVo
	 * @return
	 */
	IPage<SnCouponManageVO> findExchangeBulkPage(IPage<SnCouponManageEntity> page, ExchangeBulkListReqVO exchangeBulkListReqVo);

	/**
	 * 保存优惠券管理
	 *
	 * @param couponManage
	 */
	void saveCouponManage(SnCouponManageEntity couponManage);

	/**
	 * 查询优惠券管理详情
	 *
	 * @param manageId
	 * @return
	 */
	SnCouponManageEntity findCouponManage(Long manageId);
}
