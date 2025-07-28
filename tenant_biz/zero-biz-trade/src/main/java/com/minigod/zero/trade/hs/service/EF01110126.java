package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110126Request;
import com.minigod.zero.trade.hs.resp.EF01110126VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 取设置费率
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110126Request.class,responseVo = EF01110126VO.class)
public class EF01110126<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110126Request){
            EF01110126Request req = (EF01110126Request) request;
            oParamMap.put(HsConstants.Fields.CLIENT_ID,req.getClientId());
            oParamMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            oParamMap.put(HsConstants.Fields.FARE_KIND_STR,req.getFareKindStr().getTradeFareCode());
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String,String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

}
