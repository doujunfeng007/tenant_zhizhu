package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110010Request;
import com.minigod.zero.trade.hs.resp.EF01110010VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 证券冻结
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110010Request.class,responseVo = EF01110010VO.class)
public class EF01110010<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110010Request) {
            EF01110010Request req = (EF01110010Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.CLIENT_ID, req.getClientId());
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
            reqMap.put(HsConstants.Fields.STOCK_ACCOUNT, req.getStockAccount());
            reqMap.put(HsConstants.Fields.STOCK_CODE, req.getStockCode());
            reqMap.put(HsConstants.Fields.OCCUR_AMOUNT, req.getOccurAmount());
            reqMap.put(HsConstants.Fields.VALID_DATE, req.getValidDate());
            reqMap.put(HsConstants.Fields.REMARK, req.getRemark());
            reqMap.put(HsConstants.Fields.LOCALE_REMARK, req.getLocaleRemark());
            reqMap.put(HsConstants.Fields.OVERDRAFT_FORCED_FLAG,req.getOverdraftForcedFlag());
            reqMap.put(HsConstants.Fields.FUNCTION_ID,"613000");
            reqMap.put(HsConstants.Fields.MENU_ID,"613003");
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String,String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if(grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
           List<Map<String,String>> list =  grmDataVO.getResult();
            Iterator<Map<String,String>> iterator = list.iterator();
            EF01110010VO vo ;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110010VO();
                vo.setSerialNo(rowMap.get(HsConstants.Fields.SERIAL_NO));
                vo.setAuditSerialNo(rowMap.get(HsConstants.Fields.AUDIT_SERIAL_NO));
                vo.setCurrentAmount(rowMap.get(HsConstants.Fields.CURRENT_AMOUNT));
                vo.setEnableAmount(rowMap.get(HsConstants.Fields.ENABLE_AMOUNT));
                vo.setStkrevertjourNo(rowMap.get(HsConstants.Fields.STKREVERTJOUR_NO));
                vo.setOpRemark(rowMap.get(HsConstants.Fields.OP_REMARK));
                responseVO.addResultData(vo);
            }
            return responseVO;
        }
            responseVO.setErrorId(grmDataVO.getErrorId());
            responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

}
