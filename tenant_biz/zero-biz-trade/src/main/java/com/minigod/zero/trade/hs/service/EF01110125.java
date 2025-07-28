package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110125Request;
import com.minigod.zero.trade.hs.resp.EF01110125VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 取帐号属性
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110125Request.class,responseVo = EF01110125VO.class)
public class EF01110125<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110125Request){
            EF01110125Request req = (EF01110125Request) request;
            oParamMap.put(HsConstants.Fields.CLIENT_ID,req.getClientId());
            oParamMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String,String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if(grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
           List<Map<String,String>> list =  grmDataVO.getResult();
            Iterator<Map<String,String>> iterator = list.iterator();
            EF01110125VO vo ;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110125VO();
                vo.setBrithday(rowMap.get(HsConstants.Fields.BIRTHDAY));
                vo.setClientName(rowMap.get(HsConstants.Fields.CLIENT_NAME));
                vo.setIdKind(rowMap.get(HsConstants.Fields.ID_KIND));
                vo.setIdNo(rowMap.get(HsConstants.Fields.ID_NO));
                vo.setIdBegindate(rowMap.get(HsConstants.Fields.ID_BEGINDATE));
                vo.setIdTerm(rowMap.get(HsConstants.Fields.ID_TERM));
                vo.setAssetProp(rowMap.get(HsConstants.Fields.ASSET_PROP));
                vo.setBrokerAccount(rowMap.get(HsConstants.Fields.BROKER_ACCOUNT));
                responseVO.addResultData(vo);
            }
            return responseVO;
        }
            responseVO.setErrorId(grmDataVO.getErrorId());
            responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

}
