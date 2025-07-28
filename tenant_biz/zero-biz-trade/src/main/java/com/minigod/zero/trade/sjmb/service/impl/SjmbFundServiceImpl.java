package com.minigod.zero.trade.sjmb.service.impl;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_SJMB;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.trade.fund.vo.request.*;
import com.minigod.zero.trade.hs.service.GrmCacheMgr;
import com.minigod.zero.trade.service.IFundService;
import com.minigod.zero.trade.sjmb.common.MessageCode;
import com.minigod.zero.trade.sjmb.common.SjmbFunctionsUrlEnum;
import com.minigod.zero.trade.sjmb.req.brokerapi.*;
import com.minigod.zero.trade.sjmb.resp.SjmbResponse;
import com.minigod.zero.trade.sjmb.resp.brokerapi.*;
import com.minigod.zero.trade.sjmb.service.ICounterService;
import com.minigod.zero.trade.utils.MarketUtils;
import com.minigod.zero.trade.vo.sjmb.*;
import com.minigod.zero.trade.vo.sjmb.req.*;
import com.minigod.zero.trade.vo.sjmb.resp.*;

import cn.hutool.core.date.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @ClassName SjmbFundServiceImpl.java
 * @Description 随机漫步资金
 * @createTime 2024年04月13日 17:23:00
 */

@Slf4j
@Service
@ConditionalOnProperty(value="trade.type",havingValue = MULTI_SERVER_TYPE_SJMB)
@RequiredArgsConstructor
public class SjmbFundServiceImpl implements IFundService {

	private final ZeroRedis zeroRedis;
	private final GrmCacheMgr grmCacheMgr;

	//警示值
	@Value("${trade.bondRatio.warn:'0.8'}")
	private BigDecimal warn;
	//追收值
	@Value("${trade.bondRatio.recovery:'1'}")
	private BigDecimal recovery;
	//平仓值
	@Value("${trade.bondRatio.closePosition:'1.2'}")
	private BigDecimal closePosition;

	@Value("${fund.api.url:''}")
	private String fundApiUrl;


	@Value("${counter.fund.segmentId:''}")
	private String segmentId;

	@Resource
	private ICounterService counterService;

