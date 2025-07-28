package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110181Request;
import com.minigod.zero.trade.hs.resp.EF01110181VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 服务_海外_取历史成交流水
 */

@Component
@AllArgsConstructor
@GrmFunctionEntity(requestVo = EF01110181Request.class,responseVo = EF01110181VO.class)
public class EF01110181<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110181Request) {
			EF01110181Request req = (EF01110181Request) request;
            Map<String, String> reqMap = new HashMap<>();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            if(StringUtils.isNotEmpty(req.getExchangeType())){
                reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
            }
			if(StringUtils.isNotEmpty(req.getStockCode())){
				reqMap.put(HsConstants.Fields.STOCK_CODE,req.getStockCode());
			}
            reqMap.put(HsConstants.Fields.START_DATE, req.getStartDate());
            reqMap.put(HsConstants.Fields.END_DATE, req.getEndDate());
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            List<Map<String, String>> list = grmDataVO.getResult();
            List<EF01110181VO> listVO = new ArrayList<>();
            Iterator<Map<String, String>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
				EF01110181VO vo = JSON.parseObject(JSON.toJSONString(rowMap), EF01110181VO.class);
				listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
