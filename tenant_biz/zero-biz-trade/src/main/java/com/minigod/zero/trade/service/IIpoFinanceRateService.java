package com.minigod.zero.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.trade.entity.IpoFinanceRateEntity;
import com.minigod.zero.trade.vo.IpoFinanceRateVO;

/**
 * IPO认购融资利率 服务类
 *
 * @author 掌上智珠
 * @since 2023-01-30
 */
public interface IIpoFinanceRateService extends IService<IpoFinanceRateEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoFinanceRate
	 * @return
	 */
	IPage<IpoFinanceRateVO> selectIpoFinanceRatePage(IPage<IpoFinanceRateVO> page, IpoFinanceRateVO ipoFinanceRate);

	/**
	 * 通过股票代码获取IPO认购融资利率信息
	 *
	 * @param assetId
	 * @return
	 */
	IpoFinanceRateEntity getIpoFinanceRate(String assetId);

	/**
	 * 保存/更新IPO认购融资利率
	 *
	 * @param ipoFinanceRate
	 */
	void saveOrUpdIpoFinanceRate(IpoFinanceRateEntity ipoFinanceRate);
}
