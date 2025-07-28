package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.constants.DataFormatUtil;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100303Request;
import com.minigod.zero.trade.hs.resp.EF01100303VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 获取当日委托信息
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100303Request.class,responseVo = EF01100303VO.class)
public class EF01100303<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100303Request){
            EF01100303Request req = (EF01100303Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            String exchangeType = req.getExchangeType();
            if(StringUtils.isNotEmpty(exchangeType)){
                reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
            }
            reqMap.put(HsConstants.Fields.PASSWORD,req.getPassword());
            reqMap.put(HsConstants.Fields.QUERY_FLAG,"0");
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
            EF01100303VO vo;
            String exchangeType ;
            String entrustStatus;
            String assetId;
            StkInfo assetInfo;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01100303VO();
                vo.setEntrustNo(rowMap.get(HsConstants.Fields.ENTRUST_NO));
                exchangeType =  rowMap.get(HsConstants.Fields.EXCHANGE_TYPE);
                vo.setExchangeType(exchangeType);
                vo.setStockCode(rowMap.get(HsConstants.Fields.STOCK_CODE));
                vo.setMoneyType(rowMap.get(HsConstants.Fields.MONEY_TYPE));
                vo.setEntrustBs(rowMap.get(HsConstants.Fields.ENTRUST_BS));
                vo.setEntrustProp(rowMap.get(HsConstants.Fields.ENTRUST_PROP));
                vo.setEntrustAmount(rowMap.get(HsConstants.Fields.ENTRUST_AMOUNT));
                vo.setEntrustPrice(rowMap.get(HsConstants.Fields.ENTRUST_PRICE));
                entrustStatus = rowMap.get(HsConstants.Fields.ENTRUST_STATUS);
                vo.setEntrustStatus(entrustStatus);
                if(entrustStatus.equals("0") || entrustStatus.equals("2")
                        || entrustStatus.equals("5") || entrustStatus.equals("7")){
                    vo.setCancelable("1");
                    if(!exchangeType.equals(HsConstants.HSExchageType.US.getCode())){
                        vo.setModifiable("1");
                    }else{
                        vo.setModifiable("0");
                    }
                }else{
                    vo.setCancelable("0");
                    vo.setModifiable("0");
                }
                vo.setBusinessAmount(rowMap.get(HsConstants.Fields.BUSINESS_AMOUNT));
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
