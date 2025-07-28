package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100883Request;
import com.minigod.zero.trade.hs.resp.EF01100883VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@GrmFunctionEntity(requestVo = EF01100883Request.class, responseVo = EF01100883VO.class)
public class EF01100883<T extends GrmRequestVO> extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01100883Request) {
            EF01100883Request req = (EF01100883Request) request;
            Map<String, String> reqMap = new HashMap();
            if(StringUtils.isNotBlank(req.getOpStation())) {
                reqMap.put(HsConstants.Fields.OP_STATION, req.getOpStation());
            }
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.PASSWORD, req.getPassword());
            reqMap.put(HsConstants.Fields.PASSWORD_TYPE, req.getPasswordType());
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
