package com.minigod.zero.trade.service.impl;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.minigod.zero.biz.common.utils.JSONUtil;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.core.tool.utils.StringUtil;
import com.minigod.zero.trade.fund.vo.request.*;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.*;
import com.minigod.zero.trade.hs.resp.*;
import com.minigod.zero.trade.hs.service.*;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import com.minigod.zero.trade.service.IFundService;
import com.minigod.zero.trade.sjmb.common.MessageCode;
import com.minigod.zero.trade.sjmb.common.SjmbFunctionsUrlEnum;
import com.minigod.zero.trade.sjmb.req.brokerapi.*;
import com.minigod.zero.trade.sjmb.resp.SjmbResponse;
import com.minigod.zero.trade.sjmb.resp.brokerapi.*;
import com.minigod.zero.trade.sjmb.service.ICounterService;
import com.minigod.zero.trade.utils.HSUtil;
import com.minigod.zero.trade.utils.MarketUtils;
import com.minigod.zero.trade.vo.resp.AssetDetailVO;
import com.minigod.zero.trade.vo.resp.PositionDetailVO;
import com.minigod.zero.trade.vo.sjmb.*;
import com.minigod.zero.trade.vo.sjmb.req.*;
import com.minigod.zero.trade.vo.sjmb.resp.*;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.minigod.zero.trade.common.ServerConstant.MULTI_SERVER_TYPE_HS;

