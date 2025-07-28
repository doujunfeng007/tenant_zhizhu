package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.enums.Hs2BsFlag;
import com.minigod.zero.trade.enums.HsFundOrderStatus;
import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.req.EF01110150Request;
import com.minigod.zero.trade.hs.resp.EF01110150VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 查询客户费用(免密查询客户历史成交明细)
 * Created by sunline on 2020/05/11 13:16.
 * sunline
 */
@Slf4j
@Component
@GrmFunctionEntity(requestVo = EF01110150Request.class,responseVo = EF01110150VO.class)
public class EF01110150<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01110150Request){
            EF01110150Request req = (EF01110150Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            reqMap.put(HsConstants.Fields.START_DATE, req.getStartDate());
            reqMap.put(HsConstants.Fields.END_DATE, req.getEndDate());
            if(StringUtils.isNotBlank(req.getStockCode())){
                reqMap.put(HsConstants.Fields.STOCK_CODE, req.getStockCode());
            }
            if(StringUtils.isNotBlank(req.getExchangeType())){
                reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
            }
            reqMap.put(HsConstants.Fields.ALLOCATION_ID, "0");
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam,
																   R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();

        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            String sessionId = request.getSessionId();
            List<Map<String, String>> list = grmDataVO.getResult();
            Iterator<Map<String, String>> iterator = list.iterator();
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                EF01110150VO vo = com.alibaba.fastjson2.JSON.parseObject(com.alibaba.fastjson2.JSON.toJSONString(rowMap), EF01110150VO.class);
                String assetId = DataFormatUtil.stkCodeToAssetId(vo.getStockCode(),vo.getExchangeType());
                vo.setAssetId(assetId);
                StkInfo assetInfo = grmCacheMgr.getAssetInfo(assetId);
                if (null != assetInfo) {
                    vo.setStockName(rowMap.get(HsConstants.Fields.STOCK_NAME));
                    vo.setStockNameZhCN(assetInfo.getStkNameLong());
                    vo.setStockNameZhHK(assetInfo.getTraditionalName());
                }
                vo.setBsFlag(Hs2BsFlag.getByPortfolio(vo.getBsFlag()));
                //调整订单状态的显示（用于app端）
                String orderStatus = HsFundOrderStatus.getZszzByHs(vo.getOrderStatus());
                vo.setOrderStatus(orderStatus);
                if(StringUtils.isNotBlank(vo.getBusinessTime())){
                    if(vo.getBusinessTime().length() == 5) vo.setBusinessTime("0"+vo.getBusinessTime());
                }
                responseVO.addResultData(vo);
            }
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}

