package com.minigod.zero.trade.hs.service;

import com.alibaba.fastjson2.JSON;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110173Request;
import com.minigod.zero.trade.hs.resp.EF01110173VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 历史资金流水-免密
 * @author sunline
 * @date 2020.6.24
 * @param <T>
 */

@Component
@GrmFunctionEntity(requestVo = EF01110173Request.class, responseVo = EF01110173VO.class)
public class EF01110173<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01110173Request) {
            EF01110173Request req = (EF01110173Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            String moneyType = req.getMoneyType();
            if (StringUtils.isNotEmpty(moneyType)) {
                reqMap.put(HsConstants.Fields.MONEY_TYPE, moneyType);
            }
            reqMap.put(HsConstants.Fields.BEGIN_DATE, req.getBeginDate());
            reqMap.put(HsConstants.Fields.END_DATE, req.getEndDate());
            reqMap.put(HsConstants.Fields.QUERY_DIRECTION, "0");
            oParamMap.putAll(reqMap);
        }
        return oParamMap;
    }

    @Override
    protected <R extends GrmRequestVO> GrmResponseVO parseResponse(GrmDataVO grmDataVO, Map<String, String> oParam, R request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        if (grmDataVO.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            List<Map<String, String>> list = grmDataVO.getResult();
            Iterator<Map<String, String>> iterator = list.iterator();
            List<EF01110173VO> listVO = new ArrayList<>();
            while (iterator.hasNext()) {
				Map<String,String> rowMap = iterator.next();
				EF01110173VO vo = JSON.parseObject(JSON.toJSONString(rowMap), EF01110173VO.class);
				listVO.add(vo);
            }
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
