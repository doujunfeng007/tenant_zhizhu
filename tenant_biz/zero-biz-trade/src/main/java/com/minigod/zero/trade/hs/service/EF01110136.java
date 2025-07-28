package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110136Request;
import com.minigod.zero.trade.hs.resp.EF01110136VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 查询客户费用
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110136Request.class,responseVo = EF01110136VO.class)
public class EF01110136<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110136Request){
            EF01110136Request req = (EF01110136Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            reqMap.put(HsConstants.Fields.ENTRUST_WAY,req.getEntrustWay());
            reqMap.put(HsConstants.Fields.BEGIN_DATE, req.getBeginDate());
            reqMap.put(HsConstants.Fields.END_DATE, req.getEndDate());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
            reqMap.put("show_his","0");
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
            List<EF01110136VO>  listVO = new ArrayList<>();
            EF01110136VO vo;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110136VO();
                vo.setBranchNo(rowMap.get(HsConstants.Fields.BRANCH_NO));
                vo.setClientId(rowMap.get(HsConstants.Fields.CLIENT_ID));
                vo.setFundAccount(rowMap.get(HsConstants.Fields.FUND_ACCOUNT));
                vo.setFareDict(rowMap.get(HsConstants.Fields.FARE_DICT));
                vo.setFeeType(rowMap.get(HsConstants.Fields.FEE_TYPE));
                vo.setFeeCount(rowMap.get(HsConstants.Fields.FEE_COUNT));
                vo.setFareType(rowMap.get(HsConstants.Fields.FARE_TYPE));
                vo.setExchangeType(rowMap.get(HsConstants.Fields.EXCHANGE_TYPE));
                vo.setStockType(rowMap.get(HsConstants.Fields.STOCK_TYPE));
                vo.setStockCode(rowMap.get(HsConstants.Fields.STOCK_CODE));
                vo.setEntrustBs(rowMap.get(HsConstants.Fields.ENTRUST_BS));
                vo.setEntrustWay(rowMap.get(HsConstants.Fields.ENTRUST_WAY));
                vo.setMoneyType(rowMap.get(HsConstants.Fields.MONEY_TYPE));
                vo.setMinFare(rowMap.get(HsConstants.Fields.MIN_FARE));
                vo.setMaxFare(rowMap.get(HsConstants.Fields.MAX_FARE));
                vo.setPrecisionFlag(rowMap.get(HsConstants.Fields.PRECISION_FLAG));
                vo.setPrecision(rowMap.get(HsConstants.Fields.PRECISION));
                vo.setFeeCountFix(rowMap.get(HsConstants.Fields.FEE_COUNT_FIX));
                vo.setBeginDate(rowMap.get(HsConstants.Fields.BEGIN_DATE));
                vo.setEndDate(rowMap.get(HsConstants.Fields.END_DATE));
                vo.setFareStr(rowMap.get(HsConstants.Fields.FARE_STR));
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }


}
