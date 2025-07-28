package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.GrmConstants;
import com.minigod.zero.trade.hs.constants.GrmFunctionEntity;
import com.minigod.zero.trade.hs.constants.GrmFunctions;
import com.minigod.zero.trade.hs.constants.HsConstants;
import com.minigod.zero.trade.hs.req.EF01100404Request;
import com.minigod.zero.trade.hs.req.EF01100417Request;
import com.minigod.zero.trade.hs.resp.FundJourRecord;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 查资金流水，保存登录session
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01100417Request.class, responseVo = FundJourRecord.class)
public class EF01180417 extends Abstractbiz {
    @Resource
    private EF01100404 ef01000404;
    @Resource
    private EF01100417 ef01100417;

    @Override
    protected <T extends GrmRequestVO> GrmResponseVO request(T request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        EF01100417Request req = (EF01100417Request) request;
        String isFilter =  req.getIsFilter();
        String endDate = req.getEndDate();
        String dateStr = DateFormatUtils.format(new Date(), HsConstants.yyyyMMdd);
        List<FundJourRecord> fundJourListTemp = new ArrayList<>();
        if (endDate.compareTo(dateStr) >= 0) {
            EF01100404Request req404 = new EF01100404Request();
            req404.setFunctionId(GrmFunctions.CLIENT_FUNDJOUR_TODAY);
            req404.copyCommonParams(req);
            req404.setPassword(req.getPassword());
            req404.setFundAccount(req.getFundAccount());
            req404.setMoneyType(req.getMoneyType());
            GrmResponseVO rsp404 = ef01000404.requestData(req404);
            if (rsp404.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
                fundJourListTemp.addAll((List) rsp404.getResult().get("data"));
            }
        }
        req.setFunctionId(GrmFunctions.CLIENT_FUNDJOUR_HIS);
        GrmResponseVO rsp417 = ef01100417.requestData(req);
        if (rsp417.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            fundJourListTemp.addAll((List) rsp417.getResult().get("data"));
        } else {
            return rsp417;
        }
        List<FundJourRecord> fundJourList= new ArrayList<>();
        for (FundJourRecord fundJourRecord:fundJourListTemp){
            //todo 转换货币
            if("1".equals(isFilter) && !"0".equals(fundJourRecord.getCurrency())){
                continue;
            }
            fundJourList.add(fundJourRecord);
        }

        return responseVO.setResult(fundJourList);
    }
}
