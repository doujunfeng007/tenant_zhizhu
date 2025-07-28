package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110008Request;
import com.minigod.zero.trade.hs.resp.EF01110008VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 现金存
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110008Request.class,responseVo = EF01110008VO.class)
public class EF01110008<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110008Request){
            EF01110008Request req = (EF01110008Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.MONEY_TYPE, req.getMoneyType());
            reqMap.put(HsConstants.Fields.OCCUR_BALANCE, req.getOccurBalance());
            reqMap.put(HsConstants.Fields.BANK_NO, req.getBankNo());
            reqMap.put(HsConstants.Fields.BANK_ACCOUNT_CLI, req.getBankAccountCli());
            reqMap.put(HsConstants.Fields.LEDGER_ID, req.getLedgerId());
            reqMap.put(HsConstants.Fields.CHECK_NO, req.getCheckNo());
            reqMap.put(HsConstants.Fields.REMARK, req.getRemark());
            reqMap.put(HsConstants.Fields.BANK_ID, req.getBankId());
            reqMap.put(HsConstants.Fields.BANK_ACCOUNT, req.getBankAccount());
            reqMap.put(HsConstants.Fields.VALUE_DATE, req.getValueDate());
            reqMap.put(HsConstants.Fields.BUSINESS_FLAG_REAL, req.getBusinessFlagReal());
            reqMap.put(HsConstants.Fields.CHECK_VALID_DATE, req.getCheckValidDate());
            reqMap.put(HsConstants.Fields.LOCALE_REMARK, req.getLocaleRemark());
            reqMap.put(HsConstants.Fields.FEE_MONEY_TYPE, req.getFeeMoneyType());
            reqMap.put(HsConstants.Fields.FEE_OCCUR_BALANCE, req.getFeeOccurBalance());
            reqMap.put(HsConstants.Fields.THETHIRD, req.getTheThird());
            reqMap.put(HsConstants.Fields.EX_STATUS, req.getExStatus());
            reqMap.put(HsConstants.Fields.FETCH_BALANCE_JUST, req.getFetchBalanceJust());
            reqMap.put(HsConstants.Fields.REMARK_FEE, req.getRemarkFee());
            reqMap.put(HsConstants.Fields.LOCALE_REMARK_FEE, req.getLocaleRemarkFee());
            reqMap.put(HsConstants.Fields.FUNCTION_ID,"200002");
            reqMap.put(HsConstants.Fields.MENU_ID,"200504");
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
            EF01110008VO vo ;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110008VO();
                vo.setSerialNo(rowMap.get(HsConstants.Fields.SERIAL_NO));
                vo.setTmpJoinSerialno(rowMap.get(HsConstants.Fields.TMP_JOIN_SERIALNO));
                vo.setAuditSerialNo(rowMap.get(HsConstants.Fields.AUDIT_SERIAL_NO));
                vo.setFeeJoinSerialno(rowMap.get(HsConstants.Fields.FEE_JOIN_SERIALNO));
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
