package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.constants.DataFormatUtil;
import com.minigod.zero.trade.hs.constants.GrmHsConstants;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.resp.TradeEvent;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class TradeEventNotifyService {
	private static final Logger logger = LoggerFactory.getLogger(TradeEventNotifyService.class);
//	@Autowired
//	private MsgService msgService;
//	@Autowired
//	private CommMktmgrService mktMgr;
//	@Autowired
//	private MktInfoService mktInfoService;
//	@Autowired
//	private UserService userService;
//	@Autowired
//    private UserInfoService userInfoService;
//	@Autowired
//	private PlatformService platformService;

	private final DecimalFormat DOUBLE4DF = new DecimalFormat("#.####");

	private final int QUEUE_SIZE = 2000; // 设置队列大小，防止内存耗尽
	// 消息队列
	private BlockingQueue<TradeEvent> messageQueue = new LinkedBlockingQueue<TradeEvent>(QUEUE_SIZE);
	private final int THREADS = 2;
	private final ExecutorService executor = Executors.newFixedThreadPool(THREADS);

	public void dispatchModifyCancelEvent(GrmRequestVO request, Map<String, String> oParam, GrmDataVO grmDataVO) {
		try {
			doDispatchModifyCancelEvent(request, oParam, grmDataVO);
		} catch (Exception ex) {
			logger.error("dispatchModifyCancelEvent", ex);
		}
	}
	private void doDispatchModifyCancelEvent(GrmRequestVO request, Map<String, String> oParam, GrmDataVO grmDataVO) {
		String exchangeType = oParam.get(HsConstants.Fields.EXCHANGE_TYPE);
		String stockCode = oParam.get(HsConstants.Fields.STOCK_CODE);
		String assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(stockCode, exchangeType), exchangeType);
		//
		String amount = oParam.get(HsConstants.Fields.ENTRUST_AMOUNT);
		String price = oParam.get(HsConstants.Fields.ENTRUST_PRICE);
		// String entrustBs = oParam.get(HsConstants.Fields.ENTRUST_BS);

		String entrustType = oParam.get(HsConstants.Fields.ENTRUST_TYPE);
		String ip = request.getIpAddress();
		//
		TradeEvent event = new TradeEvent();
		event.setFundAccount(oParam.get(HsConstants.Fields.FUND_ACCOUNT));
		event.setEntrustType(entrustType);
		event.setAmount(amount);
		event.setAssetId(assetId);
		event.setExchangeType(exchangeType);
		event.setIp(ip);
		event.setPrice(price);
		event.setStockCode(stockCode);
		event.setDate(new Date());
		event.setSessionId(request.getSessionId());
		// 异步处理
		if (!messageQueue.offer(event)) {
			logger.error("dispatchBuySellEvent消息队列溢出，消息丢弃。event={}", JSON.toJSON(event));
		}
	}

	/**
	 * 检查用户的交易通知开关是否有打开
	 */
	private boolean isUserTradeNotificationEnabled(Integer userId) {
		if (userId == null) {
			return false;
		}
		// TODO
//		ReqUserSwitchVO vo = new ReqUserSwitchVO();
//		UserSwitch params = new UserSwitch();
//		params.setSessionUserId(userId);
//		params.setFlag((long) (1 << 5)); // 开关位置
//		vo.setParams(params);
//		ResponseVO resp = userService.findUserSwitch(vo);
//		if (resp != null && resp.getCode() != null
//				&& StaticType.CodeType.OK.getCode() == resp.getCode()) {
//			RespUserSwitchVO result = (RespUserSwitchVO) resp.getResult();
//			String value = result.getValue();
//			return "Y".equals(value);
//		}
		return false;
	}
	public void dispatchBuySellEvent(GrmRequestVO request, Map<String, String> oParam, GrmDataVO grmDataVO) {
		try {
			doDispatchBuySellEvent(request, oParam, grmDataVO);
		} catch (Exception ex) {
			logger.error("dispatchBuySellEvent", ex);
		}
	}
	private void doDispatchBuySellEvent(GrmRequestVO request, Map<String, String> oParam, GrmDataVO grmDataVO) {
		String exchangeType = oParam.get(HsConstants.Fields.EXCHANGE_TYPE);
		String stockCode = oParam.get(HsConstants.Fields.STOCK_CODE);
		String assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(stockCode, exchangeType), exchangeType);
		//
		String amount = oParam.get(HsConstants.Fields.ENTRUST_AMOUNT);
		String price = oParam.get(HsConstants.Fields.ENTRUST_PRICE);
		String entrustBs = oParam.get(HsConstants.Fields.ENTRUST_BS);
		String entrustType = oParam.get(HsConstants.Fields.ENTRUST_TYPE);
		String ip = request.getIpAddress();
		//
		TradeEvent event = new TradeEvent();
		event.setFundAccount(oParam.get(HsConstants.Fields.FUND_ACCOUNT));
		event.setEntrustType(entrustType);
		event.setAmount(amount);
		event.setAssetId(assetId);
		event.setEntrustBs(entrustBs);
		event.setExchangeType(exchangeType);
		event.setIp(ip);
		event.setPrice(price);
		event.setStockCode(stockCode);
		event.setDate(new Date());
		event.setSessionId(request.getSessionId());
		// 异步处理
		if (!messageQueue.offer(event)) {
			logger.error("dispatchBuySellEvent消息队列溢出，消息丢弃。event={}", JSON.toJSON(event));
		}
	}

	private void processMessage() throws InterruptedException {
		TradeEvent msg = messageQueue.take();
		if (msg == null){
			return;
		}
// TODO
//		Integer userId = userInfoService.getUserIdBySession(msg.getSessionId());
//		if (userId == null) {
//			logger.info("sessionId找不到对应的userId: {}", msg.getSessionId());
//			return;
//		}
//		// 交易提醒
//		if (isUserTradeNotificationEnabled(userId)) {
//			sendTradeNotification(msg);
//		}
//		// IPO跟单提醒
//		if (GrmHsConstants.EntrustType.BUYSELL.getCode().equals(msg.getEntrustType())) {
//			if (GrmHsConstants.EntrustBs.SELL.getCode().equals(msg.getEntrustBs())) {
//				// 卖出
//				if (isNiuren(userId)) {
//					String stk = "";
//					StkInfo stkInfo = mktInfoService.findStkInfo(msg.getAssetId());
//					if (stkInfo != null) {
//						stk = stkInfo.getStkName();
//					}
//					stk = stk + " " + msg.getAssetId();
//					String nickName = getNiurenNickName(userId);
//					List<String> params = new ArrayList<String>();
//					params.add(nickName);
//					params.add(stk);
//					platformService.sendIpoSubscrPush(userId, params, MsgTempEnum.HNEW_SELL_PUSH.getCode());
//				}
//			}
//		}
	}

	/**
	 * 查找牛人昵称
	 * @param userId
	 * @return
	 */
	private String getNiurenNickName(Integer userId) {
		// TODO 缓存昵称
		// TODO
//		UserBaseVO vo = new UserBaseVO();
//		SNUserBase params = new SNUserBase();
//		params.setSessionUserId(userId);
//		params.setFlag(UserSummaryFlag.nickname.getIndex());
//		vo.setParams(params);
//		ResponseVO ret = userInfoService.findUserSummary(vo);
//		UserSummaryVO summary = (UserSummaryVO) ret.getResult();
//		if (summary == null || StringUtils.isBlank(summary.getNickname())) {
//			return "牛人";
//		}
//		return summary.getNickname();
		return null;
	}

	private boolean isNiuren(Integer userId) {
		// TODO 缓存牛人userId
		if (userId == null) {
			return false;
		}
		// TODO
//		List<IpoGeniusInfo> niurenList = userService.findIpoGeniusInfoList(true);
//		if (CollectionUtils.isEmpty(niurenList)) {
//			return false;
//		}
//		for (IpoGeniusInfo niuren : niurenList) {
//			Integer niurenUserId = niuren.getUserId();
//			if (userId.equals(niurenUserId)) {
//				return true;
//			}
//		}
		return false;
	}

	private void sendTradeNotification(TradeEvent msg) throws InterruptedException {
		String buySell = GrmHsConstants.EntrustBs.BUY.getCode().equals(msg.getEntrustBs()) ? "买入" : "卖出";
		double totalPrice = Double.parseDouble(msg.getAmount())*Double.parseDouble(msg.getPrice());
		String totalPriceStr = DOUBLE4DF.format(totalPrice);
		//
		String ipStr = getIpAddr(msg.getIp());
		String assetId = msg.getAssetId();
		//
		StkInfo stkInfo = findStkInfo(assetId);
		String stk = "";
		if (stkInfo != null) {
			stk = stkInfo.getStkName();
		}
		if (StringUtils.isBlank(stk)) {
			stk = assetId;
		} else {
			stk = stk + "（" + assetId + "）";
		}
		String fundAccount = msg.getFundAccount();
		//
// TODO
//		EmailMsg bean = new EmailMsg();
//		String entrustType = msg.getEntrustType();
//		if (GrmHsConstants.EntrustType.BUYSELL.getCode().equals(entrustType)) {
//	    	bean.setCode(EmailTempEnum.XD_SUCCESS.getCode()); // 下单 1068
//	    	bean.setFundAccount(fundAccount);
//	    	List<String> list = Lists.newArrayList();
//	    	list.add(DateUtils.dateToString(msg.getDate(), TimeFormatter.YYYY_MM_DD_HH_MM_SS));
//	    	list.add(ipStr);
//	    	list.add("掌上智珠");
//	    	list.add(buySell);
//	    	list.add(stk);
//	    	list.add(msg.getPrice());
//	    	list.add(msg.getAmount());
//	    	list.add(totalPriceStr);
//			bean.setList(list);
//		} else if (GrmHsConstants.EntrustType.MODIFY.getCode().equals(entrustType)) {
//			// 改单
//			bean.setCode(EmailTempEnum.GD_SUCCESS.getCode()); // 改单 1069
//			bean.setFundAccount(fundAccount);
//	    	List<String> list = Lists.newArrayList();
//	    	list.add(DateUtils.dateToString(msg.getDate(), TimeFormatter.YYYY_MM_DD_HH_MM_SS));
//	    	list.add(ipStr);
//	    	list.add("掌上智珠");
////	    	list.add(buySell);
//	    	list.add(stk);
//	    	list.add(msg.getPrice());
//	    	list.add(msg.getAmount());
//	    	list.add(totalPriceStr);
//			bean.setList(list);
//		} else if (GrmHsConstants.EntrustType.CANCEL.getCode().equals(entrustType)) {
//			// 撤单
//			bean.setCode(EmailTempEnum.CD_SUCCESS.getCode()); // 撤单 1070
//			bean.setFundAccount(fundAccount);
//	    	List<String> list = Lists.newArrayList();
//	    	list.add(DateUtils.dateToString(msg.getDate(), TimeFormatter.YYYY_MM_DD_HH_MM_SS));
//	    	list.add(ipStr);
//	    	list.add("掌上智珠");
////	    	list.add(buySell);
//	    	list.add(stk);
//	    	list.add(msg.getPrice());
//	    	list.add(msg.getAmount());
//	    	list.add(totalPriceStr);
//			bean.setList(list);
//		} else {
//			// 其他
//			return;
//		}
//		msgService.sendEmail(bean);
	}

	private StkInfo findStkInfo(String assetId) {
		try {
			// TODO return mktInfoService.findStkInfo(assetId);
			return null;
		} catch (Exception ex) {
			logger.error("findStkInfo failed", ex);
			return null;
		}
	}

	private String getIpAddr(String ip) {
		try {
// TODO
//			ResponseVO ipRep = mktMgr.getIpAddr(ip);
//			String ipStr = String.valueOf(ipRep.getResult());
//			return ipStr;
			return null;
		} catch (Exception ex) {
			logger.error("getIpAddr failed, using empty string.", ex);
			return "";
		}
	}

	@PostConstruct
	public void init() {
		for (int i = 0; i < THREADS; i++) {
			executor.execute(new Task(i));
		}
	}

	private class Task implements Runnable {
		private int id;
		Task(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			logger.info("Starting TradeEventNotifyService consumer {} ...", id);
			while (true) {
				try {
					processMessage();
				} catch (Exception ex) {
					logger.error("processMessage", ex);
				}
			}
		}
	}

}