	@Override
	public R getFundInfo(FundInfoRequest request) {
		FundQueryVO fundQueryVO =new FundQueryVO();
		List<AssetInfoVO> fundStats = new ArrayList<>();
		List<PositionVO> hkPosition = new ArrayList<>();
		List<PositionVO> usPosition = new ArrayList<>();
		List<PositionVO> cnPosition = new ArrayList<>();

		AssetReq assetReq = new AssetReq();
		assetReq.setAccountId(request.getTradeAccount());

		// 资金详细
		SjmbResponse response = counterService.brokerApiSend(assetReq, SjmbFunctionsUrlEnum.ASSET_DETAIL_QUERY, HttpMethod.GET);
		// 持仓信息
		PositionReq positionReq = new PositionReq();
		positionReq.setAccountId(request.getTradeAccount());
		positionReq.setSegmentId(segmentId);
		SjmbResponse responsePosit = counterService.brokerApiSend(positionReq, SjmbFunctionsUrlEnum.POSITION_QUERY, HttpMethod.GET);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		} else {
			AssetDetailResp assetDetailResp = JSONUtil.fromJson(response.getData().toString(), AssetDetailResp.class);
			AssetInfoVO hkAssetInfoVO = new AssetInfoVO();
			// 港币
			hkAssetInfoVO.setCurrency("HKD");
			hkAssetInfoVO.setEnableBalance(assetDetailResp.getHkEnableBalance());
			hkAssetInfoVO.setFrozenBalance(assetDetailResp.getHkFrozenBalance());
			hkAssetInfoVO.setMarketValue(assetDetailResp.getHkMarketValue()); // 证券市值 根据持仓计算的
			hkAssetInfoVO.setAsset(assetDetailResp.getHkAsset());
			hkAssetInfoVO.setFetchBalance(assetDetailResp.getHkFetchBalance());
			hkAssetInfoVO.setCashOnHold(assetDetailResp.getHkCashOnHold());
			hkAssetInfoVO.setCurrentBalance(assetDetailResp.getHkCurrentCash());
//          hkAssetInfoVO.setRiskLevel(1); // 风险水平
			hkAssetInfoVO.setTotalBuyMoney(assetDetailResp.getHkUnsettledBalance());
//          hkAssetInfoVO.setMaxExposure(); // 信用额度
//          hkAssetInfoVO.setTodayIncome();  // 今日盈亏
//          hkAssetInfoVO.setTodayIncomeRatio(); //今日盈亏比
//          hkAssetInfoVO.setIncomeBalance(); //持仓盈亏
//          hkAssetInfoVO.setIncomeBalanceRatio(); //持仓盈亏比

			// 美元
			AssetInfoVO usAssetInfoVO = new AssetInfoVO();
			usAssetInfoVO.setCurrency("USD");
			usAssetInfoVO.setEnableBalance(assetDetailResp.getUsEnableBalance());
			usAssetInfoVO.setFrozenBalance(assetDetailResp.getUsFrozenBalance());
			usAssetInfoVO.setMarketValue(assetDetailResp.getUsMarketValue()); // 证券市值 根据持仓计算的
			usAssetInfoVO.setAsset(assetDetailResp.getUsAsset());
			usAssetInfoVO.setFetchBalance(assetDetailResp.getUsFetchBalance());
			usAssetInfoVO.setCashOnHold(assetDetailResp.getUsCashOnHold());
			usAssetInfoVO.setCurrentBalance(assetDetailResp.getUsCurrentCash());
//          usAssetInfoVO.setRiskLevel(1); // 风险水平
			usAssetInfoVO.setTotalBuyMoney(assetDetailResp.getUsUnsettledBalance());
//          usAssetInfoVO.setMaxExposure(); // 信用额度
//          usAssetInfoVO.setTodayIncome();  // 今日盈亏
//          usAssetInfoVO.setTodayIncomeRatio(); //今日盈亏比
//          usAssetInfoVO.setIncomeBalance(); //持仓盈亏
//          usAssetInfoVO.setIncomeBalanceRatio(); //持仓盈亏比
			// 人民币
			AssetInfoVO chAssetInfoVO = new AssetInfoVO();
			chAssetInfoVO.setCurrency("CNY");

			// 持仓
			if (response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {

				PositionResp positionResp = JSONUtil.fromJson(responsePosit.getData().toString(), PositionResp.class);
				List<PositionResp.Position> positions = positionResp.getPositions();
				if (null != positions && positions.size() > 0) {
					for (PositionResp.Position positionInfo : positions) {

						PositionVO positionVO =new PositionVO();
						positionVO.setExchangeType(ExchangeTypeEnum.getZsExchangeType(positionInfo.getExchange()));
						if(ExchangeTypeEnum.HK.getExchangeType().equals(positionVO.getExchangeType())){
							positionVO.setStockCode(MarketUtils.getSymbol(positionInfo.getSymbol()).replaceAll("^(0+)", ""));
							positionVO.setAssetId(MarketUtils.translateHkAssetId(positionVO.getStockCode()));
						}else if(ExchangeTypeEnum.NYSE.getExchangeType().equals(positionVO.getExchangeType())){
							// 美股
							positionVO.setStockCode(positionInfo.getSymbol());
							positionVO.setAssetId(MarketUtils.translateUsAssetId(positionVO.getStockCode()));
						}else if (ExchangeTypeEnum.SH.getExchangeType().equals(positionVO.getExchangeType()) ||
							ExchangeTypeEnum.SZ.getExchangeType().equals(positionVO.getExchangeType())){
							positionVO.setExchangeType(ExchangeTypeEnum.ML.getExchangeType());
							positionVO.setStockCode(positionInfo.getSymbol());
							positionVO.setAssetId(positionInfo.getSymbol()+"."+positionVO.getExchangeType());
						}
						StkInfo assetInfo = grmCacheMgr.getAssetInfo(positionVO.getAssetId());
						positionVO.setLotSize(assetInfo.getLotSize());
						positionVO.setSecSType(assetInfo.getSecSType());
						positionVO.setStockName(assetInfo.getEngName());
						positionVO.setStockNameZhCN(assetInfo.getStkName());
						positionVO.setStockNameZhHK(assetInfo.getTraditionalName());
						positionVO.setAvBuyPrice(new BigDecimal(positionInfo.getAveragePrice().getValue()+positionInfo.getAveragePrice().getOffset()));
						positionVO.setCostPrice(new BigDecimal(positionInfo.getAverageCost().getValue()+positionInfo.getAverageCost().getOffset()));
						positionVO.setCurrentQty(new BigDecimal(positionInfo.getQuantity().getValue()+positionInfo.getQuantity().getOffset()));
						positionVO.setEnableQty(new BigDecimal(positionInfo.getQuantity().getValue()+positionInfo.getQuantity().getOffset())); //可用持股数量
						positionVO.setLastPrice(new BigDecimal(positionInfo.getLatestPrice().getValue()+positionInfo.getLatestPrice().getOffset()));
//                        positionVO.setClosePrice(); // 收盘价
						positionVO.setCurrency(positionInfo.getCurrency());
						positionVO.setMarketValue(positionVO.getLastPrice().multiply(positionVO.getCurrentQty())); // 持仓市值
						BigDecimal incomeBalance =(positionVO.getLastPrice().subtract(positionVO.getCostPrice())).multiply(positionVO.getCurrentQty());
						positionVO.setIncomeBalance(incomeBalance);
						if (positionVO.getMarketValue().compareTo(BigDecimal.ZERO) > 0) {
							positionVO.setIncomeBalanceRatio(incomeBalance.divide(positionVO.getMarketValue(),new MathContext(5, RoundingMode.HALF_UP)));
						}

						if(ExchangeTypeEnum.HK.getExchangeType().equals(positionVO.getExchangeType())){
							hkPosition.add(positionVO);
						}else if(ExchangeTypeEnum.NYSE.getExchangeType().equals(positionVO.getExchangeType())){
							// 美股
							usPosition.add(positionVO);
						}else if (ExchangeTypeEnum.ML.getExchangeType().equals(positionVO.getExchangeType())
						){
							cnPosition.add(positionVO);
						}

					}
				}
			}
			fundStats.add(hkAssetInfoVO);
			fundStats.add(usAssetInfoVO);
			fundStats.add(chAssetInfoVO);

			fundQueryVO.setFundStats(fundStats);
			fundQueryVO.setHkPosition(hkPosition);
			fundQueryVO.setUsPosition(usPosition);
			fundQueryVO.setCnPosition(cnPosition);
		}
		return R.data(fundQueryVO);
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
		return null;
	}

