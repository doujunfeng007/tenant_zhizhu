package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110013Request;
import com.minigod.zero.trade.hs.resp.EF01110013VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 证券取
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110013Request.class,responseVo = EF01110013VO.class)
public class EF01110013<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110013Request) {
            EF01110013Request req = (EF01110013Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.OP_PASSWORD, req.getOpPassword());
            reqMap.put(HsConstants.Fields.CLIENT_ID, req.getClientId());
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
            reqMap.put(HsConstants.Fields.STOCK_ACCOUNT, req.getStockAccount());
            reqMap.put(HsConstants.Fields.STOCK_CODE, req.getStockCode());
            reqMap.put(HsConstants.Fields.STOCK_PRICE, req.getStockPrice());
            reqMap.put(HsConstants.Fields.OCCUR_AMOUNT, req.getOccurAmount());
            reqMap.put(HsConstants.Fields.MONEY_TYPE, req.getMoneyType());
            reqMap.put(HsConstants.Fields.OCCUR_BALANCE, req.getOccurBalance());
            reqMap.put(HsConstants.Fields.REMARK, req.getRemark());
            reqMap.put(HsConstants.Fields.LOCAL_ID, req.getLocalId());
            reqMap.put(HsConstants.Fields.BANK_NO, req.getBankNo());
            reqMap.put(HsConstants.Fields.STOCK_MONEY_TYPE, req.getStockMoneyType());
            reqMap.put(HsConstants.Fields.CUSTODIAN_ID, req.getCustodianId());
            reqMap.put(HsConstants.Fields.CUSTODIAN, req.getCustodian());
            reqMap.put(HsConstants.Fields.VALUE_DATE, req.getValueDate());
            reqMap.put(HsConstants.Fields.LOCALE_REMARK, req.getLocaleRemark());
            reqMap.put(HsConstants.Fields.REMARK_FUND, req.getRemarkFund());
            reqMap.put(HsConstants.Fields.REMARK_FUND_LOCALE, req.getRemarkFundLocale());
            reqMap.put(HsConstants.Fields.BANK_ID, req.getBankId());
            reqMap.put(HsConstants.Fields.BANK_ACCOUNT, req.getBankAccount());
            reqMap.put(HsConstants.Fields.OCCUR_AMOUNT_P1, req.getOccurAmountP1());
            reqMap.put(HsConstants.Fields.OCCUR_AMOUNT_P2, req.getOccurAmountP2());
            reqMap.put(HsConstants.Fields.OCCUR_AMOUNT_P3, req.getOccurAmountP3());
            reqMap.put(HsConstants.Fields.FARE_MONEY_TYPE, req.getFareMoneyType());
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
            EF01110013VO vo ;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110013VO();
                vo.setSerialNo(rowMap.get(HsConstants.Fields.SERIAL_NO));
                vo.setAuditSerialNo(rowMap.get(HsConstants.Fields.AUDIT_SERIAL_NO));
                vo.setCurrentAmount(rowMap.get(HsConstants.Fields.CURRENT_AMOUNT));
                vo.setEnableAmount(rowMap.get(HsConstants.Fields.ENABLE_AMOUNT));
                vo.setOpRemark(rowMap.get(HsConstants.Fields.OP_REMARK));
                vo.setFundBusinessFlag(rowMap.get(HsConstants.Fields.FUND_BUSINESS_FLAG));
                vo.setTransmitAmount(rowMap.get(HsConstants.Fields.TRANSMIT_AMOUNT));
                responseVO.addResultData(vo);
            }
            return responseVO;
        }
            responseVO.setErrorId(grmDataVO.getErrorId());
            responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

}
