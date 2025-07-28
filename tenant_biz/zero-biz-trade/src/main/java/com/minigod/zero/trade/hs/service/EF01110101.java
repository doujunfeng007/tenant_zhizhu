package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson.JSON;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.EF01110101Request;
import com.minigod.zero.trade.hs.resp.EF01110101VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 10101 查询最大可卖
 */
@Slf4j
@Component
@GrmFunctionEntity(requestVo = EF01110101Request.class, responseVo = EF01110101VO.class)
public class EF01110101<T extends GrmRequestVO> extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110101Request) {
            EF01110101Request req = (EF01110101Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.STOCK_CODE, req.getStockCode());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
            if (StringUtils.isNotBlank(req.getEntrustProp()) &&
                    GrmHsConstants.EntrustProp.OLI.getCode().equals(req.getEntrustProp())) {
                // 碎股单单独处理
                reqMap.put(HsConstants.Fields.ENTRUST_PROP, req.getEntrustProp());
            }
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            List<Map<String, String>> list = grmDataVO.getResult();
            List<EF01110101VO> listVO = new ArrayList<>();
            Iterator<Map<String, String>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                EF01110101VO vo = JSON.parseObject(JSON.toJSONString(rowMap), EF01110101VO.class);
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        try {
            // 碎股交易单独处理
            checkOLIResponse(request, responseVO);
        } catch (Exception e) {
            log.error("处理碎股最大可卖股票数量异常, request:{}, response:{}", JSON.toJSONString(request), JSON.toJSONString(responseVO), e);
        }
        return responseVO;
    }

    private <R extends GrmRequestVO> void checkOLIResponse(R request, GrmResponseVO responseVO) {
        if (request instanceof EF01110101Request) {
            EF01110101Request req = (EF01110101Request) request;
            if (StringUtils.isNotBlank(req.getEntrustProp()) &&
                    GrmHsConstants.EntrustProp.OLI.getCode().equals(req.getEntrustProp())) {
                log.info("request:{}", JSON.toJSONString(request));
                log.info("responseVO:{}", JSON.toJSONString(responseVO));

                String assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(req.getStockCode(), req.getExchangeType()), req.getExchangeType());
                StkInfo assetInfo = grmCacheMgr.getAssetInfo(assetId);

                log.info("assetInfo:{}", JSON.toJSONString(assetInfo));
                if (assetInfo != null) {
                    List<?> objects = responseVO.resultData();
                    if (!CollectionUtils.isEmpty(objects)) {
                        EF01110101VO eF01110101VO = (EF01110101VO) objects.get(0);
                        Integer enableAmount = Integer.valueOf(eF01110101VO.getEnableAmount());
                        eF01110101VO.setEnableAmount(String.valueOf(enableAmount % assetInfo.getLotSize()));
                    }
                }
            }
        }
    }
}
