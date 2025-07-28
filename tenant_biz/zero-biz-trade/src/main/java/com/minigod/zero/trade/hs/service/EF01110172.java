package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110172Request;
import com.minigod.zero.trade.hs.resp.EF01110172VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 历史委托流水-免密
 * @param <T>
 */

@Component
@GrmFunctionEntity(requestVo = EF01110172Request.class, responseVo = EF01110172VO.class)
public class EF01110172<T extends GrmRequestVO> extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110172Request) {
			EF01110172Request req = (EF01110172Request) request;
            Map<String, String> reqMap = new HashMap<>();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
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
            List<EF01110172VO> listVo = new ArrayList<>();
            while (iterator.hasNext()) {
				Map<String, String> rowMap = iterator.next();
				EF01110172VO vo = JSON.parseObject(JSON.toJSONString(rowMap), EF01110172VO.class);
				listVo.add(vo);
            }
            responseVO.setResult(listVo);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
