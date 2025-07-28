package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110229Request;
import com.minigod.zero.trade.hs.resp.EF01110229VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author sunline
 * @date 2020/7/31
 */

@Component
@GrmFunctionEntity(requestVo = EF01110229Request.class, responseVo = EF01110229VO.class)
public class EF01110229 <T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110229Request){
            EF01110229Request req = (EF01110229Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.CLIENT_ID, req.getClientId());
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.OP_PASSWORD, req.getOpPassword());
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if(grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
            List<Map<String,String>> list =  grmDataVO.getResult();
            Iterator<Map<String,String>> iterator = list.iterator();
            EF01110229VO vo;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110229VO();
                vo.setBcan(rowMap.get(HsConstants.Fields.BCAN));
                vo.setStatus(rowMap.get(HsConstants.Fields.STATUS));
                responseVO.addResultData(vo);
            }
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
