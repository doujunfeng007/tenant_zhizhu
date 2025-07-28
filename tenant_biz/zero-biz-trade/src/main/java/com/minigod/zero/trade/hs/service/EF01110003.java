package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson2.JSON;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.GrmFunctions;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110003Request;
import com.minigod.zero.trade.hs.req.EF01110005Request;
import com.minigod.zero.trade.hs.resp.*;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import com.minigod.zero.trade.utils.HSUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 获取客户资金信息,经纪模式
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Slf4j
@Component
@AllArgsConstructor
@GrmFunctionEntity(requestVo = EF01110003Request.class, responseVo = EF01110003VO.class)
public class EF01110003<T extends GrmRequestVO> extends T2sdkBiz<T> {
	private final ZeroRedis zeroRedis;
    private final EF01110005 ef01110005;

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110003Request) {
            EF01110003Request req = (EF01110003Request) request;
            Map<String, String> reqMap = new HashMap<String, String>();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            if (StringUtils.isNotEmpty(req.getMoneyType())) {
                reqMap.put(HsConstants.Fields.MONEY_TYPE, req.getMoneyType());
            }
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @SuppressWarnings("unchecked")
	@Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            EF01110003Request req = (EF01110003Request) request;

			List<EF01110003VO> vos = new ArrayList<>();
			List<Map<String, String>> list = grmDataVO.getResult();
			Iterator<Map<String, String>> iterator = list.iterator();

			// 不获取持仓明细重算，只返回柜台各市场资金数据
			if(!req.isQueryPositions()){
				while (iterator.hasNext()) {
					Map<String, String> rowMap = iterator.next();
					EF01110003VO vo = JSON.parseObject(JSON.toJSONString(rowMap), EF01110003VO.class);
					vo.setFrozenBalance((new BigDecimal(vo.getFrozenBalance()).add(new BigDecimal(vo.getCashOnHold()))).toString());
					vo.setUnsettBalance(vo.getUncomeSellBalance().add(vo.getRealSellBalance()).subtract(vo.getUncomeBuyBalance()).subtract(vo.getRealBuyBalance()));
					vos.add(vo);
				}
				responseVO.setResult(vos);
				return responseVO;
			}
			//兑港币汇率
			HsExchangeRateMapVO hkRateMapVO = grmCacheMgr.getHsExchangeRateMapVO(HsConstants.HsMoneyType.HKD.getMoneyType());
            //获取重新计算的单市场市值
            CacheEF01110005VO cacheEF01110005VO = getCachedMarketValue(req);
			// 持仓明细按币种分类
			Map<String,List<EF01110005VO>> positions = new HashMap<String,List<EF01110005VO>>();
			// 统计每类币种的总市值、持仓盈亏、今日盈亏、昨日市值、持仓成本
			BigDecimal hkMarketValue = BigDecimal.ZERO;
        	BigDecimal usMarketValue = BigDecimal.ZERO;
        	BigDecimal cnyMarketValue = BigDecimal.ZERO;
			BigDecimal otherMarketValue = BigDecimal.ZERO;
			BigDecimal hkIncomeBalance = BigDecimal.ZERO;
			BigDecimal usIncomeBalance = BigDecimal.ZERO;
			BigDecimal cnyIncomeBalance = BigDecimal.ZERO;
			BigDecimal hkTodayIncome = BigDecimal.ZERO;
			BigDecimal usTodayIncome = BigDecimal.ZERO;
			BigDecimal cnyTodayIncome = BigDecimal.ZERO;
			BigDecimal hkYesterdayMarketValue = BigDecimal.ZERO;
			BigDecimal usYesterdayMarketValue = BigDecimal.ZERO;
			BigDecimal cnyYesterdayMarketValue = BigDecimal.ZERO;
			BigDecimal hkCostMarketValue = BigDecimal.ZERO;
			BigDecimal usCostMarketValue = BigDecimal.ZERO;
			BigDecimal cnyCostMarketValue = BigDecimal.ZERO;
            if(cacheEF01110005VO  != null && cacheEF01110005VO.getEf01110005VOList() != null){
				String moneyType = StringUtils.EMPTY;
            	for(EF01110005VO eF01110005VO : cacheEF01110005VO.getEf01110005VOList()){
					// 统计每类币种的总市值
					if(!HSUtil.isOtherExchage(eF01110005VO.getExchangeType())){
						moneyType = eF01110005VO.getMoneyType();
						if(HsConstants.HsMoneyType.CNY.getMoneyType().equals(eF01110005VO.getMoneyType())){
							cnyMarketValue = cnyMarketValue.add(new BigDecimal(eF01110005VO.getMarketValue()));
							cnyIncomeBalance = cnyIncomeBalance.add(new BigDecimal(eF01110005VO.getIncomeBalance()));
							cnyTodayIncome = cnyTodayIncome.add(new BigDecimal(eF01110005VO.getTodayIncome()));
							cnyYesterdayMarketValue = cnyYesterdayMarketValue.add(new BigDecimal(eF01110005VO.getYesterdayMarketValue()));
							cnyCostMarketValue = cnyCostMarketValue.add(new BigDecimal(eF01110005VO.getCostPrice()).multiply(new BigDecimal(eF01110005VO.getCurrentQty())));
						}
						if(HsConstants.HsMoneyType.USD.getMoneyType().equals(eF01110005VO.getMoneyType())){
							usMarketValue = usMarketValue.add(new BigDecimal(eF01110005VO.getMarketValue()));
							usIncomeBalance = usIncomeBalance.add(new BigDecimal(eF01110005VO.getIncomeBalance()));
							usTodayIncome = usTodayIncome.add(new BigDecimal(eF01110005VO.getTodayIncome()));
							usYesterdayMarketValue = usYesterdayMarketValue.add(new BigDecimal(eF01110005VO.getYesterdayMarketValue()));
							usCostMarketValue = usCostMarketValue.add(new BigDecimal(eF01110005VO.getCostPrice()).multiply(new BigDecimal(eF01110005VO.getCurrentQty())));
						}
						if(HsConstants.HsMoneyType.HKD.getMoneyType().equals(eF01110005VO.getMoneyType())){
							hkMarketValue = hkMarketValue.add(new BigDecimal(eF01110005VO.getMarketValue()));
							hkIncomeBalance = hkIncomeBalance.add(new BigDecimal(eF01110005VO.getIncomeBalance()));
							hkTodayIncome = hkTodayIncome.add(new BigDecimal(eF01110005VO.getTodayIncome()));
							hkYesterdayMarketValue = hkYesterdayMarketValue.add(new BigDecimal(eF01110005VO.getYesterdayMarketValue()));
							hkCostMarketValue = hkCostMarketValue.add(new BigDecimal(eF01110005VO.getCostPrice()).multiply(new BigDecimal(eF01110005VO.getCurrentQty())));
						}
					}else{
						moneyType = HsConstants.HsMoneyType.OTH.getMoneyType();
						// 其他市场总市值按港币显示
						HsExchangeRateVO rateVO = hkRateMapVO.getExchangeRateMap().get(eF01110005VO.getMoneyType());
						if(StringUtils.isNotBlank(eF01110005VO.getMarketValue())){
							if(rateVO != null && rateVO.getExchangeRate() != null){
								otherMarketValue = otherMarketValue.add(new BigDecimal(eF01110005VO.getMarketValue()).multiply(rateVO.getExchangeRate()));
							}else{
								otherMarketValue = otherMarketValue.add(new BigDecimal(eF01110005VO.getMarketValue()));
							}
						}
					}
					// 持仓明细按币种分类,对人名币、美元、港币进行分类，其他币种放到一类
					if(positions.get(moneyType) == null){
						List<EF01110005VO> eF01110005VOs = new ArrayList<EF01110005VO>();
						eF01110005VOs.add(eF01110005VO);
						positions.put(moneyType, eF01110005VOs);
					}else{
						positions.get(moneyType).add(eF01110005VO);
					}
            	}
            }

			// 除人民币，美元，港币以外，其他市场，只返回一条记录即可
			boolean other = true;
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
				EF01110003VO vo = JSON.parseObject(JSON.toJSONString(rowMap),EF01110003VO.class);
				// 不是人民币，美元，港币的市场持仓归类到其他市场，只返回市值即可，市值按港币汇算
                if(!HsConstants.HsMoneyType.CNY.getMoneyType().equals(vo.getMoneyType())
                		&& !HsConstants.HsMoneyType.USD.getMoneyType().equals(vo.getMoneyType())
                		&& !HsConstants.HsMoneyType.HKD.getMoneyType().equals(vo.getMoneyType())){
					if(other){
						vo.setMarketValue(otherMarketValue.toString());
						vo.setPositions(positions.get(HsConstants.HsMoneyType.OTH.getMoneyType()));
						vo.setMoneyType(HsConstants.HsMoneyType.HKD.getMoneyType());
						vo.setMarketType(HsConstants.MarketTypeEnum.OTH.getCode());
						other = false;
					}else{
						continue;
					}
                }else{
					vo.setFrozenBalance((new BigDecimal(vo.getFrozenBalance()).add(new BigDecimal(vo.getCashOnHold()))).toString());
					vo.setPositions(positions.get(vo.getMoneyType()));
					BigDecimal marketValue = hkMarketValue;
					BigDecimal incomeBalance = hkIncomeBalance;
					BigDecimal todayIncome = hkTodayIncome;
					BigDecimal yesterdayMarketValue = hkYesterdayMarketValue;
					BigDecimal costMarketValue = hkCostMarketValue;
					vo.setMarketType(HsConstants.MarketTypeEnum.HK.getCode());
					if (HsConstants.HsMoneyType.USD.getMoneyType().equals(vo.getMoneyType())) {
						marketValue = usMarketValue;
						incomeBalance = usIncomeBalance;
						todayIncome = usTodayIncome;
						yesterdayMarketValue = usYesterdayMarketValue;
						costMarketValue = usCostMarketValue;
						vo.setMarketType(HsConstants.MarketTypeEnum.US.getCode());
					} else if (HsConstants.HsMoneyType.CNY.getMoneyType().equals(vo.getMoneyType())) {
						marketValue = cnyMarketValue;
						incomeBalance = cnyIncomeBalance;
						todayIncome = cnyTodayIncome;
						yesterdayMarketValue = cnyYesterdayMarketValue;
						costMarketValue = cnyCostMarketValue;
						vo.setMarketType(HsConstants.MarketTypeEnum.CN.getCode());
					}
					vo.setMarketValue(marketValue.setScale(2, RoundingMode.HALF_UP).toPlainString());
					vo.setIncomeBalance(incomeBalance.setScale(2, RoundingMode.HALF_UP).toPlainString());
					vo.setTodayIncome(todayIncome.setScale(2, RoundingMode.HALF_UP).toPlainString());

					if(BigDecimal.ZERO.compareTo(costMarketValue) != 0){
						vo.setIncomeBalanceRatio(incomeBalance.divide(costMarketValue,5).setScale(2, RoundingMode.HALF_UP).toPlainString());
					}
					if(BigDecimal.ZERO.compareTo(yesterdayMarketValue) != 0 || (StringUtils.isNotBlank(vo.getRealBuyBalance().toString()) && BigDecimal.ZERO.compareTo(vo.getRealBuyBalance()) != 0)){
						vo.setTodayIncomeRatio(todayIncome.divide((yesterdayMarketValue.add(vo.getRealBuyBalance())),5).setScale(2, RoundingMode.HALF_UP).toPlainString());
					}
				}
                log.info("EF01110003.fundAccount={}, moneyType={}, marketValue={}, hsMarketValue={}", req.getFundAccount(), vo.getMoneyType(), vo.getMarketValue(), vo.getMarketValue());
				vos.add(vo);
            }
            responseVO.setResult(vos);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

    /**
     * 取缓存中本地计算的分市场市值
     */
    private CacheEF01110005VO getCachedMarketValue(EF01110003Request ef01110003Request) {
		EF01110005Request ef01110005Request = JSONObject.parseObject(JSONObject.toJSONString(ef01110003Request), EF01110005Request.class);
		ef01110005Request.setExchangeType(null);
		ef01110005Request.setIsFilter("0");
		ef01110005Request.setFunctionId(GrmFunctions.BROKER_GET_CLIENT_HOLDING);
		ef01110005Request.setUserId(ef01110003Request.getUserId());
		ef01110005.requestData(ef01110005Request);
		return zeroRedis.get(ef01110003Request.getFundAccount());
    }

}
