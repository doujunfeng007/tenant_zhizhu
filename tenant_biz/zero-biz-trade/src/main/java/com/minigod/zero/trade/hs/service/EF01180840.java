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

/**
 * 查股票流水
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100840Request.class, responseVo = StockJourRecord.class)
public class EF01180840 extends Abstractbiz {
    @Resource
    private EF01100839 ef01100839;
    @Resource
    private EF01100840 ef01100840;

    @Override
    protected <T extends GrmRequestVO> GrmResponseVO request(T request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        EF01100840Request req = (EF01100840Request) request;
        String endDate = req.getEndDate();
        String dateStr = DateFormatUtils.format(new Date(), HsConstants.yyyyMMdd);
        List<StockJourRecord> stockJourList = new ArrayList<>();
        if (endDate.compareTo(dateStr) >= 0) {
            EF01100839Request req839 = new EF01100839Request();
            req839.copyCommonParams(req);
            req839.setFunctionId(GrmFunctions.CLIENT_STOCKJOUR_TODAY);
            req839.setFundAccount(req.getFundAccount());
            GrmResponseVO rsp839 = ef01100839.requestData(req839);
            if (rsp839.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
                stockJourList.addAll((List) rsp839.getResult().get("data"));
            }
        }
        req.setFunctionId(GrmFunctions.CLIENT_STOCKJOUR_HIS);
        GrmResponseVO rsp840 = ef01100840.requestData(req);
        if (rsp840.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            stockJourList.addAll((List) rsp840.getResult().get("data"));
        } else {
            return rsp840;
        }
        if (CollectionUtils.isNotEmpty(stockJourList) && StringUtils.isNotBlank(req.getExchangeType())) {
            //需要根据市场过滤结果
            for (Iterator<StockJourRecord> ite = stockJourList.iterator(); ite.hasNext(); ) {
                StockJourRecord record = ite.next();
                if (!req.getExchangeType().equals(record.getExchangeType())) {
                    ite.remove();
                }
            }
        }
        return responseVO.setResult(stockJourList);
    }
}
