package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.GrmHsConstants;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110103Request;
import com.minigod.zero.trade.hs.resp.EF01110103VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * 服务_海外_委托确认
 * @author sunline
 * @date 2020.7.20
 * @param <T>
 */

@Component
@GrmFunctionEntity(requestVo = EF01110103Request.class, responseVo = EF01110103VO.class)
public class EF01110103<T extends GrmRequestVO> extends T2sdkBiz<T> {

    private static Logger logger = LoggerFactory.getLogger(EF01110103.class);

    @Resource
    private GrmCacheMgr grmCacheMgr;

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110103Request){
            EF01110103Request req = (EF01110103Request) request;
            logger.info("EF01110103.req={}", JSON.toJSON(req));
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.STOCK_CODE, req.getStockCode());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
            reqMap.put(HsConstants.Fields.ENTRUST_PRICE, req.getEntrustPrice());
            if (GrmHsConstants.ExchangeType.US.getExchangeType().equals(req.getExchangeType())){
				if(StringUtils.isNotBlank(req.getSessionType())){
					reqMap.put(HsConstants.Fields.SESSION_TYPE, req.getSessionType());
				}
            }
            reqMap.put(HsConstants.Fields.ENTRUST_AMOUNT,req.getEntrustAmount());
            reqMap.put(HsConstants.Fields.ENTRUST_BS,req.getEntrustBs());
            reqMap.put(HsConstants.Fields.ENTRUST_TYPE,req.getEntrustType());
            reqMap.put(HsConstants.Fields.ENTRUST_PROP,req.getEntrustProp());
            reqMap.put(HsConstants.Fields.SHORTSELL_TYPE,req.getShortsellType());
            reqMap.put(HsConstants.Fields.HEDGE_FLAG,req.getHedgeFlag());
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();

        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            List<Map<String, String>> list = grmDataVO.getResult();
            List<EF01110103VO> listVO = new ArrayList<>();
            Iterator<Map<String, String>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                EF01110103VO vo = JSON.parseObject(JSON.toJSONString(rowMap), EF01110103VO.class);
                listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
