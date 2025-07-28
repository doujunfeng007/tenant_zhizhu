package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110006Request;
import com.minigod.zero.trade.hs.resp.EF01110006VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 资金冻结
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110006Request.class,responseVo = EF01110006VO.class)
public class EF01110006<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110006Request){
            EF01110006Request req = (EF01110006Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            reqMap.put(HsConstants.Fields.MONEY_TYPE,req.getMoneyType());
            reqMap.put(HsConstants.Fields.FROZEN_REASON,req.getFrozen_reason());
            reqMap.put(HsConstants.Fields.OCCUR_BALANCE,req.getOccurBalance());
            reqMap.put(HsConstants.Fields.VALID_DATE,req.getValidDate());
            reqMap.put(HsConstants.Fields.REMARK,req.getRemark());
            reqMap.put(HsConstants.Fields.LOCALE_REMARK,req.getLocaleRemark());
            reqMap.put(HsConstants.Fields.OVERDRAFT_FORCED_FLAG,req.getOverdraftForcedFlag());
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
            EF01110006VO vo ;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110006VO();
                vo.setSerialNo(rowMap.get(HsConstants.Fields.SERIAL_NO));
                vo.setRevertSerialNo(rowMap.get(HsConstants.Fields.REVERT_SERIAL_NO));
                vo.setInitDate(rowMap.get(HsConstants.Fields.INIT_DATE));
                responseVO.addResultData(vo);
            }
            return responseVO;
        }
            responseVO.setErrorId(grmDataVO.getErrorId());
            responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

}
