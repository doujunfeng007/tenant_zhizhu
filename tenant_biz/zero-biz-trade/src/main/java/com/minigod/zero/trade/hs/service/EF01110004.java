package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.EF01110004Request;
import com.minigod.zero.trade.hs.req.EF01110005Request;
import com.minigod.zero.trade.hs.resp.*;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 获取客户EF01110003汇总资金信息
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Slf4j
@Component
@GrmFunctionEntity(requestVo = EF01110004Request.class, responseVo = ClientCashSumInfo.class)
public class EF01110004<T extends GrmRequestVO> extends T2sdkBiz<T> {
	@Resource
	private ZeroRedis zeroRedis;
    @Resource
    protected HsFieldFormater fieldFormater;
    //@Resource
    //protected IBuyMoneyService buyMoneyService;
    @Resource
    private EF01110005 ef01110005;

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110004Request) {
            EF01110004Request req = (EF01110004Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            String toMoneyType = req.getToMoneyType();
            if (StringUtils.isEmpty(toMoneyType)) {
                toMoneyType = HsConstants.HsMoneyType.HKD.getMoneyType();
                req.setToMoneyType(toMoneyType);
            }
            reqMap.put(HsConstants.Fields.TO_MONEY_TYPE, toMoneyType);
            String exchangeType = req.getExchangeType();
            if (StringUtils.isEmpty(exchangeType)) {
                exchangeType = HsConstants.HSExchageType.HK.getCode();
            }
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, exchangeType);
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            EF01110004Request ef01110004Request = (EF01110004Request) request;
            List<Map<String, String>> list = grmDataVO.getResult();
            Iterator<Map<String, String>> iterator = list.iterator();
            List<Object> listVO = new ArrayList<>();

            //查询用户在途资金
            BigDecimal totalBuyMoney = BigDecimal.ZERO;
            try {
				/** TODO
                if (HsConstants.HsMoneyType.HKD.getMoneyType().equals(ef01110004Request.getToMoneyType())) {
                    BuyMoneyResponse ipoBuyMoney = buyMoneyService.findIpoBuyMoney(ef01110004Request.getFundAccount());
                    if (ipoBuyMoney != null) {
                        totalBuyMoney = BigDecimal.valueOf(ipoBuyMoney.getTotalBuyMoney());
                    }
                } else if (HsConstants.HsMoneyType.USD.getMoneyType().equals(ef01110004Request.getToMoneyType())) {
                    //用户查询非港币总资产
                    BuyMoneyResponse ipoBuyMoney = buyMoneyService.findIpoBuyMoney(ef01110004Request.getFundAccount());
                    if (ipoBuyMoney != null) {
                        totalBuyMoney = BigDecimal.valueOf(ipoBuyMoney.getTotalBuyMoney());
                        HsExchangeRateMapVO rateMapVO = grmCacheMgr.getHsExchangeRateMapVO(HsConstants.HsMoneyType.USD.getMoneyType());//查汇率
                        if (rateMapVO != null) {
                            HsExchangeRateVO rateVO = rateMapVO.getExchangeRateMap().get(HsConstants.HsMoneyType.HKD.getMoneyType());//港币兑目标币种汇率
                            if (rateVO != null) {
                                totalBuyMoney = totalBuyMoney.multiply(rateVO.getExchangeRate()).setScale(2, BigDecimal.ROUND_HALF_UP);
                            } else {
                                logger.warn("rateVO is null, toMoneyType={}", ef01110004Request.getToMoneyType());
                            }
                        } else {
                            logger.warn("rateMapVO is null, toMoneyType={}", ef01110004Request.getToMoneyType());
                        }
                    }
                }
				 */
            } catch (Exception e) {
                log.error("findIpoBuyMoney error", e);
            }
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                ClientCashSumInfo vo = new ClientCashSumInfo();
                String cashOnHold = rowMap.get(HsConstants.Fields.CACHE_ON_HOLD);
                String frozenBalance = rowMap.get(HsConstants.Fields.FROZEN_BALANCE);
                double frozenBalanceCount = Double.parseDouble(cashOnHold) + Double.parseDouble(frozenBalance);

                vo.setAsset(rowMap.get(HsConstants.Fields.ASSET));
                vo.setEnableBalance(rowMap.get(HsConstants.Fields.ENABLE_BALANCE));
                String frozenBalanceCountStr = fieldFormater.format(HsConstants.Fields.FROZEN_BALANCE, Double.toString(frozenBalanceCount));
                vo.setFrozenBalance(frozenBalanceCountStr);
                vo.setMarketValue(rowMap.get(HsConstants.Fields.MARKET_VALUE));
                vo.setMoneyType(rowMap.get(HsConstants.Fields.MONEY_TYPE));
                vo.setTransferBalance(rowMap.get(HsConstants.Fields.TRANSFER_BALANCE));
                vo.setCurrentBalance(rowMap.get(HsConstants.Fields.CURRENT_BALANCE));
                vo.setIpoBalance(rowMap.get(HsConstants.Fields.IPO_BALANCE));
                vo.setFetchBalance(rowMap.get(HsConstants.Fields.SPECIAL_FETCH_BALANCE));
                vo.setGfFetchBalanceT(rowMap.get(HsConstants.Fields.GF_FETCH_BALANCE_T));
                vo.setTradeDayBalance(rowMap.get(HsConstants.Fields.TRADEDAY_BALANCE));
                vo.setMaxExposure(rowMap.get(HsConstants.Fields.MAX_EXPOSURE));
                vo.setIncomeBalance("0");
                vo.setIncomeRatio("0");
                //总资产加上在途资金
                double asset = Double.parseDouble(vo.getAsset());
                asset = asset + totalBuyMoney.doubleValue();
//                logger.info("*******************************originAsset={}, originMarketValue={}", asset, vo.getMarketValue());
                vo.setAsset(String.format("%.2f", asset));
                vo.setBuyMoney(totalBuyMoney.toPlainString());
                //重新计算总资产
                GrmResponseVO countResp = countTotalAsset(ef01110004Request, vo);
                if (!GrmConstants.GRM_RESPONSE_OK.equals(grmDataVO.getErrorId())) {
                    return countResp;
                }
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
            //存入客户非登录session
            ClientSession clientSession = zeroRedis.findObject(ClientSession.class, request.getSessionId());
            if (clientSession == null) {
                clientSession = new ClientSession();
                Date now = new Date();
                clientSession.setClientId(ef01110004Request.getClientId());
                clientSession.setBranchNo(HsConstants.InnerBrokerConfig.INNER_OP_BRANCHNO);
                clientSession.setIpAddress(request.getIpAddress());
                clientSession.setSid(request.getSid());
                clientSession.setUserType(Constants.USER_TYPE_CLIENT);
                clientSession.setSessionId(request.getSessionId());
                clientSession.setLoginTime(now);
                clientSession.setOpStation(ef01110004Request.getOpStation());
                clientSession.setEntrustWay(HsConstants.InnerBrokerConfig.INNER_OP_ENTRUST_WAY);
                clientSession.setLastOperateTime(now);
				zeroRedis.saveUpdate(clientSession, request.getSessionId());
            }
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

    /**
     * 重新计算总资产，总资产=总资产-总市值+本地计算的总市值
     */
    private GrmResponseVO countTotalAsset(EF01110004Request ef01110004Request, ClientCashSumInfo cashSumInfo) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (cashSumInfo != null) {
            BigDecimal totalMarketValue2HKD = BigDecimal.ZERO;// 港美股持仓市值转为港币（不包括A股市值）
            BigDecimal aMarketValue = BigDecimal.ZERO;// A股持仓市值
            CacheEF01110005VO cacheEF01110005VO = zeroRedis.findObject(CacheEF01110005VO.class, ef01110004Request.getFundAccount());
            if (cacheEF01110005VO != null) {
                log.info("use cacheEF01110005VO");
                totalMarketValue2HKD = cacheEF01110005VO.getTotalMarketValue2HKD();
                aMarketValue = cacheEF01110005VO.getAMarketValue();
            } else {
				log.info("query ef01110005");
                EF01110005Request ef01110005Request = JSONObject.parseObject(JSONObject.toJSONString(ef01110004Request), EF01110005Request.class);
                ef01110005Request.setExchangeType(null);
                ef01110005Request.setIsFilter("0");// 不过滤
                ef01110005Request.setFunctionId(GrmFunctions.BROKER_GET_CLIENT_HOLDING);
                GrmResponseVO response10005VO = ef01110005.requestData(ef01110005Request);
                if (response10005VO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
                    cacheEF01110005VO = zeroRedis.findObject(CacheEF01110005VO.class, ef01110004Request.getFundAccount());
                    if (cacheEF01110005VO != null) {
                        //持仓不为空
                        totalMarketValue2HKD = cacheEF01110005VO.getTotalMarketValue2HKD();
                        aMarketValue = cacheEF01110005VO.getAMarketValue();
                    }
                } else {
                    return response10005VO;
                }
            }
            BigDecimal totalAsset = new BigDecimal(cashSumInfo.getAsset());//柜台总资产
            BigDecimal totalMarketValue = new BigDecimal(cashSumInfo.getMarketValue());//柜台持仓总市值
            BigDecimal defaultTotalAsset = totalAsset.subtract(totalMarketValue).add(totalMarketValue2HKD);//默认值
            if (HsConstants.HsMoneyType.HKD.getMoneyType().equals(ef01110004Request.getToMoneyType())) {
                totalAsset = defaultTotalAsset;
            } else if (HsConstants.HsMoneyType.USD.getMoneyType().equals(ef01110004Request.getToMoneyType())) {
                HsExchangeRateMapVO usRateMapVO = grmCacheMgr.getHsExchangeRateMapVO(HsConstants.HsMoneyType.USD.getMoneyType());//兑美元汇率
                if (usRateMapVO != null) {
                    HsExchangeRateVO rateVO = usRateMapVO.getExchangeRateMap().get(HsConstants.HsMoneyType.HKD.getMoneyType());
                    //最新持仓总市值(美元)=最新市场总市值(港币)*卖出汇率
                    totalAsset = totalAsset.subtract(totalMarketValue).add(totalMarketValue2HKD.multiply(rateVO.getExchangeRate()));
                } else {
					log.warn("usRateMapVO is null");
                }
            } else if (HsConstants.HsMoneyType.CNY.getMoneyType().equals(ef01110004Request.getToMoneyType())) {
                totalAsset = totalAsset.subtract(totalMarketValue).add(aMarketValue);// A股和港美股分开
            } else {
                totalAsset = defaultTotalAsset;
				log.warn("other toMoneyType={}", ef01110004Request.getToMoneyType());
            }
            String hsAsset = cashSumInfo.getAsset();//柜台总资产
            String hsMarketValue = cashSumInfo.getMarketValue();//柜台市值
            cashSumInfo.setAsset(totalAsset.setScale(2, RoundingMode.HALF_UP).toPlainString());
			log.info("EF01110004.fundAccount={}, asset={}, hsAsset={}, totalMarketValue2HKD={}, hsMarketValue={}",
                    ef01110004Request.getFundAccount(), cashSumInfo.getAsset(), hsAsset, totalMarketValue2HKD, hsMarketValue);
        }
        return responseVO;
    }

}
