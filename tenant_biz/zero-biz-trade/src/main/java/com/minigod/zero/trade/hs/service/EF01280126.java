package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.filter.GrmServerHolder;
import com.minigod.zero.trade.hs.req.EF01110014Request;
import com.minigod.zero.trade.hs.req.EF01110126Request;
import com.minigod.zero.trade.hs.req.EF01280126Request;
import com.minigod.zero.trade.hs.resp.EF01280126VO;
import com.minigod.zero.trade.hs.resp.FundAccountInfo;
import com.minigod.zero.trade.hs.vo.GrmRequestVO;
import com.minigod.zero.trade.hs.vo.GrmResponseVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取资金帐号列表，AE模式
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01280126Request.class,responseVo = EF01280126VO.class)
public class EF01280126 extends Abstractbiz {
    @Resource
    private EF01110014 ef01110014;
    @Resource
    private EF01110126 ef01110126;
    @Resource
    private GrmServerHolder grmServerHolder;

    @Override
    protected <T extends GrmRequestVO> GrmResponseVO request(T request) {
        EF01280126Request req =(EF01280126Request) request;
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
            return GrmResponseVO.getInstance().setErrorId(StaticCode.ErrorCode.INVALID_REQUEST).setErrorMsg(ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.INVALID_REQUEST, lang));
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
        for(Object fo :fundAccts){
            FundAccountInfo fundAcct = (FundAccountInfo) fo;
            String fundAccount = fundAcct.getFundAccount();

            //设置佣金
            EF01110126Request ef01110126Request = new EF01110126Request();
            ef01110126Request.setFunctionId(GrmFunctions.BROKER_TRADEFARE_SET);
            ef01110126Request.copyCommonParams(request);
            ef01110126Request.setClientId(req.getClientId());
            ef01110126Request.setFundAccount(fundAccount);
            ef01110126Request.setFareKindStr(req.getFareKindStr());
            ef01110126Request.setSessionId(operatorSessionId);
            GrmResponseVO ftVo = ef01110126.requestData(ef01110126Request);
            if(!ftVo.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)){
                return ftVo;
            }
        }
        return GrmResponseVO.getInstance();
    }
}
