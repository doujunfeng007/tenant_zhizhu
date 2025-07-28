package com.minigod.zero.manage.service;

import com.minigod.zero.core.mp.base.BaseService;
import com.minigod.zero.manage.entity.SnCouponManageMatchEntity;
import com.minigod.zero.manage.vo.SnCouponManageMatchVO;

import java.util.List;

/**
 * 兑换码匹配表维护服务接口定义
 *
 * @author eric
 * @since 2024-12-26 16:01:08
 */
public interface ISnCouponManageMatchService extends BaseService<SnCouponManageMatchEntity> {
	/**
	 * 查询兑换码匹配表维护列表
	 *
	 * @return
	 */
	List<SnCouponManageMatchEntity> findSnCouponManageMatchList();

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
}
