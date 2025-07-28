package com.minigod.zero.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.minigod.zero.manage.entity.SnCouponExchangeCodeDataEntity;
import com.minigod.zero.manage.entity.SnCouponExchangeCodeEntity;
import com.minigod.zero.manage.entity.SnCouponManageEntity;
import com.minigod.zero.manage.entity.SnCouponManageMatchEntity;
import com.minigod.zero.manage.vo.ExchangeCodeRespVO;
import com.minigod.zero.manage.vo.SnCouponManageMatchVO;
import com.minigod.zero.manage.vo.SnCouponManageVO;
import com.minigod.zero.manage.vo.request.CouponManageVO;
import com.minigod.zero.manage.vo.request.ExchangeBulkListReqVO;
import com.minigod.zero.manage.vo.request.ExchangeCodeReqVO;

import java.util.List;

/**
 * 优惠券管理服务
 *
 * @author eric
 * @since 2024-12-26 09:28:01
 */
public interface ICouponService {
	/**
	 * 分页查询优惠券兑换码列表
	 *
	 * @param page
	 * @param exchangeCodeReqVo
	 * @return
	 */
	IPage<ExchangeCodeRespVO> findExchangeCodePage(IPage<ExchangeCodeRespVO> page, ExchangeCodeReqVO exchangeCodeReqVo);

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
	 * 保存优惠券管理
	 *
	 * @param couponManageVo
	 */
	void saveCouponManage(CouponManageVO couponManageVo);

	/**
	 * 查询优惠券管理列表
	 *
	 * @param page
	 * @return
	 */
	IPage<SnCouponManageEntity> findCouponManagePage(IPage<SnCouponManageEntity> page);

	/**
	 * 查询优惠券管理详情
	 *
	 * @param manageId
	 * @return
	 */
	SnCouponManageEntity findCouponManage(Long manageId);

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

	/**
	 * 查询兑换码匹配表维护列表
	 *
	 * @return
	 */
	List<SnCouponManageMatchEntity> findSnCouponManageMatchList();

	/**
	 * 根据序列号查询匹配表数据
	 *
	 * @param serialNumList
	 * @return
	 */
	List<SnCouponManageMatchVO> findSnCouponManageMatchSerialNumData(String[] serialNumList);

	/**
	 * 根据兑换码查询匹配表数据
	 *
	 * @param codeList
	 * @return
	 */
	List<SnCouponManageMatchVO> findSnCouponManageMatchCodeData(String[] codeList);

	/**
	 * 分页查询优惠券兑换码批量列表
	 *
	 * @param page
	 * @param exchangeBulkListReqVo
	 * @return
	 */
	IPage<SnCouponManageVO> findExchangeBulkPage(IPage<SnCouponManageEntity> page, ExchangeBulkListReqVO exchangeBulkListReqVo);

	/**
	 * 删除兑换码匹配表维护列表
	 *
	 * @param list
	 */
	void deleteSnCouponManageMatchList(List<SnCouponManageMatchEntity> list);

	/**
	 * 保存兑换码匹配表维护列表
	 *
	 * @param list
	 * @return
	 */
	boolean saveSnCouponManageMatchList(List<SnCouponManageMatchEntity> list);

	/**
	 * 更新优惠券兑换码状态
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

}
