package com.minigod.zero.trade.afe.service;

import static com.minigod.zero.biz.common.utils.DateUtil.YYYYMMDD;
import static com.minigod.zero.biz.common.utils.DateUtil.YYYY_MM_DD;
import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_AFE;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.minigod.zero.biz.common.utils.DateUtil;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.trade.afe.common.AfeConstants;
import com.minigod.zero.trade.entity.CustStrategyOrderEntity;
import com.minigod.zero.trade.service.ICustStrategyOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.minigod.zero.biz.common.cache.CacheNames;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.core.tool.utils.WebUtil;
import com.minigod.zero.trade.afe.common.AfeInterfaceTypeEnum;
import com.minigod.zero.trade.afe.common.ApiEnum;
import com.minigod.zero.trade.afe.config.WebSocketClientService;
import com.minigod.zero.trade.afe.req.*;
import com.minigod.zero.trade.afe.resp.*;
import com.minigod.zero.trade.afe.resp.back.BackCommonResponse;
import com.minigod.zero.trade.afe.resp.back.StockMarginRatioResponse;
import com.minigod.zero.trade.fund.vo.request.*;
import com.minigod.zero.trade.hs.constants.Constants;
import com.minigod.zero.trade.hs.service.GrmCacheMgr;
import com.minigod.zero.trade.service.IFundService;
import com.minigod.zero.trade.utils.HSUtil;
import com.minigod.zero.trade.utils.MarketUtils;
import com.minigod.zero.trade.vo.afe.*;
import com.minigod.zero.trade.vo.sjmb.CurrencyEnum;
import com.minigod.zero.trade.vo.sjmb.ExchangeTypeEnum;
import com.minigod.zero.trade.vo.sjmb.req.*;
import com.minigod.zero.trade.vo.sjmb.resp.*;

import lombok.extern.slf4j.Slf4j;

/**
 * @author chen
 * @ClassName AfeFundServiceImpl.java
 * @Description TODO
 * @createTime 2024年04月13日 17:42:00
 */
@Slf4j
@Service
@ConditionalOnProperty(value = "trade.type", havingValue = MULTI_SERVER_TYPE_AFE)
public class AfeFundServiceImpl implements IFundService {

    @Autowired
    SendApiService sendApiService;

    @Resource
    protected GrmCacheMgr grmCacheMgr;

    @Autowired
    ZeroRedis zeroRedis;

    @Autowired
    private WebSocketClientService webSocketClientService;

	@Resource
	private ICustStrategyOrderService custStrategyOrderService;

    @Override
    public R<FundQueryVO> getFundInfo(FundInfoRequest request) {
        FundQueryVO fundQueryVO = new FundQueryVO();
        List<AssetInfoVO> fundStats;
        List<PositionVO> hkPosition;
        List<PositionVO> usPosition;
        List<PositionVO> cnPosition;

        /**
         * 查询持仓
         */
        HoldingQueryRequest holdingQueryRequest = new HoldingQueryRequest();
        holdingQueryRequest.setClientId(request.getTradeAccount());
		R holdingQueryResult ;
		if(StringUtils.isNotBlank(request.getChannel()) && AfeConstants.AGENT_CHANNEL.equals(request.getChannel())){
			holdingQueryResult = webSocketClientService.sendAgentSyncMsg(AfeInterfaceTypeEnum.HOLDING_QUERY.getRequestType(),
				holdingQueryRequest, request.getTradeAccount());
		}else{
			holdingQueryResult = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.HOLDING_QUERY.getRequestType(),
				holdingQueryRequest, request.getTradeAccount());
		}

        CommonResponse commonResponse = (CommonResponse)holdingQueryResult.getData();
        if (holdingQueryResult.isSuccess()) {
            /**
             * 资金
             */
            fundStats = fundList(commonResponse);
            fundQueryVO.setFundStats(fundStats);

            List<PositionVO> positions = listPosition(commonResponse);
            hkPosition = positions.stream()
                .filter(position -> AfeExchangeTypeEnum.HK.getExchangeType().equals(position.getExchangeType()))
                .collect(Collectors.toList());
            usPosition = positions.stream()
                .filter(position -> AfeExchangeTypeEnum.US.getExchangeType().equals(position.getExchangeType()))
                .collect(Collectors.toList());
            cnPosition = positions.stream()
                .filter(position -> AfeExchangeTypeEnum.ML.getExchangeType().equals(position.getExchangeType()))
                .collect(Collectors.toList());
            fundQueryVO.setHkPosition(hkPosition);
            fundQueryVO.setUsPosition(usPosition);
            fundQueryVO.setCnPosition(cnPosition);

        } else {
			return holdingQueryResult;
        }

