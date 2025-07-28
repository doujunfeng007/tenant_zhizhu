package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.BeanHelper;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100810Request;
import com.minigod.zero.trade.hs.resp.EF01100810VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@GrmFunctionEntity(requestVo = EF01100810Request.class, responseVo = EF01100810VO.class)
public class EF01100810<T extends GrmRequestVO> extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01100810Request) {
            EF01100810Request req = (EF01100810Request) request;
            Map<String, String> reqMap = new HashMap<>();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            if(StringUtils.isNotBlank(req.getPassword())) {
                reqMap.put(HsConstants.Fields.PASSWORD, req.getPassword());
            }
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
            reqMap.put(HsConstants.Fields.INIT_DATE, req.getInitDate());
            reqMap.put(HsConstants.Fields.RECORD_NO, req.getRecordNo());
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
            List<Object>  listVO = new ArrayList<>();
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                EF01100810VO vo = BeanHelper.copyProperties(rowMap, EF01100810VO.class);
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
