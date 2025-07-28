package com.minigod.zero.trade.service.options;

import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.trade.vo.req.options.*;

/**
 * @author chen
 * @ClassName IOptionsService.java
 * @Description TODO
 * @createTime 2024年08月27日 11:16:00
 */
public interface IOptionsService {
	/**
	 * 询价
	 * @param inquiryReq
	 * @return
	 */
	R queryInquiry(InquiryReq inquiryReq);

	/**
	 * 下单
	 * @param inquiryPlaceOrderReq
	 * @return
	 */
	R placeOrder(InquiryPlaceOrderReq inquiryPlaceOrderReq);

	/**
	 * 撤单
	 * @param inquiryCancelOrderReq
	 * @return
	 */
	R cancelOrder(InquiryCancelOrderReq inquiryCancelOrderReq);

	/**
	 * 改单
	 * @param inquiryUpdateOrderReq
	 * @return
	 */
	R updateOrder(InquiryUpdateOrderReq inquiryUpdateOrderReq);

	R getUserPortfolio(PortfolioReq portfolioReq);

	/**
	 * 今日委托
	 * @param journalOrdersReq
	 * @return
	 */
	R getJournalOrders(JournalOrdersReq journalOrdersReq);

	/**
	 * 历史委托
	 * @param journalOrdersReq
	 * @return
	 */
	R getHistoryOrders(JournalOrdersReq journalOrdersReq);

	/**
	 * 成交订单
	 * @param inquiryCancelOrderReq
	 * @return
	 */
	R completed(InquiryCancelOrderReq inquiryCancelOrderReq);

}