        return R.data(fundQueryVO);
    }

    /**
     * 资金数据的请求
     *
     * @param commonResponse
     * @return
     */
    private List<AssetInfoVO> fundList(CommonResponse commonResponse) {
        List<AssetInfoVO> assetInfoVOList = new ArrayList<>();
        HoldingQueryResponse holdingQueryResponse =
            JSONUtil.fromJson(commonResponse.getResult().toString(), HoldingQueryResponse.class);

        for (HoldingQueryResponse.CashHolding cashHolding : holdingQueryResponse.getCashHoldingList()) {

            AssetInfoVO assetInfoVO = new AssetInfoVO();
            assetInfoVO.setCurrency(AfeCurrencyEnum.getZsCurrency(cashHolding.getCurrencyCode()));
            /**
             * TODO 相关字段取值待定
             */
            assetInfoVO.setEnableBalance(new BigDecimal(cashHolding.getBuyingPower().replace(",", "")));
            // TODO 冻结金额
            assetInfoVO.setFrozenBalance(new BigDecimal(cashHolding.getCashOnHold().replace(",", ""))
                .add(new BigDecimal(cashHolding.getBuySoldConsideration().replace(",", ""))));
            // TODO 市值
            assetInfoVO.setMarketValue(new BigDecimal(cashHolding.getAvailableMktVal().replace(",", ""))); // 证券市值
                                                                                                           // 根据持仓计算的
//            if (StringUtils.isNotEmpty(cashHolding.getAvailableMarginVal())) {
//                assetInfoVO.setAsset(new BigDecimal(cashHolding.getTotalPnl().replace(",", "")));
//            }else{
//				assetInfoVO.setAsset(new BigDecimal(cashHolding.getBuyingPower().replace(",", "")));
//			}
			assetInfoVO.setAsset(new BigDecimal(cashHolding.getBuyingPower().replace(",", "")));

            assetInfoVO.setFetchBalance(new BigDecimal(cashHolding.getAvailableBal().replace(",", "")));
            assetInfoVO.setCashOnHold(new BigDecimal(cashHolding.getBuySoldConsideration().replace(",", "")));
            assetInfoVO.setCurrentBalance(new BigDecimal(cashHolding.getAvailableBal().replace(",", "")));
            // assetInfoVO.setRiskLevel(1); // 风险水平
            assetInfoVO.setTotalBuyMoney(new BigDecimal(cashHolding.getUnderDueAmt().replace(",", "")));
            assetInfoVO.setMaxExposure(new BigDecimal(cashHolding.getExRate())); // 信用额度
            // assetInfoVO.setTodayIncome(); // 今日盈亏
            // assetInfoVO.setTodayIncomeRatio(); //今日盈亏比
            // assetInfoVO.setIncomeBalance(); //持仓盈亏
            // assetInfoVO.setIncomeBalanceRatio(); //持仓盈亏比
            assetInfoVOList.add(assetInfoVO);

        }
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
        FundInfoRequest request = new FundInfoRequest();
        request.setTradeAccount(fundAccount);
        return getFundInfo(request);
    }

    @Override
    public R getSingleAccount(SingleAccountRequest request) {
        return null;
    }

    @Override
    public R getUserPortfolio(UserPortfolioRequest request) {
        List<PositionVO> positions;
        HoldingQueryRequest holdingQueryRequest = new HoldingQueryRequest();
        holdingQueryRequest.setClientId(request.getTradeAccount());
        R result = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.HOLDING_QUERY.getRequestType(),
            holdingQueryRequest, request.getTradeAccount());
        CommonResponse commonResponse = (CommonResponse)result.getData();
        if (result.isSuccess()) {
            positions = listPosition(commonResponse);
        } else {
            return result;
        }
        return R.data(positions);
    }

    private List<PositionVO> listPosition(CommonResponse response) {
        List<PositionVO> positions = new ArrayList<>();
        HoldingQueryResponse holdingQueryResponse =
            JSONUtil.fromJson(response.getResult().toString(), HoldingQueryResponse.class);
        for (HoldingQueryResponse.StockHolding stockHolding : holdingQueryResponse.getStockHoldingList()) {
            PositionVO positionVO = new PositionVO();
            positionVO.setExchangeType(AfeExchangeTypeEnum.getZsExchangeType(stockHolding.getMarket()));
            if (AfeExchangeTypeEnum.HK.getExchangeType().equals(positionVO.getExchangeType())) {
                positionVO.setStockCode(stockHolding.getInstrumentCode());
                positionVO.setAssetId(MarketUtils.translateHkAssetId(positionVO.getStockCode()));
            } else if (AfeExchangeTypeEnum.US.getExchangeType().equals(positionVO.getExchangeType())) {
                // 美股
                positionVO.setStockCode(stockHolding.getInstrumentCode());
                positionVO.setAssetId(MarketUtils.translateUsAssetId(positionVO.getStockCode()));
            } else if (AfeExchangeTypeEnum.ML.getExchangeType().equals(positionVO.getExchangeType())) {
                positionVO.setExchangeType(ExchangeTypeEnum.ML.getExchangeType());
                positionVO.setStockCode(stockHolding.getInstrumentCode());
                positionVO.setAssetId(stockHolding.getInstrumentCode() + "." + positionVO.getExchangeType());
            }
            StkInfo assetInfo = grmCacheMgr.getAssetInfo(positionVO.getAssetId());
            positionVO.setLotSize(assetInfo.getLotSize());
            positionVO.setSecSType(assetInfo.getSecSType());
            positionVO.setStockName(assetInfo.getEngName());
            positionVO.setStockNameZhCN(assetInfo.getStkName());
            positionVO.setStockNameZhHK(assetInfo.getTraditionalName());
            positionVO.setAvBuyPrice(new BigDecimal(stockHolding.getAvgCost()));
            positionVO.setCostPrice(new BigDecimal(stockHolding.getCurrentAvgCost()));
            positionVO.setCurrentQty(new BigDecimal(stockHolding.getAvailableQuantity().replace(",", "")));
            positionVO.setEnableQty(new BigDecimal(stockHolding.getAvailableQuantity().replace(",", ""))); // 可用持股数量
            // TODO 要根据行情权限展示价格 从行情获取
            positionVO.setLastPrice(new BigDecimal(stockHolding.getLastClosePrice()));
            // positionVO.setClosePrice(); // 收盘价
            positionVO.setCurrency(AfeCurrencyEnum.getZsCurrency(stockHolding.getCurrencyCode()));
            positionVO.setMarketValue(positionVO.getLastPrice().multiply(positionVO.getCurrentQty())); // 持仓市值
            BigDecimal incomeBalance =
                (positionVO.getLastPrice().subtract(positionVO.getCostPrice())).multiply(positionVO.getCurrentQty());
            positionVO.setIncomeBalance(incomeBalance);
            if (positionVO.getMarketValue().compareTo(BigDecimal.ZERO) > 0) {
                positionVO.setIncomeBalanceRatio(
                    incomeBalance.divide(positionVO.getMarketValue(), new MathContext(5, RoundingMode.HALF_UP)));
            }
            positions.add(positionVO);
        }
        return positions;

    }

    @Override
    public R getJournalOrders(JournalOrdersRequest request) {
        List<JournalOrdersVO> ordersVOList;
        OrderSummaryQueryRequest orderSummaryQueryRequest = new OrderSummaryQueryRequest();
        orderSummaryQueryRequest.setFrom(DateUtil.dateTimeNow(YYYYMMDD));
        orderSummaryQueryRequest.setTo(DateUtil.dateTimeNow(YYYYMMDD));
        orderSummaryQueryRequest.setClientId(request.getTradeAccount());
        R result = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.ORDER_SUMMARY_QUERY.getRequestType(),
            orderSummaryQueryRequest, request.getTradeAccount());

        if (result.isSuccess()) {
			CommonResponse commonResponse = (CommonResponse)result.getData();
            ordersVOList = listJournalOrders(commonResponse);
        } else {
            return result;
        }
        return R.data(ordersVOList);

    }

    private List<JournalOrdersVO> listJournalOrders(CommonResponse commonResponse) {
        List<JournalOrdersVO> journalOrdersVOList = new ArrayList<>();
        OrderSummaryQueryResponse orderSummaryQueryResponse =
            JSONUtil.fromJson(commonResponse.getResult().toString(), OrderSummaryQueryResponse.class);
        for (OrderSummaryQueryResponse.OrderSummary orderSummary : orderSummaryQueryResponse.getOrderSummaries()) {
            JournalOrdersVO vo = new JournalOrdersVO();
            vo.setOrderNo(orderSummary.getOrderNo());
            vo.setExchangeType(AfeExchangeTypeEnum.getZsExchangeType(orderSummary.getMarket()));
            if (AfeExchangeTypeEnum.HK.getExchangeType().equals(vo.getExchangeType())) {
                vo.setStockCode(orderSummary.getInstrumentNo());
                vo.setAssetId(MarketUtils.translateHkAssetId(vo.getStockCode()));
            } else if (AfeExchangeTypeEnum.US.getExchangeType().equals(vo.getExchangeType())) {
                // 美股
                vo.setStockCode(orderSummary.getInstrumentNo());
                vo.setAssetId(MarketUtils.translateUsAssetId(vo.getStockCode()));
            } else if (AfeExchangeTypeEnum.ML.getExchangeType().equals(vo.getExchangeType())) {
                vo.setExchangeType(ExchangeTypeEnum.ML.getExchangeType());
                vo.setStockCode(orderSummary.getInstrumentNo());
                vo.setAssetId(orderSummary.getInstrumentNo() + "." + vo.getExchangeType());
            }

            vo.setOrderTime(orderSummary.getOrderSubmitDatetime());
            StkInfo assetInfo = grmCacheMgr.getAssetInfo(vo.getAssetId());
            vo.setLotSize(assetInfo.getLotSize());
            vo.setSecSType(assetInfo.getSecSType());
            vo.setStockName(assetInfo.getEngName());
            vo.setStockNameZhCN(assetInfo.getStkName());
            vo.setStockNameZhHK(assetInfo.getTraditionalName());
            vo.setBsFlag(AfeEntrustBsEnum.getZsEntrustBs(orderSummary.getBidAskType()));
            if (StringUtils.isNotBlank(orderSummary.getPrice())) {
                vo.setPrice(new BigDecimal(orderSummary.getPrice()));
            }
            vo.setQty(new BigDecimal(orderSummary.getQuantity()));
            // TODO 成交价格没有
            vo.setBusinessPrice(new BigDecimal(orderSummary.getPrice()));
            vo.setBusinessQty(new BigDecimal(orderSummary.getExecutedQuantity()));
            // TODO 成交时间没有 成交金额没有
            vo.setBusinessTime(orderSummary.getOrderSubmitDatetime());
            vo.setBusinessBalance(new BigDecimal("0"));
            vo.setOrderType(AfeEntrustPropEnum.getZsEntrustProp(orderSummary.getMatchingType()));
            vo.setOrderStatus(AfeEntrustStatusEnum.getZsEntrustStatus(orderSummary.getOrderStatus()));
            vo.setEntrustDate(orderSummary.getOrderSubmitDatetime());
            vo.setEntrustTime(orderSummary.getOrderSubmitDatetime());
            vo.setEntrustProp(AfeEntrustPropEnum.getZsEntrustProp(orderSummary.getMatchingType()));

            if ("0".equals(vo.getOrderStatus()) || "1".equals(vo.getOrderStatus()) || "2".equals(vo.getOrderStatus())
                || "3".equals(vo.getOrderStatus()) || "5".equals(vo.getOrderStatus())
                || "7".equals(vo.getOrderStatus())) {
                vo.setCancelable("1");
                vo.setModifiable("1");
            } else {
                vo.setCancelable("0");
                vo.setModifiable("0");
            }
            /**
             * 中华通不可改单
             */
            if (AfeExchangeTypeEnum.ML.getExchangeType().equals(vo.getExchangeType())) {
                vo.setModifiable("0");
            }
            journalOrdersVOList.add(vo);
        }
        return journalOrdersVOList;
    }

    @Override
    public R getHistoryOrders(HistoryOrdersRequest request) {
        List<JournalOrdersVO> ordersVOList;
        OrderBookHistoryRequest orderBookHistoryRequest = new OrderBookHistoryRequest();
        if (StringUtils.isNotBlank(request.getStartDate())) {
            Date startDate = cn.hutool.core.date.DateUtil.parse(request.getStartDate(), YYYY_MM_DD);
            orderBookHistoryRequest.setFrom(cn.hutool.core.date.DateUtil.format(startDate, YYYYMMDD));
        }
        if (StringUtils.isNotBlank(request.getEndDate())) {
            Date endDate = cn.hutool.core.date.DateUtil.parse(request.getEndDate(), YYYY_MM_DD);
            orderBookHistoryRequest.setTo(cn.hutool.core.date.DateUtil.format(endDate, YYYYMMDD));
        }
        orderBookHistoryRequest.setClientId(request.getTradeAccount());
		R result ;
		if(StringUtils.isNotBlank(request.getChannel()) && AfeConstants.AGENT_CHANNEL.equals(request.getChannel())){
			result = webSocketClientService.sendAgentSyncMsg(AfeInterfaceTypeEnum.ORDER_BOOK_HISTORY.getRequestType(),
				orderBookHistoryRequest, request.getTradeAccount());
		}else{
			result = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.ORDER_BOOK_HISTORY.getRequestType(),
				orderBookHistoryRequest, request.getTradeAccount());
		}
        CommonResponse commonResponse = (CommonResponse)result.getData();
        if (result.isSuccess()) {
            ordersVOList = listHistoryOrders(commonResponse);
            if (StringUtils.isNotBlank(request.getExchangeType())) {
                ordersVOList = ordersVOList.stream()
                    .filter(vo -> vo.getExchangeType().equals(request.getExchangeType())).collect(Collectors.toList());
            }
            if (StringUtils.isNotBlank(request.getStockCode())) {
                ordersVOList = ordersVOList.stream().filter(vo -> vo.getStockCode().equals(request.getStockCode()))
                    .collect(Collectors.toList());
            }
        } else {
            return result;
        }
        return R.data(ordersVOList);
    }

    private List<JournalOrdersVO> listHistoryOrders(CommonResponse commonResponse) {

        List<JournalOrdersVO> journalOrdersVOList = new ArrayList<>();
        OrderBookHistoryResponse orderBookHistoryResponse =
            JSONUtil.fromJson(commonResponse.getResult().toString(), OrderBookHistoryResponse.class);
        for (OrderBookHistoryResponse.OrderBook orderBook : orderBookHistoryResponse.getOrderBook()) {
            JournalOrdersVO vo = new JournalOrdersVO();
            vo.setOrderNo(orderBook.getOrderNo());
			String market =orderBook.getMarket().trim();
			String exchangeType=AfeExchangeTypeEnum.getZsExchangeType(market);
            vo.setExchangeType(exchangeType);
            if (AfeExchangeTypeEnum.HK.getExchangeType().equals(market)) {
                vo.setStockCode(orderBook.getInstrumentNo());
                vo.setAssetId(MarketUtils.translateHkAssetId(vo.getStockCode()));
            } else if (AfeExchangeTypeEnum.US.getExchangeType().equals(market)) {
                // 美股
                vo.setStockCode(orderBook.getInstrumentNo());
                vo.setAssetId(MarketUtils.translateUsAssetId(vo.getStockCode()));
            } else if (AfeExchangeTypeEnum.ML.getExchangeType().equals(market)) {
                vo.setExchangeType(ExchangeTypeEnum.ML.getExchangeType());
                vo.setStockCode(orderBook.getInstrumentNo());
                vo.setAssetId(orderBook.getInstrumentNo() + "." + vo.getExchangeType());
            }

            vo.setOrderTime(orderBook.getOrderSubmitDatetime());
            StkInfo assetInfo = grmCacheMgr.getAssetInfo(vo.getAssetId());
            vo.setLotSize(assetInfo.getLotSize());
            vo.setSecSType(assetInfo.getSecSType());
            vo.setStockName(assetInfo.getEngName());
            vo.setStockNameZhCN(assetInfo.getStkName());
            vo.setStockNameZhHK(assetInfo.getTraditionalName());
            vo.setBsFlag(AfeEntrustBsEnum.getZsEntrustBs(orderBook.getBidAskType()));
            if (StringUtils.isNotBlank(orderBook.getPrice())) {
                vo.setPrice(new BigDecimal(orderBook.getPrice()));
            }
            vo.setQty(new BigDecimal(orderBook.getQuantity()));
            // TODO 成交价格没有
            List<OrderBookHistoryResponse.Execution> executionList = orderBook.getExecution();
            if (executionList.size() > 0) {
                List<JournalOrdersVO.OrdersDetailVO> ordersDetailList = new ArrayList<>();
                for (OrderBookHistoryResponse.Execution execution : executionList) {
                    JournalOrdersVO.OrdersDetailVO ordersDetailVO = new JournalOrdersVO.OrdersDetailVO();
                    ordersDetailVO.setPrice(new BigDecimal(execution.getPrice()));
                    ordersDetailVO.setQty(new BigDecimal(execution.getExecutedQuantity()));
                    ordersDetailVO.setBusinessTime(execution.getExecutionDatetime());
                    ordersDetailList.add(ordersDetailVO);
                }
                vo.setOrdersDetailList(ordersDetailList);
                BigDecimal sumPrice = executionList.stream().map(OrderBookHistoryResponse.Execution::getPrice)
                    .map(BigDecimal::new).reduce(BigDecimal.ZERO, BigDecimal::add);
                sumPrice = sumPrice.divide(new BigDecimal(executionList.size()), 2, BigDecimal.ROUND_HALF_UP);
                BigDecimal sumQty = executionList.stream().map(OrderBookHistoryResponse.Execution::getExecutedQuantity)
                    .map(BigDecimal::new).reduce(BigDecimal.ZERO, BigDecimal::add);
                vo.setBusinessPrice(sumPrice);
                vo.setBusinessQty(sumQty);
                // TODO 成交时间没有 成交金额没有
                vo.setBusinessTime(executionList.get(0).getExecutionDatetime());

            } else {
                vo.setBusinessPrice(new BigDecimal("0"));
                vo.setBusinessQty(new BigDecimal("0"));
                vo.setBusinessTime("");
            }
            vo.setBusinessBalance(new BigDecimal("0"));
            vo.setOrderType(AfeEntrustPropEnum.getZsEntrustProp(orderBook.getMatchingType()));
            vo.setOrderStatus(AfeEntrustStatusEnum.getZsEntrustStatus(orderBook.getOrderStatus()));
            vo.setEntrustDate(orderBook.getOrderSubmitDatetime());
            vo.setEntrustTime(orderBook.getOrderSubmitDatetime());
            vo.setEntrustProp(AfeEntrustPropEnum.getZsEntrustProp(orderBook.getMatchingType()));
            journalOrdersVOList.add(vo);
        }
        return journalOrdersVOList;
    }

    @Override
    public R getJournalFundRecord(JournalFundRecordRequest journalFundRecordRequest) {

        List<FundRecordVO> fundRecordVOS;

        CashMovementQueryRequest request = new CashMovementQueryRequest();

        request.setFrom(DateUtil.dateTimeNow(YYYYMMDD));
        request.setTo(DateUtil.dateTimeNow(YYYYMMDD));
        request.setClientId(journalFundRecordRequest.getTradeAccount());

        R result = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.CASH_MOVEMENT_QUERY.getRequestType(),
            request, journalFundRecordRequest.getTradeAccount());
        CommonResponse commonResponse = (CommonResponse)result.getData();
        if (result.isSuccess()) {
            fundRecordVOS = listFundRecord(commonResponse);
        } else {
            return result;
        }
        return R.data(fundRecordVOS);

    }

    /**
     * 资金记录
     *
     * @param commonResponse
     * @return
     */
    private List<FundRecordVO> listFundRecord(CommonResponse commonResponse) {
        List<FundRecordVO> fundRecordVOS = new ArrayList<>();
        CashMovementQueryResponse response =
            JSONUtil.fromJson(commonResponse.getResult().toString(), CashMovementQueryResponse.class);
        for (CashMovementQueryResponse.CashMovement cashMovement : response.getCashMovementList()) {
            FundRecordVO vo = new FundRecordVO();
            vo.setCurrency(AfeCurrencyEnum.getZsCurrency(cashMovement.getCurrency()));
            vo.setBusinessDate(cashMovement.getTransactionDate());
            vo.setBusinessFlag(cashMovement.getMovementType());
            // TODO 业务类型
            vo.setBusinessName(cashMovement.getMovementType());
            vo.setBusinessBalance(new BigDecimal(cashMovement.getAmount().replace(",", "")));
            vo.setOrderNo(cashMovement.getRefNo());
            fundRecordVOS.add(vo);
        }
        return fundRecordVOS;

    }

    @Override
    public R getHistoryFundRecord(HistoryFundRecordRequest historyFundRecordRequest) {
        List<FundRecordVO> fundRecordVOS;

        CashMovementQueryRequest request = new CashMovementQueryRequest();

        if (StringUtil.isNotBlank(historyFundRecordRequest.getStartDate())) {
            Date startDate = cn.hutool.core.date.DateUtil.parse(historyFundRecordRequest.getStartDate(), YYYY_MM_DD);
            request.setFrom(cn.hutool.core.date.DateUtil.format(startDate, YYYYMMDD));
        }
        if (StringUtil.isNotBlank(historyFundRecordRequest.getEndDate())) {
            Date endDate = cn.hutool.core.date.DateUtil.parse(historyFundRecordRequest.getEndDate(), YYYY_MM_DD);
            request.setTo(cn.hutool.core.date.DateUtil.format(endDate, YYYYMMDD));
        }
        request.setClientId(historyFundRecordRequest.getTradeAccount());

        R result = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.CASH_MOVEMENT_QUERY.getRequestType(),
            request, historyFundRecordRequest.getTradeAccount());
        CommonResponse commonResponse = (CommonResponse)result.getData();
        if (result.isSuccess()) {
            fundRecordVOS = listFundRecord(commonResponse);
            if (StringUtil.isNotBlank(historyFundRecordRequest.getCurrency())) {
                fundRecordVOS = fundRecordVOS.stream()
                    .filter(fundRecordVO -> fundRecordVO.getCurrency().equals(historyFundRecordRequest.getCurrency()))
                    .collect(Collectors.toList());
            }
        } else {
            return result;
        }
        return R.data(fundRecordVOS);
    }
    // 下载文件

    @Override
    public R getJournalStockRecord(JournalStkRecordRequest journalStkRecordRequest) {
        List<StockRecordVO> stockRecordVOList;
        StockMovementQueryRequest request = new StockMovementQueryRequest();
        request.setFrom(DateUtil.dateTimeNow(YYYYMMDD));
        request.setTo(DateUtil.dateTimeNow(YYYYMMDD));
        request.setClientId(journalStkRecordRequest.getTradeAccount());
        R result = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.STOCK_MOVEMENT_QUERY.getRequestType(),
            request, journalStkRecordRequest.getTradeAccount());
        CommonResponse commonResponse = (CommonResponse)result.getData();
        if (result.isSuccess()) {
            stockRecordVOList = listStockRecord(commonResponse);
        } else {
            return result;
        }
        return R.data(stockRecordVOList);
    }

    private List<StockRecordVO> listStockRecord(CommonResponse commonResponse) {
        List<StockRecordVO> stockRecordVOList = new ArrayList<>();
        StockMovementQueryResponse response =
            JSONUtil.fromJson(commonResponse.getResult().toString(), StockMovementQueryResponse.class);
        for (StockMovementQueryResponse.StockMovement stockMovement : response.getStockMovementList()) {
            StockRecordVO vo = new StockRecordVO();
            // vo.setExchangeType(AfeExchangeTypeEnum.getZsExchangeType(stockMovement.getExchange()));
            vo.setExchangeType(AfeExchangeTypeEnum.HK.getExchangeType());
            if (AfeExchangeTypeEnum.HK.getExchangeType().equals(vo.getExchangeType())) {
                vo.setStockCode(stockMovement.getInstrumentNo());
                vo.setAssetId(MarketUtils.translateHkAssetId(vo.getStockCode()));
            } else if (AfeExchangeTypeEnum.US.getExchangeType().equals(vo.getExchangeType())) {
                // 美股
                vo.setStockCode(stockMovement.getInstrumentNo());
                vo.setAssetId(MarketUtils.translateUsAssetId(vo.getStockCode()));
            } else if (AfeExchangeTypeEnum.ML.getExchangeType().equals(vo.getExchangeType())) {
                vo.setExchangeType(ExchangeTypeEnum.ML.getExchangeType());
                vo.setStockCode(stockMovement.getInstrumentNo());
                vo.setAssetId(stockMovement.getInstrumentNo() + "." + vo.getExchangeType());
            }
            vo.setBusinessDate(stockMovement.getTransactionDate());
            vo.setBusinessFlag(stockMovement.getMovementType());
            // TODO 业务类型
            vo.setBusinessName(stockMovement.getMovementType());
            vo.setCurrency("");
            // vo.setBusinessBalance(positionFlow.getPrice().multiply(positionFlow.getQuantity()));
            // vo.setBusinessAmount(positionFlow.getQuantity());
            vo.setOrderNo(stockMovement.getRefNo());
            StkInfo assetInfo = grmCacheMgr.getAssetInfo(vo.getAssetId());
            vo.setStockName(assetInfo.getEngName());
            vo.setStockNameZhCN(assetInfo.getStkName());
            vo.setStockNameZhHK(assetInfo.getTraditionalName());
            stockRecordVOList.add(vo);
        }
        return stockRecordVOList;

    }

    @Override
    public R getHistoryStockRecord(HistoryStkRecordRequest historyStkRecordRequest) {

        List<StockRecordVO> stockRecordVOList;

        StockMovementQueryRequest request = new StockMovementQueryRequest();

        if (StringUtil.isNotBlank(historyStkRecordRequest.getStartDate())) {
            Date startDate = cn.hutool.core.date.DateUtil.parse(historyStkRecordRequest.getStartDate(), YYYY_MM_DD);
            request.setFrom(cn.hutool.core.date.DateUtil.format(startDate, YYYYMMDD));
        }
        if (StringUtil.isNotBlank(historyStkRecordRequest.getEndDate())) {
            Date endDate = cn.hutool.core.date.DateUtil.parse(historyStkRecordRequest.getEndDate(), YYYY_MM_DD);
            request.setTo(cn.hutool.core.date.DateUtil.format(endDate, YYYYMMDD));
        }
        request.setClientId(historyStkRecordRequest.getTradeAccount());

        R result = webSocketClientService.sendSyncMsg(AfeInterfaceTypeEnum.STOCK_MOVEMENT_QUERY.getRequestType(),
            request, historyStkRecordRequest.getTradeAccount());
        CommonResponse commonResponse = (CommonResponse)result.getData();
        if (result.isSuccess()) {
            stockRecordVOList = listStockRecord(commonResponse);
            if (StringUtil.isNotBlank(historyStkRecordRequest.getExchangeType())) {
                stockRecordVOList = stockRecordVOList.stream().filter(
                    stockRecordVO -> stockRecordVO.getExchangeType().equals(historyStkRecordRequest.getExchangeType()))
                    .collect(Collectors.toList());
            }
        } else {
            return result;
        }
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
        Map<String, StockMarginRatioVO> map = zeroRedis.get(CacheNames.TRADE_STOCK_MARGIN_RATIO.concat(lang));
        if (map != null) {
            return R.data(map.values());
        }
        // 从柜台获取可融资股票列表
        StockMarginRatioRequest stockMarginRatioReq = new StockMarginRatioRequest();
        stockMarginRatioReq.setMarketId(AfeCommon.MARKET_ID);
        stockMarginRatioReq.setApiName(ApiEnum.MARGINABLE_INSTRUMENT.getUrl());
        BackCommonResponse backCommonResponse =
            sendApiService.sendMessage(stockMarginRatioReq, stockMarginRatioReq.getApiName(), true);
        if (backCommonResponse != null && backCommonResponse.isSuccess()) {
            StockMarginRatioResponse stockMarginRatioResponse = JSONUtil
                .fromJson(backCommonResponse.getResponse().getResult().toString(), StockMarginRatioResponse.class);
            if (stockMarginRatioResponse.getRecord() != null && stockMarginRatioResponse.getRecord().size() > 0) {
                Map<String, StockMarginRatioVO> scMap = new HashMap<>();
                Map<String, StockMarginRatioVO> tcMap = new HashMap<>();
                Map<String, StockMarginRatioVO> enMap = new HashMap<>();
                List<StockMarginRatioResponse.Record> recordList = stockMarginRatioResponse.getRecord();
                for (StockMarginRatioResponse.Record record : recordList) {
                    StockMarginRatioVO stockMarginRatioVO = new StockMarginRatioVO();
                    StkInfo stkInfo = zeroRedis.protoGet(record.getInstrumentId().concat(".HK"), StkInfo.class);
                    stockMarginRatioVO.setMarginRatio(record.getMarginPercentage().getContent());
                    stockMarginRatioVO.setStockName(HSUtil.getStockName(stkInfo, Constants.ZH_HANS, ""));
                    stockMarginRatioVO.setAssetId(record.getInstrumentId().concat(".HK"));
                    stockMarginRatioVO.setExchangeType(AfeExchangeTypeEnum.HK.getExchangeType());
                    stockMarginRatioVO.setStockCode(record.getInstrumentId());
                    scMap.put(stockMarginRatioVO.getAssetId(), stockMarginRatioVO);

                    StockMarginRatioVO tcVo = new StockMarginRatioVO();
                    tcVo.setMarginRatio(stockMarginRatioVO.getMarginRatio());
                    tcVo.setAssetId(stockMarginRatioVO.getAssetId());
                    tcVo.setStockName(
                        HSUtil.getStockName(stkInfo, Constants.ZH_HANT, stockMarginRatioVO.getStockName()));
                    tcMap.put(stockMarginRatioVO.getAssetId(), tcVo);

                    StockMarginRatioVO enVo = new StockMarginRatioVO();
                    enVo.setMarginRatio(stockMarginRatioVO.getMarginRatio());
                    enVo.setAssetId(stockMarginRatioVO.getAssetId());
                    enVo.setStockName(HSUtil.getStockName(stkInfo, Constants.EN, stockMarginRatioVO.getStockName()));
                    enMap.put(stockMarginRatioVO.getAssetId(), enVo);

                }
                zeroRedis.setEx(CacheNames.TRADE_STOCK_MARGIN_RATIO.concat(Constants.ZH_HANS), scMap,
                    CacheNames.TRADE_STOCK_MARGIN_RATIO_EXPIRE_TIME);
                zeroRedis.setEx(CacheNames.TRADE_STOCK_MARGIN_RATIO.concat(Constants.ZH_HANT), tcMap,
                    CacheNames.TRADE_STOCK_MARGIN_RATIO_EXPIRE_TIME);
                zeroRedis.setEx(CacheNames.TRADE_STOCK_MARGIN_RATIO.concat(Constants.EN), enMap,
                    CacheNames.TRADE_STOCK_MARGIN_RATIO_EXPIRE_TIME);
            }

            map = zeroRedis.get(CacheNames.TRADE_STOCK_MARGIN_RATIO.concat(lang));
            if (map != null) {
                return R.data(map.values());
            }
        }
        return R.fail();
    }

    /**
     * 入金
     *
     * @param fundDepositReq
     * @return
     */
    @Override
    public R deposit(FundDepositReq fundDepositReq) {


		DepositAmountReq depositAmountReq = new DepositAmountReq();
		depositAmountReq.setAccountId(fundDepositReq.getFundAccount());
		depositAmountReq.setCurrencyId(CurrencyEnum.getCounterCurrency(fundDepositReq.getCurrency()));
		depositAmountReq.setAmount(fundDepositReq.getAmount());
		depositAmountReq.setApiName(ApiEnum.CREATE_CASH_DEPOSIT.getUrl());
		depositAmountReq.setInternalRemark(fundDepositReq.getRemark());
		BackCommonResponse backCommonResponse =
			sendApiService.sendMessage(depositAmountReq, depositAmountReq.getApiName(), true);

		if (null != backCommonResponse) {
			if (backCommonResponse.isSuccess()) {
				return R.success();
			} else {
				return R.fail(backCommonResponse.getMessage());
			}
		}
		return R.fail();

    }

    @Override
    public R withdrawal(FundWithdrawalReq fundWithdrawalReq) {
		WithdrawAmountReq withdrawAmountReq = new WithdrawAmountReq();
		withdrawAmountReq.setAccountId(fundWithdrawalReq.getFundAccount());
		withdrawAmountReq.setCurrencyId(CurrencyEnum.getCounterCurrency(fundWithdrawalReq.getCurrency()));
		withdrawAmountReq.setAmount(fundWithdrawalReq.getAmount());
		withdrawAmountReq.setApiName(ApiEnum.CREATE_CASH_WITHDRAWAL.getUrl());
		withdrawAmountReq.setInternalRemark(fundWithdrawalReq.getRemark());

		log.info("afe出金请求参数为:{}", JsonUtil.toJson(withdrawAmountReq));
		BackCommonResponse backCommonResponse =
			sendApiService.sendMessage(withdrawAmountReq, withdrawAmountReq.getApiName(), true);
		log.info("afe出金返回参数为:{}", JsonUtil.toJson(backCommonResponse));
		if (null != backCommonResponse) {
			if (backCommonResponse.isSuccess()) {
				return R.success();
			} else {
				return R.fail(backCommonResponse.getMessage());
			}
		}
		return R.fail();
    }

    @Override
    public R freeze(FundFreezeReq fundFreezeReq) {
		CreateCashHoldRequest createCashHoldRequest = new CreateCashHoldRequest();
		createCashHoldRequest.setValueDate(DateUtil.getDate());
		createCashHoldRequest.setAccountId(fundFreezeReq.getFundAccount());
		createCashHoldRequest.setCurrencyId(CurrencyEnum.getCounterCurrency(fundFreezeReq.getCurrency()));
		createCashHoldRequest.setAmount(fundFreezeReq.getAmount());
		createCashHoldRequest.setHoldType("T");
		createCashHoldRequest.setAutoApprovalFlag("Y");
		createCashHoldRequest.setTransactionReference(fundFreezeReq.getOrderId());
		createCashHoldRequest.setRemark("FUND HOLD");
		createCashHoldRequest.setApiName(ApiEnum.CREATE_CASH_HOLD.getUrl());
		BackCommonResponse backCommonResponse =
			sendApiService.sendMessage(createCashHoldRequest, createCashHoldRequest.getApiName(), true);

		if (null != backCommonResponse) {
			if (backCommonResponse.isSuccess()) {
				return R.success();
			} else {
				return R.fail(backCommonResponse.getMessage());
			}
		}
		return R.fail();
    }

    @Override
    public R unFreeze(FundUnFreezeReq fundUnFreezeReq) {
		CreateCashReleaseRequest createdCashReleaseRequest =  new CreateCashReleaseRequest();
		createdCashReleaseRequest.setValueDate(DateUtil.getDate());
		createdCashReleaseRequest.setAccountId(fundUnFreezeReq.getFundAccount());
		createdCashReleaseRequest.setCurrencyId(CurrencyEnum.getCounterCurrency(fundUnFreezeReq.getCurrency()));
		createdCashReleaseRequest.setAmount(fundUnFreezeReq.getAmount());
		createdCashReleaseRequest.setHoldType("T");
		createdCashReleaseRequest.setAutoApprovalFlag("Y");
		createdCashReleaseRequest.setRemark("FUND UnFreeze");
		createdCashReleaseRequest.setTransactionReference(fundUnFreezeReq.getLockedCashId());
		createdCashReleaseRequest.setApiName(ApiEnum.CREATE_CASH_RELEASE.getUrl());
		log.info("afe解冻请求参数为:{}", JsonUtil.toJson(createdCashReleaseRequest));
		BackCommonResponse backCommonResponse =
			sendApiService.sendMessage(createdCashReleaseRequest, createdCashReleaseRequest.getApiName(), true);
		log.info("afe解冻返回参数为:{}", JsonUtil.toJson(backCommonResponse));
		if (null != backCommonResponse) {
			if (backCommonResponse.isSuccess()) {
				return R.success();
			} else {
				return R.fail(backCommonResponse.getMessage());
			}
		}
		return R.fail();
    }

    @Override
    public R unFreezeWithdraw(FundUnFreezeWithdrawReq fundUnFreezeWithdrawReq) {
		/**
		 * 先解冻再扣款
		 */
		FundUnFreezeReq fundUnFreezeReq =new FundUnFreezeReq();
		fundUnFreezeReq.setTradeAccount(fundUnFreezeWithdrawReq.getTradeAccount());
		fundUnFreezeReq.setFundAccount(fundUnFreezeWithdrawReq.getFundAccount());
		fundUnFreezeReq.setCurrency(fundUnFreezeWithdrawReq.getCurrency());
		fundUnFreezeReq.setAmount(fundUnFreezeWithdrawReq.getAmount());
		fundUnFreezeReq.setLockedCashId(fundUnFreezeWithdrawReq.getBusinessNumber());
		R result =unFreeze(fundUnFreezeReq);
		if(result.isSuccess()){
			// 扣款
			FundWithdrawalReq fundWithdrawalReq =new FundWithdrawalReq();
			fundWithdrawalReq.setTradeAccount(fundUnFreezeWithdrawReq.getTradeAccount());
			fundWithdrawalReq.setFundAccount(fundUnFreezeWithdrawReq.getFundAccount());
			fundWithdrawalReq.setCurrency(fundUnFreezeWithdrawReq.getCurrency());
			fundWithdrawalReq.setAmount(fundUnFreezeWithdrawReq.getAmount());
			result =withdrawal(fundWithdrawalReq);
			if(result.isSuccess()){
				return R.success();
			}else{
				log.error("资金账号：{}扣款失败",fundUnFreezeWithdrawReq.getFundAccount());
			}
		}else{
			log.error("资金账号：{}解冻失败",fundUnFreezeWithdrawReq.getFundAccount());
			return result;
		}
        return result;
    }

    @Override
    public R<FundQueryVO> queryFundByAccount(String tradeAccount, String capitalAccount) {
        return R.success();
    }

    @Override
    public R stockMarginRatioSetting(StockMarginSettingReq stockMarginSettingReq) {

        StockMarginSettingRequest stockMarginSettingRequest = new StockMarginSettingRequest();
        stockMarginSettingRequest.setMarketId(AfeCommon.MARKET_ID);
        stockMarginSettingRequest.setApiName(ApiEnum.MARGINABLE_INSTRUMENT_SETTING.getUrl());
        stockMarginSettingRequest.setInstrumentId(stockMarginSettingReq.getAssetId());
        stockMarginSettingRequest.setMarginPercentage(stockMarginSettingReq.getMarginRatio());

        BackCommonResponse backCommonResponse =
            sendApiService.sendMessage(stockMarginSettingRequest, stockMarginSettingRequest.getApiName(), true);

        if (null != backCommonResponse) {
            if (backCommonResponse.isSuccess()) {
                return R.success();
            } else {
                return R.fail(backCommonResponse.getMessage());
            }
        }
        return R.fail();
    }

	@Override
	public R getJournalStrategyOrders(JournalOrdersRequest request) {
		List<JournalOrdersVO> ordersVOList =new ArrayList<>();

		LambdaQueryWrapper<CustStrategyOrderEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustStrategyOrderEntity::getCustId, AuthUtil.getTenantCustId());
		queryWrapper.ge(CustStrategyOrderEntity::getCreateTime, DateUtil.dateTimeNow(YYYY_MM_DD));
