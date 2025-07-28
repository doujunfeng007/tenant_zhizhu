package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.EF01110184Request;
import com.minigod.zero.trade.hs.resp.EF01110184VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *获取IPO客户申购信息
 * @author sunline
 * @date 2020.6.24
 */

@Component
@GrmFunctionEntity(requestVo = EF01110184Request.class, responseVo = EF01110184VO.class)
public class EF01110184<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110184Request) {
            EF01110184Request req = (EF01110184Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
            reqMap.put(HsConstants.Fields.BEGIN_DATE, req.getBeginDate());
            reqMap.put(HsConstants.Fields.END_DATE, req.getEndDate());
            reqMap.put(HsConstants.Fields.QUERY_DIRECTION, "0");
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();

        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
             List<Map<String, String>> list = grmDataVO.getResult();
            Iterator<Map<String, String>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                EF01110184VO vo = BeanHelper.copyProperties(rowMap, EF01110184VO.class);
                String assetId = DataFormatUtil.stkCodeToAssetId(vo.getStockCode(),vo.getExchangeType());
                vo.setAssetId(assetId);
                responseVO.addResultData(vo);
            }
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
