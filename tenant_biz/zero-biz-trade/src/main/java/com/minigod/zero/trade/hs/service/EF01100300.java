package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100300Request;
import com.minigod.zero.trade.hs.resp.EF01100300VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 获取最大可卖数量
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100300Request.class,responseVo = EF01100300VO.class)
public class EF01100300<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100300Request){
            EF01100300Request req = (EF01100300Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            reqMap.put(HsConstants.Fields.PASSWORD,req.getPassword());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
            reqMap.put(HsConstants.Fields.STOCK_CODE,req.getStockCode());
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String,String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if(grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
            List<Map<String,String>> list =  grmDataVO.getResult();
            Iterator<Map<String,String>> iterator = list.iterator();
            List<Object>  listVO = new ArrayList<>();
            EF01100300VO vo;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01100300VO();
                vo.setStockCode(rowMap.get(HsConstants.Fields.STOCK_CODE));
                vo.setStockName(rowMap.get(HsConstants.Fields.STOCK_NAME));
                vo.setEnableAmount(rowMap.get(HsConstants.Fields.ENABLE_AMOUNT));
                vo.setIncomeBalance(rowMap.get(HsConstants.Fields.INCOME_BALANCE));
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
