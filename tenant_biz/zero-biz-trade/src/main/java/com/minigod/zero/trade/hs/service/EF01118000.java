package com.minigod.zero.trade.hs.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01118000Request;
import com.minigod.zero.trade.hs.resp.EF01118000VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @description: 服务_海外_今日持仓盈亏
 * @author: sunline
 * @date: 2019/7/4 10:50
 * @version: v1.0
 */

@Component
@GrmFunctionEntity(requestVo = EF01118000Request.class, responseVo = EF01118000VO.class)
public class EF01118000<T extends GrmRequestVO> extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {

        if (request instanceof EF01118000Request) {
            EF01118000Request req = (EF01118000Request) request;
            Map<String, String> reqMap = Maps.newHashMap();
            reqMap.put(HsConstants.Fields.CLIENT_ID, req.getClientId());
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT, req.getFundAccount());
            reqMap.put(HsConstants.Fields.EXCHANGE_TYPE, req.getExchangeType());
            reqMap.put(HsConstants.Fields.STOCK_CODE, req.getStockCode());

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
            List<EF01118000VO> listVO = Lists.newArrayList();
            EF01118000VO vo;

            while (iterator.hasNext()) {
                Map<String, String> rowMap = iterator.next();
                vo = new EF01118000VO();
                vo.setClientId(rowMap.get(HsConstants.Fields.CLIENT_ID));
                vo.setFundAccount(rowMap.get(HsConstants.Fields.FUND_ACCOUNT));
                vo.setExchangeType(rowMap.get(HsConstants.Fields.EXCHANGE_TYPE));
                vo.setStockCode(rowMap.get(HsConstants.Fields.STOCK_CODE));
                vo.setMoneyType(rowMap.get(HsConstants.Fields.MONEY_TYPE));
                vo.setCurrentAmount(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.CURRENT_AMOUNT)) ? 0.0 :Double.parseDouble(rowMap.get(HsConstants.Fields.CURRENT_AMOUNT)));
                vo.setAssetPrice(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.ASSET_PRICE))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.ASSET_PRICE)));
                vo.setBeginAmount(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.BEGIN_AMOUNT))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.BEGIN_AMOUNT)));
                vo.setClosePrice(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.CLOSE_PRICE))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.CLOSE_PRICE)));
                vo.setRealBuyBalance(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.REAL_BUY_BALANCE))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.REAL_BUY_BALANCE)));
                vo.setRealSellBalace(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.REAL_SELL_BALANCE))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.REAL_SELL_BALANCE)));
                vo.setDepositAmount(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.DEPOSIT_AMOUNT))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.DEPOSIT_AMOUNT)));
                vo.setDepositValue(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.DEPOSIT_VALUE))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.DEPOSIT_VALUE)));
                vo.setWithdrawAmount(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.WITHDRAW_AMOUNT))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.WITHDRAW_AMOUNT)));
                vo.setWithdrawValue(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.WITHDRAW_VALUE))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.WITHDRAW_VALUE)));
                vo.setQuantityAllotted(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.QUANTITY_ALLOTTED))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.QUANTITY_ALLOTTED)));
                vo.setFinalPrice(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.FINAL_PRICE))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.FINAL_PRICE)));
                vo.setReduceBalance(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.REDUCE_BALANCE))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.REDUCE_BALANCE)));
                vo.setTotalRightsFare(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.TOTAL_RIGHTS_FARE))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.TOTAL_RIGHTS_FARE)));
                vo.setAccruedInterest(StringUtils.isEmpty(rowMap.get(HsConstants.Fields.ACCRUED_INTEREST))?0.0:Double.parseDouble(rowMap.get(HsConstants.Fields.ACCRUED_INTEREST)));

                listVO.add(vo);
            }

            responseVO.setResult(listVO);
        }

        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }
}
