package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson.JSONObject;
import com.minigod.zero.core.redis.cache.ZeroRedis;
import com.minigod.zero.trade.fund.vo.response.ClientCashInfo;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.EF01110005Request;
import com.minigod.zero.trade.hs.req.EF01110108Request;
import com.minigod.zero.trade.hs.resp.CacheEF01110005VO;
import com.minigod.zero.trade.hs.resp.EF01110003VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Component
@GrmFunctionEntity(requestVo = EF01110108Request.class, responseVo = EF01110003VO.class)
public class EF01110108<T extends GrmRequestVO> extends T2sdkBiz<T>{

    private Logger logger = LoggerFactory.getLogger(EF01110108.class);
	@Resource
	private ZeroRedis zeroRedis;
    @Resource
    private EF01110005 ef01110005;

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110108Request) {
            EF01110108Request req = (EF01110108Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            if (StringUtils.isNotEmpty(req.getMoneyType())) {
                reqMap.put(HsConstants.Fields.MONEY_TYPE, req.getMoneyType());
            }
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            List<Map<String, String>> list = grmDataVO.getResult();
            Iterator<Map<String, String>> iterator = list.iterator();
            List<Object> listVO = new ArrayList<>();
            Map<String, BigDecimal> tempObj;
            BigDecimal lastPrice;
            BigDecimal costPrice;
            EF01110108Request ef01110108Request = (EF01110108Request) request;


            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                ClientCashInfo vo = new ClientCashInfo();
                String cashOnHold = rowMap.get(HsConstants.Fields.CACHE_ON_HOLD);
                String frozenBalance = rowMap.get(HsConstants.Fields.FROZEN_BALANCE);
                Double frozenBalanceCount = Double.parseDouble(cashOnHold) + Double.parseDouble(frozenBalance);

                vo.setCashOnHold(cashOnHold);
                vo.setCreditLine(rowMap.get(HsConstants.Fields.CREDIT_LINE));
                vo.setEnableBalance(rowMap.get(HsConstants.Fields.ENABLE_BALANCE));
                vo.setFetchBalance(rowMap.get(HsConstants.Fields.FETCH_BALANCE));
                vo.setGfFetchBalanceT(rowMap.get(HsConstants.Fields.GF_FETCH_BALANCE_T));
                vo.setTradedayBalance(rowMap.get(HsConstants.Fields.TRADEDAY_BALANCE));
                vo.setFrozenBalance(frozenBalanceCount.toString());
                vo.setIpoBalance(rowMap.get(HsConstants.Fields.IPO_BALANCE));
                vo.setMarginCall(rowMap.get(HsConstants.Fields.MARGIN_CALL));
                vo.setMarginValue(rowMap.get(HsConstants.Fields.MARGIN_VALUE));
                vo.setMarketValue(rowMap.get(HsConstants.Fields.MARKET_VALUE));
                vo.setAsset(rowMap.get(HsConstants.Fields.ASSET));
                vo.setIncomeBalance(rowMap.get(HsConstants.Fields.INCOME_BALANCE));
                vo.setMaxExposure(rowMap.get(HsConstants.Fields.MAX_EXPOSURE));
                vo.setCurrency(GrmHsConstants.CurrencyType.getCurrencyType(rowMap.get(HsConstants.Fields.MONEY_TYPE)));
                vo.setTransferBalance(rowMap.get(HsConstants.Fields.TRANSFER_BALANCE));
                vo.setCurrentBalance(rowMap.get(HsConstants.Fields.CURRENT_BALANCE));
                vo.setIpoApplyingBalance(rowMap.get(HsConstants.Fields.IPO_APPLYING_BALANCE));
                vo.setIncomeRatio("0");
                vo.setCreditValue(rowMap.get(HsConstants.Fields.CREDIT_VALUE));
                vo.setLoanValue(rowMap.get(HsConstants.Fields.LOAN_VALUE));
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

    /**
     * 取缓存中本地计算的分市场市值
     */
    private CacheEF01110005VO getCachedMarketValue(EF01110108Request ef01110108Request, GrmResponseVO responseVO) {
        CacheEF01110005VO cacheEF01110005VO = zeroRedis.findObject(CacheEF01110005VO.class, ef01110108Request.getFundAccount());
        if (cacheEF01110005VO != null) {
            logger.info("use cacheEF01110005VO");
        } else {
            logger.info("query ef01110005");
            EF01110005Request ef01110005Request = JSONObject.parseObject(JSONObject.toJSONString(ef01110108Request), EF01110005Request.class);
            ef01110005Request.setExchangeType(null);
            ef01110005Request.setIsFilter("0");// 不过滤
            ef01110005Request.setFunctionId(GrmFunctions.BROKER_GET_CLIENT_HOLDING);
            GrmResponseVO response10005VO = ef01110005.requestData(ef01110005Request);
            if (response10005VO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
                cacheEF01110005VO = zeroRedis.findObject(CacheEF01110005VO.class, ef01110108Request.getFundAccount());
            } else {
                responseVO.setErrorId(response10005VO.getErrorId());
                responseVO.setErrorMsg(response10005VO.getErrorMsg());
            }
        }
        if (cacheEF01110005VO == null) {
            cacheEF01110005VO = new CacheEF01110005VO();
        }
        return cacheEF01110005VO;
    }
}