	@Override
	public R getJournalOrders(JournalOrdersRequest request) {
		List<JournalOrdersVO> ordersVOList =new ArrayList<>();

		OrderTodayReq orderTodayReq =new OrderTodayReq();
		orderTodayReq.setAccountId(request.getTradeAccount());
		orderTodayReq.setPage(1);
		orderTodayReq.setLimit(9999);

		SjmbResponse response = counterService.brokerApiSend(orderTodayReq, SjmbFunctionsUrlEnum.ORDER_TODAY, HttpMethod.GET);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		} else {
			OrderTodayResp orderTodayResp = JSONUtil.fromJson(response.getData().toString(), OrderTodayResp.class);
			List<OrderTodayResp.Order> orders = orderTodayResp.getItems();
			if (null != orders && orders.size() > 0) {
				for (OrderTodayResp.Order order : orders) {
					JournalOrdersVO vo =new JournalOrdersVO();
					vo.setOrderNo(order.getOrderId());
					vo.setExchangeType(ExchangeTypeEnum.getZsExchangeType(order.getExchange()));

					if(ExchangeTypeEnum.HK.getExchangeType().equals(vo.getExchangeType())){
						vo.setStockCode(MarketUtils.getSymbol(order.getSymbol()).replaceAll("^(0+)", ""));
						vo.setAssetId(MarketUtils.translateHkAssetId(vo.getStockCode()));
					}else if(ExchangeTypeEnum.NYSE.getExchangeType().equals(vo.getExchangeType())){
						// 美股
						vo.setStockCode(order.getSymbol());
						vo.setAssetId(MarketUtils.translateUsAssetId(vo.getStockCode()));
					}else if (ExchangeTypeEnum.SH.getExchangeType().equals(vo.getExchangeType()) ||
						ExchangeTypeEnum.SZ.getExchangeType().equals(vo.getExchangeType())){
						vo.setStockCode(order.getSymbol());
						vo.setAssetId(order.getSymbol()+"."+vo.getExchangeType());
					}
					vo.setOrderTime(DateUtil.format(new Date(order.getPlaceTime()), "yyyy-MM-dd HH:mm:ss"));
					StkInfo assetInfo = grmCacheMgr.getAssetInfo(vo.getAssetId());
					vo.setLotSize(assetInfo.getLotSize());
					vo.setSecSType(assetInfo.getSecSType());
					vo.setStockName(assetInfo.getEngName());
					vo.setStockNameZhCN(assetInfo.getStkName());
					vo.setStockNameZhHK(assetInfo.getTraditionalName());
					vo.setBsFlag(EntrustBsEnum.getZsEntrustBs(order.getSide()));
					if(null != order.getPrice()){
						vo.setPrice(new BigDecimal(order.getPrice().getValue()+order.getPrice().getOffset()));
					}else{
						vo.setPrice(new BigDecimal(order.getAveragePrice().getValue()+order.getAveragePrice().getOffset()));
					}
					vo.setQty(new BigDecimal(order.getQuantity().getValue()+order.getQuantity().getOffset()));
					vo.setBusinessPrice(new BigDecimal(order.getAveragePrice().getValue()+order.getAveragePrice().getOffset()));
					vo.setBusinessQty(new BigDecimal(order.getCumulativeQuantity().getValue()+order.getCumulativeQuantity().getOffset()));
					vo.setBusinessTime(DateUtil.format(new Date(order.getLastUpdateTime()), "yyyy-MM-dd HH:mm:ss"));
					vo.setBusinessBalance(new BigDecimal("0"));
					vo.setOrderType(EntrustPropEnum.getZsEntrustProp(order.getOrderType()));
					vo.setOrderStatus(EntrustStatusEnum.getZsEntrustStatus(order.getStatus()));
					vo.setEntrustDate(DateUtil.format(new Date(order.getPlaceTime()), "yyyy-MM-dd HH:mm:ss"));
					vo.setEntrustTime(DateUtil.format(new Date(order.getPlaceTime()), "yyyy-MM-dd HH:mm:ss"));
					vo.setEntrustProp(EntrustPropEnum.getCounterEntrustProp(order.getOrderType()));

					if ("0".equals(vo.getOrderStatus()) || "1".equals(vo.getOrderStatus()) ||
						"2".equals(vo.getOrderStatus()) || "3".equals(vo.getOrderStatus()) ||
						"5".equals(vo.getOrderStatus()) || "7".equals(vo.getOrderStatus())) {
						vo.setCancelable("1");
						vo.setModifiable("1");
					} else {
						vo.setCancelable("0");
						vo.setModifiable("0");
					}
					/**
					 * 中华通不可改单
					 */
					if (ExchangeTypeEnum.SH.getExchangeType().equals(vo.getExchangeType()) ||
						ExchangeTypeEnum.SZ.getExchangeType().equals(vo.getExchangeType())) {
						vo.setModifiable("0");
					}
					ordersVOList.add(vo);
				}
			}
		}
		return R.data(ordersVOList);
	}

	@Override
	public R getHistoryOrders(HistoryOrdersRequest request) {
		List<HistoryOrderVO> historyOrdersVOList =new ArrayList<>();
		OrderHistoryReq orderHistoryReq =new OrderHistoryReq();
		orderHistoryReq.setAccountId(request.getTradeAccount());
		orderHistoryReq.setPage(1);
		orderHistoryReq.setLimit(9999);
		Date startDate = DateUtil.parse(request.getStartDate(), "yyyy-MM-dd");
		orderHistoryReq.setBegin(startDate.getTime());
		Date endDate = DateUtil.parse(request.getEndDate(), "yyyy-MM-dd");
		orderHistoryReq.setEnd(endDate.getTime());
		SjmbResponse response = counterService.brokerApiSend(orderHistoryReq, SjmbFunctionsUrlEnum.ORDER_HISTORY, HttpMethod.GET);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		} else {
			OrderTodayResp orderTodayResp = JSONUtil.fromJson(response.getData().toString(), OrderTodayResp.class);
			List<OrderTodayResp.Order> orders = orderTodayResp.getItems();
			if (null != orders && orders.size() > 0) {
				for (OrderTodayResp.Order order : orders) {
					HistoryOrderVO vo =new HistoryOrderVO();
					vo.setOrderNo(order.getOrderId());
					vo.setExchangeType(ExchangeTypeEnum.getZsExchangeType(order.getExchange()));

					if(ExchangeTypeEnum.HK.getExchangeType().equals(vo.getExchangeType())){
						vo.setStockCode(MarketUtils.getSymbol(order.getSymbol()).replaceAll("^(0+)", ""));
						vo.setAssetId(MarketUtils.translateHkAssetId(vo.getStockCode()));
					}else if(ExchangeTypeEnum.NYSE.getExchangeType().equals(vo.getExchangeType())){
						// 美股
						vo.setStockCode(order.getSymbol());
						vo.setAssetId(MarketUtils.translateUsAssetId(vo.getStockCode()));
					}else if (ExchangeTypeEnum.SH.getExchangeType().equals(vo.getExchangeType()) ||
						ExchangeTypeEnum.SZ.getExchangeType().equals(vo.getExchangeType())){
						vo.setStockCode(order.getSymbol());
						vo.setAssetId(order.getSymbol()+"."+vo.getExchangeType());
					}
					vo.setOrderTime(DateUtil.format(new Date(order.getPlaceTime()), "yyyy-MM-dd HH:mm:ss"));
					StkInfo assetInfo = grmCacheMgr.getAssetInfo(vo.getAssetId());
					vo.setLotSize(assetInfo.getLotSize());
					vo.setSecSType(assetInfo.getSecSType());
					vo.setStockName(assetInfo.getEngName());
					vo.setStockNameZhCN(assetInfo.getStkName());
					vo.setStockNameZhHK(assetInfo.getTraditionalName());
					vo.setBsFlag(EntrustBsEnum.getZsEntrustBs(order.getSide()));
					if(null != order.getPrice()){
						vo.setPrice(new BigDecimal(order.getPrice().getValue()+order.getPrice().getOffset()));
					}else{
						vo.setPrice(new BigDecimal(order.getAveragePrice().getValue()+order.getAveragePrice().getOffset()));
					}
					vo.setQty(new BigDecimal(order.getQuantity().getValue()+order.getQuantity().getOffset()));
					vo.setBusinessPrice(new BigDecimal(order.getAveragePrice().getValue()+order.getAveragePrice().getOffset()));
					vo.setBusinessQty(new BigDecimal(order.getCumulativeQuantity().getValue()+order.getCumulativeQuantity().getOffset()));
					vo.setBusinessTime(DateUtil.format(new Date(order.getLastUpdateTime()), "yyyy-MM-dd HH:mm:ss"));
					vo.setBusinessBalance(new BigDecimal("0"));
					vo.setOrderType(EntrustPropEnum.getZsEntrustProp(order.getOrderType()));
					vo.setOrderStatus(EntrustStatusEnum.getZsEntrustStatus(order.getStatus()));
					vo.setEntrustDate(DateUtil.format(new Date(order.getPlaceTime()), "yyyy-MM-dd HH:mm:ss"));
					vo.setEntrustTime(DateUtil.format(new Date(order.getPlaceTime()), "yyyy-MM-dd HH:mm:ss"));
					vo.setEntrustProp(EntrustPropEnum.getCounterEntrustProp(order.getOrderType()));

					historyOrdersVOList.add(vo);
				}
			}
		}
		return R.data(historyOrdersVOList);
	}

	@Override
	public R getJournalFundRecord(JournalFundRecordRequest request) {
		List<FundRecordVO> result =new ArrayList<>();
		CashReq cashReq =new CashReq();
		cashReq.setAccountId(request.getTradeAccount());
		if(StringUtil.isNotBlank(request.getCurrency())){
			cashReq.setCurrency(CurrencyEnum.getCounterCurrency(request.getCurrency()));
		}
		cashReq.setBeginDate(DateUtil.format(new Date(), "yyyy/MM/dd"));
		cashReq.setEndDate(DateUtil.format(DateUtil.offsetDay(DateUtil.date(), 1), "yyyy/MM/dd"));
		cashReq.setPage(1);
		cashReq.setLimit(9999);

		SjmbResponse response = counterService.brokerApiSend(cashReq, SjmbFunctionsUrlEnum.CASH_FLOW, HttpMethod.GET);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		} else {
			CashFlowResp cashFlowResp = JSONUtil.fromJson(response.getData().toString(), CashFlowResp.class);

			List<CashFlowResp.CashFlow> cashFlows =cashFlowResp.getItems();
			if (null != cashFlows && cashFlows.size() > 0) {
				for (CashFlowResp.CashFlow cashFlow : cashFlows) {

					FundRecordVO vo =new FundRecordVO();
					vo.setCurrency(cashFlow.getCurrency());
					Date businessDate = DateUtil.parse(cashFlow.getBusinessDate(), "yyyy/MM/dd");
					vo.setBusinessDate(DateUtil.format(businessDate, "yyyy-MM-dd"));
					vo.setBusinessFlag(cashFlow.getTransSubType());
					vo.setBusinessName(cashFlow.getTransSubType());
					vo.setBusinessBalance(cashFlow.getAmount());
					vo.setOrderNo(cashFlow.getCashFlowId());
					result.add(vo);
				}
			}
		}
		return R.data(result);
	}

	@Override
	public R getHistoryFundRecord(HistoryFundRecordRequest request) {
		List<FundRecordVO> result =new ArrayList<>();
		CashReq cashReq =new CashReq();
		cashReq.setAccountId(request.getTradeAccount());
		if(StringUtil.isNotBlank(request.getCurrency())){
			cashReq.setCurrency(CurrencyEnum.getCounterCurrency(request.getCurrency()));
		}
		if(StringUtil.isNotBlank(request.getStartDate())){
			Date startDate = DateUtil.parse(request.getStartDate(), "yyyy-MM-dd");
			cashReq.setBeginDate(DateUtil.format(startDate, "yyyy/MM/dd"));
		}
		if(StringUtil.isNotBlank(request.getEndDate())){
			Date endDate = DateUtil.parse(request.getEndDate(), "yyyy-MM-dd");
			cashReq.setEndDate(DateUtil.format(endDate, "yyyy/MM/dd"));
		}

		cashReq.setPage(1);
		cashReq.setLimit(9999);

		SjmbResponse response = counterService.brokerApiSend(cashReq, SjmbFunctionsUrlEnum.CASH_FLOW, HttpMethod.GET);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		} else {
			CashFlowResp cashFlowResp = JSONUtil.fromJson(response.getData().toString(), CashFlowResp.class);

			List<CashFlowResp.CashFlow> cashFlows =cashFlowResp.getItems();
			if (null != cashFlows && cashFlows.size() > 0) {
				for (CashFlowResp.CashFlow cashFlow : cashFlows) {

					FundRecordVO vo =new FundRecordVO();
					vo.setCurrency(cashFlow.getCurrency());
					Date businessDate = DateUtil.parse(cashFlow.getBusinessDate(), "yyyy/MM/dd");
					vo.setBusinessDate(DateUtil.format(businessDate, "yyyy-MM-dd"));
					vo.setBusinessFlag(cashFlow.getTransSubType());
					vo.setBusinessName(cashFlow.getTransSubType());
					vo.setBusinessBalance(cashFlow.getAmount());
					vo.setOrderNo(cashFlow.getCashFlowId());
					result.add(vo);
				}
			}
		}
		return R.data(result);
	}

	@Override
	public R getJournalStockRecord(JournalStkRecordRequest request) {
		List<StockRecordVO> result =new ArrayList<>();

		PositionFlowReq positionFlowReq =new PositionFlowReq();
		positionFlowReq.setAccountId(request.getTradeAccount());
		if(StringUtil.isNotBlank(request.getExchangeType())){
			positionFlowReq.setExchange(ExchangeTypeEnum.getCounterExchangeType(request.getExchangeType()));
		}
		positionFlowReq.setBeginDate(DateUtil.format(new Date(), "yyyy/MM/dd"));
		positionFlowReq.setEndDate(DateUtil.format(DateUtil.offsetDay(DateUtil.date(), 1), "yyyy/MM/dd"));
		positionFlowReq.setPage(1);
		positionFlowReq.setLimit(9999);

		SjmbResponse response = counterService.brokerApiSend(positionFlowReq, SjmbFunctionsUrlEnum.STOCK_FLOW, HttpMethod.GET);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		} else {
			PositionFlowResp positionFlowResp = JSONUtil.fromJson(response.getData().toString(), PositionFlowResp.class);

			List<PositionFlowResp.PositionFlow> positionFlowList =positionFlowResp.getItems();
			if (null != positionFlowList && positionFlowList.size() > 0) {
				for (PositionFlowResp.PositionFlow positionFlow : positionFlowList) {

					StockRecordVO vo =new StockRecordVO();
					vo.setExchangeType(ExchangeTypeEnum.getZsExchangeType(positionFlow.getExchange()));

					if(ExchangeTypeEnum.HK.getExchangeType().equals(vo.getExchangeType())){
						vo.setStockCode(MarketUtils.getSymbol(positionFlow.getSymbol()).replaceAll("^(0+)", ""));
						vo.setAssetId(MarketUtils.translateHkAssetId(vo.getStockCode()));
					}else if(ExchangeTypeEnum.NYSE.getExchangeType().equals(vo.getExchangeType())){
						// 美股
						vo.setStockCode(positionFlow.getSymbol());
						vo.setAssetId(MarketUtils.translateUsAssetId(vo.getStockCode()));
					}else if (ExchangeTypeEnum.SH.getExchangeType().equals(vo.getExchangeType()) ||
						ExchangeTypeEnum.SZ.getExchangeType().equals(vo.getExchangeType())){
						vo.setStockCode(positionFlow.getSymbol());
						vo.setAssetId(positionFlow.getSymbol()+"."+vo.getExchangeType());

					}
					vo.setBusinessDate(positionFlow.getBusinessDate());
					vo.setBusinessFlag(positionFlow.getTransSubType());
					vo.setBusinessName(positionFlow.getTransSubType());
					vo.setCurrency("");
					vo.setBusinessBalance(positionFlow.getPrice().multiply(positionFlow.getQuantity()));
					vo.setBusinessAmount(positionFlow.getQuantity());
					vo.setOrderNo(positionFlow.getPositionFlowId());
					StkInfo assetInfo = grmCacheMgr.getAssetInfo(vo.getAssetId());
					vo.setStockName(assetInfo.getEngName());
					vo.setStockNameZhCN(assetInfo.getStkName());
					vo.setStockNameZhHK(assetInfo.getTraditionalName());
					result.add(vo);
				}
			}
		}
		return R.data(result);
	}

	@Override
	public R getHistoryStockRecord(HistoryStkRecordRequest request) {
		List<StockRecordVO> result =new ArrayList<>();

		PositionFlowReq positionFlowReq =new PositionFlowReq();
		positionFlowReq.setAccountId(request.getTradeAccount());
		if(StringUtil.isNotBlank(request.getExchangeType())){
			positionFlowReq.setExchange(ExchangeTypeEnum.getCounterExchangeType(request.getExchangeType()));
		}

		if(StringUtil.isNotBlank(request.getStartDate())){
			Date startDate = DateUtil.parse(request.getStartDate(), "yyyy-MM-dd");
			positionFlowReq.setBeginDate(DateUtil.format(startDate, "yyyy/MM/dd"));
		}
		if(StringUtil.isNotBlank(request.getEndDate())){
			Date endDate = DateUtil.parse(request.getEndDate(), "yyyy-MM-dd");
			positionFlowReq.setEndDate(DateUtil.format(endDate, "yyyy/MM/dd"));
		}
		positionFlowReq.setPage(1);
		positionFlowReq.setLimit(9999);

		SjmbResponse response = counterService.brokerApiSend(positionFlowReq, SjmbFunctionsUrlEnum.STOCK_FLOW, HttpMethod.GET);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		} else {
			PositionFlowResp positionFlowResp = JSONUtil.fromJson(response.getData().toString(), PositionFlowResp.class);

			List<PositionFlowResp.PositionFlow> positionFlowList =positionFlowResp.getItems();
			if (null != positionFlowList && positionFlowList.size() > 0) {
				for (PositionFlowResp.PositionFlow positionFlow : positionFlowList) {

					StockRecordVO vo =new StockRecordVO();
					vo.setExchangeType(ExchangeTypeEnum.getZsExchangeType(positionFlow.getExchange()));

					if(ExchangeTypeEnum.HK.getExchangeType().equals(vo.getExchangeType())){
						vo.setStockCode(MarketUtils.getSymbol(positionFlow.getSymbol()).replaceAll("^(0+)", ""));
						vo.setAssetId(MarketUtils.translateHkAssetId(vo.getStockCode()));
					}else if(ExchangeTypeEnum.NYSE.getExchangeType().equals(vo.getExchangeType())){
						// 美股
						vo.setStockCode(positionFlow.getSymbol());
						vo.setAssetId(MarketUtils.translateUsAssetId(vo.getStockCode()));
					}else if (ExchangeTypeEnum.SH.getExchangeType().equals(vo.getExchangeType()) ||
						ExchangeTypeEnum.SZ.getExchangeType().equals(vo.getExchangeType())){
						vo.setStockCode(positionFlow.getSymbol());
						vo.setAssetId(positionFlow.getSymbol()+"."+vo.getExchangeType());

					}

					Date businessDate = DateUtil.parse(positionFlow.getBusinessDate(), "yyyy/MM/dd");
					vo.setBusinessDate(DateUtil.format(businessDate, "yyyy-MM-dd"));
					vo.setBusinessFlag(positionFlow.getTransSubType());
					vo.setBusinessName(positionFlow.getTransSubType());
					vo.setCurrency("");
					vo.setBusinessBalance(positionFlow.getPrice().multiply(positionFlow.getQuantity()));
					vo.setBusinessAmount(positionFlow.getQuantity());
					vo.setOrderNo(positionFlow.getPositionFlowId());
					StkInfo assetInfo = grmCacheMgr.getAssetInfo(vo.getAssetId());
					vo.setStockName(assetInfo.getEngName());
					vo.setStockNameZhCN(assetInfo.getStkName());
					vo.setStockNameZhHK(assetInfo.getTraditionalName());
					result.add(vo);
				}
			}
		}
		return R.data(result);
	}

	@Override
	public R getMaxmumBuy(MaxmumBuyRequest request) {
		MaxmumBuySellVO result =new MaxmumBuySellVO();
		MaxmumBuyReq maxmumBuyReq =new MaxmumBuyReq();
		maxmumBuyReq.setAccountId(request.getTradeAccount());
		maxmumBuyReq.setSecurityType("EQTY"); //股票
		maxmumBuyReq.setExchange(ExchangeTypeEnum.getCounterExchangeType(request.getExchangeType()));
		if(ExchangeTypeEnum.HK.getExchangeType().equals(request.getExchangeType())){
			maxmumBuyReq.setSymbol(org.apache.commons.lang.StringUtils.leftPad(request.getStockCode(), 5, "0"));
		}else{
			maxmumBuyReq.setSymbol(request.getStockCode());
		}

		SjmbResponse response = counterService.brokerApiSend(maxmumBuyReq, SjmbFunctionsUrlEnum.POSITION_OPEN_LIMIT, HttpMethod.GET);

		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		} else {
			MaxmumBuyResp maxmumBuyResp = JSONUtil.fromJson(response.getData().toString(), MaxmumBuyResp.class);

			if(null != maxmumBuyResp.getMaxLong()){
				result.setEnableAmount(new BigDecimal(maxmumBuyResp.getMaxLong()));
			}
			if(null != maxmumBuyResp.getMaxByCash()){
				result.setCashAmount(new BigDecimal(maxmumBuyResp.getMaxByCash()));
			}
			if(null != maxmumBuyResp.getPositionForClose()){
				result.setEnableSellAmount(new BigDecimal(maxmumBuyResp.getPositionForClose()));
			}


		}
		return R.data(result);
	}

	@Override
	public R getMaxmumSell(MaxmumSellRequest request) {
		MaxmumBuySellVO result =new MaxmumBuySellVO();
		MaxmumBuyReq maxmumBuyReq =new MaxmumBuyReq();
		maxmumBuyReq.setAccountId(request.getTradeAccount());
		maxmumBuyReq.setSecurityType("EQTY"); //股票
		maxmumBuyReq.setExchange(ExchangeTypeEnum.getCounterExchangeType(request.getExchangeType()));
		if(ExchangeTypeEnum.HK.getExchangeType().equals(request.getExchangeType())){
			maxmumBuyReq.setSymbol(org.apache.commons.lang.StringUtils.leftPad(request.getStockCode(), 5, "0"));
		}else{
			maxmumBuyReq.setSymbol(request.getStockCode());
		}

		SjmbResponse response = counterService.brokerApiSend(maxmumBuyReq, SjmbFunctionsUrlEnum.POSITION_OPEN_LIMIT, HttpMethod.GET);

		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		} else {
			MaxmumBuyResp maxmumBuyResp = JSONUtil.fromJson(response.getData().toString(), MaxmumBuyResp.class);

			result.setEnableAmount(new BigDecimal(maxmumBuyResp.getMaxLong()));
			result.setCashAmount(new BigDecimal(maxmumBuyResp.getMaxByCash()));
			result.setEnableSellAmount(new BigDecimal(maxmumBuyResp.getPositionForClose()));
		}
		return R.data(result);
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
		List<StockRecordVO> result =new ArrayList<>();

		PositionFlowReq positionFlowReq =new PositionFlowReq();
		positionFlowReq.setAccountId(request.getTradeAccount());
		if(StringUtil.isNotBlank(request.getExchangeType())){
			positionFlowReq.setExchange(ExchangeTypeEnum.getCounterExchangeType(request.getExchangeType()));
		}
		positionFlowReq.setBeginDate(DateUtil.format(new Date(), "yyyy/MM/dd"));
		positionFlowReq.setEndDate(DateUtil.format(new Date(), "yyyy/MM/dd"));
		positionFlowReq.setPage(1);
		positionFlowReq.setLimit(9999);

		SjmbResponse response = counterService.brokerApiSend(positionFlowReq, SjmbFunctionsUrlEnum.STOCK_FLOW, HttpMethod.GET);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		} else {
			PositionFlowResp positionFlowResp = JSONUtil.fromJson(response.getData().toString(), PositionFlowResp.class);

			List<PositionFlowResp.PositionFlow> positionFlowList =positionFlowResp.getItems();
			if (null != positionFlowList && positionFlowList.size() > 0) {
				for (PositionFlowResp.PositionFlow positionFlow : positionFlowList) {

					StockRecordVO vo =new StockRecordVO();
					vo.setExchangeType(ExchangeTypeEnum.getZsExchangeType(positionFlow.getExchange()));

					if(ExchangeTypeEnum.HK.getExchangeType().equals(vo.getExchangeType())){
						vo.setStockCode(MarketUtils.getSymbol(positionFlow.getSymbol()).replaceAll("^(0+)", ""));
						vo.setAssetId(MarketUtils.translateHkAssetId(vo.getStockCode()));
					}else if(ExchangeTypeEnum.NYSE.getExchangeType().equals(vo.getExchangeType())){
						// 美股
						vo.setStockCode(positionFlow.getSymbol());
						vo.setAssetId(MarketUtils.translateUsAssetId(vo.getStockCode()));
					}else if (ExchangeTypeEnum.SH.getExchangeType().equals(vo.getExchangeType()) ||
						ExchangeTypeEnum.SZ.getExchangeType().equals(vo.getExchangeType())){
						vo.setStockCode(positionFlow.getSymbol());
						vo.setAssetId(positionFlow.getSymbol()+"."+vo.getExchangeType());

					}
					Date businessDate = DateUtil.parse(positionFlow.getBusinessDate(), "yyyy/MM/dd");
					vo.setBusinessDate(DateUtil.format(businessDate, "yyyy-MM-dd"));
					vo.setBusinessFlag(positionFlow.getTransSubType());
					vo.setBusinessName(positionFlow.getTransSubType());
					vo.setCurrency("");
					vo.setBusinessBalance(positionFlow.getPrice().multiply(positionFlow.getQuantity()));
					vo.setBusinessAmount(positionFlow.getQuantity());
					vo.setOrderNo(positionFlow.getPositionFlowId());
					StkInfo assetInfo = grmCacheMgr.getAssetInfo(vo.getAssetId());
					vo.setStockName(assetInfo.getEngName());
					vo.setStockNameZhCN(assetInfo.getStkName());
					vo.setStockNameZhHK(assetInfo.getTraditionalName());
					result.add(vo);
				}
			}
		}
		return R.data(result);
	}

	@Override
	public R getHistoryStockRecordDetails(StockRecordDetailsVO request) {
		List<StockRecordVO> result =new ArrayList<>();

		PositionFlowReq positionFlowReq =new PositionFlowReq();
		positionFlowReq.setAccountId(request.getTradeAccount());
		if(StringUtil.isNotBlank(request.getExchangeType())){
			positionFlowReq.setExchange(ExchangeTypeEnum.getCounterExchangeType(request.getExchangeType()));
		}
		if(StringUtil.isNotBlank(request.getStartDate())){
			Date startDate = DateUtil.parse(request.getStartDate(), "yyyy-MM-dd");
			positionFlowReq.setBeginDate(DateUtil.format(startDate, "yyyy/MM/dd"));
		}
		if(StringUtil.isNotBlank(request.getEndDate())){
			Date endDate = DateUtil.parse(request.getEndDate(), "yyyy-MM-dd");
			positionFlowReq.setEndDate(DateUtil.format(endDate, "yyyy/MM/dd"));
		}

		if(ExchangeTypeEnum.HK.getExchangeType().equals(request.getExchangeType())){
			positionFlowReq.setSymbol(org.apache.commons.lang.StringUtils.leftPad(request.getStockCode(), 5, "0"));
		}else{
			positionFlowReq.setSymbol(request.getStockCode());
		}

		positionFlowReq.setPage(1);
		positionFlowReq.setLimit(9999);

		SjmbResponse response = counterService.brokerApiSend(positionFlowReq, SjmbFunctionsUrlEnum.STOCK_FLOW, HttpMethod.GET);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		} else {
			PositionFlowResp positionFlowResp = JSONUtil.fromJson(response.getData().toString(), PositionFlowResp.class);

			List<PositionFlowResp.PositionFlow> positionFlowList =positionFlowResp.getItems();
			if (null != positionFlowList && positionFlowList.size() > 0) {
				for (PositionFlowResp.PositionFlow positionFlow : positionFlowList) {

					StockRecordVO vo =new StockRecordVO();
					vo.setExchangeType(ExchangeTypeEnum.getZsExchangeType(positionFlow.getExchange()));

					if(ExchangeTypeEnum.HK.getExchangeType().equals(vo.getExchangeType())){
						vo.setStockCode(MarketUtils.getSymbol(positionFlow.getSymbol()).replaceAll("^(0+)", ""));
						vo.setAssetId(MarketUtils.translateHkAssetId(vo.getStockCode()));
					}else if(ExchangeTypeEnum.NYSE.getExchangeType().equals(vo.getExchangeType())){
						// 美股
						vo.setStockCode(positionFlow.getSymbol());
						vo.setAssetId(MarketUtils.translateUsAssetId(vo.getStockCode()));
					}else if (ExchangeTypeEnum.SH.getExchangeType().equals(vo.getExchangeType()) ||
						ExchangeTypeEnum.SZ.getExchangeType().equals(vo.getExchangeType())){
						vo.setStockCode(positionFlow.getSymbol());
						vo.setAssetId(positionFlow.getSymbol()+"."+vo.getExchangeType());

					}

					Date businessDate = DateUtil.parse(positionFlow.getBusinessDate(), "yyyy/MM/dd");
					vo.setBusinessDate(DateUtil.format(businessDate, "yyyy-MM-dd"));
					vo.setBusinessFlag(positionFlow.getTransSubType());
					vo.setBusinessName(positionFlow.getTransSubType());
					vo.setCurrency("");
					vo.setBusinessBalance(positionFlow.getPrice().multiply(positionFlow.getQuantity()));
					vo.setBusinessAmount(positionFlow.getQuantity());
					vo.setOrderNo(positionFlow.getPositionFlowId());
					StkInfo assetInfo = grmCacheMgr.getAssetInfo(vo.getAssetId());
					vo.setStockName(assetInfo.getEngName());
					vo.setStockNameZhCN(assetInfo.getStkName());
					vo.setStockNameZhHK(assetInfo.getTraditionalName());
					result.add(vo);
				}
			}
		}
		return R.data(result);
	}

	@Override
	public R getStockMarginRatio() {
		String lang = WebUtil.getLanguage();
		Map<String, StockMarginRatioVO> map = zeroRedis.get(CacheNames.TRADE_STOCK_MARGIN_RATIO.concat(lang));
		if (map != null) {
			return R.data(map.values());
		}

		/**
		 * 查询柜台 放入redis(过期时间6小时)
		 */
		return R.success();
	}

	@Override
	public R deposit(FundDepositReq fundDepositReq) {
		//		DepositReq depositReq = new DepositReq();
//		depositReq.setAccountId(fundDepositReq.getFundAccount());
//		depositReq.setSegmentId(""); //资金组
//		depositReq.setCurrency(CurrencyEnum.getCounterCurrency(fundDepositReq.getCurrency()));
//		depositReq.setAmount(fundDepositReq.getAmount());
//		depositReq.setRemark(fundDepositReq.getRemark());
//
//		SjmbResponse response = counterService.brokerApiSend(depositReq, SjmbFunctionsUrlEnum.DEPOSIT, HttpMethod.POST);
//
//		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
//			return R.fail(response.getMsg());
//		}
		return R.success();
	}

	@Override
	public R withdrawal(FundWithdrawalReq fundWithdrawalReq) {
		//		WithdrawalReq withdrawalReq = new WithdrawalReq();
//		withdrawalReq.setAccountId(fundWithdrawalReq.getFundAccount());
//		withdrawalReq.setSegmentId(""); //资金组
//		withdrawalReq.setCurrency(CurrencyEnum.getCounterCurrency(fundWithdrawalReq.getCurrency()));
//		withdrawalReq.setAmount(Long.valueOf(fundWithdrawalReq.getAmount()));
//		withdrawalReq.setRemark(fundWithdrawalReq.getRemark());
//		SjmbResponse response = counterService.brokerApiSend(withdrawalReq, SjmbFunctionsUrlEnum.WITHDRAWAL, HttpMethod.POST);
//		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
//			return R.fail(response.getMsg());
//		}
		return R.success();
	}

	@Override
	public R freeze(FundFreezeReq fundFreezeReq) {
		//		FreezeReq freezeReq =new FreezeReq();
//		freezeReq.setAccountId(fundFreezeReq.getFundAccount());
//		freezeReq.setSegmentId("");
//		freezeReq.setLockedAmount(Double.valueOf(fundFreezeReq.getAmount()));
//		freezeReq.setCurrency(CurrencyEnum.getCounterCurrency(fundFreezeReq.getCurrency()));
//		freezeReq.setReason(fundFreezeReq.getRemark());
//		freezeReq.setBusinessDate(fundFreezeReq.getBusinessDate());
//		freezeReq.setTransSubType("WITHDRAWAL");
//		SjmbResponse response = counterService.brokerApiSend(freezeReq, SjmbFunctionsUrlEnum.FREEZE, HttpMethod.POST);
//		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
//			return R.fail(response.getMsg());
//		}
		FundFreezeVO vo =new FundFreezeVO();
		String orderNumber = Long.toString(new Random().nextLong() & Long.MAX_VALUE).substring(0, 10);
		vo.setBusinessNumber(orderNumber);
		return R.data(vo);

	}

	@Override
	public R unFreeze(FundUnFreezeReq fundUnFreezeReq) {
		return R.success();
	}

	@Override
	public R unFreezeWithdraw(FundUnFreezeWithdrawReq fundUnFreezeWithdrawReq) {
		return R.success();
	}

	@Override
	public R<FundQueryVO> queryFundByAccount(String tradeAccount, String capitalAccount) {
		FundQueryVO fundQueryVO =new FundQueryVO();
		List<AssetInfoVO> fundStats = new ArrayList<>();


		AssetReq assetReq = new AssetReq();
		assetReq.setAccountId(tradeAccount);

		// 资金详细
		SjmbResponse response = counterService.brokerApiSend(assetReq, SjmbFunctionsUrlEnum.ASSET_DETAIL_QUERY, HttpMethod.GET);
		if (!response.getCode().endsWith(MessageCode.SUCCESS.getCode())) {
			return R.fail(response.getMsg());
		} else {
			AssetDetailResp assetDetailResp = JSONUtil.fromJson(response.getData().toString(), AssetDetailResp.class);
			AssetInfoVO hkAssetInfoVO = new AssetInfoVO();
			// 港币
			hkAssetInfoVO.setCurrency("HKD");
			hkAssetInfoVO.setEnableBalance(assetDetailResp.getHkEnableBalance());
			hkAssetInfoVO.setFrozenBalance(assetDetailResp.getHkFrozenBalance());
			hkAssetInfoVO.setMarketValue(assetDetailResp.getHkMarketValue()); // 证券市值 根据持仓计算的
			hkAssetInfoVO.setAsset(assetDetailResp.getHkAsset());
			hkAssetInfoVO.setFetchBalance(assetDetailResp.getHkFetchBalance());
			hkAssetInfoVO.setCashOnHold(assetDetailResp.getHkCashOnHold());
			hkAssetInfoVO.setCurrentBalance(assetDetailResp.getHkCurrentCash());
//          hkAssetInfoVO.setRiskLevel(1); // 风险水平
			hkAssetInfoVO.setTotalBuyMoney(assetDetailResp.getHkUnsettledBalance());
//          hkAssetInfoVO.setMaxExposure(); // 信用额度
//          hkAssetInfoVO.setTodayIncome();  // 今日盈亏
//          hkAssetInfoVO.setTodayIncomeRatio(); //今日盈亏比
//          hkAssetInfoVO.setIncomeBalance(); //持仓盈亏
//          hkAssetInfoVO.setIncomeBalanceRatio(); //持仓盈亏比

			// 美元
			AssetInfoVO usAssetInfoVO = new AssetInfoVO();
			usAssetInfoVO.setCurrency("USD");
			usAssetInfoVO.setEnableBalance(assetDetailResp.getUsEnableBalance());
			usAssetInfoVO.setFrozenBalance(assetDetailResp.getUsFrozenBalance());
			usAssetInfoVO.setMarketValue(assetDetailResp.getUsMarketValue()); // 证券市值 根据持仓计算的
			usAssetInfoVO.setAsset(assetDetailResp.getUsAsset());
			usAssetInfoVO.setFetchBalance(assetDetailResp.getUsFetchBalance());
			usAssetInfoVO.setCashOnHold(assetDetailResp.getUsCashOnHold());
			usAssetInfoVO.setCurrentBalance(assetDetailResp.getUsCurrentCash());
//          usAssetInfoVO.setRiskLevel(1); // 风险水平
			usAssetInfoVO.setTotalBuyMoney(assetDetailResp.getUsUnsettledBalance());
//          usAssetInfoVO.setMaxExposure(); // 信用额度
//          usAssetInfoVO.setTodayIncome();  // 今日盈亏
//          usAssetInfoVO.setTodayIncomeRatio(); //今日盈亏比
//          usAssetInfoVO.setIncomeBalance(); //持仓盈亏
//          usAssetInfoVO.setIncomeBalanceRatio(); //持仓盈亏比
			// 人民币
			AssetInfoVO chAssetInfoVO = new AssetInfoVO();
			chAssetInfoVO.setCurrency("CNY");
			fundStats.add(hkAssetInfoVO);
			fundStats.add(usAssetInfoVO);
			fundStats.add(chAssetInfoVO);
			fundQueryVO.setFundStats(fundStats);
		}
		return R.data(fundQueryVO);
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