//		queryWrapper.le(CustStrategyOrderEntity::getCreateTime, DateUtil.dateTimeNow(YYYY_MM_DD));
		queryWrapper.eq(CustStrategyOrderEntity::getIsDeleted, 0);
		queryWrapper.orderByDesc(CustStrategyOrderEntity::getCreateTime	);

		List<CustStrategyOrderEntity> orderList =custStrategyOrderService.list(queryWrapper);

		if(null != orderList && orderList.size()>0){
			ordersVOList = listJournalStrategyOrders(orderList);
		}
		return R.data(ordersVOList);
	}

	@Override
	public R getHistoryStrategyOrders(HistoryOrdersRequest request) {
		List<JournalOrdersVO> ordersVOList =new ArrayList<>();
		LambdaQueryWrapper<CustStrategyOrderEntity> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(CustStrategyOrderEntity::getCustId, AuthUtil.getTenantCustId());
		if(StringUtils.isNotBlank(request.getStartDate())){
			queryWrapper.ge(CustStrategyOrderEntity::getCreateTime, request.getStartDate()+ " 00:00:00");
		}
	    if(StringUtils.isNotBlank(request.getEndDate())){
			queryWrapper.le(CustStrategyOrderEntity::getCreateTime, request.getEndDate()+" 23:59:59");
		}
		if(StringUtils.isNotBlank(request.getEntrustStatus())){
			queryWrapper.eq(CustStrategyOrderEntity::getEntrustStatus, request.getEntrustStatus());
		}
		if(StringUtils.isNotBlank(request.getExchangeType())){
			if(AfeExchangeTypeEnum.ML.getExchangeType().equals(request.getExchangeType())){
				List<String> exchangeTypeList = new ArrayList<>();
				exchangeTypeList.add(AfeExchangeTypeEnum.SH.getExchangeType());
				exchangeTypeList.add(AfeExchangeTypeEnum.SZ.getExchangeType());
				queryWrapper.in(CustStrategyOrderEntity::getExchangeType, exchangeTypeList);
			}else{
				queryWrapper.eq(CustStrategyOrderEntity::getExchangeType, request.getExchangeType());
			}

		}

		queryWrapper.eq(CustStrategyOrderEntity::getIsDeleted, 0);
		queryWrapper.orderByDesc(CustStrategyOrderEntity::getCreateTime	);
		List<CustStrategyOrderEntity> orderList =custStrategyOrderService.list(queryWrapper);
		if(null != orderList && orderList.size()>0){
			ordersVOList = listJournalStrategyOrders(orderList);
		}
		return R.data(ordersVOList);
	}

	private List<JournalOrdersVO> listJournalStrategyOrders(List<CustStrategyOrderEntity> orderList) {
		List<JournalOrdersVO> journalOrdersVOList = new ArrayList<>();
		for (CustStrategyOrderEntity custStrategyOrderEntity : orderList) {
			JournalOrdersVO vo = new JournalOrdersVO();
			vo.setOrderNo(custStrategyOrderEntity.getId().toString());
			vo.setStrategyPrice(custStrategyOrderEntity.getStrategyPrice());
			vo.setExchangeType(custStrategyOrderEntity.getExchangeType());
			if (AfeExchangeTypeEnum.HK.getExchangeType().equals(vo.getExchangeType())) {
				vo.setStockCode(MarketUtils.extractStockCode(custStrategyOrderEntity.getAssetId()));
				vo.setAssetId(custStrategyOrderEntity.getAssetId());
			} else if (AfeExchangeTypeEnum.US.getExchangeType().equals(vo.getExchangeType())) {
				// 美股
				vo.setStockCode(MarketUtils.translateUsStockCode(custStrategyOrderEntity.getAssetId()));
				vo.setAssetId(MarketUtils.translateUsAssetId(vo.getStockCode()));
			} else if (AfeExchangeTypeEnum.ML.getExchangeType().equals(vo.getExchangeType())) {
				vo.setExchangeType(ExchangeTypeEnum.ML.getExchangeType());
				vo.setStockCode(custStrategyOrderEntity.getAssetId());
				vo.setAssetId(custStrategyOrderEntity.getAssetId());
			}

			vo.setOrderTime(DateUtil.formatDate(custStrategyOrderEntity.getCreateTime()));
			StkInfo assetInfo = grmCacheMgr.getAssetInfo(vo.getAssetId());
			vo.setLotSize(assetInfo.getLotSize());
			vo.setSecSType(assetInfo.getSecSType());
			vo.setStockName(assetInfo.getEngName());
			vo.setStockNameZhCN(assetInfo.getStkName());
			vo.setStockNameZhHK(assetInfo.getTraditionalName());
			vo.setBsFlag(custStrategyOrderEntity.getEntrustBs());
			vo.setPrice(custStrategyOrderEntity.getEntrustPrice());
			vo.setQty(new BigDecimal(custStrategyOrderEntity.getEntrustAmount()));
			// TODO 成交价格没有
//			vo.setBusinessPrice(new BigDecimal(orderSummary.getPrice()));
//			vo.setBusinessQty(new BigDecimal(orderSummary.getExecutedQuantity()));
			// TODO 成交时间没有 成交金额没有
//			vo.setBusinessTime(orderSummary.getOrderSubmitDatetime());
//			vo.setBusinessBalance(new BigDecimal("0"));
			vo.setOrderType("1");
			vo.setOrderStatus(custStrategyOrderEntity.getEntrustStatus());
			vo.setEntrustDate(cn.hutool.core.date.DateUtil.format(custStrategyOrderEntity.getCreateTime(), "yyyy-MM-dd"));
			vo.setEntrustTime(cn.hutool.core.date.DateUtil.format(custStrategyOrderEntity.getCreateTime(), "HH:mm:ss"));
			vo.setEntrustProp(custStrategyOrderEntity.getEntrustProp());
			vo.setStrategyAction(custStrategyOrderEntity.getStrategyAction());
			vo.setDeadlineDate(custStrategyOrderEntity.getDeadlineDate());
			vo.setEntrustStatus(custStrategyOrderEntity.getEntrustStatus());
			if ("C0".equals(vo.getOrderStatus())){
				vo.setCancelable("1");
				vo.setModifiable("1");
			} else {
				vo.setCancelable("0");
				vo.setModifiable("0");
			}
			/**
			 * 中华通不可改单
			 */
			if (AfeExchangeTypeEnum.ML.getExchangeType().equals(vo.getExchangeType())) {
				vo.setModifiable("0");
			}
			journalOrdersVOList.add(vo);
		}
		return journalOrdersVOList;

	}

}
