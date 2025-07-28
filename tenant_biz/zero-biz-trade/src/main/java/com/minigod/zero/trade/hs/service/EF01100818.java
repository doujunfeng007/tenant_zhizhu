package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.BeanHelper;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100818Request;
import com.minigod.zero.trade.hs.resp.EF01100818VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 恒生功能号：818
 * 查询客户资金信息(包括孖展和现金客户)如果输入币种就是查询单个币种的资金信息。如果不输入币种就是查询这个客户所有币种的资金信息.
 */
@Component
@GrmFunctionEntity(requestVo = EF01100818Request.class, responseVo = EF01100818VO.class)
public class EF01100818<T extends GrmRequestVO> extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01100818Request) {
            EF01100818Request req = (EF01100818Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.OP_STATION, req.getOpStation());
            reqMap.put(HsConstants.Fields.CLIENT_ID, req.getClientId());
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.TO_MONEY_TYPE, req.getToMoneyType());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            List<EF01100818VO> voList = new ArrayList<>();
            List<Map<String, String>> list = grmDataVO.getResult();
            Iterator<Map<String, String>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                EF01100818VO vo = BeanHelper.copyProperties(rowMap, EF01100818VO.class);
                voList.add(vo);
            }
            responseVO.setResult(voList);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }


}
