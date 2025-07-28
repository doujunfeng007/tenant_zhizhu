package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.GrmFunctions;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01110113Request;
import com.minigod.zero.trade.hs.req.EF01110173Request;
import com.minigod.zero.trade.hs.resp.FundJourRecord;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@GrmFunctionEntity(requestVo = EF01110173Request.class, responseVo = FundJourRecord.class)
public class EF01180173 extends Abstractbiz {

    @Resource
    private EF01110113 ef01110113;

    @Resource
    private EF01110173 ef01110173;

    @Override
    protected <T extends GrmRequestVO> GrmResponseVO request(T request) throws Exception {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        EF01110173Request req = (EF01110173Request) request;
        String endDate = req.getEndDate();
        String dateStr = DateFormatUtils.format(new Date(), HsConstants.yyyyMMdd);
        List<FundJourRecord> fundJourList = new ArrayList<>();
        if (endDate.compareTo(dateStr) >= 0) {
            EF01110113Request req10113 = new EF01110113Request();
            req10113.setFunctionId(GrmFunctions.BROKER_FUNDJOUR_TODAY);
            req10113.copyCommonParams(req);
            req10113.setFundAccount(req.getFundAccount());
            req10113.setMoneyType(req.getMoneyType());
            GrmResponseVO rsp10113 = ef01110113.requestData(req10113);
            if (rsp10113.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
                fundJourList.addAll((List) rsp10113.getResult().get("data"));
            }
        }
        req.setFunctionId(GrmFunctions.BROKER_FUNDJOUR_HIS);
        GrmResponseVO rsp10173 = ef01110173.requestData(req);
        if (rsp10173.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            fundJourList.addAll((List) rsp10173.getResult().get("data"));
        } else {
            return rsp10173;
        }
        return responseVO.setResult(fundJourList);
    }
}
