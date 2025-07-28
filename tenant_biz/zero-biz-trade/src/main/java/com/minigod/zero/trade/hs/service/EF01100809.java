package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.constants.DataFormatUtil;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100809Request;
import com.minigod.zero.trade.hs.resp.EF01100809VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 获取委托明细
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100809Request.class,responseVo = EF01100809VO.class)
public class EF01100809<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100809Request){
            EF01100809Request req = (EF01100809Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            String exchangeType = req.getExchangeType();
            if(StringUtils.isNotEmpty(exchangeType)){
                reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
            }
            String stockCode= req.getStockCode();
            if(StringUtils.isNotEmpty(stockCode)){
                reqMap.put(HsConstants.Fields.STOCK_CODE,req.getStockCode());
            }
            reqMap.put(HsConstants.Fields.PASSWORD,req.getPassword());
            reqMap.put(HsConstants.Fields.RECORD_NO,req.getEntrustNo());
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String,String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if(grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
            String sessionId = request.getSessionId();
            List<Map<String,String>> list =  grmDataVO.getResult();
            Iterator<Map<String,String>> iterator = list.iterator();
            List<Object>  listVO = new ArrayList<>();
            EF01100809VO vo;
            String entrustStatus ;
            String exchangeType;
            String assetId;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01100809VO();
                vo.setEntrustNo(rowMap.get(HsConstants.Fields.ENTRUST_NO));
                vo.setCurrDate(rowMap.get(HsConstants.Fields.CURR_DATE));
                vo.setCurrTime(rowMap.get(HsConstants.Fields.CURR_TIME));
                exchangeType = rowMap.get(HsConstants.Fields.EXCHANGE_TYPE);
                vo.setExchangeType(exchangeType);
                vo.setStockCode(rowMap.get(HsConstants.Fields.STOCK_CODE));
                vo.setEntrustBs(rowMap.get(HsConstants.Fields.ENTRUST_BS));
                vo.setEntrustProp(rowMap.get(HsConstants.Fields.ENTRUST_PROP));
                vo.setEntrustAmount(rowMap.get(HsConstants.Fields.ENTRUST_AMOUNT));
                vo.setEntrustPrice(rowMap.get(HsConstants.Fields.ENTRUST_PRICE));
                entrustStatus = rowMap.get(HsConstants.Fields.ENTRUST_STATUS);
                vo.setEntrustStatus(entrustStatus);
                vo.setBusinessAmount(rowMap.get(HsConstants.Fields.BUSINESS_AMOUNT));
                vo.setBusinessPrice(rowMap.get(HsConstants.Fields.BUSINESS_PRICE));
                vo.setBusinessBalance(rowMap.get(HsConstants.Fields.BUSINESS_BALANCE));
                vo.setBusinessTime(rowMap.get(HsConstants.Fields.BUSINESS_TIME));
                vo.setReportTime(rowMap.get(HsConstants.Fields.REPORT_TIME));
                vo.setMoneyType(rowMap.get(HsConstants.Fields.MONEY_TYPE));
                vo.setEntrustTime(rowMap.get(HsConstants.Fields.ENTRUST_TIME));
                assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(vo.getStockCode(), vo.getExchangeType()),vo.getExchangeType());
                vo.setAssetId(assetId);
                StkInfo assetInfo = grmCacheMgr.getAssetInfo(assetId);
                if(null != assetInfo){
                    vo.setLotSize(String.valueOf(assetInfo.getLotSize()));
                    vo.setStockName(getStkNameOrTtional(sessionId,assetInfo.getStkName(), assetInfo.getTraditionalName(),getLang()));
                }
                if(StringUtils.isEmpty(vo.getStockName())){
                    vo.setStockName(rowMap.get(HsConstants.Fields.STOCK_NAME));
                }
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }


}
