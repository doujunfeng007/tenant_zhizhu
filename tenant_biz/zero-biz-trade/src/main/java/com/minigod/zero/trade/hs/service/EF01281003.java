package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.filter.GrmServerHolder;
import com.minigod.zero.trade.hs.req.EF01110014Request;
import com.minigod.zero.trade.hs.req.EF01110015Request;
import com.minigod.zero.trade.hs.req.EF01281002Request;
import com.minigod.zero.trade.hs.resp.ClientCashSumInfo;
import com.minigod.zero.trade.hs.resp.FundAccountInfo;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取资金帐号列表，AE模式
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01281002Request.class,responseVo = FundAccountInfo.class)
public class EF01281003 extends Abstractbiz {
    @Resource
    private EF01110014 ef01110014;
    @Resource
    private EF01110015 ef01110015;
    @Resource
    private GrmServerHolder grmServerHolder;

    @Override
    protected <T extends GrmRequestVO> GrmResponseVO request(T request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        EF01281002Request req =(EF01281002Request) request;
        String lang = req.getLang();
        Map<String,String> oParam = new HashMap();
        if(StringUtils.isEmpty(lang)){
            lang = Constants.LANG_DEFAULT;
        }

        //操作员登录
        String operatorSessionId;
        if(grmServerHolder.isInnerClient(req.getSid())){
            operatorSessionId = Constants.innerClientSideSid;
        }else{
            return GrmResponseVO.getInstance().setErrorId(StaticCode.ErrorCode.INVALID_REQUEST).setErrorMsg(ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.INVALID_REQUEST, req.getLang()));
        }

        //获取营业部
        String branchNo = HsConstants.InnerBrokerConfig.INNER_OP_BRANCHNO;
        //获取帐号列表
        EF01110014Request ef01110014Request = new EF01110014Request();
        ef01110014Request.setFunctionId(GrmFunctions.BROKER_GET_CLIENT_FUND_ACCT_INFO);
        ef01110014Request.copyCommonParams(request);
        ef01110014Request.setBranchNo(branchNo);
        ef01110014Request.setClientId(req.getClientId());
        ef01110014Request.setSessionId(operatorSessionId);
        GrmResponseVO fundsVo = ef01110014.requestData(ef01110014Request);
        if(!fundsVo.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
            return fundsVo;
        }
        List<Object> fundAccts = (List<Object>) fundsVo.resultData();
        List<FundAccountInfo> fundAccountInfoWithCashs = new ArrayList<>();
        ClientCashSumInfo clientCashSumInfo ;
        for(Object fo :fundAccts){
            FundAccountInfo fundAcct = (FundAccountInfo) fo;
            String fundAccount = fundAcct.getFundAccount();

            //获取帐号类型
            EF01110015Request ef01110015Request = new EF01110015Request();
            ef01110015Request.setClientId(req.getClientId());
            ef01110015Request.setBranchNo(branchNo);
            ef01110015Request.setFundAccount(fundAccount);
            ef01110014Request.copyCommonParams(request);
            ef01110015Request.setSessionId(operatorSessionId);
            ef01110015Request.setFunctionId(GrmFunctions.BROKER_GET_FUND_ACCT_TYPE);
            GrmResponseVO ftVo= ef01110015.requestData(ef01110015Request);
            if(!ftVo.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
                return ftVo;
            }
            fundAcct = (FundAccountInfo)ftVo.resultData().get(0);
            responseVO.addResultData(fundAcct);
        }
        return responseVO;
    }
}
