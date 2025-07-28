package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110230Request;
import com.minigod.zero.trade.hs.resp.EF01110230VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 交易员-IPO认购
 * @param <T>
 */

@Component
@GrmFunctionEntity(requestVo = EF01110230Request.class, responseVo = EF01110230VO.class)
public class EF01110230 <T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110230Request) {
            EF01110230Request req = (EF01110230Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.STOCK_CODE, req.getStockCode());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.QUANTITY_APPLY, req.getQuantityApply());
            reqMap.put(HsConstants.Fields.DEPOSIT_RATE,req.getDepositRate());
            reqMap.put(HsConstants.Fields.TYPE,req.getType());
            reqMap.put(HsConstants.Fields.ACTION_IN,req.getActionIn());
            reqMap.put(HsConstants.Fields.CLIENT_ID,req.getClientId());
            if(StringUtils.isNotBlank(req.getManualHandlingFee())) {
                reqMap.put(HsConstants.Fields.MANUAL_HANDLING_FEE,req.getManualHandlingFee());
            }
            if(StringUtils.isNotBlank(req.getHandlingFeeAble())) {
                reqMap.put(HsConstants.Fields.HANDLING_FEE_ABLE,req.getHandlingFeeAble());
            }
            if(StringUtils.isNotBlank(req.getManualIpoIntrate())) {
                reqMap.put(HsConstants.Fields.MANUAL_IPO_INTRATE,req.getManualIpoIntrate());
            }
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
            EF01110230VO vo;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110230VO();
                vo.setOverFlag(rowMap.get(HsConstants.Fields.OVER_FLAG));
                responseVO.addResultData(vo);
            }
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
