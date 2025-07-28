package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.EF01100839Request;
import com.minigod.zero.trade.hs.resp.EF01100839VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 获取当日股票流水
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100839Request.class, responseVo = EF01100839VO.class)
public class EF01100839<T extends GrmRequestVO> extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01100839Request) {
            EF01100839Request req = (EF01100839Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.QUERY_DIRECTION, "0");
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        String assetId;
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            String sessionId = request.getSessionId();
            EF01100839Request req = (EF01100839Request) request;
            List<Map<String, String>> list = grmDataVO.getResult();
            Iterator<Map<String, String>> iterator = list.iterator();
            List<EF01100839VO> listVo = new ArrayList<>();
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                String retExchangeType = rowMap.get(HsConstants.Fields.EXCHANGE_TYPE);
                if(StringUtils.isNotBlank(req.getExchangeType()) && !req.getExchangeType().equals(retExchangeType)){
                    //根据exchangeType过滤数据
                    continue;
                }
                EF01100839VO vo = new EF01100839VO();
                vo.setBusinessDate(rowMap.get(HsConstants.Fields.INIT_DATE));
                vo.setSerialNo(rowMap.get(HsConstants.Fields.SERIAL_NO));
                vo.setBusinessFlag(rowMap.get(HsConstants.Fields.BUSINESS_FLAG));
                vo.setBusinessName(DictMsgHandler.getValueName(HsConstants.Fields.BUSINESS_FLAG, vo.getBusinessFlag(), request.getLang(), rowMap.get(HsConstants.Fields.BUSINESS_NAME)));
                vo.setMoneyType(rowMap.get(HsConstants.Fields.MONEY_TYPE));
                vo.setStockCode(rowMap.get(HsConstants.Fields.STOCK_CODE));
                vo.setOccurAmount(rowMap.get(HsConstants.Fields.OCCUR_AMOUNT));
                vo.setPostAmount(rowMap.get(HsConstants.Fields.POST_AMOUNT));
                vo.setLocaleRemark(rowMap.get(HsConstants.Fields.LOCALE_REMARK));
                vo.setPositionStr(rowMap.get(HsConstants.Fields.POSITION_STR));
                vo.setExchangeType(rowMap.get(HsConstants.Fields.EXCHANGE_TYPE));
                assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(vo.getStockCode(), vo.getExchangeType()), vo.getExchangeType());
                StkInfo assetInfo = grmCacheMgr.getAssetInfo(assetId);
                if(assetInfo != null){
                    vo.setStockName(getStkNameOrTtional(sessionId,assetInfo.getStkName(), assetInfo.getTraditionalName(),getLang()));
                }
                vo.setAssetId(assetId);
                listVo.add(vo);
            }

            responseVO.setResult(listVo);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }


}
