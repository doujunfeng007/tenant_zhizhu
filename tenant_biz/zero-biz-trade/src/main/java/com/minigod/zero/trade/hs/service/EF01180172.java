package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.GrmFunctions;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100839Request;
import com.minigod.zero.trade.hs.req.EF01100840Request;
import com.minigod.zero.trade.hs.resp.StockJourRecord;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Component
@GrmFunctionEntity(requestVo = EF01100840Request.class, responseVo = StockJourRecord.class)
public class EF01180172 extends Abstractbiz {

    @Resource
    private EF01110114 ef01110114;

    @Resource
    private EF01110172 ef01110172;

    @Override
    protected <T extends GrmRequestVO> GrmResponseVO request(T request) throws Exception {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        EF01100840Request req = (EF01100840Request) request;
        String isFilter = req.getIsFilter();
        String endDate = req.getEndDate();
        String dateStr = DateFormatUtils.format(new Date(), HsConstants.yyyyMMdd);
        List<StockJourRecord> stockJourListTemp = new ArrayList<>();
        if (endDate.compareTo(dateStr) >= 0) {
            EF01100839Request req839 = new EF01100839Request();
            req839.copyCommonParams(req);
            req839.setFunctionId(GrmFunctions.BROKER_STOCKJOUR_TODAY);
            req839.setFundAccount(req.getFundAccount());
            GrmResponseVO rsp839 = ef01110114.requestData(req839);
            if (rsp839.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
                stockJourListTemp.addAll((List) rsp839.getResult().get("data"));
            }
        }
        req.setFunctionId(GrmFunctions.BROKER_STOCKJOUR_HIS);
        GrmResponseVO rsp840 = ef01110172.requestData(req);
        if (rsp840.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            stockJourListTemp.addAll((List) rsp840.getResult().get("data"));
        } else {
            return rsp840;
        }
        if (CollectionUtils.isNotEmpty(stockJourListTemp) && StringUtils.isNotBlank(req.getExchangeType())) {
            //需要根据市场过滤结果
            for (Iterator<StockJourRecord> ite = stockJourListTemp.iterator(); ite.hasNext(); ) {
                StockJourRecord record = ite.next();
                if (!req.getExchangeType().equals(record.getExchangeType())) {
                    ite.remove();
                }
            }
        }
        List<StockJourRecord> stockJourList = new ArrayList<>();
        for (StockJourRecord stockJourRecord:stockJourListTemp){

            if("1".equals(isFilter) && (!stockJourRecord.getAssetId().endsWith("SH") && !stockJourRecord.getAssetId().endsWith("SZ"))){
                continue;
            }

            stockJourList.add(stockJourRecord);
        }

        return responseVO.setResult(stockJourList);
    }
}
