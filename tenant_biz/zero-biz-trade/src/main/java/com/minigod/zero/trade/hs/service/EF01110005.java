package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.core.secure.utils.AuthUtil;
import com.minigod.zero.core.tool.api.R;
import com.minigod.zero.core.tool.beans.QuotationVO;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.core.tool.constant.MktConstants;
import com.minigod.zero.core.tool.jackson.JsonUtil;
import com.minigod.zero.cust.apivo.CustAccountVO;
import com.minigod.zero.cust.feign.ICustTradeClient;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.EF01110005Request;
import com.minigod.zero.trade.hs.req.EF01110111Request;
import com.minigod.zero.trade.hs.resp.*;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import com.minigod.zero.trade.utils.HSUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 获取客户EF01110005查股票
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Slf4j
@Component
@AllArgsConstructor
@GrmFunctionEntity(requestVo = EF01110005Request.class, responseVo = EF01110005VO.class)
public class EF01110005<T extends GrmRequestVO> extends T2sdkBiz<T> {
	private final ZeroRedis zeroRedis;
	private final GrmCacheMgr grmCacheMgr;
	private final EF01110111 eF01110111;
	private final ICustTradeClient custTradeClient;

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110005Request) {
            EF01110005Request req = (EF01110005Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            //由于需要手工计算总资产和总市值，这里需要查询所有持仓，所以不需要传exchangeType到柜台，如果客户需要根据exchangeType过滤，则从全部持仓结果里过滤
            if (StringUtils.isNotEmpty(req.getStockCode())) {
                reqMap.put(HsConstants.Fields.STOCK_CODE, req.getStockCode());
            }
            reqMap.put(HsConstants.Fields.QUERY_MODE, "1"); // 查询模式0 明细 1 汇总
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            EF01110005Request ef01110005Request = (EF01110005Request) request;
            List<Map<String, String>> rowMapList = grmDataVO.getResult();
            Map<String, QuotationVO> quotationVOMap = getQuotationVOMap(ef01110005Request, rowMapList);//取实时行情和延时行情
            //cacheHoldStockList(ef01110005Request, rowMapList, quotationVOMap);//缓存用户持仓记录到redis中
            String sessionId = request.getSessionId();
            String isFilter = ef01110005Request.getIsFilter();
            Iterator<Map<String, String>> iterator = rowMapList.iterator();
            List<Object> listVO = new ArrayList<>();
            EF01110005VO vo;
            String currentAmount;
            String lastPrice;
            String costPrice;
            double incomeBalance;
            double cost;
            StkInfo assetInfo;
            Double priceFromRedis;
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                vo = new EF01110005VO();
                currentAmount = rowMap.get(HsConstants.Fields.CURRENT_AMOUNT);
                vo.setExchangeType(rowMap.get(HsConstants.Fields.EXCHANGE_TYPE));
                vo.setStockCode(rowMap.get(HsConstants.Fields.STOCK_CODE));
                if (StringUtils.isNotBlank(ef01110005Request.getExchangeType())) {
                    if (!ef01110005Request.getExchangeType().contains(vo.getExchangeType())) {
                        //持仓股票的exchangeType不在前端入参的exchangeType范围内
                        continue;
                    }
                } else {
                    //前端入参的exchangeType为空的情况
                    if (HsConstants.HSExchageType.FUND.getCode().equals(vo.getExchangeType())) {
                        continue;//入参市场类型为空的，过滤掉基金持仓--智珠宝基金等
                    }
                }
                double realSellAmount = NumberUtils.toDouble(rowMap.get(HsConstants.Fields.REAL_SELL_AMOUNT), 0.0d);
                double currentAmountDbl = NumberUtils.toDouble(currentAmount, 0.0d);
                if (realSellAmount == 0.0d && currentAmountDbl == 0.0d) {
                    continue;
                }
                if (HsConstants.HSExchageType.SZ.getCode().equals(vo.getExchangeType())) {
                    vo.setExchangeType("SZ");
                }
                if (HsConstants.HSExchageType.SH.getCode().equals(vo.getExchangeType())) {
                    vo.setExchangeType("SH");
                }
                String assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(vo.getStockCode(), vo.getExchangeType()), vo.getExchangeType());
                // 中华通持仓过滤
                /*if ("1".equals(isFilter) && (!assetId.endsWith("SZ") && !assetId.endsWith("SH"))) {
                    continue;
                }*/
                vo.setAssetId(assetId);
                assetInfo = grmCacheMgr.getAssetInfo(assetId);
                if (null != assetInfo) {
                    vo.setStockName(rowMap.get(HsConstants.Fields.STOCK_NAME));
                    vo.setStockNameZhCN(assetInfo.getStkNameLong());
                    vo.setStockNameZhHK(assetInfo.getTraditionalName());
                    //添加每股手数,和股票子类型
                    vo.setLotSize(assetInfo.getLotSize());
                    vo.setSecSType(assetInfo.getSecSType());
                }
                QuotationVO quotationVO = quotationVOMap.get(assetId);//获取最新价
                if (quotationVO != null) {
                    priceFromRedis = quotationVO.getPrice().doubleValue();
                    log.info("quotationVO not null, assetId={}, lastPrice={}", assetId, priceFromRedis);
                    lastPrice = fieldFormater.format(HsConstants.Fields.AV_BUY_PRICE, String.valueOf(priceFromRedis));
                } else {
                    lastPrice = rowMap.get(HsConstants.Fields.LAST_PRICE);
                    log.info("quotationVO null, assetId={}, lastPrice={}", assetId, lastPrice);
                }
                costPrice = rowMap.get(HsConstants.Fields.KEEP_COST_PRICE);
                vo.setCurrentQty(currentAmount);
                vo.setCostPrice(costPrice);
                vo.setEnableQty(rowMap.get(HsConstants.Fields.ENABLE_AMOUNT));
                //重新计算市值
                double lastPriceD = NumberUtils.toDouble(lastPrice);
                BigDecimal marketValue = BigDecimal.valueOf(currentAmountDbl * lastPriceD).setScale(2, RoundingMode.HALF_UP);
                vo.setMarketValue(marketValue.toString());
                vo.setLastPrice(lastPrice);
                vo.setMoneyType(rowMap.get(HsConstants.Fields.MONEY_TYPE));
                vo.setAvBuyPrice(rowMap.get(HsConstants.Fields.AV_BUY_PRICE));
                vo.setAssetId(assetId);
                if (Double.valueOf(currentAmount).compareTo(0.0d) == 0) {
                    if (realSellAmount > 0.0d) {
                        // 当天清空
                        vo.setIncomeBalance(rowMap.get(HsConstants.Fields.INCOME_BALANCE));
                        vo.setIncomeRatio("--");
                    } else {
                        // 非当天清空
                        continue;
                    }
                } else if (StringUtils.isNotEmpty(costPrice) && Double.valueOf(costPrice).compareTo(0.0) > 0) {
                    cost = Double.parseDouble(costPrice) * Double.parseDouble(currentAmount);
                    incomeBalance = Double.parseDouble(lastPrice) * Double.parseDouble(currentAmount) - cost;
                    vo.setIncomeBalance(fieldFormater.format(HsConstants.Fields.INCOME_BALANCE, String.valueOf(incomeBalance)));
                    vo.setIncomeRatio(fieldFormater.format(HsConstants.Fields.INCOME_RATIO, String.valueOf(incomeBalance / cost)));
                } else {
                    //modify sunline by 2018-01-15 修复成本价为负数盈亏金额的bug
                    vo.setIncomeBalance(rowMap.get(HsConstants.Fields.INCOME_BALANCE));
                    vo.setIncomeRatio(String.valueOf(1));
                }
                //判断当前用户是否是专业用户
                try {
					/** TODO
                    boolean isInvestor = userInvestorStmtService.isIsInvestor(request.getSessionId(), null);
                    if (isInvestor && assetId.endsWith(Constants.MarketCode.MARKET_CODE_US)) {
                        vo.setMarketValue("--");
                        vo.setLastPrice("--");
                        vo.setIncomeBalance("--");
                        vo.setIncomeRatio("--");
                    }
					 */
                } catch (Exception e) {
                    log.error("query Investor error", e);
                }
                if (StringUtils.isEmpty(vo.getStockName())) {
                    vo.setStockName(rowMap.get(HsConstants.Fields.STOCK_NAME));
                }
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

    /**
     * 缓存用户持仓记录到redis中
     */
    private void cacheHoldStockList(EF01110005Request ef01110005Request, List<Map<String, String>> rowMapList, Map<String, QuotationVO> quotationVOMap) {
		// 缓存持仓信息到redis
        CacheEF01110005VO cacheEF01110005VO = new CacheEF01110005VO();
        if (CollectionUtils.isNotEmpty(rowMapList)) {
            List<EF01110005VO> ef01110005VOList = new ArrayList<>(rowMapList.size());
			//获取当日成交数据
			Map<String,EF01110111VO> todayTrade = getTodayTrade(ef01110005Request);
			for (Map<String, String> rowMap : rowMapList) {
                EF01110005VO ef01110005VO = JSON.parseObject(JSON.toJSONString(rowMap),EF01110005VO.class);
				ef01110005VO.setCostPrice(ef01110005VO.getKeepCostPrice());
				if (new BigDecimal(ef01110005VO.getRealSellAmount()).compareTo(BigDecimal.ZERO) == 0 && new BigDecimal(ef01110005VO.getCurrentQty()).compareTo(BigDecimal.ZERO) == 0) {
					continue;
				}
				String assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(ef01110005VO.getStockCode(), ef01110005VO.getExchangeType()), ef01110005VO.getExchangeType());
				ef01110005VO.setAssetId(assetId);

				// 不是A股，美股，港股的市场持仓归类到其他市场，直接返回恒生返回的数据
				if(!HSUtil.isOtherExchage(ef01110005VO.getExchangeType())){
					QuotationVO quotationVO = quotationVOMap.get(assetId);
					// 获取到行情，最新价按获取到的行情，否则是恒生返回的行情
					if (quotationVO != null) {
						ef01110005VO.setLastPrice(fieldFormater.format(HsConstants.Fields.AV_BUY_PRICE, String.valueOf(quotationVO.getPrice())));
						ef01110005VO.setClosePrice(quotationVO.getPrevClose());
					}
					// 市值 = 最新价 * 当前数量
					ef01110005VO.setMarketValue(new BigDecimal(ef01110005VO.getLastPrice()).multiply(new BigDecimal(ef01110005VO.getCurrentQty())).toPlainString());
					// 计算各市场持仓市值
					countMarketValue(ef01110005VO, cacheEF01110005VO);

					// 计算持仓盈亏和持仓盈亏率
					// 1.持仓盈亏 = (现价-成本) x 持仓数量
					ef01110005VO.setIncomeBalance((new BigDecimal(ef01110005VO.getLastPrice()).subtract(new BigDecimal(ef01110005VO.getCostPrice()))).multiply(new BigDecimal(ef01110005VO.getCurrentQty())).setScale(2, RoundingMode.HALF_UP).toString());
					// 2.持仓盈亏率 = (现价-成本) / 成本价  当成本价为负数时，盈亏率计算结果无意义，因此展示为0.00%
					if(new BigDecimal(ef01110005VO.getCostPrice()).compareTo(BigDecimal.ZERO) < 1){
						ef01110005VO.setIncomeRatio("0");
					}else{
						ef01110005VO.setIncomeRatio((new BigDecimal(ef01110005VO.getLastPrice()).subtract(new BigDecimal(ef01110005VO.getCostPrice()))).divide(new BigDecimal(ef01110005VO.getCostPrice()),5).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP).toString());
					}

					// 计算今日盈亏和今日盈亏率
					EF01110111VO todayTradeBuy = todayTrade.get(ef01110005VO.getStockCode() + "1");
					EF01110111VO todayTradeSell = todayTrade.get(ef01110005VO.getStockCode() + "2");
					BigDecimal buyAmount = BigDecimal.ZERO;
					BigDecimal sellAmount = BigDecimal.ZERO;
					BigDecimal buyBalance = BigDecimal.ZERO;
					BigDecimal sellBalance = BigDecimal.ZERO;
					if(todayTradeBuy != null && todayTradeBuy.getBusinessAmount() != null){
						buyAmount = todayTradeBuy.getBusinessAmount();
					}
					if(todayTradeSell != null && todayTradeSell.getBusinessAmount() != null){
						sellAmount = todayTradeBuy.getBusinessAmount();
					}
					if(todayTradeBuy != null && todayTradeBuy.getBusinessBalance() != null){
						buyBalance = todayTradeBuy.getBusinessBalance();
					}
					if(todayTradeSell != null && todayTradeSell.getBusinessBalance() != null){
						sellBalance = todayTradeBuy.getBusinessBalance();
					}
					// 昨日市值 = (当前数量+回报卖出数量-回报买入数量)*昨收市价
					BigDecimal yesterdayMarketValue = (new BigDecimal(ef01110005VO.getCurrentQty()).add(sellAmount).subtract(buyAmount)).multiply(ef01110005VO.getClosePrice());
					// 1.今日盈亏 = (今日市值 - 昨日市值) + (今日卖出成交额 - 今日买入成交额)
					ef01110005VO.setTodayIncome(new BigDecimal(ef01110005VO.getMarketValue()).subtract(yesterdayMarketValue).add(sellBalance).subtract(buyBalance).setScale(2, RoundingMode.HALF_UP).toString());
					// 2.今日盈亏率 = 今日盈亏/(昨日市值 + 今日买入成交额)
					ef01110005VO.setTodayIncomeRatio(new BigDecimal(ef01110005VO.getTodayIncome()).divide((yesterdayMarketValue.add(buyBalance)),5).multiply(new BigDecimal("100")).setScale(2, RoundingMode.HALF_UP).toString());
					ef01110005VO.setYesterdayMarketValue(yesterdayMarketValue.toString());
				}
				ef01110005VO.setStockCode(DataFormatUtil.stockCodeFormat(ef01110005VO.getStockCode(), ef01110005VO.getExchangeType()));
                ef01110005VOList.add(ef01110005VO);
            }
            cacheEF01110005VO.setEf01110005VOList(ef01110005VOList);
        }
        log.info("cacheEF01110005VO:{}", JsonUtil.toJson(cacheEF01110005VO));
		zeroRedis.set( ef01110005Request.getFundAccount(),cacheEF01110005VO);
    }

    /**
     * 计算汇总各市场持仓市值
     */
    private void countMarketValue(EF01110005VO ef01110005VO, CacheEF01110005VO cacheEF01110005VO) {
		HsExchangeRateMapVO hkRateMapVO = grmCacheMgr.getHsExchangeRateMapVO(HsConstants.HsMoneyType.HKD.getMoneyType());//兑港币汇率
		HsExchangeRateMapVO usRateMapVO = grmCacheMgr.getHsExchangeRateMapVO(HsConstants.HsMoneyType.USD.getMoneyType());//兑美元汇率
        BigDecimal marketValue = new BigDecimal(ef01110005VO.getMarketValue());
        //计算各市场持仓市值
        if (HsConstants.HSExchageType.HK.getCode().equals(ef01110005VO.getExchangeType())) {
            //港股市场
            BigDecimal singleHkMarketValue;
            if (HsConstants.HsMoneyType.HKD.getMoneyType().equals(ef01110005VO.getMoneyType())) {
                singleHkMarketValue = marketValue;
            } else {
                //港股市场非港币持仓，汇率转换
                HsExchangeRateVO rateVO = hkRateMapVO.getExchangeRateMap().get(ef01110005VO.getMoneyType());
                if (rateVO != null) {
                    singleHkMarketValue = marketValue.multiply(rateVO.getExchangeRate());
                } else {
                    singleHkMarketValue = marketValue;
                    log.warn("HsExchangeRateVO is null, fundAccount={}, assetId={}, fromMoneyType={}",
                            cacheEF01110005VO.getFundAccount(), ef01110005VO.getAssetId(), ef01110005VO.getMoneyType());
                }
            }
            BigDecimal hkMarketValue = cacheEF01110005VO.getHkMarketValue().add(singleHkMarketValue);
            cacheEF01110005VO.setHkMarketValue(hkMarketValue);
        } else if (HsConstants.HSExchageType.US.getCode().equals(ef01110005VO.getExchangeType())) {
            //美股市场
            BigDecimal singleUsMarketValue;
            if (HsConstants.HsMoneyType.USD.getMoneyType().equals(ef01110005VO.getMoneyType())) {
                singleUsMarketValue = marketValue;
            } else {
                //美股市场非港币持仓，汇率转换
                HsExchangeRateVO rateVO = usRateMapVO.getExchangeRateMap().get(ef01110005VO.getMoneyType());
                if (rateVO != null) {
                    singleUsMarketValue = marketValue.multiply(rateVO.getExchangeRate());
                } else {
                    singleUsMarketValue = marketValue;
					log.warn("HsExchangeRateVO is null, fundAccount={}, assetId={}, fromMoneyType={}",
                            cacheEF01110005VO.getFundAccount(), ef01110005VO.getAssetId(), ef01110005VO.getMoneyType());
                }
            }
            BigDecimal usMarketValue = cacheEF01110005VO.getUsMarketValue().add(singleUsMarketValue);
            cacheEF01110005VO.setUsMarketValue(usMarketValue);
        }
        if (HsConstants.HSExchageType.SH.getCode().equals(ef01110005VO.getExchangeType()) || HsConstants.HSExchageType.SZ.getCode().equals(ef01110005VO.getExchangeType())) {
            //A股市场
            BigDecimal aMarketValue = cacheEF01110005VO.getAMarketValue().add(marketValue);
            cacheEF01110005VO.setAMarketValue((aMarketValue));
        }
        BigDecimal marketValue2HKD = marketValue;
        if (!HsConstants.HsMoneyType.HKD.getMoneyType().equals(ef01110005VO.getMoneyType())) {
            //非港币持仓，汇率转换
            HsExchangeRateVO rateVO = hkRateMapVO.getExchangeRateMap().get(ef01110005VO.getMoneyType());
            if (rateVO != null) {
                marketValue2HKD = marketValue2HKD.multiply(rateVO.getExchangeRate());
            } else {
                log.warn("HsExchangeRateVO is null, fundAccount={}, fromMoneyType={}", cacheEF01110005VO.getFundAccount(), ef01110005VO.getMoneyType());
            }
        }
        BigDecimal totalMarketValue2HKD = cacheEF01110005VO.getTotalMarketValue2HKD();
        if (!HsConstants.HSExchageType.SH.getCode().equals(ef01110005VO.getExchangeType())
                && !HsConstants.HSExchageType.SZ.getCode().equals(ef01110005VO.getExchangeType())) {
            //非A股股票，累加到totalMarketValue2HKD
            totalMarketValue2HKD = totalMarketValue2HKD.add(marketValue2HKD);
        }
        cacheEF01110005VO.setTotalMarketValue2HKD(totalMarketValue2HKD);
    }

    /**
     * 处理实时行情和延时行情报价
     */
    private Map<String, QuotationVO> getQuotationVOMap(GrmRequestVO requestVO, List<Map<String, String>> rowMapList) {
        Map<String, QuotationVO> quotationVOMap;
        if (CollectionUtils.isNotEmpty(rowMapList)) {
            //高于2.8.0的版本才开放美股盘前盘后业务
            //boolean isHighVersion = grmCacheMgr.isHighVersion(requestVO.getDeviceId(), "2.8.0");
        	//资产信息界面，即一级页是通过user，调用003接口获得的，没有设备号，获取的行情是收盘价
        	//二级页是app直接调用的，有设备号，获取的行情是盘前或者盘后的，最终导致资产不一致
        	//因为现在版本都是高于2.8.0的，所以直接给true
            boolean isHighVersion = true;
            // 处理港美股实时行情，延时行情报价
            // 港股行情assetId
            List<String> hkAssetList = new ArrayList<>();
            // 美股行情assetId
            List<String> usAssetList = new ArrayList<>();
            // 上海行情assetId
            List<String> shAssetList = new ArrayList<>();
            // 深圳行情assetId
            List<String> szAssetList = new ArrayList<>();
            List<QuotationVO> quotationVOList = new ArrayList<>(rowMapList.size());
            quotationVOMap = new HashMap<>(rowMapList.size());
            for (Map<String, String> rowMap : rowMapList) {
                String exchangeType = rowMap.get(HsConstants.Fields.EXCHANGE_TYPE);
                String stockCode = rowMap.get(HsConstants.Fields.STOCK_CODE);
                String assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(stockCode, exchangeType), exchangeType);
                if (HsConstants.HSExchageType.HK.getCode().equals(exchangeType)) {
                    hkAssetList.add(assetId);
                } else if (HsConstants.HSExchageType.US.getCode().equals(exchangeType)) {
                    usAssetList.add(assetId);
                }
                if (HsConstants.HSExchageType.SH.getCode().equals(exchangeType)) {
                    shAssetList.add(assetId);
                }
                if (HsConstants.HSExchageType.SZ.getCode().equals(exchangeType)) {
                    szAssetList.add(assetId);
                }
            }
            //港股实时行情，延时行情处理
            handleQuotation(requestVO, hkAssetList, MktConstants.Market.HK, quotationVOList, isHighVersion);
            //美股实时行情，延时行情处理
            handleQuotation(requestVO, usAssetList, MktConstants.Market.US, quotationVOList, isHighVersion);
            //取上海市场实时行情报价
            if (CollectionUtils.isNotEmpty(shAssetList)) {
                List<QuotationVO> shList = getQuotationVOList(shAssetList, MktConstants.Market.SH, requestVO, true, isHighVersion);
                if (CollectionUtils.isNotEmpty(shList)) {
                    quotationVOList.addAll(shList);
                }
            }
            //取实深圳市场时行情报价
            if (CollectionUtils.isNotEmpty(szAssetList)) {
                List<QuotationVO> szList = getQuotationVOList(szAssetList, MktConstants.Market.SZ, requestVO, true, isHighVersion);
                if (CollectionUtils.isNotEmpty(szList)) {
                    quotationVOList.addAll(szList);
                }
            }
            if (CollectionUtils.isNotEmpty(quotationVOList)) {
                for (QuotationVO quotationVO : quotationVOList) {
                    if (quotationVO != null) {
                        quotationVOMap.put(quotationVO.getAssetId(), quotationVO);
                    }
                }
            }
        } else {
            quotationVOMap = new HashMap<>(0);
        }

        return quotationVOMap;
    }

    /**
     * 处理延时行情报价
     */
    private void handleQuotation(GrmRequestVO requestVO, List<String> assetIdList, String market, List<QuotationVO> quotationVOList, boolean isHighVersion) {
        if (CollectionUtils.isEmpty(assetIdList)) {
            return;
        }
        //延时行情分界线，前20条实时，后面的延时
        int limit = 20;
        if (assetIdList.size() <= limit) {
            //取实时行情报价
            List<QuotationVO> list = getQuotationVOList(assetIdList, market, requestVO, true, isHighVersion);
            if (CollectionUtils.isNotEmpty(list)) {
                quotationVOList.addAll(list);
            }
        } else {
            boolean isRealQuotation = false;
			/** TODO
            if (MktConstants.Market.HK.equals(market)) {
                isRealQuotation = MktmgrUtil.isRealQuota(requestVO.getSessionId());
            } else if (MktConstants.Market.US.equals(market)) {
                isRealQuotation = MktmgrUtil.isRealUsQuota(requestVO.getSessionId());
            }
			 */
            if (isRealQuotation) {
                //取实时行情报价
                List<QuotationVO> list = getQuotationVOList(assetIdList, market, requestVO, true, isHighVersion);
                if (CollectionUtils.isNotEmpty(list)) {
                    quotationVOList.addAll(list);
                }
            } else {
                //取延时行情报价
				log.info("getDelayQuotationVO, userId={}, market={}", requestVO.getUserId(), market);
                List<String> partRealAssetIds = assetIdList.subList(0, limit);
                List<QuotationVO> list1 = getQuotationVOList(partRealAssetIds, market, requestVO, true, isHighVersion);
                if (CollectionUtils.isNotEmpty(list1)) {
                    quotationVOList.addAll(list1);
                }
                List<String> partDelayAssetIds = assetIdList.subList(limit, assetIdList.size());
                List<QuotationVO> list2 = getQuotationVOList(partDelayAssetIds, market, requestVO, false, isHighVersion);
                if (CollectionUtils.isNotEmpty(list2)) {
                    quotationVOList.addAll(list2);
                }
            }
        }
    }

    /**
     * 获取点击报价行情
     */
    private List<QuotationVO> getQuotationVOList(List<String> assetIds, String mktCode,
                                                 GrmRequestVO requestVO,
                                                 boolean isRealQuotation,
                                                 boolean isHighVersion) {
        QuotAndLogWriteReqVo quotAndLogWriteReqVo = new QuotAndLogWriteReqVo();
        try {
            if (requestVO.getUserId() != null) {
                quotAndLogWriteReqVo.setUserId(Long.valueOf(requestVO.getUserId()));
            } else {
                log.warn("getQuotationVOList failed, userId is null, sessionId={}", requestVO.getSessionId());
              //return new ArrayList<>(0);  // 这里直接返回，导致无法获取到行情，一定要传入userId。但是userId这里看起来不是必须的。
            }
            quotAndLogWriteReqVo.setSessionId(requestVO.getSessionId());
            quotAndLogWriteReqVo.setIp(requestVO.getIpAddress());
            quotAndLogWriteReqVo.setAssetIds(assetIds);
            quotAndLogWriteReqVo.setMktCode(mktCode);
            if (MktConstants.Market.US.equals(mktCode) && isHighVersion) {
                //美股设置自动获取盘前盘后行情
                quotAndLogWriteReqVo.setUsAutoChange(true);
            }
            quotAndLogWriteReqVo.setDealy(!isRealQuotation);
            //记录点击报价
            // TODO return (List<QuotationVO>) quotationServiceNew.findQuotByMktCode(quotAndLogWriteReqVo);
        } catch (Exception e) {
            log.error("getQuotationVOList error", e);
        }
        return null;
    }

	/**
	 * 获取当日成交数据
	 */
	private Map<String,EF01110111VO> getTodayTrade(EF01110005Request ef01110005Request) {
		Map<String,EF01110111VO> todayTrade = new HashMap<>();
		R result = custTradeClient.custCurrentAccount(AuthUtil.getCustId());
		if (!result.isSuccess()) {
			return todayTrade;
		}
		CustAccountVO custAccount = (CustAccountVO) result.getData();

		EF01110111Request eF01110111Request = new EF01110111Request();
		eF01110111Request.setSid(Constants.innerClientSideSid);
		eF01110111Request.setSessionId(Constants.innerClientSideSid);
		eF01110111Request.setFunctionId(GrmFunctions.BROKER_ENTRUST_CURRFILL);
		eF01110111Request.setFundAccount(ef01110005Request.getFundAccount());
		eF01110111Request.setClientId(custAccount.getTradeAccount());
		eF01110111Request.setQueryMode("1");
		GrmResponseVO response = eF01110111.requestData(eF01110111Request);
		if (!response.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
			log.error("getTodayTrade EF01110111, error=" + JSON.toJSONString(response));
			return todayTrade;
		}
		List<EF01110111VO> todayTradeVOs = (List<EF01110111VO>) response.getResult();
		if(todayTradeVOs != null){
			for(EF01110111VO vo : todayTradeVOs){
				todayTrade.put(vo.getStockCode() + vo.getBsFlag(),vo);
			}
		}
		return todayTrade;
	}

}
