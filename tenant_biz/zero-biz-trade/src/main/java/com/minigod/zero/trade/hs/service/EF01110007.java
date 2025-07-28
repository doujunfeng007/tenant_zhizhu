package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110007Request;
import com.minigod.zero.trade.hs.resp.EF01110007VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 资金解冻
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110007Request.class,responseVo = EF01110007VO.class)
public class EF01110007<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110007Request){
            EF01110007Request req = (EF01110007Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.JOUR_DATE,req.getJourDate());
            reqMap.put(HsConstants.Fields.JOUR_SERIAL_NO,req.getJourSerialNo());
            reqMap.put(HsConstants.Fields.OCCUR_AMOUNT,req.getOccurBalance());
            reqMap.put(HsConstants.Fields.CANCEL_BALANCE,req.getCancelBalance());
            reqMap.put(HsConstants.Fields.REMARK,req.getRemark());
            reqMap.put(HsConstants.Fields.LOCALE_REMARK,req.getLocaleRemark());
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
            EF01110007VO vo ;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110007VO();
                vo.setSerialNo(rowMap.get(HsConstants.Fields.SERIAL_NO));
                responseVO.addResultData(vo);
            }
            return responseVO;
        }
            responseVO.setErrorId(grmDataVO.getErrorId());
            responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

}
