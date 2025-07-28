package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110135Request;
import com.minigod.zero.trade.hs.resp.EF01110135VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 管理客户费用
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110135Request.class,responseVo = EF01110135VO.class)
public class EF01110135<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110135Request){
            EF01110135Request req = (EF01110135Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.BRANCH_NO,req.getBranchNo());
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            reqMap.put(HsConstants.Fields.ACTION_IN,req.getActionIn());
            reqMap.put(HsConstants.Fields.CLIENT_ID,req.getClientId());
            reqMap.put(HsConstants.Fields.FARE_DICT,req.getFareDict());
            reqMap.put(HsConstants.Fields.FEE_TYPE,req.getFeeType());
            reqMap.put(HsConstants.Fields.FARE_TYPE,req.getFareType());
            reqMap.put(HsConstants.Fields.FEE_COUNT,req.getFeeCount());
            reqMap.put(HsConstants.Fields.STOCK_CODE,req.getStockCode());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
            reqMap.put(HsConstants.Fields.STOCK_TYPE,req.getStockType());
            reqMap.put(HsConstants.Fields.ENTRUST_BS,req.getEntrustBs());
            reqMap.put(HsConstants.Fields.ENTRUST_WAY,req.getEntrustWay());
            reqMap.put(HsConstants.Fields.MONEY_TYPE,req.getMoneyType());
            reqMap.put(HsConstants.Fields.MIN_FARE,req.getMinFare());
            reqMap.put(HsConstants.Fields.MAX_FARE,req.getMaxFare());
            reqMap.put(HsConstants.Fields.PRECISION_FLAG,req.getPrecisionFlag());
            reqMap.put(HsConstants.Fields.PRECISION,req.getPrecision());
            reqMap.put(HsConstants.Fields.FEE_COUNT_FIX,req.getFeeCountFix());
            reqMap.put(HsConstants.Fields.BEGIN_DATE, req.getBeginDate());
            reqMap.put(HsConstants.Fields.END_DATE, req.getEndDate());
            if(!"2".equals(req.getActionIn())){
                reqMap.put(HsConstants.Fields.FARE_STR,req.getFareStr());
            }
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
            List<EF01110135VO>  listVO = new ArrayList<>();
            EF01110135VO vo;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01110135VO();
                vo.setOpRemark(rowMap.get(HsConstants.Fields.OP_REMARK));
                vo.setInitDate(rowMap.get(HsConstants.Fields.INIT_DATE));
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
