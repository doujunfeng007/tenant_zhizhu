package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100304Request;
import com.minigod.zero.trade.hs.resp.EF01100304VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * 改撤单
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100304Request.class,responseVo = EF01100304VO.class)
public class EF01100304<T extends GrmRequestVO>  extends T2sdkBiz<T> {
	@Autowired
	private TradeEventNotifyService tradeEventNotifyService;

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100304Request){
            EF01100304Request req = (EF01100304Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            reqMap.put(HsConstants.Fields.PASSWORD,req.getPassword());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
            reqMap.put(HsConstants.Fields.STOCK_CODE,req.getStockCode());
            reqMap.put(HsConstants.Fields.ENTRUST_AMOUNT,req.getEntrustAmount());
            reqMap.put(HsConstants.Fields.ENTRUST_PRICE,req.getEntrustPrice());
            reqMap.put(HsConstants.Fields.ENTRUST_NO_FIRST,req.getEntrustNoFirst());
            reqMap.put(HsConstants.Fields.ENTRUST_TYPE,req.getEntrustType());

            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String,String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if(grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){

            List<Map<String, String>> list = grmDataVO.getResult();
            Iterator<Map<String, String>> iterator = list.iterator();
            EF01100304VO vo;
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                vo = new EF01100304VO();
                vo.setEntrustNo(rowMap.get(HsConstants.Fields.ENTRUST_NO));
                vo.setInitDate(rowMap.get(HsConstants.Fields.INIT_DATE));
                responseVO.addResultData(vo);
            }
        	// 触发改单、撤单通知
        	tradeEventNotifyService.dispatchModifyCancelEvent(request, oParam, grmDataVO);
           return responseVO;
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
