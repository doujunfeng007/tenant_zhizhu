package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110104Request;
import com.minigod.zero.trade.hs.resp.EF01110104VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 服务_海外_委托撤单
 */

@Component
@GrmFunctionEntity(requestVo = EF01110104Request.class, responseVo = EF01110104VO.class)
public class EF01110104<T extends GrmRequestVO> extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110104Request){
			EF01110104Request req = (EF01110104Request) request;
			oParamMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
			oParamMap.put(HsConstants.Fields.STOCK_CODE, req.getStockCode());
			oParamMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
			oParamMap.put(HsConstants.Fields.ENTRUST_PRICE, req.getEntrustPrice());
			oParamMap.put(HsConstants.Fields.ENTRUST_AMOUNT,req.getEntrustAmount());
			oParamMap.put(HsConstants.Fields.ENTRUST_NO_FIRST,req.getEntrustNo());
			oParamMap.put(HsConstants.Fields.ENTRUST_TYPE,req.getEntrustType());
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            List<Map<String, String>> list = grmDataVO.getResult();
            List<EF01110104VO> listVO = new ArrayList<>();
            Iterator<Map<String, String>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
				EF01110104VO vo = JSON.parseObject(JSON.toJSONString(rowMap), EF01110104VO.class);
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
