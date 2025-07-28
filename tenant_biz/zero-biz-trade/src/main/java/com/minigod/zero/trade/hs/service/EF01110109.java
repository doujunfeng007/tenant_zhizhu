package com.minigod.zero.trade.hs.service;

import com.alibaba.excel.util.DateUtils;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110109Request;
import com.minigod.zero.trade.hs.resp.ClientCashSumInfo;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 查询客户的汇总币种信息(单客户)
 */
@Component
@GrmFunctionEntity(requestVo = EF01110109Request.class, responseVo = ClientCashSumInfo.class)
public class EF01110109<T extends GrmRequestVO> extends T2sdkBiz<T> {
    @Resource
    protected HsFieldFormater fieldFormater;

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110109Request) {
            EF01110109Request req = (EF01110109Request) request;
            Map<String, String> reqMap = new HashMap<>();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            String toMoneyType = req.getToMoneyType();
            if (StringUtils.isEmpty(toMoneyType)) {
                toMoneyType = "2";
            }
            reqMap.put(HsConstants.Fields.TO_MONEY_TYPE, toMoneyType);

            String initDate = req.getInitDate();
            if(StringUtils.isEmpty(initDate)) {
                initDate = DateUtils.format(new Date(),"yyyyMMdd");
            }
            reqMap.put(HsConstants.Fields.INIT_DATE,initDate);

            String exchangeType = req.getExchangeType();
            if (StringUtils.isEmpty(exchangeType)) {
                exchangeType = "K";
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
            List<Map<String, String>> list = grmDataVO.getResult();
            Iterator<Map<String, String>> iterator = list.iterator();
            List<Object> listVO = new ArrayList<>();
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
                vo.setMaxExposure(rowMap.get(HsConstants.Fields.MAX_EXPOSURE));
                vo.setIncomeBalance("0");
                vo.setIncomeRatio("0");
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