/**
 * 帐户服务
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "trade", name = "type", havingValue = MULTI_SERVER_TYPE_HS)
public class FundServiceHsImpl implements IFundService {

    private final EF01100100 eF01100100;
    private final EF01110003 eF01110003;
    private final EF01110180 eF01110180;
    private final EF01110101 eF01110101;
    private final EF01110102 eF01110102;
    private final EF01110113 eF01110113;
    private final EF01110173 eF01110173;
    private final EF01110005 eF01110005;
    private final EF01110148 eF01110148;
    private final EF01110112 eF01110112;
    private final EF01110186 eF01110186;
    private final EF01110114 eF01110114;
    private final EF01110172 eF01110172;
    private final EF01110004 ef01110004;
    private final EF01110108 ef01110108;
    private final EF01110111 eF01110111;
    private final EF01110181 eF01110181;
    private final EF01110150 eF01110150;
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

    @SneakyThrows
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
        String fundAccount = request.getFundAccount();
        EF01110003Request ef01110003Request = new EF01110003Request();
        ef01110003Request.setSid(Constants.innerClientSideSid);
        ef01110003Request.setSessionId(Constants.innerClientSideSid);
        ef01110003Request.setFunctionId(GrmFunctions.BROKER_GET_CACHE_INFO);
        ef01110003Request.setUserId(AuthUtil.getCustId());
        ef01110003Request.setFundAccount(fundAccount);
        ef01110003Request.setQueryPositions(false);
        GrmResponseVO response = eF01110003.requestData(ef01110003Request);
        if (!response.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            log.error("getFundInfo EF01110003, error=" + JSON.toJSONString(response));
            return R.fail(response.getErrorMsg());
        }
        List<EF01110003VO> eF01110003VOs = (List<EF01110003VO>) response.getResult().get("data");
        AssetDetailVO vo = new AssetDetailVO();
        vo.setAssetDetails(eF01110003VOs);
        // 获取港币保证金比例
        vo.setBondRatio(getEF01110180(fundAccount));
        return R.data(vo);
    }

    @Override
    public R getDetailAccount(Long custId, String fundAccount, String moneyType) {
        EF01110003Request ef01110003Request = new EF01110003Request();
        ef01110003Request.setSid(Constants.innerClientSideSid);
        ef01110003Request.setSessionId(Constants.innerClientSideSid);
        ef01110003Request.setFunctionId(GrmFunctions.BROKER_GET_CACHE_INFO);
        ef01110003Request.setUserId(custId);
        ef01110003Request.setFundAccount(fundAccount);
        if (!StringUtils.isEmpty(moneyType)) {
            moneyType = HsConstants.HsMoneyType.valueOf(moneyType).getMoneyType();

        }
        ef01110003Request.setMoneyType(moneyType);
        ef01110003Request.setQueryPositions(false);
        GrmResponseVO response = eF01110003.requestData(ef01110003Request);
        if (!response.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            log.error("getFundInfo EF01110003, error=" + JSON.toJSONString(response));
            return R.fail(response.getErrorMsg());
        }
        List<EF01110003VO> eF01110003VOs = (List<EF01110003VO>) response.getResult().get("data");
        AssetDetailVO vo = new AssetDetailVO();
        vo.setAssetDetails(eF01110003VOs);
        // 获取港币保证金比例
        vo.setBondRatio(getEF01110180(fundAccount));
        return R.data(vo);
    }

    @Override
    public R getSingleAccount(SingleAccountRequest request) {
        return null;
    }

    @Override
    public R getUserPortfolio(UserPortfolioRequest request) {

        String fundAccount = request.getFundAccount();
        PositionDetailVO vo = new PositionDetailVO();

        // 获取持仓详情
        EF01110005Request ef01110005Request = new EF01110005Request();
        ef01110005Request.setSid(Constants.innerClientSideSid);
        ef01110005Request.setSessionId(Constants.innerClientSideSid);
        ef01110005Request.setFunctionId(GrmFunctions.BROKER_GET_CLIENT_HOLDING);
        ef01110005Request.setUserId(AuthUtil.getCustId());
        ef01110005Request.setFundAccount(fundAccount);
        ef01110005Request.setExchangeType(null);
        ef01110005Request.setIsFilter("0");
        //ef01110005Request.setStockCode(params.get("stockCode"));
        GrmResponseVO response = eF01110005.requestData(ef01110005Request);
		/*CacheEF01110005VO cache = zeroRedis.get(fundAccount);
		if (cache == null || CollectionUtils.isEmpty(cache.getEf01110005VOList())) {
			log.error("getHistoryOrders EF01110005, error=" + JSON.toJSONString(request));
			return R.fail();
		}
		EF01110005VO eF01110005VO = cache.getEf01110005VOList().get(0);
		BeanUtils.copyProperties(eF01110005VO,vo);
		*/
        // 获取持仓孖展
        EF01110186Request eF01110186Request = new EF01110186Request();
        eF01110186Request.setFunctionId(GrmFunctions.BROKER_GET_MARGIN_RATIO);
        eF01110186Request.setStockCode(request.getStockCode());
        GrmResponseVO response186 = eF01110186.requestData(eF01110186Request);
        if (!response186.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            log.error("getHistoryOrders EF01110186, error=" + JSON.toJSONString(response186));
            return R.fail(response186.getErrorMsg());
        }
        List<EF01110186VO> vos186s = (List<EF01110186VO>) response186.getResult().get("data");
        if (CollectionUtils.isNotEmpty(vos186s)) {
            Integer todayStr = Integer.valueOf(DateUtil.format(new Date(), "yyyyMMdd"));
            for (EF01110186VO vos186 : vos186s) {
                if (vos186.getInitDate() <= todayStr && vos186.getEndDate() >= todayStr) {
                    vo.setMarginRatio(vos186.getMarginRatio());
                    vo.setMarginValue(new BigDecimal(vo.getMarketValue()).multiply(vo.getMarginRatio()));
                    break;
                }
            }
        }
        return R.data(vo);
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
        EF01110004Request ef01110004Request = new EF01110004Request();
        ef01110004Request.setToMoneyType(moneyType);
        ef01110004Request.setFunctionId(GrmFunctions.BROKER_GET_CACHE_SUM_INFO);
        ef01110004Request.setFundAccount(fundAccount);

        GrmResponseVO sumVo = ef01110004.requestData(ef01110004Request);
        String totalAmount = "0";
        if (!sumVo.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            log.error("柜台服务失败：" + JSON.toJSONString(sumVo));
        }
        Map<String, ? extends Object> result = sumVo.getResult();
        if (result != null && result.get("data") != null) {
            List<Object> list = (List<Object>) result.get("data");
            if (list != null && !list.isEmpty()) {
                for (Object temp : list) {
                    ClientCashSumInfo resp = (ClientCashSumInfo) temp;
                    totalAmount = String.format("%.2f", Double.valueOf(resp.getFetchBalance()));
                }
            }
        }
        return R.data(totalAmount);
    }

    /**
     * 获取港币保证金比例
     */
    public EF01110180VO getEF01110180(String fundAccount) {
        EF01110180Request eF01110180Request = new EF01110180Request();
        eF01110180Request.setFunctionId(GrmFunctions.BROKER_GET_MV_RATIO);
        eF01110180Request.setFundAccount(fundAccount);
        eF01110180Request.setMoneyType(HsConstants.HsMoneyType.HKD.getMoneyType());
        GrmResponseVO res180 = eF01110180.requestData(eF01110180Request);
        if (!res180.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            log.error("柜台服务失败：" + JSON.toJSONString(res180));
        }
        Map<String, ? extends Object> result = res180.getResult();
        if (result != null && result.get("data") != null) {
            List<EF01110180VO> eF01110180VO = (List<EF01110180VO>) result.get("data");
            if (eF01110180VO != null && eF01110180VO.size() > 0) {
                return eF01110180VO.get(0);
            }
        }
        return null;
    }

    /**
     * 查询交易状态
     */
    @SneakyThrows
    private boolean tradeSwitch() {
        if (zeroRedis.get("Trade:Switch:".concat(GrmFunctions.SV_EXT_SYS_LOGIN)) != null) {
            return true;
        }
        EF01100100Request request = new EF01100100Request();
        request.setFunctionId(GrmFunctions.SV_EXT_SYS_LOGIN);
        GrmResponseVO responseVO = eF01100100.requestData(request);
        if (GrmConstants.GRM_RESPONSE_OK.equals(responseVO.getErrorId())) {
            EF01100100VO ef01100100VO = (EF01100100VO) responseVO.resultData().get(0);
            Date now = new Date();
            String todayStr = DateUtil.format(now, "yyyyMMdd");
            if (ef01100100VO.getInitDate().compareTo(todayStr) > 0) {
                //柜台时间大于当前时间，结算完成
                log.info("hs clear done, initDate={}", ef01100100VO.getInitDate());
                String switchEndTimeStr = ef01100100VO.getInitDate() + " 09:00:00";
                Date switchEndTime = DateUtil.parse(switchEndTimeStr, "yyyyMMdd HH:mm:ss");
                long timeoutSeconds = (switchEndTime.getTime() - now.getTime()) / 1000;
                zeroRedis.setEx("Trade:Switch:".concat(GrmFunctions.SV_EXT_SYS_LOGIN), GrmFunctions.SV_EXT_SYS_LOGIN,
                        timeoutSeconds);
                return true;
            }
        }
        return false;
    }

    /**
     * 获取港币保证金比例
     */
    public R getRiskLevel(String request) {

        Map<String, String> params = (Map) JSON.parse(request);

        String riskLevel = "A";
        EF01110180VO vo = new EF01110180VO();
        vo.setRiskLevel(riskLevel);
        vo.setMvRatio(BigDecimal.ZERO);
        vo.setMarginCall(BigDecimal.ZERO);
        EF01110180Request eF01110180Request = new EF01110180Request();
        eF01110180Request.setFunctionId(GrmFunctions.BROKER_GET_MV_RATIO);
        eF01110180Request.setFundAccount(params.get("capitalAccount"));
        eF01110180Request.setMoneyType(HsConstants.HsMoneyType.HKD.getMoneyType());
        GrmResponseVO res180 = eF01110180.requestData(eF01110180Request);
        if (!res180.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            log.error("柜台服务失败：" + JSON.toJSONString(res180));
        }
        Map<String, ? extends Object> result = res180.getResult();
        if (result != null && result.get("data") != null) {
            List<EF01110180VO> eF01110180VO = (List<EF01110180VO>) result.get("data");
            if (eF01110180VO != null && eF01110180VO.size() > 0) {
                vo = eF01110180VO.get(0);
                //A.【安全】：保证金比例<警示值，警示值由恒生柜台配置，该值可能会发生变动，因此要求该数值可灵活配置。
                //当前警示值设为：80% warn
                //B.【警示】：警示值<-保证金比例<追收值，追收值由恒生柜台配置，该值可能会发生调整，因此要求该数值可灵活配置。
                //当前追收值设为：100% recovery
                //C.【危险】：追收值＜-保证金比例<平仓值，平仓值由恒生柜台配置，该值可能会发生调整，因此要求该数值可灵活配置。
                //当前平仓值设为：120% closePosition
                //D.【平仓】：保证金比例>=平仓值。
                if (vo.getMvRatio() != null) {
                    if (vo.getMvRatio().compareTo(warn) <= 0) {
                        riskLevel = "A";
                    }
                    if (vo.getMvRatio().compareTo(warn) > 0 || vo.getMvRatio().compareTo(recovery) <= 0) {
                        riskLevel = "B";
                    }
                    if (vo.getMvRatio().compareTo(recovery) > 0 || vo.getMvRatio().compareTo(closePosition) <= 0) {
                        riskLevel = "C";
                    }
                    if (vo.getMvRatio().compareTo(closePosition) >= 0) {
                        riskLevel = "D";
                    }
                }
                vo.setRiskLevel(riskLevel);
            }
        }
        return R.data(vo);
    }

    @Override
    public R getOrdersFee(HistoryOrdersVO request) {


        EF01110181Request req = new EF01110181Request();
        req.setFunctionId(GrmFunctions.BROKER_HISTORY_ENTRUST_DETAIL);
        req.setFundAccount(request.getFundAccount());
        req.setExchangeType(HSUtil.getMarketToHS(request.getAssetId()));
        req.setStockCode(HSUtil.getStockCode(request.getAssetId()));
        req.setStartDate(request.getStartDate());
        req.setEndDate(request.getEndDate());
        req.setStartDate("20150101");
        req.setEndDate(DateUtil.format(new Date(), "yyyyMMdd"));
        GrmResponseVO response = eF01110181.requestData(req);
        if (!response.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            log.error("getOrdersFee eF01110181, error=" + JSON.toJSONString(response));
            return R.fail(response.getErrorMsg());
        }
        Map<String, ? extends Object> result = response.getResult();
        return R.data(result == null ? null : result.get("data"));




		/*
		EF01110149Request req = new EF01110149Request();
		req.setFunctionId(GrmFunctions.BROKER_GET_HIS_BARGAIN_INFO);
		req.setFundAccount(request.getCapitalAccount());
		req.setExchangeType(HSUtil.getMarketToHS(request.getAssetId()));
		req.setStockCode(HSUtil.getStockCode(request.getAssetId()));
		req.setStartDate(request.getStartDate());
		req.setEndDate(request.getEndDate());
		req.setStartDate("20150101");
		req.setEndDate(DateUtil.formatDate(new Date(),"yyyyMMdd"));
		GrmResponseVO response = eF01110149.requestData(req);
		if (!response.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
			log.error("getOrdersFee eF01110149, error=" + JSON.toJSONString(response));
			return R.fail(response.getErrorMsg());
		}
		Map<String,? extends Object>  result = response.getResult();
		return R.data(result.get("data"));
		 */


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
		/**
		 * 查询柜台 放入redis(过期时间6小时)
		 */
		return R.success();
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
