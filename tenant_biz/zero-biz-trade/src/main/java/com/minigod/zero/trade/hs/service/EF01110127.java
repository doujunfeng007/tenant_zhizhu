package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110127Request;
import com.minigod.zero.trade.hs.resp.EF01110127VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 中签
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110127Request.class,responseVo = EF01110127VO.class)
public class EF01110127<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110127Request){
            EF01110127Request req = (EF01110127Request) request;
            oParamMap.put(HsConstants.Fields.CLIENT_ID,req.getClientId());
            oParamMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            oParamMap.put(HsConstants.Fields.STOCK_CODE,req.getStockCode());
            oParamMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());

        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String,String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if(grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
           List<Map<String,String>> list =  grmDataVO.getResult();
            Iterator<Map<String,String>> iterator = list.iterator();
            EF01110127VO vo ;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110127VO();
                vo.setOpRemark(rowMap.get(HsConstants.Fields.OP_REMARK));
                vo.setRemark(rowMap.get(HsConstants.Fields.REMARK));
                responseVO.addResultData(vo);
            }
            return responseVO;
        }
            responseVO.setErrorId(grmDataVO.getErrorId());
            responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

}
