package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100814Request;
import com.minigod.zero.trade.hs.resp.EF01100814VO;
import com.minigod.zero.trade.hs.resp.FundAccountInfo;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 查询当前及期汇率
 * sunline
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100814Request.class,responseVo = FundAccountInfo.class)
public class EF01100814<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100814Request){
            EF01100814Request req = (EF01100814Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.ACTION_IN,req.getActionIn());
            reqMap.put(HsConstants.Fields.FROM_MONEY_TYPE,req.getFromMoneyType());
            reqMap.put(HsConstants.Fields.TO_MONEY_TYPE,req.getToMoneyType());
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
            List<EF01100814VO> listVo = new ArrayList<>();
            EF01100814VO vo;
            String assetId;
            StkInfo assetInfo;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01100814VO();
                vo.setExchRate(rowMap.get(HsConstants.Fields.EXCH_RATE));
                vo.setExchRateReverse(rowMap.get(HsConstants.Fields.EXCH_RATE_REVERSE));

                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }


}
