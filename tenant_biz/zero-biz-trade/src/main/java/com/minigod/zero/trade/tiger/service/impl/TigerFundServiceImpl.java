package com.minigod.zero.trade.tiger.service.impl;

import cn.hutool.core.date.DateUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.trade.fund.vo.request.*;
import com.minigod.zero.trade.hs.constants.Constants;
import com.minigod.zero.trade.hs.service.GrmCacheMgr;
import com.minigod.zero.trade.service.IFundService;
import com.minigod.zero.trade.tiger.req.OrderQueryReq;
import com.minigod.zero.trade.tiger.req.PositionsReq;
import com.minigod.zero.trade.tiger.req.UserAccountQuery;
import com.minigod.zero.trade.tiger.resp.*;
import com.minigod.zero.trade.tiger.service.TigerCommonService;
import com.minigod.zero.trade.utils.HSUtil;
import com.minigod.zero.trade.utils.MarketUtils;
import com.minigod.zero.trade.vo.afe.AfeExchangeTypeEnum;
import com.minigod.zero.trade.vo.sjmb.ExchangeTypeEnum;
import com.minigod.zero.trade.vo.sjmb.req.*;
import com.minigod.zero.trade.vo.sjmb.resp.*;
import com.minigod.zero.trade.vo.tiger.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_TIGER;


/**
 * @author chen
 * @date 2025/5/19 14:50
 * @description
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_TIGER)
public class TigerFundServiceImpl implements IFundService {

	@Autowired
	private TigerCommonService tigerCommonService;

	@Resource
	protected GrmCacheMgr grmCacheMgr;

	@Resource
	ZeroRedis zeroRedis;

	@Override
	public R<FundQueryVO> getFundInfo(FundInfoRequest request) {
		FundQueryVO fundQueryVO = new FundQueryVO();
		List<AssetInfoVO> fundStats;
		List<PositionVO> hkPosition =new ArrayList<>();
		List<PositionVO> usPosition =new ArrayList<>();
		List<PositionVO> cnPosition=new ArrayList<>();

		UserAccountQuery baseRequest = new UserAccountQuery();
		baseRequest.setAccountId(request.getTradeAccount());

		R assetsQueryResult = tigerCommonService.queryAssets(baseRequest);

		if (assetsQueryResult.isSuccess()) {
			AssetsResp assetsResp = (AssetsResp) assetsQueryResult.getData();
			List<AssetsResp.Assets> assetsList = assetsResp.getAssets();
			/**
			 * 查询资金
			 */
			fundStats = fundList(assetsList);
			fundQueryVO.setFundStats(fundStats);

			/**
			 * 查询持仓
			 */
			PositionsReq positionsReq = new PositionsReq();
			positionsReq.setAccountId(request.getTradeAccount());
			positionsReq.setMarket(TigerExchangeTypeEnum.US.getCounterExchangeType());
			R rt = tigerCommonService.getAccountPositions(positionsReq);
			if(rt.isSuccess()){
				PositionResp positionsResp = (PositionResp) rt.getData();
				List<PositionVO> positions = listPosition(positionsResp);
				usPosition = positions.stream()
					.filter(position -> AfeExchangeTypeEnum.US.getExchangeType().equals(position.getExchangeType()))
					.collect(Collectors.toList());
			}

			positionsReq.setMarket(TigerExchangeTypeEnum.HK.getCounterExchangeType());
			rt = tigerCommonService.getAccountPositions(positionsReq);
			if(rt.isSuccess()){
				PositionResp positionsResp = (PositionResp) rt.getData();
				List<PositionVO> positions = listPosition(positionsResp);
				hkPosition = positions.stream()
					.filter(position -> AfeExchangeTypeEnum.HK.getExchangeType().equals(position.getExchangeType()))
					.collect(Collectors.toList());
			}

