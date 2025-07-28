package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.EF01110149Request;
import com.minigod.zero.trade.hs.resp.EF01110149Fare;
import com.minigod.zero.trade.hs.resp.EF01110149VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 查询客户费用
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01110149Request.class,responseVo = EF01110149VO.class)
public class EF01110149<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    private static final String ZERO_FARE = "0.00";// 费用为零

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110149Request){
            EF01110149Request req = (EF01110149Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            reqMap.put(HsConstants.Fields.START_DATE, req.getStartDate());
            reqMap.put(HsConstants.Fields.END_DATE, req.getEndDate());
            if(StringUtils.isNotEmpty(req.getExchangeType())){
                reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
            }
            if(StringUtils.isNotEmpty(req.getLang())){
                reqMap.put(HsConstants.Fields.LANG,req.getLang());
            }
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();

        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            String sessionId = request.getSessionId();
            String isFilter = "";
            if (request instanceof EF01110149Request) {
                EF01110149Request ef01110149Request = (EF01110149Request) request;
                isFilter = ef01110149Request.getIsFilter();
            }
            List<Map<String, String>> list = grmDataVO.getResult();
            Iterator<Map<String, String>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                EF01110149VO vo = BeanHelper.copyProperties(rowMap, EF01110149VO.class);
                // 获取费用对照表数据
                Map<String, String> fareNames = grmCacheMgr.getFareNames(vo.getExchangeType(), oParam.get(HsConstants.Fields.LANG));
                this.transFares(vo, fareNames);

                if("v".equals(vo.getExchangeType())){
                    vo.setExchangeType("SZ");
                }
                if("t".equals(vo.getExchangeType())){
                    vo.setExchangeType("SH");
                }
                String assetId = DataFormatUtil.stkCodeToAssetId(vo.getStockCode(), vo.getExchangeType());
                vo.setAssetId(assetId);
                // 中华通持仓过滤
                if(!StringUtils.isEmpty(assetId)){
                    if("1".equals(isFilter) && (!assetId.endsWith("SH") && !assetId.endsWith("SZ"))){
                        continue;
                    }
                }
                StkInfo assetInfo = grmCacheMgr.getAssetInfo(assetId);
                if(null != assetInfo){
                    vo.setStockName(getStkNameOrTtional(sessionId,assetInfo.getStkName(), assetInfo.getTraditionalName(), getLang()));
                }
                responseVO.addResultData(vo);
            }
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

    // 封装费用名称 by licc
    private void transFares(EF01110149VO vo, Map<String, String> fareNames) {
        List<EF01110149Fare> fareList = new ArrayList();
        if (fareNames != null) {
            this.setFares(fareList, fareNames.get("0"), vo.getFare0());
            this.setFares(fareList, fareNames.get("1"), vo.getFare1());
            this.setFares(fareList, fareNames.get("2"), vo.getFare2());
            this.setFares(fareList, fareNames.get("3"), vo.getFare3());
            this.setFares(fareList, fareNames.get("4"), vo.getFare4());
            this.setFares(fareList, fareNames.get("5"), vo.getFare5());
            this.setFares(fareList, fareNames.get("6"), vo.getFare6());
            this.setFares(fareList, fareNames.get("7"), vo.getFare7());
            this.setFares(fareList, fareNames.get("8"), vo.getFare8());
            this.setFares(fareList, fareNames.get("x"), vo.getFarex());
        }
        vo.setFareList(fareList);
    }

    private void setFares(List<EF01110149Fare> fareList, String text, String amt) {
        if (amt != null && !ZERO_FARE.equals(amt)) {
            EF01110149Fare fare = new EF01110149Fare();
            fare.setText(text);
            fare.setFare(amt);
            fareList.add(fare);
        }
    }

}
