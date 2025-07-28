package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.DictMsgHandler;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100417Request;
import com.minigod.zero.trade.hs.resp.EF01100417VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.*;


/**
 * 获取历史流水
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100417Request.class, responseVo = EF01100417VO.class)
public class EF01100417<T extends GrmRequestVO> extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if (request instanceof EF01100417Request) {
            EF01100417Request req = (EF01100417Request) request;
            Map<String, String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            String passwd = req.getPassword();
            if (StringUtils.isNotEmpty(passwd)) {
                reqMap.put(HsConstants.Fields.PASSWORD, passwd);
            }
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
            List<Object> listVO = new ArrayList<>();
            List<EF01100417VO> listVo = new ArrayList<>();
            EF01100417VO vo;
            String realType;
            String realStatus;
            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();

                vo = new EF01100417VO();
                vo.setBusinessDate(rowMap.get(HsConstants.Fields.INIT_DATE));
                vo.setSerialNo(rowMap.get(HsConstants.Fields.SERIAL_NO));
                vo.setBusinessFlag(rowMap.get(HsConstants.Fields.BUSINESS_FLAG));
                vo.setBusinessName(DictMsgHandler.getValueName(HsConstants.Fields.BUSINESS_FLAG, vo.getBusinessFlag(), request.getLang(), rowMap.get(HsConstants.Fields.BUSINESS_NAME)));
                String moneyType = rowMap.get(HsConstants.Fields.MONEY_TYPE);
                if (!StringUtils.isEmpty(moneyType)) {
                    moneyType = HsConstants.HsMoneyType.valueOf(moneyType).getMoneyType();
                }
                vo.setCurrency(moneyType);
                vo.setOccurBalance(rowMap.get(HsConstants.Fields.OCCUR_BALANCE));
                vo.setRemark(rowMap.get(HsConstants.Fields.REMARK));
                vo.setPostBalance(rowMap.get(HsConstants.Fields.POST_BALANCE));
                vo.setPositionStr(rowMap.get(HsConstants.Fields.POSITION_STR));
                listVO.add(vo);
            }

            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }


}
