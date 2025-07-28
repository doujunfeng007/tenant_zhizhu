package com.minigod.zero.trade.hs.service;

import com.minigod.zero.trade.hs.constants.*;
import com.minigod.zero.trade.hs.filter.GrmServerHolder;
import com.minigod.zero.trade.hs.req.EF01110014Request;
import com.minigod.zero.trade.hs.req.EF01110018Request;
import com.minigod.zero.trade.hs.req.EF01281018Request;
import com.minigod.zero.trade.hs.resp.EF01110018VO;
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
 * 校验客户号及密码正确性，内部接口。
 * Created by sunline on 2016/4/11 13:16.
 * sunline
 */
@Component
@GrmFunctionEntity(requestVo = EF01281018Request.class, responseVo = EF01110018VO.class)
public class EF01281018 extends Abstractbiz {
    @Resource
    private EF01110014 ef01110014;
    @Resource
    private EF01110018 ef01110018;
    @Resource
    private GrmServerHolder grmServerHolder;

    @Override
    protected <T extends GrmRequestVO> GrmResponseVO request(T request) {
        GrmResponseVO responseVO = GrmResponseVO.getInstance();
        EF01281018Request req = (EF01281018Request) request;
        String lang = req.getLang();
        Map<String, String> oParam = new HashMap();
        if (StringUtils.isEmpty(lang)) {
            lang = Constants.LANG_DEFAULT;
        }

        //操作员登录
        String operatorSessionId;
        if (grmServerHolder.isInnerClient(req.getSid())) {
            operatorSessionId = Constants.innerClientSideSid;
        } else {
            //本功能只能内部使用
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
        //ef01110014Request.setFundAccount(req.getFundAccount());
        ef01110014Request.setSessionId(operatorSessionId);
        GrmResponseVO fundsVo = ef01110014.requestData(ef01110014Request);
        if (!fundsVo.getErrorId().equals(GrmConstants.GRM_RESPONSE_OK)) {
            return fundsVo;
        }
        List<Object> fundAccts = (List<Object>) fundsVo.resultData();
        List<FundAccountInfo> fundAccountInfos = new ArrayList<>();
        String fundAccount;
        String mainFlag;
        for (Object fo : fundAccts) {
            FundAccountInfo fundAcct = (FundAccountInfo) fo;
            fundAccount = fundAcct.getFundAccount();
            //验证交易密码
            EF01110018Request ef01110018Request = new EF01110018Request();
            ef01110018Request.copyCommonParams(request);
            ef01110018Request.setFunctionId(GrmFunctions.CLIENT_PASSWD_VALIDATION);
            ef01110018Request.setClientId(req.getClientId()); //存量用户迁移过渡新增
            ef01110018Request.setFundAccount(fundAccount);
            ef01110018Request.setPassword(req.getPassword());
            ef01110018Request.setSessionId(operatorSessionId);
            return ef01110018.requestData(ef01110018Request);
        }
        return responseVO.setErrorId(StaticCode.ErrorCode.PASSWORD_CHECK_FAILED)
                .setErrorMsg(ErrorMsgHandler.getErrorMsg(StaticCode.ErrorCode.PASSWORD_CHECK_FAILED, lang));
    }
}
