package com.minigod.zero.trade.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minigod.zero.trade.entity.IpoFinanceQueueOrderEntity;
import com.minigod.zero.trade.vo.IpoFinanceQueueOrderVO;
import com.minigod.zero.trade.vo.IpoQueueInfoVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * IPO融资排队订单表 服务类
 *
 * @author 掌上智珠
 * @since 2023-02-06
 */
public interface IIpoFinanceQueueOrderService extends IService<IpoFinanceQueueOrderEntity> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param ipoFinanceQueueOrder
	 * @return
	 */
	IPage<IpoFinanceQueueOrderVO> selectIpoFinanceQueueOrderPage(IPage<IpoFinanceQueueOrderVO> page, IpoFinanceQueueOrderVO ipoFinanceQueueOrder);

	/**
	 * 获取有排队订单的股票名称 ，排队订单数
	 *
	 * @return
	 */
	List<IpoQueueInfoVO> findQueueOrderInfo();

	/**
	 * 查询排队中的订单
	 *
	 * @return
	 */
	List<IpoFinanceQueueOrderEntity> findQueueList(String stockCode);

	/**
	 * 更新融资排队订单信息
	 *
	 * @param ipoFinanceQueueOrder
	 * @return
	 */
	int updateIpoFinance(IpoFinanceQueueOrderEntity ipoFinanceQueueOrder);

	/**
	 *  根据资金账号，股票代码 查询订单信息
	 * @param search
	 * @return
	 */
	IpoFinanceQueueOrderEntity getIpoFinance(IpoFinanceQueueOrderEntity search);

	/**
	 * 根据assetId查询累计排队总额
	 * @param assetId
	 * @param i
	 * @param b
	 * @return
	 */
	BigDecimal getMaxQueueAmount(String assetId, int i, boolean b);

	/**
	 * 根据资金账号，股票代码 查询订单信息
	 * @param order
	 * @return
	 */
	IpoFinanceQueueOrderEntity getIpoFinanceDb(IpoFinanceQueueOrderEntity order);

	/**
	 * 根据ID查询一个
	 * @param order
	 * @return
	 */
	IpoFinanceQueueOrderEntity getIpoFinanceQueueOrderById(IpoFinanceQueueOrderEntity order);

	/**
	 * 添加融资排队订单
	 * @param ipoFinanceQueueOrder
	 */
	Integer saveIpoFinance(IpoFinanceQueueOrderEntity ipoFinanceQueueOrder);
}
