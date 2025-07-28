package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.tool.beans.QuotationVO;
import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.constants.DataFormatUtil;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100403Request;
import com.minigod.zero.trade.hs.resp.EF01100403VO;
import com.minigod.zero.trade.hs.vo.GrmDataVO;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;


/**
 * 获取营业部编号
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100403Request.class,responseVo = EF01100403VO.class)
public class EF01100403<T extends GrmRequestVO>  extends T2sdkBiz<T> {
    @Resource
    protected HsFieldFormater fieldFormater;
    //@Resource
    //private QuotationService quotationServiceNew;
    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100403Request){
            EF01100403Request req = (EF01100403Request) request;
            Map<String,String> reqMap = new HashMap();
            reqMap.put(HsConstants.Fields.FUND_ACCOUNT,req.getFundAccount());
            String exchangeType = req.getExchangeType();
            if(StringUtils.isNotEmpty(exchangeType)){
                reqMap.put(HsConstants.Fields.EXCHANGE_TYPE,req.getExchangeType());
            }
            String stockCode= req.getStockCode();
            if(StringUtils.isNotEmpty(stockCode)){
                reqMap.put(HsConstants.Fields.STOCK_CODE,req.getStockCode());
            }
            reqMap.put(HsConstants.Fields.QUERY_MODE, "1"); // 查询模式0 明细 1 汇总
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
            EF01100403VO vo;
            String currentAmount ;
            String lastPrice;
            Double priceFromRedis = 0.0;
            String costPrice;
            Double incomeBalance;
            Double cost;
            String assetId;
            StkInfo assetInfo;
            String keepCostPrice;
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                vo = new EF01100403VO();
                currentAmount = rowMap.get(HsConstants.Fields.CURRENT_AMOUNT);
                keepCostPrice = rowMap.get(HsConstants.Fields.KEEP_COST_PRICE);
                vo.setExchangeType(rowMap.get(HsConstants.Fields.EXCHANGE_TYPE));
                vo.setStockCode(rowMap.get(HsConstants.Fields.STOCK_CODE));
                //判断保本价是否为零，过滤上个交易日清仓的股票
                assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(vo.getStockCode(), vo.getExchangeType()), vo.getExchangeType());
//                if(keepCostPrice.equals("0") && currentAmount.equals("0")){
//                    continue;
//                }

                double realSellAmount = NumberUtils.toDouble(rowMap.get("real_sell_amount"), 0.0d);
                double currentAmountDbl = NumberUtils.toDouble(currentAmount, 0.0d);
                if (realSellAmount == 0.0d && currentAmountDbl == 0.0d) {
                	continue;
                }

                vo.setStockName(rowMap.get(HsConstants.Fields.STOCK_NAME));
                vo.setAssetId(assetId);
                //从redis获取最新价
                QuotationVO quotationVO = null; // TODO quotationServiceNew.findQuotByAssetId(assetId);
                if (quotationVO != null) {
                    priceFromRedis = quotationVO.getPrice().doubleValue();
                    lastPrice = fieldFormater.format(HsConstants.Fields.AV_BUY_PRICE,String.valueOf(priceFromRedis));
                }else{
                    lastPrice = rowMap.get(HsConstants.Fields.LAST_PRICE);
                }
                costPrice = rowMap.get(HsConstants.Fields.COST_PRICE);
                vo.setCurrentQty(currentAmount);
                vo.setCostPrice(HsConstants.Fields.KEEP_COST_PRICE);
                vo.setCurrentQty(rowMap.get(HsConstants.Fields.ENABLE_AMOUNT));
                vo.setMarketValue(rowMap.get(HsConstants.Fields.MARKET_VALUE));
                vo.setLastPrice(lastPrice);
                vo.setMoneyType(rowMap.get(HsConstants.Fields.MONEY_TYPE));
                //因为前端不愿意修改，所以将买入均价修改为保本价
                vo.setAvBuyPrice(keepCostPrice);
                vo.setKeepCostPrice(keepCostPrice);
                vo.setAssetId(DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(vo.getStockCode(), vo.getExchangeType()), vo.getExchangeType()));
                if(Double.valueOf(currentAmount).compareTo(0.0d) == 0){
                	if (realSellAmount > 0.0d) {
                		// 当天清空
                		vo.setIncomeBalance(rowMap.get(HsConstants.Fields.INCOME_BALANCE));
                		vo.setIncomeRatio("--");
                	} else {
                		// 非当天清空
                		continue;
                	}
//                    vo.setIncomeBalance("0");
//                    vo.setIncomeRatio("0");
                }else if(StringUtils.isNotEmpty(costPrice)  && Double.valueOf(costPrice).compareTo(0.0) >0){
                    cost = Double.valueOf(costPrice)*Double.valueOf(currentAmount);
                    incomeBalance = Double.valueOf(lastPrice)*Double.valueOf(currentAmount) - cost;
                    vo.setIncomeBalance(fieldFormater.format(HsConstants.Fields.INCOME_BALANCE,String.valueOf(incomeBalance)));
                    vo.setIncomeRatio(fieldFormater.format(HsConstants.Fields.INCOME_RATIO, String.valueOf(incomeBalance / cost)));
                }else{
                    vo.setIncomeBalance(fieldFormater.format(HsConstants.Fields.INCOME_BALANCE,String.valueOf(Double.valueOf(lastPrice)*Double.valueOf(currentAmount))));
                    vo.setIncomeRatio(String.valueOf(1));
                }

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
