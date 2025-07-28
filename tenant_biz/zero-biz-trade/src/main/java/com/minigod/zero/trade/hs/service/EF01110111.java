package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.trade.enums.Hs2BsFlag;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.EF01110111Request;
import com.minigod.zero.trade.hs.resp.EF01110111VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Component
@GrmFunctionEntity(requestVo = EF01110111Request.class, responseVo = EF01110111VO.class)
public class EF01110111<T extends GrmRequestVO> extends T2sdkBiz<T> {


    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110111Request) {
            EF01110111Request req = (EF01110111Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.CLIENT_ID, req.getClientId());
            String exchangeType = req.getExchangeType();
            if(StringUtils.isNotEmpty(exchangeType)){
                reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
            }
            if(StringUtils.isNotBlank(req.getStockCode())) {
                reqMap.put(HsConstants.Fields.STOCK_CODE,req.getStockCode());
            }
            if(StringUtils.isNotBlank(req.getRequestNum())) {
                reqMap.put(HsConstants.Fields.REQUEST_NUM,req.getRequestNum());
            }
            reqMap.put(HsConstants.Fields.QUERY_DIRECTION, "0");
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
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
				EF01110111VO eF01110111VO = JSON.parseObject(JSON.toJSONString(rowMap),EF01110111VO.class);
                eF01110111VO.setBsFlag(Hs2BsFlag.getByPortfolio(eF01110111VO.getBsFlag()));
                responseVO.addResultData(eF01110111VO);
            }
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
