package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100832Request;
import com.minigod.zero.trade.hs.resp.EF01100832VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


/**
 * 获取当日流水
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100832Request.class,responseVo = EF01100832VO.class)
public class EF01100832<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100832Request){
            EF01100832Request req = (EF01100832Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            reqMap.put(HsConstants.Fields.PASSWORD,req.getPassword());
            reqMap.put(HsConstants.Fields.MONEY_TYPE,req.getMoneyType());
            reqMap.put(HsConstants.Fields.TO_FUND_ACCOUNT,req.getToFundAccount());
            reqMap.put(HsConstants.Fields.OCCUR_BALANCE,req.getOccurBalance());
            String remark  = req.getRemark();
            if(StringUtils.isNotEmpty(remark)) {
                reqMap.put(HsConstants.Fields.REMARK, req.getRemark());
            }
            String localeRemark = req.getLocaleRemark();
            if(StringUtils.isNotEmpty(localeRemark)) {
                reqMap.put(HsConstants.Fields.LOCALE_REMARK, req.getLocaleRemark());
            }
            oParamMap.putAll(reqMap);
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
