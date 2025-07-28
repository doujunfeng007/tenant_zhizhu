package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.constants.DataFormatUtil;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100411Request;
import com.minigod.zero.trade.hs.resp.EF01100411VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 获取成交历史
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100411Request.class,responseVo = EF01100411VO.class)
public class EF01100411<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100411Request){
            EF01100411Request req = (EF01100411Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            String exchangeType = req.getExchangeType();
            if(StringUtils.isNotEmpty(exchangeType)){
                reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
            }
            reqMap.put(HsConstants.Fields.START_DATE,req.getStartDate());
            reqMap.put(HsConstants.Fields.END_DATE,req.getEndDate());
            reqMap.put(HsConstants.Fields.POSITION_STR,"0");
            reqMap.put(HsConstants.Fields.PASSWORD,req.getPassword());
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
            List<EF01100411VO> listVo = new ArrayList<>();
            EF01100411VO vo;
            String assetId;
            StkInfo assetInfo;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                    vo = new EF01100411VO();
                    vo.setInitDate(rowMap.get(HsConstants.Fields.INIT_DATE));
                    vo.setSerialNo(rowMap.get(HsConstants.Fields.SERIAL_NO));
                    vo.setExchangeType(rowMap.get(HsConstants.Fields.EXCHANGE_TYPE));
                    vo.setEntrustBs(rowMap.get(HsConstants.Fields.ENTRUST_BS));
                    vo.setStockCode(rowMap.get(HsConstants.Fields.STOCK_CODE));
                    vo.setBusinessAmount(rowMap.get(HsConstants.Fields.BUSINESS_AMOUNT));
                    vo.setBusinessPrice(rowMap.get(HsConstants.Fields.BUSINESS_PRICE));
                    vo.setBusinessBalance(rowMap.get(HsConstants.Fields.BUSINESS_BALANCE));
                    vo.setBusinessNo(rowMap.get(HsConstants.Fields.BUSINESS_NO));
                vo.setBusinessTime(rowMap.get(HsConstants.Fields.BUSINESS_TIME));
                    vo.setAssetId(DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(vo.getStockCode(), vo.getExchangeType()), vo.getExchangeType()));
                assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(vo.getStockCode(), vo.getExchangeType()), vo.getExchangeType());
                vo.setAssetId(assetId);
                assetInfo = grmCacheMgr.getAssetInfo(assetId);
                if(null != assetInfo){
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
