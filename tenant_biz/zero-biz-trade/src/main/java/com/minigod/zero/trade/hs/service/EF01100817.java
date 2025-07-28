package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.BeanHelper;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100817Request;
import com.minigod.zero.trade.hs.resp.EF01100817VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 恒生功能号：817
 * 查询客户资金信息(包括孖展和现金客户)如果输入币种就是查询单个币种的资金信息。如果不输入币种就是查询这个客户所有币种的资金信息.
 */
@Component
@GrmFunctionEntity(requestVo = EF01100817Request.class, responseVo = EF01100817VO.class)
public class EF01100817<T extends GrmRequestVO> extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01100817Request) {
            EF01100817Request req = (EF01100817Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.OP_STATION, req.getOpStation());
            reqMap.put(HsConstants.Fields.CLIENT_ID, req.getClientId());
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            if (StringUtils.isNotBlank(req.getMoneyType())) {
                reqMap.put(HsConstants.Fields.MONEY_TYPE, req.getMoneyType());
            }
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            List<EF01100817VO> voList = new ArrayList<>();
            List<Map<String, String>> list = grmDataVO.getResult();
            Iterator<Map<String, String>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                EF01100817VO vo = BeanHelper.copyProperties(rowMap, EF01100817VO.class);
                voList.add(vo);
            }
            responseVO.setResult(voList);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }


}
