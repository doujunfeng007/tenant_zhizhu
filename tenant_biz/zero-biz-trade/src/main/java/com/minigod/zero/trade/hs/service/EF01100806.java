package com.minigod.zero.trade.hs.service;

import com.minigod.zero.core.tool.beans.StkInfo;
import com.minigod.zero.trade.hs.constants.DataFormatUtil;
import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100806Request;
import com.minigod.zero.trade.hs.resp.EF01100806VO;
import com.minigod.zero.trade.hs.resp.SystemMaintainVO;
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
@GrmFunctionEntity(requestVo = EF01100806Request.class,responseVo = EF01100806VO.class)
public class EF01100806<T extends GrmRequestVO>  extends T2sdkBiz<T> {

    @Override
    protected Map<String, String> parseParamsFromRequestObject(GrmRequestVO request, Map<String, String> oParamMap) {
        if(request instanceof EF01100806Request){
            EF01100806Request req = (EF01100806Request) request;
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
            reqMap.put(HsConstants.Fields.PASSWORD,req.getPassword());
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
//            log.info("EF01100806.resp={}", JSON.toJSONString(list));
            Iterator<Map<String,String>> iterator = list.iterator();
            List<EF01100806VO> listVO = new ArrayList<>();
            String entrustStatus ;
            String exchangeType;
            String assetId;
            EF01100806Request req = (EF01100806Request) request;
            SystemMaintainVO maintainVO = grmCacheMgr.getSystemMaintainVO();
            while(iterator.hasNext()){
                Map<String,String> rowMap = iterator.next();
                String conditionType = rowMap.get(HsConstants.Fields.CONDITION_TYPE);
                if (req.getFilterType().equals(HsConstants.EntrustFilterType.NORMAL.getCode())) {
                    //需要的是普通单,只保留“普通单”和“执行完成的条件单”
                    //strategy_status策略状态（1-未执行;2-执行中;3-执行完成;4-暂停;5-强制释放）
                    String strategyStatus = rowMap.get(HsConstants.Fields.STRATEGY_STATUS);
                    if (StringUtils.isNotBlank(conditionType) && !"3".equals(strategyStatus) ) {
                        //未执行完成的条件单，过滤掉
                        continue;
                    }
                }else if (req.getFilterType().equals(HsConstants.EntrustFilterType.STRATEGY.getCode())) {
                    //需要的是条件单
                    if (StringUtils.isBlank(conditionType)) {
                        //普通委托单，过滤掉
                        continue;
                    }
                }
                EF01100806VO vo = new EF01100806VO();
                vo.setEntrustNo(rowMap.get(HsConstants.Fields.ENTRUST_NO));
                vo.setCurrDate(rowMap.get(HsConstants.Fields.CURR_DATE));
                vo.setCurrTime(rowMap.get(HsConstants.Fields.CURR_TIME));
                exchangeType = rowMap.get(HsConstants.Fields.EXCHANGE_TYPE);
                vo.setExchangeType(exchangeType);
                vo.setStockCode(rowMap.get(HsConstants.Fields.STOCK_CODE));
                vo.setEntrustBs(rowMap.get(HsConstants.Fields.ENTRUST_BS));
                vo.setEntrustProp(rowMap.get(HsConstants.Fields.ENTRUST_PROP));
                vo.setEntrustAmount(rowMap.get(HsConstants.Fields.ENTRUST_AMOUNT));
                vo.setEntrustPrice(rowMap.get(HsConstants.Fields.ENTRUST_PRICE));
                vo.setSessionType(rowMap.get(HsConstants.Fields.SESSION_TYPE));
                entrustStatus = rowMap.get(HsConstants.Fields.ENTRUST_STATUS);
                vo.setEntrustStatus(entrustStatus);
                if(entrustStatus.equals("0") || entrustStatus.equals("2")
                        || entrustStatus.equals("7")){
                    vo.setCancelable("1");
                    if(exchangeType.equals(HsConstants.HSExchageType.US.getCode()) && maintainVO.getMaintainFlag()==0){
                        // 美股改单，仅对维护模式开启后的白名单接口开放，非维护模式的美股改单不开放
                        vo.setModifiable("0");
                    }else{
                        vo.setModifiable("1");
                    }
                }else{
                    vo.setCancelable("0");
                    vo.setModifiable("0");
                }
                // 暗盘不能改单
                /*if (HsConstants.HSExchageType.HK.getCode().equals(exchangeType)
                		&& "2".equals(rowMap.get(HsConstants.Fields.SESSION_TYPE))) {
                	// 交易阶段标识（0-正常订单交易（默认），1-盘前盘后交易，2-暗盘交易）
                	vo.setModifiable("0"); // 不能改单
                }*/

                vo.setBusinessAmount(rowMap.get(HsConstants.Fields.BUSINESS_AMOUNT));
                vo.setBusinessPrice(rowMap.get(HsConstants.Fields.BUSINESS_PRICE));
                vo.setBusinessBalance(rowMap.get(HsConstants.Fields.BUSINESS_BALANCE));
                vo.setBusinessTime(rowMap.get(HsConstants.Fields.BUSINESS_TIME));
                vo.setReportTime(rowMap.get(HsConstants.Fields.REPORT_TIME));
                vo.setMoneyType(rowMap.get(HsConstants.Fields.MONEY_TYPE));
                vo.setEntrustTime(rowMap.get(HsConstants.Fields.ENTRUST_TIME));

                vo.setStrategyType(rowMap.get(HsConstants.Fields.STRATEGY_TYPE));
                vo.setStrategyEnddate(rowMap.get(HsConstants.Fields.STRATEGY_ENDDATE));
                vo.setStrategyStatus(rowMap.get(HsConstants.Fields.STRATEGY_STATUS));
                vo.setConditionType(rowMap.get(HsConstants.Fields.CONDITION_TYPE));

                assetId = DataFormatUtil.stkCodeToAssetId(DataFormatUtil.stockCodeFormat(vo.getStockCode(), vo.getExchangeType()),vo.getExchangeType());
                vo.setAssetId(assetId);
                StkInfo assetInfo = grmCacheMgr.getAssetInfo(assetId);
                if(null != assetInfo){
                    vo.setLotSize(String.valueOf(assetInfo.getLotSize()));
                    vo.setStockName(getStkNameOrTtional(sessionId,assetInfo.getStkName(), assetInfo.getTraditionalName(),getLang()));
                    vo.setSecSType(assetInfo.getSecSType());
                }
                if(StringUtils.isEmpty(vo.getStockName())){
                    vo.setStockName(rowMap.get(HsConstants.Fields.STOCK_NAME));
                }
                listVO.add(vo);
            }
            Collections.sort(listVO, new TimeDescComparator());// 委托时间降序排列
            responseVO.setResult(listVO);
        }
        responseVO.setErrorId(grmDataVO.getErrorId());
        responseVO.setErrorMsg(grmDataVO.getErrorMsg());
        return responseVO;
    }

    /**
     * 委托时间降序排列
     */
    private static class TimeDescComparator implements Comparator<EF01100806VO> {

        @Override
        public int compare(EF01100806VO o1, EF01100806VO o2) {
            String o1Timestamp = o1.getCurrDate() + DataFormatUtil.intTimeCompletion(o1.getCurrTime());
            String o2Timestamp = o2.getCurrDate() + DataFormatUtil.intTimeCompletion(o2.getCurrTime());
            return o2Timestamp.compareTo(o1Timestamp);
        }
    }
}
