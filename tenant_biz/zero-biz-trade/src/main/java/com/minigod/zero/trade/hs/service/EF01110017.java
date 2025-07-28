package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110017Request;
import com.minigod.zero.trade.hs.resp.EF01110017VO;
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
@GrmFunctionEntity(requestVo = EF01110017Request.class,responseVo = EF01110017VO.class)
public class EF01110017<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110017Request){
            EF01110017Request req = (EF01110017Request) request;
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
            EF01110017VO vo ;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110017VO();
                vo.setAssetProp(rowMap.get(HsConstants.Fields.ASSET_PROP));
                vo.setRestriction(rowMap.get(HsConstants.Fields.RESTRICTION));
                responseVO.addResultData(vo);
            }
            return responseVO;
        }
            responseVO.setErrorId(grmDataVO.getErrorId());
            responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

}