//			cnPosition = positions.stream()
//				.filter(position -> AfeExchangeTypeEnum.ML.getExchangeType().equals(position.getExchangeType()))
//				.collect(Collectors.toList());
			fundQueryVO.setHkPosition(hkPosition);
			fundQueryVO.setUsPosition(usPosition);
			fundQueryVO.setCnPosition(cnPosition);

		} else {
			return assetsQueryResult;
		}

		return R.data(fundQueryVO);
	}

	private List<AssetInfoVO> fundList(List<AssetsResp.Assets> assetsList) {
		List<AssetInfoVO> assetInfoVOList = new ArrayList<>();
		AssetInfoVO hk = new AssetInfoVO();
		hk.setCurrency(TigerCurrencyEnum.HKD.getCurrency());
		Optional<AssetsByCurrency> assets = assetsList.get(0).getAssetsByCurrency().stream()
			.filter(asset -> TigerCurrencyEnum.HKD.getCounterCurrency().equals(asset.getCurrency()))
			.findFirst();
		if (assets.isPresent()) {
			hk.setEnableBalance(assets.get().getAvailableCash());
			hk.setFrozenBalance(assets.get().getLockedCash());
			hk.setMarketValue(new BigDecimal("0"));
			hk.setAsset(assets.get().getCashBalance());
			hk.setFetchBalance(assets.get().getAvailableCash());
			hk.setCurrentBalance(assets.get().getCashBalance());
		}

		AssetInfoVO us = new AssetInfoVO();
		us.setCurrency(TigerCurrencyEnum.USD.getCurrency());
		assets = assetsList.get(0).getAssetsByCurrency().stream()
			.filter(asset -> TigerCurrencyEnum.USD.getCounterCurrency().equals(asset.getCurrency()))
			.findFirst();
		if (assets.isPresent()) {
			us.setEnableBalance(assets.get().getCashBalance());
			us.setFrozenBalance(assets.get().getLockedCash());
			us.setMarketValue(new BigDecimal("0"));
			us.setAsset(assets.get().getCashBalance());
			us.setFetchBalance(assets.get().getSettledCash());
			us.setCurrentBalance(assets.get().getCashBalance());
		}
		AssetInfoVO cn = new AssetInfoVO();
		cn.setCurrency(TigerCurrencyEnum.CNY.getCurrency());
		assetInfoVOList.add(hk);
		assetInfoVOList.add(us);
		assetInfoVOList.add(cn);
		return assetInfoVOList;
	}

	@Override
	public R getTotalAccount(TotalAccountRequest request) {
		return null;
	}

	@Override
	public R getDetailAccount(TotalAccountRequest request) {
		return null;
	}

	@Override
	public R getDetailAccount(Long custId, String fundAccount, String moneyType) {
		return null;
	}

	@Override
	public R getSingleAccount(SingleAccountRequest request) {
		return null;
	}

	@Override
	public R getUserPortfolio(UserPortfolioRequest request) {
		List<PositionVO> positions;
		PositionsReq positionsReq = new PositionsReq();
		positionsReq.setAccountId(request.getTradeAccount());
		positionsReq.setMarket(TigerExchangeTypeEnum.getCounterExchangeType(request.getExchangeType()));
		R rt = tigerCommonService.getAccountPositions(positionsReq);
		if (rt.isSuccess()) {
			PositionResp positionsResp = (PositionResp) rt.getData();
			positions = listPosition(positionsResp);
		} else {
			return rt;
		}
		return R.data(positions);
	}

	private List<PositionVO> listPosition(PositionResp positionsResp) {

		List<PositionVO> positions = new ArrayList<>();
		List<TigerPositionResp> tigerPositionRespList = positionsResp.getPositions();
		for (TigerPositionResp stockHolding : tigerPositionRespList) {
			PositionVO positionVO = new PositionVO();
			positionVO.setSecType("STOCK");
			positionVO.setExchangeType(TigerExchangeTypeEnum.getZsExchangeType(stockHolding.getContract().getMarket()));
			if (TigerExchangeTypeEnum.HK.getExchangeType().equals(positionVO.getExchangeType())) {
				positionVO.setStockCode(stockHolding.getContract().getSymbol());
				positionVO.setAssetId(MarketUtils.translateHkAssetId(positionVO.getStockCode()));
			} else if (TigerExchangeTypeEnum.US.getExchangeType().equals(positionVO.getExchangeType())) {
				// 美股
				positionVO.setStockCode(stockHolding.getContract().getSymbol());
				positionVO.setAssetId(MarketUtils.translateUsAssetId(positionVO.getStockCode()));
			} else if (TigerExchangeTypeEnum.ML.getExchangeType().equals(positionVO.getExchangeType())) {
				positionVO.setStockCode(stockHolding.getContract().getSymbol());
				positionVO.setAssetId(stockHolding.getContract().getSymbol() + "." + positionVO.getExchangeType());
			}
			StkInfo assetInfo = grmCacheMgr.getAssetInfo(positionVO.getAssetId());
			positionVO.setLotSize(assetInfo.getLotSize());
			positionVO.setSecSType(assetInfo.getSecSType());
			positionVO.setStockName(assetInfo.getEngName());
			positionVO.setStockNameZhCN(assetInfo.getStkName());
			positionVO.setStockNameZhHK(assetInfo.getTraditionalName());
			positionVO.setAvBuyPrice(stockHolding.getAverageCostOfCarry());
			positionVO.setCostPrice(stockHolding.getAverageCostOfCarry());
			positionVO.setCurrentQty(stockHolding.getPositionQty());
			positionVO.setEnableQty(new BigDecimal(stockHolding.getClosablePosition())); // 可用持股数量
			// TODO 要根据行情权限展示价格 从行情获取
			positionVO.setLastPrice(stockHolding.getLatestPrice());
			// positionVO.setClosePrice(); // 收盘价
			positionVO.setCurrency(TigerCurrencyEnum.getZsCurrency(stockHolding.getContract().getCurrency()));
			positionVO.setMarketValue(positionVO.getLastPrice().multiply(positionVO.getCurrentQty())); // 持仓市值
			BigDecimal incomeBalance =
				(positionVO.getLastPrice().subtract(positionVO.getCostPrice())).multiply(positionVO.getCurrentQty());
			positionVO.setIncomeBalance(incomeBalance);
			if (positionVO.getMarketValue().compareTo(BigDecimal.ZERO) > 0) {
				positionVO.setIncomeBalanceRatio(
					incomeBalance.divide(positionVO.getMarketValue(), new MathContext(5, RoundingMode.HALF_UP)));
			}
			/**
			 * 期权
			 */
			if ("OPTION".equals(stockHolding.getContract().getSecType())) {
				positionVO.setStockCode(stockHolding.getContract().getSymbol() + " "
					+ stockHolding.getContract().getExpiry() + " "
					+ stockHolding.getContract().getStrike() + " " + stockHolding.getContract().getRight());
				positionVO.setAssetId(MarketUtils.translateUsAssetId(positionVO.getStockCode()));
				positionVO.setSecType("OPTION");
			}
			positions.add(positionVO);
		}
		return positions;
	}

	@Override
	public R getJournalOrders(JournalOrdersRequest request) {
		List<JournalOrdersVO> ordersVOList;

		OrderQueryReq order = new OrderQueryReq();
		order.setAccountId(request.getTradeAccount());
		Date todayStart = DateUtil.beginOfDay(new Date());
		long todayStartMillis = todayStart.getTime();
		long tomorrowStart = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), 1)).getTime();
		order.setSinceTime(String.valueOf(todayStartMillis));
		/**
		 * 结束时间是当前时间+1天
		 */
		order.setToTime(String.valueOf(tomorrowStart));
		order.setLimit(100L);
		if(StringUtils.isNotBlank(request.getExchangeType())){
			order.setMarket(TigerExchangeTypeEnum.getCounterExchangeType(request.getExchangeType()));
		}


		R rt = tigerCommonService.getOrders(order);

		if (rt.isSuccess()) {
			OrderQueryResp orderQueryResps = (OrderQueryResp) rt.getData();
			ordersVOList = listJournalOrders(orderQueryResps);
		} else {
			return rt;
		}
		return R.data(ordersVOList);

	}

	private List<JournalOrdersVO> listJournalOrders(OrderQueryResp orderQueryResps) {

		List<JournalOrdersVO> journalOrdersVOList = new ArrayList<>();
		List<OrderQueryResp.Orders> orders =orderQueryResps.getOrders();
		for (OrderQueryResp.Orders order : orders) {
			JournalOrdersVO vo = new JournalOrdersVO();
			vo.setSecType("STOCK");
			vo.setOrderNo(order.getId().toString());
			vo.setExchangeType(TigerExchangeTypeEnum.getZsExchangeType(order.getMarket()));
			if (TigerExchangeTypeEnum.HK.getExchangeType().equals(vo.getExchangeType())) {
				vo.setStockCode(order.getSymbol());
				vo.setAssetId(MarketUtils.translateHkAssetId(vo.getStockCode()));
			} else if (AfeExchangeTypeEnum.US.getExchangeType().equals(vo.getExchangeType())) {
				// 美股
				vo.setStockCode(order.getSymbol());
				vo.setAssetId(MarketUtils.translateUsAssetId(vo.getStockCode()));
			} else if (AfeExchangeTypeEnum.ML.getExchangeType().equals(vo.getExchangeType())) {
				vo.setExchangeType(ExchangeTypeEnum.ML.getExchangeType());
				vo.setStockCode(order.getSymbol());
				vo.setAssetId(order.getSymbol() + "." + vo.getExchangeType());
			}
			Long createdAt = order.getCreatedAt();
			LocalDateTime localDateTime = Instant.ofEpochMilli(createdAt)
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
			DateTimeFormatter dateAllFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

			vo.setOrderTime(localDateTime.format(dateAllFormatter));
			StkInfo assetInfo = grmCacheMgr.getAssetInfo(vo.getAssetId());
			vo.setLotSize(assetInfo.getLotSize());
			vo.setSecSType(assetInfo.getSecSType());
			vo.setStockName(assetInfo.getEngName());
			vo.setStockNameZhCN(assetInfo.getStkName());
			vo.setStockNameZhHK(assetInfo.getTraditionalName());
			vo.setBsFlag(TigerEntrustBsEnum.getZsEntrustBs(order.getAction()));
			vo.setPrice(order.getPrice());
			vo.setQty(order.getTotalQty());
			vo.setBusinessPrice(order.getAvgFillPrice());
			vo.setBusinessQty(order.getFilledQty());
			LocalDateTime updatedAt = Instant.ofEpochMilli(order.getUpdatedAt())
				.atZone(ZoneId.systemDefault())
				.toLocalDateTime();
			vo.setBusinessTime(updatedAt.format(dateAllFormatter));
			vo.setBusinessBalance(new BigDecimal("0"));
			vo.setOrderType(TigerEntrustPropEnum.getZsEntrustProp(order.getOrderType()));
			vo.setOrderStatus(TigerEntrustStatusEnum.getZsEntrustStatus(order.getStatus()));
			vo.setEntrustDate(localDateTime.format(dateFormatter));
			vo.setEntrustTime(localDateTime.format(timeFormatter));
			vo.setEntrustProp(TigerEntrustPropEnum.getZsEntrustProp(order.getOrderType()));

			if (order.getModifiable()) {
				vo.setModifiable("1");
			} else {
				vo.setModifiable("0");
			}
			if (order.getCancelable()) {
				vo.setCancelable("1");
			} else {
				vo.setCancelable("0");
			}
			/**
			 * 中华通不可改单
			 */
			if (AfeExchangeTypeEnum.ML.getExchangeType().equals(vo.getExchangeType())) {
				vo.setModifiable("0");
			}

			/**
			 * 期权
			 */
			if ("OPTION".equals(order.getSecType())) {
				vo.setStockCode(order.getSymbol() + " "
					+ order.getExpiry() + " "
					+ order.getStrike() + " " + order.getRight());
				vo.setAssetId(MarketUtils.translateUsAssetId(vo.getStockCode()));
				vo.setSecType("OPTION");
			}

			journalOrdersVOList.add(vo);
		}
		return journalOrdersVOList;
	}

	@Override
	public R getHistoryOrders(HistoryOrdersRequest request) {
		List<JournalOrdersVO> ordersVOList;

		OrderQueryReq order = new OrderQueryReq();
		order.setAccountId(request.getTradeAccount());
		Date todayStart = DateUtil.beginOfDay(new Date());
		order.setSinceTime(String.valueOf(DateUtil.beginOfDay(DateUtil.parse(request.getStartDate())).getTime()));
		/**
		 * 结束时间是当前时间+1天
		 */
		Date endDate = DateUtil.parse(request.getEndDate());
		endDate = DateUtil.offsetDay(endDate, 1);
		order.setToTime(String.valueOf(DateUtil.beginOfDay(endDate).getTime()));
		if(StringUtils.isNotBlank(request.getExchangeType())){
			order.setMarket(TigerExchangeTypeEnum.getCounterExchangeType(request.getExchangeType()));
		}
		order.setLimit(100L);
		R rt = tigerCommonService.getOrders(order);
		if (rt.isSuccess()) {
			OrderQueryResp orderQueryResps = (OrderQueryResp) rt.getData();
			ordersVOList = listJournalOrders(orderQueryResps);
		} else {
			return rt;
		}
		return R.data(ordersVOList);
	}

	@Override
	public R getJournalFundRecord(JournalFundRecordRequest request) {
		List<FundRecordVO> fundRecordVOS =new ArrayList<>();
		return R.data(fundRecordVOS);
	}

	@Override
	public R getHistoryFundRecord(HistoryFundRecordRequest request) {
		List<FundRecordVO> fundRecordVOS =new ArrayList<>();
		return R.data(fundRecordVOS);
	}

	@Override
	public R getJournalStockRecord(JournalStkRecordRequest request) {
		List<StockRecordVO> stockRecordVOList =new ArrayList<>();
		return R.data(stockRecordVOList);
	}

	@Override
	public R getHistoryStockRecord(HistoryStkRecordRequest request) {
		List<StockRecordVO> stockRecordVOList =new ArrayList<>();
		return R.data(stockRecordVOList);
	}

	@Override
	public R getMaxmumBuy(MaxmumBuyRequest request) {
		return null;
	}

	@Override
	public R getMaxmumSell(MaxmumSellRequest request) {
		return null;
	}

	@Override
	public R getCurrencyMaster(CurrencyMasterRequest request) {
		return null;
	}

	@Override
	public R getJournalProfit(UserPortfolioRequest request) {
		return null;
	}

	@Override
	public R getStrategyOrders(String request) {
		return null;
	}

	@Override
	public R getOrderCharge(String request) {
		return null;
	}

	@Override
	public R getCompanyAction(String request) {
		return null;
	}

	@Override
	public R<String> getExtractableMoney(String fundAccount, String moneyType) {
		return null;
	}

	@Override
	public R getRiskLevel(String request) {
		return null;
	}

	@Override
	public R getOrdersFee(HistoryOrdersVO request) {
		return null;
	}

	@Override
	public R getStockRecordDetails(StockRecordDetailsVO request) {
		return null;
	}

	@Override
	public R getHistoryStockRecordDetails(StockRecordDetailsVO request) {
		return null;
	}

	@Override
	public R getStockMarginRatio() {
		String lang = WebUtil.getLanguage();
		if (StringUtil.isBlank(lang)) {
			lang = Constants.ZH_HANS;
		}
//		Map<String, StockMarginRatioVO> map = zeroRedis.get(CacheNames.TRADE_STOCK_MARGIN_RATIO.concat(lang));
		Map<String, StockMarginRatioVO> map =new HashMap<>();

		StockMarginRatioVO stockMarginRatioVO = new StockMarginRatioVO();
		StkInfo stkInfo = zeroRedis.protoGet("00700".concat(".HK"), StkInfo.class);
		stockMarginRatioVO.setMarginRatio(new BigDecimal("0.7"));
		stockMarginRatioVO.setStockName(HSUtil.getStockName(stkInfo, Constants.ZH_HANS, ""));
		stockMarginRatioVO.setAssetId("00700".concat(".HK"));
		stockMarginRatioVO.setExchangeType(AfeExchangeTypeEnum.HK.getExchangeType());
		stockMarginRatioVO.setStockCode("00700");
		map.put(stockMarginRatioVO.getAssetId(), stockMarginRatioVO);
		return  R.data(map.values());
//		if (map != null) {
//			return R.data(map.values());
//		}
//		// 从柜台获取可融资股票列表
//		return R.fail();
	}

	@Override
	public R deposit(FundDepositReq fundDepositReq) {
		return null;
	}

	@Override
	public R withdrawal(FundWithdrawalReq fundWithdrawalReq) {
		return null;
	}

	@Override
	public R freeze(FundFreezeReq fundFreezeReq) {
		return null;
	}

	@Override
	public R unFreeze(FundUnFreezeReq fundUnFreezeReq) {
		return null;
	}

	@Override
	public R unFreezeWithdraw(FundUnFreezeWithdrawReq fundUnFreezeWithdrawReq) {
		return null;
	}

	@Override
	public R<FundQueryVO> queryFundByAccount(String tradeAccount, String capitalAccount) {
		return null;
	}

	@Override
	public R stockMarginRatioSetting(StockMarginSettingReq stockMarginSettingReq) {
		return null;
	}

	@Override
	public R getJournalStrategyOrders(JournalOrdersRequest request) {
		return null;
	}

	@Override
	public R getHistoryStrategyOrders(HistoryOrdersRequest request) {
		return null;
	}
}
