package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110016Request;
import com.minigod.zero.trade.hs.resp.StockAccountInfo;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 获取营业部编号
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110016Request.class,responseVo = StockAccountInfo.class)
public class EF01110016<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    private final static int iFuncNo = 10016;

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110016Request){
            EF01110016Request req = (EF01110016Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
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
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                StockAccountInfo vo = new StockAccountInfo();
                vo.setExchangeType(rowMap.get(HsConstants.Fields.EXCHANGE_TYPE));
                vo.setHolderStatus(rowMap.get(HsConstants.Fields.HOLDER_STATUS));
                vo.setStockAccount(rowMap.get(HsConstants.Fields.stock_account));
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }


}
