package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100830Request;
import com.minigod.zero.trade.hs.resp.ClientCashSumInfo;
import com.minigod.zero.trade.hs.resp.EF01100830VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 客户信息查询请求
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100830Request.class,responseVo = ClientCashSumInfo.class)
public class EF01100830<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100830Request){
            EF01100830Request req = (EF01100830Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.BANK_NO,req.getBankNo());
            reqMap.put(HsConstants.Fields.MONEY_TYPE,req.getTransType());
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
            List<Object>  listVO = new ArrayList<>();
            List<EF01100830VO> listVo = new ArrayList<>();
            EF01100830VO vo;
            EF01100830Request req = (EF01100830Request) request;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01100830VO();
                vo.setSerialNo(rowMap.get(HsConstants.Fields.SERIAL_NO));
                vo.setInitDate(rowMap.get(HsConstants.Fields.INIT_DATE));
                vo.setBankNo(rowMap.get(HsConstants.Fields.BANK_NO));
                vo.setMoneyType(rowMap.get(HsConstants.Fields.MONEY_TYPE));
                vo.setCurrDate(rowMap.get(HsConstants.Fields.CURR_DATE));
                vo.setSourceFlag(rowMap.get(HsConstants.Fields.SOURCE_FLAG));
                vo.setBktransStatus(rowMap.get(HsConstants.Fields.BKTRANS_STATUS));
                vo.setTransType(rowMap.get(HsConstants.Fields.TRANS_TYPE));
                vo.setBankAccount(rowMap.get(HsConstants.Fields.BANK_ACCOUNT));
                vo.setClientAccount(rowMap.get(HsConstants.Fields.CLIENT_ACCOUNT));
                vo.setOccurBalance(rowMap.get(HsConstants.Fields.OCCUR_BALANCE));
                vo.setCurrTime(rowMap.get(HsConstants.Fields.CURR_TIME));
                vo.setReportTime(rowMap.get(HsConstants.Fields.REPORT_TIME));
                vo.setPositionStr(rowMap.get(HsConstants.Fields.POSITION_STR));
                vo.setExchangeType(rowMap.get(HsConstants.Fields.EXCHANGE_TYPE));
                vo.setBusinType(rowMap.get(HsConstants.Fields.BUSIN_TYPE));
                vo.setDebitCuy(rowMap.get(HsConstants.Fields.DEBIT_CUY));
                vo.setCreditCuy(rowMap.get(HsConstants.Fields.CREDIT_CUY));
                vo.setTradeSign(rowMap.get(HsConstants.Fields.TRADE_SIGN));
                vo.setRemark(rowMap.get(HsConstants.Fields.REMARK));
                vo.setEntrustWay(rowMap.get(HsConstants.Fields.ENTRUST_WAY));
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
